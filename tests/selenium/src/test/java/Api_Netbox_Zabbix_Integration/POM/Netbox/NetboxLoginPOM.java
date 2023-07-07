package Api_Netbox_Zabbix_Integration.POM.Netbox;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NetboxLoginPOM extends PageObject {

    WebDriver driver = getDriver();


    @FindBy(css = ".content-container .mdi.mdi-login-variant")
    public WebElement LoginButtonPage;
    @FindBy(id = "id_username")
    public WebElement UsernameInput;

    @FindBy(id = "id_password")
    public WebElement PasswordInput;

    @FindBy(css = "button.btn.btn-primary.btn-lg.w-100.mt-4")
    public WebElement LoginButton;


}
