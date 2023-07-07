package Api_Netbox_Zabbix_Integration.Pages.Zabbix;

import Api_Netbox_Zabbix_Integration.Manage.Credentials;
import Api_Netbox_Zabbix_Integration.Manage.ManageDriver;
import Api_Netbox_Zabbix_Integration.POM.Zabbix.ZabbixHostsPOM;
import net.thucydides.core.annotations.Step;
import org.junit.Assert;


public class ZabbixHosts {

    ManageDriver manageDriver = new ManageDriver();
    ZabbixHostsPOM zabbixHostsPOM;
    Credentials credentials = new Credentials();

    @Step
    public void cleanFilters(String netboxDeviceName) {
        zabbixHostsPOM.resetButton.click();
        zabbixHostsPOM.nameField.sendKeys(netboxDeviceName);
        zabbixHostsPOM.applyButton.click();
    }
    @Step
    public void findZabbixDeviceEquals(String netboxDeviceName) {


        zabbixHostsPOM.zabbixHost.click();
        String expectedValue = netboxDeviceName;
        String actualValue = zabbixHostsPOM.zabbixHostName.getAttribute("value");

        Assert.assertEquals("Name don't match", expectedValue, actualValue);

    }
    @Step
    public void findZabbixDeviceNotEquals(String netboxDeviceName) {


        zabbixHostsPOM.zabbixHost.click();
        String expectedValue = netboxDeviceName;
        String actualValue = zabbixHostsPOM.zabbixHost.getText();

        Assert.assertNotEquals("Name don't match", expectedValue, actualValue);

    }
    @Step
    public  void matchSiteWithHostGroup(String Site){
        Assert.assertTrue(zabbixHostsPOM.zabbixHostgroup.isDisplayed());

        String expectedValue = Site;
        String actualValue = zabbixHostsPOM.zabbixHostgroup.getAttribute("title");

        Assert.assertEquals("Site don't match", expectedValue, actualValue);

    }

    @Step
    public  void matchPlatformWithTemplate(String Platform){

        Assert.assertTrue(zabbixHostsPOM.zabbixTemplate.isDisplayed());

        String expectedValue = Platform;
        String actualValue = zabbixHostsPOM.zabbixTemplate.getText();

        Assert.assertEquals("Platform don't match", expectedValue, actualValue);

        zabbixHostsPOM.closeHostWindow.click();
    }


    @Step
    public void validateInterface(String s) {

        String expectedValue = s;
        String actualValue = zabbixHostsPOM.interfaceZabbixHost.getText();

        Assert.assertEquals("Interface don't match", expectedValue, actualValue);

    }
    public void deleteAllHosts(){


        String urlHost =  credentials.ZabbixURL +"zabbix.php?action=host.list";
        zabbixHostsPOM.openUrl(urlHost);
        zabbixHostsPOM.resetButton.click();
        zabbixHostsPOM.selectAllHosts.click();
        zabbixHostsPOM.deleteButton.click();
    }


}