package Api_Netbox_Zabbix_Integration.Pages.Zabbix;

import Api_Netbox_Zabbix_Integration.Manage.Credentials;
import Api_Netbox_Zabbix_Integration.POM.Zabbix.ZabbixLoginPOM;
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
