package guru.qa.niffler.page.component.basicComponents;

import guru.qa.niffler.page.component.BaseComponent;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class LineEdit extends BaseComponent {

    public LineEdit(By locator) {
        this.locator = locator;
    }

    protected By locator;

    public LineEdit fill(String value) {
        $(locator).scrollTo();
        $(locator).sendKeys(value);
        return this;
    }

    public LineEdit clear() {
        $(locator).scrollTo();
        $(locator).clear();
        return this;
    }

    public LineEdit clearThenFill(String value) {
        clear();
        fill(value);
        return this;
    }
}
