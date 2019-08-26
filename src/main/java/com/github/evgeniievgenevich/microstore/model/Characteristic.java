package com.github.evgeniievgenevich.microstore.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
//@Document("product_characteristic")
public class Characteristic {
    @Id
    private ID id;

    // findDistinctByDataKeyAndDataValue
    private List<Entry> data;

    public Characteristic(Product product, List<Entry> data) {
        this.id = new ID(product);
        this.data = data;
    }

    public Characteristic(Product product, Map<String, Object> data) {
        this.id = new ID(product);
        this.data = data.entrySet()
                .parallelStream()
                .map(e -> new Entry(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    @Value
    static public class ID implements Serializable {
        @DBRef(lazy = true)
        private Product product;
    }

    @Value
    static public class Entry {
        private String key;
        private Object value;
    }
}
