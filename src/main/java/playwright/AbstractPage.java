package playwright;

import com.google.inject.Inject;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Selectors;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import playwright.PlaywrightManagement;

public class AbstractPage {

    @Inject
    private PlaywrightManagement playwrightManagement;

    public Locator getByLocator(String selector) {
        return playwrightManagement.getPage().locator(selector);
    }

    public Locator getByAriaRole(AriaRole role, String selector) {
        return playwrightManagement.getPage().getByRole(role, new Page.GetByRoleOptions().setName(selector));
    }

    public void reloadPage() {
        playwrightManagement.getPage().reload();
    }

    public void click(Locator locator) {
        playwrightManagement.getPage().click(String.valueOf(locator));
    }
}
