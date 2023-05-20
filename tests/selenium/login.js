package package_selenium1;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class class_selenium1 {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver","C:\\VS Code\\selenium\\chrome driver\\chromedriver.exe");
        var urlbase = "http://192.168.40.50:3000/";

        WebDriver driver = new ChromeDriver();


        // MANAGE
        driver.manage().window().maximize();// -- maximize the current window
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS );
        driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS);

        driver.get(urlbase);

        //
        driver.findElement(By.cssSelector(".content-container .mdi.mdi-login-variant")).click();
        // username:
        driver.findElement(By.cssSelector("input#id_username")).sendKeys("admin");
        // contraseñan
        driver.findElement(By.cssSelector("input#id_password")).sendKeys("admin");
        // localizar el elemento y sing in
        driver.findElement(By.cssSelector("button.btn.btn-primary.btn-lg.w-100.mt-4")).click();


        //DEVICES
        driver.findElement(By.xpath(".//span[text()='Devices']")).click();
        //Crear devices
        driver.findElement(By.cssSelector("a.btn.btn-sm.btn-green.lh-1[href='/dcim/devices/add/']")).click();
        // nombre
        driver.findElement(By.cssSelector("input#id_name")).sendKeys("Selenium_Device");
        // Device Role
        driver.findElement(By.xpath("//div[@class = 'ss-single-selected'] //span[text()='---------']")).click();

        Thread.sleep(1000);// esperar un 1s

            // seleccionar
        driver.findElement(By.xpath("//div[@class = 'ss-option' and text()='device_role' and @role='option']")).click();

        //WebElement hijo = driver.findElement(By.cssSelector(".btn.btn-primary.ws-nowrap .clase-hijo"));
    }
}
