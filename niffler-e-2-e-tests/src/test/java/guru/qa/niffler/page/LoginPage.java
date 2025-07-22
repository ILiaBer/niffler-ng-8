package guru.qa.niffler.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideDriver;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.extensions.UsersQueueExtension;
import guru.qa.niffler.model.users.UserJson;
import guru.qa.niffler.utils.SelenideUtils;


public class LoginPage extends BasePage {

    private final SelenideElement usernameInput;
    private final SelenideElement passwordInput;
    private final SelenideElement submitBtn;
    private final SelenideElement createNewAccount;
    private final SelenideElement error;

    public MainPage doLogin(String username, String password) {
        Config CFG = Config.getInstance();
        SelenideUtils.chromeDriver.open(CFG.frontUrl());
        usernameInput.setValue(username);
        passwordInput.setValue(password);
        submitBtn.click();
        return new MainPage();
    }

    public LoginPage(SelenideDriver driver) {
        this.usernameInput = driver.$("input[name='username']");
        this.passwordInput = driver.$("input[name='password']");
        this.submitBtn = driver.$("button[type='submit']");
        this.createNewAccount = driver.$x("//*[contains(text(),'Create new account')]");
        this.error = driver.$x("//p[contains(@class,'form__error')]");
    }

    public MainPage doLogin(UsersQueueExtension.StaticUser user) {
        doLogin(user.userName(), user.pass());
        return new MainPage();
    }

    public MainPage doLogin(UserJson user) {
        doLogin(user.username(), user.password());
        return new MainPage();
    }

    public LoginPage clickCreateNewAccount() {
        createNewAccount.click();
        return new LoginPage(SelenideUtils.chromeDriver);
    }

    public LoginPage checkError(String errorText) {
        error.shouldHave(Condition.text(errorText));
        return this;
    }
}
