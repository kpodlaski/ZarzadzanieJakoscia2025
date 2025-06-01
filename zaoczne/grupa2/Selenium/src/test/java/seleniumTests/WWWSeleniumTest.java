package seleniumTests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WWWSeleniumTest {


    @Test
    public void basicTest() throws InterruptedException {
        //Test Based on:
        //https://www.selenium.dev/documentation/webdriver/getting_started/first_script/
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");
        driver.getTitle();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        Thread.sleep(500);
        WebElement textBox = driver.findElement(By.name("my-text"));
        WebElement submitButton = driver.findElement(By.cssSelector("button"));
        textBox.sendKeys("Selenium");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
        Thread.sleep(2000);
        submitButton.click();
        WebElement message = driver.findElement(By.id("message"));
        assertEquals("Received!",message.getText());
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
        Thread.sleep(2000);
        driver.quit();
    }

    @Test
    public void imageOverButton() throws InterruptedException {
        String url ="D:\\git\\Zajecia\\ZarzadzanieJakoscia2024-25\\zaoczne\\grupa2\\Selenium\\src\\test\\resources\\simple.html";
        WebDriver driver = new ChromeDriver();
        driver.get(url);
        WebElement message = driver.findElement(By.id("btn"));
        message.click();
        Thread.sleep(2000);
        driver.quit();

    }
}
