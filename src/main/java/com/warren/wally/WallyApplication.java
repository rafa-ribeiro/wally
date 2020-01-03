package com.warren.wally;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;


@SpringBootApplication
@EnableJpaRepositories("com.warren.wally.*")
@ComponentScan(basePackages = {"com.warren.wally.*"})
@EntityScan("com.warren.wally.*")
public class WallyApplication {

    public static void main(String[] args) {
        SpringApplication.run(WallyApplication.class, args);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean factoryBean =
                new LocalContainerEntityManagerFactoryBean();

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        factoryBean.setDataSource(dataSource);
        factoryBean.setJpaProperties(aditionalProperties());
        factoryBean.setPackagesToScan("com.warren.wally");
        return factoryBean;
    }

    private Properties aditionalProperties() {
        Properties props = new Properties();
        // Setar a property "hibernate.dialect" se/quando tivermos um WallyAplicationH2, por exemplo.
        props.setProperty("hibernate.show_sql", "true");
        props.setProperty("hibernate.hbm2ddl.auto", "update");
        return props;
    }
}
