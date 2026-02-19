package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;

public class LoginTest extends BaseTest {

    @Test
    public void verifyUserCanLogin() {
        HomePage homePage = new HomePage(page);
        Assert.assertTrue(homePage.isHomeVisible(), "Home element is not visible after login");
    }
}
