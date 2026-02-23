package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

public class ConfigReader {

    private static Properties properties;
    private static final Random random = new Random();

    // Generated once per test run — same values used throughout
    private static final String empUsername = generateEmpUsername();
    private static final String empMobile = generateMobile();
    private static final String empEmail = generateEmail();

    static {
        try (FileInputStream fis = new FileInputStream("src/test/resources/config.properties")) {
            properties = new Properties();
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    // --- App ---
    public static String getBaseUrl() {
        return get("base.url");
    }

    public static String getBrowser() {
        return get("browser");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(get("headless"));
    }

    // --- App Login ---
    public static String getUsername() {
        return get("username");
    }

    public static String getPassword() {
        return get("password");
    }

    // --- Employee Login Details — RANDOM each run ---
    public static String getEmpUsername() {
        return empUsername;
    }

    public static String getEmpPassword() {
        return get("emp.password");
    }

    // --- Personal Details — RANDOM each run ---
    public static String getEmpName() {
        return get("emp.name");
    }

    public static String getEmpMobile() {
        return empMobile;
    }

    public static String getEmpDob() {
        return get("emp.dob");
    }

    public static String getEmpEmail() {
        return empEmail;
    }

    public static String getEmpAddress() {
        return get("emp.address");
    }

    // --- Random Generators ---

    // Pattern: smc-oy-hd-test + 2 digit random number e.g. smc-oy-hd-test07
    private static String generateEmpUsername() {
        int num = random.nextInt(90) + 10; // 10 to 99
        String username = "smc-oy-hd-test" + num;
        System.out.println("[ConfigReader] Generated emp.username: " + username);
        return username;
    }

    // Pattern: 8 + 10 random digits = 11 digits total e.g. 84567891234
    private static String generateMobile() {
        StringBuilder sb = new StringBuilder("8");
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(10));
        }
        String mobile = sb.toString();
        System.out.println("[ConfigReader] Generated emp.mobile: " + mobile);
        return mobile;
    }

    // Pattern: test + random 4 digits + @test.com e.g. test4821@test.com
    private static String generateEmail() {
        int num = random.nextInt(9000) + 1000; // 1000 to 9999
        String email = "test" + num + "@test.com";
        System.out.println("[ConfigReader] Generated emp.email: " + email);
        return email;
    }
}