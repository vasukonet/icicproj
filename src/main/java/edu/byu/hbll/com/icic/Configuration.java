package edu.byu.hbll.com.icic;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;

import edu.byu.hbll.config.YamlLoader;

/**
 * Configuration manager for this application.
 */
@Singleton
@Startup
public class Configuration {

    /**
     * The system property containing the list of config files to be loaded.
     */
    public static final String CONFIGS_PROPERTY = "edu.byu.hbll.com.icic.configs";

    /**
     * The regex pattern used to split the list of config files contained in the {@link #CONFIGS_PROPERTY} property.
     */
    public static final String CONFIGS_DELIMITER = "\\s+";

    private static final Logger logger = LoggerFactory.getLogger(Configuration.class);

    // TODO: Define additional class properties/members as needed here.

    /**
     * Loads configuration data read from the config file(s) listed in the {@link #CONFIGS_PROPERTY} property.
     * <p>
     * No checked exceptions are thrown by this method. Instead, all exceptions encountered will be logged at the ERROR
     * level. Be sure to check the application logs following each attempted deployment to verify that deployment was in
     * fact successful.
     */
    @PostConstruct
    private void postConstruct() {
        try {
            String configs = System.getProperty(CONFIGS_PROPERTY);
            if (configs == null) {
                loadConfigFrom();
            } else {
                Path[] configFiles = Arrays.stream(configs.split(CONFIGS_DELIMITER))
                        .map(Paths::get)
                        .toArray(size -> new Path[size]);
                loadConfigFrom(configFiles);
            }
        } catch (Exception e) {
            logger.error("com.icic failed to deploy", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads configuration data read from the given YAML config file(s).
     * 
     * @param configFiles the files from which to read configuration
     * @throws IOException if the given files are not readable or cannot be parsed as YAML
     */
    public void loadConfigFrom(Path... configFiles) throws IOException {
        JsonNode config = new YamlLoader().load(configFiles);
        
        if(config == null || config.size() == 0) {
            logger.warn("configuration is empty");
            throw new RuntimeException("configuration is empty");
        }

        // TODO: After creating a new project, add the application-specific instructions to this method needed to
        //       process your config data and make it available to the rest of your application. You should not need to
        //       modify the postConstruct() method, which simply calls this method using the config files listed in the
        //       system property identified by CONFIGS_PROPERTY.
    }

}
