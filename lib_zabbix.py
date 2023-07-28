from nb_local_lib import *
from pyzabbix import ZabbixAPI
from credentials import *
import requests
import json

ZABBIX_SERVER = Zabbix_Url

zapi = ZabbixAPI(ZABBIX_SERVER)
zapi.login(Zabbix_User, Zabbix_Password)

zabbix_url = Zabbix_Url + "api_jsonrpc.php"
zabbix_headers = {
    'Content-Type': 'application/json',
    'Authorization': f'Bearer {Zabbix_Token}'
}

def zab_patch_HostGroups(nb_device_name_name, nb_site_name):
    try:

        zb_hostgroup = zapi.hostgroup.get(output=["groupid", "name"], filter={"name": nb_site_name})

        zb_host_get_1  = zapi.host.get(output=["hostid", "name", "interfaces"], filter={"name": nb_device_name_name}, selectInterfaces=["interfaceid", "ip", "dns"], selectGroups=["groupid"], selectParentTemplates = ["templateid"])

        zab_hostgroup_id = zb_hostgroup[0]['groupid']

        gid = str(zab_hostgroup_id)

        zb_host_id = zb_host_get_1[0]['hostid']
        json_list = zb_host_get_1[0]['groups']

        variable = {'groupid': gid} in json_list
        #print(variable)

        if variable == True:
            print("Se encontro, no se hara nada")
        else:
            aditional = [{"groupid" : f'{zab_hostgroup_id}'}]
            json_list += aditional
            #print(json_list)

            print("No encontro, UPDATEEEE")
            zapi.host.update(hostid = zb_host_id,
                groups = json_list)

            
    except Exception as e:
        print(f'{e}')
        print(f"ZABBIX | ERROR | Hostgroup and Site ({nb_site_name}) DON'T MATCH")

def zab_patch_Template( platform_name, nb_device_name):
    try: #MATCH PLATFORM Y TEMPLATE

        zab_host_get = zapi.host.get(output=["hostid", "name", "interfaces","template"], filter={"name": nb_device_name}, selectInterfaces=["interfaceid", "ip", "dns"], selectGroups=["groupid"], selectParentTemplates = ["templateid"])
        host_id = zab_host_get[0]['hostid']
        zab_template_get = zapi.template.get(output=["name", "templateid"], filter={"name": platform_name})

        print(zab_host_get)
        print("")
        print(zab_template_get)

        platform_template_id = zab_template_get[0]['templateid']

        t_id = str(platform_template_id)
        array_list = zab_host_get[0]['parentTemplates']

        variable = {'templateid': t_id} in array_list    
        
        if variable == True:
            print(f'ZABBIX | WARNING | Template ({platform_name}) already exists')

        else: 
            aditional = [{'templateid': f'{platform_template_id}'}]
            array_list += aditional
            #print(array_list)
            zapi.host.update(hostid = host_id,
            templates = array_list
                )
            print(f"ZABBIX | SUCCESS | template ({platform_name}) updated ")

    except: 
        print(f"ZABBIX | ERROR | PLATFORM ({platform_name}) DONT MATCH with ZABBIX TEMPLATE")

###############################################

# DEVICE NAME | HOST NAME
# -----------------------

def update_Hostname(Before_DeviceName, After_DeviceName):
    # Find the HostID by DeviceName
    Host_Id = zapi.host.get(filter={"name": Before_DeviceName},output=['hostid'])[0]['hostid']
    
    
    zapi.host.update(
        host = After_DeviceName,
        hostid = Host_Id,
        )
    print(f"SUCCESS | Name change from {Before_DeviceName} to {After_DeviceName}")
    print("****************************************************************************")
    
###############################################
# PLATFORM | TEMPLATE
# -------------------

def get_Host_Templates(DEVICE_NAME):
    
    Host_Id = zapi.host.get(filter={"name": DEVICE_NAME},output=['hostid'])[0]['hostid']
    if (len(Host_Id) == 0):
        return print("HOST NOT EXIST IN ZABBIX ---------")
    
    data = {
        "jsonrpc": "2.0",
        "method": "host.get",
        "params": {
            "output": [
                "hostid"
            ],
            "selectParentTemplates": [
                "templateid",
                #"name",
            ],
            "hostids": Host_Id
        },
        "id": 1,
    }
    
    r = requests.get(zabbix_url,json=data,headers=zabbix_headers)
    
	    #print(json.dumps(r.json(), indent=4, sort_keys=True))
    return r.json()['result'][0]['parentTemplates']

def get_Template_ID(TEMPLATE_NAME):
    data = {
    "jsonrpc": "2.0",
    "method": "template.get",
    "params": {
        "output": "simple",
        "filter": {
            "host": TEMPLATE_NAME
        }
    },
    "id": 1
    }
  
    r = requests.get(zabbix_url,json=data,headers=zabbix_headers)
    return r.json()['result']
    
def post_update_Template_Host(DEVICE_NAME,Template_Array):
    # Find the HOST ID by Device Name 
    Host_Id = zapi.host.get(filter={"name": DEVICE_NAME},output=['hostid'])[0]['hostid']
        
    data = {
        "jsonrpc": "2.0",
        "method": "host.update",
        "params": {
            "hostid": Host_Id,
            "templates": Template_Array
        },
        "id": 1
    }
    
    r = requests.post(zabbix_url,json=data,headers=zabbix_headers)
    return r.json()

def update_Template_Host(DEVICE_NAME, BEFORE_PLATFORM, AFTER_PLATFORM):
    
        # PLATFORM name | "Windows Server 2019"
        BEFORE_PLATFORM_name = get_Platform_by_ID(BEFORE_PLATFORM)
        AFTER_PLATFORM_name =  get_Platform_by_ID(AFTER_PLATFORM)  
    
        Template_Array = []
        templates_of_the_current_host = get_Host_Templates(DEVICE_NAME)
        
        # Templates ID by the name | {'templateid': '1212'}
        BEFORE_PLATFORM_Id = get_Template_ID(BEFORE_PLATFORM_name)
        AFTER_PLATFORM_Id = get_Template_ID(AFTER_PLATFORM_name)
        
        ADD_TEMPLATE = True
        
        if(len(AFTER_PLATFORM_Id) == 0):
            return print(f"ERROR | PLATFORM - TEMPLATE | Template: {AFTER_PLATFORM_name} not exits\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        
        # Add the old templates host
        for i in templates_of_the_current_host:
            Template_Array.append(i)
        try:
            for i in Template_Array:
            # Check if the PRE platform exist to DELETE
                if  ( i['templateid'] == BEFORE_PLATFORM_Id[0]['templateid']):
                    Template_Array.remove(BEFORE_PLATFORM_Id[0])
                    print(f"SUCCESS | PLATFORM - TEMPLATE | DELETE ")
        except:
            print(f"WARNING | PLATFORM - TEMPLATE | The template that should have been replaced was not found. ")
                    
            # Check if the POST platform exist to ADD
            if  ( i['templateid'] == AFTER_PLATFORM_Id[0]['templateid']):
                ADD_TEMPLATE = False
        
        if (ADD_TEMPLATE == True):
            if( AFTER_PLATFORM == None):
                print(f"WARNING | PLATFORM - TEMPLATE | Template: '{AFTER_PLATFORM}' is updated to the Host: '{DEVICE_NAME}' ")
            else:
                Template_Array.append(AFTER_PLATFORM_Id[0])
            print(f"SUCCESS | PLATFORM - TEMPLATE | ADD")
        else:
            print(f"WARNING | PLATFORM - TEMPLATE | Template: '{AFTER_PLATFORM_name}' Already exits in '{DEVICE_NAME}' ")
        
        post_update_Template_Host(DEVICE_NAME,Template_Array)

        print("")
        print(f"SUCCES | PLATFORM - TEMPLATE | Host: {DEVICE_NAME} ")
        print(f"SUCCES | PLATFORM - TEMPLATE | Template DELTETED: {BEFORE_PLATFORM_name}")
        print(f"SUCCES | PLATFORM - TEMPLATE | Template ADDED: {AFTER_PLATFORM_name} ")
        print("*******************************************")
        
###############################################    
    
# SITE - HOSTGROUPS
# ----------------

def get_Host_HostGroups(DEVICE_NAME):
    
    Host_Id = zapi.host.get(filter={"name": DEVICE_NAME},output=['hostid'])[0]['hostid']
    if (len(Host_Id) == 0):
        return print("HOST NOT EXIST IN ZABBIX ---------")
    
    data = {
            "jsonrpc": "2.0",
            "method": "host.get",
            "params": {
                "output": ["hostid"],
                "selectHostGroups": "extend",
                "filter": {
                    "host": [
                        DEVICE_NAME
                    ]
                }
            },
            "id": 1
        }
    
    r = requests.get(zabbix_url,json=data,headers=zabbix_headers)
    array_hostgroups_ids = [{'groupid': item['groupid']} for item in r.json()['result'][0]['hostgroups']]
    return array_hostgroups_ids
#print(get_Host_HostGroups_IDs("adrian"))

def post_Hostgroups_host(DEVICE_NAME,Sites_Array):
    host = zapi.host.get(filter={"host": DEVICE_NAME}, selectGroups="extend")
    if not host:
        print(f"HOST: {DEVICE_NAME} NOT EXIST IN ZABBIX")
    else:
        host_id = host[0]['hostid']

        # Lista de IDs de los hostgroups a los que deseas asignar el host
        # Reemplaza [ID1, ID2, ...] con los IDs de los hostgroups deseados

        # Actualizar los hostgroups del host usando host.update
        r = zapi.host.update(hostid=host_id, groups=Sites_Array)

        if not r['hostids']:
            print(f"ERROR | SITES - HOSTGROUPS | Host: {DEVICE_NAME} was not updated correctly")
#post_Hostgroups_host("adrian",[{'groupid': '2', 'name': 'Linux servers', 'flags': '0', 'uuid': 'dc579cd7a1a34222933f24f52a68bcd8'}, {'groupid': '22', 'name': 'SITIO-01', 'flags': '0', 'uuid': '457d5ebadaa346dcaf5a5bd90d400407'}])

def get_HostGroups_ID(SITE_NAME):
    r = zapi.hostgroup.get(filter={"name": SITE_NAME}, output=["groupid"])
    
    return r
#print(get_HostGroups_ID("SITIO-01"))

def update_HostGroup_Host(DEVICE_NAME, BEFORE_SITE, AFTER_SITE):

    # Transform the Site ID to NAME
    BEFORE_SITE_name = get_Site_by_ID(BEFORE_SITE)
    AFTER_SITE_name = get_Site_by_ID(AFTER_SITE)
    
    # Find the current HostGroups of the Host to save them
    Site_Array = []
    sites_of_the_current_host = get_Host_HostGroups(DEVICE_NAME)
    
    # HOSTGROUPS IDs by the Site
    BEFORE_SITE_Id = get_HostGroups_ID(BEFORE_SITE_name)
    AFTER_SITE_Id = get_HostGroups_ID(AFTER_SITE_name)

    ADD_TEMPLATE = True
    
    if(len(AFTER_SITE_Id) == 0):
        return print(f"ERROR | SITE - HOSTGROUP | HostGroup: {AFTER_SITE_name} not exits\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")

    # Add the old Hostgroups to the Site_Array
    for i in sites_of_the_current_host:
        Site_Array.append(i)

    try:
        for i in Site_Array:
            # Check if the PRE platform exist to DELETE
            if( i['groupid'] == BEFORE_SITE_Id[0]['groupid']):
                Site_Array.remove(BEFORE_SITE_Id[0])
                print(f"SUCCESS | SITE - HOSTGROUP | DELETE")
    except:
        print(f"WARNING | SITE - HOSTGROUP | The HostGroup that should have been replaced was not found. ")
        
        
        # Check if the POST platform exist to ADD
        if  ( i['groupid'] == AFTER_SITE_Id[0]['groupid']):
            ADD_TEMPLATE = False
    
    # Adding the Hostgroups        
    if (ADD_TEMPLATE == True):
        if( AFTER_SITE == None):
            print(f"WARNING | SITE - HOSTGROUP | HostGroup: '{AFTER_SITE}' is updated to the Host: '{DEVICE_NAME}' ")
        else:
            Site_Array.append(AFTER_SITE_Id[0])
            
        print(f"SUCCESS | SITE - HOSTGROUP | ADD")
            
    else:
        print(f"WARNING | SITE - HOSTGROUP | HostGroup: '{AFTER_SITE}' Already exits in '{DEVICE_NAME}'")
        
    post_Hostgroups_host(DEVICE_NAME,Site_Array)
       
    print("")
    print(f"SUCCES | SITE - HOSTGROUP | Host: {DEVICE_NAME} ")
    print(f"SUCCES | SITE - HOSTGROUP | HostGroup DELETED: {BEFORE_SITE_name}")
    print(f"SUCCES | SITE - HOSTGROUP | Template ADDED: {AFTER_SITE_name} ")
    print("*******************************************")
#update_HostGroup_Host("adrian","1", "2")


###############################################3

# IP ADDRESS

def enable_Interface_By_IP_and_HostName(host_id, IP_ADDRESS):
    host = host_id
    
    ENABLE_interface_id = None
    for interface in host[0]['interfaces']:
        if interface['ip'] == IP_ADDRESS or interface['dns'] == IP_ADDRESS:
            ENABLE_interface_id = interface['interfaceid']
            break
    
    # Find the interface that matches that is DEFAULT
    DISABLE_interface_id = None
    for interface in host[0]['interfaces']:
        
        if interface['main'] == "1":
            DISABLE_interface_id = interface['interfaceid']
            break
            
    print("")
    if ENABLE_interface_id:
       
        # Set the new default interface by updating the host
        data = {
                "jsonrpc": "2.0",
                "method": "hostinterface.update",
                "params": [{
                    "interfaceid": ENABLE_interface_id,       # ACTIVATING
                    "main": 1       
                    },
                    {
                    "interfaceid": DISABLE_interface_id,      # DISACTIVATING
                    "main": 0
                    }],
                "id": 1
            }
        r = requests.post(zabbix_url,json=data,headers=zabbix_headers)
        response = r.json()
        
        if response and 'hostids' in response:
            print(f"The default interface has been changed to {IP_ADDRESS}.")
        
    else:
        print(f"No interface found with the IP or DNS '{IP_ADDRESS}'.")
    
#enable_Interface_By_IP_and_HostName("adrian","9.9.9.9")

def update_InterfaceIP_By_IP(HOST_ID, IP, IP_to_FIND):

    # Obtener las interfaces del host por el nombre del IP
    ZABBIX_INTERFACES = zapi.hostinterface.get(hostids=HOST_ID, filter={"ip": IP_to_FIND})
    
    if(len(ZABBIX_INTERFACES) > 1):
        master_interface_id = ZABBIX_INTERFACES[0]['interfaceid']
        for i in ZABBIX_INTERFACES:
            if (i['interfaceid'] != master_interface_id ):
                data = {
                    "jsonrpc": "2.0",
                    "method": "hostinterface.delete",
                    "params": [
                        i['interfaceid']
                    ],
                    "id": 1
                }
                
                r = requests.post(zabbix_url,json=data,headers=zabbix_headers)
                if not r:
                    print( "ERROR --REMOVE INTERFACE | update_InterfaceIP_By_IP -----------------")
    # Verificar si se encontró una interfaz con el IP especificado
    if (len(ZABBIX_INTERFACES) == 0 ):
        
        print(f" UPDATE| No se encontró una interfaz con el ip: {IP_to_FIND} para el host_ID: {HOST_ID}")
        return False
    else:
        for i in ZABBIX_INTERFACES:
            # Actualizar la dirección IP de la interfaz encontrada
            interface_id = i['interfaceid']
            new_ip = IP
            update_result = zapi.hostinterface.update(interfaceid=interface_id, ip=new_ip, dns_name="")
            
            if update_result['interfaceids']:
                print(f"SUCCESS | IP ADDRESS | From:{IP_to_FIND} to {IP}")
            else:
                print(f"ERROR | IP ADDRESS | IP: {IP} not updated")
    
#update_InterfaceIP_By_IP("host_id", "9.9.9.9", "9.1.1.9")

def delete_InterfaceIP_By_IP(HOST_ID, ARRAY_IP_to_DELETE):
    # Obtener las interfaces del host por el nombre del IP
    ARRAY_IP_to_DELETE_IPS = []
    
    if isinstance(ARRAY_IP_to_DELETE, str):
        ARRAY_IP_to_DELETE_IPS.append(ARRAY_IP_to_DELETE)
        
    elif isinstance(ARRAY_IP_to_DELETE, list):
        ARRAY_IP_to_DELETE_IPS = ARRAY_IP_to_DELETE
    
    for ip in ARRAY_IP_to_DELETE_IPS:
        interfaceID_to_Delete = zapi.hostinterface.get(hostids=HOST_ID, filter={"ip": ip})
        # Verificar si se encontró una interfaz con el IP especificado
        if len(interfaceID_to_Delete) == 0:
            print(f" DELETE | IP ADDRESS | No se encontró una interfaz con el ip: {ip} para el host_ID: {HOST_ID}")
            return False
        
        else:
            interface_id_del = str(interfaceID_to_Delete[0]['interfaceid'])
            data = {
                "jsonrpc": "2.0",
                "method": "hostinterface.delete",
                "params": [
                    interface_id_del
                ],
                "id": 1
            }
            
            r = requests.post(zabbix_url,json=data,headers=zabbix_headers)
            
            # Validate if the intereface cannot be deleted
            try:
                if r.json()['error']:
                    dns_message = "Netbox-Zabbix_Cant-Delete"
                    update_data = {
                            'interfaceid': interface_id_del,
                            'dns': dns_message,
                            "useip": 0
                        }

                    r = zapi.hostinterface.update(update_data)
                        
                    return print(f"WARNING | IP ADDRESS | The interface of: '{ip}' can't no be delete, setting up DNSname as '{dns_message}'." ) 
            except:
                print(f"SUCCES | IP ADDRESS | The interface of: '{ip}' was deleted " )  
            print(f" " )            
#delete_InterfaceIP_By_IP("10762","9.9.9.9")

def create_interface(zab_host_id, ARRAY_IP_to_ADD):
    ARRAY_IP_to_ADD_ids = []
    if isinstance(ARRAY_IP_to_ADD, str):
        ARRAY_IP_to_ADD_ids = [ARRAY_IP_to_ADD]
    elif isinstance(ARRAY_IP_to_ADD, list):
        ARRAY_IP_to_ADD_ids = ARRAY_IP_to_ADD
    print("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee")
    if len(ARRAY_IP_to_ADD_ids) == 1:
        new_interface = {
            "type": 1,  # Tipo de interfaz (1 para agente, 2 para SNMP, 3 para JMX, etc.)
            "main": 0,  # 1 para hacerla la interfaz por defecto, 0 en caso contrario
            "ip": ARRAY_IP_to_ADD,  # Dirección IP de la interfaz
            "dns": "",  # Nombre DNS (opcional, déjalo vacío si no es necesario)
            "port": "9999" ,  # Puerto de la interfaz (por ejemplo, 10050 para el agente Zabbix)
            "useip": 1,  # 1 para usar dirección IP, 0 para usar nombre DNS
            "hostid": zab_host_id  # ID del host al que se agregará la interfaz
        }
        r = zapi.hostinterface.create(new_interface)
        return True
    for i in ARRAY_IP_to_ADD_ids:
        print("Aaaaaaaaaaaaaaaaaaaaaaaaaaa")
        # All this parameters are mandary
        new_interface = {
            "type": 1,  # Tipo de interfaz (1 para agente, 2 para SNMP, 3 para JMX, etc.)
            "main": 0,  # 1 para hacerla la interfaz por defecto, 0 en caso contrario
            "ip": i,  # Dirección IP de la interfaz
            "dns": "",  # Nombre DNS (opcional, déjalo vacío si no es necesario)
            "port": "9999" ,  # Puerto de la interfaz (por ejemplo, 10050 para el agente Zabbix)
            "useip": 1,  # 1 para usar dirección IP, 0 para usar nombre DNS
            "hostid": zab_host_id  # ID del host al que se agregará la interfaz
        }
        r = zapi.hostinterface.create(new_interface)
        print(r)
        print(f'SUCCESS | IP ADDRESS |ADD interface: {i}')
#create_interface("10762", "192.168.1.52")

def update_ips_host(DEVICE_NAME, BEFORE_PRIMARY_IPv4, AFTER_PRIMARY_IPv4): # <--------
    
    BEFORE_PRIMARY_IPv4_ip  = get_IPadress_By_IPaddress_ID(BEFORE_PRIMARY_IPv4)
    AFTER_PRIMARY_IPv4_ip   = get_IPadress_By_IPaddress_ID(AFTER_PRIMARY_IPv4)
    
    # Validar que valores faltan en Zabbix (ADD) y valores que estan sobrando en Zabbix (DELETE)
    host = zapi.host.get(filter={"host": DEVICE_NAME}, output=["hostid", "host"])
    if not host:
        return print(f"ERROR | IP ADDRESS | Host: {DEVICE_NAME} no found")
    host_id = host[0]['hostid']
    
    ZABBIX_INTERFACES = zapi.hostinterface.get(hostids=host_id)
    ZABBIX_INTERFACES_IPs = [item['ip'] for item in ZABBIX_INTERFACES]
    NETOBX_IPS = get_ips_by_devices(DEVICE_NAME)
    
    print(ZABBIX_INTERFACES_IPs)
    print(NETOBX_IPS)
    print()
    
    # 1) Setup the Main DEFAULT to the primary IP
    if( update_InterfaceIP_By_IP(host_id, AFTER_PRIMARY_IPv4_ip, BEFORE_PRIMARY_IPv4_ip) == False):
        print("SUCCESS | IP ADDRESS | No interface, creating one")
        create_interface(host_id, AFTER_PRIMARY_IPv4)
        NETOBX_IPS.remove(AFTER_PRIMARY_IPv4_ip)
    try:
        enable_Interface_By_IP_and_HostName(host_id,AFTER_PRIMARY_IPv4_ip)
    except:
        print("ERROR | IP ADDRESS | No interface to change the DEFAULT interface")
    # Encontrar los valores que sobran en el array zabbix (DELETE)
    valores_sobran_zabbix = None
    valores_sobran_zabbix = list(set(ZABBIX_INTERFACES_IPs) - set(NETOBX_IPS))


    # Encontrar los valores que faltan en el array b para que sea igual a NETBOX (ADD)
    valores_faltan_netbox = None
    valores_faltan_netbox = list(set(NETOBX_IPS) - set(ZABBIX_INTERFACES_IPs))

    print(valores_faltan_netbox)
    print(valores_sobran_zabbix)
    print()
    # ACTIONS
    if( len(valores_sobran_zabbix) >= len(valores_faltan_netbox)):
        index = 0
        for n, z in zip(valores_faltan_netbox, valores_sobran_zabbix):
            print(n)
            print(z)
            update_InterfaceIP_By_IP(host_id, n, z)
            valores_faltan_netbox.remove(n)
            valores_sobran_zabbix.remove(z)
            
    delete_InterfaceIP_By_IP(host_id, valores_sobran_zabbix)  
              
    create_interface(host_id, valores_faltan_netbox)
    print(valores_faltan_netbox)
    print(valores_sobran_zabbix)
    print()
#update_ips_host("adrian","192.168.1.202","192.168.1.202")
