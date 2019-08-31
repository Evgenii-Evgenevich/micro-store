package com.github.evgeniievgenevich.microstore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 * Product Characteristic Data
 *
 * @author Evgenii Evgenevich
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CharacteristicData {
    @EmbeddedId
    private CharacteristicId id;

    private String value;

    public CharacteristicData(Product product, Key key, String value) {
        this(new CharacteristicId(product, key), value);
    }
}
