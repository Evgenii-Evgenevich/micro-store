package com.github.evgeniievgenevich.microstore.config;

import org.hibernate.engine.transaction.jta.platform.internal.JBossStandAloneJtaPlatform;
import org.hibernate.ogm.datastore.mongodb.MongoDB;
import org.hibernate.ogm.datastore.mongodb.MongoDBDialect;
import org.hibernate.ogm.jpa.HibernateOgmPersistence;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.github.evgeniievgenevich.microstore.dao"
)
public class MongdbConfig {
    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.port}")
    private String port;

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Primary
    @Bean(destroyMethod = "close")
    public EntityManagerFactory entityManagerFactory() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.transactionType", "JTA");
        properties.put("javax.persistence.provider", HibernateOgmPersistence.class.getName());
        properties.put("hibernate.ogm.datastore.provider", MongoDB.DATASTORE_PROVIDER_NAME);
        properties.put("hibernate.ogm.datastore.grid_dialect", MongoDBDialect.class.getName());
        properties.put("hibernate.ogm.datastore.host", host);
        properties.put("hibernate.ogm.datastore.port", port);
        properties.put("hibernate.ogm.datastore.database", database);
        properties.put("hibernate.transaction.jta.platform", JBossStandAloneJtaPlatform.class.getName());

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPackagesToScan("com.github.evgeniievgenevich.microstore.model");
        factoryBean.setPersistenceUnitName("primary");
        factoryBean.setPersistenceProviderClass(HibernateOgmPersistence.class);
        factoryBean.setJpaPropertyMap(properties);
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }

    @Primary
    @Bean
    public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory bean) {
        return new JpaTransactionManager(bean);
    }
}


