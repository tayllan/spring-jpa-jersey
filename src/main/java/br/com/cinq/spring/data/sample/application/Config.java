package br.com.cinq.spring.data.sample.application;

import javax.ws.rs.ApplicationPath;

import br.com.cinq.spring.data.sample.resource.CityResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Register Jersey modules
 * @author Adriano Kretschmer
 */
@Configuration
@ApplicationPath("rest")
public class Config extends ResourceConfig {

    public Config() {
        register(CityResource.class);
        packages("br.com.cinq.spring.data.sample.resource");
        property(ServletProperties.FILTER_FORWARD_ON_404, true);
    }

}