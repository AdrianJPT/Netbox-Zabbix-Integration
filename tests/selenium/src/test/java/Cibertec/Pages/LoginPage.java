package Cibertec.Pages;

import net.thucydides.core.annotations.Step;
import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.zip.CheckedOutputStream;

import static net.serenitybdd.core.Serenity.getDriver;

public class LoginPage {

    WebDriver driver = getDriver();
    LoginPagePOM loginPagePOM = new LoginPagePOM();

    @Step
    public void openAplication(){
        //loginPagePOM = new LoginPagePOM();
        loginPagePOM.ventanaMax();
        driver.get("https://mistramitescerti.cibertec.edu.pe/Autenticar/LoginCT");

    }
    @Step
    public void enterUsername(String user){
        loginPagePOM.usernameBox.sendKeys("I"+user);
    }

    @Step
    public void enterUsernameEvaluador(String evaluador){
        loginPagePOM.usernameBox.sendKeys(evaluador);
    }
    @Step
    public void enterPassword(String pass){
        loginPagePOM.passwordBox.sendKeys(pass);
    }
    @Step
    public void clickLoginButton(){
        loginPagePOM.loginButton.click();
    }
}
