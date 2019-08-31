package com.github.evgeniievgenevich.microstore.dto;


import lombok.Data;

import java.util.Map;

/**
 * Product Filter Data Transfer Object
 *
 * @author Evgenii Evgenevich
 */
@Data
public class ProductFilterDto {
    private String title;

    private Map<String, String> characteristic;
}
