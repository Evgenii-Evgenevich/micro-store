package com.github.evgeniievgenevich.microstore.model;

import lombok.Value;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;

/**
 * Product Characteristic Id
 *
 * @author Evgenii Evgenevich
 */
@Value
public class CharacteristicId implements Serializable {
    @DBRef(lazy = true)
    private Product product;

    private String key;
}
