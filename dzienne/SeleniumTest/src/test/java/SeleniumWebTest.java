import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeleniumWebTest
{

    WebDriver driver;

    @BeforeEach
    public void setUp()
    {
        driver = new ChromeDriver();
    }

    @Test
    public void simpleTest() throws InterruptedException {
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");
        driver.getTitle();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        WebElement textBox = driver.findElement(By.name("my-text"));
        WebElement submitButton = driver.findElement(By.cssSelector("button"));
        textBox.sendKeys("Selenium");
        Thread.sleep(3000);
        submitButton.click();
        WebElement message = driver.findElement(By.id("message"));
        String text = message.getText();
        assertEquals("Received!", text);
        driver.quit();
    }
}
