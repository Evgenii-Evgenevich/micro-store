package com.github.evgeniievgenevich.microstore.model;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;

/**
 * Product Characteristic Id
 *
 * @author Evgenii Evgenevich
 */
@Data
public class CharacteristicId implements Serializable {
    @Indexed
    @DBRef(lazy = true)
    @NonNull
    private Product product;

    @DBRef(lazy = true)
    @NonNull
    private Key key;
}
