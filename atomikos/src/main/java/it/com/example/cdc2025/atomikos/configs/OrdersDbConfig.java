package it.com.example.cdc2025.atomikos.configs;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        basePackages = "it.com.example.cdc2025.atomikos.repos.ord",
        entityManagerFactoryRef = "ordersEntityManager"
)
public class OrdersDbConfig {

    @Bean(name = "ordersDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.orders")
    public DataSource ordersDataSource() {
        return new AtomikosDataSourceBean();
    }

    @Bean(name = "ordersEntityManager")
    public LocalContainerEntityManagerFactoryBean ordersEntityManager() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJtaDataSource(ordersDataSource());
        factory.setPackagesToScan("it.com.example.cdc2025.atomikos.entities.ord");
        factory.setPersistenceUnitName("ordersPU");
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties jpa = new Properties();
        jpa.put("hibernate.hbm2ddl.auto", "update");
        jpa.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        jpa.put("hibernate.transaction.jta.platform", "org.hibernate.engine.transaction.jta.platform.internal.AtomikosJtaPlatform");

        factory.setJpaProperties(jpa);
        return factory;
    }
}
