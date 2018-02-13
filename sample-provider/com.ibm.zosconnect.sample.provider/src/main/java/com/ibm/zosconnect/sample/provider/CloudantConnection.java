package com.ibm.zosconnect.sample.provider;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

@Component(configurationPid = "com.ibm.zosconnect.sample.provider.connection", service = CloudantConnection.class, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class CloudantConnection {

    private String id;
    private String url;
    private String userId;
    private String password;

    private CloudantClient client;

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

            try {
                client = ClientBuilder.url(new URL(url)).username(userId).password(password).build();
            } catch (MalformedURLException e) {
            }
        }
    }

    public String getId(){
        return id;
    }

    public CloudantClient getClient() {
        return client;
    }
}
