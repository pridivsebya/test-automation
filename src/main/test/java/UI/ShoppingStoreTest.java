package UI;

import com.google.inject.Inject;
import com.microsoft.playwright.Page;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;
import playwright.*;
import io.qameta.allure.Description;
import io.qameta.allure.Step;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static io.qameta.allure.Allure.step;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Guice(modules = PlaywrightModule.class)
public class ShoppingStoreTest {

    @Inject
    private ShoppingPage shoppingPage;
    @Inject
    private AbstractPage abstractPage;
    @Inject
    private AuthorizationPage authorizationPage;
    @Inject
    private PlaywrightManagement playwrightManagement;
    @Inject
    private Page page;
    @Inject
    private CheckoutPage checkoutPage;



    @Test
    @Description("Тест-кейс LOG1. Авторизация с корректным логином и/или паролем")
    public void LOG1() {

        step("Инициализация страницы");
        page = PlaywrightManagement.setupPlaywright("https://www.saucedemo.com/");

        assert authorizationPage.loginField().isVisible() : "Поле логина отсутствует";
        assert authorizationPage.passwordField().isVisible() : "Поле пароля отсутствует";
        assert authorizationPage.buttonLogin().isVisible() : "Кнопка отсутствует";

        step("Вход в систему");
        authorizationPage.UserLogin("standard_user", "secret_sauce");

        step("Авторизация");
        authorizationPage.loggedIn();
    }



    @Test
    @Description("Тест-кейс LOG2. Авторизация с некорректным логином и/или паролем")
    public void LOG2() {

        step("Инициализация страницы");
        page = PlaywrightManagement.setupPlaywright("https://www.saucedemo.com/");

        step("Оставить поля ввода пустыми");
        authorizationPage.UserLogin("", "");
       // assert authorizationPage.errorMessage().isVisible() : "Локатор ошибки не найден";

        abstractPage.reloadPage();

        step("Ввести логин, а поле пароля оставить пустым");
        authorizationPage.UserLogin("standard_user", "");
        assert authorizationPage.errorMessage().isVisible() : "Локатор ошибки не найден";

        abstractPage.reloadPage();

        step("Ввести пароль, а поле логина оставить пустым");
        authorizationPage.UserLogin("", "secret_sauce");
        assert authorizationPage.errorMessage().isVisible() : "Локатор ошибки не найден";

        abstractPage.reloadPage();

        step("Ввести неверный логин и ввести правильный пароль");
        authorizationPage.UserLogin("no-standard_user", "secret_sauce");
        assert authorizationPage.errorMessage().isVisible() : "Локатор ошибки не найден";

        abstractPage.reloadPage();

        step("Ввести неверный пароль и правильный логин");
        authorizationPage.UserLogin("standard_user", "no-secret_sauce");
        assert authorizationPage.errorMessage().isVisible() : "Локатор ошибки не найден";
    }



    @Test
    @Description("Тест-кейс HOM1.Проверка наличия основных атрибутов на главной странице")
    public void HOM1() {

        step("Инициализация страницы");
        page = PlaywrightManagement.setupPlaywright("https://www.saucedemo.com/");

        step("Вход в систему");
        authorizationPage.UserLogin("standard_user", "secret_sauce");

        step("Проверка наличия всех атрибутов");
        assert shoppingPage.addToCartButton().isVisible() : "Кнопка добавить в корзину отсутствует";
        assert shoppingPage.shoppingCart().isVisible() : "Кнопка корзины отсутствует";
        assert shoppingPage.removeButton().isVisible() : "Кнопка удалить из корзины отсутствует";
        assert shoppingPage.title().isVisible() : "Заголовок отсутствует";
        assert shoppingPage.inventoryList().isVisible() : "Лист с продуктами отсутствует";
        assert shoppingPage.openMenu().isVisible() : "Кнопка открытия меню отсутствует";
    }



    @Test
    @Description("Тест-кейс HOM2. Добавление товара в корзинину и последующее его удаление")
    public void HOM2() {

        step("Инициализация страницы");
        page = PlaywrightManagement.setupPlaywright("https://www.saucedemo.com/");

        step("Вход в систему");
        authorizationPage.UserLogin("standard_user", "secret_sauce");

        step("Добавить в корзину все товары");
        for (int i = 0; i < shoppingPage.addToCartButton().count(); i++) {
            shoppingPage.addToCartButton().nth(i).click();
        }
        step("Удалить из корзины все добавленные товары с главной страницы");
        for (int i = 0; i < shoppingPage.removeButton().count(); i++) {
            shoppingPage.addToCartButton().nth(i).click();
        }
    }



    @Test
    @Description("Тест-кейс HOM3.Функциональность карточки товара")
    public void HOM3() {

        step("Инициализация страницы");
        page = PlaywrightManagement.setupPlaywright("https://www.saucedemo.com/");

        step("Вход в систему");
        authorizationPage.UserLogin("standard_user", "secret_sauce");

        step("Открыть карточку товара нажатием на картинку");
        // abstractPage.click(shoppingPage.productImg());

        step("Вернуться на главную страницу");
        shoppingPage.buttonBack();

        step("Открыть карточку товара нажатием на название товара");
        // abstractPage.click(shoppingPage.productName());

        step("Положить товар в корзину");
        shoppingPage.addToCartButton();

        step("Удалить товар из корзины");
        shoppingPage.removeButton();

        step("Вернуться на главную страницу");
        shoppingPage.buttonBack();
    }



    @Test
    @Description("Тест-кейс HOM4.Функциональность фильтра товаров")
    public void HOM4() {
        step("Инициализация страницы");
        page = PlaywrightManagement.setupPlaywright("https://www.saucedemo.com/");

        step("Вход в систему");
        authorizationPage.UserLogin("standard_user", "secret_sauce");

        step("Проверка фильтрации a to z");
        shoppingPage.selectFilter("az");
        List<String> productNames = shoppingPage.getProductNames();
        List<String> sortedNamesAZ = productNames.stream().sorted().collect(Collectors.toList());

        assertEquals(productNames, sortedNamesAZ, "Сортировка a to z некорректна");

        step("Проверка фильтрации z to a");
        shoppingPage.selectFilter("za");
        List<String> sortedNamesZA = productNames.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());

        assertEquals(productNames, sortedNamesZA, "Сортировка z to a некорректна");

        step("Проверка фильтрации low to high");
        shoppingPage.selectFilter("lohi");
        List<Double> productPrices = shoppingPage.getProductPrices();
        List<Double> sortedPricesLH = productPrices.stream().sorted().collect(Collectors.toList());

        assertEquals(productPrices, sortedPricesLH, "Сортировка low to high некорректна");

        step("Проверка фильтрации high to low");
        shoppingPage.selectFilter("hilo");
        List<Double> sortedPricesHL = productPrices.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());

        assertEquals(productPrices, sortedPricesHL, "Сортировка high to low некорректна");
    }



    @Test
    @Description("Тест-кейс HOM5.Работоспособность кнопок соц-сетей")
    public void HOM5() {
        step("Инициализация страницы");
        page = PlaywrightManagement.setupPlaywright("https://www.saucedemo.com/");

        step("Вход в систему");
        authorizationPage.UserLogin("standard_user", "secret_sauce");

        step("Нажимаем на иконку Твиттера");
        abstractPage.click(shoppingPage.socialTwitter());

        step("Переходим на новую вкладку");
        shoppingPage.navigateToNewTab(page);

        step("Проверка совпадения страниц");
        assert shoppingPage.isTwitterPagesOpened(page): "Открылась не та страница Twitter";

        step("Нажимаем на иконку Фейсбука");
        abstractPage.click(shoppingPage.socialFacebook());

        step("Переходим на новую вкладку");
        shoppingPage.navigateToNewTab(page);

        step("Проверка совпадения страниц");
        assert shoppingPage.isFacebookPagesOpened(page): "Открылась не та страница Facebook";

        step("Нажимаем на иконку Linkedin");
        abstractPage.click(shoppingPage.socialLinkedin());

        step("Переходим на новую вкладку");
        shoppingPage.navigateToNewTab(page);

        step("Проверка совпадения страниц");
        assert shoppingPage.isLinkedinPagesOpened(page): "Открылась не та страница Linkedin";
    }



    @Test
    @Description("Тест-кейс BAS1. Функциональность корзины")
    public void BAS1() {

        step("Инициализация страницы");
        page = PlaywrightManagement.setupPlaywright("https://www.saucedemo.com/");

        step("Вход в систему");
        authorizationPage.UserLogin("standard_user", "secret_sauce");

        step("Добавить товар в корзину");
        // abstractPage.click(shoppingPage.addToCartButton());

        step("Перейти в корзину и проверить, что добавлен именно этот товар");
       // abstractPage.click(shoppingPage.shoppingCart());
        assert shoppingPage.backpackInTheCart(page): "В корзине не тот товар";

        step("Нажать на кноку удалить и удостовериться, что товар удален из корзины");
        abstractPage.click(shoppingPage.removeButton());
        if (page.locator("#cart_contents_container > div > div.cart_list > div.cart_item").isVisible()) {
            System.out.println("Продукт не удален из корзины");
        }

        step("Вернуться на главный экран с помощью специальной кнопки");
        abstractPage.click(shoppingPage.continueShoppingButton());
        }



    @Test
    @Description("Тест-кейс BAS2. Оформление заказа с валидными данными")
    public void BAS2() {

        step("Инициализация страницы");
        page = PlaywrightManagement.setupPlaywright("https://www.saucedemo.com/");

        step("Вход в систему");
        authorizationPage.UserLogin("standard_user", "secret_sauce");

        step("Добавить товар в корзину");
        // abstractPage.click(shoppingPage.addToCartSomeone());

        step("Перейти в корзину и проверить, что добавлен именно этот товар");
        abstractPage.click(shoppingPage.shoppingCart());
        assert shoppingPage.backpackInTheCart(page) : "В корзине не тот товар";

        step("Перейти на страницу оплаты с помощью специальной кнопки");
        abstractPage.click(checkoutPage.checkout());

        step("Ввести личную информацию для оформления заказа");
        checkoutPage.checkoutInfo("MyFirstName", "MyLastName", 444);

        step("Завершить оформление заказа");
        abstractPage.click(checkoutPage.finishButton());
    }
    }