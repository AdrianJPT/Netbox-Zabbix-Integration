package Cibertec.Pages;

import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import static org.junit.Assume.assumeTrue;

public class TramitesEnCurso {


    TramitesEnCursoPOM tramitesEnCursoPOM;

    public String ID_tramite;

    @Step
    public void obtenerIDTramite(){
        ID_tramite = tramitesEnCursoPOM.obtenerIDTramite();
    }

    @Step
    public void clickSolicitudCreada(String tramite){
        tramitesEnCursoPOM.clickSolicitudCreada(tramite);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Step
    public void validarEstado(String EstadoEsperado){
        tramitesEnCursoPOM.validarEstado(EstadoEsperado);
    }

    @Step
    public void validarPrecio(String precioEsperado){
        tramitesEnCursoPOM.scrollToElement(tramitesEnCursoPOM.MontoPagar);
        tramitesEnCursoPOM.validarPrecio(precioEsperado);
    }

    @Step
    public void validarMotivo(String MotivoEsperado){
        tramitesEnCursoPOM.validarMotivo(MotivoEsperado);
    }

    @Step
    public void validarTrackStatusREGISTROdeSOLICITUD(){
        assumeTrue("El elemento REGISTRO SOLICITUD no es visible", tramitesEnCursoPOM.trackRegistroDeSolicitud.isVisible());
    }
    @Step
    public void validarTrackStatusPAGOdeSOLICITUD(){
        assumeTrue("El elemento PAGO SOLICITUD no es visible", tramitesEnCursoPOM.trackPagoDeSolicitud.isVisible());
    }
    @Step
    public void validarTrackStatusEVALUACIONdeSOLICITUD(){
        assumeTrue("El elemento EVALUACION SOLICITUD no es visible", tramitesEnCursoPOM.trackEvaluacionDeSolicitud.isVisible());
    }
    @Step
    public void validarTrackStatusNOTIFICACION(){
        assumeTrue("El elemento NOTIFICACION no es visible", tramitesEnCursoPOM.trackNotificacion.isVisible());
    }

    @Step
    public void loginOut(){
        tramitesEnCursoPOM.loginOut();
    }



}
