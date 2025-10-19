package com.jaz.inventory.Model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;

@Component
public class InitializeDataBase implements CommandLineRunner {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Override
    public void run(String... args) throws Exception {
        String serverUrl = url.substring(0,url.lastIndexOf('/'));
        try (Connection conn = DriverManager.getConnection(serverUrl, username, password);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS inventorydb");
        }

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS inventory (" +
                            "id INT AUTO_INCREMENT PRIMARY KEY," +
                            "name VARCHAR(100) NOT NULL," +
                            "quantity INT NOT NULL," +
                            "price FLOAT NOT NULL," +
                            "description TEXT)"
            );
        }

        System.out.println("Database and table checked/created successfully");
    }
}
