/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ms.utils;

import com.ms.app.MSApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import java.util.Random;

public class Utils {

    public static Integer getRandom() {
        Random rnd = new Random();
        return rnd.nextInt(Integer.parseInt(MSApplication.properties.getProperty("application.random.seed"))) + 1;
    }


    public static Properties getConfiguration() {
        InputStream input = null;
        Properties prop = new Properties();
        try {

            //To load the config file from non classpath filesystem
            //input = new FileInputStream("configuration.properties");
            input = Utils.class.getClassLoader().getResourceAsStream("configuration.properties");
            
            prop.load(input);

        } catch (Exception io) {
            io.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return prop;
    }
}
