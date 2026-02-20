package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.HRMSPage;

public class HRMSTest extends BaseTest {

    @Test
    public void createNewEmployee() {
        HomePage homePage = new HomePage(page);
        homePage.navigateToCreateUser();

        HRMSPage hrmsPage = new HRMSPage(page);

        hrmsPage.fillLoginDetails("smc-oy-hd-test02", "eGov@1234");

        hrmsPage.fillPersonalDetails(
                "Test Employee",
                "98765432101",
                "1995-01-01",
                "test@test.com",
                "Test Address");

        hrmsPage.fillEmployeeDetails();

        hrmsPage.submitForm();

        Assert.assertTrue(
                hrmsPage.isEmployeeCreatedSuccessfully(),
                "Employee was not created successfully");

        System.out.println("New Employee ID: " + hrmsPage.getEmployeeId());
    }
}
