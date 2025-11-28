package guru.qa.niffler.page.component;

import com.codeborne.selenide.Condition;
import guru.qa.niffler.utils.SelenideUtils;
import org.openqa.selenium.By;

import javax.annotation.Nonnull;

public abstract class BaseComponent<T extends BaseComponent<T>> extends SelenideUtils {

    private By locator;

    @SuppressWarnings("unchecked")
    @Nonnull
    public T checkVisible() {
         SelenideUtils.chromeDriver.$(locator).shouldBe((Condition.visible));
        return (T) this;
    }
}