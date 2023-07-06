package Netbox.stepdefinitions;

import Netbox.Manage.Credentials;
import Netbox.Manage.ManageDriver;
import Netbox.Pages.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import net.thucydides.core.annotations.Steps;
import org.apache.commons.digester.annotations.rules.SetProperty;
import org.openqa.selenium.WebDriver;

public class CreateDeviceStepDef {
    @Steps
    NetboxLogin netboxLogin;
    @Steps
    NetboxMain netboxMainPage;
    @Steps
    NetboxDevice netboxDevice;

    @Steps
    ZabbixLogin zabbixLogin;
    @Steps
    ZabbixMain zabbixMain;
    @Steps
    ZabbixHosts zabbixHosts;

    ManageDriver manageDriver;
    Credentials credentials = new Credentials();


    @Given("I log in successfully in Netbox")
    public void i_log_in_successfully_in_netbox() {
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

        netboxDevice.writeDeviceName(DeviceName);
    }

    @When("The field Device Role: {string} is selected")
    public void the_field_device_role_is_selected(String DeviceRole) {
        netboxDevice.selectDeviceRole(DeviceRole);
    }

    @When("The field Device Type: {string} is selected")
    public void the_field_device_type_is_selected(String DeviceType) {
        netboxDevice.selectDeviceType(DeviceType);
    }

    @When("The field Site: {string} is selected")
    public void the_field_site_is_selected(String Site) {
        netboxDevice.selectSite(Site);
    }

    @When("The field Platform: {string} is selected")
    public void the_field_platform_is_selected(String Platform) {
        netboxDevice.selectPlatform(Platform);
    }

    @And("The button Create Device is clicked")
    public void the_button_create_device_is_clicked() {
        netboxDevice.clickCreateDeviceButton();
    }


    @Given("I log in successfully in Zabbix")
    public void iLogInSuccessfullyInZabbix() {
        manageDriver.ZabbixTAB = manageDriver.createNewTab();

        zabbixLogin.openAplication( credentials.ZabbixURL );
        zabbixLogin.enterUsername(credentials.ZabbixUsername);
        zabbixLogin.enterPassword(credentials.ZabbixPassword);
        zabbixLogin.clickLoginButton();
    }
    @Then("The Netbox device: {string} is displayed in Zabbix hosts")
    public void the_netbox_device_is_displayed_in_zabbix_hosts(String NetboxDevice) {
        zabbixMain.clickOnBarHost();

        zabbixHosts.cleanFilters();
        zabbixHosts.findZabbixDevice(NetboxDevice);



    }

    @Then("The Zabbix host is related to the HostGroup: {string}")
    public void the_zabbix_host_is_related_to_the_host_group(String string) {

    }

    @Then("The Zabbix host is related to the template: {string}")
    public void the_zabbix_host_is_related_to_the_template(String string) {

    }

    @Then("The Zabbix host interface is DNS")
    public void the_zabbix_host_interface_is_dns() {

    }

    @Then("The name is UPDATE_IP with port {int}")
    public void the_name_is_update_ip_with_port(Integer int1) {

    }



}
