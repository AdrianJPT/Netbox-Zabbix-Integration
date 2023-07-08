Feature: Create NETBOX to ZABBIX

  Scenario Outline: Create Device with NO platform
    Given I am creating a device in Netbox

    When The field Name is <DeviceName>
    And The field Device Role: <DeviceRole> is selected
    And The field Device Type: <DeviceType> is selected
    And The field Site: <ZabbixHostGroup> is selected
    And The button Create Device is clicked

    Examples:
      | DeviceName     | DeviceRole     | DeviceType    |  ZabbixHostGroup |
      | "E01_DELETE-01"  | "device_role"  | "Device Type" |  "SITIO-01"      |
      | "E01_DELETE-02"  | "device_role"  | "Device Type" |  "SITIO-01"      |
      | "E01_DELETE-03"  | "device_role"  | "Device Type" |  "SITIO-01"      |
      | "E01_DELETE-04"  | "device_role"  | "Device Type" |  "SITIO-01"      |
      | "E01_DELETE-05"  | "device_role"  | "Device Type" |  "SITIO-01"      |
      | "E01_DELETE-06"  | "device_role"  | "Device Type" |  "SITIO-01"      |

  Scenario: Delete all the devices created
    Given I'm in the netbox Device Page
    When I delete all the devices
    Then The Zabbix hosts are empty


  Scenario Outline: Create Device with all the features
    Given I am creating a device in Netbox

    When The field Name is <DeviceName>
    And The field Device Role: <DeviceRole> is selected
    And The field Device Type: <DeviceType> is selected
    And The field Site: <ZabbixHostGroup> is selected
    And The field Platform: <ZabbixTemplate> is selected
    And The button Create Device is clicked
    And The <DeviceName> is deleted

    Given I log in successfully in Zabbix
    Then The Zabbix host: <DeviceName> does not appear in Zabbix


    Examples:
      | DeviceName     | DeviceRole     | DeviceType    | ZabbixTemplate      | ZabbixHostGroup |
      | "E02_DELETE-01"  | "device_role"  | "Device Type" | "Template_Adrian"   | "SITIO-01"      |




