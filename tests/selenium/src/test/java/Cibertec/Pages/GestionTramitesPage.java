package Cibertec.Pages;

import net.thucydides.core.annotations.Step;

import java.time.Duration;


public class GestionTramitesPage
{
    GestionTramitesPagePOM gestionTramitesPagePOM;


    @Step
    public void seleccionarModalidad(String tipoModalidad){
        gestionTramitesPagePOM.comboboxTipoModalidad.withTimeoutOf(Duration.ofSeconds(10)).waitUntilEnabled();
        gestionTramitesPagePOM.comboboxTipoModalidad.click();
        gestionTramitesPagePOM.seleccionarOpcionCombobox(tipoModalidad);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @Step
    public void seleccionarPrograma(){

        // va a escoger la segunda opcion, posicion 2
        gestionTramitesPagePOM.seleccionarPosicicionOpcionCombobox();
        //System.out.println(programa);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @Step
    public void seleccionarTramite(String tipoTramite){
        gestionTramitesPagePOM.comboboxtipoTramite.withTimeoutOf(Duration.ofSeconds(10)).waitUntilEnabled();
        gestionTramitesPagePOM.comboboxtipoTramite.click();
        gestionTramitesPagePOM.seleccionarOpcionCombobox(tipoTramite);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    @Step
    public void seleccionarPago(String formaPago){
        gestionTramitesPagePOM.comboboxformaPago.withTimeoutOf(Duration.ofSeconds(10)).waitUntilEnabled();

        gestionTramitesPagePOM.comboboxformaPago.click();
        gestionTramitesPagePOM.seleccionarOpcionCombobox(formaPago);
    }
    @Step
    public void adjuntarArchivo(String RutaFile) throws InterruptedException {
        //gestionTramitesPagePOM.scrollDownMax();

        gestionTramitesPagePOM.scrollToElement(gestionTramitesPagePOM.tittle_AdjuntarUnArchivo);
        gestionTramitesPagePOM.cargarArchivo(RutaFile);
    }
    @Step
    public void ingresarMotivo(String motivo){
        gestionTramitesPagePOM.scrollToElement(gestionTramitesPagePOM.motivo);
        gestionTramitesPagePOM.motivo.sendKeys(motivo);
    }
    @Step
    public void clickEnviarSolicitud(){
        gestionTramitesPagePOM.clickEnviarSolicitud();
    }

    //      AVISOS
    @Step
    public void validarAvisos(String txt_DEFINICION, String txt_TIEMPO,String txt_MODALIDAD,String txtPREREQUISITOS){
        gestionTramitesPagePOM.debug_states();
        gestionTramitesPagePOM.validarAvisosTexto(gestionTramitesPagePOM.avisoDefinicionTxt,  txt_DEFINICION);
        gestionTramitesPagePOM.validarAvisosTexto(gestionTramitesPagePOM.avisoTiempoTxt,  txt_TIEMPO);
        gestionTramitesPagePOM.validarAvisosTexto(gestionTramitesPagePOM.avisoModalidadTxt, txt_MODALIDAD);
        gestionTramitesPagePOM.validarAvisosTexto(gestionTramitesPagePOM.avisoPrerequisitosTxt , txtPREREQUISITOS);

        //gestionTramitesPagePOM.validarAvisosImagen(gestionTramitesPagePOM.avisoDefinicionImg);
        //gestionTramitesPagePOM.validarAvisosImagen(gestionTramitesPagePOM.avisoTiempoImg);
        //gestionTramitesPagePOM.validarAvisosImagen(gestionTramitesPagePOM.avisoModalidadImg);
        //gestionTramitesPagePOM.validarAvisosImagen(gestionTramitesPagePOM.avisoPrerequisitosImg);


    }

    @Step
    public void validarAvisoTiempo(){}

    @Step
    public void validarAvisoModalidad(){}

    @Step
    public void validarAvisoPrerequisitos(){}

}
