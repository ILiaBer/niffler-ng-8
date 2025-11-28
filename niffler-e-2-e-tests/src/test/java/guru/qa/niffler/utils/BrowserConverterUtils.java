package guru.qa.niffler.utils;

import guru.qa.niffler.data.enums.Browser;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;

public class BrowserConverterUtils implements ArgumentConverter {

    @Override
    public Object convert(Object source, ParameterContext context) throws ArgumentConversionException {
        if (!(source instanceof Browser)){
            throw new ArgumentConversionException("Source must be instance of enum Browser");
        }
        return ((Browser) source).createDriver();
    }
}
