/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.njp.model;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author Nick
 */
public class MyServletContextListener implements ServletContextListener{
    
    
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("App Started");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("App Stopped");
    }
    
}
