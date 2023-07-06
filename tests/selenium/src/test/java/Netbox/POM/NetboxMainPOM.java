package Netbox.POM;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.WebElement;

public class NetboxMainPOM extends PageObject {


    @FindBy(xpath = ".//span[text()='Devices']")
    public WebElement DeviceBar;
    @FindBy(css = "a.btn.btn-sm.btn-green.lh-1[href='/dcim/devices/add/']")
    public WebElement CreateDeviceButton;

}
