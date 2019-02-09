/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author Osito
 */
@WebListener
public class Config implements ServletContextListener {

    @PersistenceContext(unitName = "npkpir_22PU")
    private EntityManager em;
    
    @Override
    public void contextInitialized(ServletContextEvent event) {
        // ...
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        em.close();
    }

}
