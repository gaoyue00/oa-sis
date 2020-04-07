package com.cjs.example.lock.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.DriverManager;

/**
 * @描述：
 * @author： yue.gao
 * @date： 2020/3/13 17:12
 */
@Configuration
@MapperScan("com.cjs.example.lock")
@ComponentScan("com.cjs.example.lock")
public class AppConfig {

    @Autowired
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean;
    }

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("oasisadmin");
        driverManagerDataSource.setUrl("jdbc:mysql://60.205.106.190:3306/test_gy?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&autoReconnect=true&useSSL=false");
        return driverManagerDataSource;
    }
}
