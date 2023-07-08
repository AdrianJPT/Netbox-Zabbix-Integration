package Api_Netbox_Zabbix_Integration.Pages.Netbox;

import Api_Netbox_Zabbix_Integration.Manage.Credentials;
import Api_Netbox_Zabbix_Integration.Manage.ManageDriver;
import Api_Netbox_Zabbix_Integration.POM.Netbox.NetboxDevicePOM;

import Api_Netbox_Zabbix_Integration.POM.Netbox.NetboxMainPOM;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.WebElement;

public class NetboxDevice {

    ManageDriver manageDriver;

    NetboxMainPOM netboxMainPOM;
    NetboxDevicePOM netboxDevicePOM;

    Credentials credentials;

    public void openURL(String url){

        netboxDevicePOM.openUrl(url);
    }
    public void deleteAllNetboxDevices(){

        String urlHost =  credentials.NetboxURL +"dcim/devices/";
        netboxDevicePOM.openUrl(urlHost);
        netboxMainPOM.deviceButton.click();

        netboxDevicePOM.selectAllDevices.click();
        netboxDevicePOM.deleteAllDevices.click();

    }

    @Step
    public void deleteSingleDevice(String string) {


        WebElement deviceNetbox = netboxDevicePOM.deviceNetBox(string);

        deviceNetbox.click();
        netboxDevicePOM.deleteDeviceButton.click();

    }

}
