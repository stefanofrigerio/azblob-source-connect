package com.generali.aateam.connectors.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Config {

    final static Logger logger = Logger.getLogger(Config.class);
    private static Config instance;
    public Properties props;
    //ENV VARIABLES LISTED HERE
    public static final String CONFIG_ACCOUNT_NAME="ACCOUNT_NAME";
    public static final String CONFIG_ACCOUNT_KEY="ACCOUNT_KEY";
    public static final String CONFIG_CONTAINER_REFERENCE="CONTAINER_REFERENCE";
    public static final String CONFIG_ROOT_FOLDER="ROOT_FOLDER";
    public static final String CONFIG_BROKER_LIST="BROKER_LIST";
    public static final String CONFIG_EXACTLY_ONCE_ENABLED="EXACTLY_ONCE_ENABLED";
    public static final String CONFIG_DATABASE_JDBC_URL="DATABASE_JDBC_URL";
    public static final String CONFIG_DATABASE_USERNAME="DATABASE_USERNAME";
    public static final String CONFIG_DATABASE_PASSWORD="DATABASE_PASSWORD";

    private static final String ACCOUNT_NAME=System.getenv(CONFIG_ACCOUNT_NAME);
    private static final String ACCOUNT_KEY=System.getenv(CONFIG_ACCOUNT_KEY);
    private static final String CONTAINER_REFERENCE=System.getenv(CONFIG_CONTAINER_REFERENCE);
    private static final String ROOT_FOLDER=System.getenv(CONFIG_ROOT_FOLDER);
    private static final String BROKER_LIST=System.getenv(CONFIG_BROKER_LIST);
    private static final String EXACTLY_ONCE_ENABLED=System.getenv(CONFIG_EXACTLY_ONCE_ENABLED);
    private static final String DATABASE_JDBC_URL=System.getenv(CONFIG_DATABASE_JDBC_URL);
    private static final String DATABASE_USERNAME=System.getenv(CONFIG_DATABASE_USERNAME);
    private static final String DATABASE_PASSWORD=System.getenv(CONFIG_DATABASE_PASSWORD);

    private Config(){
        try {
            props = new Properties();
            Properties external = new Properties();
            Properties internal = new Properties();
            internal.load(Config.class.getResourceAsStream("/azconnect.application.properties"));
            external.put(CONFIG_ACCOUNT_NAME,ACCOUNT_NAME);
            external.put(CONFIG_ACCOUNT_KEY,ACCOUNT_KEY);
            external.put(CONFIG_CONTAINER_REFERENCE,CONTAINER_REFERENCE);
            external.put(CONFIG_ROOT_FOLDER,ROOT_FOLDER);
            external.put(CONFIG_BROKER_LIST,BROKER_LIST);
            external.put(CONFIG_EXACTLY_ONCE_ENABLED,EXACTLY_ONCE_ENABLED);
            external.put(CONFIG_DATABASE_JDBC_URL,DATABASE_JDBC_URL);
            external.put(CONFIG_DATABASE_USERNAME,DATABASE_USERNAME);
            external.put(CONFIG_DATABASE_PASSWORD,DATABASE_PASSWORD);


            props.putAll(internal);
            props.putAll(external);
        } catch (FileNotFoundException e) {
            logger.error("properties files not found - exiting",e);
            System.exit(1);
        } catch (IOException e) {
            logger.error("i/o error while read props file - exiting",e);
            System.exit(1);

        } catch (NullPointerException e){
            logger.error("some env variable not set - exiting",e);
            System.exit(1);
        }
    }


    public String getValue(String key){
        return getConfig().props.getProperty(key);
    }


    public static Config getConfig(){
        if (instance==null) {
            instance = new Config();
            //logger.debug(System.getenv());
            logger.info("Configuration: "+instance.props.toString());
        }
        return instance;
    }
}