from flask import Flask, jsonify, request,redirect
import json
import requests
import traceback
# RESOLVE IP ADDRESS
import ipaddress 
import lib_zabbix as za

from credentials import *
import nb_local_lib as ntbx


# ZABBIXs
from pyzabbix import ZabbixAPI, ZabbixAPIException



app = Flask(__name__)

ZABBIX_SERVER = Zabbix_Url

zapi = ZabbixAPI(ZABBIX_SERVER)
zapi.login(api_token=Zabbix_Token)


@app.route('/create', methods=['POST'])
def zabbix_host_create():

    ## LEER REQUEST DE NETBOX
    if(request.data):

        reading_post = request.get_json()
        print(reading_post)
        
        nb_device_name = reading_post['data']['name']
        nb_site_name = reading_post['data']['site']['name']
    
        
        # HOSTGROUP - SITES | 
        try:
            # HOSTGROUP - SITES | TEMPLATE - PLATFORM                
            nb_platform = reading_post['data']['platform']['name']
            print(nb_platform)
            template = zapi.template.get(filter={'host': nb_platform})
            
            find_hostgroup = zapi.hostgroup.get(filter={"name": nb_site_name},output=['groupid'])
            # Validate HOSTGROPS
            if(len(find_hostgroup) != 0):
                zapi.host.create(
                
                host=nb_device_name,
                interfaces=[{
                    'type': 1,
                    'main': 1,
                    'useip': 0,
                    'ip': '',
                    'dns': 'netbox-zabbix-PrimaryIP',
                    'port': '9999'
                }],
                groups=[{'groupid': find_hostgroup[0]['groupid']}],
                templates=[{'templateid': template[0]['templateid']}]
                )
                
                print(f"ZABBIX | SUCCESS | Created ({nb_device_name})")
                
            else:
                print(f'HostGroup {nb_site_name} not found in ZABBIX')
                
            # HOSTGROUP - SITES 
             
        except :
            print("NO PLATFORM SELECTED")
            
        
        try:
                find_hostgroup = zapi.hostgroup.get(filter={"name": nb_site_name}, output=['groupid'])
                if(len(find_hostgroup) != 0):
                    zapi.host.create(
                        
                        host = nb_device_name,
                        interfaces=[{
                            'type': 1,
                            'main': 1,
                            'useip': 0,
                            'ip': '',
                            'dns': 'netbox-zabbix-PrimaryIP',
                            'port': '9999'
                        }],
                        groups = [{"groupid": find_hostgroup[0]['groupid']}])
                    
                    print(f"ZABBIX | SUCCESS | Creado ({nb_device_name})")
                else:
                    print(f'HostGroup {nb_site_name} not found in ZABBIX')
                    
        except ZabbixAPIException as e:
            print("DEVICE NOT CREATED")
            
    return jsonify({"msg":"Correct"})

@app.route('/update', methods=['POST'])
def zabbix_host_update():
    if(request.data):
        
        # LEER NOMBRE DEL DEVICE NETBOX
        reading_post = request.get_json()
        #print(reading_post)
        # Mandatory
        nb_device_name = reading_post['data']['name']
        pre_site = reading_post['snapshots']['prechange']['site']
        post_site = reading_post['snapshots']['postchange']['site']
        
        # OPTIONAL
        pre_platform = reading_post['snapshots']['prechange']['platform']
        post_platform = reading_post['snapshots']['postchange']['platform']
        
        pre_primary_ipv4 = reading_post['snapshots']['prechange']['primary_ip4']
        post_primary_ipv4 = reading_post['snapshots']['postchange']['primary_ip4']
                
        # Update Platform 
        za.update_Template_Host(nb_device_name, pre_platform, post_platform)
        
        # Update Site
        za.update_HostGroup_Host(nb_device_name, pre_site, post_site)
        
        # Change name
        if(reading_post['snapshots']['prechange']['name'] != nb_device_name):
            before_nb_device_name = reading_post['snapshots']['prechange']['name']
            
            za.update_Hostname(before_nb_device_name, nb_device_name)
        
        # Manage IP Address
        za.update_ips_host(nb_device_name, pre_primary_ipv4, post_primary_ipv4)


        
    return jsonify({"msg":"Correct"})    
    
@app.route('/delete', methods=['DELETE'])
def zabbix_host_delete():

    ## LEER REQUEST DE NETBOX
    if(request.data):
        reading_post = request.get_json()
        nb_device_name = reading_post['data']['name']


        hosts_zabbix = zapi.host.get(output=["hostid", "name", "interfaces"], filter={"name": nb_device_name}, selectInterfaces=["interfaceid", "ip", "dns"], selectGroups=["groupid"])

        zab_host_id = hosts_zabbix[0]['hostid']     # IDENTIFY The host ID 
        
        print("")
        print(f"ZABBIX | Eliminando ... ({nb_device_name})")

        try:
            zapi.host.delete(hostid = zab_host_id)

            print(f"ZABBIX | SUCCESS | Eliminado ... ({nb_device_name}) ")

        except Exception as e:
            # ESTO SE EJECUTA SI YA EXISTE EN ZABBIX
            print("ZABBIX | ERROR | ", e.__class__, f"ocurrio para ({nb_device_name}).")
            print(f"ZABBIX | SUCCESS | ({nb_device_name}) Device no existia en Zabbix, Pero se creo Correctamente")

    return jsonify({"msg": "Correct"})


if __name__ == '__main__':
    app.run(debug=True,host='0.0.0.0',port=5000)
