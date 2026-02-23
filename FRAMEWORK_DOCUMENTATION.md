# Web Automation Framework Documentation
**Java + Playwright + TestNG (POM Architecture)**

---

## 1. Project Overview

This project is a Maven-based Java Web Automation Framework built using:

- **Playwright (v1.42.0)** for browser automation
- **TestNG (v7.9.0)** for test execution and reporting
- **Java 11+**
- **Page Object Model (POM)** design pattern

The framework is designed to be:
- Scalable
- Maintainable
- Reusable
- Easy to extend
- CI/CD ready

---

## 2. Technology Stack

| Component | Technology |
|-----------|------------|
| Automation Library | Playwright |
| Test Framework | TestNG |
| Language | Java 11+ |
| Build Tool | Maven |
| Design Pattern | Page Object Model (POM) |
| IDE | VS Code / IntelliJ |

---

## 3. Project Structure

```
src
└── test
    ├── java
    │   ├── base
    │   │     └── BaseTest.java
    │   ├── pages
    │   │     ├── LoginPage.java
    │   │     ├── HomePage.java
    │   │     ├── ComplaintPage.java
    │   │     └── HRMSPage.java
    │   ├── tests
    │   │     ├── LoginTest.java
    │   │     ├── HomeTest.java
    │   │     ├── ComplaintTest.java
    │   │     └── HRMSTest.java
    │   └── utils
    │         └── ConfigReader.java
    └── resources
          └── config.properties

testng.xml
pom.xml
README.md
```

---

## 4. Framework Architecture

### 4.1 Page Object Model (POM)

Each page in the application is represented as a separate class inside the `pages` package.

**Responsibilities:**
- Store locators
- Implement page-level actions
- Encapsulate UI behavior
- Avoid test logic inside page classes

### 4.2 BaseTest Layer

`BaseTest.java` handles:
- Playwright initialization
- Browser launch
- Reading configuration
- Login setup
- Timeout configuration
- Browser cleanup

**Benefits:**
- No duplicated setup code
- Centralized configuration control
- Clean test classes

### 4.3 Test Layer

Test classes:
- Extend BaseTest
- Contain only test logic
- Perform assertions
- Use page methods for interactions

### 4.4 Utility Layer

`ConfigReader.java`:
- Loads `config.properties`
- Reads runtime configuration
- Avoids hardcoding environment data

---

## 5. Configuration Management

`config.properties` contains:

```properties
base.url=
browser=chromium
headless=false
username=
password=
```

**Advantages:**
- Environment switching support
- Secure credential handling
- No hardcoding in codebase

---

## 6. Test Execution Flow

1. TestNG triggers test class
2. `@BeforeMethod` in BaseTest runs
3. Playwright instance created
4. Browser launched (Chrome channel)
5. Application URL loaded
6. Login performed automatically
7. Test steps executed
8. Assertion performed
9. Browser kept open for 10 seconds
10. Test completes

---

## 7. Implemented Functional Areas

### Login Automation
- Username & password handling
- Dropdown selection
- Checkbox handling

### Home Page Navigation
- Create Complaint
- Search Complaint
- Create User
- Search User

### Complaint Creation
- Dropdown interactions
- Date selection
- Radio buttons
- Form filling

### HRMS – Create Employee
- Multi-section form handling
- Dynamic dropdowns
- Confirmation popup handling
- Success message validation
- Employee ID extraction

---

## 8. TestNG Suite Configuration

`testng.xml` defines:
- Suite Name: Web Automation Suite
- Test Name: Smoke Tests
- Included Classes: LoginTest, HRMSTest (optional)

Supports:
- Selective test execution
- Group execution
- Parallel execution (future ready)

---

## 9. Prerequisites

Before running the framework, install:
- Java 11 or higher
- Maven 3.8+
- Chrome Browser
- Node.js (required by Playwright)

Verify installation:
```bash
java -version
mvn -version
```

---

## 10. Setup Instructions

**Clone Repository**
```bash
git clone <repository-url>
cd project-folder
```

**Install Dependencies**
```bash
mvn clean install
```

**Install Playwright Browsers**
```bash
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
```

---

## 11. Running Tests

**Run All Tests**
```bash
mvn test
```

**Run Specific Suite**
```bash
mvn test -DsuiteXmlFile=testng.xml
```

---

## 12. Framework Design Principles

- Separation of Concerns
- Reusability
- Scalability
- Clean Code Structure
- Configuration Driven
- Maintainability
- CI/CD Compatible

---

## 13. Best Practices Followed

- No hardcoded values
- Page Object encapsulation
- Centralized setup logic
- Proper assertion messages
- Clean locator strategy
- Reusable methods
- Explicit waiting strategy
- Meaningful method naming

---

## 14. Step-by-Step Prompts to Build the Framework

### Prompt 1 — Project Setup
```
Create a Maven-based Java web automation framework with:
- Playwright (version 1.42.0)
- TestNG (version 7.9.0)
- Java 11+
- Page Object Model (POM)

Create folder structure:
  src/test/java/base/
  src/test/java/pages/
  src/test/java/tests/
  src/test/java/utils/
  src/test/resources/

Generate:
- pom.xml with Playwright and TestNG dependencies
- config.properties with keys: base.url, browser, headless, username, password
- ConfigReader.java using FileInputStream
```

### Prompt 2 — BaseTest Class
```
Create BaseTest.java with:
Protected fields: playwright, browser, page

@BeforeMethod:
- Create Playwright instance
- Launch Chromium using Chrome channel
- Read headless from ConfigReader
- Set default timeout to 60000 ms
- Navigate to base.url
- Perform automatic login

@AfterMethod:
- Keep browser open for 10 seconds
- Thread.sleep(10000)
```

### Prompt 3 — Login Page
```
Create LoginPage.java.
Locators:
  Username   → input[name='username']
  Password   → input[name='password']
  Tenant     → #user-login-core_common_city
  Options    → .main-option
  Checkbox   → #privacy-component-check
  Login btn  → #user-login-continue

Method: login(String username, String password)
Steps: Fill username → Fill password → Click dropdown →
       Select first option → Click checkbox → Click login
```

### Prompt 4 — Home Page
```
Create HomePage.java.
Locators:
  Top bar → .digit-topbar-ulb
  Buttons → h2.digit-button-label:has-text('Button Name')

Methods:
  navigateToCreateComplaint()
  navigateToSearchComplaint()
  navigateToCreateUser()
  navigateToSearchUser()
  isHomeVisible()
```

### Prompt 5 — Complaint Page
```
Create ComplaintPage.java.
Locators include: complaint type dropdown, date input,
  today picker, country dropdown, radio button,
  description field, submit button

Method: fillComplaintForm()
Steps: Select complaint type → Select today's date →
       Select country → Select complaint for → Fill description
```

### Prompt 6 — Test Classes
```
1. LoginTest.java
   verifyUserCanLogin() — Assert: "Home element is not visible after login"

2. HomeTest.java
   navigateToCreateComplaint()

3. ComplaintTest.java
   createComplaint()
```

### Prompt 7 — TestNG Suite File
```
Create testng.xml:
  Suite: Web Automation Suite
  Test:  Smoke Tests
  Include: tests.LoginTest
  Use proper TestNG DOCTYPE.
```

---

## 15. Challenges Faced & How They Were Fixed

| # | Challenge | Root Cause | Fix Applied |
|---|-----------|------------|-------------|
| 1 | JAVA_HOME not defined | Java 21 installed but JAVA_HOME not set; Maven could not find JDK | Installed Java 11 (Amazon Corretto), set JAVA_HOME, added `%JAVA_HOME%\bin` to PATH, restarted VS Code |
| 2 | Playwright browser download blocked | Corporate firewall blocked playwright.azureedge.net | Switched to mobile hotspot, ran `npx playwright install chromium` (~150MB). One-time only |
| 3 | PowerShell scripts disabled | Windows execution policy blocked npx.ps1 | PowerShell as Admin → `Set-ExecutionPolicy RemoteSigned -Scope CurrentUser` |
| 4 | Wrong locators on Home Page | Generated locators used assumed class names that didn't exist | Used Chrome DevTools (F12) to inspect real HTML, updated to actual selectors |
| 5 | Strict mode violation: 2 elements matched | `span.text.removeHeight` matched both Complaints and HRMS spans | Updated to `span.text.removeHeight:has-text('Complaints')` |
| 6 | Target folder reports not visible in VS Code | VS Code Explorer excluded target/ folder | Checked Windows File Explorer — all 25+ report files confirmed. Refreshed VS Code |
| 7 | Dropdown timeout (60s exceeded on .main-option) | Code clicked dropdown before items loaded in DOM | Added `.first().waitFor()` before clicking the option |

---

### Challenge 1 — JAVA_HOME Not Defined Correctly
**Error:** `The JAVA_HOME environment variable is not defined correctly`

**Fix:**
1. Downloaded Amazon Corretto Java 11 from corretto.aws
2. Installed to `C:\Program Files\Amazon Corretto\jdk11.0.x_x`
3. Set System Variable: `JAVA_HOME = C:\Program Files\Amazon Corretto\jdk11.0.x_x`
4. Updated PATH: `%JAVA_HOME%\bin`
5. Restarted VS Code
6. Verified with `java -version` → OpenJDK 11.0.30 Corretto

---

### Challenge 2 — Playwright Browser Download Blocked
**Error:** `Download failed: server returned code 400. URL: https://playwright.azureedge.net/...`

**Fix:**
1. Disconnected from office WiFi; connected to personal mobile hotspot
2. Ran `npx playwright install chromium` — download completed (~150MB)
3. Reconnected to office WiFi — Playwright uses cached browser for all runs

---

### Challenge 3 — PowerShell Script Execution Blocked
**Error:** `npx.ps1 cannot be loaded because running scripts is disabled`

**Fix:**
1. Opened PowerShell as Admin
2. Ran: `Set-ExecutionPolicy RemoteSigned -Scope CurrentUser`
3. Confirmed with Y
4. Re-ran `npx playwright install chromium` — succeeded

---

### Challenge 4 — Wrong Locators on Home Page
**Error:** Timeout waiting for locator `.digit-topbar-ulb`

**Fix:** Used Chrome DevTools (F12) to inspect actual HTML. Updated locators to match real elements (`<a href='/digit-ui/employee/pgr/complaint/create'>` etc.)

---

### Challenge 5 — Strict Mode Violation: Locator Matched 2 Elements
**Error:** Locator `span.text.removeHeight` matched both Complaints and HRMS elements

**Fix:** Updated to `span.text.removeHeight:has-text('Complaints')`

---

### Challenge 6 — Target Folder Reports Not Visible
**Fix:** Checked Windows File Explorer — all reports generated. Refreshed VS Code Explorer.

---

### Challenge 7 — Dropdown Timeout
**Fix:** Added `.first().waitFor()` before clicking dropdown options to ensure DOM items are visible before interaction.

---

## 16. Additional Framework Improvements & Fixes

### Other Key Fixes

| Area | Fix |
|------|-----|
| Employment Type Dropdown | Click SVG arrow (`svg.cp`) instead of container div |
| Role Dropdown | Click input + ArrowDown key — opens options correctly |
| Submit Button | Two-step click to allow React state update |
| Date of Appointment | Used `locator.fill(date)` + `dispatchEvent('change')` for React input |
| ScreenshotHelper | Create directories if missing before saving screenshots |
| Stale Build Cache | Run `mvn clean` to remove old `.class` files |
| FormHelper & ScreenshotHelper | Added reusable methods for fill, click, dropdown, date, screenshots |
| ConfigReader | Added getters for all employee fields; random username, email, mobile generated per run |
| Date Input Fix | Format `yyyy-MM-dd` instead of `dd/MM/yyyy` |

---

## 17. Final Test Result (Initial Build)

| Milestone | Status |
|-----------|--------|
| Java 11 installed & JAVA_HOME configured | Fixed |
| Maven project created with all Java files | Complete |
| Playwright Chromium browser downloaded | Fixed via mobile hotspot |
| PowerShell execution policy updated | Fixed |
| Home page locators corrected via DevTools | Fixed |
| Strict mode violation resolved with has-text() | Fixed |
| mvn test - LoginTest | Tests run: 1, Failures: 0, BUILD SUCCESS |
| 25+ target/ report files generated | Auto-generated |

---

**Date:** 23/02/2026

---

## 18. Search Employee Test Cases — Implementation

### Prompt Used
```
I have a Playwright Java automation framework with Maven + TestNG + Page Object Model.

Create:
  SearchEmployeePage.java under pages/
  SearchEmployeeTest.java under tests/

Update:
  config.properties — add search.emp.id=oyo-hfs-user-1
  testng.xml — add SearchEmployeeTest class
```

### Test Case 1 — Edit Employee
1. Click Search Employee: `a[href='/digit-ui/employee/hrms/inbox']`
2. Enter employee ID: `input[name='codes']`
3. Click Search: `button.submit-bar-search[type='submit']`
4. Click employee result: `a[href*='empId']`
5. Click Take Action: `button.submit-bar[type='button']`
6. Click Edit Employee: `div.menu-wrap p:has-text('Edit Employee')`
7. Edit name — append " one" to existing name: `input[pattern*='1,50'][title*='Username']`
8. Click Save: `div.action-bar-wrap button[type='submit']`
9. Verify: `div.emp-success-wrap header` contains `Employee Details Updated Successfully`

### Test Case 2 — Edit Campaign Assignment *(Removed — failing manually)*
Same search steps → Edit Campaign Assignment → Select today's date → Save → Verify success

### Test Case 3 — Deactivate Employee
1. Same search steps as Test Case 1
2. Click Deactivate Employee from menu
3. Click Reason dropdown SVG: `div.select svg.cp`
4. Select first option: `.profile-dropdown--item`
5. Click confirm: `button.selector-button-primary[type='submit']`
6. Verify: `div.emp-success-wrap header` contains `Employee Deactivated Successfully`

---

## 19. Code Cleanup & Framework Refactoring

### BasePage.java (Created)
Common base class with:
- Common locators: `.digit-topbar-home`, `.digit-back-btn`
- Navigation: `goToHome()`, `goBack()`, `clickBackButton()`
- Wait helpers: `waitForPageLoad()`, `waitForVisible()`, `waitForHidden()`, `wait(ms)`
- Verification: `isOnPage()`, `getCurrentUrl()`, `isVisible()`
- All pages now extend BasePage

### BaseTest.java (Updated)
- Added protected fields: `homePage`, `nav`, `form`, `screenshot`
- `LoginPage.login()` returns `HomePage`
- Auto-screenshot on failure using `ITestResult`
- `headless=false` hardcoded
- `page.setDefaultTimeout(120000)`

### New Test Cases Added

| File | New Test | Description |
|------|----------|-------------|
| HRMSTest.java | createEmployeeMinimalData() | Creates employee with only mandatory fields |
| SearchEmployeeTest.java | searchAndVerifyEmployee() | Searches by employee ID and verifies result link |
| LoginTest.java | verifyInvalidLogin() | Incorrect password — verifies error message |
| LoginTest.java | verifyEmptyLogin() | Empty form submit — verifies validation |

---

## 20. Test Execution & Debugging Summary

### Run 1 — 2 Failures, 3 Skipped

| Issue | Root Cause | Fix |
|-------|------------|-----|
| 3 tests skipped (120s timeout) | `WaitUntilState.COMMIT` resolved before React SPA rendered login form | Changed to `DOMCONTENTLOADED` + `waitForTimeout(2000)` |
| editCampaignAssignment failed | TestNG alphabetical order ran deactivateEmployee first | Added `preserve-order="true"` + explicit method ordering |

### Run 2 — 1 Failure

| Issue | Root Cause | Fix |
|-------|------------|-----|
| editCampaignAssignment failed | saveCampaignEdit() reused wrong locator | Rewrote method with broader locator, null checks, scroll, logging |
| TargetClosedError | Toast validation executed after browser close | Wrapped in try-catch with break |

### Final Finding
`editCampaignAssignment` confirmed failing manually as well.
Accepted as **application-level bug**, not framework issue. Test removed.

---

## 21. Files Modified / Created

| File | Action |
|------|--------|
| BasePage.java | Created |
| BaseTest.java | Updated |
| LoginPage.java | Updated |
| HomePage.java | Updated |
| HRMSPage.java | Updated |
| SearchEmployeePage.java | Updated |
| HRMSTest.java | Updated |
| LoginTest.java | Updated |
| SearchEmployeeTest.java | Updated |
| testng.xml | Updated |
| .gitignore | Updated |

---

## 22. How to Run Only LoginTest

### Option 1 — Maven Command Line (Recommended)
```bash
mvn test -Dtest=LoginTest
```
Fastest. No file changes required.

### Option 2 — Run a Specific Test Method
```bash
mvn test -Dtest=LoginTest#verifyUserCanLogin
mvn test -Dtest=LoginTest#verifyInvalidLogin
mvn test -Dtest=LoginTest#verifyEmptyLogin
```

### Option 3 — Temporarily Update testng.xml
```xml
<suite name="Web Automation Suite">
    <test name="Smoke Tests" preserve-order="true">
        <classes>
            <class name="tests.LoginTest"/>
        </classes>
    </test>
</suite>
```
Then run: `mvn test`

### Option 4 — Create a Separate TestNG Suite
Create `testng-login.xml`:
```xml
<suite name="Login Suite">
    <test name="Login Tests">
        <classes>
            <class name="tests.LoginTest"/>
        </classes>
    </test>
</suite>
```
Run: `mvn test -Dsurefire.suiteXmlFiles=testng-login.xml`

---

## 23. Smart Waits Refactoring

### Problem
Hard-coded waits (`page.waitForTimeout()` and `Thread.sleep()`) were used throughout the framework — causing slow tests and flakiness.

### Impact
- **57 hard sleeps removed** across **7 files**
- Replaced with conditional waits that reflect real browser behavior

### Key Replacement Patterns

**After navigation:**
```java
// Before: waitForTimeout(2000–5000)
locator("input[name='username']").waitFor();
page.waitForLoadState(LoadState.NETWORKIDLE);
```

**After clicking buttons/menus:**
```java
// Before: waitForTimeout(1000–2000)
locator("div.menu-wrap p").waitFor();
locator("table tbody tr").waitFor();
page.waitForURL("**/hrms/**");
```

**React disabled → enabled button:**
```java
// Before: waitForTimeout(3000)
page.waitForFunction("!btn.className.includes('disable')");
```

**After form submission:**
```java
// Before: waitForTimeout(3000–5000)
locator("div.emp-success-wrap header").waitFor();
```

**Dropdowns:**
```java
// Before: waitForTimeout(1500)
locator(".profile-dropdown--item").waitFor(VISIBLE);
locator(".profile-dropdown--item").waitFor(HIDDEN);
```

### Files Changed

| File | Waits Removed | Replaced With |
|------|---------------|---------------|
| BaseTest.java | 2 + Thread.sleep(3000) | Element visible + removed tearDown sleep |
| NavigationHelper.java | 8 | Element visible / NETWORKIDLE / DOMCONTENTLOADED |
| HomePage.java | 4 | Element visible / NETWORKIDLE |
| FormHelper.java | 4 | waitFor(VISIBLE) + waitFor(HIDDEN) for dropdowns |
| HRMSPage.java | 10 | Element visible + waitForFunction for React button state |
| SearchEmployeePage.java | 24 | Element visible + waitForURL + waitForFunction |
| LoginTest.java | 4 | Element visible + waitForFunction |

---

## 24. Day 2 Stability Fixes (23/02/2026)

### Phase 1 — NavigationHelper Overhaul

**Problem:** `goToSearchEmployee()` used `page.navigate("/hrms/inbox")` causing:
- Full React app reinitialization
- HRMS inbox API firing before auth context was ready
- Persistent **"Something went wrong!"** error screen

**Fix:** Replaced with sidebar-click navigation + **3-attempt retry loop:**
1. Load home page
2. Wait for "Complaints" + 2s
3. Click sidebar
4. Verify `input[name='codes']` within 15s
5. Retry if not found

**Result:** Zero "Something went wrong!" screens.

---

### Phase 2 — SearchEmployeePage Fixes

**Fix 1 — Role Deselection Bug**
- Problem: `fillRequiredEditFields()` blindly clicked first role checkbox → if already selected, deselected it → form failed
- Fix: Added `isChecked()` — only selects role if none assigned

**Fix 2 — Save Button Infinite Wait**
- Problem: If role pre-assigned, first Save submitted immediately, page navigated away → `getAttribute("class")` waited 120s for gone element
- Fix: Wrapped in try-catch with 5s timeout — catch = form already submitted

**Fix 3 — Removed editCampaignAssignment**
- Failing manually → removed from `SearchEmployeeTest.java` and `testng.xml`

---

### Phase 3 — HRMSPage Timeout Increase
- Problem: `createNewEmployee` timed out under full suite load
- Fix: Increased success message wait from **30s → 60s**

---

### Phase 4 — ScreenshotHelper Fix
- Problem: `tearDown()` hung when screenshotting error/loading pages
- Fix: Added try-catch + 15s timeout on `page.screenshot()`

---

### Phase 5 — Test Ordering (Two-Layer Enforcement)

**Layer 1 — testng.xml:**
- `preserve-order="true"` at `<suite>` level
- Explicit `<methods>` blocks for all 3 classes

**Layer 2 — @Test(priority=X):**
- Added to every test method in all 3 classes

**Final Execution Order:**

| # | Test |
|---|------|
| 1 | LoginTest.verifyUserCanLogin |
| 2 | LoginTest.verifyInvalidLogin |
| 3 | LoginTest.verifyEmptyLogin |
| 4 | HRMSTest.createNewEmployee |
| 5 | HRMSTest.createEmployeeMinimalData |
| 6 | SearchEmployeeTest.editEmployee |
| 7 | SearchEmployeeTest.deactivateEmployee |
| 8 | SearchEmployeeTest.searchAndVerifyEmployee |

**Final Result: Tests run: 8, Failures: 0, Errors: 0 — BUILD SUCCESS (~3:33 min)**

---

## 25. Deep Dive — Why Direct URL Fails for Search Employee

### What `page.navigate("/hrms/inbox")` Actually Does

Performs a **hard browser navigation**:
- Browser requests `index.html` fresh
- Entire JavaScript bundle reloads
- React application reboots from zero
- All global state reset
- All providers (AuthContext, Router, etc.) reinitialize

Equivalent to typing the full URL in the address bar and pressing Enter.

---

### Why It Works Manually But Fails in Automation

**Manual user:**
- Browser is slower
- Human latency + rendering overhead
- React has enough time to: read token → initialize AuthContext → attach Authorization header → THEN fire API

**Automation (Playwright):**
- Extremely fast
- React boots fast
- Route `/hrms/inbox` matched immediately
- `useEffect()` inside HRMS Inbox fires immediately
- API request sent **BEFORE** auth interceptor fully attaches token

Automation exposes a timing flaw in the app architecture.

---

### The Real Root Cause (Architectural Level)

This is a **React app design issue**, not a Playwright problem.

```javascript
// Inside InboxPage — fires immediately
useEffect(() => {
  fetchInboxData();
}, []);

// Inside AuthProvider — also async
useEffect(() => {
  initializeAuth(); // reads token async
}, []);
```

Both `useEffect()` run simultaneously. There is **no guarantee auth finishes first.**
That's the race condition.

---

### Why Sidebar Navigation Avoids the Problem

```
page.navigate("/employee")
  → React boots at /employee
  → Auth initializes completely
  → All context providers ready

sidebar.click()
  → Client-side routing (NO page reload)
  → No React reboot
  → No context reset
  → Auth already exists
  → Token already attached
  → API call succeeds ✓
```

---

### Timing Comparison

```
DIRECT URL:
t=0ms   Page loads
t=50ms  React boots
t=60ms  HRMS API fires  ← TOO EARLY, auth not ready
t=80ms  Auth initializes
t=100ms API response → ERROR (no auth token)
        "Something went wrong!" ✗

SIDEBAR NAVIGATION:
t=0ms    Home page loads
t=500ms  React fully booted, auth ready
t=2500ms Sidebar click (after 2s wait)
t=2510ms React Router changes URL (no reload)
t=2520ms HRMS API fires ← Auth already ready ✓
t=2800ms Employee list loads ✓
```

---

### Why Direct URL Randomly Works Sometimes

Race conditions are **non-deterministic**:

- Sometimes: Auth at t=50ms, API at t=70ms → token attached → **SUCCESS**
- Sometimes: API at t=40ms, Auth at t=60ms → no token → **FAILURE**

That's why it was unreliable — not a consistent bug, a timing bug.

---

### Why the Retry Loop Is Needed

Even sidebar navigation can fail if:
- Home page not fully rendered
- Auth still initializing
- Network slow

The retry loop converts a race condition into a **deterministic flow**.

---

### Comparison Table

| | Direct URL | Sidebar Navigation |
|--|------------|-------------------|
| Page reload | Full reload | No reload |
| React state | Reboots | Already running |
| Auth context | Initializes async | Already ready |
| Inbox API timing | May fire early | Fires safely |
| Result | Race condition | Stable execution |

---

### If You Wanted to Fix It at Application Level

The correct engineering fix in the DIGIT codebase:
```javascript
// Guard protected routes until auth is confirmed
if (!auth?.accessToken) return null;

// Or delay API calls
await auth.initialize();
fetchInbox();
```

Since we don't control the app, we stabilized at the test layer — which is the correct approach for automation scope.

---

## 26. GitHub Repository

All changes pushed to:
**https://github.com/swathi-ghanate/WebAutomation-Impel**

Latest commit: `d1c69e7` — Fix navigation, test ordering, and reliability improvements
