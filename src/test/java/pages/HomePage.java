package pages;

import com.microsoft.playwright.Page;

public class HomePage {

    private final Page page;

    // --- Locators ---
    private final String homeElement = ".digit-topbar-ulb";
    private final String createComplaintBtn = "h2.digit-button-label:has-text('Create Complaint')";
    private final String searchComplaintBtn = "h2.digit-button-label:has-text('Search Complaint')";
    private final String createUserBtn = "h2.digit-button-label:has-text('Create User')";
    private final String searchUserBtn = "h2.digit-button-label:has-text('Search User')";

    public HomePage(Page page) {
        this.page = page;
    }

    public boolean isHomeVisible() {
        page.locator(homeElement).waitFor();
        return page.locator(homeElement).isVisible();
    }

    public void navigateToCreateComplaint() {
        page.click(createComplaintBtn);
    }

    public void navigateToSearchComplaint() {
        page.click(searchComplaintBtn);
    }

    public void navigateToCreateUser() {
        page.click(createUserBtn);
    }

    public void navigateToSearchUser() {
        page.click(searchUserBtn);
    }
}
