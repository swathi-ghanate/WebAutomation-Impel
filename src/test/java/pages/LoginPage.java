package pages;

import com.microsoft.playwright.Page;

public class LoginPage {

    private final Page page;

    // --- Locators ---
    private final String usernameField = "input[name='username']";
    private final String passwordField = "input[name='password']";
    private final String loginButton   = "button.submit-bar";

    public LoginPage(Page page) {
        this.page = page;
    }

    public HomePage login(String username, String password) {
        page.locator(usernameField).waitFor();
        page.locator(usernameField).fill(username);

        page.locator(passwordField).waitFor();
        page.locator(passwordField).fill(password);

        page.locator(loginButton).waitFor();
        page.locator(loginButton).click();

        return new HomePage(page);
    }
}