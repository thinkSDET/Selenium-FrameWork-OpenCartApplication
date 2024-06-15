package commonMethods;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Waits {
    Wait wait;
    protected static void implicitWait(WebDriver driver){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    protected void visibilityOfElement(WebDriver driver, WebElement element){
        wait = new WebDriverWait(driver,Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOf(element));
    }
    protected void elementTBeClickAble(WebDriver driver,WebElement element){
        wait = new WebDriverWait(driver,Duration.ofSeconds(8));
        wait.until(ExpectedConditions.elementToBeClickable(element));

    }
}
