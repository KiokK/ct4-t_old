package ru.clevertec.product.service.impl;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.exception.ProductNotFoundException;
import ru.clevertec.product.exception.ValidationException;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.repository.ProductRepository;
import ru.clevertec.product.util.ProductTestData;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static ru.clevertec.product.util.consts.TestsConstants.DEFAULT_UUID;
import static ru.clevertec.product.util.consts.TestsConstants.NEW_UUID;
import static ru.clevertec.product.util.consts.TestsConstants.NO_EXCISTS_UUID;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper mapper;

    @Captor
    private ArgumentCaptor<Product> productCaptor;

    @Nested
    class Get {

        @Test
        void getShouldThrowProductNotFoundException() {
            assertThrows(ProductNotFoundException.class,
                    () -> productService.get(null));
        }

        @Test
        void getShouldReturnInfoProductDto() {
            //given
            final UUID TEST_UUID = DEFAULT_UUID;
            InfoProductDto expected = ProductTestData.getDefaultInfoDto();
            Product testProduct = ProductTestData.getDefault();

            //when
            doReturn(Optional.of(testProduct))
                    .when(productRepository).findById(TEST_UUID);
            doReturn(expected)
                    .when(mapper).toInfoProductDto(testProduct);
            InfoProductDto actual = productService.get(TEST_UUID);

            //then
            assertThat(actual)
                    .hasFieldOrPropertyWithValue(Product.Fields.uuid, expected.uuid())
                    .hasFieldOrPropertyWithValue(Product.Fields.name, expected.name())
                    .hasFieldOrPropertyWithValue(Product.Fields.description, expected.description())
                    .hasFieldOrPropertyWithValue(Product.Fields.price, expected.price());
        }

    }

    @Nested
    class GetAll {

        @Test
        void getAllShouldReturnSize1() {
            //given
            final int expected = 1;

            //when
            doReturn(ProductTestData.getInitInMemoryProductsList())
                    .when(productRepository).findAll();
            final int actual = productService.getAll().size();

            //then
            assertThat(actual)
                    .isEqualTo(expected);
        }

    }

    @Nested
    class Create {

        @Test
        void createShouldThrowsValidationException() {
            assertAll(
                    () ->
                            assertThrows(ValidationException.class, () -> productService.create(null)),
                    () ->
                            verify(productRepository, times(0)).save(any())
            );
        }

        @Test
        void createShouldReturnUuid() {
            //given
            Product testProduct = ProductTestData.getNewProductUuidIsNull();
            ProductDto testDto = ProductTestData.getDtoWithNewValuesAndUuidNull();
            UUID expected = NEW_UUID;
            Product expectedMapperProduct = ProductTestData.getNewProductUuidIsNull();
            Product expectedRepositoryProduct = ProductTestData.getNewProductUuidIsNull();
            expectedRepositoryProduct.setUuid(expected);

            //when
            doReturn(expectedMapperProduct)
                    .when(mapper).toProduct(testDto);
            doReturn(expectedRepositoryProduct)
                    .when(productRepository).save(testProduct);
            UUID actual = productService.create(testDto);

            //then
            assertThat(actual)
                    .isEqualTo(expected);
        }

    }

    @Nested
    class Update {

        @Test
        void updateShouldThrowProductNotFoundException() {
            //given
            ProductDto testDtoProduct = ProductTestData.getDtoWithNewValuesAndUuidNull();

            //when//then
            assertThrows(ProductNotFoundException.class,
                    () -> productService.update(null, testDtoProduct));
        }

        @Test
        void updateShouldReturnProductWithNewFields() {
            //given
            ProductDto testDtoProduct = ProductTestData.getDtoWithNewValuesAndUuidNull();
            Product testProduct = ProductTestData.getNewProductUuidIsNull();
            Product expected = ProductTestData.getNewProductUuidIsNull();
            expected.setUuid(NEW_UUID);
            testProduct.setUuid(NEW_UUID);

            //when
            doReturn(Optional.of(expected))
                    .when(productRepository).findById(NEW_UUID);
            doReturn(expected)
                    .when(mapper).merge(testProduct, testDtoProduct);
            doReturn(expected)
                    .when(productRepository).save(testProduct);
            productService.update(NEW_UUID, testDtoProduct);

            //then
            assertAll(
                    () -> assertThat(testProduct).isEqualTo(expected),
                    () -> verify(productRepository).save(productCaptor.capture()),
                    () -> assertThat(productCaptor.getValue()).isEqualTo(testProduct)
            );
        }

    }

    @Nested
    class Delete {

        @ParameterizedTest
        @MethodSource("argsProviderDelete")
        void deleteVerify(UUID UUID_DELETE) {
            //when
            productService.delete(UUID_DELETE);

            //then
            verify(productRepository).delete(UUID_DELETE);
        }

        static Stream<UUID> argsProviderDelete() {
            return Stream.of(DEFAULT_UUID, NO_EXCISTS_UUID, null);
        }

    }

}
