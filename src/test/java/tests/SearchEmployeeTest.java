package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.SearchEmployeePage;
import utils.ConfigReader;

public class SearchEmployeeTest extends BaseTest {

    // ---------------------------------------------------------------
    // Test Case 1 — Edit Employee
    // ---------------------------------------------------------------
    @Test(priority = 1)
    public void editEmployee() {
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

        searchPage.fillRequiredEditFields();
        screenshot.take("edit_07_required_fields_filled");

        searchPage.saveEmployeeEdit();
        screenshot.take("edit_08_saved");

        boolean success = searchPage.isSuccessMessageVisible("Employee Details Updated Successfully");
        if (success) {
            screenshot.takeOnSuccess("editEmployee");
        }

        Assert.assertTrue(success, "Edit Employee: success message not found");

        searchPage.goBackToHome();
    }

    // ---------------------------------------------------------------
    // Test Case 2 — Deactivate Employee
    // ---------------------------------------------------------------
    @Test(priority = 2)
    public void deactivateEmployee() {
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
        }

        Assert.assertTrue(success, "Deactivate Employee: success message not found");

        searchPage.goBackToHome();
    }

    // ---------------------------------------------------------------
    // Test Case 3 — Search and Verify Employee Appears in Results
    // ---------------------------------------------------------------
    @Test(priority = 3)
    public void searchAndVerifyEmployee() {
        String empId = ConfigReader.get("search.emp.id");

        SearchEmployeePage searchPage = nav.goToSearchEmployee();
        screenshot.take("verify_01_search_page");

        searchPage.searchEmployee(empId);
        screenshot.take("verify_02_search_results");

        // Verify a result link appears in the table — try most specific to broadest
        String[] selectors = {
                "table tbody tr td:first-child a",
                "tbody tr td a",
                "tbody a"
        };

        boolean resultFound = false;
        for (String sel : selectors) {
            int count = page.locator(sel).count();
            System.out.println("[SearchVerify] Selector '" + sel + "' count: " + count);
            if (count > 0) {
                resultFound = true;
                System.out.println("[SearchVerify] Result link found with: " + sel);
                break;
            }
        }

        screenshot.take("verify_03_result_check");

        if (resultFound) {
            screenshot.takeOnSuccess("searchAndVerifyEmployee");
        }

        Assert.assertTrue(resultFound,
                "Employee '" + empId + "' result link not found in search results table");
    }
}