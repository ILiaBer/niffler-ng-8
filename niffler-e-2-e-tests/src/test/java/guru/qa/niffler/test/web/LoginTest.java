package guru.qa.niffler.test.web;

import com.codeborne.selenide.SelenideDriver;
import com.github.javafaker.Faker;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.data.enums.Browser;
import guru.qa.niffler.data.enums.CurrencyValues;
import guru.qa.niffler.jupiter.annotations.Category;
import guru.qa.niffler.jupiter.annotations.Spend;
import guru.qa.niffler.jupiter.annotations.User;
import guru.qa.niffler.jupiter.extensions.BrowserExtension;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.page.AllPeoplePage;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.ProfilePage;
import guru.qa.niffler.page.RegisterPage;
import guru.qa.niffler.utils.BrowserConverterUtils;
import guru.qa.niffler.utils.SelenideUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;

public class LoginTest extends BaseUITest {
    private static final Config CFG = Config.getInstance();
    String actualLogin = CFG.mainUserLogin();
    String actualPass = CFG.mainUserPass();
    private SelenideDriver defaultDriver = SelenideUtils.chromeDriver;


    @RegisterExtension
    private static final BrowserExtension browserExtension = new BrowserExtension();

    @Spend(
            username = "duck",
            category = "Обучение",
            description = "Обучение Niffler 2.0",
            amount = 89000.00,
            currency = CurrencyValues.RUB
    )
    @Test
    void spendingDescriptionShouldBeUpdatedByTableAction(SpendJson spend) {
        final String newDescription = "Обучение Niffler NG";

        SelenideUtils.chromeDriver.open(CFG.frontUrl());
        new LoginPage(defaultDriver).doLogin("duck", "12345");
        mainPage().table.editSpendingByDescription(spend.description());
        spendingPage().description.clearThenFill(newDescription);
        mainPage().table.checkTableContainsSpendingByDescription(newDescription);
    }

    @Test
    void shouldRegisterNewUser() {
        String userName = new Faker().name().username();
        String pass = new Faker().number().digits(5);
        SelenideUtils.chromeDriver.open(CFG.frontUrl());
        new LoginPage(defaultDriver).clickCreateNewAccount();
        new RegisterPage(defaultDriver).setUsername(userName)
                .setPassword(pass)
                .setPasswordSubmit(pass)
                .signUp().signIn();

        new LoginPage(defaultDriver).doLogin(actualLogin, actualPass);


        sidebarPage().header.toAllPeople();
        new AllPeoplePage().findPersonByName(userName)
                .checkTableSize(1)
                .checkTableContainsPerson(userName);

    }

    @Test
    void shouldNotRegisterUserWithExistingUsername() {
        SelenideDriver firefox = new SelenideDriver(SelenideUtils.firefoxConfig);

        browserExtension.drivers().addAll(List.of(defaultDriver, firefox));
        defaultDriver.open(CFG.frontUrl());
        firefox.open(CFG.frontUrl());
        new LoginPage(defaultDriver).clickCreateNewAccount();
        new RegisterPage(defaultDriver).setUsername(actualLogin)
                .setPassword(actualPass)
                .setPasswordSubmit(actualPass)
                .signUp().checkError("Username `" + actualLogin + "` already exists");

        new LoginPage(firefox).doLogin(actualLogin, actualPass);
    }

    @ParameterizedTest
    @EnumSource(Browser.class)
    void shouldNotRegisterUserWithExistingUsername(@ConvertWith(BrowserConverterUtils.class) SelenideDriver driver) {
        driver.open(CFG.frontUrl());
        new LoginPage(driver).clickCreateNewAccount();
        new RegisterPage(driver).setUsername(actualLogin)
                .setPassword(actualPass)
                .setPasswordSubmit(actualPass)
                .signUp().checkError("Username `" + actualLogin + "` already exists");
    }

    @Test
    void shouldShowErrorIfPasswordAndConfirmPasswordAreNotEqual() {
        String userName = new Faker().name().username();
        String pass = new Faker().number().digits(5);
        String wrongPass = new Faker().number().digits(5);
        SelenideUtils.chromeDriver.open(CFG.frontUrl(), LoginPage.class)
                .clickCreateNewAccount();
        new RegisterPage(defaultDriver).setUsername(userName)
                .setPassword(pass)
                .setPasswordSubmit(wrongPass)
                .signUp().checkError("Passwords should be equal");
    }

//    @Test
//    void mainPageShouldBeDisplayedAfterSuccessLogin() {
//        SelenideUtils.chromeDriver.open(CFG.frontUrl(), LoginPage.class)
//                .doLogin(actualLogin, actualPass);
//        mainPage().table.checkTableVisible();
//        Assertions.assertEquals(CFG.frontUrl() + "main", WebDriverRunner.url());
//    }

    @Test
    void userShouldStayOnLoginPageAfterLoginWithBadCredentials() {
        String wrongPass = new Faker().number().digits(5);
        SelenideUtils.chromeDriver.open(CFG.frontUrl());
        new LoginPage(defaultDriver).doLogin(actualLogin, wrongPass);
        mainPage().table.checkTableVisible();
        new LoginPage(defaultDriver).checkError("Неверные учетные данные пользователя");
    }


    @User(
            username = "test",
            categories = @Category(
                    archived = true))
    @Test
    void archivedCategoryCantBePresentedInCategoryList(CategoryJson category) {
        SelenideUtils.chromeDriver.open(CFG.frontUrl(), LoginPage.class)
                .doLogin(actualLogin, actualPass);

        sidebarPage().header.toProfile();
        new ProfilePage().hideArchive()
                .checkCategoryNotExistInTable(category.name());
    }


    @User(
            username = "test",
            categories = @Category(
                    archived = false))
    @Test
    void activeCategoryShouldPresentInCategoryList(CategoryJson category) {
        SelenideUtils.chromeDriver.open(CFG.frontUrl(), LoginPage.class)
                .doLogin(actualLogin, actualPass);
        sidebarPage().header.toProfile();
        new ProfilePage().hideArchive()
                .checkTableContainsCategory(category.name());
    }
}
