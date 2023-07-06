package Netbox.Pages;

import Netbox.POM.NetboxMainPOM;
import net.thucydides.core.annotations.Step;

public class NetboxMain {
    NetboxMainPOM netboxMainPOM;
    @Step
    public void clickCreateDeviceButton() {
        netboxMainPOM.DeviceBar.click();
        netboxMainPOM.CreateDeviceButton.click();
    }

}
