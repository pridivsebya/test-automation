package playwright;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;

import java.util.List;
import java.util.stream.Collectors;

import static playwright.PlaywrightManagement.page;

public class ShoppingPage extends AbstractPage {

    public Locator addToCartButton() {
        return getByAriaRole(AriaRole.BUTTON, "Add to cart");
    }

    public Locator addToCartSomeone() {
        return getByAriaRole(AriaRole.BUTTON, "#add-to-cart-sauce-labs-backpack");
    }

    public Locator removeButton() {
        return getByAriaRole(AriaRole.BUTTON, "Remove");
    }

    public Locator shoppingCart() {
        return getByAriaRole(AriaRole.LINK, "shopping_cart_container > a");
    }

    public Locator title() {
        return getByAriaRole(AriaRole.HEADING, "Products");
    }

    public Locator inventoryList() {
        return getByAriaRole(AriaRole.LIST, "inventory-list");
    }

    public Locator openMenu() {
        return getByAriaRole(AriaRole.BUTTON, "Open Menu");
    }

    public Locator productName() {
        return getByAriaRole(AriaRole.GENERIC, "#item_4_title_link > div");
    }

    public Locator productImg() {
        return getByAriaRole(AriaRole.LINK, "#item_4_img_link > img");
    }

    public void buttonBack() {
        getByAriaRole(AriaRole.BUTTON, "back-to-products");
    }

    public Locator continueShoppingButton() {
        getByAriaRole(AriaRole.BUTTON, "#continue-shopping");
        return null;
    }

    @Step("Выбрать фильтрацию")
    public void selectFilter(String filterProducts) {
        page.locator("#header_container > div.header_secondary_container > div > span > select").selectOption(filterProducts);
    }

    @Step("Получить список названий товаров")
    public List<String> getProductNames() {
        return page.locator("inventory-list").allTextContents();
    }

    @Step("Получить список цен товаров")
    public List<Double> getProductPrices() {
        return page.locator("inventory-list").allTextContents()
                .stream()
                .map(price -> Double.parseDouble((price.replace("$", ""))))
                .collect(Collectors.toList());
    }

    public Locator socialTwitter() {
        return getByAriaRole(AriaRole.LINK, "#page_wrapper > footer > ul > li.social_twitter > a");
    }

    @Step("Проверка, что открыта страница Twitter")
    public boolean isTwitterPagesOpened(Page page) {
        String title = page.title();
        return title.contains("Sauce Lab") || title.contains("Twitter");
    }

    public Locator socialFacebook() {
        return getByAriaRole(AriaRole.LINK, "#page_wrapper > footer > ul > li.social_facebook > a");
    }

    @Step("Проверка, что открыта страница Facebook")
    public boolean isFacebookPagesOpened(Page page) {
        String title = page.title();
        return title.contains("Sauce Lab") || title.contains("Facebook");
    }

    public Locator socialLinkedin() {
        return getByAriaRole(AriaRole.LINK, "#page_wrapper > footer > ul > li.social_linkedin > a");
    }

    @Step("Проверка, что открыта страница Linkedin")
    public boolean isLinkedinPagesOpened(Page page) {
        String title = page.title();
        return title.contains("Sauce Lab") || title.contains("Linkedin");
    }

    @Step("Перейти на новую вкладку")
    public void navigateToNewTab(Page page) {
        page.context().pages().get(1).bringToFront();
    }

    @Step("Проверка, что в корзину добавился именно тот товар")
    public boolean backpackInTheCart(Page page) {
        String title = page.title();
        return title.contains("Your Cart") || title.contains("Sauce Labs Backpack");
    }


}