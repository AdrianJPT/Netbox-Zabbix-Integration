package Cibertec.Pages;


import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class LoginPagePOM extends PageObject {


    @FindBy(id = "usuario")
    WebElementFacade usernameBox;

    @FindBy(id = "password")
    WebElementFacade passwordBox;

    @FindBy(id = "btnLogin")
    WebElementFacade loginButton;

    public void ventanaMax(){
        Serenity.getDriver().manage().window().setSize(new org.openqa.selenium.Dimension(1920, 1080));

        // Maximizar la ventana
        Serenity.getDriver().manage().window().maximize();
    }
}

