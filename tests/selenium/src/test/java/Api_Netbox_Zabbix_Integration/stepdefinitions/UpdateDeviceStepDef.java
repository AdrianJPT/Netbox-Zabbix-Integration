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

public class UpdateDeviceStepDef {
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





}
