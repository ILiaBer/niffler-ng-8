package guru.qa.niffler.page.component.basicComponents;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.page.component.BaseComponent;
import guru.qa.niffler.utils.SelenideUtils;
import org.openqa.selenium.By;

public class Select extends BaseComponent {

    private By btnLoc;
    private By selectorLoc;

    public Select(By btnLoc, By selectorLoc) {
        this.selectorLoc = selectorLoc;
        this.btnLoc = btnLoc;
    }


    public Select choose(String variant) {
         SelenideUtils.chromeDriver.$(btnLoc).shouldBe(Condition.visible);
         SelenideUtils.chromeDriver.$(btnLoc).scrollTo();
        ElementsCollection countriesList =  SelenideUtils.chromeDriver.$(selectorLoc).findAll(By.xpath("//li"));
        for (SelenideElement li : countriesList) {
            if (li.getText().contains(variant)) {
                li.shouldBe(Condition.visible);
                li.click();
                return this;
            }
        }
        return this;
    }
}