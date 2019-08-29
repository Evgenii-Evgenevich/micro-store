package com.github.evgeniievgenevich.microstore.model;

import lombok.Data;
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
@Data
@Document
public class Product {
    @Id
    private ObjectId id;

    private String title;

    private String description;

    private Map<String, Object> characteristic;
}
