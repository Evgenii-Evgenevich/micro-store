package com.github.evgeniievgenevich.microstore.web;

import com.github.evgeniievgenevich.microstore.dto.ProductCreateDto;
import com.github.evgeniievgenevich.microstore.dto.ProductDetailDto;
import com.github.evgeniievgenevich.microstore.dto.ProductShortDto;
import com.github.evgeniievgenevich.microstore.service.ProductService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Product Controller
 * Товар
 *
 * @author Evgenii Evgenevich
 */
@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService service;

    @Autowired
    public ProductController(ProductService productService) {
        this.service = productService;
    }

    @PostMapping
    public ProductDetailDto create(@RequestBody ProductCreateDto createDto) {
        return this.service.create(createDto);
    }

    @PostMapping("/{id}")
    public ProductDetailDto update(@PathVariable("id") ObjectId id, @RequestBody ProductCreateDto createDto) {
        return this.service.update(id, createDto);
    }

    @GetMapping("/{id}")
    public ProductDetailDto product(@PathVariable("id") ObjectId id) {
        return this.service.product(id);
    }

    @RequestMapping(method = {GET, POST}, value = "/{page}/{count}")
    public Page<ProductShortDto> products(@PathVariable("page") int page, @PathVariable("count") int count, @RequestBody(required = false) String titleContainingIgnoreCase) {
        return titleContainingIgnoreCase == null
                ? this.service.products(page, count)
                : this.service.findByTitle(titleContainingIgnoreCase, page, count);
    }

    @GetMapping("/characteristic-key")
    public Set<String> characteristicKeySet() {
        return this.service.characteristicKeySet();
    }

    @PostMapping("/characteristic-key")
    public List<ProductShortDto> findByKeys(@RequestBody List<String> keys) {
        return this.service.findByKeys(keys);
    }

    @PostMapping("/characteristic-value")
    public List<ProductShortDto> findByCharacteristic(@RequestBody Map<String, Object> characteristic) {
        return this.service.findByCharacteristic(characteristic);
    }
}
