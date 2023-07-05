package Cibertec.stepdefinitions;

import Cibertec.Pages.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;

import java.sql.SQLException;

public class LoginPageStepDef {
    @Steps
    LoginPage loginPage;
    @Steps
    GestionTramitesPage gestionTramitesPage;
    @Steps
    MainPage mainPage;

    @Steps
    Querys querys;

    @Steps
    TramitesEnCurso tramitesEnCurso;

    @Steps
    PanelEvaluador panelEvaluador;

    @When("enter a correct username and password")
    public void enter_a_correct_username_and_password() {
        loginPage.enterUsername("202010959");
        loginPage.enterPassword("123456789");
    }
    @Then("I should see the main page")
    public void i_should_see_the_main_page() {
        loginPage.clickLoginButton();
    }


    @Given("I am in the login page of Tramitator")
    public void iAmInTheLoginPageOfTramitator() {
        System.out.println("Login Page");
    }
}
