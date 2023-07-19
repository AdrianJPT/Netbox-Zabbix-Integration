package Api_Netbox_Zabbix_Integration.stepdefinitions;

import Api_Netbox_Zabbix_Integration.Manage.ManageDriver;
import Api_Netbox_Zabbix_Integration.Pages.Netbox.NetboxDevice;
import Api_Netbox_Zabbix_Integration.Pages.Netbox.NetboxDeviceConfig;
import Api_Netbox_Zabbix_Integration.Pages.Zabbix.ZabbixHosts;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;

import java.util.List;

import static Api_Netbox_Zabbix_Integration.Manage.ManageDriver.TABS;
import static Api_Netbox_Zabbix_Integration.Manage.ManageDriver.ZabbixTAB;

public class DeleteDeviceStepDef {


    @Steps
    NetboxDeviceConfig netboxDeviceConfig;

    @Steps
    ZabbixHosts zabbixHosts;

    @Steps
    NetboxDevice netboxDevice;

    ManageDriver manageDriver;





    // SCENARIO CLEANING UP
    @Given("There are any device in Devices in Netbox")
    public void thereAreAnyDeviceInDevicesInNetbox() {

        netboxDevice.openURLdevice();

        netboxDevice.deleteAllNetboxDevices();

    }

    @Given("There are any device in Hosts in Zabbix")
    public void thereAreAnyDeviceInHostsInZabbix() {


        manageDriver.changeTab(manageDriver.getDriver(),ZabbixTAB );

        zabbixHosts.openURLHosts();

        //if there are no devices, the flow should continue
        try {
            zabbixHosts.deleteAllHosts();
        }catch (Exception e){
            System.out.println("No hosts were found");
        }



    }

    // -- SCENARIO 01
    @Given("I have {string} devices in Netbox and Zabbix")
    public void i_have_devices_in_netbox_and_zabbix(String DeviceName) {

        netboxDevice.openURLdevice();
        netboxDeviceConfig.createNetboxDevice(DeviceName,"device_role","Device Type", "SITIO-01","Template_Adrian");
    }

    @When("I delete the device {string}")
    public void i_delete_the_device( String DeviceName) {
        netboxDevice.deleteSingleDevice(DeviceName);

    }

    @Then("{string} should not exist in Zabbix")
    public void should_not_exist_in_zabbix(String DeviceName) {
        manageDriver.changeTab(manageDriver.getDriver(), ZabbixTAB);
        zabbixHosts.openURLHosts();

        zabbixHosts.validateHostExist(DeviceName);


    }

    // -- SCENARIO 02
    @Given("I have {int} Devices in Netbox and Zabbix")
    public void i_have_devices_in_netbox_and_zabbix(Integer iteration) {
        netboxDevice.openURLdevice();

        netboxDeviceConfig.createNetboxDevice("Delete_Clone_1","device_role","Device Type", "SITIO-01","Template_Adrian");

        netboxDevice.cloneNetboxDevice("Delete_Clone_1",iteration);
    }

    @When("I delete all the devices")
    public void i_delete_all_the_devices() {
        netboxDevice.openURLdevice();
        netboxDevice.deleteAllNetboxDevices();
    }

    @Then("I should not see the {int} devices in Zabbix")
    public void i_should_not_see_the_devices_in_zabbix(int iteration) {
        manageDriver.changeTab(manageDriver.getDriver(), ZabbixTAB  );
        zabbixHosts.openURLHosts();
        zabbixHosts.validateAllHostsExists(iteration);



    }


}
