package com.github.evgeniievgenevich.microstore.dto;

import com.github.evgeniievgenevich.microstore.model.CharacteristicData;
import com.github.evgeniievgenevich.microstore.model.Product;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public List<CharacteristicData> characteristicData(Product product) {
        if (CollectionUtils.isEmpty(this.characteristic)) return null;
        List<CharacteristicData> data = new ArrayList<>(this.characteristic.size());
        this.characteristic.forEach((key, value) -> data.add(new CharacteristicData(product, key, value)));
        return data;
    }
}
