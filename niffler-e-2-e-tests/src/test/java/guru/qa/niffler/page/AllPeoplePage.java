package guru.qa.niffler.page;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.utils.SelenideUtils;
import org.openqa.selenium.Keys;


public class AllPeoplePage extends BasePage{

    private final SelenideElement search =  SelenideUtils.chromeDriver.$("input");
    private final ElementsCollection allPeopleRows =  SelenideUtils.chromeDriver.$$x("//table//tr");

    public AllPeoplePage findPersonByName(String name) {
        search.sendKeys(name);
        search.sendKeys(Keys.ENTER);
        return this;
    }

    public AllPeoplePage checkTableContainsPerson(String name) {
        allPeopleRows.filter(Condition.text(name)).first().shouldBe(Condition.visible);
        return this;
    }

    public AllPeoplePage checkTableSize(int count) {
        allPeopleRows.shouldBe(CollectionCondition.size(count));
        return this;
    }
}