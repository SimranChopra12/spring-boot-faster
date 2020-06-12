package ewing.faster.application.config;

import com.querydsl.sql.MySQLTemplates;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.spring.SpringExceptionTranslator;
import ewing.query.BaseQueryFactory;
import ewing.query.support.FriendlySQLLogger;
import ewing.query.support.SafeSQLListener;
import ewing.query.support.SpringConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * Querydsl 配置类。
 *
 * @author Ewing
 */
@Configuration
public class FasterDaoConfig {

    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @Autowired
    public BaseQueryFactory queryFactory(DataSource dataSource) {
        SQLTemplates templates = MySQLTemplates.builder().quote().build();
        com.querydsl.sql.Configuration configuration = new com.querydsl.sql.Configuration(templates);
        configuration.setExceptionTranslator(new SpringExceptionTranslator());
        configuration.addListener(new FriendlySQLLogger());
        configuration.addListener(new SafeSQLListener());
        return new BaseQueryFactory(configuration, new SpringConnectionProvider(dataSource, configuration));
    }

}