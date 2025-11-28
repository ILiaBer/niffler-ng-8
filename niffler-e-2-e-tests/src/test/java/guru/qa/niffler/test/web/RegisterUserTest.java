package guru.qa.niffler.test.web;


import guru.qa.niffler.jupiter.extensions.BrowserExtension;
import guru.qa.niffler.model.users.UserJson;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.utils.RandomDataUtils;
import guru.qa.niffler.utils.SelenideUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(BrowserExtension.class)
public class RegisterUserTest extends BaseUITest {


    //Проверено с UserApiClient
    @Test
    void testRegisterUser() {
        UserJson user = RandomDataUtils.generateUser();
        userClient.createUser(user);

         SelenideUtils.chromeDriver.open(CFG.frontUrl(), LoginPage.class)
                .doLogin(user);
    }
}
