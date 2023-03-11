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
![Vídeo sin título ‐ Hecho con Clipchamp](https://user-images.githubusercontent.com/86939628/224466508-8b9dc0db-fcce-4123-a3e0-4c82206389f7.gif)

- Assigning and UPDATING an IP address:
![Vídeo sin título ‐ Hecho con Clipchamp (1)](https://user-images.githubusercontent.com/86939628/224466633-19af34aa-77de-433d-a5dd-ac6bdd26cafe.gif)

![Vídeo sin título ‐ Hecho con Clipchamp (2)](https://user-images.githubusercontent.com/86939628/224466696-fc9b8ddb-0eec-4bda-9581-4634375b4e55.gif)

- Deleting a Device:
![Vídeo sin título ‐ Hecho con Clipchamp (3)](https://user-images.githubusercontent.com/86939628/224466770-420d1b96-a948-4a10-b86a-a1693555c4fa.gif)


 

