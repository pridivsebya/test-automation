package UI;

import com.google.inject.Inject;
import com.microsoft.playwright.Page;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;
import com.microsoft.playwright.Locator;
import playwright.PlaywrightManagement;

import javax.swing.*;

@Guice
public class PlaywrightTest {
    @Inject
    private PlaywrightManagement playwrightManagement;

    @Test
    public void openPage() {
        String url = "https://www.ozon.ru/";
        playwrightManagement.setupPlaywright(url);
    }
    @Test
    public void searchPage() {
        String url = "https://www.ozon.ru/?__rr=1&abt_att=1&origin_referer=www.google.com";
        Page page = playwrightManagement.setupPlaywright(url);

        page.locator("#stickyHeader > div > div.i3d_9 > div > div > form > div > div.iaa5_33 > input").fill("Книга");
        page.click("#stickyHeader > div > div.i3d_9 > div > div > form > button > div");

        String pageContent = page.content();
        if (pageContent.contains("Книги")) {
            System.out.println("Страница откылась");
        } else {
            System.out.println("Страница не открылась :(");
        }
    }
    }
