package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import utils.FormHelper;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SearchEmployeePage {

    private final Page page;
    private final FormHelper form;

    // --- Search ---
    private final String searchInput   = "input[name='codes']";
    private final String searchBtn     = "button.submit-bar-search[type='submit']";
    private final String resultLink    = "a[href*='empId']";

    // --- Action menu ---
    private final String takeActionBtn  = "button.submit-bar[type='button']";
    private final String menuItem       = "div.menu-wrap p";

    // --- Edit Employee ---
    private final String employeeName = "input[pattern*='1,50'][title*='Username']";
    private final String saveBtn       = "div.action-bar-wrap button[type='submit']";

    // --- Edit Campaign Assignment ---
    private final String campaignDateInput = "input.employee-card-input[type='date'][min]";

    // --- Deactivate ---
    private final String reasonDropdownSvg = "div.select svg.cp";
    private final String deactivateConfirm = "button.selector-button-primary[type='submit']";

    // --- Success & Back ---
    private final String successMessage  = "div.emp-success-wrap header";
    private final String goBackToHomeBtn = "div.emp-success-wrap button";

    public SearchEmployeePage(Page page) {
        this.page = page;
        this.form = new FormHelper(page);
    }

    // ---------------------------------------------------------------
    // Shared: search by id, open first result,
    //         open Take Action menu, click the named menu item
    // ---------------------------------------------------------------

    public void searchEmployee(String empId) {
        form.waitFor(searchInput);
        form.fill(searchInput, empId);
        page.waitForTimeout(500);
        form.clickDispatch(searchBtn);
        page.waitForTimeout(2000);
        System.out.println("[Search] Searched for employee: " + empId);
    }

    public void openEmployeeResult() {
        form.waitFor(resultLink);
        form.clickDispatch(resultLink);
        page.waitForTimeout(2000);
        System.out.println("[Search] Opened employee result");
    }

    public void openTakeActionMenu() {
        form.waitFor(takeActionBtn);
        form.clickDispatch(takeActionBtn);
        page.waitForTimeout(1000);
        System.out.println("[Search] Opened Take Action menu");
    }

    private void clickMenuItemByText(String menuText) {
        page.locator(menuItem + ":has-text('" + menuText + "')")
                .first().dispatchEvent("click");
        page.waitForTimeout(2000);
        System.out.println("[Search] Clicked menu: " + menuText);
    }

    // ---------------------------------------------------------------
    // Test Case 1 — Edit Employee
    // ---------------------------------------------------------------

    public void clickEditEmployee() {
        clickMenuItemByText("Edit Employee");
    }

    public void editEmployeeName() {
        form.waitFor(employeeName);
        String currentName = page.locator(employeeName).first().inputValue();
        String updatedName = currentName + " one";
        page.locator(employeeName).first().fill(updatedName);
        page.waitForTimeout(500);
        System.out.println("[Search] Updated employee name: " + currentName + " → " + updatedName);
    }

    public void saveEmployeeEdit() {
        form.waitFor(saveBtn);
        form.scrollTo(saveBtn);
        form.clickDispatch(saveBtn);
        page.waitForTimeout(3000);
        System.out.println("[Search] Clicked Save");
    }

    // ---------------------------------------------------------------
    // Test Case 2 — Edit Campaign Assignment
    // ---------------------------------------------------------------

    public void clickEditCampaignAssignment() {
        clickMenuItemByText("Edit Campaign Assignment");
    }

    public void selectTodayForCampaign() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Locator dateInput = page.locator(campaignDateInput);
        dateInput.scrollIntoViewIfNeeded();
        dateInput.fill(today);
        dateInput.dispatchEvent("change");
        page.waitForTimeout(500);
        System.out.println("[Search] Campaign date set to: " + today);
    }

    public void saveCampaignEdit() {
        saveEmployeeEdit();
    }

    // ---------------------------------------------------------------
    // Test Case 3 — Deactivate Employee
    // ---------------------------------------------------------------

    public void clickDeactivateEmployee() {
        clickMenuItemByText("Deactivate Employee");
    }

    public void selectDeactivateReason() {
        // Click the SVG arrow to open the reason dropdown
        form.waitFor(reasonDropdownSvg);
        form.clickDispatch(reasonDropdownSvg);
        page.waitForTimeout(1500);

        // Select first option
        if (page.locator(".profile-dropdown--item").count() > 0) {
            page.locator(".profile-dropdown--item").first().dispatchEvent("click");
            System.out.println("[Search] Selected deactivate reason");
        }
        page.waitForTimeout(500);
    }

    public void confirmDeactivate() {
        form.waitFor(deactivateConfirm);
        form.clickDispatch(deactivateConfirm);
        page.waitForTimeout(3000);
        System.out.println("[Search] Confirmed deactivation");
    }

    // ---------------------------------------------------------------
    // Shared — Success check and go back
    // ---------------------------------------------------------------

    public boolean isSuccessMessageVisible(String expectedText) {
        try {
            page.locator(successMessage)
                    .waitFor(new Locator.WaitForOptions().setTimeout(30000));
            String text = page.locator(successMessage).textContent();
            System.out.println("[Search] Success message: " + text);
            return text.contains(expectedText);
        } catch (Exception e) {
            System.out.println("[Search] Success not found: " + e.getMessage().split("\n")[0]);
            return false;
        }
    }

    public void goBackToHome() {
        try {
            page.locator(goBackToHomeBtn).first()
                    .waitFor(new Locator.WaitForOptions().setTimeout(5000));
            page.locator(goBackToHomeBtn).first().dispatchEvent("click");
        } catch (Exception e) {
            // fallback — navigate directly
            page.navigate(page.url().split("/hrms")[0] + "/digit-ui/employee");
        }
        page.waitForTimeout(2000);
        System.out.println("[Search] Navigated back to home");
    }
}
