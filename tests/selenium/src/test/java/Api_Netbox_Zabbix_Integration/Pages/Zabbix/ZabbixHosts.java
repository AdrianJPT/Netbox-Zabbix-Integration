package Api_Netbox_Zabbix_Integration.Pages.Zabbix;

import Api_Netbox_Zabbix_Integration.Manage.Credentials;
import Api_Netbox_Zabbix_Integration.Manage.ManageDriver;
import Api_Netbox_Zabbix_Integration.POM.Zabbix.ZabbixHostsPOM;
import net.thucydides.core.annotations.Step;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;


public class ZabbixHosts {

    ManageDriver manageDriver = new ManageDriver();
    ZabbixHostsPOM zabbixHostsPOM;
    Credentials credentials = new Credentials();


    public void openURLHosts(){
        String url =  credentials.ZabbixURL +"/zabbix.php?action=host.list";
        zabbixHostsPOM.openUrl(url);
    }


    @Step
    public void filterByName(String netboxDeviceName) {
        zabbixHostsPOM.resetButton.click();
        zabbixHostsPOM.nameField.sendKeys(netboxDeviceName);
        zabbixHostsPOM.applyButton.click();
    }
    @Step
    public void findZabbixDeviceEquals(String netboxDeviceName) {


        zabbixHostsPOM.zabbixHostFirst.click();
        String expectedValue = netboxDeviceName;
        String actualValue = zabbixHostsPOM.zabbixHostName.getAttribute("value");

        Assert.assertEquals("Name don't match", expectedValue, actualValue);

    }
    @Step
    public void findZabbixDeviceNotEquals(String netboxDeviceName) {


        zabbixHostsPOM.zabbixHostFirst.click();
        String expectedValue = netboxDeviceName;
        String actualValue = zabbixHostsPOM.zabbixHostFirst.getText();

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

        // Explicit Wait
        manageDriver.ExplicitWait(zabbixHostsPOM.getDriver(),3,zabbixHostsPOM.interfaceZabbixHost);

        // Assertion
        String expectedValue = s;
        String actualValue = zabbixHostsPOM.interfaceZabbixHost.getText();

        Assert.assertEquals("Interface don't match", expectedValue, actualValue);

    }
    public void deleteAllHosts(){

        zabbixHostsPOM.resetButton.click();
        zabbixHostsPOM.selectAllHosts.click();
        zabbixHostsPOM.deleteButton.click();
    }

    @Step
    public void validateHostExist(String DeviceName) {
        // Set up the filter to find the host
        zabbixHostsPOM.resetButton.click();
        zabbixHostsPOM.nameField.sendKeys(DeviceName);
        zabbixHostsPOM.applyButton.click();

        // Assertions
        //manageDriver.ExplicitWait(zabbixHostsPOM.getDriver(), 5, zabbixHostsPOM.zabbixHostFirst);

        MatcherAssert.assertThat("The host was not deleted",zabbixHostsPOM.zabbixHostFirst,Matchers.notNullValue());


    }

    @Step
    public void validateAllHostsExists(int iteration) {
        String clone = "Delete_Clone_";
        for (int i = 1; i <= iteration; i++ )
        {
            zabbixHostsPOM.resetButton.click();

            String result = clone + i;

            zabbixHostsPOM.nameField.sendKeys(result);

            zabbixHostsPOM.applyButton.click();

            //manageDriver.ExplicitWait(zabbixHostsPOM.getDriver(), 5, zabbixHostsPOM.zabbixHostFirst);
            MatcherAssert.assertThat("The host was not deleted",zabbixHostsPOM.zabbixHostFirst,Matchers.notNullValue());

        }
    }




}