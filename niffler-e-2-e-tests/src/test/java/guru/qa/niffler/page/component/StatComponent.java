package guru.qa.niffler.page.component;

import guru.qa.niffler.data.condition.Bubble;
import guru.qa.niffler.data.condition.Color;
import guru.qa.niffler.utils.CommonSteps;
import guru.qa.niffler.utils.SelenideUtils;
import org.openqa.selenium.By;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static guru.qa.niffler.data.condition.StatConditions.*;
import static java.lang.Thread.sleep;

public class StatComponent extends BaseComponent {

    protected By locator;

    public StatComponent(By locator) {
        this.locator = locator;
    }

    public BufferedImage screenshotStats() throws IOException, InterruptedException {
        sleep(5000);
        return CommonSteps.screenshot( SelenideUtils.chromeDriver.$(locator));
    }

    public StatComponent checkBubbles(Color... expectedColors) {
         SelenideUtils.chromeDriver.$(locator).$$("li").should(color(expectedColors));

        return this;
    }

    public StatComponent checkBubbles(Bubble... bubbles) {
         SelenideUtils.chromeDriver.$(locator).$$("li").should(shouldHaveBubbles(bubbles));
        return this;
    }

    public StatComponent checkStatBubblesInAnyOrder(Bubble... bubbles) {
         SelenideUtils.chromeDriver.$(locator).$$("li").should(shouldHaveStatBubblesInAnyOrder(bubbles));
        return this;
    }

    public StatComponent checkContainsStatBubbles(Bubble... bubbles) {
         SelenideUtils.chromeDriver.$(locator).$$("li").should(shouldContainsStatBubbles(bubbles));
        return this;
    }
}
