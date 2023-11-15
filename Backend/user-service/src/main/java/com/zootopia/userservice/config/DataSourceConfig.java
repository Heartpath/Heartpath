package com.zootopia.userservice.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


@Configuration
public class DataSourceConfig {

    // application.yml의 설정 정보를 토대로 HikariCP 설정
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari") // 읽어올 설정 정보의 prefix 지정
    public HikariConfig hikaryConfig() {
        return new HikariConfig();
    }

    // Connection Pool을 관리하는 DataSource 인터페이스 객체 선언
    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(hikaryConfig());
    }

    // SqlSessionTemplate에서 사용할 SqlSession을 생성하는 Factory
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        /*
         * MyBatis는 JdbcTemplate 대신 Connection 객체를 통한 질의를 위해서 SqlSession을 사용한다.
         * 내부적으로 SqlSessionTemplate가 SqlSession을 구현한다.
         * Thread-Safe하고 여러 개의 Mapper에서 공유할 수 있다.
         */
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);

        // MyBatis Mapper Source
        // MyBatis의 SqlSession에서 불러올 쿼리 정보
        Resource[] res = new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml");
        bean.setMapperLocations(res);
        bean.setTypeAliasesPackage("com.zootopia.userservice.dto");

        // MyBatis Config Setting
        // MyBatis 설정 파일
//        Resource myBatisConfig = new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml");
//        bean.setConfigLocation(myBatisConfig);

        return bean.getObject();
    }

    // DataSource에서 Transaction 관리를 위한 Manager 클래스 등록
    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
