package Netbox.Pages;

import Netbox.Manage.Credentials;
import Netbox.POM.ZabbixLoginPOM;
import net.thucydides.core.annotations.Step;

public class ZabbixLogin {

    ZabbixLoginPOM zabbixLoginPOM;

    Credentials credentials;
    public void openAplication(String url){
        zabbixLoginPOM.openUrl(url);;
    }

    @Step
    public void enterUsername(String user) {
        zabbixLoginPOM.usernameInput.sendKeys(user);
    }

    @Step
    public void enterPassword(String pass) {
        zabbixLoginPOM.passwordInput.sendKeys(pass);
    }
    @Step
    public void clickLoginButton(){
        zabbixLoginPOM.buttonLogin.click();
    }

}
