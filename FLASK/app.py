from flask import Flask, jsonify, request,redirect
import json
import requests


import ipaddress

from pyzabbix import ZabbixAPI, ZabbixAPIException



app = Flask(__name__)



ZABBIX_SERVER = "http://54.207.189.123/zabbix/api_jsonrpc.php"

zapi = ZabbixAPI(ZABBIX_SERVER)
zapi.login("Admin", "zabbix")


zabbix_url = 'http://54.207.189.123/zabbix/api_jsonrpc.php'
zabbix_token = '64acb78476350f099de1ea79800e2901ffb5a267f7aef5ac3e7c4d1af167e42a'



@app.route('/create', methods=['POST'])
def zabbix_host_create():

    ## LEER REQUEST DE NETBOX
    if(request.data):
        reading_post = request.get_json()
        nb_device_name = reading_post['data']['name']
        print (nb_device_name)


        print("ESTA CREANDO")

        try:
            zapi.host.create(host = nb_device_name,
                groups = [
                            {
                                "groupid": "1"
                            }
                        ]
                        )

            print("Se creo el Device")

        except Exception as e:
            # ESTO SE EJECUTA SI YA EXISTE EN ZABBIX
            print("ERROR", e.__class__, "ocurrio.")
            print("WARNING Netbox Device ya existe en Zabbix")

    return jsonify({"msg":"pong"})

@app.route('/update', methods=['POST'])
def zabbix_host_update():
    if(request.data):
        # LEER NOMBRE DEL DEVICE NETBOX
        reading_post = request.get_json()
        nb_device_name = reading_post['data']['name']
        print (nb_device_name)
        
        hosts_zabbix = zapi.host.get(output=["hostid", "name", "interfaces"], filter={"name": nb_device_name}, selectInterfaces=["interfaceid", "ip", "dns"], selectGroups=["groupid"])

        zab_host_id = hosts_zabbix[0]['hostid']     # IDENTIFICAR AL HOST POR EL ID Y EL NETBOX NAME
        
        print (hosts_zabbix)
        print("CORRECTO se encontro el equipo en zabbix")

        try:
            # QUITAR LA MASK del CIDR, VALIDAR LA IP EN EL REQUEST Y ACTUALIZAR
            request_nb_primary_ip = reading_post['data']['primary_ip']['address']
            convert_to_ip = ipaddress.IPv4Interface(request_nb_primary_ip)
            nb_primary_ip = str(convert_to_ip.ip)
            

            print(nb_primary_ip)
            zapi.host.update(hostid = zab_host_id, name = nb_device_name, interfaces = [{
                                             
                                             "type": 1,
                                             "main": 1,
                                             "useip": 1,
                                             "ip": nb_primary_ip,
                                             "dns": "",
                                             "port": "10050"
                                         }]
                                         ,
                                         groups = [
                                             {
                                                 "groupid": "1"
                                             }
                                         ]
                                         )
            print("CORRECTO IP primaria actualizada")

        except:
            print("WARNING no se encontro IP en Netbox")
            print(nb_primary_ip)

        
        

    return jsonify({"msg":"pong"})    
             



        



if __name__ == '__main__':
    app.run(debug=True,host='192.168.1.204',port=4000)