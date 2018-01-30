package com.ibm.zosconnect.sample.provider;

import com.ibm.zosconnect.spi.SarFile;
import com.ibm.zosconnect.spi.ServiceFactory;
import com.ibm.zosconnect.spi.ServiceFactoryException;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.*;

import java.util.HashMap;
import java.util.Properties;

@Component(name = "com.ibm.zosconnect.sample.cloudant", configurationPolicy = ConfigurationPolicy.IGNORE, property = {"service.provider=IBM"}, service = {ServiceFactory.class})
public class CloudantServiceFactory implements ServiceFactory {

    private BundleContext context;

    private HashMap<String, CloudantConnection> connections = new HashMap<String, CloudantConnection>();

    @Activate
    protected void activate(ComponentContext cc){
        context = cc.getBundleContext();
        System.out.println("Activated ServiceFactory");
    }

    public String getProvider() {
        return "cloudant-1.0";
    }

    public void registerService(SarFile sarFile, Properties properties) throws ServiceFactoryException {

    }

    public void updateService(SarFile sarFile, Properties properties) throws ServiceFactoryException {

    }

    public void deregisterService(SarFile sarFile) {

    }

    @Reference(name = "connection", service = CloudantConnection.class, cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
    public void addCloudantConnection(CloudantConnection cc){

    }

    public void removeCloudantConnection(CloudantConnection cc){

    }
}
