package Netbox.POM;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ZabbixHostsPOM extends PageObject {

    WebDriver driver = getDriver();
    @FindBy(xpath = "//button[text()='Reset']")
    public WebElement resetButton;

    @FindBy(id = "filter_host")
    public WebElement nameField;

    @FindBy(id = "filter_set")
    public WebElement applyButton;


    public boolean HostFound(String Hostname){
        try {
            WebElement zabbixHostFound = driver.findElement(By.xpath("// table[@class = 'list-table' ] // a[text() = '" + Hostname + "' ]"));
            return zabbixHostFound.isDisplayed();
        } catch(Exception e) {
            return false;
        }
    }

}
