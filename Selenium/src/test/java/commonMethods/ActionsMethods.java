package commonMethods;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ActionsMethods extends Waits {
    protected Actions actions;
    protected void moveToElementAndClick(WebDriver driver, WebElement element){
        actions =  new Actions(driver);
        actions.moveToElement(element).click().perform();
    }

}
