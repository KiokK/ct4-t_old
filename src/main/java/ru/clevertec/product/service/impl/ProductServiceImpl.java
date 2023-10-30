package ru.clevertec.product.service.impl;

import lombok.RequiredArgsConstructor;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.exception.ProductNotFoundException;
import ru.clevertec.product.exception.ValidationException;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.repository.ProductRepository;
import ru.clevertec.product.service.ProductService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper mapper;
    private final ProductRepository productRepository;

    @Override
    public InfoProductDto get(UUID uuid) {
        return productRepository.findById(uuid)
                .map(mapper::toInfoProductDto)
                .orElseThrow(() -> new ProductNotFoundException(uuid));
    }


    @Override
    public List<InfoProductDto> getAll() {
        return productRepository.findAll()
                .stream()
                .map(mapper::toInfoProductDto)
                .toList();
    }

    @Override
    public UUID create(ProductDto productDto) {
        if (productDto == null)
            throw new ValidationException(ProductDto.class);
        Product newProduct = mapper.toProduct(productDto);
        return productRepository.save(newProduct).getUuid();
    }

    @Override
    public void update(UUID uuid, ProductDto productDto) {
        productRepository.findById(uuid)
                .map(curentProduct -> mapper.merge(curentProduct, productDto))
                .map(productRepository::save)
                .orElseThrow(() -> new ProductNotFoundException(uuid));
    }

    @Override
    public void delete(UUID uuid) {
        productRepository.delete(uuid);
    }

}
