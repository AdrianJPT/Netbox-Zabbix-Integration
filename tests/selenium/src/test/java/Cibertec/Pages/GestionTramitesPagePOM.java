package Cibertec.Pages;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.junit.Assert;
import org.junit.Assume;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

public class GestionTramitesPagePOM extends PageObject {

    WebDriver driver = getDriver();
    @FindBy(id = "Div_TipoPrograma")
    WebElementFacade comboboxTipoModalidad;
    @FindBy(id = "Div_ProgramaPregrado")
    WebElementFacade comboboxtipoPrograma;
    @FindBy(id = "Div_TramitePregrado")
    WebElementFacade comboboxtipoTramite;

    @FindBy(id = "Div_ModalidadPago")
    WebElementFacade comboboxformaPago;

    @FindBy(id = "txtMovil2")
    WebElementFacade alumno;
    @FindBy(id = "txtCorreo")
    WebElementFacade correo;

    @FindBy(id = "uploaders")
    WebElementFacade tittle_AdjuntarUnArchivo;
    @FindBy(xpath = "//input[@type='file' and @id = 'fileToUpload_SolicitudTramitesGenerales']")
    WebElementFacade buttonCargarArchivo;
    @FindBy(id = "txtMotivo")
    WebElementFacade motivo;

    @FindBy(xpath = "//div[@id = 'DivEnviarSolicitudPregrado'] /input[ @type = 'button' and @value='Enviar Solicitud']")
    WebElement botonEnviarSolicitud;

    //      AVISOS
    @FindBy(id= "DetalleAvisosSolicitudTramitesGenerales")
    WebElementFacade tittle_Avisos;


    //@FindBy(xpath = "//div[ @class = 'col-xs-10 col-sm-11'][1]")
    @FindBy(xpath = "//div[@id='DetalleAvisosSolicitudTramitesGenerales']/div/div/div/div/div/div/div[1]/ul/li/div[2]")

    WebElementFacade avisoDefinicionTxt;

    @FindBy(xpath = "//div[@id='DetalleAvisosSolicitudTramitesGenerales']/div/div/div/div/div/div/div[2]/ul/li/div[2]")
    WebElementFacade avisoTiempoTxt;
    @FindBy(xpath = "//div[@id='DetalleAvisosSolicitudTramitesGenerales']/div/div/div/div/div/div/div[3]/ul/li/div[2]")
    WebElementFacade avisoModalidadTxt;

    @FindBy(xpath = "//div[@id='DetalleAvisosSolicitudTramitesGenerales']/div/div/div/div/div/div/div[4]/ul/li/div[2]")
    WebElementFacade avisoPrerequisitosTxt;

    @FindBy(xpath = "//div[ @class = 'col-xs-10 col-sm-11'][1]")
    WebElementFacade avisoDefinicionImg;

    @FindBy(xpath = "//img[ @class = 'icon-sol'][2]")
    WebElementFacade avisoTiempoImg;

    @FindBy(xpath = "//img[ @class = 'icon-sol'][3]")
    WebElementFacade avisoModalidadImg;

    @FindBy(xpath = "//img[ @class = 'icon-sol'][4]")
    WebElementFacade avisoPrerequisitosImg;

    public void debug_states(){
        waitFor(avisoDefinicionTxt).shouldBeVisible();
        System.out.println("]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]}}}}");
        System.out.println(avisoDefinicionTxt.getText());
        //System.out.println(avisoModalidadTxt.getText());
    }

    void scrollToElement(WebElementFacade objeto) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
        jsExecutor.executeScript("arguments[0].scrollIntoView({block: 'start', behavior: 'instant'});", objeto);

    }
    public void seleccionarOpcionCombobox(String opcion) {
        WebElementFacade combobox = comboboxTipoModalidad.find(By.xpath("//label[text()='" + opcion + "']"));
        combobox.click();
    }
    public void  seleccionarPosicicionOpcionCombobox(){
        comboboxtipoPrograma.click();
        WebElementFacade combobox = find(By.xpath("//div[@id='Div_ProgramaPregrado']//ul[@class='options']/li[2]"));
        System.out.println(comboboxtipoPrograma);
        combobox.click();
    }
    public void cargarArchivo(String rutaArchivo) throws InterruptedException {
        File file = new File(rutaArchivo);
        String path = file.getAbsolutePath();
        find(By.xpath("//input[@type='file' and @id = 'fileToUpload_SolicitudTramitesGenerales']")).sendKeys(path);



        // Esperar hasta que el elemento de carga del archivo desaparezca
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='uploaders']//div[contains(@class, 'upload-progress-bar')]")));

        // Esperar un tiempo adicional para asegurarse de que la carga haya finalizado completamente
        Thread.sleep(2000);
    }

    public void clickEnviarSolicitud(){


        botonEnviarSolicitud.click();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void validarAvisosTexto( WebElementFacade AvisoTxt, String texto){

        try {
            if (AvisoTxt.isPresent() && AvisoTxt.isVisible()) {
                Assert.assertTrue("El texto no coincide parcialmente" + AvisoTxt.getText(), AvisoTxt.getText().contains(texto));
            } else {
                throw new AssertionError("El elemento TXT no es visible");
            }
        } catch (AssertionError e) {
            // Agrega un reporte de error en Serenity
            throw new AssertionError("Error TXT inesperado: " + e.getMessage(), e);
        }
    }

    public void validarAvisosImagen( WebElementFacade AvisoImg){
        try {
            if(AvisoImg.isVisible()) {
                System.out.println("El elemento SI es visible");
            }
            else {
                throw new AssertionError("El elemento IMG no es visible");
            }

        } catch (Exception e) {
            throw new AssertionError("Error IMG inesperado: " + e.getMessage(), e);
        }
    }
}
