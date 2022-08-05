package com.pineapple.commons.infra.config;

import com.pineapple.commons.infra.config.ConfigurationRetriever;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Profile("local")
@Component
public class LocalConfigurationRetriever implements ConfigurationRetriever {

    @Override
    public String getEnv(String name) {
        return properties().get(name);
    }

    public Map<String, String> properties() {
        return new HashMap<>() {{
            put("JWT_SECRET", "A1B2C3D4F5G6H7I8J9K10A1B2C3D4F5G6H7I8J9K10A1B2C3D4F5G6H7I8J9K10");
            put("JWT_ISSUER", "http://localhost:8081");
        }};
    }
}
