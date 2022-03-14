package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class HomePage extends BasePage{
    public HomePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }
    public void sendEmail(String emailOfRecipient, String emailSubject, String emailContent) throws InterruptedException {

        WebElement buttonNewMessage = webElement(".button-large.button-solid-norm");
        clickOnElement(buttonNewMessage);

        WebElement inputEnterRecipientEmailAddress = webElement("[placeholder='Email address']");
        typeText(inputEnterRecipientEmailAddress, emailOfRecipient);

        WebElement inputEnterEmailSubject = webElement("[placeholder='Subject']");
        typeText(inputEnterEmailSubject, emailSubject);

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".button-group.editor-toolbar"))));
        switchToIFrameByIndex(0);
        WebElement inputEmailContent = webElement("#rooster-editor>:nth-child(1)");
        typeText(inputEmailContent, emailContent);
        switchFromIFrame();

        WebElement buttonSendEmail = webElement("[class='button button-group-item button-ghost-weak composer-send-button']");
        clickOnElement(buttonSendEmail);

        wait.until(ExpectedConditions.visibilityOf(webElement("[role='alert']>span")));
        WebElement alertEmailSent = webElement("[role='alert']>span");
        Assert.assertEquals(alertEmailSent.getText(), "Message sent.");
    }
    public void LogOut(){
        WebElement buttonMenuForLogOut = webElement("[data-testid='heading:userdropdown']");
        clickOnElement(buttonMenuForLogOut);

        WebElement buttonLogOut = webElement("[data-testid='userdropdown:button:logout']");
        clickOnElement(buttonLogOut);

        wait.until(ExpectedConditions.visibilityOf(webElement("[class='sign-layout-title text-center mt1 mb0-5']>strong")));
        WebElement textOnLogInForm = webElement("[class='sign-layout-title text-center mt1 mb0-5']>strong");
        Assert.assertEquals(textOnLogInForm.getText(), "Sign in");
    }
}
