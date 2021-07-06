package io.muzoo.ooc.webapp.basic.security;

import com.zaxxer.hikari.HikariDataSource;
import io.muzoo.ooc.webapp.basic.config.ConfigProperties;
import io.muzoo.ooc.webapp.basic.config.ConfigurationLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBaseConnectionService {

    private final HikariDataSource ds;

    public DataBaseConnectionService() {
        ds = new HikariDataSource();
        ds.setMaximumPoolSize(20);

        ConfigProperties configProperties = ConfigurationLoader.load();
        if(configProperties == null){
            throw new RuntimeException("Unable to read the config.properties");
        }
        ds.setDriverClassName(configProperties.getDatabaseDriverClassName());
        ds.setJdbcUrl(configProperties.getDatabaseConnectionUrl());
        ds.addDataSourceProperty("user", configProperties.getDatabaseUsername());
        ds.addDataSourceProperty("password", configProperties.getDatabasePassword());
        ds.setAutoCommit(false);
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

//    public static void main(String[] args) {
//
//
//        try {
//            Connection connection = ds.getConnection();
//            String sql = "INSERT INTO tbl_user (username, password, display_name) VALUES (?,?,?);";
//            PreparedStatement ps = connection.prepareStatement(sql);
//            // setting username column 1
//            ps.setString(1,"my_username");
//            // setting password column 1
//            ps.setString(2,"my_password");
//            // setting display name column 1
//            ps.setString(3,"my_display_name");
//
//            ps.executeUpdate();
//            // need to be manually committed to change
//            connection.commit();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
