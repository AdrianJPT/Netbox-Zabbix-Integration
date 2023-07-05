package Cibertec.stepdefinitions;

import Cibertec.Pages.*;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.sql.SQLException;

import static net.serenitybdd.core.Serenity.getDriver;


public class CarnetMedioPasajeStepDef {


    

    @Steps
    GestionTramitesPage gestionTramitesPage;

    @After
    public void tearDown() {
        getDriver().close(); // Opcionalmente, puedes utilizar quit() en lugar de close()
    }


    @Then("Se debe mostrar los avisos de Medio Pasaje")
    public void seDebeMostrarLosAvisosDeMedioPasaje() {

        gestionTramitesPage.validarAvisos
                ("Trámite que permite al alumno obtener un carné de medio pasaje",
                        "03 días útiles",
                        "AC, EA, SP, WS",
                        "No haber realizado el retiro de ciclo y tener la fotografía registrada en la intranet (recomendamos que verifiques)");
    }


}
    



