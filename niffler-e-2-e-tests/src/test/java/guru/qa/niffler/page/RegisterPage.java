package guru.qa.niffler.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideDriver;
import com.codeborne.selenide.SelenideElement;

public class RegisterPage extends BasePage {

    private final SelenideElement usernameInput;
    private final SelenideElement passwordInput;
    private final SelenideElement passwordSubmitInput;
    private final SelenideElement signUpBtn;
    private final SelenideElement signInBtn;
    private final SelenideElement error;

    public RegisterPage(SelenideDriver driver) {
        this.usernameInput = driver.$("#username");
        this.passwordInput = driver.$("#password");
        this.passwordSubmitInput = driver.$("#passwordSubmit");
        this.signUpBtn = driver.$x("//button[contains(text(),'Sign Up')]");
        this.signInBtn = driver.$x("//a[contains(text(),'Sign in')]");
        this.error = driver.$x("//span[contains(@class,'form__error')]");
    }

    public RegisterPage setUsername(String username) {
        usernameInput.sendKeys(username);
        return this;
    }

    public RegisterPage setPassword(String password) {
        passwordInput.sendKeys(password);
        return this;
    }

    public RegisterPage setPasswordSubmit(String password) {
        passwordSubmitInput.sendKeys(password);
        return this;
    }

    public RegisterPage signUp() {
        signUpBtn.click();
        return this;
    }

    public RegisterPage signIn() {
        signInBtn.click();
        return this;
    }

    public RegisterPage checkError(String errorText) {
        error.shouldHave(Condition.text(errorText));
        return this;
    }
}
