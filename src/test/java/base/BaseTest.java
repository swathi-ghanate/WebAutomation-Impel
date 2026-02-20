package base;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.WaitUntilState;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.LoginPage;
import utils.ConfigReader;
import java.util.Arrays;

public class BaseTest {

    protected Playwright playwright;
    protected Browser browser;
    protected Page page;

    @BeforeMethod
    public void setUp() {
        playwright = Playwright.create();

        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setArgs(Arrays.asList(
                                "--no-sandbox",
                                "--disable-setuid-sandbox",
                                "--disable-dev-shm-usage",
                                "--disable-web-security",
                                "--ignore-certificate-errors")));

        page = browser.newPage();
        page.setDefaultTimeout(120000);

        page.navigate(ConfigReader.getBaseUrl(),
                new Page.NavigateOptions()
                        .setTimeout(120000)
                        .setWaitUntil(WaitUntilState.COMMIT));

        LoginPage loginPage = new LoginPage(page);
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());

        // Wait for home page to fully load after login
        page.waitForURL("**/digit-ui/employee**");
        page.waitForTimeout(3000);
    }

    @AfterMethod
    public void tearDown() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}