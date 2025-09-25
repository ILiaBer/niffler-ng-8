package guru.qa.niffler.grpc;

import com.google.protobuf.Empty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CurrencyGrpcTest extends BaseGrpcTest {

    @Test
    void allCurrenciesShouldBeReturned() {
        final CurrencyResponse response = blockingStub.getAllCurrencies(Empty.getDefaultInstance());
        final List<Currency> allCurrencies = response.getAllCurrenciesList();
        Assertions.assertEquals(4, allCurrencies.size());
    }
}
