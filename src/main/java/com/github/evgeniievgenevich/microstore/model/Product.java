package com.github.evgeniievgenevich.microstore.model;

import lombok.Data;
import org.bson.types.ObjectId;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

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
    private ObjectId id = new ObjectId();

    private String title;

    private String description;

//    @OneToMany(mappedBy = "id.product")
//    private List<CharacteristicData> characteristic;

    public List<CharacteristicData> getCharacteristic() {
        return null;
    }
}
