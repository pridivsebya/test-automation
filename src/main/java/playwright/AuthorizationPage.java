package playwright;

import com.microsoft.playwright.Page;


public class AuthorizationPage {
    private final Page page;

    public AuthorizationPage(Page page) {
        this.page = page ;
    }
    public void fillUserName(String value) {
        page.waitForSelector("#user-name");
        page.locator("#user-name").fill(value);
    }
    public void fillUserPass(String value) {
        page.waitForSelector("#password");
        page.locator("#password").fill(value);
    }
    public void pressButton() {
        page.locator("#login-button").click();
    }
    public boolean isAuthorized() {
        return page.locator("#page_wrapper > footer > div").isVisible();
    }
}
