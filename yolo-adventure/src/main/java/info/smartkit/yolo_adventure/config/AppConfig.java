package info.smartkit.yolo_adventure.config;

import info.smartkit.yolo_adventure.aspects.NotifyAspect;
import info.smartkit.yolo_adventure.dto.IdeaDto;
import info.smartkit.yolo_adventure.model.Idea;

import javax.sql.DataSource;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.mvc.Controller;

@Configuration
@ComponentScan(basePackages = { "info.smartkit.yolo_adventure" }, excludeFilters = { @ComponentScan.Filter(value = Controller.class, type = FilterType.ANNOTATION) })
@EnableJpaRepositories(basePackages = { "info.smartkit.yolo_adventure.repository" })
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class AppConfig {
	// Config

	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL)
				.build();
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setShowSql(true);
		adapter.setGenerateDdl(true);
		adapter.setDatabase(Database.HSQL);
		return adapter;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory()
			throws ClassNotFoundException {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean.setPersistenceUnitName("ideas");
		factoryBean.setJpaVendorAdapter(jpaVendorAdapter());

		return factoryBean;
	}

	@Bean
	public JpaTransactionManager transactionManager()
			throws ClassNotFoundException {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory()
				.getObject());

		return transactionManager;
	}
	
	@Bean
	public BeanMappingBuilder beanMappingBuilder() {
	    BeanMappingBuilder builder = new BeanMappingBuilder() {
	        protected void configure() {
	            mapping(IdeaDto.class, Idea.class);
	        }
	    };

	    return builder;
	}

	@Bean
	public Mapper mapper() {
	    DozerBeanMapper mapper = new DozerBeanMapper();
	    mapper.addMapping(beanMappingBuilder());

	    return mapper;
	}
	
	@Bean
	public NotifyAspect notifyAspect() {
	    return new NotifyAspect();
	}
}