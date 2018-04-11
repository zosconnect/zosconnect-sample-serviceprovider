package com.ibm.zosconnect.sample.provider;

import java.util.Map;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

@Component(configurationPid = "com.ibm.zosconnect.sample.provider.connection", service = SampleConnection.class, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class SampleConnection {

    private String id;
    private String url;
    private String userId;
    private String password;

    @Activate
    public void activate(ComponentContext cc, Map<String, Object> properties) {
        System.out.println("Activating connection: " + properties);
        updateConfig(properties);
    }

    @Modified
    public void modified(ComponentContext cc, Map<String, Object> properties) {
        updateConfig(properties);
    }

    @Deactivate
    public void deactivate(ComponentContext cc) {
    }

    private void updateConfig(Map<String, Object> properties) {
        if (properties != null) {
            id = (String) properties.get("id");
            url = (String) properties.get("url");
            userId = (String) properties.get("userId");
            password = (String) properties.get("password");
        }
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String userId() {
        return userId;
    }

    public String password() {
        return password;
    }
}
