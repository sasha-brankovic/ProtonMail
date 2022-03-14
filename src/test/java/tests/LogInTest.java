package tests;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.BasePage;
import pages.LogInPage;

import java.io.FileNotFoundException;

public class LogInTest extends BaseTest{
    @Test(testName = "LogIn Test")
    @Parameters({"userName", "password"})
    public void fillSignInForm(String userName, String password) throws FileNotFoundException {
        BasePage basePage = new BasePage(driver, wait);
        basePage.logIn();
        LogInPage logInPage = new LogInPage(driver, wait);
        logInPage.fillSignInForm(userName, password);
    }
}
