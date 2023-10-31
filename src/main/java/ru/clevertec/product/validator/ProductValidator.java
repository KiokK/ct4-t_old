package ru.clevertec.product.validator;

import ru.clevertec.product.entity.Product;
import ru.clevertec.product.exception.ProductDescriptionValidationException;
import ru.clevertec.product.exception.ProductNameValidationException;
import ru.clevertec.product.exception.ProductPriceValidationException;
import ru.clevertec.product.exception.ValidationException;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class ProductValidator {

    private static final Pattern correctNameSimbl = Pattern.compile("^[a-zA-Z ]*$");
    private static final BigDecimal ZERO = new BigDecimal("0");

    private static int MAX_NAME_LEN = 10;
    private static int MIN_NAME_LEN = 5;
    private static int MAX_DESCRIPTION_LEN = 30;
    private static int MIN_DESCRIPTION_LEN = 10;

    public static boolean isProductValid(Product product) {
        if (product == null)
            throw new ValidationException(Product.class);

        if (product.getName() == null || product.getName().length() > MAX_NAME_LEN ||
                product.getName().length() < MIN_NAME_LEN || !correctNameSimbl.matcher(product.getName()).find())
            throw new ProductNameValidationException(product.getName());

        if (product.getDescription() != null)
            if (product.getDescription().length() > MAX_DESCRIPTION_LEN ||
                    product.getDescription().length() < MIN_DESCRIPTION_LEN ||
                    !correctNameSimbl.matcher(product.getDescription()).find()) {
                throw new ProductDescriptionValidationException(product.getDescription());
            }

        if (product.getPrice() == null || ZERO.compareTo(product.getPrice()) >= 0)
            throw new ProductPriceValidationException(product.getPrice());
        return true;
    }
}
