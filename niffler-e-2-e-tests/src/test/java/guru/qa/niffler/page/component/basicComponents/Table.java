package guru.qa.niffler.page.component.basicComponents;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.page.component.BaseComponent;
import guru.qa.niffler.utils.SelenideUtils;
import org.openqa.selenium.By;


public class Table extends BaseComponent {


    protected final By table = By.xpath("table");
    protected final By rows = By.xpath("tr");


    protected SelenideElement findRowByText(String text) {
            return  SelenideUtils.chromeDriver.$("//table//tr//*[contains(text(), '" + text + "')]//ancestor::tr");
    }

    public Table checkTableSize(int count) {
        SelenideUtils.chromeDriver.$(table).$$(rows).shouldBe(CollectionCondition.size(count));
        return this;
    }

    public Table checkTableEmpty() {
        checkTableSize(0);
        return this;
    }

    public Table checkTableVisible() {
        SelenideUtils.chromeDriver.$(table).shouldBe(Condition.visible);
        return this;
    }

    public Table checkTableNotVisible() {
        SelenideUtils.chromeDriver.$(table).shouldNotBe(Condition.visible);
        return this;
    }
}
