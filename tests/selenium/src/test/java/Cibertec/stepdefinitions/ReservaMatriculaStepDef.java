package Cibertec.stepdefinitions;

import Cibertec.Pages.GestionTramitesPage;
import io.cucumber.java.en.Then;
import net.thucydides.core.annotations.Manual;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;

public class ReservaMatriculaStepDef {

    @Steps
    GestionTramitesPage gestionTramitesPage;

    @Step
    @Then("Se debe mostrar los avisos de Reserva Matricula")
    public void seDebeMostrarLosAvisosDeReservaMatricual() {


        gestionTramitesPage.validarAvisos("Trámite que permite al alumno reservar su matricula",
                        "03 dias utiles",
                        "AC, EA, SP, WS, DC",
                        "Comunicarse con SAE para la entrega del formulario");
    }


}
