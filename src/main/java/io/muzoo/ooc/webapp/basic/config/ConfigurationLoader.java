package io.muzoo.ooc.webapp.basic.config;

import java.io.FileInputStream;
import java.io.ObjectInputFilter;
import java.util.Properties;

public class ConfigurationLoader {

    //added static method for loading configuration from disk
    //default location is 'config.properties' in the same folder.

    public static ConfigProperties load(){
        String configFileName = "config.properties";
        try(FileInputStream fin = new FileInputStream(configFileName);) {
            Properties prop = new Properties();
            prop.load(fin);

            // get the property value and print it out
            String driverClassName = prop.getProperty("database.driverClassName");
            String connectionUrl = prop.getProperty("database.connectionUrl");
            String username = prop.getProperty("database.username");
            String password = prop.getProperty("database.password");

            ConfigProperties cp = new ConfigProperties();
            cp.setDatabaseDriverClassName(driverClassName);
            cp.setDatabaseConnectionUrl(connectionUrl);
            cp.setDatabaseUsername(username);
            cp.setDatabasePassword(password);

            return cp;
        } catch (Exception e) {
            return null;
        }
    }
}
