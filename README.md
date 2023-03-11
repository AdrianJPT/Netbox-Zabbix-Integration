# Netbox and Zabbix Integration using Flask API

Integration between Netbox and Zabbix to unify everything in a single SoT (Source of Truth) using Flask as a middleware

## Architecture:

![image](https://user-images.githubusercontent.com/86939628/224465296-abddcd6b-f1a6-4a51-90b1-c212dcd4d08f.png)

## Requirements:
  - pynetbox = 7.0.1
  - pyzabbix = 1.2.1
  - Flask = 2.2.3
  - requests = 2.22.0

## Tested on:
  - netbox = 3.3.2
  - zabbix = 6.0.7

## Funcionalities from Netbox to Zabbix:
- CREATE devices, ipaddress, templates, sites, platform
- UPDATE devices, ipaddress, templates, sites, platform
- DELETE devices, ipaddress, templates, sites, platform

## Setup
 - Set the corresponding variables in "credentials.py" file:
![image](https://user-images.githubusercontent.com/86939628/224465775-7a07d1ca-989e-4aef-89d0-fd627f933413.png)
 - Then You run the Flask middleware file "app.py" (Change for your IP)
![image](https://user-images.githubusercontent.com/86939628/224466267-7ce09abc-5a4c-49e0-8ffd-a684a94826a5.png)

## Execute:
- Creating a device:

https://user-images.githubusercontent.com/86939628/224466846-707d1ebb-51e6-4810-81c3-ddcaed001dfe.mp4


- Assigning and UPDATING an IP address:

https://user-images.githubusercontent.com/86939628/224466840-b284fa0c-51bf-47f0-af25-8f757269e3cb.mp4


https://user-images.githubusercontent.com/86939628/224466834-455d832e-1e59-420a-b45e-ec7331a84e0c.mp4


- Deleting a Device:
https://user-images.githubusercontent.com/86939628/224466829-aaf29897-ef5f-4767-bf44-2161f925c63a.mp4




 

