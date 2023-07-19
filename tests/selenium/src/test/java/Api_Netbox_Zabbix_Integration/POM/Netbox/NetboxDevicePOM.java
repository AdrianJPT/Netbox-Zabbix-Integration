package Api_Netbox_Zabbix_Integration.POM.Netbox;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NetboxDevicePOM extends PageObject {

    WebDriver driver = getDriver();

    @FindBy(css = "a[class='btn btn-sm btn-success']")
    public WebElement deviceButtonADD;
    @FindBy(css = "input[title='Toggle All']")
    public WebElement selectAllDevices;

    @FindBy(css = "button[name='_delete']")
    public WebElement deleteAllDevices;

    @FindBy(css = "button[name='_confirm']")
    public WebElement deleteAllDevicesConfirm;


    @FindBy(xpath = "//a[@class='btn btn-sm btn-danger']")
    public WebElement deleteDeviceButton;

    @FindBy(xpath = "//button[contains(text(),'Delete')]")
    public WebElement deleteDeviceButtonConfirmation;
    public WebElement deviceNetBox(String DeviceName){
       return driver.findElement(By.xpath("//a[normalize-space()='" + DeviceName+ "']")) ;
    }

}
