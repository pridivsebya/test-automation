package UI;

import com.google.inject.AbstractModule;
import com.microsoft.playwright.Page;
import playwright.PlaywrightManagement;

public class PlaywrightModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PlaywrightManagement.class).asEagerSingleton();

        bind(Page.class).toProvider(() -> {
            PlaywrightManagement playwrightManagement = new PlaywrightManagement();
            return playwrightManagement.setupPlaywright("https://www.saucedemo.com/");
        });
    }
}

