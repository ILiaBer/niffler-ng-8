package guru.qa.niffler.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.page.component.StatComponent;
import guru.qa.niffler.page.component.basicComponents.Button;
import guru.qa.niffler.page.component.basicComponents.LineEdit;
import guru.qa.niffler.utils.SelenideUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import java.io.File;


public class ProfilePage extends BasePage {

    public final StatComponent profileIcon = new StatComponent(By.xpath("//*[@id='image__input']/following-sibling::div//div"));
    private final SelenideElement imageInput =  SelenideUtils.chromeDriver.$x("//*[@id='image__input']");
    private final SelenideElement userName =  SelenideUtils.chromeDriver.$("#username");
    private final SelenideElement name =  SelenideUtils.chromeDriver.$("#name");
    private final SelenideElement saveChanges =  SelenideUtils.chromeDriver.$x("//*[.='Save changes']//span");
    private final SelenideElement showArchived =  SelenideUtils.chromeDriver.$x("//*[contains(@class, 'MuiSwitch-switchBase')]");
    private final SelenideElement newCategory =  SelenideUtils.chromeDriver.$("#category");


    private final ElementsCollection categoriesTable =  SelenideUtils.chromeDriver.$$x("//*[contains(@class, 'css-1lekzkb')]");
    private final SelenideElement edit =  SelenideUtils.chromeDriver.$x("//*[@aria-label='Edit category']");
    private final SelenideElement archive =  SelenideUtils.chromeDriver.$x("//*[@aria-label='Archive category']");

    public LineEdit nameInput = new LineEdit(By.cssSelector("#name"));
    public Button saveChangesBtn = new Button(By.xpath("//*[.='Save changes']//span"));


    public ProfilePage setUsername(String username) {
        userName.sendKeys(username);
        return this;
    }

    public ProfilePage setName(String name) {
        this.name.sendKeys(name);
        return this;
    }

    public ProfilePage clickSave() {
        saveChanges.click();
        return this;
    }

    private Boolean isArchiveShowed() {
        return showArchived.has(Condition.attribute("class", "Mui-checked"));
    }

    public ProfilePage showArchive() {
        if (isArchiveShowed()) {
            return this;
        } else showArchived.click();
        return this;
    }

    public ProfilePage hideArchive() {
        showArchived.is(Condition.exist);
        showArchived.scrollTo();
        if (!isArchiveShowed()) {
            return this;
        } else showArchived.click();
        return this;
    }

    public ProfilePage setNewCategory(String category) {
        showArchived.scrollTo();
        newCategory.sendKeys(category);
        return this;
    }

    public ProfilePage checkTableContainsCategory(String name) {
        categoriesTable.filter(Condition.text(name)).first().shouldBe(Condition.exist);
        return this;
    }

    public ProfilePage checkCategoryNotExistInTable(String name) {
        categoriesTable.filter(Condition.text(name)).first().shouldNotBe(Condition.exist);
        return this;
    }

    public ProfilePage uploadProfileImage(File image1) {
         SelenideUtils.chromeDriver.$("input[type='file']").should(Condition.exist);
        JavascriptExecutor js = (JavascriptExecutor)  SelenideUtils.chromeDriver.getWebDriver();
        js.executeScript("document.getElementById(\"image__input\").removeAttribute('hidden');");
        imageInput.uploadFile(image1);
        return this;
    }

    public ProfilePage clickSaveChanges() {
        JavascriptExecutor js = (JavascriptExecutor)  SelenideUtils.chromeDriver.getWebDriver();
        saveChanges.shouldBe(Condition.visible);
        js.executeScript("arguments[0].click();", saveChanges);
        return this;
    }
}