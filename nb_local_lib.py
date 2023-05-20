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



