package playwright;

import com.microsoft.playwright.Page;
import io.qameta.allure.Step;


public class AuthorizationPage {
    private final Page page;

    public AuthorizationPage(Page page) {
        this.page = page ;
    }

    @Step("Авторизация")
    public void UserLogin(String name, String pass) {
        page.waitForSelector("#user-name");
        page.waitForSelector("#password");
        page.locator("#user-name").fill(name);
        page.locator("#password").fill(pass);
        page.locator("#login-button").click();
    }

    public boolean isAuthorized() {
        return page.locator("#page_wrapper > footer > div").isVisible();
    }
}
