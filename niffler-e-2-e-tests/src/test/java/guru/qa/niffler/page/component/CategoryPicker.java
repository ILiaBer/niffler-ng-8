package guru.qa.niffler.page.component;

import guru.qa.niffler.utils.RandomDataUtils;
import guru.qa.niffler.utils.SelenideUtils;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.exist;

public class CategoryPicker extends BaseComponent {

    private By loc;

    public CategoryPicker(By locator) {
        this.loc = locator;
    }


    public CategoryPicker pickRandomCategory() {
        int randomNum = RandomDataUtils.getRandomNumberInRange(0, getSizeSelect());
         SelenideUtils.chromeDriver.$(loc).$(By.xpath("./li[" + randomNum + "]")).should(exist).click();
        return this;
    }

    private int getSizeSelect() {
         SelenideUtils.chromeDriver.$(loc).$(By.xpath("./li")).should(exist);
        return  SelenideUtils.chromeDriver.$(loc).$$(By.xpath("./li")).size();
    }
}
