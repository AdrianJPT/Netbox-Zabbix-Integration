# Netbox and Zabbix Integration using Flask API

[Netbox-Docker]:https://github.com/netbox-community/netbox-docker/tree/release
[Zabbix-Docker]:https://www.zabbix.com/documentation/current/en/manual/installation/containers#docker-compose

Integration between Netbox and Zabbix to unify everything in a single SoT (Source of Truth) using Flask as a middleware
### Funcionalities from Netbox to Zabbix:
- CREATE devices, ipaddress (IPv4 Primary IP), templates, sites, platform
- UPDATE devices, ipaddress(IPv4 Primary IP)
- DELETE devices, ipaddress(IPv4 Primary IP), templates, sites, platform

## Architecture:

![image](https://user-images.githubusercontent.com/86939628/224465296-abddcd6b-f1a6-4a51-90b1-c212dcd4d08f.png)

## Quickstart

To get _Netbox-Zabbix-Integracion_ up and running run the following commands.

* Set VALUES equals in Netbox and Zabbix: 
    - Platform(Netbox) - Templates(Zabbix)
    - Sites(Netbox) - HostGroups(Zabbix)

* Set the WeebHooks in Netbox pointing to the API roots:
    * CREATE (POST): https://[your_IP]:[PORT]/create
    * UPDATE (POST): https://[your_IP]:[PORT]/update
    * DELETE (DELETE): https://[your_IP]:[PORT]/delete

* Clone this repository
```bash
git clone https://github.com/AdrianJPT/Netbox-Zabbix-Integration.git

cd Netbox-Zabbix-Integration
```
* Set the corresponding variables in "credentials.py" file:
```python
Netbox_Url = '[your_netbox_url]'
Netbox_Token = '[your_netbox_token]'

Zabbix_Url = '[your_netbox_url]'
Zabbix_Token = '[your_zabbix_token]'

Zabbix_User = 'Admin'
Zabbix_Password = 'zabbix'
```

* Getting _Netbox-Zabbix-Integracion_, Build the DockerFile
```bash
docker compose build
```
* Getting _Netbox-Zabbix-Integracion_, Create the container
```bash
docker compose up
```

> If you want to edit the port of the docker container, override the _docker-compose.yml_ file.
```yml
version: '3.8'

services:
  api:
    build: .
    ports:
      - "5000:5000"
    volumes:
      - .:/app
    command: flask run --host=0.0.0.0 --port=5000 --debug


```

## Tested on:
  - netbox-docker = 2.6.1 (How to deploy [Netbox-Docker])
  - zabbix-docker = 6.4 (How to deploy [Zabbix-Docker])

## Execution VIDEOS:
- Creating a device:

https://user-images.githubusercontent.com/86939628/224466846-707d1ebb-51e6-4810-81c3-ddcaed001dfe.mp4

- Assigning and UPDATING an IP address:

https://user-images.githubusercontent.com/86939628/224466840-b284fa0c-51bf-47f0-af25-8f757269e3cb.mp4


https://user-images.githubusercontent.com/86939628/224466834-455d832e-1e59-420a-b45e-ec7331a84e0c.mp4


- Deleting a Device:

https://user-images.githubusercontent.com/86939628/224466829-aaf29897-ef5f-4767-bf44-2161f925c63a.mp4




 

