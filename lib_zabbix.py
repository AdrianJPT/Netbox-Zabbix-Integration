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
def update_Hostname(Before_DeviceName, After_DeviceName):
    # Find the HostID by DeviceName
    Host_Id = zapi.host.get(filter={"name": Before_DeviceName},output=['hostid'])[0]['hostid']
    
    
    zapi.host.update(
        host = After_DeviceName,
        hostid = Host_Id,
        )
    print(f"SUCCESS | Name change from {Before_DeviceName} to {After_DeviceName}")
    print("****************************************************************************")
    

# PLATFORM | TEMPLATE

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
    
    # Check for a PLATFORM UPDATE
    #if(BEFORE_PLATFORM != AFTER_PLATFORM):
        #FIND and Save Templates in a Array
        Template_Array = []
        templates_of_the_current_host = get_Host_Templates(DEVICE_NAME)
        
        # Templates ID by the name | {'templateid': '1212'}
        BEFORE_PLATFORM_Id = get_Template_ID(BEFORE_PLATFORM_name)
        AFTER_PLATFORM_Id = get_Template_ID(AFTER_PLATFORM_name)
        
        ADD_TEMPLATE = True
        
        if(len(AFTER_PLATFORM_Id) == 0):
            return print(f"ERROR | PLATFORM - TEMPLATE | Template: {AFTER_PLATFORM_name} not exits ")
        
        # Add the old templates host
        for i in templates_of_the_current_host:
            Template_Array.append(i)
        
        for i in Template_Array:
            # Check if the PRE platform exist to DELETE
            if  ( i['templateid'] == BEFORE_PLATFORM_Id[0]['templateid']):
                try:
                    Template_Array.remove(BEFORE_PLATFORM_Id[0])
                    print(f"SUCCESS | PLATFORM - TEMPLATE | Delete ")
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
            print(f"SUCCESS | PLATFORM - TEMPLATE | ADD ")
        else:
            print(f"WARNING | PLATFORM - TEMPLATE | Template: '{AFTER_PLATFORM_name}' Already exits in '{DEVICE_NAME}' ")


        
        post_update_Template_Host(DEVICE_NAME,Template_Array)
       
        print(f"SUCCES | PLATFORM - TEMPLATE | Template updated from: {BEFORE_PLATFORM} to {AFTER_PLATFORM}")
        print("")
        print(f"SUCCES | PLATFORM - TEMPLATE | Host: {DEVICE_NAME} ")
        print(f"SUCCES | PLATFORM - TEMPLATE | Template BEFORE: {BEFORE_PLATFORM_name}")
        print(f"SUCCES | PLATFORM - TEMPLATE | Template NOW: {AFTER_PLATFORM_name} ")
        print("*******************************************")
        
