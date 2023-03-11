# Netbox | Zabbix | Python | API | Ansible 

Integration between Netbox and Zabbix to unify synchronously a everything in a single SoT (Source of Truth)

Requirements:
Hosts:
  - Install WinRM to access from the CM (Control Machine)

CM:
  - Install Kerberos/sshkeys to access to the hosts of your ansible inventory.
  - Install Ansible.
  
  - Adapat the variables of the file roles/main.yml
  
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



  
  



Funtionality supported NETBOX - ZABBIX:
- Devices (Disk, VM)
- IPAM
