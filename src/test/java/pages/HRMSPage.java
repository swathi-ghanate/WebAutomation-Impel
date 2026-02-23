package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import utils.FormHelper;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HRMSPage {

    private final Page page;
    private final FormHelper form;

    // --- Login Details ---
    private final String employeeId = "input[title='Please provide a valid and unique Username']";
    private final String password = "input[type='password']";

    // --- Personal Details ---
    private final String employeeName = "input[required][pattern*='1,50']";
    private final String mobileNumber = "input[autocomplete='off'][type='text']";
    private final String genderMale = "input.radio-btn[type='radio']";
    private final String dateOfBirth = "input[type='date'][max*='2008']";
    private final String email = "input[type='email']";
    private final String address = "input[pattern*='1,300']";

    // --- Success ---
    private final String successMessage = "div.emp-success-wrap header";
    private final String successEmpId = "div.emp-success-wrap p";

    public HRMSPage(Page page) {
        this.page = page;
        this.form = new FormHelper(page);
    }

    public void fillLoginDetails(String empId, String pwd) {
        page.evaluate("window.scrollTo(0, 0)");
        page.waitForTimeout(2000);

        form.fillForce(employeeId, empId);
        form.fillForceNth(password, 0, pwd);
        form.fillForceNth(password, 1, pwd);
    }

    public void fillPersonalDetails(String name, String mobile,
            String dob, String emailId, String addr) {

        form.waitFor(employeeName);
        form.fill(employeeName, name);

        page.locator(mobileNumber).nth(2).waitFor();
        page.locator(mobileNumber).nth(2).fill(mobile);

        form.waitFor(genderMale);
        form.scrollTo(genderMale);
        form.clickDispatch(genderMale);

        form.waitFor(dateOfBirth);
        form.fill(dateOfBirth, dob);

        form.waitFor(email);
        form.fill(email, emailId);

        form.waitFor(address);
        form.fill(address, addr);
    }

    public void fillEmployeeDetails() {
        // Employment Type — first dropdown, first option
        form.selectDropdown(0, 0);

        // Date of Appointment — Playwright fill triggers React events; JS-only set does not
        String today = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Locator dateInput = page.locator("input[type='date'][max*='2026']");
        dateInput.scrollIntoViewIfNeeded();
        dateInput.fill(today);
        dateInput.dispatchEvent("change");
        page.waitForTimeout(500);

        // Role
        page.locator("div.master input.cursorPointer").first().scrollIntoViewIfNeeded();
        page.waitForTimeout(500);
        try {
            page.locator("div.master input.cursorPointer").first()
                    .click(new Locator.ClickOptions().setForce(true));
        } catch (Exception e) {
            form.clickDispatch("div.master input.cursorPointer");
        }
        page.waitForTimeout(1500);

        // ArrowDown is required to open this typeahead dropdown — click alone is not enough
        if (page.locator("div.server input[type='checkbox'], .profile-dropdown--item").count() == 0) {
            page.locator("div.master input.cursorPointer").first().press("ArrowDown");
            page.waitForTimeout(1500);
        }

        // Select role option
        if (page.locator("div.server input[type='checkbox']").count() > 0) {
            page.locator("div.server input[type='checkbox']").first().dispatchEvent("click");
        } else if (page.locator(".profile-dropdown--item").count() > 0) {
            page.locator(".profile-dropdown--item").first().dispatchEvent("click");
        }
        page.waitForTimeout(500);
        page.keyboard().press("Escape");
        page.waitForTimeout(400);
    }

    public void submitForm() {
        page.evaluate("window.scrollTo(0, document.body.scrollHeight)");
        page.waitForTimeout(4000);

        Locator submitBtns = page.locator("button[type='submit']");
        int count = submitBtns.count();
        System.out.println("[HRMS] Submit buttons found: " + count);
        if (count == 0)
            return;

        Locator lastBtn = submitBtns.nth(count - 1);
        lastBtn.scrollIntoViewIfNeeded();

        // First click — if button was disabled, this enables it (React state update)
        lastBtn.dispatchEvent("click");
        page.waitForTimeout(3000);

        // Second click — if button is now enabled, this is the actual submission
        String btnClass = lastBtn.getAttribute("class");
        if (btnClass != null && !btnClass.contains("disable")) {
            System.out.println("[HRMS] Button enabled after first click — clicking again to submit");
            lastBtn.dispatchEvent("click");
            page.waitForTimeout(3000);
        }

        // Confirm popup — appears after actual submission
        String[] confirmSelectors = {
                "button.selector-button-primary[type='submit']",
                "button.selector-button-primary",
                "button[class*='selector-button-primary']"
        };
        for (String sel : confirmSelectors) {
            if (page.locator(sel).count() > 0) {
                page.locator(sel).first().dispatchEvent("click");
                System.out.println("[HRMS] Confirmed with: " + sel);
                break;
            }
        }
        page.waitForTimeout(5000);
    }

    public boolean isEmployeeCreatedSuccessfully() {
        try {
            page.locator(successMessage)
                    .waitFor(new Locator.WaitForOptions().setTimeout(30000));
            String text = page.locator(successMessage).textContent();
            System.out.println("[HRMS] Success message: " + text);
            return text.contains("Employee Created Successfully");
        } catch (Exception e) {
            System.out.println("[HRMS] Success not found: " + e.getMessage().split("\n")[0]);
            return false;
        }
    }

    public String getEmployeeId() {
        try {
            page.locator(successEmpId).waitFor();
            return page.locator(successEmpId).textContent();
        } catch (Exception e) {
            return "Could not get Employee ID";
        }
    }
}