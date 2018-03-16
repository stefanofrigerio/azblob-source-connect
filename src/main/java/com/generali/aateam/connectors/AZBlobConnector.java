package com.generali.aateam.connectors;

import com.generali.aateam.connectors.API.Status;
import com.generali.aateam.connectors.client.AZblobClient;
import com.google.gson.Gson;

import java.util.HashMap;

import static spark.Spark.*;

public class AZBlobConnector {

    public static void main(String... args){
        Status.addStatus();
        Status.addCfg();

        AZblobClient client = AZblobClient.getClient();


    }
}
