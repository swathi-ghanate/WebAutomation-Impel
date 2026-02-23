package tests;

import base.BaseTest;
import com.microsoft.playwright.options.WaitUntilState;
import com.microsoft.playwright.Page;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import utils.ConfigReader;

public class LoginTest extends BaseTest {

    // ---------------------------------------------------------------
    // Test Case 1 — Valid login (verified via home page visibility)
    // ---------------------------------------------------------------
    @Test
    public void verifyUserCanLogin() {
        // BaseTest.setUp() already logged in — just assert home page loaded
        HomePage home = new HomePage(page);
        Assert.assertTrue(home.isHomeVisible(), "Home element is not visible after login");
    }

    // ---------------------------------------------------------------
    // Test Case 2 — Invalid credentials show an error
    // ---------------------------------------------------------------
    @Test
    public void verifyInvalidLogin() {
        // Reset to a fresh unauthenticated session
        page.context().clearCookies();
        page.navigate(ConfigReader.getBaseUrl(),
                new Page.NavigateOptions()
                        .setTimeout(60000)
                        .setWaitUntil(WaitUntilState.COMMIT));
        page.waitForTimeout(2000);
        screenshot.take("invalid_01_login_page");

        // Fill valid username but wrong password
        page.locator("input[name='username']").waitFor();
        page.locator("input[name='username']").fill(ConfigReader.getUsername());
        page.locator("input[name='password']").fill("wrongpass123");
        page.locator("button.submit-bar").click();
        page.waitForTimeout(3000);
        screenshot.take("invalid_02_after_submit");

        // Check for an error indicator — any of these is a pass
        String[] errorSelectors = {
            "[class*='error']",
            "[class*='invalid']",
            ".digit-toast",
            "div[role='alert']",
            ".field-error",
            "[class*='toast']"
        };
        boolean errorVisible = false;
        for (String sel : errorSelectors) {
            if (page.locator(sel).count() > 0) {
                System.out.println("[LoginTest] Error indicator found: " + sel
                        + " — text: " + page.locator(sel).first().textContent());
                errorVisible = true;
                break;
            }
        }

        // Fallback: still on login page (redirect did not happen)
        boolean stillOnLogin = page.url().contains("login")
                || page.locator("input[name='password']").isVisible();

        screenshot.take("invalid_03_error_state");

        Assert.assertTrue(errorVisible || stillOnLogin,
                "Expected error message or stay on login page for invalid credentials");
    }

    // ---------------------------------------------------------------
    // Test Case 3 — Empty fields keep user on login page
    // ---------------------------------------------------------------
    @Test
    public void verifyEmptyLogin() {
        // Reset to a fresh unauthenticated session
        page.context().clearCookies();
        page.navigate(ConfigReader.getBaseUrl(),
                new Page.NavigateOptions()
                        .setTimeout(60000)
                        .setWaitUntil(WaitUntilState.COMMIT));
        page.waitForTimeout(2000);
        screenshot.take("empty_01_login_page");

        // Click login without filling any fields
        page.locator("button.submit-bar").waitFor();
        page.locator("button.submit-bar").click();
        page.waitForTimeout(2000);
        screenshot.take("empty_02_after_click");

        // Either HTML5 validation blocks submit (fields still empty + on login page)
        // or app shows an error — both mean the user is NOT logged in
        boolean loginFieldStillVisible = page.locator("input[name='username']").isVisible();
        boolean errorVisible = page.locator("[class*='error'], [class*='invalid'], div[role='alert']").count() > 0;

        screenshot.take("empty_03_validation_state");

        Assert.assertTrue(loginFieldStillVisible || errorVisible,
                "Expected to remain on login page or see error when submitting empty form");
    }
}
