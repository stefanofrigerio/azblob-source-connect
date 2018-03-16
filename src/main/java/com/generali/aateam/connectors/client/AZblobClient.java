package com.generali.aateam.connectors.client;

import com.generali.aateam.connectors.config.Config;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.*;
import kafka.utils.json.JsonObject;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;

public class AZblobClient {

    final static Logger logger = Logger.getLogger(Config.class);
    private static AZblobClient instance;
    private CloudStorageAccount account;
    private CloudBlobClient blobClient;
    CloudBlobContainer container;

    public static AZblobClient getClient(){
        if (instance==null) {
            instance = new AZblobClient();
            //logger.info(System.getenv());
            //logger.info("Configuration: "+Config.getConfig());
        }
        return instance;
    }
    private AZblobClient(){

        final String storageConnectionString =
                "DefaultEndpointsProtocol=https;"
                        + "AccountName="+Config.getConfig().getValue(Config.CONFIG_ACCOUNT_NAME)+";"
                        + "AccountKey="+Config.getConfig().getValue(Config.CONFIG_ACCOUNT_KEY);//+";EndpointSuffix=core.windows.net";
        try {
            logger.debug("connection string: "+storageConnectionString);
            account = CloudStorageAccount.parse(storageConnectionString);

            blobClient = account.createCloudBlobClient();
            //logger.debug(serviceClient.getServiceStats().toString());
            container = blobClient.getContainerReference(Config.getConfig().getValue(Config.CONFIG_CONTAINER_REFERENCE));
            logger.debug(container.getName());

            for (ListBlobItem blobItem : container.listBlobs()) {
                // If the item is a blob, not a virtual directory
                if (blobItem instanceof CloudBlockBlob) {
                    // Download the text
                    CloudBlockBlob retrievedBlob = (CloudBlockBlob) blobItem;
                    logger.debug("stocazzo"+retrievedBlob.getProperties().toString());
                    logger.debug("staminchia"+retrievedBlob.downloadText());

                    retrievedBlob.getMetadata().forEach((k,v)->{
                        logger.debug(k.toString()+" - "+v.toString());
                    });
                    //System.out.println(retrievedBlob.downloadText());
                }
            }
            container.getMetadata().forEach((k,v)->{
                logger.debug(k.toString()+" - "+v.toString());
            });

                    container.listBlobs().forEach((k)->{
                        logger.debug("element: "+k.toString());
                        if (k instanceof CloudBlobDirectory) {
                            logger.debug("sticazzi "+((CloudBlobDirectory)k).getUri());
                        }
                });
        } catch (Exception e) {
            logger.error("Exception encountered: ",e);
            logger.error(e.getMessage());
            System.exit(-1);
        }
    }

    public void walkAndDownload(){
        ArrayList list = DatabaseClient.getInstance().getFileList();
        container.get;
    }



    private JsonObject getJsonFile(){
        //File downloadedFile = new File();
        return null;
    }
//CloudBlobContainer container = serviceClient.getContainerReference(Config.getConfig().getValue(Config.CONFIG_CONTAINER_REFERENCE));

}
