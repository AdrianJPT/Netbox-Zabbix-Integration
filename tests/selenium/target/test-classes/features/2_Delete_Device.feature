Feature: Delete devices from NETBOX to ZABBIX



  Scenario: Cleaning up Netbox Devices
    Given There are any device in Devices in Netbox


  Scenario Outline: Delete one Netbox Device
    Given I have <DeviceName> devices in Netbox and Zabbix
    When I delete the device <DeviceName>
    Then <DeviceName> should not exist in Zabbix

    Examples:
      | DeviceName      |
      | "E01_DELETE-01" |
      | "E01_DELETE-02" |

  Scenario: Delete many Netobx Devices
    Given I have 5 Devices in Netbox and Zabbix
    When I delete all the devices
    Then I should not see the 5 devices in Zabbix







