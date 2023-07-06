package Netbox.Pages;

import Netbox.POM.ZabbixMainPOM;
import net.thucydides.core.annotations.Step;

public class ZabbixMain {

    ZabbixMainPOM zabbixMainPOM;

    @Step
    public void clickOnBarHost() {
        zabbixMainPOM.barDataColletion.click();
        zabbixMainPOM.barHosts.click();
    }

}
