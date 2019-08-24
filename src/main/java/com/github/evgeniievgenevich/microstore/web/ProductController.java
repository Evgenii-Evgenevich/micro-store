package com.github.evgeniievgenevich.microstore.web;

import com.github.evgeniievgenevich.microstore.dto.ProductCreateDto;
import com.github.evgeniievgenevich.microstore.model.Product;
import com.github.evgeniievgenevich.microstore.service.ProductService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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
    public Product create(@RequestBody ProductCreateDto createDto) {
        return this.service.create(createDto);
    }

    @PostMapping("/{id}")
    public Product update(@PathVariable("id") ObjectId id, @RequestBody ProductCreateDto createDto) {
        return this.service.update(id, createDto);
    }

    @GetMapping("/{id}")
    public Product product(@PathVariable("id") ObjectId id) {
        return this.service.product(id);
    }

    @RequestMapping(method = {GET, POST}, value = "/{page}/{count}")
    public Page<Product> products(@PathVariable("page") int page, @PathVariable("count") int count, @RequestBody(required = false) String titleContainingIgnoreCase) {
        return titleContainingIgnoreCase == null
                ? this.service.products(page, count)
                : this.service.findByTitle(titleContainingIgnoreCase, page, count);
    }
}
