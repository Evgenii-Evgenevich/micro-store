package com.github.evgeniievgenevich.microstore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Product Characteristic
 * параметры товара
 *
 * @author Evgenii Evgenevich
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("product_characteristic")
public class CharacteristicData {
    @Id
    private CharacteristicId id;

    private Object value;

    public CharacteristicData(Product product, String key, Object value) {
        this.id = new CharacteristicId(product, key);
        this.value = value;
    }
}
