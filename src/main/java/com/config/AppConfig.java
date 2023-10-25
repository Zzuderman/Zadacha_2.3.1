package com.config;


import com.model.User;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
@ComponentScan(value = "com")
public class AppConfig {

   @Autowired
   private Environment env;

   @Bean
   public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
      LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
      em.setDataSource(getDataSource());
      em.setPackagesToScan(env.getRequiredProperty("db.entity.package"));

      em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
      em.setJpaProperties(getHibernateProperties());

      return  em;
   }

   @Bean
   public DataSource getDataSource() {
      BasicDataSource dataSource = new BasicDataSource();
      dataSource.setDriverClassName(env.getRequiredProperty("db.driver"));
      dataSource.setUrl(env.getRequiredProperty("db.url"));
      dataSource.setUsername(env.getRequiredProperty("db.username"));
      dataSource.setPassword(env.getRequiredProperty("db.password"));

      dataSource.setInitialSize(Integer.valueOf(env.getRequiredProperty("db.initialSize")));
      dataSource.setMinIdle(Integer.valueOf(env.getRequiredProperty("db.minIdle")));
      dataSource.setMaxIdle(Integer.valueOf(env.getRequiredProperty("db.maxIdle")));
      dataSource.setTimeBetweenEvictionRunsMillis(Integer.valueOf(env.getRequiredProperty("db.timeBetweenEvictionRunsMillis")));
      dataSource.setMinEvictableIdleTimeMillis(Integer.valueOf(env.getRequiredProperty("db.minEvictableIdleTimeMillis")));
      dataSource.setTestOnBorrow(Boolean.valueOf(env.getRequiredProperty("db.testOnBorrow")));
      dataSource.setValidationQuery(env.getRequiredProperty("db.validationQuery"));

      return dataSource;
   }

   @Bean
   public PlatformTransactionManager platformTransactionManager() {
      JpaTransactionManager manager = new JpaTransactionManager();
      manager.setEntityManagerFactory(entityManagerFactoryBean().getObject());

      return manager;
   }

   @Bean
   public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
      return new PersistenceExceptionTranslationPostProcessor();
   }

   private Properties getHibernateProperties() {
      Properties properties = new Properties();
      properties.setProperty("hibernate.hbm2ddl.auto", "none");
      properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");

      return properties;
   }

}
