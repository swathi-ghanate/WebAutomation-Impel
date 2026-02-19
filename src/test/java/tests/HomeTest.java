package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.HomePage;

public class HomeTest extends BaseTest {

    @Test
    public void navigateToCreateComplaint() {
        HomePage homePage = new HomePage(page);
        homePage.navigateToCreateComplaint();
    }
}
