package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
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
                        .setWaitUntil(WaitUntilState.DOMCONTENTLOADED));
        page.locator("input[title*='Username']").first()
                .waitFor(new Locator.WaitForOptions().setTimeout(30000));
        return new HRMSPage(page);
    }

    public SearchEmployeePage navigateToSearchUser() {
        page.navigate(BASE + "/hrms/inbox",
                new Page.NavigateOptions()
                        .setTimeout(120000)
                        .setWaitUntil(WaitUntilState.LOAD));
        try {
            page.waitForLoadState(LoadState.NETWORKIDLE,
                    new Page.WaitForLoadStateOptions().setTimeout(60000));
        } catch (Exception e) {
            System.out.println("[HomePage] NETWORKIDLE not reached: "
                    + e.getMessage().split("\n")[0]);
        }
        return new SearchEmployeePage(page);
    }

    public void navigateToCreateComplaint() {
        page.navigate(BASE + "/pgr/complaint/create",
                new Page.NavigateOptions()
                        .setTimeout(120000)
                        .setWaitUntil(WaitUntilState.DOMCONTENTLOADED));
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    public void navigateToSearchComplaint() {
        page.navigate(BASE + "/pgr/inbox",
                new Page.NavigateOptions()
                        .setTimeout(120000)
                        .setWaitUntil(WaitUntilState.DOMCONTENTLOADED));
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }
}
