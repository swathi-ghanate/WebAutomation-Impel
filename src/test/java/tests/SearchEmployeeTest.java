package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.SearchEmployeePage;
import utils.ConfigReader;
import utils.NavigationHelper;
import utils.ScreenshotHelper;

public class SearchEmployeeTest extends BaseTest {

    // ---------------------------------------------------------------
    // Test Case 1 — Edit Employee
    // ---------------------------------------------------------------
    @Test
    public void editEmployee() {
        ScreenshotHelper screenshot = new ScreenshotHelper(page);
        NavigationHelper nav = new NavigationHelper(page);
        SearchEmployeePage searchPage = nav.goToSearchEmployee();
        screenshot.take("edit_01_search_page");

        searchPage.searchEmployee(ConfigReader.get("search.emp.id"));
        screenshot.take("edit_02_search_results");

        searchPage.openEmployeeResult();
        screenshot.take("edit_03_employee_profile");

        searchPage.openTakeActionMenu();
        screenshot.take("edit_04_take_action_menu");

        searchPage.clickEditEmployee();
        screenshot.take("edit_05_edit_form_opened");

        searchPage.editEmployeeName();
        screenshot.take("edit_06_name_updated");

        searchPage.saveEmployeeEdit();
        screenshot.take("edit_07_saved");

        boolean success = searchPage.isSuccessMessageVisible("Employee Details Updated Successfully");
        if (success) {
            screenshot.takeOnSuccess("editEmployee");
        } else {
            screenshot.takeOnFailure("editEmployee");
        }

        Assert.assertTrue(success, "Edit Employee: success message not found");

        searchPage.goBackToHome();
    }

    // ---------------------------------------------------------------
    // Test Case 2 — Edit Campaign Assignment
    // ---------------------------------------------------------------
    @Test
    public void editCampaignAssignment() {
        ScreenshotHelper screenshot = new ScreenshotHelper(page);
        NavigationHelper nav = new NavigationHelper(page);
        SearchEmployeePage searchPage = nav.goToSearchEmployee();
        screenshot.take("campaign_01_search_page");

        searchPage.searchEmployee(ConfigReader.get("search.emp.id"));
        screenshot.take("campaign_02_search_results");

        searchPage.openEmployeeResult();
        screenshot.take("campaign_03_employee_profile");

        searchPage.openTakeActionMenu();
        screenshot.take("campaign_04_take_action_menu");

        searchPage.clickEditCampaignAssignment();
        screenshot.take("campaign_05_campaign_form_opened");

        searchPage.selectTodayForCampaign();
        screenshot.take("campaign_06_date_selected");

        searchPage.saveCampaignEdit();
        screenshot.take("campaign_07_saved");

        boolean success = searchPage.isSuccessMessageVisible("Employee Details Updated Successfully");
        if (success) {
            screenshot.takeOnSuccess("editCampaignAssignment");
        } else {
            screenshot.takeOnFailure("editCampaignAssignment");
        }

        Assert.assertTrue(success, "Edit Campaign Assignment: success message not found");

        searchPage.goBackToHome();
    }

    // ---------------------------------------------------------------
    // Test Case 3 — Deactivate Employee
    // ---------------------------------------------------------------
    @Test
    public void deactivateEmployee() {
        ScreenshotHelper screenshot = new ScreenshotHelper(page);
        NavigationHelper nav = new NavigationHelper(page);
        SearchEmployeePage searchPage = nav.goToSearchEmployee();
        screenshot.take("deactivate_01_search_page");

        searchPage.searchEmployee(ConfigReader.get("search.emp.id"));
        screenshot.take("deactivate_02_search_results");

        searchPage.openEmployeeResult();
        screenshot.take("deactivate_03_employee_profile");

        searchPage.openTakeActionMenu();
        screenshot.take("deactivate_04_take_action_menu");

        searchPage.clickDeactivateEmployee();
        screenshot.take("deactivate_05_deactivate_form");

        searchPage.selectDeactivateReason();
        screenshot.take("deactivate_06_reason_selected");

        searchPage.confirmDeactivate();
        screenshot.take("deactivate_07_confirmed");

        boolean success = searchPage.isSuccessMessageVisible("Employee Deactivated Successfully");
        if (success) {
            screenshot.takeOnSuccess("deactivateEmployee");
        } else {
            screenshot.takeOnFailure("deactivateEmployee");
        }

        Assert.assertTrue(success, "Deactivate Employee: success message not found");

        searchPage.goBackToHome();
    }
}
