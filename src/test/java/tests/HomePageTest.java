package tests;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.BasePage;
import pages.HomePage;
import pages.LogInPage;

import java.io.FileNotFoundException;

public class HomePageTest extends BaseTest{
    @Test(testName = "Sending Email Test")
    @Parameters({"userName", "password", "emailOfRecipient", "emailSubject", "emailContent"})
    public void testBasicFunctionalityOnHomePage(String userName, String password, String emailOfRecipient, String emailSubject, String emailContent) throws FileNotFoundException, InterruptedException {
        BasePage basePage = new BasePage(driver, wait);
        basePage.logIn();
        LogInPage logInPage = new LogInPage(driver, wait);
        logInPage.fillSignInForm(userName, password);
        HomePage homePage = new HomePage(driver, wait);
        homePage.sendEmail(emailOfRecipient, emailSubject, emailContent);
        homePage.LogOut();
    }

}
