package guru.qa.niffler.page.component;

import guru.qa.niffler.page.component.basicComponents.LineEdit;
import guru.qa.niffler.utils.SelenideUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

public class SearchField extends LineEdit {


    public SearchField(By locator) {
        super(locator);
    }

    protected SearchField search(String value) {
        fill(value);
         SelenideUtils.chromeDriver.$(locator).sendKeys(Keys.ENTER);
        return this;
    }
}
