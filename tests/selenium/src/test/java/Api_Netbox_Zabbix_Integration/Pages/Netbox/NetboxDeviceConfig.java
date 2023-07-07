package Api_Netbox_Zabbix_Integration.Pages.Netbox;

import Api_Netbox_Zabbix_Integration.Manage.ManageDriver;
import Api_Netbox_Zabbix_Integration.POM.Netbox.NetboxDeviceConfigPOM;
import net.thucydides.core.annotations.Step;

public class NetboxDeviceConfig {
    ManageDriver manageDriver;

    NetboxDeviceConfigPOM netboxDevicePOM;

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
