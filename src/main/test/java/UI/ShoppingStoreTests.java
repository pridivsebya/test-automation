package UI;

import com.microsoft.playwright.Page;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;
import playwright.PlaywrightManagement;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import playwright.AuthorizationPage;

import static io.qameta.allure.Allure.step;

@Guice
public class ShoppingStoreTests {

    private AuthorizationPage authorizationPage;

    @Test
    @Description("Зайти в систему тестовой площадки")
    public void TestPlatform() {

        step("Инициализация страницы");
        String url = "https://www.saucedemo.com/";
        Page page = openUrl(url);

        step("Инициализация методов");
        authorizationPage = new AuthorizationPage(page);

        step("Вход в систему");
        authorizationPage.UserLogin("standard_user", "secret_sauce");

        step("Вы вошли в систему");
        loggedIn(page);
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
    public void loggedIn(Page page) {
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
