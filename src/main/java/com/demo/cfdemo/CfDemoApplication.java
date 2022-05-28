package com.demo.cfdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class CfDemoApplication {

    public static Map<String, Tool> inventory = new HashMap<>();

    public static void main(String[] args) {
        initializeData();
        SpringApplication.run(CfDemoApplication.class, args);
    }


    protected static void initializeData() {
        Tool chainsaw_stihl = new Tool("CHNS","Chainsaw","Stihl",1.49,true,false,true);
        Tool ladder_werner = new Tool("LADW","Ladder","Werner",1.99,true,true,false);
        Tool jackhammer_dewalt = new Tool("JAKD","Jackhammer","DeWalt",2.99,true,false,false);
        Tool jackhammer_ridgid = new Tool("JAKR","Jackhammer","Ridgid",2.99,true,false,false);

        inventory.put(chainsaw_stihl.getToolCode(), chainsaw_stihl);
        inventory.put(ladder_werner.getToolCode(), ladder_werner);
        inventory.put(jackhammer_dewalt.getToolCode(), jackhammer_dewalt);
        inventory.put(jackhammer_ridgid.getToolCode(), jackhammer_ridgid);
    }
}
