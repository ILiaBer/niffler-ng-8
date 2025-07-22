package guru.qa.niffler.test.web;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.extensions.BrowserExtension;
import guru.qa.niffler.jupiter.extensions.UsersQueueExtension;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.utils.SelenideUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static guru.qa.niffler.data.enums.FriendType.*;
import static guru.qa.niffler.jupiter.extensions.UsersQueueExtension.StaticUser;
import static guru.qa.niffler.jupiter.extensions.UsersQueueExtension.UserType;
import static guru.qa.niffler.jupiter.extensions.UsersQueueExtension.UserType.Type.*;

@ExtendWith({BrowserExtension.class})
public class UserTest extends BaseUITest {
    private static final Config CFG = Config.getInstance();

    @Test
    @ExtendWith(UsersQueueExtension.class)
    void friendShouldBePresentInFriendsTable(@UserType(WITH_FRIEND) StaticUser user) {
         SelenideUtils.chromeDriver.open(CFG.frontUrl(), LoginPage.class).doLogin(user);
        sidebarPage().header.toFriendsPage();
        friendsPage().table.checkTableContainsFriendWithStatus(user.userName(), FRIEND);
    }

    @Test
    @ExtendWith(UsersQueueExtension.class)
    void friendsTableShouldBeEmptyForNewUser(@UserType(EMPTY) StaticUser user) {
         SelenideUtils.chromeDriver.open(CFG.frontUrl(), LoginPage.class).doLogin(user);
        sidebarPage().header.toFriendsPage();
        friendsPage().table.checkTableEmpty();
    }

    @Test
    @ExtendWith(UsersQueueExtension.class)
    void incomeInvitationBePresentInFriendsTable(@UserType(WITH_INCOME_REQUEST) StaticUser user) {
         SelenideUtils.chromeDriver.open(CFG.frontUrl(), LoginPage.class).doLogin(user);
        sidebarPage().header.toFriendsPage();
        friendsPage().table.checkTableContainsFriendWithStatus(user.userName(), INCOME);
    }

    @Test
    @ExtendWith(UsersQueueExtension.class)
    void outcomeInvitationBePresentInAllPeoplesTable(@UserType(WITH_OUTCOME_REQUEST) StaticUser user) {
         SelenideUtils.chromeDriver.open(CFG.frontUrl(), LoginPage.class).doLogin(user);
        sidebarPage().header.toFriendsPage();
        friendsPage().table.checkTableContainsFriendWithStatus(user.userName(), OUTCOME);
    }
}
