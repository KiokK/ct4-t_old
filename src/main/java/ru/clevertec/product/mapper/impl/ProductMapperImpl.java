package ru.clevertec.product.mapper.impl;

import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.exception.ValidationException;
import ru.clevertec.product.mapper.ProductMapper;

public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product toProduct(ProductDto productDto) {
        if (productDto == null)
            throw new ValidationException(ProductDto.class);
        return Product.builder()
                .name(productDto.name())
                .description(productDto.description())
                .price(productDto.price())
                .build();
    }

    @Override
    public InfoProductDto toInfoProductDto(Product product) {
        if (product == null)
            throw new ValidationException(Product.class);
        return new InfoProductDto(
                product.getUuid(),
                product.getName(),
                product.getDescription(),
                product.getPrice());
    }

    @Override
    public Product merge(Product product, ProductDto productDto) {
        if (productDto.name() != null)
            product.setName(productDto.name());
        if (productDto.description() != null)
            product.setDescription(productDto.description());
        if (productDto.price() != null)
            product.setPrice(productDto.price());
        return product;
    }

}
