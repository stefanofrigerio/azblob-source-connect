package com.generali.aateam.connectors.client;

import com.generali.aateam.connectors.config.Config;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Properties;

public class DatabaseClient {

    final static Logger logger = Logger.getLogger(DatabaseClient.class);
    private final String insertQuery="insert into processed_blobs values (?)";
    private static Connection conn;
    private static DatabaseClient ourInstance = new DatabaseClient();

    public static DatabaseClient getInstance() {
        return ourInstance;
    }

    private DatabaseClient() {
        String url = Config.getConfig().getValue(Config.CONFIG_DATABASE_JDBC_URL);
        Properties props = new Properties();
        props.setProperty("user",Config.getConfig().getValue(Config.CONFIG_DATABASE_USERNAME));
        props.setProperty("password",Config.getConfig().getValue(Config.CONFIG_DATABASE_PASSWORD));
        props.setProperty("ssl","true");
        try {
            conn = DriverManager.getConnection(url, props);
        } catch (SQLException ex){
            logger.error("cannot connect to database: "+ex);
        }
    }

    public boolean writeState(String MD5){
        try {
            PreparedStatement pst = conn.prepareStatement(insertQuery);
            pst.setString(1,MD5);
            int result = pst.executeUpdate();
            if (result==1) return true; else return false;
        } catch (SQLException ex){
            logger.error("error during insert queryS: "+ex);
            return false;
        }

    }
}
