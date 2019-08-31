package com.github.evgeniievgenevich.microstore.dao;

import com.github.evgeniievgenevich.microstore.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Stream;
/**
 * Product Data Access Object
 *
 * @author Evgenii Evgenevich
 */
public class ProductDaoImpl implements ProductDao {
    private final EntityManager entityManager;

    @Autowired
    public ProductDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Stream<Product> streamAll() {
        return entityManager.createQuery("select p from Product p", Product.class).getResultList().stream();
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        Long total = entityManager.createQuery("select count(p) from Product p", Long.class).getSingleResult();
        TypedQuery<Product> p = entityManager.createQuery("select p from Product p", Product.class);
        if (total != null && pageable.isPaged()) {
            return new PageImpl<>(
                    p
                            .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                            .setMaxResults(pageable.getPageSize())
                            .getResultList(),
                    pageable,
                    total
            );
        }
        return new PageImpl<>(p.getResultList());
    }

    @Override
    public Stream<Product> findByTitleContainingIgnoreCase(String titleContainingIgnoreCase) {
        return entityManager.createQuery("select p from Product p where upper(p.title) like upper(:s)", Product.class)
                .setParameter("s", '%' + titleContainingIgnoreCase + '%')
                .getResultList().stream();
    }

    @Override
    public Page<Product> findByTitleContainingIgnoreCase(String titleContainingIgnoreCase, Pageable pageable) {
        Long total = entityManager.createQuery("select count(p) from Product p where upper(p.title) like upper(:s)", Long.class)
                .setParameter("s", '%' + titleContainingIgnoreCase + '%')
                .getSingleResult();
        TypedQuery<Product> p = entityManager.createQuery("select p from Product p where upper(p.title) like upper(:s)", Product.class)
                .setParameter("s", '%' + titleContainingIgnoreCase + '%');
        if (total != null && pageable.isPaged()) {
            return new PageImpl<>(
                    p
                            .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                            .setMaxResults(pageable.getPageSize())
                            .getResultList(),
                    pageable,
                    total
            );
        }
        return new PageImpl<>(p.getResultList());
    }

    @Override
    public Stream<Product> findByCharacteristicKeyIn(Collection<String> keys) {
        return entityManager.createQuery("select distinct c.id.product from CharacteristicData c where c.id.key in :k", Product.class)
                .setParameter("k", keys)
                .getResultList().stream();
    }

    @Override
    public Stream<Product> findByCharacteristicContains(Map<String, String> characteristic) {
        return characteristic.entrySet()
                .parallelStream()
                .flatMap(e ->
                        entityManager.createQuery("select distinct c.id.product from CharacteristicData c where c.id.key = :k and c.value = :v", Product.class)
                                .setParameter("k", e.getKey())
                                .setParameter("v", e.getValue())
                                .getResultList().stream()
                )
                .distinct()
                .sorted(Comparator.comparing(Product::getId));
    }

    @Override
    public Stream<Product> findByTitleContainingIgnoreCaseAndCharacteristicContains(String titleContainingIgnoreCase, Map<String, String> characteristic) {
        return characteristic.entrySet()
                .parallelStream()
                .flatMap(e ->
                        entityManager.createQuery("select distinct c.id.product from CharacteristicData c where c.id.product.title like upper(:s) and c.id.key = :k and c.value = :v", Product.class)
                                .setParameter("s", '%' + titleContainingIgnoreCase + '%')
                                .setParameter("k", e.getKey())
                                .setParameter("v", e.getValue())
                                .getResultList().stream()
                )
                .distinct()
                .sorted(Comparator.comparing(Product::getId));
    }
}
