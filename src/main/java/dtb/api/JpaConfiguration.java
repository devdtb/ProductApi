package dtb.api;

import java.util.Properties;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Component
@Configuration
@EnableJpaRepositories(basePackages = {
		"dtb.api.repository" }, entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager")
@EnableTransactionManagement
public class JpaConfiguration {

	@Resource
	private Environment env;

	@Bean
	@Primary
	public DataSource dataSource() {
		String url = env.getRequiredProperty("spring.datasource.url");
		String username = env.getRequiredProperty("spring.datasource.username");
		String password = env.getRequiredProperty("spring.datasource.password");
		String driver = env.getRequiredProperty("spring.datasource.driver");
		
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);

		dataSource.setInitialSize(1);
		dataSource.setMaxIdle(1);
		dataSource.setMinIdle(1);
		dataSource.setMaxActive(1);

		return dataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);

		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	private Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", env.getRequiredProperty("spring.jpa.hibernate.ddl-auto"));
		properties.setProperty("hibernate.format_sql", env.getRequiredProperty("spring.jpa.format-sql"));
		properties.setProperty("hibernate.naming-strategy", env.getRequiredProperty("spring.jpa.hibernate.naming-strategy"));

		return properties;
	}
	
    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setGenerateDdl(true);
        
        jpaVendorAdapter.setShowSql(Boolean.valueOf(env.getRequiredProperty("spring.jpa.show-sql")));
        jpaVendorAdapter.setDatabasePlatform(env.getRequiredProperty("spring.jpa.properties.hibernate.dialect"));
        
        return jpaVendorAdapter;
    }  
    
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
        lef.setPackagesToScan(new String[] { "dtb.api.repository", "dtb.api.model" });
        lef.setDataSource(dataSource());
        lef.setJpaVendorAdapter(jpaVendorAdapter());

        lef.setJpaProperties(this.additionalProperties());
        return lef;
    }
}
