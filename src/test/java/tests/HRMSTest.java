package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HRMSPage;
import utils.ConfigReader;
import utils.NavigationHelper;
import utils.ScreenshotHelper;

public class HRMSTest extends BaseTest {

    @Test
    public void createNewEmployee() {
        ScreenshotHelper screenshot = new ScreenshotHelper(page);

        NavigationHelper nav = new NavigationHelper(page);
        HRMSPage hrmsPage = nav.goToCreateEmployee();
        screenshot.take("01_hrms_form_opened");

        // All values come from config.properties
        hrmsPage.fillLoginDetails(
                ConfigReader.getEmpUsername(),
                ConfigReader.getEmpPassword());
        screenshot.take("02_login_details_filled");

        hrmsPage.fillPersonalDetails(
                ConfigReader.getEmpName(),
                ConfigReader.getEmpMobile(),
                ConfigReader.getEmpDob(),
                ConfigReader.getEmpEmail(),
                ConfigReader.getEmpAddress());
        screenshot.take("03_personal_details_filled");

        hrmsPage.fillEmployeeDetails();
        screenshot.take("04_employee_details_filled");

        hrmsPage.submitForm();
        screenshot.take("05_form_submitted");

        boolean success = hrmsPage.isEmployeeCreatedSuccessfully();
        if (success) {
            screenshot.takeOnSuccess("createNewEmployee");
            System.out.println("New Employee ID: " + hrmsPage.getEmployeeId());
        } else {
            screenshot.takeOnFailure("createNewEmployee");
        }

        Assert.assertTrue(success, "Employee was not created successfully");
    }
}