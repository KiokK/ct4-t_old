package ru.clevertec.product.exception;

public class ProductDescriptionValidationException extends RuntimeException {

    /**
     * Сообщение ошибки вылидации поля description объекта класса {@link ru.clevertec.product.entity.Product}
     * @param incorrectDescription ошибочное значение поля description
     */
    public ProductDescriptionValidationException(String incorrectDescription) {
        super(String.format("Product with description: '%s' - incorrect", incorrectDescription));
    }

}
