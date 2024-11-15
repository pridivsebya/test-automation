package UI;

import com.microsoft.playwright.Page;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;
import playwright.PlaywrightManagement;
import io.qameta.allure.Description;
import io.qameta.allure.Step;


@Guice
public class PlaywrightTest {

    @Test
    @Description("Открытие страницы Google")
    public void openPage() {
        String url = "https://google.ru";
        Page page = PlaywrightManagement.setupPlaywright(url);
    }

    @Test
    @Description("Поиск и открытие страницы в Яндексе")
    public void searchPage() {
        String url = "https://ya.ru/";
        Page page = openUrl(url);

        searchText(page, "Книга");

        openPage(page);
    }

    @AfterSuite
    public void tearDown() {
        closePlaywright();
    }

    @Step("Открытие страницы по URL")
    public Page openUrl(String url) {
        return PlaywrightManagement.setupPlaywright(url);

    }

    @Step("Заполнение поля ввода текстом")
    public void searchText(Page page, String value) {
        page.waitForSelector("#text");
        page.locator("#text").fill(value);
        page.keyboard().press("Enter");

    }

    @Step("Проверка открытия страницы")
    public void openPage(Page page) {
        page.waitForSelector("#RelatedBottom", new Page.WaitForSelectorOptions().setTimeout(10000));
        if (page.locator("#RelatedBottom").isVisible()) {
            System.out.println("Страница открылась");
        } else {
            System.out.println("Страница не открылась");
        }
    }

    @Step("Закрытие Playwright")
    public void closePlaywright() {
        PlaywrightManagement.close();
        }
    }

