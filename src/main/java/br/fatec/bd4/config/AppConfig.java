package br.fatec.bd4.config;

import org.geolatte.geom.codec.db.oracle.ConnectionFinder;
import com.zaxxer.hikari.pool.HikariProxyConnection;

import lombok.SneakyThrows;
import oracle.jdbc.OracleConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;

@Configuration
public class AppConfig {
    
    @Bean
    public CustomConnectionFinderForSpatialSupport connectionFinder() {
        return new CustomConnectionFinderForSpatialSupport();
    }

    public class CustomConnectionFinderForSpatialSupport implements ConnectionFinder {
        @SneakyThrows
        @Override
        public Connection find(Connection connection) {
            return ((HikariProxyConnection) connection).unwrap(OracleConnection.class);
        }
    }
}
