package ru.clevertec.product.repository.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.repository.ProductRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
public class InMemoryProductRepository implements ProductRepository {

    private Map<UUID, Product> productBase = new HashMap<>();

    @Override
    public Optional<Product> findById(UUID uuid) {
        return Optional.ofNullable(productBase.get(uuid));
    }

    @Override
    public List<Product> findAll() {
        return productBase.values().stream().toList();
    }

    @Override
    public Product save(Product product) {
        if (product.getUuid() == null || !productBase.containsKey(product.getUuid()))
            product.setUuid(UUID.randomUUID());
        productBase.put(product.getUuid(), product);
        return product;
    }

    @Override
    public void delete(UUID uuid) {
        productBase.remove(uuid);
    }
}
