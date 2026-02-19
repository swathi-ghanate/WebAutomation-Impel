# WebAutomation-Impel

Web automation framework built with Java, Playwright, and TestNG using the Page Object Model design pattern.

---

## Project Overview

This framework automates end-to-end testing for a web application covering login, home page navigation, and complaint creation workflows. It uses Microsoft Playwright for browser automation and TestNG for test execution and assertions.

---

## Prerequisites

| Requirement    | Version   |
|----------------|-----------|
| Java JDK       | 11+       |
| Maven          | 3.x       |
| Google Chrome  | Latest    |

Ensure `JAVA_HOME` and `MAVEN_HOME` are set and both `java` and `mvn` are available on your system `PATH`.

---

## Setup

```bash
# Clone the repository
git clone https://github.com/your-username/WebAutomation-Impel.git
cd WebAutomation-Impel

# Install dependencies
mvn clean install -DskipTests

# Install Playwright browsers
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
```

Update `src/test/resources/config.properties` with your environment values:

```properties
base.url=https://your-app-url.com
browser=chromium
headless=true
username=your-username
password=your-password
```

---

## Running Tests

```bash
# Run all tests
mvn clean test

# Run a specific test class
mvn clean test -Dtest=LoginTest

# Run using the TestNG suite file
mvn clean test -DsuiteXmlFile=testng.xml
```

---

## Folder Structure

```
WebAutomation-Impel/
├── pom.xml                              # Maven dependencies & build config
├── testng.xml                           # TestNG suite definition
├── README.md
├── APPROACH_DOCUMENT.md
│
└── src/test/
    ├── java/
    │   ├── base/
    │   │   └── BaseTest.java            # Browser setup, login, teardown
    │   ├── pages/
    │   │   ├── LoginPage.java           # Login page interactions
    │   │   ├── HomePage.java            # Home page navigation
    │   │   └── ComplaintPage.java       # Complaint form interactions
    │   ├── tests/
    │   │   ├── LoginTest.java           # Login verification
    │   │   ├── HomeTest.java            # Home navigation test
    │   │   └── ComplaintTest.java       # Complaint creation test
    │   └── utils/
    │       └── ConfigReader.java        # Loads config.properties
    │
    └── resources/
        └── config.properties            # Environment configuration
```

---

## Author

**Swathi**
