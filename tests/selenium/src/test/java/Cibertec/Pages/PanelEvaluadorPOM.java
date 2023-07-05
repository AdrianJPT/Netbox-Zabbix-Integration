package Cibertec.Pages;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import javax.xml.xpath.XPath;

public class PanelEvaluadorPOM extends PageObject {

    WebDriver driver = getDriver();

    @FindBy(xpath = "//div[@id = 'menu'] // a")
    WebElementFacade buttonMisTramites;

    @FindBy(xpath = "//a[text() = 'BANDEJA DEL EVALUADOR']")
    WebElementFacade buttonBandejaEvaluador;

    @FindBy(id = "txtBuscar")
    WebElementFacade textoBuscar;
    @FindBy(id = "btnBuscarEvaluaciones")
    WebElementFacade buttonBuscar;

    @FindBy(xpath = "//a[text() = 'Evaluar']")
    WebElementFacade buttonEvaluar;

    @FindBy(xpath = "//textarea[contains(@id, 'txt-')]")
    WebElementFacade observacionEvaluador;

    @FindBy(xpath = "//button[contains(@id,'eval-aprobar')]")
    WebElementFacade buttonAprobar;

    @FindBy(xpath = "//button[contains(@id,'eval-rechazar')]")
    WebElementFacade buttonRechazar;

    @FindBy(xpath = "//button[contains(@id,'ModalAceptar')]")
    WebElementFacade buttonAceptar;


    @FindBy(xpath = "//button[contains(@id,'ModalCancelar')]")
    WebElementFacade buttonCancelar;


    public void ingresarBandejaEvaluador(){
        buttonMisTramites.click();
        buttonBandejaEvaluador.click();
    }
    public void buscarTramiteID(String id){
        textoBuscar.sendKeys(id);
        buttonBuscar.click();
    }
    public void abrirTramite(String tramite){
        WebElementFacade link_tramite = find(By.xpath("//a[text() = '"+ tramite +"']"));
        link_tramite.click();
    }

    public void aprobarTramite(String observacion_evaluador){

        buttonEvaluar.click();
        observacionEvaluador.sendKeys(observacion_evaluador);
        buttonAprobar.click();
        buttonAceptar.click();
    }
    public void rechazarTramite(String observacion_evaluador){
        buttonEvaluar.click();
        observacionEvaluador.sendKeys(observacion_evaluador);
        scrollDown();

        waitFor(buttonRechazar).shouldBeEnabled();
        buttonRechazar.click();
        waitFor(buttonAceptar).shouldBeEnabled();
        buttonAceptar.click();
    }

    void scrollToElement(WebElementFacade objeto) {

        Actions actions = new Actions(driver);
        actions.moveToElement(objeto);
        actions.perform();
    }

    void scrollDown(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

    }

}
