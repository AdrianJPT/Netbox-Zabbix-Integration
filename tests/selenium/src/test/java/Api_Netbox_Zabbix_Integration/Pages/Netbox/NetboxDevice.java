package Api_Netbox_Zabbix_Integration.Pages.Netbox;

import Api_Netbox_Zabbix_Integration.Manage.Credentials;
import Api_Netbox_Zabbix_Integration.Manage.ManageDriver;
import Api_Netbox_Zabbix_Integration.POM.Netbox.NetboxDeviceConfigPOM;
import Api_Netbox_Zabbix_Integration.POM.Netbox.NetboxDevicePOM;

import Api_Netbox_Zabbix_Integration.POM.Netbox.NetboxMainPOM;
import net.thucydides.core.annotations.Step;
import org.checkerframework.checker.units.qual.C;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.openqa.selenium.WebElement;

public class NetboxDevice {
    NetboxDevicePOM netboxDevicePOM;

    NetboxDeviceConfig netboxDeviceConfig;
    NetboxDeviceConfigPOM netboxDeviceConfigPOM;

    Credentials credentials = new Credentials();


    public void openURLdevice() {
        String url = credentials.NetboxURL + "dcim/devices/";
        netboxDevicePOM.openUrl(url);
    }

    @Step
    public void deleteAllNetboxDevices() {

        netboxDevicePOM.selectAllDevices.click();
        netboxDevicePOM.deleteAllDevices.click();

        // if there weren't any devices, the flow must continue
        try {
            netboxDevicePOM.deleteAllDevicesConfirm.click();


        }catch(Exception e){
            MatcherAssert.assertThat("No hosts to be deleted",netboxDevicePOM.deleteAllDevicesConfirm, Matchers.notNullValue());
        }
    }

    @Step
    public void deleteSingleDevice(String DeviceName) {


        netboxDevicePOM.deviceNetBox(DeviceName).click();
        netboxDevicePOM.deleteDeviceButton.click();
        netboxDevicePOM.deleteDeviceButtonConfirmation.click();

    }



    @Step
    public void cloneNetboxDevice(String DeviceName,int num_clones){
        // redirect to the device Page
        openURLdevice();
        // Clicks to clone the device chosen
        netboxDevicePOM.deviceNetBox(DeviceName).click();

        String clone = "Delete_Clone_";
        for (int i = 2 ; i <= num_clones; i++ ){
            netboxDeviceConfigPOM.DeviceButtonCLONE.click();


            String result = clone + i;

            netboxDeviceConfigPOM.DeviceName.sendKeys(result);

            netboxDeviceConfigPOM.ButtonCreateDevice.click();

        }
    }

}
