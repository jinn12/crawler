package com.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

@Configuration
@Import({
	AspectConfig.class,
	CommonConfig.class,
	DataSourceConfig.class,
	SqlMapConfig.class,
	TransactionConfig.class,
})
@ImportResource({

})
@Profile(value={"!test"})
public class RootConfig {

}
