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
    private Page page;


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
        return getByLocator("[data-test='item-4-title-link']");
    }

    public Locator productImg() {
        return getByLocator("[data-test='item-4-img-link']");
    }

    public Locator buttonBack() {
       return getByLocator("[data-test='back-to-products']");
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
        return getByLocator("[data-test='social-twitter']");
    }

    @Step("Проверка, что открыта страница Twitter")
    public boolean isTwitterPagesOpened(Page page) {
        String title = page.title();
        return title.contains("Sauce Lab") && title.contains("Twitter");
    }

    public Locator socialFacebook() {
        return getByLocator("[data-test='social-facebook']");
    }

    @Step("Проверка, что открыта страница Facebook")
    public boolean isFacebookPagesOpened(Page page) {
        String title = page.title();
        return title.contains("Sauce Lab") && title.contains("Facebook");
    }

    public Locator socialLinkedin() {
        return getByLocator("[data-test='social-linkedin']");
    }

    @Step("Проверка, что открыта страница Linkedin")
    public boolean isLinkedinPagesOpened(Page page) {
        String title = page.title();
        return title.contains("Sauce Lab") && title.contains("Linkedin");
    }

    @Step("Перейти на новую вкладку")
    public void navigateToNewTab(Page page) {
        List<Page> pages = page.context().pages();

        if (pages.size() > 1) {
            pages.get(1).bringToFront();
        }
    }

    @Step("Проверка, что в корзину добавился именно тот товар")
    public boolean backpackInTheCart(Page page) {
        page.waitForSelector(".cart_item_label");
        String cartItemText = page.locator("[data-test='item-4-title-link']").innerText();
        return cartItemText.contains("Sauce Labs Backpack");
    }
}