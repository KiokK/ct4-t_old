package ru.clevertec.product.exception;

public class ProductNameValidationException extends RuntimeException {

    /**
     * Сообщение ошибки вылидации поля name объекта класса {@link ru.clevertec.product.entity.Product}
     * @param incorrectName ошибочное значение поля name
     */
    public ProductNameValidationException(String incorrectName) {
        super(String.format("Product with name: '%s' - incorrect", incorrectName));
    }

}
