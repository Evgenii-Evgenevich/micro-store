package com.github.evgeniievgenevich.microstore.model;

import lombok.*;
import org.bson.types.ObjectId;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Product Data
 * Товар
 *
 * @author Evgenii Evgenevich
 */
@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String title;

    private String description;

    @OneToMany(mappedBy = "id.product")
    private List<CharacteristicData> characteristic;
}
