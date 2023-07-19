package Api_Netbox_Zabbix_Integration.Pages.Zabbix;

import Api_Netbox_Zabbix_Integration.Manage.Credentials;
import Api_Netbox_Zabbix_Integration.POM.Zabbix.ZabbixLoginPOM;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.Step;

public class ZabbixLogin extends PageObject {

    ZabbixLoginPOM zabbixLoginPOM = new ZabbixLoginPOM();

    public void openAplication(String url){
        zabbixLoginPOM.openUrl(url);;
    }


    public void enterUsername(String user) {
        zabbixLoginPOM.usernameInput.sendKeys(user);
    }


    public void enterPassword(String pass) {
        zabbixLoginPOM.passwordInput.sendKeys(pass);
    }

    public void clickLoginButton(){
        zabbixLoginPOM.buttonLogin.click();
    }

}
