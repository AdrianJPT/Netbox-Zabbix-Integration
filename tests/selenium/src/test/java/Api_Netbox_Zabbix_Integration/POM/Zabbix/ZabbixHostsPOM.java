package Api_Netbox_Zabbix_Integration.POM.Zabbix;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import org.eclipse.jetty.util.ssl.X509;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertTrue;

public class ZabbixHostsPOM extends PageObject {

    WebDriver driver = getDriver();
    @FindBy(xpath = "//button[text()='Reset']")
    public WebElement resetButton;

    @FindBy(id = "filter_host")
    public WebElement nameField;

    @FindBy(name = "filter_set")
    public WebElement applyButton;

    @FindBy(xpath = "//body[1]/div[1]/main[1]/form[1]/table[1]/tbody[1]/tr[1]/td[2]/a")
    public WebElement zabbixHost;

    @FindBy(xpath = "//input[@id='host']")
    public  WebElement zabbixHostName;

    @FindBy(css = "td[class='wordwrap'] a[target='_blank']")
    public WebElement zabbixTemplate;
    @FindBy(xpath = "//span[@class = 'subfilter-enabled']//span[1]")
    public  WebElement zabbixHostgroup;

    @FindBy(xpath = "//label[@for='interfaces_81_useip_0']")
    public WebElement dnsButton;


    public WebElement dnsNameField = driver.findElement(By.xpath("//input[@id='interfaces_81_dns']"));


    @FindBy(xpath = "//input[@id='interfaces_44_port']")
    public WebElement portField;

    @FindBy (xpath = "/html[1]/body[1]/div[1]/main[1]/form[1]/table[1]/tbody[1]/tr[1]/td[8]")
    public WebElement interfaceZabbixHost;
    @FindBy(xpath = "//div[@class='dashboard-widget-head']//button[@title='Close']")
    public WebElement closeHostWindow;

    // Delete ALL devices
    @FindBy(id = "all_hosts")
    public WebElement selectAllHosts;

    @FindBy(xpath = "//button[normalize-space()='Delete']")
    public WebElement deleteButton;

    public void validateDnsButton(){
        WebElement radioButton = dnsButton;
        String checkedValue = radioButton.getAttribute("checked");

        assertTrue("is not selected!!", checkedValue != null && checkedValue.equalsIgnoreCase("true"));
    }
}
