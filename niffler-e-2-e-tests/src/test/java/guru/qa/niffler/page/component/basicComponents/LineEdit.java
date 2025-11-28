package guru.qa.niffler.page.component.basicComponents;

import guru.qa.niffler.page.component.BaseComponent;
import guru.qa.niffler.utils.SelenideUtils;
import org.openqa.selenium.By;


public class LineEdit extends BaseComponent {

    public LineEdit(By locator) {
        this.locator = locator;
    }

    protected By locator;

    public LineEdit fill(String value) {
         SelenideUtils.chromeDriver.$(locator).scrollTo();
         SelenideUtils.chromeDriver.$(locator).sendKeys(value);
        return this;
    }

    public LineEdit clear() {
         SelenideUtils.chromeDriver.$(locator).scrollTo();
         SelenideUtils.chromeDriver.$(locator).clear();
        return this;
    }

    public LineEdit clearThenFill(String value) {
        clear();
        fill(value);
        return this;
    }
}