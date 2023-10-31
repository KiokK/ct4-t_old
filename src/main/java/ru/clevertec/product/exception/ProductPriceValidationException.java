package ru.clevertec.product.exception;

import java.math.BigDecimal;

public class ProductPriceValidationException extends RuntimeException {

    /**
     * Сообщение ошибки вылидации поля price объекта класса {@link ru.clevertec.product.entity.Product}
     * @param incorrectPrice ошибочное значение поля
     */
    public ProductPriceValidationException(BigDecimal incorrectPrice) {
        super(String.format("Product with price: '%s' - incorrect", incorrectPrice));
    }
}
