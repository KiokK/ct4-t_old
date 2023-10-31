package ru.clevertec.product.util;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static ru.clevertec.product.util.consts.TestsConstants.DEFAULT_NAME;
import static ru.clevertec.product.util.consts.TestsConstants.DEFAULT_CREATED;
import static ru.clevertec.product.util.consts.TestsConstants.DEFAULT_DESCRIPTION;
import static ru.clevertec.product.util.consts.TestsConstants.DEFAULT_PRICE;
import static ru.clevertec.product.util.consts.TestsConstants.DEFAULT_UUID;
import static ru.clevertec.product.util.consts.TestsConstants.NEW_DESCRIPTION;
import static ru.clevertec.product.util.consts.TestsConstants.NEW_NAME;
import static ru.clevertec.product.util.consts.TestsConstants.NEW_PRICE;

@Data
@Builder(setterPrefix = "with")
public class ProductTestData {

    @Builder.Default
    private UUID uuid = DEFAULT_UUID;

    @Builder.Default
    private String name = DEFAULT_NAME;

    @Builder.Default
    private String description = DEFAULT_DESCRIPTION;

    @Builder.Default
    private BigDecimal price = DEFAULT_PRICE;

    @Builder.Default
    private LocalDateTime created = DEFAULT_CREATED;

    public static ProductTestDataBuilder defaultBuilder() {
        return ProductTestData.builder();
    }

    public Product buildProduct() {
        return new Product(uuid, name, description, price, created);
    }


    public static List<Product> getInitInMemoryProductsList() {
        return List.of(getDefault());
    }

    public static Map<UUID, Product> getInitInMemoryProductsMap() {
        return new HashMap<> () {
            {
                put(DEFAULT_UUID, getDefault());
            }
        };
    }

    public static Product getDefault() {
        return ProductTestData.builder().build().buildProduct();
    }

    public static ProductDto getDefaultDto() {
        return new ProductDto(DEFAULT_NAME, DEFAULT_DESCRIPTION, DEFAULT_PRICE);
    }

    public static InfoProductDto getDefaultInfoDto() {
        return new InfoProductDto(DEFAULT_UUID, DEFAULT_NAME, DEFAULT_DESCRIPTION, DEFAULT_PRICE);
    }

    public static ProductDto getDtoWithNewValuesAndUuidNull() {
        return new ProductDto(NEW_NAME, NEW_DESCRIPTION, NEW_PRICE);
    }

    public static Product getNewProductUuidIsNull() {
        return Product.builder()
                .price(NEW_PRICE)
                .name(NEW_NAME)
                .description(NEW_DESCRIPTION)
                .build();
    }

}
