package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SignUpPage extends BasePage{
    private String userName;
    private final String USERNAMESUFFIX = "_"+System.currentTimeMillis();

    public SignUpPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }
    public void fillSignUpForm(String userName, String password, String repeatedPassword) {
        this.userName = userName;
        WebElement selectFreeAccount = webElement("[href='#plan-free']");
        clickOnElement(selectFreeAccount);

        WebElement selectFreePlan = webElement("#freePlan");
        clickOnElement(selectFreePlan);

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[type='submit']")));
        switchToIFrameByIndex(0);
        WebElement inputUserName = webElement("#username");
        if(userName != null){
            typeText(inputUserName, userName+USERNAMESUFFIX);
        }else{
            System.out.println("Sent text on the input: "+inputUserName.getAttribute("id")+", was null!");
        }
        switchFromIFrame();

        WebElement inputPassword = webElement("#password");
        typeText(inputPassword, password);

        WebElement inputRepeatPassword = webElement("#repeat-password");
        typeText(inputRepeatPassword, repeatedPassword);

        WebElement buttonNext = webElement("[type='submit']");
        clickOnElement(buttonNext);
    }
    public void continueWithSignUp() throws InterruptedException, IOException {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[name='recoveryForm']>button:nth-last-child(1)")));
        WebElement buttonSkipAddRecoveryMethod = webElement("[name='recoveryForm']>button:nth-last-child(1)");
        clickOnElement(buttonSkipAddRecoveryMethod);

        WebElement buttonConfirmSkipAddRecoveryMethod = webElement(".modal-two-dialog-container>div:nth-last-child(1)>button:nth-last-child(2)");
        clickOnElement(buttonConfirmSkipAddRecoveryMethod);

        WebElement buttonSelectPlan = webElement("[aria-describedby='desc_Free']");
        clickOnElement(buttonSelectPlan);

        // On this part we must do manual manipulation with Captcha or if Captcha not available, should choose Email or Phone verification
        // When I started with automation Captcha was available, but after two attempts it has turned off
        // Thread.sleep() is here only for finish Captcha
        Thread.sleep(45000);


        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("footer>button")));
        WebElement buttonGetStartedCustomization = webElement("footer>button");
        clickOnElement(buttonGetStartedCustomization);
        clickOnElement(buttonGetStartedCustomization);

        WebElement selectTheme = webElement(".theme-modal-list>:nth-child(4)");
        clickOnElement(selectTheme);
        clickOnElement(buttonGetStartedCustomization);
        clickOnElement(buttonGetStartedCustomization);
        // Assertion: entered email is match with email on home page
        Assert.assertEquals(webElement(".user-dropdown-text>span:nth-child(2)").getText()
                .replaceAll("@protonmail.com", ""), userName+USERNAMESUFFIX);
        // The code below is just for write credentials because username is given dynamically, to be used after for login
        String usernameCredential = webElement(".user-dropdown-text>span:nth-child(2)").getText()
                .replaceAll("@protonmail.com", "");
        FileWriter username = new FileWriter("username/username.txt");
        username.write(usernameCredential);
        username.close();
    }
    public void assertionsOnCreateYourProtonAccountFields(String userName, String password, String repeatedPassword) throws IOException, InterruptedException {
        switchToIFrameByIndex(0);
        List<WebElement> listOfValidationTextsOnUsername = driver.findElements(By.cssSelector("#id-1>span"));
        switchFromIFrame();
        if(listOfValidationTextsOnUsername.size() > 0){
            switchToIFrameByIndex(0);
            if(userName == null){
                WebElement inputUserNameValidationText = webElement("#id-1>span");
                Assert.assertEquals(inputUserNameValidationText.getText(), "This field is required");
            }else{
                WebElement inputUserNameValidationText = webElement("#id-1>span");
                Assert.assertEquals(inputUserNameValidationText.getText(), "Username already used");
                System.out.println("Username already used");
            }
            switchFromIFrame();
        }else if(password == null){
            WebElement inputPasswordValidationText = webElement("#id-2>span");
            Assert.assertEquals(inputPasswordValidationText.getText(), "Password must contain at least 8 characters");
        }else if (repeatedPassword == null){
            WebElement inputPasswordValidationText = webElement("#id-3>span");
            Assert.assertEquals(inputPasswordValidationText.getText(), "Password must contain at least 8 characters");
        }
        else if (!password.equals(repeatedPassword)){
            WebElement inputPasswordValidationText = webElement("#id-3>span");
            Assert.assertEquals(inputPasswordValidationText.getText(), "Passwords do not match");
        }else{
            continueWithSignUp();
        }
    }
}
