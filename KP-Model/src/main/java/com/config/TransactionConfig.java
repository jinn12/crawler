package com.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement(proxyTargetClass=true)
public class TransactionConfig {

	@Bean(name="txManagerSolstice")
	public DataSourceTransactionManager txManagerSolstice(@Qualifier("dataSourceSolstice") DataSource dataSource) {

		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
		return dataSourceTransactionManager;

	}

	@Bean(name="txManagerCatchUs")
	public DataSourceTransactionManager txManagerCatchUs(@Qualifier("dataSourceCatchUs") DataSource dataSource) {

		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
		return dataSourceTransactionManager;

	}

}
