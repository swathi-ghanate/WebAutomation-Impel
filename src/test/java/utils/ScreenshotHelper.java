package utils;

import com.microsoft.playwright.Page;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotHelper {

    private final Page page;
    private static final String SCREENSHOT_DIR = "target/screenshots/";

    public ScreenshotHelper(Page page) {
        this.page = page;
    }

    // Take screenshot with custom name
    public void take(String name) {
        try {
            Files.createDirectories(Paths.get(SCREENSHOT_DIR));
        } catch (IOException e) {
            System.out.println("[Screenshot] Could not create dir: " + e.getMessage());
        }
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = SCREENSHOT_DIR + name + "_" + timestamp + ".png";
        try {
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get(fileName))
                    .setFullPage(true)
                    .setTimeout(15000));
            System.out.println("[Screenshot] Saved: " + fileName);
        } catch (Exception e) {
            System.out.println("[Screenshot] Failed (page may still be loading): " + e.getMessage().split("\n")[0]);
        }
    }

    // Take screenshot on failure
    public void takeOnFailure(String testName) {
        take("FAILED_" + testName);
    }

    // Take screenshot on success
    public void takeOnSuccess(String testName) {
        take("PASSED_" + testName);
    }
}