package com.github.evgeniievgenevich.microstore.dto;

import com.github.evgeniievgenevich.microstore.model.Key;
import com.github.evgeniievgenevich.microstore.model.Product;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Product Create Data Transfer Object
 * создать товар
 *
 * @author Evgenii Evgenevich
 */
@Data
public class ProductCreateDto {
    private String title;

    private String description;

    private Map<String, Object> characteristic;

    public Product to(Product product) {
        product.setTitle(this.title);
        product.setDescription(this.description);
        return product;
    }

    public Product toDocument(Product product, Function<String, Key> key) {
        product.setTitle(this.title);
        product.setDescription(this.description);
        if (!CollectionUtils.isEmpty(this.characteristic)) {
            product.setCharacteristic(new HashMap<>(this.characteristic.size()));
            this.characteristic.forEach((k, v) -> product.getCharacteristic().put(key.apply(k).getId(), v));
        } else {
            product.setCharacteristic(Collections.emptyMap());
        }
        return product;
    }
}
