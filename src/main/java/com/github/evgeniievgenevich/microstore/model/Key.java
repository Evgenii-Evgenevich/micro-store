package com.github.evgeniievgenevich.microstore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Product Characteristic Key
 *
 * @author Evgenii Evgenevich
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("product_characteristic_key")
public class Key implements Serializable {
    @Id
    private String id;

    @Override
    public String toString() {
        return id;
    }
}
