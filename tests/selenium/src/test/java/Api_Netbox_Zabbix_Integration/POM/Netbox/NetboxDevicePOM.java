package Api_Netbox_Zabbix_Integration.POM.Netbox;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.WebElement;

public class NetboxDevicePOM extends PageObject {

    @FindBy(xpath = "//input[@title='Toggle All']")
    public WebElement selectAllDevices;

    @FindBy(xpath = "//button[@name='_delete']")
    public WebElement deleteAllDevices;


}
