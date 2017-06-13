/*
 Copyright IBM Corporation 2017
 
 LICENSE: Apache License
          Version 2.0, January 2004
          http://www.apache.org/licenses/
          
 The following code is sample code created by IBM Corporation.
 This sample code is not part of any standard IBM product and
 is provided to you solely for the purpose of assisting you in
 the development of your applications.  The code is provided
 'as is', without warranty or condition of any kind.  IBM shall
 not be liable for any damages arising out of your use of the
 sample code, even if IBM has been advised of the possibility
 of such damages.
*/
package com.ibm.zosconnect.sample.provider;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;

import com.ibm.zosconnect.spi.SarFile;
import com.ibm.zosconnect.spi.ServiceController;
import com.ibm.zosconnect.spi.ServiceControllerConstants;
import com.ibm.zosconnect.spi.ServiceFactory;
import com.ibm.zosconnect.spi.ServiceFactoryException;

public class SampleServiceFactory implements ServiceFactory {
	
	private BundleContext context;
	private HashMap<String, SarSampleService> installedServices = new HashMap<>();
	private HashMap<String, ServiceRegistration<ServiceController>> installedServiceRefs = new HashMap<>();
	
	protected void activate(ComponentContext cc, Map<String, Object> properties) {
        this.context = cc.getBundleContext();
    }

	@Override
	public String getProvider() {
		//This matches the provider name defined by the build toolkit plugin
		return "DateTimeSample";
	}

	@Override
	public void registerService(SarFile sarFile, Properties properties) throws ServiceFactoryException {
		String timeZone = (String)sarFile.getProperties().get("timezone");
		SarSampleService service = new SarSampleService(timeZone);
		Dictionary<String, Object> serviceProps = new Hashtable<>();
		serviceProps.put(ServiceControllerConstants.SERVICE_NAME, sarFile.getName());
		serviceProps.put(ServiceControllerConstants.SERVICE_DESCRIPTION, sarFile.getDescription());
		installedServiceRefs.put(sarFile.getName(), context.registerService(ServiceController.class, service, serviceProps));
		installedServices.put(sarFile.getName(), service);
	}

	@Override
	public void updateService(SarFile sarFile, Properties properties) throws ServiceFactoryException {
		SarSampleService service = installedServices.get(sarFile.getName());
		if(service != null){
			service.setTimezone((String)sarFile.getProperties().get("timezone"));
		}
	}

	@Override
	public void deregisterService(SarFile sarFile) {
		ServiceRegistration<?> serviceRef = installedServiceRefs.get(sarFile.getName());
		if(serviceRef != null){
			serviceRef.unregister();
			installedServiceRefs.remove(sarFile.getName());
			installedServices.remove(sarFile.getName());
		}

	}

}
