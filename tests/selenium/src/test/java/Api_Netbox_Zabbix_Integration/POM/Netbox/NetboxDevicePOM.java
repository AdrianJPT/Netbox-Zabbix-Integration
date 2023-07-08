package Api_Netbox_Zabbix_Integration.POM.Netbox;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NetboxDevicePOM extends PageObject {

    WebDriver driver = getDriver();
    @FindBy(xpath = "//input[@title='Toggle All']")
    public WebElement selectAllDevices;

    @FindBy(xpath = "//button[@name='_delete']")
    public WebElement deleteAllDevices;



    @FindBy(xpath = "//a[@class='btn btn-sm btn-danger']")
    public WebElement deleteDeviceButton;

    public WebElement deviceNetBox(String DeviceName){
       return driver.findElement(By.xpath("//a[normalize-space()='" + DeviceName+ "']")) ;
    }

}
