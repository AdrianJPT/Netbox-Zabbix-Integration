package Api_Netbox_Zabbix_Integration.Pages.Netbox;

import Api_Netbox_Zabbix_Integration.Manage.Credentials;
import Api_Netbox_Zabbix_Integration.Manage.ManageDriver;
import Api_Netbox_Zabbix_Integration.POM.Netbox.NetboxLoginPOM;
import net.thucydides.core.annotations.Step;

public class NetboxLogin  {




    NetboxLoginPOM netboxLoginPOM = new NetboxLoginPOM();
    ManageDriver manageDriver = new ManageDriver();



    public void openAplication(String url){
        netboxLoginPOM.openUrl(url);;
    }


    public void clickToLoginButton(){
        manageDriver.WindowPageMaximize();
        netboxLoginPOM.LoginButtonPage.click();
        manageDriver.Syncchronization();
    }


    public void enterUsername(String username){
        netboxLoginPOM.UsernameInput.sendKeys(username);

    }


    public void enterPassword(String password){
        netboxLoginPOM.PasswordInput.sendKeys(password);
    }


    public void clickLoginButton(){
        netboxLoginPOM.LoginButton.click();
    }

}
