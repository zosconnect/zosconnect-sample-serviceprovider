package com.ibm.zosconnect.sample.provider;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

import java.util.Map;

@Component(configurationPid = "com.ibm.zosconnect.sample.provider.connection", service = CloudantConnection.class, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class CloudantConnection {

    @Activate
    public void activate(ComponentContext cc, Map<String, Object> properties){
        System.out.println("Activating connection: " + properties);
    }
}
