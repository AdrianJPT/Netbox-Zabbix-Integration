# Netbox | Zabbix | Python | API | Ansible 

Integration between Netbox and Zabbix to unify synchronously a everything in a single SoT (Source of Truth)

## Architecture:
![image](https://user-images.githubusercontent.com/86939628/224458899-5c0c49e8-d94a-4a73-bb90-963f0122432a.png)

## Architecture:


## Requirements:
Hosts:
  - Install WinRM to access from the CM (Control Machine)

CM:
  - Install Kerberos/sshkeys to access to the hosts of your ansible inventory.
  - Install Ansible.
  
  - Adapat the variables of the file roles/main.yml (Create the temporary objects o )
  
  ![image](https://user-images.githubusercontent.com/86939628/224456020-4c4954c4-1fde-479e-85e7-90cabafb49cd.png)
 
  - Set the inventory in roles/hosts.
  - Then run the ansible playbook and populate your server information into NETBOX.
     ansible-playbook main.yml -i hosts
  

Flask middleware libreries:
  - pynetbox
  - pyzabbix
  - flask
  - ipaddress
  - requests
  - json
  
  Set the variables to access the endpoints
  
  ![image](https://user-images.githubusercontent.com/86939628/224456921-af46541e-9f0d-4669-be24-429d6fd4c02a.png)

Netbox:


  

Funtionality supported NETBOX - ZABBIX:
- Devices (Disk, VM)
- IPAM
