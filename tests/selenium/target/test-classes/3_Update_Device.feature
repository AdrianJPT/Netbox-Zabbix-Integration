Feature: Update NETBOX devices to ZABBIX

Scenario: Update Device Type
  Given I have device "Update"
  When  I update the Device Type to "SITIO-02"
  Then  Nothing changes in the Zabbix Host

Scenario: Update IP
  Given I have device "Update"
  When  I associate the new IP address with the device "Update"
  Then  Changes the IP in Zabbix hosts

Scenario: Update IP that already exists in the Device
  Given I have device "Update" with an IP address
  When  I associate the new IP address with the device "Update"
  Then  Changes the IP in Zabbix hosts

Scenario: Update Site
  Given

Scenario: Update Platform


  Scenario Outline: Create Device with NO platform
    Given I log in successfully in Netbox
    And I am creating a device in Netbox

    When The field Name is <DeviceName>
    And The field Device Role: <DeviceRole> is selected
    And The field Device Type: <DeviceType> is selected
    And The field Site: <ZabbixHostGroup> is selected
    And The button Create Device is clicked

    Given I log in successfully in Zabbix
    Then The Netbox device: <DeviceName> is displayed in Zabbix hosts
    And The Zabbix host is related to the HostGroup: <ZabbixHostGroup>
    And The DNS name is UPDATE_IP with port 9999

    Examples:
      | DeviceName       | DeviceRole     | DeviceType        | ZabbixHostGroup   |
      | "E02_Test-01"    | "device_role"  | "Device Type"     | "SITIO-01"        |
      | "E02_Test-02"    | "device_role"  | "Device Type"     | "SITIO-01"        |



  Scenario Outline: Create Device with a SITE that not exist in Zabbix as HostGroup
    Given I log in successfully in Netbox
    And I am creating a device in Netbox

    When The field Name is <DeviceName>
    And The field Device Role: <DeviceRole> is selected
    And The field Device Type: <DeviceType> is selected
    And The field Site: <ZabbixHostGroup> is selected
    And The field Platform: <ZabbixTemplate> is selected
    And The button Create Device is clicked

    Given I log in successfully in Zabbix
    Then The Netbox device: <DeviceName> is displayed in Zabbix hosts
    And The Zabbix host is related to the HostGroup: <ZabbixHostGroup>
    And The Zabbix host is related to the template: <ZabbixTemplate>
    And The DNS name is UPDATE_IP with port 9999

    Examples:
      | DeviceName     | DeviceRole     | DeviceType    | ZabbixTemplate     | ZabbixHostGroup  |
      | "E03_Test-01"  | "device_role"  | "Device Type" | "Template_Adrian"  | "SITIO-01"       |
      | "E03_Test-02"  | "device_role"  | "Device Type" | "Template_Adrian"  | "SITIO-01"       |

