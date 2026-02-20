package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HRMSPage {

    private final Page page;

    // --- Login Details Section ---
    private final String employeeId = "input[title='Please provide a valid and unique Username']";
    private final String password = "input[type='password']";

    // --- Personal Details Section ---
    private final String employeeName = "input[required][pattern*='1,50']";
    private final String mobileNumber = "input[autocomplete='off'][type='text']";
    private final String genderMale = "input.radio-btn[type='radio']";
    private final String dateOfBirth = "input[type='date'][max*='2008']";
    private final String email = "input[type='email']";
    private final String address = "input[pattern*='1,300']";

    // --- Employee Details Section ---
    private final String dateOfAppointment = "input[type='date'][max*='2026']";

    // --- Success Message ---
    private final String successMessage = "div.emp-success-wrap header";
    private final String successEmpId = "div.emp-success-wrap p";

    public HRMSPage(Page page) {
        this.page = page;
    }

    public void fillLoginDetails(String empId, String pwd) {
        page.evaluate("window.scrollTo(0, 0)");
        page.waitForTimeout(2000);

        page.locator(employeeId).first()
                .fill(empId, new Locator.FillOptions().setForce(true));

        page.locator(password).first()
                .fill(pwd, new Locator.FillOptions().setForce(true));

        page.locator(password).nth(1)
                .fill(pwd, new Locator.FillOptions().setForce(true));
    }

    public void fillPersonalDetails(String name, String mobile,
            String dob, String emailId, String addr) {

        page.locator(employeeName).first().waitFor();
        page.locator(employeeName).first().fill(name);

        page.locator(mobileNumber).nth(2).waitFor();
        page.locator(mobileNumber).nth(2).fill(mobile);

        page.locator(genderMale).first().waitFor();
        page.locator(genderMale).first().scrollIntoViewIfNeeded();
        page.locator(genderMale).first().dispatchEvent("click");

        page.locator(dateOfBirth).waitFor();
        page.locator(dateOfBirth).fill(dob);

        page.locator(email).waitFor();
        page.locator(email).fill(emailId);

        page.locator(address).waitFor();
        page.locator(address).fill(addr);
    }

    public void fillEmployeeDetails() {

        // --- Employment Type (mandatory) ---
        // The SVG arrow with class="cp" inside div.select is the real React click target
        page.locator("div.select").first().scrollIntoViewIfNeeded();
        page.waitForTimeout(300);

        Locator etSvg = page.locator("div.select svg.cp");
        if (etSvg.count() > 0) {
            System.out.println("[HRMS] Clicking Employment Type SVG trigger");
            etSvg.first().dispatchEvent("click");
        } else {
            System.out.println("[HRMS] No svg.cp found, clicking div.select container");
            page.locator("div.select").first().dispatchEvent("click");
        }
        page.waitForTimeout(1500);

        // Options appear in #jk-dropdown-unique portal as div.profile-dropdown--item
        int etCount = page.locator(".profile-dropdown--item").count();
        System.out.println("[HRMS] Employment Type options visible: " + etCount);
        if (etCount > 0) {
            page.locator(".profile-dropdown--item").first().dispatchEvent("click");
            System.out.println("[HRMS] Clicked first Employment Type option");
        } else {
            // Fallback: try any option selector
            Locator fallback = page.locator(".main-option, [role='option'], li.options-wrapper");
            System.out.println("[HRMS] Fallback options visible: " + fallback.count());
            if (fallback.count() > 0) {
                fallback.first().dispatchEvent("click");
            }
        }
        page.waitForTimeout(500);
        page.keyboard().press("Escape");
        page.waitForTimeout(400);

        // --- Date of Appointment ---
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        page.locator(dateOfAppointment).scrollIntoViewIfNeeded();
        page.locator(dateOfAppointment).fill(today);
        page.locator(dateOfAppointment).dispatchEvent("change");
        page.waitForTimeout(500);

        // --- Employment Department (non-mandatory) --- skipped ---

        // --- Role (mandatory) ---
        // div.master: <input class="cursorPointer" type="text"> + <div class="label"><svg>
        page.locator("div.master input.cursorPointer").first().scrollIntoViewIfNeeded();
        page.waitForTimeout(500);

        // Step 1: Real Playwright force-click (CDP-level, most reliable for React)
        try {
            page.locator("div.master input.cursorPointer").first()
                    .click(new Locator.ClickOptions().setForce(true));
            System.out.println("[HRMS] Role input: force click ok");
        } catch (Exception e) {
            System.out.println("[HRMS] Role input: force click failed - " + e.getMessage().split("\n")[0]);
            page.locator("div.master input.cursorPointer").first().dispatchEvent("click");
        }
        page.waitForTimeout(1500);

        // Step 2: If still no options, try ArrowDown to open a closed typeahead
        String domSnap1 = (String) page.evaluate(
            "JSON.stringify({server:document.querySelectorAll('div.server').length," +
            "jk:document.querySelectorAll('#jk-dropdown-unique').length," +
            "cb:document.querySelectorAll('input[type=\"checkbox\"]').length," +
            "pdi:document.querySelectorAll('.profile-dropdown--item').length})"
        );
        System.out.println("[HRMS] Role DOM after click: " + domSnap1);

        if (page.locator("input[type='checkbox'], .profile-dropdown--item").count() == 0) {
            // Nothing opened yet — try ArrowDown key (opens many typeahead dropdowns)
            page.locator("div.master input.cursorPointer").first().press("ArrowDown");
            page.waitForTimeout(1500);
            String domSnap2 = (String) page.evaluate(
                "JSON.stringify({cb:document.querySelectorAll('input[type=\"checkbox\"]').length," +
                "pdi:document.querySelectorAll('.profile-dropdown--item').length," +
                "server:document.querySelectorAll('div.server').length})"
            );
            System.out.println("[HRMS] Role DOM after ArrowDown: " + domSnap2);
        }

        // Step 3: Select whatever appeared
        int serverCb = page.locator("div.server input[type='checkbox']").count();
        int jkCb = page.locator("#jk-dropdown-unique input[type='checkbox']").count();
        int allCb = page.locator("input[type='checkbox']").count();
        int profileItems = page.locator(".profile-dropdown--item").count();

        if (serverCb > 0) {
            page.locator("div.server input[type='checkbox']").first().dispatchEvent("click");
            System.out.println("[HRMS] Clicked Role checkbox in div.server");
        } else if (jkCb > 0) {
            page.locator("#jk-dropdown-unique input[type='checkbox']").first().dispatchEvent("click");
            System.out.println("[HRMS] Clicked Role checkbox in #jk-dropdown-unique");
        } else if (allCb > 0) {
            page.locator("input[type='checkbox']").first().dispatchEvent("click");
            System.out.println("[HRMS] Clicked first checkbox on page (fallback)");
        } else if (profileItems > 0) {
            page.locator(".profile-dropdown--item").first().dispatchEvent("click");
            System.out.println("[HRMS] Clicked .profile-dropdown--item for Role");
        } else {
            System.out.println("[HRMS] WARNING: Role dropdown did not open at all");
        }
        page.waitForTimeout(600);
        page.keyboard().press("Escape");
        page.waitForTimeout(400);
    }

    public void submitForm() {
        page.evaluate("window.scrollTo(0, document.body.scrollHeight)");
        // Wait for React to process Role selection and enable the submit button
        page.waitForTimeout(4000);

        // Log button state AFTER React settles
        Locator submitBtns = page.locator("button[type='submit']");
        int count = submitBtns.count();
        System.out.println("[HRMS] Submit buttons: " + count);
        for (int i = 0; i < count; i++) {
            System.out.println("[HRMS]   Button[" + i + "] class=" +
                    submitBtns.nth(i).getAttribute("class") +
                    " disabled=" + submitBtns.nth(i).getAttribute("disabled"));
        }

        if (count == 0) return;

        Locator lastBtn = submitBtns.nth(count - 1);
        lastBtn.scrollIntoViewIfNeeded();

        String preClass = lastBtn.getAttribute("class");
        System.out.println("[HRMS] Submit button class before click: " + preClass);

        // Click — if still disabled, dispatchEvent forces the event through
        lastBtn.dispatchEvent("click");
        System.out.println("[HRMS] Clicked submit button");
        page.waitForTimeout(3000);

        // If button was disabled, React may have just enabled it — click again on the enabled button
        String postClass = lastBtn.getAttribute("class");
        System.out.println("[HRMS] Submit button class after first click: " + postClass);

        if (postClass != null && !postClass.contains("disable")) {
            System.out.println("[HRMS] Button is enabled — clicking again to actually submit");
            lastBtn.dispatchEvent("click");
            page.waitForTimeout(3000);
        }

        // Log all buttons present — confirm popup adds new buttons
        Locator allBtns = page.locator("button");
        int totalBtns = allBtns.count();
        System.out.println("[HRMS] Total buttons after submit: " + totalBtns);
        for (int i = 0; i < totalBtns; i++) {
            System.out.println("[HRMS]   button[" + i + "] class=" +
                    allBtns.nth(i).getAttribute("class") +
                    " text=" + allBtns.nth(i).textContent().trim());
        }

        // Try confirm button selectors
        String[] confirmSelectors = {
            "button.selector-button-primary[type='submit']",
            "button.selector-button-primary",
            "button[class*='selector-button-primary']",
            "button[class*='primary']:not([class*='secondary']):not([class*='disable'])"
        };

        boolean confirmed = false;
        for (String sel : confirmSelectors) {
            Locator btn = page.locator(sel);
            if (btn.count() > 0) {
                System.out.println("[HRMS] Found confirm button via: " + sel);
                btn.first().dispatchEvent("click");
                confirmed = true;
                break;
            }
        }
        if (!confirmed) {
            System.out.println("[HRMS] No confirm button found");
        }
        page.waitForTimeout(5000);
    }

    public boolean isEmployeeCreatedSuccessfully() {
        try {
            page.locator(successMessage)
                    .waitFor(new Locator.WaitForOptions().setTimeout(30000));
            String text = page.locator(successMessage).textContent();
            System.out.println("[HRMS] Success message text: " + text);
            return text.contains("Employee Created Successfully");
        } catch (Exception e) {
            System.out.println("[HRMS] Success message not found: " + e.getMessage().split("\n")[0]);
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
