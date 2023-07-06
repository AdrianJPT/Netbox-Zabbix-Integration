package Netbox.Pages;

import Netbox.Manage.Credentials;
import Netbox.Manage.ManageDriver;
import Netbox.POM.NetboxLoginPOM;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.v85.page.Page;

public class NetboxLogin  {




    NetboxLoginPOM netboxLoginPOM;
    ManageDriver manageDriver;
    Credentials credentials;


    public void openAplication(String url){
        netboxLoginPOM.openUrl( url);;
    }

    @Step
    public void clickToLoginButton(){
        manageDriver.WindowPageMaximize();
        netboxLoginPOM.LoginButtonPage.click();
    }

    @Step
    public void enterUsername(String username){
        netboxLoginPOM.UsernameInput.sendKeys(username);

    }

    @Step
    public void enterPassword(String password){
        netboxLoginPOM.PasswordInput.sendKeys(password);
    }

    @Step
    public void clickLoginButton(){
        netboxLoginPOM.LoginButton.click();
    }

}
