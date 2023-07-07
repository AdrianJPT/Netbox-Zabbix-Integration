package Api_Netbox_Zabbix_Integration.Pages.Netbox;

import Api_Netbox_Zabbix_Integration.Manage.Credentials;
import Api_Netbox_Zabbix_Integration.Manage.ManageDriver;
import Api_Netbox_Zabbix_Integration.POM.Netbox.NetboxDevicePOM;

import Api_Netbox_Zabbix_Integration.POM.Netbox.NetboxMainPOM;

public class NetboxDevice {

    ManageDriver manageDriver;

    NetboxMainPOM netboxMainPOM;
    NetboxDevicePOM netboxDevicePOM;

    Credentials credentials;
    public void deleteAllNetboxDevices(){
        manageDriver.changeTab(manageDriver.NetboxTAB);

        String urlHost =  credentials.NetboxURL +"dcim/devices/";
        netboxDevicePOM.openUrl(urlHost);
        netboxMainPOM.deviceButton.click();

        netboxDevicePOM.selectAllDevices.click();
        netboxDevicePOM.deleteAllDevices.click();

    }
}
