package UI;

import com.microsoft.playwright.Page;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;
import playwright.PlaywrightManagement;

@Guice
public class PlaywrightTest {

    @Test
    public void openPage() {
        String url = "https://google.ru";
        Page page = PlaywrightManagement.setupPlaywright(url);
    }

    @Test
    public void searchPage() {
        String url = "https://ya.ru/";
        Page page = PlaywrightManagement.setupPlaywright(url);

        page.waitForSelector("#text");

        page.locator("#text").fill("Книга");
        page.keyboard().press("Enter");

        page.waitForSelector("#RelatedBottom", new Page.WaitForSelectorOptions().setTimeout(10000));

        if (page.locator("#RelatedBottom").isVisible()) {
            System.out.println("Страница открылась");
        } else {
            System.out.println("Страница не открылась :(");
        }
    }

    @AfterSuite
    public void tearDown() {
        PlaywrightManagement.close();
    }
}