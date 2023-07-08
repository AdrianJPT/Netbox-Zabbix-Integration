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
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import net.thucydides.core.annotations.Steps;

public class CreateDeviceStepDef {
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





    @Given("I am creating a device in Netbox")
    public void i_am_creating_a_device_in_netbox() {

        netboxMainPage.clickCreateDeviceButton();
    }




    @When("The field Name is {string}")
    public void the_field_name_is(String DeviceName) {

        netboxDeviceConfig.writeDeviceName(DeviceName);
    }

    @When("The field Device Role: {string} is selected")
    public void the_field_device_role_is_selected(String DeviceRole) {
        netboxDeviceConfig.selectDeviceRole(DeviceRole);
    }

    @When("The field Device Type: {string} is selected")
    public void the_field_device_type_is_selected(String DeviceType) {
        netboxDeviceConfig.selectDeviceType(DeviceType);
    }

    @When("The field Site: {string} is selected")
    public void the_field_site_is_selected(String Site) {
        netboxDeviceConfig.selectSite(Site);
    }

    @When("The field Platform: {string} is selected")
    public void the_field_platform_is_selected(String Platform) {
        netboxDeviceConfig.selectPlatform(Platform);
    }

    @And("The button Create Device is clicked")
    public void the_button_create_device_is_clicked() {
        netboxDeviceConfig.clickCreateDeviceButton();
    }


    @Given("I log in successfully in Zabbix")
    public void iLogInSuccessfullyInZabbix() {
        manageDriver.ZabbixTAB = manageDriver.createNewTab();

        zabbixLogin.openAplication( credentials.ZabbixURL );
        zabbixLogin.enterUsername(credentials.ZabbixUsername);
        zabbixLogin.enterPassword(credentials.ZabbixPassword);
        zabbixLogin.clickLoginButton();

        zabbixMain.clickOnBarHost();
    }

    @Then("The Zabbix host: {string} interface is UPDATE_IP with port")
    public void theZabbixHostDeviceNameItsInterfaceIsUPDATE_IPWithPort( String string) {


        zabbixHosts.cleanFilters(string);
        zabbixHosts.validateInterface("UPDATE_IP:9999");
    }
    @Then("The Netbox device: {string} is displayed in Zabbix hosts")
    public void the_netbox_device_is_displayed_in_zabbix_hosts(String string) {

        zabbixHosts.findZabbixDeviceEquals(string);


    }

    @Then("The Zabbix host is related to the HostGroup: {string}")
    public void the_zabbix_host_is_related_to_the_host_group(String string) {
        zabbixHosts.matchSiteWithHostGroup(string);
    }

    @Then("The Zabbix host is related to the template: {string}")
    public void the_zabbix_host_is_related_to_the_template(String string) {
        zabbixHosts.matchPlatformWithTemplate(string);

    }


    @Then("The Zabbix host: {string} does not appear in Zabbix")
    public void theZabbixHostDeviceNameDoesNotAppearInZabbix( String s) {
        zabbixHosts.findZabbixDeviceNotEquals(s);
    }


}
