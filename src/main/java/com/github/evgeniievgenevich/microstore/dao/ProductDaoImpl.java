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
        return entityManager.createQuery("select p from Product p", Product.class).getResultStream();
    }

//    @Override
//    public Page<Product> findAll(Pageable pageable) {
//        Long total = entityManager.createQuery("select count(p) from Product p", Long.class).getSingleResult();
//        TypedQuery<Product> p = entityManager.createQuery("select p from Product p", Product.class);
//        if (total != null && pageable.isPaged()) {
//            return new PageImpl<>(
//                    p
//                            .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
//                            .setMaxResults(pageable.getPageSize())
//                            .getResultList(),
//                    pageable,
//                    total
//            );
//        }
//        return new PageImpl<>(p.getResultList());
//    }

    @Override
    public Stream<Product> findByTitleContainingIgnoreCase(String titleContainingIgnoreCase) {
        return entityManager.createQuery("select p from Product p where upper(p.title) like upper(?1)", Product.class)
                .setParameter(1, '%' + titleContainingIgnoreCase + '%')
                .getResultStream();
    }

    @Override
    public Page<Product> findByTitleContainingIgnoreCase(String titleContainingIgnoreCase, Pageable pageable) {
        Long total = entityManager.createQuery("select count(p) from Product p where upper(p.title) like upper(?1)", Long.class)
                .setParameter(1, '%' + titleContainingIgnoreCase + '%')
                .getSingleResult();
        TypedQuery<Product> p = entityManager.createQuery("select p from Product p where upper(p.title) like upper(?1)", Product.class)
                .setParameter(1, '%' + titleContainingIgnoreCase + '%');
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
        return entityManager.createQuery("select distinct c.product from CharacteristicData c where c.id.key in ?1", Product.class)
                .setParameter(1, keys)
                .getResultStream();
    }

    @Override
    public Stream<Product> findByCharacteristicContains(Map<String, String> characteristic) {
        return characteristic.entrySet()
                .parallelStream()
                .flatMap(e ->
                        entityManager.createQuery("select distinct c.id.product from CharacteristicData c where c.id.key = ?1 and c.value = ?2", Product.class)
                                .setParameter(1, e.getKey())
                                .setParameter(2, e.getValue())
                                .getResultStream()
                )
                .distinct()
                .sorted(Comparator.comparing(Product::getId));
    }

    @Override
    public Stream<Product> findByTitleContainingIgnoreCaseAndCharacteristicContains(String titleContainingIgnoreCase, Map<String, String> characteristic) {
        return characteristic.entrySet()
                .parallelStream()
                .flatMap(e ->
                        entityManager.createQuery("select distinct c.id.product from CharacteristicData c where c.id.product.title like upper(?1) and c.key = ?2 and c.value = ?3", Product.class)
                                .setParameter(1, '%' + titleContainingIgnoreCase + '%')
                                .setParameter(2, e.getKey())
                                .setParameter(3, e.getValue())
                                .getResultStream()
                )
                .distinct()
                .sorted(Comparator.comparing(Product::getId));
    }
}
