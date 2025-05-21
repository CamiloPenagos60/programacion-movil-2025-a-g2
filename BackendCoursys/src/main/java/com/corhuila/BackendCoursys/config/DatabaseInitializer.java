package com.corhuila.BackendCoursys.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@Configuration
public class DatabaseInitializer {

    @Bean
    CommandLineRunner createDatabase(DataSource dataSource) {
        return args -> {
            String dbName = "coursys";

            // Obtener URL del datasource sin base de datos para crearla si no existe
            String url = dataSource.getConnection().getMetaData().getURL();
            // Ejemplo:     jdbc:mysql://127.0.0.1:3307/coursys
            String baseUrl = url.substring(0, url.lastIndexOf("/")+1);

            try (Connection connection = dataSource.getConnection();
                 Statement statement = connection.createStatement()) {

                String createDbSql = "CREATE DATABASE IF NOT EXISTS `" + dbName + "` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci";
                statement.executeUpdate(createDbSql);

                System.out.println("✅ Base de datos asegurada: " + dbName);

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("❌ Error al crear la base de datos");
            }
        };
    }
}
