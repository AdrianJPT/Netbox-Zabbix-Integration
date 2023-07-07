package Api_Netbox_Zabbix_Integration.Pages.Netbox;

import Api_Netbox_Zabbix_Integration.POM.Netbox.NetboxMainPOM;
import net.thucydides.core.annotations.Step;

public class NetboxMain {
    NetboxMainPOM netboxMainPOM;
    @Step
    public void clickCreateDeviceButton() {
        netboxMainPOM.DeviceBar.click();
        netboxMainPOM.CreateDeviceButton.click();
    }



}
