package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitUntilState;

public class HomePage extends BasePage {

    private static final String BASE = "https://oyo-hcm.digit.org/digit-ui/employee";

    // --- Locators ---
    private final String homeElement = "span.text.removeHeight:has-text('Complaints')";

    public HomePage(Page page) {
        super(page);
    }

    public boolean isHomeVisible() {
        page.locator(homeElement).waitFor();
        return page.locator(homeElement).isVisible();
    }

    public HRMSPage navigateToCreateUser() {
        page.navigate(BASE + "/hrms/create",
                new Page.NavigateOptions()
                        .setTimeout(120000)
                        .setWaitUntil(WaitUntilState.COMMIT));
        page.waitForTimeout(5000);
        return new HRMSPage(page);
    }

    public SearchEmployeePage navigateToSearchUser() {
        page.navigate(BASE + "/hrms/inbox",
                new Page.NavigateOptions()
                        .setTimeout(120000)
                        .setWaitUntil(WaitUntilState.COMMIT));
        page.waitForTimeout(3000);
        return new SearchEmployeePage(page);
    }

    public void navigateToCreateComplaint() {
        page.navigate(BASE + "/pgr/complaint/create",
                new Page.NavigateOptions()
                        .setTimeout(120000)
                        .setWaitUntil(WaitUntilState.COMMIT));
        page.waitForTimeout(3000);
    }

    public void navigateToSearchComplaint() {
        page.navigate(BASE + "/pgr/inbox",
                new Page.NavigateOptions()
                        .setTimeout(120000)
                        .setWaitUntil(WaitUntilState.COMMIT));
        page.waitForTimeout(3000);
    }
}
