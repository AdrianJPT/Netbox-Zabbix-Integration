package Api_Netbox_Zabbix_Integration.POM.Zabbix;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.WebElement;

public class ZabbixMainPOM extends PageObject {

    @FindBy(xpath = "// a[@class = 'icon-data-collection']")
    public WebElement barDataColletion;
    @FindBy(xpath = "//li[@id = 'config'] // ul[@class = 'submenu']// a[ text() = 'Hosts']")
    public WebElement barHosts;

}
