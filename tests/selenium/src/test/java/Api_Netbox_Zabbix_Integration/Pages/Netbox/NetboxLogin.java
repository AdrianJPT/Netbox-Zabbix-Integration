package Api_Netbox_Zabbix_Integration.Pages.Netbox;

import Api_Netbox_Zabbix_Integration.Manage.Credentials;
import Api_Netbox_Zabbix_Integration.Manage.ManageDriver;
import Api_Netbox_Zabbix_Integration.POM.Netbox.NetboxLoginPOM;
import net.thucydides.core.annotations.Step;

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
