package internetshop.controller;

import internetshop.lib.Injector;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class InjectInitializer implements ServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(InjectInitializer.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            LOGGER.info("Dependency injection started");
            Injector.injectDependency();
        } catch (IllegalAccessException e) {
            LOGGER.error("Can't initialize all dependencies", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
