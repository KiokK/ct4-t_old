package ru.clevertec.product.exception;

public class ValidationException extends RuntimeException {

    /**
     * Сообщение ошибки вылидации. Значение null
     * @param clazz класс объекта, с ошибкой валидации
     */
    public ValidationException(Class clazz) {
        super(String.format("Parameter with type: %s is null", clazz));
    }
}
