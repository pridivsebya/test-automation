package playwright;

import com.microsoft.playwright.*;

public class PlaywrightManagement {
    public Page setupPlaywright(String url) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            Page page = browser.newPage();
            page.navigate(url);
            System.out.println(page.title());
            return page;
        }
    }
}
