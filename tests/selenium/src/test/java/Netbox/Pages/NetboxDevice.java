package Netbox.Pages;

import Netbox.Manage.ManageDriver;
import Netbox.POM.NetboxDevicePOM;
import net.thucydides.core.annotations.Step;

public class NetboxDevice {
    ManageDriver manageDriver;

    NetboxDevicePOM netboxDevicePOM;

    @Step
    public void writeDeviceName(String DeviceName) {
        netboxDevicePOM.DeviceName.sendKeys(DeviceName);
    }

    @Step
    public void selectDeviceRole(String DeviceRole) {
        //manageDriver.ScrollToElement(netboxDevicePOM.DeviceRoleBox);
        netboxDevicePOM.DeviceRoleBox.click();
        netboxDevicePOM.selectDeviceRole(DeviceRole);

    }
    @Step
    public void selectDeviceType(String DeviceType) {
        //manageDriver.ScrollToElement(netboxDevicePOM.DeviceType);
        netboxDevicePOM.DeviceType.click();
        netboxDevicePOM.selectDeviceType(DeviceType);
    }
    @Step
    public void selectSite(String Site) {
        //manageDriver.ScrollToElement(netboxDevicePOM.Site);
        netboxDevicePOM.Site.click();
        netboxDevicePOM.selectSite(Site);
    }
    @Step
    public void selectPlatform(String Platform) {
        manageDriver.ScrollToElement(netboxDevicePOM.Site);
        netboxDevicePOM.Platform.click();
        netboxDevicePOM.selectPlatform(Platform);
    }

    public void clickCreateDeviceButton() {
        netboxDevicePOM.ButtonCreateDevice.click();
    }
}
