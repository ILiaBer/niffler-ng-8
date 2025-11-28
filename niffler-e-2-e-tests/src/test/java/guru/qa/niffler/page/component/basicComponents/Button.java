package guru.qa.niffler.page.component.basicComponents;

import com.codeborne.selenide.Condition;
import guru.qa.niffler.page.component.BaseComponent;
import guru.qa.niffler.utils.SelenideUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

public class Button extends BaseComponent {

    private By locator;

    public Button(By locator) {
        this.locator = locator;
    }

    public Button click(){
         SelenideUtils.chromeDriver.$(locator).scrollTo();
         SelenideUtils.chromeDriver.$(locator).click();
        return this;
    }

    public Button jsClick(){
        JavascriptExecutor js = (JavascriptExecutor)  SelenideUtils.chromeDriver.getWebDriver();
         SelenideUtils.chromeDriver.$(locator).shouldBe(Condition.visible);
        js.executeScript("arguments[0].click();",  SelenideUtils.chromeDriver.$(locator));
        return this;
    }
}