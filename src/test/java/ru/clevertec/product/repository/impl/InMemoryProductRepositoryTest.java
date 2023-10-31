package ru.clevertec.product.repository.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.repository.ProductRepository;
import ru.clevertec.product.util.ProductTestData;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.clevertec.product.util.consts.TestsConstants.DEFAULT_UUID;
import static ru.clevertec.product.util.consts.TestsConstants.NEW_NAME;
import static ru.clevertec.product.util.consts.TestsConstants.NEW_PRICE;
import static ru.clevertec.product.util.consts.TestsConstants.NO_EXCISTS_UUID;

class InMemoryProductRepositoryTest {

    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository(ProductTestData.getInitInMemoryProductsMap());
    }

    @Nested
    class FindById {

        @Test
        void findByIdShouldReturnExpectedObject() {
            //given
            final Product expected = ProductTestData.getDefault();

            //when
            Product actual = productRepository.findById(DEFAULT_UUID)
                    .orElseThrow();

            //then
            assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        void findByIdNullShouldReturnOptionalEmpty() {
            //when
            Optional<Product> actual = productRepository.findById(null);

            //then
            assertThat(actual)
                    .isEmpty();
        }

    }

    @Nested
    class FindAll {

        @Test
        void findAllShouldCheckCount1() {
            //given
            int expected = ProductTestData.getInitInMemoryProductsList().size();

            //when
            int actual = productRepository.findAll().size();

            //then
            assertThat(actual)
                    .isEqualTo(expected);
        }

    }

    @Nested
    class Save {

        @Test
        void saveShouldUpdateProduct() {
            //given
            Product expected = ProductTestData.defaultBuilder()
                    .withName(NEW_NAME)
                    .withPrice(NEW_PRICE)
                    .build().buildProduct();

            //when
            Product actual = productRepository.save(expected);

            //then
            assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        void saveShouldReturnNewProductWithGeneratedUuid() {
            //given
            final UUID EXPECTED_UUID = NO_EXCISTS_UUID;
            Product expected = ProductTestData.defaultBuilder()
                    .withUuid(EXPECTED_UUID)
                    .build().buildProduct();

            //when
            Product actual = productRepository.save(expected);

            //then
            assertThat(actual)
                    .hasFieldOrPropertyWithValue(Product.Fields.uuid, expected.getUuid())
                    .hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName())
                    .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription())
                    .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice())
                    .hasFieldOrPropertyWithValue(Product.Fields.created, expected.getCreated())
                    .doesNotReturn(EXPECTED_UUID, from(Product::getUuid));
        }

        @Test
        void saveNullShouldThrowNullPointerException() {
            assertThrows(NullPointerException.class,
                    () -> productRepository.save(null));
        }


        @Test
        void saveShouldReturnObjectWithUuidOnly() {
            //given
            Product expected = new Product();

            //when
            Product actual = productRepository.save(expected);

            // then
            assertThat(actual.getUuid())
                    .isEqualTo(expected.getUuid());
        }
    }

    @Nested
    class Delete {

        @Test
        void deleteShouldMakeAmountLess() {
            //given
            final UUID TEST_UUID = DEFAULT_UUID;
            int expected = productRepository.findAll().size() - 1;

            //when
            productRepository.delete(TEST_UUID);
            int actual = productRepository.findAll().size();

            //then
            assertThat(actual)
                    .isEqualTo(expected);
        }

    }

}
