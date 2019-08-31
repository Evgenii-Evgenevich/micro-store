package com.github.evgeniievgenevich.microstore.dao;

import com.github.evgeniievgenevich.microstore.model.CharacteristicData;
import com.github.evgeniievgenevich.microstore.model.Product;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Characteristic Data Access Object
 *
 * @author Evgenii Evgenevich
 */
public class CharacteristicDaoImpl implements CharacteristicDao {
    private final EntityManager entityManager;

    @Autowired
    public CharacteristicDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<CharacteristicData> findByIdProduct(Product product) {
        return entityManager.createQuery("select c from CharacteristicData c where c.id.product.id = :p", CharacteristicData.class)
                .setParameter("p", product.getId())
                .getResultList();
    }
}
