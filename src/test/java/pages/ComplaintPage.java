package pages;

import com.microsoft.playwright.Page;

public class ComplaintPage {

    private final Page page;

    // --- Locators ---
    private final String complaintTypeDropdown = ".digit-dropdown-employee-select-wrap";
    private final String dropdownOption = ".main-option";
    private final String complaintDateInput = "input[name='ComplaintDate']";
    private final String todayDate = ".react-datepicker__day--today";
    private final String countryDropdown = ".digit-dropdown-employee-select-wrap";
    private final String complaintForRadio = ".digit-radio-btn-checkmark";
    private final String descriptionField = ".digit-field-container";
    private final String submitButton = ".digit-submit-bar .digit-formcomposer-submitbar";

    // --- Commented-out Locators ---
    // private final String complainantName = "#complainant-name";
    // private final String complainantNumber = "#complainant-number";

    public ComplaintPage(Page page) {
        this.page = page;
    }

    public void fillComplaintForm() {
        // 1. Select complaint type
        page.locator(complaintTypeDropdown).first().click();
        page.locator(dropdownOption).first().click();

        // 2. Select today's date
        page.click(complaintDateInput);
        page.click(todayDate);

        // 3. Select country
        page.locator(countryDropdown).nth(1).click();
        page.locator(dropdownOption).first().click();

        // 4. Select complaint for radio button
        page.locator(complaintForRadio).first().click();

        // 5. Fill description (placeholder)
        page.locator(descriptionField).fill("");
    }

    public void submitComplaint() {
        page.click(submitButton);
    }
}
