package guru.qa.niffler.jupiter.annotations;

import guru.qa.niffler.data.enums.CurrencyValues;
import guru.qa.niffler.jupiter.extensions.SpendingExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ExtendWith({SpendingExtension.class})
public @interface Spend {
  String username();

  String category();

  String description();

  double amount();

  CurrencyValues currency();
}
