package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

public class BasePage {

    protected final Page page;

    // --- Common Locators ---
    private final String homeButton   = ".digit-topbar-home";
    private final String backButton   = ".digit-back-btn";

    public BasePage(Page page) {
        this.page = page;
    }

    // ==================== NAVIGATION ====================

    public void goToHome() {
        if (page.locator(homeButton).isVisible()) {
            page.locator(homeButton).click();
            waitForPageLoad();
        }
    }

    public void goBack() {
        page.goBack();
        waitForPageLoad();
    }

    public void clickBackButton() {
        if (page.locator(backButton).isVisible()) {
            page.locator(backButton).click();
            waitForPageLoad();
        }
    }

    // ==================== WAIT HELPERS ====================

    public void waitForPageLoad() {
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
    }

    public void waitForVisible(String selector) {
        page.locator(selector).first()
                .waitFor(new Locator.WaitForOptions()
                        .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                        .setTimeout(30000));
    }

    public void waitForHidden(String selector) {
        page.locator(selector).first()
                .waitFor(new Locator.WaitForOptions()
                        .setState(com.microsoft.playwright.options.WaitForSelectorState.HIDDEN)
                        .setTimeout(30000));
    }

    public void wait(int ms) {
        page.waitForTimeout(ms);
    }

    // ==================== VERIFICATION HELPERS ====================

    public boolean isOnPage(String urlPart) {
        return page.url().contains(urlPart);
    }

    public String getCurrentUrl() {
        return page.url();
    }

    public boolean isVisible(String selector) {
        try {
            return page.locator(selector).first().isVisible();
        } catch (Exception e) {
            return false;
        }
    }
}
