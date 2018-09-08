package it.lei.boot.data.jdbc.Config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

public class DataSourceConfig {
    @Bean(name="dataSource")
    public DataSource dataSource(Environment environment){
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(environment.getProperty("jdbc:mysql://127.0.0.1:3306/mysql"));
        hikariDataSource.setUsername(environment.getProperty("spring.datasource.username"));
        hikariDataSource.setPassword(environment.getProperty("spring.datasource.password"));
        hikariDataSource.setDriverClassName("spring.datasource.driver-class-name");
        return  hikariDataSource;
    }
}
