package guru.qa.niffler.grpc;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.annotations.meta.GrpcTest;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.extension.RegisterExtension;

@GrpcTest
public class BaseGrpcTest {

    protected static final Config CFG = Config.getInstance();


    protected static final Channel channel = ManagedChannelBuilder
            .forAddress(CFG.currencyGrpcAddress(), CFG.currencyGrpcPort())
            .usePlaintext()
            .intercept(new GrpcConsoleInterceptor())
            .build();

    //NifflerCurrencyServiceGrpc - proto file в niffler-currency.proto
    protected static final NifflerCurrencyServiceGrpc.NifflerCurrencyServiceBlockingStub blockingStub =
            NifflerCurrencyServiceGrpc.newBlockingStub(channel);

}
