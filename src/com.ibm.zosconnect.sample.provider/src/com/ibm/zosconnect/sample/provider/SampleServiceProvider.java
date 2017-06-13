/*
 Copyright IBM Corporation 2016, 2017
 
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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicInteger;

import org.osgi.service.component.ComponentContext;

import com.ibm.json.java.JSONObject;
import com.ibm.zosconnect.spi.DataXform;
import com.ibm.zosconnect.spi.HttpZosConnectRequest;
import com.ibm.zosconnect.spi.Interceptor;
import com.ibm.zosconnect.spi.RequestData;
import com.ibm.zosconnect.spi.ResponseData;
import com.ibm.zosconnect.spi.Service;
import com.ibm.zosconnect.spi.ServiceArchiveData;
import com.ibm.zosconnect.spi.ServiceController;
import com.ibm.zosconnect.spi.ServiceException;
import com.ibm.zosconnect.spi.ServiceStatus;

public class SampleServiceProvider implements Service {

	//Hold the status of the service
	private ServiceStatus status = new ServiceStatus();
	
	//Hold the values used to calculate the statistics
	private AtomicInteger requestCount = new AtomicInteger(0);
	private AtomicInteger requestData = new AtomicInteger(0);
	
	//Format the date time is returned in
	protected SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
	
	/**
	 * Called when the service is created, the configuration from server.xml is
	 * supplied in the properties Map.
	 * 
	 * @param cc The ComponentContext supplied by the OSGi registry
	 * @param properties The properties supplied for this service
	 */
	public void activate(ComponentContext cc, Map<String, Object> properties) {
		//Set the service to started
		status.setStatus(ServiceStatus.STARTED);
		//Update hte configuration based on server.xml
		if(properties.get("timezone") != null){
			sdf.setTimeZone(TimeZone.getTimeZone(properties.get("timezone").toString()));
		}
	}
	
	/**
	 * Called when the service configuration is modified.
	 * 
	 * @param cc The ComponentContext supplied by the OSGi registry
	 * @param properties The properties supplied for this service
	 */
	public void modified(ComponentContext cc, Map<String, Object> properties) {
		if(properties.get("timezone") != null){
			sdf.setTimeZone(TimeZone.getTimeZone(properties.get("timezone").toString()));
		} else {
			sdf.setTimeZone(TimeZone.getDefault());
		}
	}
	
	/**
	 * Called when the service is destroyed.
	 * 
	 * @param cc The ComponentContext supplied by the OSGi registry
	 */
	public void deactivate(ComponentContext cc){
		status.setStatus(ServiceStatus.STOPPED);
	}

	@Override
	public String getProviderName() {
		return "SampleServiceProvider";
	}

	@Override
	public Map<String, Object> getData() throws ServiceException {
		//return the configuration set for this service
		Map<String, Object> data = new HashMap<>();
		data.put("timezone", sdf.getTimeZone().getDisplayName());
		return data;
	}

	@Override
	public JSONObject getRequestSchema() throws ServiceException {
		String requestSchema = "{\"$schema\": \"http://json-schema.org/draft-04/schema#\",\"type\": \"object\",\"properties\": {\"input\": {\"type\": \"string\"}},\"required\": [\"input\"]}";
		JSONObject schema;
		try {
			schema = JSONObject.parse(requestSchema);
		} catch (IOException e){
			throw new ServiceException();
		}
		return schema;
	}

	@Override
	public JSONObject getResponseSchema() throws ServiceException {
		String responseSchema = "{\"$schema\":\"http://json-schema.org/draft-04/schema#\",\"type\":\"object\",\"properties\":{\"date\":{\"type\":\"string\"},\"output\":{\"type\":\"string\"}},\"required\":[\"date\",\"output\"]}";
		JSONObject schema;
		try {
			schema = JSONObject.parse(responseSchema);
		} catch (IOException e){
			throw new ServiceException();
		}
		return schema;
	}

	@Override
	public JSONObject getStatistics() throws ServiceException {
		//Calculate the average size of the data supplied by clients
		JSONObject stats = new JSONObject();
		int averageLength = 0;
		if(requestCount.get() > 0){
			averageLength = requestData.get() / requestCount.get();
		}
		stats.put("average_length", averageLength);
		return stats;
	}

	@Override
	public ServiceStatus status() throws ServiceException {
		return status;
	}

	@Override
	public void invoke(Map<Object, Object> requestStateMap, HttpZosConnectRequest httpZosConnectRequest,
			RequestData requestData, ResponseData responseData) throws ServiceException {
		JSONObject requestObj = requestData.getJSON();
		//Check that the service is started, otherwise return 503
		if(status.getStatus().equals(ServiceStatus.STOPPED)){
			throw new ServiceException("Service is stopped", 503);
		} else if(requestObj == null){
			//If the client didn't sent a JSON payload then return an error
			throw new ServiceException("No input payload", 400);
		} else if(requestObj.get("input") == null){
			//If the supplied JSON doesn't contain the required field then return an error
			throw new ServiceException("Invalid JSON", 400);
		} else {
			//Else create the response object
			JSONObject response = new JSONObject();
			String input = requestObj.get("input").toString();
			requestCount.incrementAndGet();
			this.requestData.addAndGet(input.length());
			Date now = new Date();
			response.put("output", input);
			response.put("date", sdf.format(now));
			try {
				responseData.setJSON(response);
			} catch (IOException e) {
				throw new ServiceException();
			}
		}

	}

	@Override
	public ServiceStatus stop() throws ServiceException {
		status.setStatus(ServiceStatus.STOPPED);
		return status;
	}

	@Override
	public ServiceStatus start() throws ServiceException {
		status.setStatus(ServiceStatus.STARTED);
		return status;
	}

	@Override
	public List<ServiceArchiveData> getServiceArchiveData() throws ServiceException {
		// This service provider doesn't support generating a SAR file
		// at runtime so return null here.
		return null;
	}
}
