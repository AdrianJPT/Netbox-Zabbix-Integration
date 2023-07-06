package Netbox.Pages;

import Netbox.POM.ZabbixHostsPOM;
import net.thucydides.core.annotations.Step;

public class ZabbixHosts {

    ZabbixHostsPOM zabbixHostsPOM;

    @Step
    public void cleanFilters() {
        zabbixHostsPOM.resetButton.click();
    }
    @Step
    public void findZabbixDevice(String netboxDeviceName) {
        zabbixHostsPOM.nameField.sendKeys(netboxDeviceName);
        zabbixHostsPOM.applyButton.click();
    }
}
