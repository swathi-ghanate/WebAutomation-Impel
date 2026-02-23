package utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class FormHelper {

    private final Page page;

    public FormHelper(Page page) {
        this.page = page;
    }

    // Fill any text input safely
    public void fill(String selector, String value) {
        page.locator(selector).first().waitFor();
        page.locator(selector).first().fill(value);
        System.out.println("[FormHelper] Filled: " + selector + " = " + value);
    }

    // Fill with force — bypasses overlays
    public void fillForce(String selector, String value) {
        page.locator(selector).first()
                .fill(value, new Locator.FillOptions().setForce(true));
        System.out.println("[FormHelper] Force filled: " + selector + " = " + value);
    }

    // Fill nth element with force
    public void fillForceNth(String selector, int index, String value) {
        page.locator(selector).nth(index)
                .fill(value, new Locator.FillOptions().setForce(true));
        System.out.println("[FormHelper] Force filled nth(" + index + "): " + selector);
    }

    // Click with force — bypasses overlays
    public void clickForce(String selector) {
        page.locator(selector).first()
                .click(new Locator.ClickOptions().setForce(true));
        System.out.println("[FormHelper] Force clicked: " + selector);
    }

    // Click using dispatchEvent — works on SVG and React elements
    public void clickDispatch(String selector) {
        page.locator(selector).first().dispatchEvent("click");
        System.out.println("[FormHelper] Dispatch clicked: " + selector);
    }

    // Click nth using dispatchEvent
    public void clickDispatchNth(String selector, int index) {
        page.locator(selector).nth(index).dispatchEvent("click");
        System.out.println("[FormHelper] Dispatch clicked nth(" + index + "): " + selector);
    }

    // Open dropdown by clicking SVG and select option by index
    public void selectDropdown(int svgIndex, int optionIndex) {
        page.locator("div.select").nth(svgIndex).scrollIntoViewIfNeeded();
        page.waitForTimeout(300);

        // Click the SVG arrow to open
        Locator svg = page.locator("div.select svg.cp");
        if (svg.count() > svgIndex) {
            svg.nth(svgIndex).dispatchEvent("click");
        } else {
            page.locator("div.select").nth(svgIndex).dispatchEvent("click");
        }
        page.waitForTimeout(1500);

        // Select option
        int count = page.locator(".profile-dropdown--item").count();
        System.out.println("[FormHelper] Dropdown options visible: " + count);
        if (count > optionIndex) {
            page.locator(".profile-dropdown--item").nth(optionIndex).dispatchEvent("click");
            System.out.println("[FormHelper] Selected option index: " + optionIndex);
        }
        page.waitForTimeout(500);
        page.keyboard().press("Escape");
        page.waitForTimeout(300);
    }

    // Fill date field via JavaScript — bypasses strict mode issues
    public void fillDateJs(String maxContains, String value) {
        page.evaluate(
                "document.querySelector('input[type=\"date\"][max*=\"" + maxContains + "\"]').value = '" + value + "'");
        System.out.println("[FormHelper] JS filled date: max*=" + maxContains + " = " + value);
    }

    // Scroll to element
    public void scrollTo(String selector) {
        page.locator(selector).first().scrollIntoViewIfNeeded();
    }

    // Wait for element
    public void waitFor(String selector) {
        page.locator(selector).first().waitFor();
    }
}