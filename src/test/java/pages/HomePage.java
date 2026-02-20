package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitUntilState;

public class HomePage {

    private final Page page;

    // --- Locators ---
    private final String homeElement = "span.text.removeHeight:has-text('Complaints')";
    private final String createComplaintBtn = "a[href='/digit-ui/employee/pgr/complaint/create']";
    private final String searchComplaintBtn = "a[href='/digit-ui/employee/pgr/inbox']";
    private final String createUserBtn = "a[href='/digit-ui/employee/hrms/create']";
    private final String searchUserBtn = "a[href='/digit-ui/employee/hrms/inbox']";

    public HomePage(Page page) {
        this.page = page;
    }

    public boolean isHomeVisible() {
        page.locator(homeElement).waitFor();
        return page.locator(homeElement).isVisible();
    }

    public void navigateToCreateComplaint() {
        page.navigate(
                "https://oyo-hcm.digit.org/digit-ui/employee/pgr/complaint/create",
                new Page.NavigateOptions()
                        .setTimeout(120000)
                        .setWaitUntil(WaitUntilState.COMMIT));
        page.waitForTimeout(3000);
    }

    public void navigateToSearchComplaint() {
        page.navigate(
                "https://oyo-hcm.digit.org/digit-ui/employee/pgr/inbox",
                new Page.NavigateOptions()
                        .setTimeout(120000)
                        .setWaitUntil(WaitUntilState.COMMIT));
        page.waitForTimeout(3000);
    }

    public void navigateToCreateUser() {
        page.navigate(
                "https://oyo-hcm.digit.org/digit-ui/employee/hrms/create",
                new Page.NavigateOptions()
                        .setTimeout(120000)
                        .setWaitUntil(WaitUntilState.COMMIT));
        page.waitForTimeout(5000);
    }

    public void navigateToSearchUser() {
        page.navigate(
                "https://oyo-hcm.digit.org/digit-ui/employee/hrms/inbox",
                new Page.NavigateOptions()
                        .setTimeout(120000)
                        .setWaitUntil(WaitUntilState.COMMIT));
        page.waitForTimeout(3000);
    }
}