package Api_Netbox_Zabbix_Integration.Pages.Netbox;

import Api_Netbox_Zabbix_Integration.Manage.ManageDriver;
import Api_Netbox_Zabbix_Integration.POM.Netbox.NetboxDeviceConfigPOM;
import Api_Netbox_Zabbix_Integration.POM.Netbox.NetboxDevicePOM;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class NetboxDeviceConfig {
    ManageDriver manageDriver = new ManageDriver();


    NetboxDevicePOM netboxDevicePOM = new NetboxDevicePOM();

    NetboxDeviceConfigPOM netboxDeviceConfigPOM;
    @Step
    public void writeDeviceName(String DeviceName) {
        netboxDeviceConfigPOM.DeviceName.sendKeys(DeviceName);
    }

    @Step
    public void selectDeviceRole(String DeviceRole) {
        //manageDriver.ScrollToElement(netboxDevicePOM.DeviceRoleBox);
        netboxDeviceConfigPOM.DeviceRoleBox.click();
        try {
            Thread.sleep(500);} catch (InterruptedException e) {throw new RuntimeException(e);}
        netboxDeviceConfigPOM.selectDeviceRole(DeviceRole);

    }
    @Step
    public void selectDeviceType(String DeviceType) {
        //manageDriver.ScrollToElement(netboxDevicePOM.DeviceType);
        netboxDeviceConfigPOM.DeviceType.click();
        try {
            Thread.sleep(500);} catch (InterruptedException e) {throw new RuntimeException(e);}
        netboxDeviceConfigPOM.selectDeviceType(DeviceType);
    }
    @Step
    public void selectSite(String Site) {
        //manageDriver.ScrollToElement(netboxDevicePOM.Site);
        netboxDeviceConfigPOM.Site.click();
        try {
            Thread.sleep(500);} catch (InterruptedException e) {throw new RuntimeException(e);}
        netboxDeviceConfigPOM.selectSite(Site);
    }
    @Step
    public void selectPlatform(String Platform) {
        manageDriver.ScrollToElement(netboxDeviceConfigPOM.Site);

        netboxDeviceConfigPOM.Platform.click();
        try {
            Thread.sleep(500);} catch (InterruptedException e) {throw new RuntimeException(e);}
        netboxDeviceConfigPOM.selectPlatform(Platform);
    }
    @Step
    public void clickCreateDeviceButton() {
        netboxDeviceConfigPOM.ButtonCreateDevice.click();
    }

    @Step
    public void createNetboxDevice(String DeviceName, String DeviceRole, String DeviceType, String Site, String Platform) {
        netboxDevicePOM.deviceButtonADD.click();

        // NAME FIELD
        netboxDeviceConfigPOM.DeviceName.sendKeys(DeviceName);

        //DEVICE ROLE
        netboxDeviceConfigPOM.DeviceRoleBox.click();
        try {
            Thread.sleep(200);} catch (InterruptedException e) {throw new RuntimeException(e);}
        netboxDeviceConfigPOM.selectDeviceRole(DeviceRole);

        // DEVICE TYPE
        netboxDeviceConfigPOM.DeviceType.click();
        try {
            Thread.sleep(200);} catch (InterruptedException e) {throw new RuntimeException(e);}
        netboxDeviceConfigPOM.selectDeviceType(DeviceType);

        //SITE
        netboxDeviceConfigPOM.Site.click();
        try {
            Thread.sleep(200);} catch (InterruptedException e) {throw new RuntimeException(e);}
        netboxDeviceConfigPOM.selectSite(Site);

        // PLATFORM
        manageDriver.ScrollToElement(netboxDeviceConfigPOM.Site);

        netboxDeviceConfigPOM.Platform.click();
        try {
            Thread.sleep(200);} catch (InterruptedException e) {throw new RuntimeException(e);}
        netboxDeviceConfigPOM.selectPlatform(Platform);

        netboxDeviceConfigPOM.ButtonCreateDevice.click();

    }


}
