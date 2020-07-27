package com.config;

import javax.sql.DataSource;

import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.rnd.catchus.model.vo.Tb20300PatentDbVO;
import com.rnd.catchus.model.vo.Tb20300PatentDbWithKirpisPlusDimensionInfosVO;
import com.rnd.solstice.model.vo.Tb20310TmBiblioListVO;
import com.rnd.solstice.model.vo.Tb20300TmDbVO;
import com.rnd.solstice.model.vo.Tb20310TmBiblioWithDimensionInfosVO;

@Configuration("sqlMapConfig")
@PropertySource("/META-INF/config/${spring.profiles.active:local}.config.xml")
public class SqlMapConfig implements EnvironmentAware {

	private Logger logger = LoggerFactory.getLogger(SqlMapConfig.class);

	private Environment env;

	@Override
	public void setEnvironment(Environment environment) {
		this.env = environment;
	}

	@Bean(name="sqlSessionSolstice")
	public SqlSessionFactoryBean sqlSessionFactorySolstice(@Qualifier("dataSourceSolstice") DataSource dataSource) throws Exception {
		return sqlSessionFactory(dataSource, env.getProperty("kp.sqlmap.db1.dbType") );
	}

	@Bean(name="sqlSessionCatchUs")
	public SqlSessionFactoryBean sqlSessionFactoryCatchUs(@Qualifier("dataSourceCatchUs") DataSource dataSource) throws Exception {
		return sqlSessionFactory(dataSource, env.getProperty("kp.sqlmap.db1.dbType") );
	}


	private SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource, String dbType) throws Exception {

		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		configuration.setCacheEnabled(true);
		configuration.setLazyLoadingEnabled(true);
		configuration.setMapUnderscoreToCamelCase(true);
		configuration.setJdbcTypeForNull(JdbcType.NULL);
		configuration.setCallSettersOnNulls(true);
		sqlSessionFactoryBean.setConfiguration(configuration);

		sqlSessionFactoryBean.setDataSource(dataSource);

		logger.debug("dbType: {}", dbType);

		sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
				.getResources("classpath:/sqlmap/mappers/" + dbType + "/**/*.xml"));

		sqlSessionFactoryBean.setTypeAliases(new Class[] {
				Tb20300TmDbVO.class
				, Tb20310TmBiblioListVO.class
				, Tb20300PatentDbVO.class
				, Tb20300PatentDbWithKirpisPlusDimensionInfosVO.class
				, Tb20310TmBiblioWithDimensionInfosVO.class
		});

		return sqlSessionFactoryBean;

	}

}
