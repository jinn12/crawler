package com.config;


import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jndi.JndiTemplate;


@Configuration
@PropertySource("/META-INF/config/${spring.profiles.active:local}.config.xml")
@Profile(value={"!test"})
public class DataSourceConfig implements EnvironmentAware {

	private Environment env;

	@Override
	public void setEnvironment(Environment environment) {
		this.env = environment;
	}

	@SuppressWarnings("resource")
	@Bean(name="dataSourceSolstice")
	public DataSource dataSourceSolstice() throws Exception {

		DataSource dataSource = null;

		if("basic".equals(env.getProperty("kp.solstice.datasource.type"))){
			BasicDataSource basicDataSource = new BasicDataSource();

			basicDataSource.setDriverClassName(env.getProperty("kp.solstice.datasource.basic.mysql.driverClassName"));
			basicDataSource.setUrl(env.getProperty("kp.solstice.datasource.basic.mysql.url"));
			basicDataSource.setUsername(env.getProperty("kp.solstice.datasource.basic.mysql.username"));
			basicDataSource.setPassword(env.getProperty("kp.solstice.datasource.basic.mysql.password"));
			basicDataSource.setMaxIdle(100);
			basicDataSource.setMinIdle(5);

			dataSource = basicDataSource;
		} else if("jndi".equals(env.getProperty("kp.solstice.datasource.type"))){
			dataSource = (DataSource) new JndiTemplate().lookup(env.getProperty("kp.solstice.datasource.basic.mysql.jndiName"));
		}
		return dataSource;

	}

	@SuppressWarnings("resource")
	@Bean(name="dataSourceCatchUs")
	public DataSource dataSourceCatchUs() throws Exception {

		DataSource dataSource = null;

		if("basic".equals(env.getProperty("kp.catchus.datasource.type"))){
			BasicDataSource basicDataSource = new BasicDataSource();

			basicDataSource.setDriverClassName(env.getProperty("kp.catchus.datasource.basic.mysql.driverClassName"));
			basicDataSource.setUrl(env.getProperty("kp.catchus.datasource.basic.mysql.url"));
			basicDataSource.setUsername(env.getProperty("kp.catchus.datasource.basic.mysql.username"));
			basicDataSource.setPassword(env.getProperty("kp.catchus.datasource.basic.mysql.password"));
			basicDataSource.setMaxIdle(100);
			basicDataSource.setMinIdle(5);

			dataSource = basicDataSource;
		} else if("jndi".equals(env.getProperty("kp.catchus.datasource.type"))){
			dataSource = (DataSource) new JndiTemplate().lookup(env.getProperty("kp.catchus.datasource.basic.mysql.jndiName"));
		}
		return dataSource;

	}


}
