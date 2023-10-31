package ru.clevertec.product.validator;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.exception.ProductDescriptionValidationException;
import ru.clevertec.product.exception.ProductNameValidationException;
import ru.clevertec.product.exception.ProductPriceValidationException;
import ru.clevertec.product.exception.ValidationException;
import ru.clevertec.product.util.ProductTestData;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductValidatorTest {

    @Nested
    class IsProductValidation {

        @Test
        void isProductThrowsValidationException() {
            assertThrows(ValidationException.class,
                    () -> ProductValidator.isProductValid(null));
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "Shrt", "Mandadadadarinus", "Apple 2"})
        void isProductValidThrowsNameValidationException(String argName) {
            //given
            Product testProduct = ProductTestData.defaultBuilder()
                    .withName(argName)
                    .build().buildProduct();

            //when//then
            assertThrows(ProductNameValidationException.class,
                    () -> ProductValidator.isProductValid(testProduct));
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "short Str", "With number like 2", "Long like It only goes as far as main street but you can get the number there"})
        void isProductValidThrowsDescriptionValidationException(String argDescription) {
            //given
            Product testProduct = ProductTestData.defaultBuilder()
                    .withDescription(argDescription)
                    .build().buildProduct();

            //when//then
            assertThrows(ProductDescriptionValidationException.class,
                    () -> ProductValidator.isProductValid(testProduct));
        }

        @ParameterizedTest
        @MethodSource("argsProviderIncorrectPrice")
        void isProductValidShouldThrowsPriceValidationException(BigDecimal argPrice) {
            //given
            Product testProduct = ProductTestData.defaultBuilder()
                    .withPrice(argPrice)
                    .build().buildProduct();

            //when//then
            assertThrows(ProductPriceValidationException.class,
                    () -> ProductValidator.isProductValid(testProduct));
        }

        static Stream<BigDecimal> argsProviderIncorrectPrice() {
            return Stream.of(BigDecimal.valueOf(-1), null);
        }

    }

}
