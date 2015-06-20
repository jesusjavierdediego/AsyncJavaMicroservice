/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ms.utils;

import com.ms.app.MSApplication;
import java.util.Random;

/**
 *
 * @author root
 */
public class Utils {
    public static Integer getRandom() {
        Random rnd = new Random();
        return rnd.nextInt(Integer.parseInt(MSApplication.properties.getProperty("application.random.seed"))) + 1;
    }
}
