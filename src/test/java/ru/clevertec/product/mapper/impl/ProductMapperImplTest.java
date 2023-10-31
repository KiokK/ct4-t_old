package ru.clevertec.product.mapper.impl;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.exception.ValidationException;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.util.ProductTestData;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductMapperImplTest {

    private ProductMapper mapper = new ProductMapperImpl();

    @Nested
    class ToProduct {

        @Test
        void toProductShouldReturnProduct() {
            //given
            ProductDto testProductDto = ProductTestData.getDefaultDto();
            Product expected = ProductTestData.getDefault();

            //when
            Product actual = mapper.toProduct(testProductDto);

            //then
            assertThat(actual)
                    .hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName())
                    .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription())
                    .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice());
        }


        @Test
        void toProductShouldThrowValidationException() {
            assertThrows(ValidationException.class,
                    () -> mapper.toProduct(null));
        }

    }

    @Nested
    class ToInfoProductDto {

        @Test
        void toInfoProductDtoShouldReturnInfo() {
            //given
            Product testProduct = ProductTestData.getDefault();
            InfoProductDto expected = ProductTestData.getDefaultInfoDto();

            //when
            InfoProductDto actual = mapper.toInfoProductDto(testProduct);

            //then
            assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        void toInfoProductDtoShouldThrowValidationException() {
            assertThrows(ValidationException.class,
                    () -> mapper.toInfoProductDto(null));
        }

    }

    @Nested
    class Merge {

        @Test
        void mergeShouldReturnProduct() {
            //given
            Product testProduct = ProductTestData.getDefault();
            ProductDto testProductDto = ProductTestData.getDtoWithNewValuesAndUuidNull();
            Product expected = ProductTestData.getNewProductUuidIsNull();
            expected.setUuid(testProduct.getUuid());
            expected.setCreated(testProduct.getCreated());

            //when
            Product actual = mapper.merge(testProduct, testProductDto);

            //then
            assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        void mergeShouldThrowsNullPointerException() {
            assertAll(
                    () -> assertThrows(NullPointerException.class,
                            () -> mapper.merge(null, ProductTestData.getDefaultDto())),
                    () -> assertThrows(NullPointerException.class,
                            () -> mapper.merge(new Product(), null))
            );

        }

    }

}
