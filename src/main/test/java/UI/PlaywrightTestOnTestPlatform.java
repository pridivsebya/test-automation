package UI;

import com.microsoft.playwright.Page;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;
import playwright.PlaywrightManagement;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import playwright.AuthorizationPage;

@Guice
public class PlaywrightTestOnTestPlatform {

    private AuthorizationPage authorizationPage;

    @Test
    @Description("Зайти в систему тестовой площадки")
    public void TestPlatform() {
        String url = "https://www.saucedemo.com/";
        Page page = openUrl(url);

        authorizationPage = new AuthorizationPage(page);

        authorizationPage.fillUserName("standard_user");
        authorizationPage.fillUserPass("secret_sauce");

        authorizationPage.pressButton();

        openPage(page);
    }

    @AfterSuite
        public void tearDown() {
        closePlaywright();
        }

    @Step("Открытие страницы по url")
    public Page openUrl(String url) {
        return PlaywrightManagement.setupPlaywright(url);
    }

    @Step("Проверка открытия страницы")
    public void openPage(Page page) {
         if (authorizationPage.isAuthorized()) {
             System.out.println("Вы вошли в систему");
         } else {
             System.out.println("Ошибка авторизации");
         }
    }
    @Step("Закрытие Playwright")
    public void closePlaywright() {
         PlaywrightManagement.close();
        }
    }

