package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
    WebDriver driver;
    WebDriverWait wait;

    public BasePage(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
    }
    public void signUp(){
        WebElement buttonSignUp = webElement("li>[href='signup']");
        clickOnElement(buttonSignUp);
    }
    public void logIn() {
        WebElement buttonLogIn = webElement(".action>[href='https://mail.protonmail.com/login']");
        clickOnElement(buttonLogIn);
    }
    protected void clickOnElement(WebElement element) {
        waitUntilElementIsInteractive(element);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).build().perform();
        element.click();
    }
    public void typeText(WebElement element, String text){
        waitUntilElementIsInteractive(element);
        if(text != null) {
            element.clear();
            element.sendKeys(text);
        } else {
            System.out.println("Sent text on the input: "+element.getAttribute("id")+", was null!");
        }
    }
    private void waitUntilElementIsInteractive(WebElement element){
        wait.until(ExpectedConditions.visibilityOf(element));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    public WebElement webElement(String elementSelector){
        return driver.findElement(By.cssSelector(elementSelector));
    }
    public void switchToIFrameByIndex(int iFrameNumber){
        driver.switchTo().frame(iFrameNumber);
    }
    public void switchFromIFrame(){
        driver.switchTo().parentFrame();;
    }
}
