package playwright;

import com.microsoft.playwright.*;

public class PlaywrightManagement {
    private static Playwright playwright;
    private static Browser browser;
    private static Page page;

    public static Page setupPlaywright(String url) {
        // Инициализация Playwright и браузера, если они ещё не созданы
        if (playwright == null) {
            playwright = Playwright.create();
            browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        }

        // Проверка и создание страницы, если она не была создана или закрыта
        if (page == null || page.isClosed()) {
            page = browser.newPage();
            page.navigate(url);
            System.out.println(page.title());
        } else {
            // Если страница уже существует и открыта, просто навигируем на URL
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