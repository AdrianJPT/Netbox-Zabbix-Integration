package Netbox.POM;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.WebElement;

public class ZabbixLoginPOM extends PageObject {

    @FindBy(id = "name")
    public WebElement usernameInput;
    @FindBy(id = "password")
    public WebElement passwordInput;

    @FindBy(id = "enter")
    public WebElement buttonLogin;

}
