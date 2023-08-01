from nb_local_lib import *
from pyzabbix import ZabbixAPI
from credentials import *
import requests
import json

ZABBIX_SERVER = Zabbix_Url

zapi = ZabbixAPI(ZABBIX_SERVER)
zapi.login(api_token=Zabbix_Token)

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
    if not Host_Id:
        print(Host_Id)
        return False
    else:
        zapi.host.update(
            host = After_DeviceName,
            hostid = Host_Id,
            )
        print(f"SUCCESS | Name change from {Before_DeviceName} to {After_DeviceName}")
        print("****************************************************************************")
        return True
        


def create_hosts_if_necessary(request_data,Before_DeviceName):    
    Host_Id = zapi.host.get(filter={"name": Before_DeviceName},output=['hostid'])
    print(Host_Id)
    if (len(Host_Id) == 0):
        print()

        return print("SUCCESSSSSSSSSSSSSSSSSss")
    else:
        return False
###############################################
# PLATFORM | TEMPLATE
# -------------------

def get_Host_Templates(DEVICE_NAME):
    
    Host_Id_pre = zapi.host.get(filter={"name": DEVICE_NAME},output=['hostid'])
    if (len(Host_Id_pre) == 0):
        return print("HOST NOT EXIST IN ZABBIX ---------")
    Host_Id = Host_Id_pre[0]['hostid']
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

def enable_Interface_By_IP_and_HostName(DEVICE_NAME, IP_ADDRESS, INTERFACE_ID):
    host = zapi.host.get(filter={'host': DEVICE_NAME}, selectInterfaces=['interfaceid', 'ip','main','type','dns'])
    if host:

        
        # Find the interface that matches that is DEFAULT
        DISABLE_interface_id = None
        for interface in host[0]['interfaces']:
            if interface['main'] == "1":
                DISABLE_interface_id = interface['interfaceid']
                break
                
        # Set the new default interface by updating the host
        data = {
                    "jsonrpc": "2.0",
                    "method": "hostinterface.update",
                    "params": [{
                        "interfaceid": str(INTERFACE_ID[0]),       # ACTIVATING
                        "main": 1       
                        },
                        {
                        "interfaceid": str(DISABLE_interface_id),      # DISACTIVATING
                        "main": 0
                        }],
                    "id": 1
                }

        r = requests.post(zabbix_url,json=data,headers=zabbix_headers)
        response = r.json()
            
        print("SUCCESS | ENABLE DEFAULT INTERFACE")
        return DISABLE_interface_id
            
       
    else:
        print(f"No host found with the name '{DEVICE_NAME}'.")
#enable_Interface_By_IP_and_HostName("adrian","9.9.9.9")


def create_interface(HOST_ID, IP, DNS, USEID, PORT):
        if len(zapi.hostinterface.get(hostids=HOST_ID, output=["interfaceid"])) == 0:
            MAIN = 1
        else:
            MAIN = 0
        # Create interface with or without interface
        
        new_interface = {
            "type": 1,  # Tipo de interfaz (1 para agente, 2 para SNMP, 3 para JMX, etc.)
            "main": MAIN,  # 1 para hacerla la interfaz por defecto, 0 en caso contrario
            "ip": IP,  # Dirección IP de la interfaz
            "dns": DNS,  # : ""  # Nombre DNS (opcional, déjalo vacío si no es necesario)
            "port": PORT ,  # Puerto de la interfaz (por ejemplo, 10050 para el agente Zabbix)
            "useip": USEID,  # 1 para usar dirección IP, 0 para usar nombre DNS
            "hostid": HOST_ID  # ID del host al que se agregará la interfaz
        }
        
        r = zapi.hostinterface.create(new_interface)
        print("SUCCESS | CREATE")
        return r['interfaceids']
   

def delete_interfaces_IPs(HOST_ID,IP, Interface_id_NO_remove):
        interfaceID_to_Delete_temporal = zapi.hostinterface.get(hostids=HOST_ID, filter={"ip": IP})
        
        interfaceID_to_Delete = [item for item in interfaceID_to_Delete_temporal if item['interfaceid'] != Interface_id_NO_remove[0]]
  
        for interfaces in interfaceID_to_Delete:

            data = {
                "jsonrpc": "2.0",
                "method": "hostinterface.delete",
                "params": [
                    interfaces['interfaceid']
                ],
                "id": 1
            }
            
            r = requests.post(zabbix_url,json=data,headers=zabbix_headers)

            print( "SUCCESS | DELETE")
            try:
                if r.json()['error']: 
                    dns_message = "Netbox-Zabbix_Cant-Delete"
                    update_data = {
                                'interfaceid': interfaces['interfaceid'],
                                'dns': dns_message,
                                "useip": 0,
                            }

                    r = zapi.hostinterface.update(update_data)
                    
                    print(f"WARNING | IP ADDRESS | The interface of: '{ip}' can't no be delete, setting up DNSname as '{dns_message}'." ) 
                
            except:
                print("SUCCESS | DELETE ALL SUCCESSFULLY")
            
#delete_interfaces_IP("10762","192.168.1.60")

def setup_primary_ip(host_id,ip):
            info_interface = zapi.hostinterface.get(hostids=host_id, filter={"ip": ip})
        
            # Set up main ip ( primary IP )    

            
            if (len(info_interface) == 0) :
                print(f"WARNING | IP ADDRESS | IP: {ip} no found")
                enable_interface_id = create_interface(host_id, ip,"","1","9999")
                
                return enable_interface_id
        
            if (len(info_interface) > 1): 
                first_object = info_interface[0]
                enable_interface_id = create_interface(host_id, first_object['ip'], first_object['dns'], first_object['useip'], first_object['port'])
                
                return enable_interface_id     
        
            else:
                
                enable_interface_id = create_interface(host_id, info_interface[0]['ip'], info_interface[0]['dns'], info_interface[0]['useip'], info_interface[0]['port'])
                
                return enable_interface_id
            
#setup_primary_ip("adrian3","10763","192.168.1.52","192.168.1.52")

def update_ips_host(DEVICE_NAME, BEFORE_PRIMARY_IPv4, AFTER_PRIMARY_IPv4):
    
    BEFORE_PRIMARY_IPv4_ip  = get_IPadress_By_IPaddress_ID(BEFORE_PRIMARY_IPv4)
    AFTER_PRIMARY_IPv4_ip   = get_IPadress_By_IPaddress_ID(AFTER_PRIMARY_IPv4)
    
    # Validar que valores faltan en Zabbix (ADD) y valores que estan sobrando en Zabbix (DELETE)
    host = zapi.host.get(filter={"host": DEVICE_NAME}, output=["hostid", "host"])
    if not host:
        return print(f"ERROR | IP ADDRESS | Host: {DEVICE_NAME} no found")
    host_id = host[0]['hostid']
    
    NETOBX_IPS = get_ips_by_devices(DEVICE_NAME)
    
    NETOBX_IPS.remove(AFTER_PRIMARY_IPv4_ip)
    NETOBX_IPS.insert(0,AFTER_PRIMARY_IPv4_ip)
    

    
    
    for ip in NETOBX_IPS:
        

        # Get the templates of the interfaces of ONE by filtering by IP [template_id, ip, dns, IP or DNS]
        info_interface = zapi.hostinterface.get(hostids=host_id, filter={"ip": ip})

        # Set up main ip ( primary IP )    
        if (ip == AFTER_PRIMARY_IPv4_ip):
            
            enable_interface_id = setup_primary_ip(host_id,ip)
            
            try:
                enable_Interface_By_IP_and_HostName(DEVICE_NAME, ip,enable_interface_id)
            except:
                print("ERROR | NO PREVIUS INTERFACE")
                
            delete_interfaces_IPs(host_id,ip,enable_interface_id)
            
            continue
        
        if not info_interface:
            print(f"WARNING | IP ADDRESS | IP: {ip} no found")
            create_interface(host_id, ip,"","1","9999")
            continue
        
        else:
            #print(info_interface)
            info_interface = info_interface[0]
            
    # Create Interface with IP, DNS, useip
        create_interface_id = create_interface(host_id, info_interface['ip'], info_interface['dns'], info_interface['useip'], info_interface['port'])
        
    # Remove until is over all the IPS are delete
        delete_interfaces_IPs(host_id,ip,create_interface_id)
        

#update_ips_host("adrian3","192.168.1.51","192.168.1.52")
