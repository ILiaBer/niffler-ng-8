package guru.qa.niffler.page;

import guru.qa.niffler.utils.SelenideUtils;

public class AllPages {

    protected AllPeoplePage allPeoplePage() {
        return SelenideUtils.chromeDriver.page(AllPeoplePage.class);
    }

    protected SpendingPage spendingPage() {
        return SelenideUtils.chromeDriver.page(SpendingPage.class);
    }

    protected FriendsPage friendsPage() {
        return SelenideUtils.chromeDriver.page(FriendsPage.class);
    }

    protected LoginPage loginPage() {
        return SelenideUtils.chromeDriver.page(LoginPage.class);
    }

    protected MainPage mainPage() {
        return SelenideUtils.chromeDriver.page(MainPage.class);
    }

    protected ProfilePage profilePage() {
        return SelenideUtils.chromeDriver.page(ProfilePage.class);
    }

    protected RegisterPage registerPage() {
        return SelenideUtils.chromeDriver.page(RegisterPage.class);
    }

    protected SidebarPage sidebarPage() {
        return SelenideUtils.chromeDriver.page(SidebarPage.class);
    }
}
