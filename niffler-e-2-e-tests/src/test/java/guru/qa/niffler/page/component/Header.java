package guru.qa.niffler.page.component;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.page.AllPeoplePage;
import guru.qa.niffler.page.FriendsPage;
import guru.qa.niffler.page.ProfilePage;
import guru.qa.niffler.utils.SelenideUtils;


public class Header extends BaseComponent {


    public Header() {
    }

    private final SelenideElement menu =  SelenideUtils.chromeDriver.$x("//button[@aria-label='Menu']");
    private final SelenideElement profile = SelenideUtils.chromeDriver.$x("//*[contains(text(),'Profile')]");
    private final SelenideElement friends = SelenideUtils.chromeDriver.$x("//*[contains(text(),'Friends')]");
    private final SelenideElement allPeople = SelenideUtils.chromeDriver.$x("//*[contains(text(),'All People')]");
    private final SelenideElement signOut = SelenideUtils.chromeDriver.$x("//*[contains(text(),'Sign Out')]");
    private final SelenideElement newSpending = SelenideUtils.chromeDriver.$x("//*[contains(text(),'New spending')]");


    public FriendsPage toFriendsPage() {
        menu.click();
        friends.click();
        return new FriendsPage();
    }

    public AllPeoplePage toAllPeople() {
        menu.click();
        allPeople.click();
        return new AllPeoplePage();
    }

    public ProfilePage toProfile() {
        menu.click();
        profile.click();
        return new ProfilePage();
    }

    public Header toMainPage() {
        SelenideUtils.chromeDriver.$x("//h1").click();
        return this;
    }

    public Header logout() {
        menu.click();
        signOut.click();
        return this;
    }

    public Header toNewSpending() {
        newSpending.click();
        return this;
    }
}
