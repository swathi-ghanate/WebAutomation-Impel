package utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitUntilState;
import pages.HRMSPage;
import pages.HomePage;
import pages.LoginPage;
import pages.SearchEmployeePage;

/**
 * NavigationHelper — central place for all page navigation.
 *
 * Usage:
 * NavigationHelper nav = new NavigationHelper(page);
 *
 * // Direct navigation
 * nav.goToHome();
 * nav.goToCreateEmployee();
 * nav.goToSearchEmployee();
 *
 * // Page object getters (use when already on the target page)
 * HRMSPage hrms = nav.hrmsPage();
 * SearchEmployeePage search = nav.searchEmployeePage();
 */
public class NavigationHelper {

    private final Page page;

    private static final String BASE = "https://oyo-hcm.digit.org/digit-ui/employee";

    public NavigationHelper(Page page) {
        this.page = page;
    }

    // ==================== QUICK NAVIGATION ====================

    /** Navigate to the employee home/dashboard. */
    public HomePage goToHome() {
        page.navigate(BASE, new Page.NavigateOptions()
                .setTimeout(60000)
                .setWaitUntil(WaitUntilState.DOMCONTENTLOADED));
        page.locator("span.text.removeHeight:has-text('Complaints')").first()
                .waitFor(new Locator.WaitForOptions().setTimeout(30000));
        return new HomePage(page);
    }

    /** Navigate to Create Employee (HRMS create) form. */
    public HRMSPage goToCreateEmployee() {
        page.navigate(BASE + "/hrms/create", new Page.NavigateOptions()
                .setTimeout(60000)
                .setWaitUntil(WaitUntilState.DOMCONTENTLOADED));
        page.locator("input[title*='Username']").first()
                .waitFor(new Locator.WaitForOptions().setTimeout(30000));
        return new HRMSPage(page);
    }

    /** Navigate to Search Employee (HRMS inbox). */
    public SearchEmployeePage goToSearchEmployee() {
        // Navigate via the sidebar from a fully-loaded home page (React Router).
        // Direct URL navigation re-initialises the React app from scratch and the
        // HRMS inbox API call intermittently fails before auth context is ready.
        // We retry up to 3 times: each attempt reloads home, clicks the sidebar
        // link, then waits up to 15s for the search form (input[name='codes']).
        // A failed attempt resets the page so the next attempt starts clean.
        String[] sidebarSelectors = {
                "a[href*='hrms/inbox']",
                "span.text.removeHeight:has-text('Search Employee')",
                "a:has-text('Search Employee')",
                "span.text:has-text('Search Employee')"
        };

        for (int attempt = 1; attempt <= 3; attempt++) {
            System.out.println("[Nav] goToSearchEmployee attempt " + attempt);

            page.navigate(BASE, new Page.NavigateOptions()
                    .setTimeout(60000)
                    .setWaitUntil(WaitUntilState.DOMCONTENTLOADED));
            page.locator("span.text.removeHeight:has-text('Complaints')").first()
                    .waitFor(new Locator.WaitForOptions().setTimeout(30000));
            page.waitForTimeout(2000);

            for (String sel : sidebarSelectors) {
                if (page.locator(sel).count() > 0) {
                    page.locator(sel).first().dispatchEvent("click");
                    System.out.println("[Nav] Clicked sidebar link: " + sel);
                    break;
                }
            }

            // Confirm the search form loaded by waiting for its input
            try {
                page.locator("input[name='codes']").first()
                        .waitFor(new Locator.WaitForOptions().setTimeout(15000));
                System.out.println("[Nav] Search form loaded on attempt " + attempt);
                return new SearchEmployeePage(page);
            } catch (Exception e) {
                System.out.println("[Nav] Search form not loaded on attempt " + attempt + " — retrying");
            }
        }

        System.out.println("[Nav] All sidebar attempts exhausted — returning page as-is");
        return new SearchEmployeePage(page);
    }

    /** Navigate to Create Complaint. */
    public void goToCreateComplaint() {
        page.navigate(BASE + "/pgr/complaint/create", new Page.NavigateOptions()
                .setTimeout(60000)
                .setWaitUntil(WaitUntilState.DOMCONTENTLOADED));
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    /** Navigate to Search Complaint inbox. */
    public void goToSearchComplaint() {
        page.navigate(BASE + "/pgr/inbox", new Page.NavigateOptions()
                .setTimeout(60000)
                .setWaitUntil(WaitUntilState.DOMCONTENTLOADED));
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    // ==================== PAGE OBJECT GETTERS ====================
    // Use these when you are already on the target page.

    /** Get LoginPage object (use when on login page). */
    public LoginPage loginPage() {
        return new LoginPage(page);
    }

    /** Get HomePage object (use when on home page). */
    public HomePage homePage() {
        return new HomePage(page);
    }

    /** Get HRMSPage object (use when on HRMS create page). */
    public HRMSPage hrmsPage() {
        return new HRMSPage(page);
    }

    /** Get SearchEmployeePage object (use when on HRMS inbox). */
    public SearchEmployeePage searchEmployeePage() {
        return new SearchEmployeePage(page);
    }

    // ==================== UTILITY ====================

    /** Go back to the previous page. */
    public void goBack() {
        page.goBack(new Page.GoBackOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
    }

    /** Reload the current page. */
    public void refresh() {
        page.reload(new Page.ReloadOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
    }

    /** Navigate to any full URL. */
    public void navigateTo(String url) {
        page.navigate(url, new Page.NavigateOptions()
                .setTimeout(60000)
                .setWaitUntil(WaitUntilState.DOMCONTENTLOADED));
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    /** Get the current page URL. */
    public String getCurrentUrl() {
        return page.url();
    }

    /** Wait for page network to be idle. */
    public void waitForPageLoad() {
        page.waitForLoadState();
    }
}
