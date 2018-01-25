package com.ibm.zosconnect.sample.provider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

@Component(configurationPid = "com.ibm.zosconnect.sample.provider.connection", service = CloudantConnection.class, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class CloudantConnection {
}
