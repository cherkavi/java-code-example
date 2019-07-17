package com.cherkashyn.vitalii.examples.springshell;

import com.cherkashyn.vitalii.examples.springshell.listener.ProcessListener;
import com.cherkashyn.vitalii.examples.springshell.listener.TaskEventListener;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@SpringBootApplication
public class SpringshellApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringshellApplication.class, args);
	}


	@Bean
	public String exampleBean(){
		return "this is my own example";
	}

	@Bean("activitiDataSourceProperties")
	@Primary
	@ConfigurationProperties(prefix = "datasource.activiti")
	public DataSourceProperties dataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean("activitiDataSource")
	public DataSource activitiDataSource(@Qualifier("activitiDataSourceProperties") DataSourceProperties properties) {
		// DataSourceBuilder
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(properties.getDriverClassName());
		dataSource.setUrl(properties.getUrl());
		dataSource.setUsername(properties.getUsername());
		dataSource.setPassword(properties.getPassword());
		return dataSource;
	}

    @Bean
    public ProcessEngineConfigurationImpl springProcessEngineConfiguration(@Qualifier("activitiDataSource") DataSource activitiDataSource){
        // standalone configuration
        // ProcessEngineConfigurationImpl configuration = (ProcessEngineConfigurationImpl) SpringProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();

        // skip using TransactionManager
        // SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration(){public CommandInterceptor createTransactionInterceptor() {return null;}};

        SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
        configuration.setTransactionManager(new DataSourceTransactionManager(activitiDataSource));
        configuration.setDataSource(activitiDataSource);
        // configuration.setDatabaseSchemaUpdate("create-drop");
        // configuration.setHistoryLevel(HistoryLevel.AUDIT);
        return configuration;
    }

	@Bean
	public ProcessEngine activitiProcessEngine(ProcessEngineConfigurationImpl configuration, ApplicationContext context) throws Exception {
		ProcessEngineFactoryBean factory = new ProcessEngineFactoryBean();
		factory.setApplicationContext(context);
		factory.setProcessEngineConfiguration(configuration);

		ProcessEngine processEngine =  factory.getObject();
		// processEngine.getRepositoryService().createDeployment().addClasspathResource("processes/direct-run.bpmn").deploy();
//		ProcessEngine processEngine = configuration.buildProcessEngine();
		return processEngine;
	}

	@Bean("printActivitiEnvironment")
	public PrintActivitiEnvironment printActivitiEnvironment(){
		return new PrintActivitiEnvironment();
	}

	@Bean("listenerStartProcess")
	public ProcessListener getProcessStartListener(){
		return new ProcessListener();
	}

	@Bean("listenerEndProcess")
	public ProcessListener getProcessEndListener(){
		return new ProcessListener();
	}

	@Bean("listenerStartTask")
	public TaskEventListener getTaskStartListener(){
		return new TaskEventListener();
	}
	@Bean("listenerEndTask")
	public TaskEventListener getTaskStopListener(){
		return new TaskEventListener();
	}


}
