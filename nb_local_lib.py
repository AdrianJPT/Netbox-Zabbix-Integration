import pynetbox
from credentials import *
import requests
import ipaddress

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


# get the Site Name by SiteID
def get_Site_by_ID(SITE_ID):
    
    headers = {
        'Authorization': f'Token {Netbox_Token}',
        'Content-Type': 'application/json',
        }
    # ID del platform que deseas obtener
    site_id = SITE_ID  # Reemplaza con el ID del platform que buscas

    # Endpoint de la API para obtener un platform específico por su ID
    endpoint = f'api/dcim/sites/{site_id}/'

    # Realizar la solicitud GET para obtener el platform específico
    response = requests.get(f'{Netbox_Url}{endpoint}', headers=headers)
    
    # Verificar el código de estado de la respuesta
    if response.status_code == 200:
        # Obtener los datos de la respuesta en formato JSON
        platform_data = response.json()

        # Mostrar el nombre del platform
        
        return platform_data['name']
    else:
        print(f"WARNING | SITE - HOSTGROUP | NO SITE ID for SITE: '{site_id}' ")
#print(get_Site_by_ID(1))


##################
# IP ADDRESS
# ----------

def get_DeviceId_by_DeviceName(DEVICE_NAME):
    # Endpoint de la API para buscar dispositivos
    endpoint = f"{Netbox_Url}api/dcim/devices/?name={DEVICE_NAME}"

    # Agregar el token de autenticación a los headers de la solicitud
    headers = {'Authorization': f'Token {Netbox_Token}'}

    # Realizar la solicitud GET a la API de NetBox
    response = requests.get(endpoint, headers=headers)

    # Comprobar si la solicitud fue exitosa (código 200)
    if response.status_code == 200:
        data = response.json()
        # Verificar si se encontraron resultados
        if data['count'] == 1:
            device_id = data['results'][0]['id']
            print(f"ID del dispositivo '{DEVICE_NAME}': {device_id}")
            return(device_id)
        elif data['count'] > 1:
            print(f"Se encontraron múltiples dispositivos con el nombre '{DEVICE_NAME}'. Especifique un nombre más único.")
        else:
            print(f"No se encontró ningún dispositivo con el nombre '{DEVICE_NAME}'.")
    else:
        print(f"Error: No se pudo obtener el ID del dispositivo. Código de error: {response.status_code}")
#get_DeviceId_by_DeviceName("adrian")
#1


def get_ips_by_devices(DEVICE_NAME):
    device_id = get_DeviceId_by_DeviceName(DEVICE_NAME)
    # Endpoint de la API para obtener las direcciones IP de un dispositivo
    endpoint = f"{Netbox_Url}api/ipam/ip-addresses/?device_id={device_id}"

    # Agregar el token de autenticación a los headers de la solicitud
    headers = {'Authorization': f'Token {Netbox_Token}'}

    # Realizar la solicitud GET a la API de NetBox
    response = requests.get(endpoint, headers=headers)

    # Comprobar si la solicitud fue exitosa (código 200)
    if response.status_code == 200:
        data = response.json()
        # Imprimir las direcciones IP asociadas al dispositivo
        
        IpAddress_Ids = []
        for ip_address in data['results']:
            ip_cidr = ip_address['address']
            convert_to_ip = ipaddress.IPv4Interface(ip_cidr)
            nb_primary_ip = str(convert_to_ip.ip)
            IpAddress_Ids.append(nb_primary_ip)
            
        return IpAddress_Ids
    else:
        print(f"Error: No se pudo obtener las direcciones IP. Código de error: {response.status_code}")
#get_ip_by_devices("adrian")


def get_IPadress_By_IPaddress_ID(IP_ADDRESS_ID):
    
    # HTTP headers for the API request (including the authentication token)
    headers = {
        'Authorization': f'Token {Netbox_Token}',
        'Content-Type': 'application/json',
    }
    # URL for the specific IP address API endpoint
    url = f'{Netbox_Url}api/ipam/ip-addresses/{IP_ADDRESS_ID}/'

    # Make the API request
    response = requests.get(url, headers=headers)

    # Check if the request was successful (status code 200)
    if response.status_code == 200:
        # Parse the JSON response
        ip_address_data = response.json()

        # Extract the IP address string from the response data
        ip_address = ip_address_data['address']
        convert_to_ip = ipaddress.IPv4Interface(ip_address)
        nb_primary_ip = str(convert_to_ip.ip)
        
        
        print(f"The IP address string for IP ID {IP_ADDRESS_ID} is: {nb_primary_ip}")    
        return nb_primary_ip

        
    else:
        print(f"Failed to retrieve IP address. Status Code: {response.status_code}")

#get_IPadress_By_IPaddress_ID("9")
