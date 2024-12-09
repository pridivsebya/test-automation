package playwright;

import com.google.inject.Inject;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;

import java.util.List;
import java.util.stream.Collectors;

public class ShoppingPage extends AbstractPage {

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


    public Locator addToCartButton() {
        return getByText( "Add to cart");
    }

    public Locator addToCartSomeone() {
        return getByLocator("[data-test='add-to-cart-sauce-labs-backpack']");
    }

    public Locator removeButton() {
        return getByText( "Remove");
    }

    public Locator shoppingCart() {
        return getByLocator("[data-test='shopping-cart-link']");
    }

    public Locator title() {
        return getByText( "Products");
    }

    public Locator inventoryList() {
        return getByLocator( "[data-test='inventory-list']");
    }

    public Locator openMenu() {
        return getByAriaRole(AriaRole.BUTTON, "Open Menu");
    }

    public Locator productName() {
        return getByLocator("a.inventory_item_name");
    }

    public Locator productImg() {
        return getByLocator("img.inventory_item_img");
    }

    public Locator buttonBack() {
        return getByText("back-to-products");
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
        return page.locator( "social_twitter");
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