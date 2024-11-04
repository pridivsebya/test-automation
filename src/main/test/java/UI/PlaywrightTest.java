package UI;

import com.google.inject.Inject;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;
import playwright.PlaywrightManagement;

@Guice
public class PlaywrightTest {
    @Inject
    private PlaywrightManagement playwrightManagement;

    @Test
    public void openPage() {
        String url = "https://google.com";
        playwrightManagement.setupPlaywright(url);
    }
}