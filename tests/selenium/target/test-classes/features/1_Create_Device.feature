Feature: Create NETBOX to ZABBIX

  Scenario Outline: Create Device with all the features
    Given I am creating a device in Netbox

    When The field Name is <DeviceName>
    And The field Device Role: <DeviceRole> is selected
    And The field Device Type: <DeviceType> is selected
    And The field Site: <ZabbixHostGroup> is selected
    And The field Platform: <ZabbixTemplate> is selected
    And The button Create Device is clicked

    Given I log in successfully in Zabbix
    Then The Zabbix host: <DeviceName> interface is UPDATE_IP with port
    And The Netbox device: <DeviceName> is displayed in Zabbix hosts
    And The Zabbix host is related to the HostGroup: <ZabbixHostGroup>
    And The Zabbix host is related to the template: <ZabbixTemplate>


    Examples:
      | DeviceName     | DeviceRole     | DeviceType    | ZabbixTemplate      | ZabbixHostGroup |
      | "E01_Test-01"  | "device_role"  | "Device Type" | "Template_Adrian"   | "SITIO-01"      |
      | "E01_Test-02"  | "device_role"  | "Device Type" | "Template_Adrian"   | "SITIO-01"      |


  Scenario Outline: Create Device with NO platform
    Given I am creating a device in Netbox

    When The field Name is <DeviceName>
    And The field Device Role: <DeviceRole> is selected
    And The field Device Type: <DeviceType> is selected
    And The field Site: <ZabbixHostGroup> is selected
    And The button Create Device is clicked

    Given I log in successfully in Zabbix
    Then The Zabbix host: <DeviceName> interface is UPDATE_IP with port
    And The Netbox device: <DeviceName> is displayed in Zabbix hosts
    And The Zabbix host is related to the HostGroup: <ZabbixHostGroup>


    Examples:
      | DeviceName     | DeviceRole     | DeviceType    |  ZabbixHostGroup |
      | "E02_Test-01"  | "device_role"  | "Device Type" |  "SITIO-01"      |
      | "E02_Test-02"  | "device_role"  | "Device Type" |  "SITIO-01"      |

  Scenario Outline: Create Device with a SITE that not exist in Zabbix as HostGroup
    Given I am creating a device in Netbox

    When The field Name is <DeviceName>
    And The field Device Role: <DeviceRole> is selected
    And The field Device Type: <DeviceType> is selected
    And The field Site: <ZabbixHostGroup> is selected
    And The field Platform: <ZabbixTemplate> is selected
    And The button Create Device is clicked

    Given I log in successfully in Zabbix
    Then The Zabbix host: <DeviceName> does not appear in Zabbix


    Examples:
      | DeviceName     | DeviceRole     | DeviceType    | ZabbixTemplate      | ZabbixHostGroup |
      | "E03_Test-01"  | "device_role"  | "Device Type" | "Template_Adrian"   | "SITIO-02"      |
      | "E03_Test-02"  | "device_role"  | "Device Type" | "Template_Adrian"   | "SITIO-02"      |

