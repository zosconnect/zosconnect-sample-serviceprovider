package com.ibm.zosconnect.sample.provider;

import com.ibm.json.java.JSONObject;
import com.ibm.zosconnect.spi.*;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.*;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Properties;

@Component(name = "com.ibm.zosconnect.sample.cloudant", configurationPolicy = ConfigurationPolicy.IGNORE, property = {"service.provider=IBM"}, service = {ServiceFactory.class})
public class CloudantServiceFactory implements ServiceFactory {

    private BundleContext context;

    //List of connections defined in server.xml
    private HashMap<String, CloudantConnection> connections = new HashMap<>();

    private HashMap<String, ServiceRegistration<ServiceController>> installedServices = new HashMap<>();

    @Activate
    protected void activate(ComponentContext cc){
        context = cc.getBundleContext();
        System.out.println("Activated ServiceFactory");
    }

    public String getProvider() {
        return "cloudant-1.0";
    }

    public void registerService(SarFile sarFile, Properties properties) throws ServiceFactoryException {
        //Get the configuration from the SAR that the service needs to know
        JSONObject requestSchema = sarFile.getRequestSchema();
        JSONObject responseSchema = sarFile.getResponseSchema();
        String connectionRef = sarFile.getProperty("ref").toString();

        //Create an instance of the service for this SAR file
        CloudantProvider cp = new CloudantProvider(connections.get(connectionRef), requestSchema, responseSchema);

        //Get the configuration required when registering the service with OSGi
        Dictionary<String, Object> config = new Hashtable<>();
        config.put(ServiceControllerConstants.SERVICE_NAME, sarFile.getName());
        config.put(ServiceControllerConstants.SERVICE_DESCRIPTION, sarFile.getDescription());

        //Install the service and store in our cache
        installedServices.put(sarFile.getName(), context.registerService(ServiceController.class, cp, config));
    }

    public void updateService(SarFile sarFile, Properties properties) throws ServiceFactoryException {

    }

    public void deregisterService(SarFile sarFile) {
        installedServices.get(sarFile.getName()).unregister();
        installedServices.remove(sarFile.getName());
    }

    @Reference(name = "connection", service = CloudantConnection.class, cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
    public void addCloudantConnection(CloudantConnection cc){
        //Store the connection in the collection of those in the configuration
        connections.put(cc.getId(), cc);
    }

    public void removeCloudantConnection(CloudantConnection cc){
        //Remove the connection
        connections.remove(cc.getId());
    }
}
