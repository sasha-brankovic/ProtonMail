package tests;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.BasePage;
import pages.SignUpPage;

import java.io.IOException;

public class SignUpTest extends BaseTest{
    @Test(testName = "SignUp Test with correct filled form")
    @Parameters({"userName", "password", "repeatedPassword"})
    public void fillSignUpForm(String userName, String password, String repeatedPassword) throws InterruptedException, IOException {
        BasePage basePage = new BasePage(driver, wait);
        basePage.signUp();
        SignUpPage signUpPage = new SignUpPage(driver, wait);
        signUpPage.fillSignUpForm(userName, password, repeatedPassword);
        signUpPage.assertionsOnCreateYourProtonAccountFields(userName, password, repeatedPassword);
    }
}
