package com.github.evgeniievgenevich.microstore.service;

import com.github.evgeniievgenevich.microstore.dao.CharacteristicRepository;
import com.github.evgeniievgenevich.microstore.dao.KeyRepository;
import com.github.evgeniievgenevich.microstore.dao.ProductRepository;
import com.github.evgeniievgenevich.microstore.dto.ProductCreateDto;
import com.github.evgeniievgenevich.microstore.dto.ProductDetailDto;
import com.github.evgeniievgenevich.microstore.dto.ProductFilterDto;
import com.github.evgeniievgenevich.microstore.dto.ProductShortDto;
import com.github.evgeniievgenevich.microstore.model.CharacteristicData;
import com.github.evgeniievgenevich.microstore.model.Key;
import com.github.evgeniievgenevich.microstore.model.Product;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Product Service
 *
 * @author Evgenii Evgenevich
 */
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CharacteristicRepository characteristicRepository;
    private final KeyRepository keyRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository
            , CharacteristicRepository characteristicRepository
            , KeyRepository keyRepository
    ) {
        this.productRepository = productRepository;
        this.characteristicRepository = characteristicRepository;
        this.keyRepository = keyRepository;
    }

    private static NoSuchElementException notFound(ObjectId id) {
        return new NoSuchElementException("could not found Product with id " + id);
    }

    private Key key(String id) {
        //ключи как справочник (key book)
        //Key key = keyRepository.findById(id)
        //        .orElseThrow(() -> new NoSuchElementException("could not found Key " + id));

        return keyRepository.save(new Key(id));//accept all
    }
//
//    @Override
//    @Transactional
//    public ProductDetailDto create(ProductCreateDto dto) {
//        Product product = productRepository.save(dto.to(new Product()));
//        product.setCharacteristic(dto.characteristicData(product, this::key));
//        return new ProductDetailDto(
//                productRepository.save(dto.to(product)),
//                product.getCharacteristic() == null ? null : characteristicRepository.saveAll(product.getCharacteristic())
//        );
//    }
//
//    @Override
//    @Transactional
//    public ProductDetailDto update(ObjectId id, ProductCreateDto dto) {
//        Product product = productRepository.findById(id).orElseThrow(() -> notFound(id));
//        characteristicRepository.deleteByIdProduct(product);
//        product.setCharacteristic(dto.characteristicData(product, this::key));
//        return new ProductDetailDto(
//                productRepository.save(dto.to(product)),
//                product.getCharacteristic() == null ? null : characteristicRepository.saveAll(product.getCharacteristic())
//        );
//    }

    @Override
    @Transactional
    public ProductDetailDto create(ProductCreateDto createDto) {
        Product product = productRepository.save(createDto.to(new Product()));
        List<CharacteristicData> characteristicData = createDto.characteristicData(product, this::key);
        return new ProductDetailDto(
                product,
                characteristicData == null ? null : characteristicRepository.saveAll(characteristicData)
        );
    }

    @Override
    @Transactional
    public ProductDetailDto update(ObjectId id, ProductCreateDto dto) {
        Product product = productRepository.findById(id).orElseThrow(() -> notFound(id));
        characteristicRepository.deleteByIdProduct(product);
        List<CharacteristicData> characteristicData = dto.characteristicData(product, this::key);
        return new ProductDetailDto(
                productRepository.save(dto.to(product)),
                characteristicData == null ? null : characteristicRepository.saveAll(characteristicData)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDetailDto product(ObjectId id) {
        return new ProductDetailDto(productRepository.findById(id).orElseThrow(() -> notFound(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductShortDto> products(int page, int count) {
        return productRepository.findAll(PageRequest.of(page, count)).map(ProductShortDto::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductShortDto> findByTitle(String titleContainingIgnoreCase, int page, int count) {
        return productRepository.findByTitleContainingIgnoreCase(titleContainingIgnoreCase, PageRequest.of(page, count)).map(ProductShortDto::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<String> characteristicKeySet() {
        List<Key> keyList = keyRepository.findAll();
        if (keyList == null) return null;
        Set<String> keySet = new HashSet<>(keyList.size());
        keyList.forEach(e -> keySet.add(e.getId()));
        return keySet;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductShortDto> findByKeys(Collection<String> keys) {
        return productRepository.findByCharacteristicKeyIn(keys)
                .map(ProductShortDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductShortDto> findByCharacteristic(Map<String, String> characteristic) {
        return productRepository.findByCharacteristicContains(characteristic)
                .map(ProductShortDto::new)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public List<ProductShortDto> findByFilter(ProductFilterDto filterDto) {
        Stream<Product> productStream;
        if (filterDto == null || (!StringUtils.hasText(filterDto.getTitle()) && CollectionUtils.isEmpty(filterDto.getCharacteristic()))) {
            productStream = productRepository.streamAll();
        } else if (StringUtils.hasText(filterDto.getTitle()) && CollectionUtils.isEmpty(filterDto.getCharacteristic())) {
            productStream = productRepository.findByTitleContainingIgnoreCase(filterDto.getTitle());
        } else if (!StringUtils.hasText(filterDto.getTitle()) && !CollectionUtils.isEmpty(filterDto.getCharacteristic())) {
            productStream = productRepository.findByCharacteristicContains(filterDto.getCharacteristic());
        } else {
            productStream = productRepository.findByTitleContainingIgnoreCaseAndCharacteristicContains(filterDto.getTitle(), filterDto.getCharacteristic());
        }
        return productStream
                .map(ProductShortDto::new)
                .collect(Collectors.toList());
    }
}

