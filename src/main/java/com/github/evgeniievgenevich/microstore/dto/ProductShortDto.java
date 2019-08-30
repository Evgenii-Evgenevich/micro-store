package com.github.evgeniievgenevich.microstore.dto;

import com.github.evgeniievgenevich.microstore.model.Product;
import com.mongodb.DBObject;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.Map;

/**
 * Product Short Data Transfer Object
 * товар
 *
 * @author Evgenii Evgenevich
 */
@Data
public class ProductShortDto {
    private String id;

    private String title;

    private String description;

    public ProductShortDto(Product product) {
        this.id = product.getId().toString();
        this.title = product.getTitle();
        this.description = product.getDescription();
    }

    public ProductShortDto(DBObject object) {
        this.id = object.get("_id").toString();
        this.title = (String) object.get("title");
        this.description = (String) object.get("description");
    }
}
