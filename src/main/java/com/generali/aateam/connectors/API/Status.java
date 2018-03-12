package com.generali.aateam.connectors.API;
import static spark.Spark.*;

import com.generali.aateam.connectors.config.Config;
import com.google.gson.Gson;

import java.util.HashMap;


public class Status {


    private static HashMap<String,String> status;

    public static void addStatus(){
        status=new HashMap<>();
        status.put("STATUS","OK");
        get("/_status", (req, res) -> new Gson().toJson(status));
    }

    public static void addCfg(){
        get("/_config", (req, res) -> new Gson().toJson(Config.getConfig()));
    }
}
