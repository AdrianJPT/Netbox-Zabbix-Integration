import pynetbox
from credentials import *
import requests

NETBOX = Netbox_Url
nb  = pynetbox.api(
    url=NETBOX,
    token= Netbox_Token
)


# DEVICE/Tags IN NETBOX
def nb_DeviceTag_update_add(nb_device_id, nb_tag_name,weebhook_id):


    try:
        nb.extras.webhooks.update([
        {
            'id': weebhook_id,
            "enabled": False
            }
            ])

        result = nb.dcim.devices.update(
        [
            {
            'id': nb_device_id,
            'tags': [
            {
            "name": nb_tag_name
            }]}])

        nb.extras.webhooks.update([
        {
            'id': weebhook_id,
            "enabled": True
            }
            ])

    except pynetbox.lib.query.RequestError as e:
        print(e.error)    

def nb_DeviceTag_update_remove(nb_device_id, weebhook_id):
    try:
        nb.extras.webhooks.update([
        {
            'id': weebhook_id,
            "enabled": False
            }
            ])

        result = nb.dcim.devices.update(
        [
            {
            'id': nb_device_id,
            'tags': []
            }
            ])

        nb.extras.webhooks.update([
        {
            'id': weebhook_id,
            "enabled": True
            }
            ])


    except pynetbox.lib.query.RequestError as e:
        print(e.error)



#######################################################3

def get_Platform_by_ID(PLATFORM_ID):
    headers = {
        'Authorization': f'Token {Netbox_Token}',
        'Content-Type': 'application/json',
        }
    # ID del platform que deseas obtener
    platform_id = PLATFORM_ID  # Reemplaza con el ID del platform que buscas

    # Endpoint de la API para obtener un platform específico por su ID
    endpoint = f'api/dcim/platforms/{platform_id}/'

    # Realizar la solicitud GET para obtener el platform específico
    response = requests.get(f'{Netbox_Url}{endpoint}', headers=headers)

    # Verificar el código de estado de la respuesta
    if response.status_code == 200:
        # Obtener los datos de la respuesta en formato JSON
        platform_data = response.json()

        # Mostrar el nombre del platform
        
        return platform_data['name']
    else:
        print(f"WARNING | PLATFORM - TEMPLATE | NO PLATFORM ID: '{platform_id}' ")
