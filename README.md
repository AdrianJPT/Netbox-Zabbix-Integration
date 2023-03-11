# Netbox | Zabbix | Python | API | Ansible 

Integration between Netbox and Zabbix to unify synchronously a everything in a single SoT (Source of Truth)

Requirements:
Hosts:
  - Install WinRM to access from the CM (Control Machine)

CM:
  - Install Kerberos to access to the hosts of your ansible inventory
  - Install Ansible
  
  Edit the variables in roles/main.yml file and set 

Flask middleware libreries:
  - pynetbox
  - pyzabbix
  - flask
  - ipaddress
  - requests
  - json
  ![image](https://user-images.githubusercontent.com/86939628/224455574-566ad056-e948-4f20-b0f3-e118afefbcc9.png)

  
  



Funtionality supported NETBOX - ZABBIX:
- Devices (Disk, VM)
- IPAM
