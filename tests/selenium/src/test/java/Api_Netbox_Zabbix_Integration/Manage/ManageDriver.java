package Api_Netbox_Zabbix_Integration.Manage;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Set;

public class ManageDriver extends PageObject {

    //Array of Tabs
    public String[] tabs = new String[3];
    public String NetboxTAB = tabs[0];
    public String ZabbixTAB;

    WebDriver driver = getDriver();
    public void WindowPageMaximize(){
        // MANAGE
        driver.manage().window().maximize();// -- maximize the current window
        driver.manage().deleteAllCookies();

    }

    public void ScrollToElement(WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", element); // Until this element is visible

    }

    public String createNewTab(){
        //open a a new TAB
        ((JavascriptExecutor)driver).executeScript("window.open()");
        Set<String> handlesSet = driver.getWindowHandles();

        // Send the actual TAB the the array
        tabs = handlesSet.toArray(new String[handlesSet.size()]);

        // Change the focus to the Tab created
        driver.switchTo().window(tabs[tabs.length-1]);
        return tabs[tabs.length-1];
    }

    public void changeTab(String TAB){
        //change TAB
        driver.switchTo().window(TAB);

    }


}
