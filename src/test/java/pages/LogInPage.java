package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class LogInPage extends BasePage{
    String usernameFromFile;

    public LogInPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }
    public void fillSignInForm(String userName, String password) throws FileNotFoundException {
        readUsername();
        if(userName != null){
            userName = usernameFromFile;
        }
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[type='submit']")));
        WebElement inputUserName = webElement("#username");
        typeText(inputUserName, userName);

        WebElement inputPassword = webElement("#password");
        typeText(inputPassword, password);

        WebElement buttonSignIn = webElement("[type='submit']");
        clickOnElement(buttonSignIn);

        if(userName == null || password == null){
            assertionOnEmptyLogInFields(userName, password);
        }else{
            checkCredentials(userName);
        }
    }
    private void assertionOnEmptyLogInFields(String userName, String password){
        if(userName == null){
            WebElement inputUserNameValidationText = webElement("#id-2>span");
            Assert.assertEquals(inputUserNameValidationText.getText(), "This field is required");
        } else if(password == null){
            WebElement inputPasswordValidationText = webElement("#id-3>span");
            Assert.assertEquals(inputPasswordValidationText.getText(), "This field is required");
        }
    }
    private void checkCredentials(String userName){
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        List<WebElement> notificationDangerList = driver.findElements(By.cssSelector("[role='alert'].notification-danger"));
        if(notificationDangerList.size() > 0){
            WebElement invalidCredentials = webElement("[role='alert'].notification-danger");
            Assert.assertEquals(invalidCredentials.getText(), "Incorrect login credentials. Please try again");
        }else{
            wait.until(ExpectedConditions.visibilityOf(webElement(".user-dropdown-text>span:nth-child(2)")));
            Assert.assertEquals(webElement(".user-dropdown-text>span:nth-child(2)").getText(), userName+"@protonmail.com");
        }
    }
    private void readUsername() throws FileNotFoundException {
        File usernameFile = new File("username/username.txt");
        Scanner reader = new Scanner(usernameFile);
        while (reader.hasNextLine()) {
            usernameFromFile = reader.nextLine();
        }
        reader.close();
    }
}
