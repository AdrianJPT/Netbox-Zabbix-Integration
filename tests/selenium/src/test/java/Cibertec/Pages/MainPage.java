package Cibertec.Pages;


import net.thucydides.core.annotations.Step;
import org.junit.Assume;

public class MainPage {
    MainPagePOM mainPagePOM;

    @Step
    public void openAplication(){
        mainPagePOM.open();
    }

    @Step
    public void ScrollToAlPanelTramitesGenerales(){
         mainPagePOM.scrollToElement(mainPagePOM.linkSolicitudTramitesGenerales);
    }
    @Step
    public void clickSolicitudTramitesGenerales(){
        mainPagePOM.linkSolicitudTramitesGenerales.click();
    }

    @Step
    public void abrirBandejaUsuario(){
        mainPagePOM.buttonMisTramites.click();
        mainPagePOM.buttonBandejaEvaluador.click();
    }

    @Step
    public void tittleSolicitudTramitesGeneralesEsVisible(String Titulo_esperado){

        mainPagePOM.tituloDelTramite(mainPagePOM.tittleSolicitudTramitesGenerales, Titulo_esperado);
    }
}
