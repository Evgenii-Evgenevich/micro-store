package com.github.evgeniievgenevich.microstore.model;

import com.mongodb.DBObject;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

/**
 * Product Data
 * Товар
 *
 * @author Evgenii Evgenevich
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Product {
    @Id
    private ObjectId id;

    private String title;

    private String description;

    private Map<String, Object> characteristic;

    public Product(DBObject object) {
        this.id = (ObjectId)object.get("_id");
        this.title = (String) object.get("title");
        this.description = (String) object.get("description");
        this.characteristic = (Map<String, Object>) object.get("characteristic");
    }
}
