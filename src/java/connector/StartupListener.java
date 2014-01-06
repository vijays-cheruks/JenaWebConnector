/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connector;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class StartupListener implements ServletContextListener
{
    @Override
    public void contextDestroyed(ServletContextEvent arg0)
    {
        // Cleanup code goes here
    }
 
    @Override
    public void contextInitialized(ServletContextEvent sce)
    {
        Logger logger = null;
        ServletContext servletContext = sce.getServletContext();
        String log4jFile = servletContext.getInitParameter("log4jFileName");
        PropertyConfigurator.configure(log4jFile);
        logger = LogManager.getLogger(StartupListener.class.getName());
        logger.debug("Loaded: " + log4jFile);
    }
}