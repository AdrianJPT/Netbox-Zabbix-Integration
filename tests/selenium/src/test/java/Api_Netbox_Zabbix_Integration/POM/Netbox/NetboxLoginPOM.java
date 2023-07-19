package Api_Netbox_Zabbix_Integration.POM.Netbox;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NetboxLoginPOM extends PageObject {




    @FindBy(css = "div[class='col-3 d-flex flex-grow-1 pe-0 justify-content-end'] a[type='button']")
    public WebElement LoginButtonPage;
    @FindBy(id = "id_username")
    public WebElement UsernameInput;

    @FindBy(id = "id_password")
    public WebElement PasswordInput;

    @FindBy(css = "button.btn.btn-primary.btn-lg.w-100.mt-4")
    public WebElement LoginButton;


}
