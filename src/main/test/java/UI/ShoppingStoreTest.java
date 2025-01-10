package UI;

import com.google.inject.Inject;
import com.microsoft.playwright.Page;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;
import playwright.*;
import io.qameta.allure.Description;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static io.qameta.allure.Allure.step;
import static org.testng.Assert.assertEquals;

@Guice(modules = PlaywrightModule.class)
public class ShoppingStoreTest {

    @Inject
    private ShoppingPage shoppingPage;
    @Inject
    private AbstractPage abstractPage;
    @Inject
    private AuthorizationPage authorizationPage;
    @Inject
    private Page page;
    @Inject
    private CheckoutPage checkoutPage;



    @Test
    @Description("Тест-кейс LOG1. Авторизация с корректным логином и паролем")
    public void AuthorizationWithTheCorrectLoginAndPassword() {

        step("Инициализация страницы");
        page = PlaywrightManagement.setupPlaywright("https://www.saucedemo.com/");

        assert authorizationPage.loginField().isVisible() : "Поле логина отсутствует";
        assert authorizationPage.passwordField().isVisible() : "Поле пароля отсутствует";
        assert authorizationPage.buttonLogin().isVisible() : "Кнопка отсутствует";

        step("Вход в систему");
        authorizationPage.UserLogin("standard_user", "secret_sauce");

        step("Авторизация");
        authorizationPage.loggedIn();

        step("Закрытие браузера");
        abstractPage.closePlaywright();
    }



    @Test
    @Description("Тест-кейс LOG2. Авторизация с некорректным логином и/или паролем")
    public void AuthorizationWithIncorrectLoginAndOrPassword() {

        step("Инициализация страницы");
        page = PlaywrightManagement.setupPlaywright("https://www.saucedemo.com/");

        step("Оставить поля ввода пустыми");
        authorizationPage.UserLogin("", "");
        assert authorizationPage.errorMessage().isVisible() : "Локатор ошибки не найден";

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

        step("Закрытие браузера");
        abstractPage.closePlaywright();
    }



    @Test
    @Description("Тест-кейс HOM1.Проверка наличия основных атрибутов на главной странице")
    public void CheckingThePresenceOfBasicAttributesOnTheMainPage() {

        step("Инициализация страницы");
        page = PlaywrightManagement.setupPlaywright("https://www.saucedemo.com/");

        step("Вход в систему");
        authorizationPage.UserLogin("standard_user", "secret_sauce");

        step("Проверка наличия всех атрибутов");
        for (int i = 0; i < shoppingPage.addToCartButton().count(); i++) {
            assert shoppingPage.addToCartButton().nth(i).isVisible() : "Кнопка добавить в корзину отсутствует"; }
        assert shoppingPage.shoppingCart().isVisible() : "Кнопка корзины отсутствует";
        for (int i = 0; i < shoppingPage.removeButton().count(); i++) {
            assert shoppingPage.removeButton().isVisible() : "Кнопка удалить из корзины отсутствует"; }
        assert shoppingPage.title().isVisible() : "Заголовок отсутствует";
        assert shoppingPage.inventoryList().isVisible() : "Лист с продуктами отсутствует";
        assert shoppingPage.openMenu().isVisible() : "Кнопка открытия меню отсутствует";

        step("Закрытие браузера");
        abstractPage.closePlaywright();
    }



    @Test
    @Description("Тест-кейс HOM2. Добавление товара в корзину и последующее его удаление")
    public void AddingAProductToCartAndThenDeletingIt() {

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

            shoppingPage.removeButton().nth(i).click();
        }

        step("Закрытие браузера");
        abstractPage.closePlaywright();
    }



    @Test
    @Description("Тест-кейс HOM3.Функциональность карточки товара")
    public void ProductCardFunctionality() {

        step("Инициализация страницы");
        page = PlaywrightManagement.setupPlaywright("https://www.saucedemo.com/");

        step("Вход в систему");
        authorizationPage.UserLogin("standard_user", "secret_sauce");

        step("Открыть карточку товара нажатием на картинку");
        shoppingPage.productImg().click();

        step("Вернуться на главную страницу");
        shoppingPage.buttonBack().click();

        step("Открыть карточку товара нажатием на название товара");
        shoppingPage.productName().click();

        step("Положить товар в корзину");
        shoppingPage.addToCartButton().click();

        step("Удалить товар из корзины");
        shoppingPage.removeButton().click();

        step("Вернуться на главную страницу");
        shoppingPage.buttonBack().click();

        step("Закрытие браузера");
        abstractPage.closePlaywright();
    }



    @Test
    @Description("Тест-кейс HOM4.Функциональность фильтра товаров")
    public void ProductFilterFunctionality() {
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

        step("Закрытие браузера");
        abstractPage.closePlaywright();
    }



    @Test
    @Description("Тест-кейс HOM5.Работоспособность кнопок соц-сетей")
    //Тест работает, только при включенном VPN для открытия страниц, заблокированных в РФ.
    public void FunctionalityOfSocialMediaButtons() {
        step("Инициализация страницы");
        page = PlaywrightManagement.setupPlaywright("https://www.saucedemo.com/");

        step("Вход в систему");
        authorizationPage.UserLogin("standard_user", "secret_sauce");

        step("Нажимаем на иконку Твиттера");
        shoppingPage.socialTwitter().click();

        step("Переходим на новую вкладку");
        shoppingPage.navigateToNewTab(page);

        step("Проверка совпадения страниц");
        assert shoppingPage.isTwitterPagesOpened(page): "Открылась не та страница Twitter";

        step("Нажимаем на иконку Фейсбука");
        shoppingPage.socialFacebook().click();

        step("Переходим на новую вкладку");
        shoppingPage.navigateToNewTab(page);

        step("Проверка совпадения страниц");
        assert shoppingPage.isFacebookPagesOpened(page): "Открылась не та страница Facebook";

        step("Нажимаем на иконку Linkedin");
        shoppingPage.socialLinkedin().click();

        step("Переходим на новую вкладку");
        shoppingPage.navigateToNewTab(page);

        step("Проверка совпадения страниц");
        assert shoppingPage.isLinkedinPagesOpened(page): "Открылась не та страница Linkedin";

        step("Закрытие браузера");
        abstractPage.closePlaywright();
    }



    @Test
    @Description("Тест-кейс BAS1. Функциональность корзины")
    public void BasketFunctionality() {

        step("Инициализация страницы");
        page = PlaywrightManagement.setupPlaywright("https://www.saucedemo.com/");

        step("Вход в систему");
        authorizationPage.UserLogin("standard_user", "secret_sauce");

        step("Добавить товар в корзину");
        shoppingPage.addToCartSomeone().click();

        step("Перейти в корзину и проверить, что добавлен именно этот товар");

        shoppingPage.shoppingCart().click();
        assert shoppingPage.backpackInTheCart(page) : "В корзине не тот товар";

        step("Нажать на кноку удалить и удостовериться, что товар удален из корзины");
        checkoutPage.removeButton().click();
        if (page.locator("#cart_contents_container > div > div.cart_list > div.cart_item").isVisible()) {
            System.out.println("Продукт не удален из корзины");
        }

        step("Вернуться на главный экран с помощью специальной кнопки");
        checkoutPage.continueShoppingButton().click();

        step("Закрытие браузера");
        abstractPage.closePlaywright();
    }



        @Test
        @Description("Тест-кейс BAS2. Оформление заказа с валидными данными")
        public void OrderingWithValidData() {

            step("Инициализация страницы");
            page = PlaywrightManagement.setupPlaywright("https://www.saucedemo.com/");

            step("Вход в систему");
            authorizationPage.UserLogin("standard_user", "secret_sauce");

            step("Добавить товар в корзину");
            shoppingPage.addToCartSomeone().click();

            step("Перейти в корзину и проверить, что добавлен именно этот товар");
            shoppingPage.shoppingCart().click();
            assert shoppingPage.backpackInTheCart(page) : "В корзине не тот товар";

            step("Перейти на страницу оплаты с помощью специальной кнопки");
            checkoutPage.checkout().click();

            step("Ввести личную информацию для оформления заказа");
            checkoutPage.checkoutInfo("MyFirstName", "MyLastName", 444);

            step("Завершить оформление заказа");
            checkoutPage.finishButton().click();

            step("Закрытие браузера");
            abstractPage.closePlaywright();
        }
    }