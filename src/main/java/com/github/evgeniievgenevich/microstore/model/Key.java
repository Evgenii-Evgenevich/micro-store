package com.github.evgeniievgenevich.microstore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Product Characteristic Key
 *
 * @author Evgenii Evgenevich
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Key {
    @Id
    private String id;

    @Override
    public String toString() {
        return id;
    }
}
