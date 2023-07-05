package Cibertec.stepdefinitions;

import Cibertec.Pages.GestionTramitesPage;
import io.cucumber.java.en.Then;
import net.thucydides.core.annotations.Steps;

public class CambioCarreraStepDef {

    @Steps
    GestionTramitesPage gestionTramitesPage;

    @Then("Se debe mostrar los avisos de Cambio de carrera")
    public void seDebeMostrarLosAvisosDeCambioCarrera() {

        gestionTramitesPage.validarAvisos("Tramite que permite al alumno obtener un cambio de carrera",
                        "03 dias utiles",
                        "AC, EA, SP, WS, DC",
                        "Comunicarse con SAE para la entrega del formulario");
    }
}
