package Api_Netbox_Zabbix_Integration.POM.Netbox;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NetboxDeviceConfigPOM extends PageObject {

    WebDriver driver = getDriver();
    @FindBy(id = "id_name")
    public WebElement DeviceName;

    @FindBy(xpath = "//div[@class = 'col']//select[@id = 'id_device_role']/following-sibling::div")
    public WebElement DeviceRoleBox;

    @FindBy(xpath = "//div[@class = 'col']//select[@id = 'id_device_type']/following-sibling::div")
    public WebElement DeviceType;

    @FindBy(xpath = "//div[@class = 'col']//select[@id = 'id_site']/following-sibling::div")
    public WebElement Site;

    @FindBy(xpath = "//div[@class = 'col']//select[@id = 'id_platform']/following-sibling::div")
    public WebElement Platform;

    @FindBy(name = "_create")
    public WebElement ButtonCreateDevice;

    public void selectDeviceRole(String deviceRole){
        DeviceRoleBox.findElement(By.xpath("//div[@class = 'ss-option' and text()='"+ deviceRole +"' and @role='option']")).click();
    }

    public void selectDeviceType(String deviceType){
        DeviceType.findElement(By.xpath("//div[@class = 'ss-option' and text()='"+ deviceType +"' and @role='option']")).click();
    }

    public void selectSite(String site) {
        Site.findElement(By.xpath("//div[@class = 'ss-option' and text()='"+ site +"' and @role='option']")).click();
    }

    public void selectPlatform(String platform) {
        Platform.findElement(By.xpath("//div[@class = 'ss-option' and text()='"+ platform +"' and @role='option']")).click();
    }
}
