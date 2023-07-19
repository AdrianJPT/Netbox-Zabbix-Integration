package Api_Netbox_Zabbix_Integration.Manage;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ManageDriver extends PageObject {

    public WebDriver driver = getDriver();

    public static List<String> TABS = new ArrayList<>();

    public String NetboxTAB;
    public static String ZabbixTAB = "";


    public void Syncchronization(){
        // The wait time for the page to be fully loaded
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));

        // The wait time for each element
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    public void ExplicitWait(WebDriver driver, int timeout, WebElement element){
        new WebDriverWait(driver, Duration.ofSeconds(timeout)).
                until( ExpectedConditions.visibilityOf( element));
    }
    public void WindowPageMaximize(){
        // MANAGE
        driver.manage().window().maximize();// -- maximize the current window
        driver.manage().deleteAllCookies();

    }

    public void ScrollToElement(WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("arguments[0].scrollIntoView();", element);
    }

    public String createNewTab(WebDriver getDriver){
        // add Netbox Handle to the LIST
        NetboxTAB = getDriver.getWindowHandle();
        TABS.add(NetboxTAB);

        //open a new TAB
        ((JavascriptExecutor)driver).executeScript("window.open()");

        // get ALL the windows handles
        Set<String> allWindowHandles = driver.getWindowHandles();

        String handleNewTAB = "";

        for (String handle : allWindowHandles)
        {
            if (!TABS.contains(handle))
            {
                handleNewTAB = handle;
                TABS.add(handleNewTAB);
            }
        }

        // Change the focus to the Tab created
        driver.switchTo().window(handleNewTAB);

        int index = TABS.size() - 1;
        return TABS.get(index);
    }


    public void changeTab(WebDriver get_driver ,String TAB){
        //change TAB
        get_driver.switchTo().window(TAB);



    }


}
