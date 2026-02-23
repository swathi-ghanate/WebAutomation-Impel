package utils;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitUntilState;
import pages.HRMSPage;
import pages.HomePage;
import pages.LoginPage;
import pages.SearchEmployeePage;

/**
 * NavigationHelper — central place for all page navigation.
 *
 * Usage:
 *   NavigationHelper nav = new NavigationHelper(page);
 *
 *   // Direct navigation
 *   nav.goToHome();
 *   nav.goToCreateEmployee();
 *   nav.goToSearchEmployee();
 *
 *   // Page object getters (use when already on the target page)
 *   HRMSPage hrms = nav.hrmsPage();
 *   SearchEmployeePage search = nav.searchEmployeePage();
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
                .setWaitUntil(WaitUntilState.COMMIT));
        page.waitForTimeout(2000);
        return new HomePage(page);
    }

    /** Navigate to Create Employee (HRMS create) form. */
    public HRMSPage goToCreateEmployee() {
        page.navigate(BASE + "/hrms/create", new Page.NavigateOptions()
                .setTimeout(60000)
                .setWaitUntil(WaitUntilState.COMMIT));
        page.waitForTimeout(3000);
        return new HRMSPage(page);
    }

    /** Navigate to Search Employee (HRMS inbox). */
    public SearchEmployeePage goToSearchEmployee() {
        page.navigate(BASE + "/hrms/inbox", new Page.NavigateOptions()
                .setTimeout(60000)
                .setWaitUntil(WaitUntilState.COMMIT));
        page.waitForTimeout(2000);
        return new SearchEmployeePage(page);
    }

    /** Navigate to Create Complaint. */
    public void goToCreateComplaint() {
        page.navigate(BASE + "/pgr/complaint/create", new Page.NavigateOptions()
                .setTimeout(60000)
                .setWaitUntil(WaitUntilState.COMMIT));
        page.waitForTimeout(3000);
    }

    /** Navigate to Search Complaint inbox. */
    public void goToSearchComplaint() {
        page.navigate(BASE + "/pgr/inbox", new Page.NavigateOptions()
                .setTimeout(60000)
                .setWaitUntil(WaitUntilState.COMMIT));
        page.waitForTimeout(2000);
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
        page.goBack();
        page.waitForTimeout(1000);
    }

    /** Reload the current page. */
    public void refresh() {
        page.reload();
        page.waitForTimeout(1000);
    }

    /** Navigate to any full URL. */
    public void navigateTo(String url) {
        page.navigate(url, new Page.NavigateOptions()
                .setTimeout(60000)
                .setWaitUntil(WaitUntilState.COMMIT));
        page.waitForTimeout(2000);
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
