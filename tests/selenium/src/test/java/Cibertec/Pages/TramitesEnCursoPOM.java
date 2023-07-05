package Cibertec.Pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TramitesEnCursoPOM extends PageObject {

    WebDriver driver = getDriver();
    WebElementFacade numeroSolicitud;

    @FindBy(xpath = "//div[@class = 'titulo' and text() ='Solicitud reserva de matricula' ][1]")
    WebElementFacade solicitud;

    @FindBy(xpath = "//li[contains(@class, 'en-proceso')][1]")
    WebElementFacade estado;

    @FindBy(xpath = "//div[contains(@id, 'PVDetalleSolicitud_')][1]//div[contains(text(),'S/')] ")
    WebElementFacade MontoPagar;


    WebElementFacade motivo = find((By.xpath("//div[contains(@class, 'panel-table lista-informativa bg-gray text-no-transform collapse')][1]//ul[@class = 'panel-table-row'][4]//li[2]/div")));

    @FindBy(xpath = "(//div[@class='track']//div[contains(@class,'step active')])[1]")
    WebElementFacade trackRegistroDeSolicitud;

    @FindBy(xpath = "(//div[@class='track']//div[contains(@class,'step active')])[2]")
    WebElementFacade trackPagoDeSolicitud;

    @FindBy(xpath = "(//div[@class='track']//div[contains(@class,'step active')])[3]")
    WebElementFacade trackEvaluacionDeSolicitud;
    @FindBy(xpath = "(//div[@class='track']//div[contains(@class,'step active')])[4]")
    WebElementFacade trackNotificacion;
    @FindBy(id = "dropdown-menu-user")
    WebElementFacade profileImage;
    @FindBy(xpath = "//ul[@role = 'menu'] // a[text() ='Cerrar Sesión']" )
    WebElementFacade buttonCerrarSesion;

    public String obtenerIDTramite(){
        return find(By.xpath("//li[@class = 'w10pct text-gothic' ][1]")).getText();
    }
    public void clickSolicitudCreada(String tramite){
        WebElementFacade tramite_object = find(By.xpath("//div[@class = 'titulo']// a[text() ='"+ tramite +"' ][1]"));
        tramite_object.click();
    }
    public void validarPrecio(String precioEsperado){
        String montoPagarValor=  MontoPagar.getText();
        System.out.println("--------------------------}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}");
        System.out.println(montoPagarValor);
        Assert.assertEquals("S/ " +precioEsperado, montoPagarValor);


    }
    public void validarMotivo(String MotivoEsperado){
        String motivoValor =  motivo.getText();
        System.out.println("--------------------------}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}");
        System.out.println(motivoValor);
        Assert.assertEquals(MotivoEsperado, motivoValor);
    }
    public void validarEstado(String EstadoEsperado){

        String estadoValor = estado.getText();
        System.out.println("--------------------------}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}");
        System.out.println(estadoValor);
        Assert.assertEquals(
                EstadoEsperado, estadoValor);
    }

    void scrollToElement(WebElementFacade objeto) {
        Actions actions = new Actions(driver);
        actions.moveToElement(objeto);
        actions.perform();
    }


    public void loginOut(){
        try {
            Thread.sleep(3000);
            waitFor(profileImage).shouldBeVisible();
            profileImage.click();
            waitFor(buttonCerrarSesion).shouldBeVisible();
            buttonCerrarSesion.click();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}

