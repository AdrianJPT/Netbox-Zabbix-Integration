package Cibertec.stepdefinitions;

import Cibertec.Pages.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.annotations.Manual;
import net.thucydides.core.annotations.Steps;

import java.sql.SQLException;

public class TramiteStep {
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




    @Given("Me he logeado correctamente con {string} y {string}")
    public void me_he_logeado_correctamente(String user_desaprobado, String password) throws SQLException {

        querys.NotificacionUpdate(user_desaprobado);
        loginPage.openAplication();

        loginPage.enterUsername(user_desaprobado);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
    }


    @When("Yo click en Solicitud de Tramite")
    public void yo_click_en_solicitud_de_tramite() {
        mainPage.ScrollToAlPanelTramitesGenerales();
        mainPage.clickSolicitudTramitesGenerales();

    }  // TIENE EL ORDEN DE EJECUCION ESTA MAL b

    @When("Elijo un Tipo de Modalidad {string}")
    public void elijo_un_tipo_de_modalidad(String modalidad) {
        gestionTramitesPage.seleccionarModalidad(modalidad);
    }

    @When("Elijo un Programa")
    public void elijo_un_programa() {
        gestionTramitesPage.seleccionarPrograma();
    }

    @Then("Elijo un Tramite {string}")
    public void elijo_un_tramite(String tramite) {
        gestionTramitesPage.seleccionarTramite(tramite);
    }

    @When("Elijo Modalidad de Pago")
    public void elijo_modalidad_de_pago() {
        gestionTramitesPage.seleccionarPago("BANCO");
    }

    @When("Adjunto Archivo")
    public void adjunto_archivo() throws InterruptedException {
        gestionTramitesPage.adjuntarArchivo("C:\\Users\\apablliu\\Downloads\\a.pdf");

    }

    @When("Escribo Motivo {string}")
    public void escribo_motivo(String motivo) {
        gestionTramitesPage.ingresarMotivo(motivo);
    }


    @When("Click en el boton Enviar Solicitud")
    public void click_en_el_boton_enviar_solicitud() {
        gestionTramitesPage.clickEnviarSolicitud();
    }

    @Then("Debe mostrar la solicitud creada")
    public void debe_mostrar_la_solicitud_creada() {

    }


    @When("Realizo click en mi {string} creado")
    public void realizo_click_en_mi_tramite_creado(String tramite) {
        mainPage.abrirBandejaUsuario(); // provicional debug
        tramitesEnCurso.obtenerIDTramite();
        tramitesEnCurso.clickSolicitudCreada(tramite);
    }

    @Then("El estatus deberia estas en PENDIENTE DE PAGO")
    public void el_estatus_deberia_estas_en_pendiente_de_pago() {
        tramitesEnCurso.validarEstado("PENDIENTE DE PAGO"); // MAYUSCULAS
    }

    @Then("El tracking debe estar en la etapa REGISTRO DE SOLICITUD")
    public void el_tracking_debe_estar_en_la_etapa_registro_de_solicitud() {
        tramitesEnCurso.validarTrackStatusREGISTROdeSOLICITUD();
    }

    @Then("Deberia ver el precio {string}")
    public void deberia_ver_el_precio(String precio) {
        tramitesEnCurso.validarPrecio(precio);
    }

    @Then("Recibo email de Registro")
    public void recibo_email_de_registro() {

    }

    @Then("Recibo email de Pendiente Pago")
    public void recibo_email_de_pendiente_pago() {

    }

    @When("Alumno {string} realiza el pago del {string} con codigo {string}")
    public void realizo_el_pago(String user_desaprobado, String tramite, String cod_tramite) {
        querys.realizarPago(user_desaprobado);
        querys.Reporte_Tramite(cod_tramite);
        Serenity.getDriver().navigate().refresh();
        tramitesEnCurso.clickSolicitudCreada(tramite);

    }


    @Then("El tracking debe estar en la etapa PAGO DE SOLICITUD")
    public void el_tracking_debe_estar_en_la_etapa_pago_de_solicitud() {
        tramitesEnCurso.validarTrackStatusPAGOdeSOLICITUD();
    }

    @Then("Recibo un email de PAGADO")
    public void recibo_un_email_de_pagado() {

    }

    @When("El evaluador con contrasena {string} desaprueba el {string} con el motivo {string" +
            "}" )
    public void elEvaluadorConContrasenaPasswordDesapruebaElTramiteConElMotivoMotivo(String password, String tramite, String motivo)
    {
        tramitesEnCurso.loginOut();


        loginPage.enterUsernameEvaluador(querys.EVALUADOR);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();

        panelEvaluador.ingresarBandejaEvaluador();
        panelEvaluador.buscarTramiteID(tramitesEnCurso.ID_tramite);
        panelEvaluador.abrirTramite(tramite);
        panelEvaluador.rechazarTramite(motivo);


    }


    @Then("El trackin de {string} con contraseña {string} su {string} debe estar en la etapa de notificacion")
    public void elTrackinDeUser_desaprobadoDebeEstarEnLaEtapaDeNotificacion(String user_desaprobado,String password,String tramite) {
        tramitesEnCurso.loginOut();
        loginPage.enterUsername(user_desaprobado);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        mainPage.abrirBandejaUsuario();
        tramitesEnCurso.clickSolicitudCreada(tramite);
        tramitesEnCurso.validarTrackStatusNOTIFICACION();

    }

    @Manual
    @And("Recibo email de PROCEDE")
    public void reciboEmailDePROCEDE() {
    }

    @Manual
    @And("Recibo email de NO PROCEDE")
    public void reciboEmailDeNOPROCEDE() {
    }

    @Manual
    @And("Recibo email de ANULADO")
    public void reciboEmailDeANULADO() {
    }

    @Given("No he pagagado")
    public void noHePagagado() {
    }

    @Manual
    @Then("Deberia recibir email de NO PROCEDE")
    public void reciboEmailDeNoProcede(){
    }

}
