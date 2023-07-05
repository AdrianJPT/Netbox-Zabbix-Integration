package Cibertec.Pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

import org.junit.Assert;
import org.junit.Assume;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;


public class MainPagePOM extends PageObject {

    @FindBy(xpath = "//div[@id = 'menu'] // a")
    WebElementFacade buttonMisTramites;
    @FindBy(xpath = "//a[text() = 'BANDEJA DE USUARIO']")
    WebElementFacade buttonBandejaEvaluador;

    @FindBy(xpath = "//a[contains(text(),'Solicitud Tramites Generales')]")
    WebElementFacade linkSolicitudTramitesGenerales;

    @FindBy(xpath = "//h3[@class= 'vigente'] and contains(text(),'Solicitud Tramites Generales')")
    WebElementFacade tittleSolicitudTramitesGenerales;

    WebDriver driver = getDriver();
     void scrollToElement(WebElementFacade objeto) {
        Actions actions = new Actions(driver);
        actions.moveToElement(objeto);
        actions.perform();
    }


    public void tittleSolicitudTramitesGeneralesEsVisible(WebElementFacade webelement){


        try {
            boolean estatus = webelement.isVisible();
            Assume.assumeTrue("Mensaje de error si la condición no se cumple",estatus );
            // Más acciones de prueba que se ejecutan si la condición se cumple
        } catch (Exception e) {
            // Capturar la excepción para mostrar el error en el informe
            throw new AssertionError("Error inesperado: " + e.getMessage(), e);
        }
    }

    public void tituloDelTramite( WebElementFacade Tittle, String texto){
        try {
            if (Tittle.isPresent()) {
                Assert.assertTrue("El texto no coincide parcialmente", Tittle.getText().contains(texto));
            } else {
                throw new AssertionError("El elemento no es visible");
            }
        } catch (AssertionError e) {
            // Agrega un reporte de error en Serenity
            throw new AssertionError("Error inesperado: " + e.getMessage(), e);
        }
    }

}
