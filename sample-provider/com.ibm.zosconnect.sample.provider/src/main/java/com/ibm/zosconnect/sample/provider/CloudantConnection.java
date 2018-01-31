package com.ibm.zosconnect.sample.provider;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.*;

import java.util.Map;

@Component(configurationPid = "com.ibm.zosconnect.sample.provider.connection", service = CloudantConnection.class, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class CloudantConnection {

    private String id;
    private String host;
    private Integer port;

    @Activate
    public void activate(ComponentContext cc, Map<String, Object> properties) {
        System.out.println("Activating connection: " + properties);
    }

    @Modified
    public void modified(ComponentContext cc, Map<String, Object> properties) {

    }

    @Deactivate
    public void deactivate(ComponentContext cc) {

    }

    private void updateConfig(Map<String, Object> properties) {
        if (properties != null) {
            id = (String) properties.get("id");
            host = (String) properties.get("host");
            port = (Integer) properties.get("port");
        }
    }

    public String getId(){
        return id;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort(){
        return port;
    }
}
