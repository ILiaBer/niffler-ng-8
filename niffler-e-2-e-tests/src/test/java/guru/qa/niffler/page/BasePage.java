package guru.qa.niffler.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideDriver;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.utils.SelenideUtils;
import io.qameta.allure.Step;

import javax.annotation.Nonnull;


public abstract class BasePage<T extends BasePage<T>>{

    private SelenideDriver driver = new SelenideDriver(SelenideUtils.chromeConfig);

    private final SelenideElement alert = driver.$(".MuiSnackbar-root");

    @Step("Check alert message with text '{errorText}'")
    @SuppressWarnings("unchecked")
    @Nonnull
    public T checkAlertMessage(String expectedText) {
        alert.should(Condition.visible).should(Condition.text(expectedText));
        return (T) this;
    }
}