package guru.qa.niffler.data.enums;

import com.codeborne.selenide.SelenideConfig;
import com.codeborne.selenide.SelenideDriver;
import guru.qa.niffler.utils.SelenideUtils;

public enum Browser {

    CHROME(SelenideUtils.chromeConfig), FIREFOX(SelenideUtils.firefoxConfig);

    private final SelenideConfig config;

    Browser(SelenideConfig config) {
        this.config = config;
    }

    public SelenideDriver createDriver() {
        return new SelenideDriver(this.config);
    }
}
