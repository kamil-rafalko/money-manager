package com.corriel.config;

import com.corriel.config.exceptions.CustomSimpleMappingExceptionResolver;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;

@EnableWebMvc
@Configuration
@ComponentScan({ "com.corriel" })
@EnableTransactionManagement
@Import({ SecurityConfig.class })
public class AppConfig extends WebMvcConfigurerAdapter {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setJpaVendorAdapter(hibernateJpaVendorAdapter());
        bean.setDataSource(dataSource());
        bean.setPackagesToScan("com.corriel.users.entity", "com.corriel.budget.entity");
        return bean;
    }

    private HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setDatabase(Database.POSTGRESQL);
        hibernateJpaVendorAdapter.setShowSql(false);
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQL9Dialect");
        return hibernateJpaVendorAdapter;
    }

    @Bean
    public DataSource dataSource() {
        final JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
        dataSourceLookup.setResourceRef(true);
        return dataSourceLookup.getDataSource("jdbc/postgres");
    }

    @Bean
    public HibernateJpaDialect jpaDialect() {
        return new HibernateJpaDialect();
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        jpaTransactionManager.setJpaDialect(jpaDialect());
        return jpaTransactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        return new InternalResourceViewResolver();
    }

    @Bean
    public MappingJackson2HttpMessageConverter jacksonMessageConverter(){
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Hibernate5Module());
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        messageConverter.setObjectMapper(mapper);
        return messageConverter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(jacksonMessageConverter());
        super.configureMessageConverters(converters);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/*")
                .addResourceLocations("/resources/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/index.html");
    }

    @Bean
    public SimpleMappingExceptionResolver createSimpleMappingExceptionResolver(){
        SimpleMappingExceptionResolver resolver = new CustomSimpleMappingExceptionResolver();

        Properties mappings = new Properties();
        mappings.setProperty("Exception", "exception");

        resolver.setExceptionMappings(mappings);
        resolver.setDefaultErrorView("error");
        return resolver;
    }
}

