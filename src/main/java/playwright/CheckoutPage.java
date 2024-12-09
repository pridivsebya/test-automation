package playwright;

import com.google.inject.Inject;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;

import java.util.List;
import java.util.stream.Collectors;

import static playwright.PlaywrightManagement.page;

public class CheckoutPage extends AbstractPage {

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

    public Locator checkout() {
        return getByAriaRole(AriaRole.BUTTON, "#checkout");
    }

   @Step("Личная информация для оформления заказа")
    public void checkoutInfo(String firstName, String lastName, Integer postalCode) {
       page.waitForSelector("#first-name");
       page.waitForSelector("#first-name");
       page.waitForSelector("#postal-code");

       page.locator("#first-name").fill(firstName);
       page.locator("#last-name").fill(lastName);
       page.locator("#postal-code").fill(String.valueOf(postalCode));
       page.locator("#continue").click();
   }

   public Locator finishButton() {
        return getByAriaRole(AriaRole.BUTTON, "#finish");
    }
}