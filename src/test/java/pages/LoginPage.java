package pages;

import com.microsoft.playwright.Page;

public class LoginPage {

    private final Page page;

    // --- Locators (CSS Selectors) ---
    private final String usernameField = "input[name='username']";
    private final String passwordField = "input[name='password']";
    private final String loginButton = "#user-login-continue";

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
        page.fill(usernameField, username);
        page.fill(passwordField, password);
        page.click(loginButton);
    }
}
