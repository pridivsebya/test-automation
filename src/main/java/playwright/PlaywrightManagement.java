package playwright;

import com.microsoft.playwright.*;

public class PlaywrightManagement {
    private static Playwright playwright;
    private static Browser browser;
    private static Page page;

    public static Page setupPlaywright(String url) {
        if (playwright == null) {
            playwright = Playwright.create();
            browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        }

        if (page == null || page.isClosed()) {
            page = browser.newPage();
            page.navigate(url);
            System.out.println(page.title());
        } else {
            page.navigate(url);
        }

        return page;
    }

    public static void close() {
        if (page != null) page.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}