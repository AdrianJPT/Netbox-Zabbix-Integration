package Api_Netbox_Zabbix_Integration.stepdefinitions;

import Api_Netbox_Zabbix_Integration.Manage.Credentials;
import Api_Netbox_Zabbix_Integration.Manage.ManageDriver;
import Api_Netbox_Zabbix_Integration.Pages.Netbox.NetboxDevice;
import Api_Netbox_Zabbix_Integration.Pages.Netbox.NetboxDeviceConfig;
import Api_Netbox_Zabbix_Integration.Pages.Netbox.NetboxLogin;
import Api_Netbox_Zabbix_Integration.Pages.Netbox.NetboxMain;
import Api_Netbox_Zabbix_Integration.Pages.Zabbix.ZabbixHosts;
import Api_Netbox_Zabbix_Integration.Pages.Zabbix.ZabbixLogin;
import Api_Netbox_Zabbix_Integration.Pages.Zabbix.ZabbixMain;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;

public class DeleteDeviceStepDef {
    @Steps
    NetboxLogin netboxLogin;
    @Steps
    NetboxMain netboxMainPage;
    @Steps
    NetboxDeviceConfig netboxDeviceConfig;

    @Steps
    ZabbixLogin zabbixLogin;
    @Steps
    ZabbixMain zabbixMain;
    @Steps
    ZabbixHosts zabbixHosts;

    NetboxDevice netboxDevice;
    ManageDriver manageDriver = new ManageDriver();
    Credentials credentials = new Credentials();


    @Before
    public void Login() {

        netboxLogin.openAplication(credentials.NetboxURL);

        netboxLogin.clickToLoginButton();

        netboxLogin.enterUsername(credentials.NetboxUsername);
        netboxLogin.enterPassword(credentials.NetboxPassword);

        netboxLogin.clickLoginButton();

    }



    @Given("I created a netbox Device: {string}")
    public void iCreatedANetboxDeviceDeviceName(String string) {
    }

    @And("The {string} is deleted")
    public void theDeviceNameIsDeleted(String string) {
        String urlHost =  credentials.NetboxURL +"dcim/devices/";
        netboxDevice.openURL(urlHost);

        netboxDevice.deleteSingleDevice(string);
    }

    @Given("I'm in the netbox Device Page")
    public void i_m_in_the_netbox_device_page() {
        String urlHost =  credentials.NetboxURL +"dcim/devices/";

        netboxDevice.openURL(urlHost);

    }

    @When("I delete all the devices")
    public void i_delete_all_the_devices() {
        String urlHost =  credentials.NetboxURL +"dcim/devices/";
        netboxDevice.openURL(urlHost);
        netboxDevice.deleteAllNetboxDevices();
    }

    @Then("The Zabbix hosts are empty")
    public void the_zabbix_hosts_are_empty() {

    }


}
