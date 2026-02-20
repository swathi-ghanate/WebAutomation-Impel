package pages;

import com.microsoft.playwright.Page;

public class LoginPage {

    private final Page page;

    // --- Locators (CSS Selectors) ---
    private final String usernameField = "input[name='username']";
    private final String passwordField = "input[name='password']";
    private final String loginButton = "button.submit-bar";

    // --- Alternative Locator Strategies (Playwright built-in) ---
    // By ID: page.locator("#username")
    // By Placeholder: page.getByPlaceholder("Enter your username")
    // By Label: page.getByLabel("Username")
    // By Role: page.getByRole(AriaRole.BUTTON, new
    // Page.GetByRoleOptions().setName("Login"))
    // By Text: page.getByText("Sign in")
    // By Test ID: page.getByTestId("login-btn")

    public LoginPage(Page page) {
        this.page = page;
    }

    public void login(String username, String password) {
        page.locator(usernameField).waitFor();
        page.locator(usernameField).fill(username);

        page.locator(passwordField).waitFor();
        page.locator(passwordField).fill(password);

        page.locator(loginButton).waitFor();
        page.locator(loginButton).click();
    }
}