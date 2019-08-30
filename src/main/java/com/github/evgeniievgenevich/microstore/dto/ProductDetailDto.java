package com.github.evgeniievgenevich.microstore.dto;


import com.github.evgeniievgenevich.microstore.model.Product;
import com.mongodb.DBObject;
import lombok.Data;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

/**
 * Product Detail Data Transfer Object
 * детали товара
 *
 * @author Evgenii Evgenevich
 */
@Data
public class ProductDetailDto {
    private String id;

    private LocalDateTime dateTime;

    private String title;

    private String description;

    private Map<String, Object> characteristic;

    public ProductDetailDto(Product product) {
        this.id = product.getId().toString();
        this.dateTime = LocalDateTime.ofEpochSecond(product.getId().getTimestamp(), 0, ZoneOffset.UTC);
        this.title = product.getTitle();
        this.description = product.getDescription();
        this.characteristic = product.getCharacteristic();
    }

    public ProductDetailDto(DBObject object) {
        this.setId((ObjectId) object.get("_id"));
        this.title = (String) object.get("title");
        this.description = (String) object.get("description");
        this.characteristic = (Map<String, Object>) object.get("characteristic");
    }

    public void setId(ObjectId id) {
        this.id = id.toString();
        this.dateTime = LocalDateTime.ofEpochSecond(id.getTimestamp(), 0, ZoneOffset.UTC);
    }
}
