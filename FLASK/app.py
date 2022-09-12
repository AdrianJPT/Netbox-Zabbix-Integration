from flask import Flask, jsonify, request,redirect
import json
import requests

# RESOLVE IP ADDRESS
import ipaddress 



# ZABBIX
from pyzabbix import ZabbixAPI, ZabbixAPIException



app = Flask(__name__)



ZABBIX_SERVER = "http://15.228.201.253/zabbix/api_jsonrpc.php"

zapi = ZabbixAPI(ZABBIX_SERVER)
zapi.login("Admin", "zabbix")

zabbix_url = 'http://15.228.201.253/zabbix/api_jsonrpc.php'
zabbix_token = '64acb78476350f099de1ea79800e2901ffb5a267f7aef5ac3e7c4d1af167e42a'


@app.route('/create', methods=['POST'])
def zabbix_host_create():

    ## LEER REQUEST DE NETBOX
    if(request.data):
        reading_post = request.get_json()
        nb_device_name = reading_post['data']['name']
        print("")
        print(f"ZABBIX | Creando ... ({nb_device_name})")

        try:
            zapi.host.create(host = nb_device_name,
                groups = [
                            {
                                "groupid": "1"
                            }
                        ]
                        )

            print(f"ZABBIX | SUCCESS | Creado ({nb_device_name})")

        except Exception as e:
            # ESTO SE EJECUTA SI YA EXISTE EN ZABBIX
            print("ERROR", e.__class__, "ocurrio.")
            print(F"ZABBIX | WARNING | ({nb_device_name}) ya existe en Zabbix Server")

    return jsonify({"msg":"Creado"})

@app.route('/update', methods=['POST'])
def zabbix_host_update():
    if(request.data):
        # LEER NOMBRE DEL DEVICE NETBOX
        reading_post = request.get_json()
        #print(reading_post) # TURN ON PARA DEBUGEAR NETBOX REQUEST !!
        nb_device_name = reading_post['data']['name']
        #print (nb_device_name)
        
        
        # TRAER LA INFO DE ZABBIX
        hosts_zabbix = zapi.host.get(output=["hostid", "name", "interfaces"], filter={"name": nb_device_name}, selectInterfaces=["interfaceid", "ip", "dns"], selectGroups=["groupid"])

        # SI YA EXISTIA EL NB DEVICE Y EN ZABBIX NO, SE CREARA UNO
        if len(hosts_zabbix) != 0:
            # SI EXISTE EN ZABBIX

            zab_host_id = hosts_zabbix[0]['hostid']     
            
            #print (hosts_zabbix)  # ACTIVAR PARA DEBUGEAR ZABBIX REQUEST
            #print("")
            print(f"ZABBIX | SUCCESS | Se encontro ({nb_device_name}) device en Zabbix Sever")



            # IDENTIFICAR SI EXISTE ALGUNA INTERFAZ
            zab_host_int = hosts_zabbix[0]['interfaces']

            if len(zab_host_int) != 0:
                print(f'Existe Interfaz')

                # OBTNER EL ID DE LA INTERFAZ
                zab_host_int_id = hosts_zabbix[0]['interfaces'][0]['interfaceid']

                # VALIDAR SI SE ESTA QUITANDO O ACTUALIZANDO LA IP
                if reading_post['data']['primary_ip'] != None:
                    print("Actulizando la interfaz")

                    request_nb_primary_ip = reading_post['data']['primary_ip']['address']
                    convert_to_ip = ipaddress.IPv4Interface(request_nb_primary_ip)
                    nb_primary_ip = str(convert_to_ip.ip)

                    zapi.hostinterface.update(interfaceid=zab_host_int_id, ip=nb_primary_ip)

                    print(f"ZABBIX | SUCCESS | Se actulizo la ip: ({nb_primary_ip}) en la interfaz id: ({zab_host_int_id})")

                else:
                    # BORRANDO INTERFAZ ZAbbix HOST
                    
                    requests.put(zabbix_url, json={
                            "jsonrpc": "2.0",
                            "method": "hostinterface.delete",
                            "params": [
                                zab_host_int_id
                            ],
                            "auth": zabbix_token,
                            "id": 1})
                    print(f"ZABBIX | SUCCESS | Se Elimino la interfaz con ip del host({nb_device_name})")
            else:           
            # NO TIENE INTERFAZ CREAR UNA Y ASOCIAR UNA IP
                try:
                    # QUITAR LA MASK del CIDR, VALIDAR LA IP EN EL REQUEST Y ACTUALIZAR
                    request_nb_primary_ip = reading_post['data']['primary_ip']['address']
                    convert_to_ip = ipaddress.IPv4Interface(request_nb_primary_ip)
                    nb_primary_ip = str(convert_to_ip.ip)
                    
                    # AGREGAR LA INTERFAZ Y IP AL ZABBIX HOST
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
                    print(f"ZABBIX | SUCCESS | Se actualizo la ip ({nb_primary_ip}) en Zabbix Server")

                except Exception as e:
                    print("ZABBIX | WARNING | ", e.__class__, f"ocurrio para ({nb_device_name}).")
                    print(f"ZABBIX | SUCCESS | La IP es: ({nb_primary_ip}) ")
                    print(f"ZABBIX | WARNING | No se identifico IP en Netbox")

        #CUANDO NO EXITE EN ZABBIX PERO EL NETBOX SÍ
        else:
            print("CREANDO CON IP O SIN UNA")
            # VER SI CREAR CON IP O SIN IP
            if reading_post['data']['primary_ip'] != None: 
                # CREAR CON IP
                print("Creando con ip")
                request_nb_primary_ip = reading_post['data']['primary_ip']['address']
                convert_to_ip = ipaddress.IPv4Interface(request_nb_primary_ip)
                nb_primary_ip = str(convert_to_ip.ip)            

                zapi.host.create(host = nb_device_name,
                    groups = [
                                {
                                    "groupid": "1"
                                }
                            ],
                    interfaces = [{
                                                
                        "type": 1,
                        "main": 1,
                        "useip": 1,
                        "ip": nb_primary_ip,
                        "dns": "",
                        "port": "10050"
                    }])

                print(f"ZABBIX | SUCCESS | Se creo el host ({nb_device_name}) con la ip ({nb_primary_ip}) , porque no exitia en Zabbix Server")

            else:
                # CREAR SOLAMENTE EL HOST SIN IP
                print("CREAND SIN IP")
                zapi.host.create(host = nb_device_name,
                    groups = [
                                {
                                    "groupid": "1"
                                }
                            ]
                            )
                
                print(f"ZABBIX | SUCCESS | Se creo el host ({nb_device_name}) , porque no exitia en Zabbix Server")


    return jsonify({"msg":"pong"})    
    
@app.route('/delete', methods=['DELETE'])
def zabbix_host_delete():

    ## LEER REQUEST DE NETBOX
    if(request.data):
        reading_post = request.get_json()
        nb_device_name = reading_post['data']['name']


        hosts_zabbix = zapi.host.get(output=["hostid", "name", "interfaces"], filter={"name": nb_device_name}, selectInterfaces=["interfaceid", "ip", "dns"], selectGroups=["groupid"])

        zab_host_id = hosts_zabbix[0]['hostid']     # IDENTIFICAR AL HOST POR EL ID Y EL NETBOX NAME
        
        print("")
        print(f"ZABBIX | Eliminando ... ({nb_device_name})")

        try:
            zapi.host.delete(hostid = zab_host_id)

            print(f"ZABBIX | SUCCESS | Eliminado ... ({nb_device_name}) ")

        except Exception as e:
            # ESTO SE EJECUTA SI YA EXISTE EN ZABBIX
            print("ZABBIX | ERROR | ", e.__class__, f"ocurrio para ({nb_device_name}).")
            print(f"ZABBIX | SUCCESS | ({nb_device_name}) Device no existia en Zabbix, Pero se creo Correctamente")

    return jsonify({"msg": "Correcto"})


if __name__ == '__main__':
    app.run(debug=True,host='192.168.1.204',port=4000)
