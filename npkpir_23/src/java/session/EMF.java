/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Osito
 */
@WebListener
public class EMF implements ServletContextListener {
    private static EntityManagerFactory emf;
    final private static String pu = "npkpir_22PU";
    final static org.slf4j.Logger logger = LoggerFactory.getLogger(ServletContextListener.class);
    
    @Override
    public void contextInitialized(ServletContextEvent event) {
        logger.info("+++ ServletContextListener : contextInitialized - Inititalizing EMF for PU: " + pu);
        emf = Persistence.createEntityManagerFactory(pu);
        logger.info("+++ ServletContextListener : contextInitialized - Init EMF done for PU: " + pu);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        logger.info("+++ ServletContextListener : contextDestroyed - Closing EMF for PU: " + pu);
        emf.close();
        logger.info("+++ ServletContextListener : contextDestroyed - Closed EMF done for PU " + pu);
    }

    public static EntityManager createEntityManager() {
        if (emf == null) {
            throw new IllegalStateException("Context is not initialized yet.");
        }
        return emf.createEntityManager();
    }
}
