package com.github.evgeniievgenevich.microstore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

import static javax.persistence.FetchType.LAZY;

/**
 * Product Characteristic Id
 *
 * @author Evgenii Evgenevich
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class CharacteristicId implements Serializable {
    @ManyToOne(fetch = LAZY)
    private Product product;

    @ManyToOne(fetch = LAZY)
    private Key key;
}
