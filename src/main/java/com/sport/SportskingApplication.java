package com.sport;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.sport.models.Role;
import com.sport.repository.RoleRepository;


@SpringBootApplication
public class SportskingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportskingApplication.class, args);
	}

	
	 //Defining the MessageSource Bean
	  @Bean
	    public MessageSource messageSource() {
	        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	        messageSource.setBasename("classpath:messages");
	        messageSource.setDefaultEncoding("UTF-8");
	        return messageSource;
	    }

	  
		 //Defining LocalValidatorFactoryBean
	    @Bean
	    public LocalValidatorFactoryBean validator() {
	        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
	        bean.setValidationMessageSource(messageSource());
	        return bean;
	    }
	    
	    
	    
//	    @Bean
//	    public DataSourceInitializer dataSourceInitializer(@Qualifier("dataSource") final DataSource dataSource) {
//	        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
//	        resourceDatabasePopulator.addScript(new ClassPathResource("/data.sql"));
//	        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
//	        dataSourceInitializer.setDataSource(dataSource);
//	        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
//	        return dataSourceInitializer;
//	    }
	
	
	
	 
	 
}
