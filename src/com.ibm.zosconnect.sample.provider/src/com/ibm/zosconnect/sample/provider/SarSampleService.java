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

import java.util.TimeZone;

import com.ibm.json.java.JSON;
import com.ibm.json.java.JSONObject;
import com.ibm.zosconnect.spi.DataXform;
import com.ibm.zosconnect.spi.Interceptor;
import com.ibm.zosconnect.spi.ServiceController;

public class SarSampleService extends SampleServiceProvider implements ServiceController{

	private JSONObject requestSchema;
	private JSONObject responseSchema;

    /**
     * Construct a new service based on the contents of a SAR file.
     *
     * @param timezone The timezone the service should report the time in. If <code>null</code> then the default of the JVM will be used.
     * @param requestSchema The JSON schema for requests to this service as read from the SAR file.
     * @param responseSchema The JSON schema for the responses to this service as read from the SAR file.
     */
	public SarSampleService(String timezone, JSONObject requestSchema, JSONObject responseSchema){
		if(timezone != null){
			sdf.setTimeZone(TimeZone.getTimeZone(timezone));
		}
		this.requestSchema = requestSchema;
		this.responseSchema = responseSchema;
	}
	
	@Override
	public Interceptor[] getInterceptors() {
		// Can return null here as any Interceptors will be configured in server.xml.
		return null;
	}

	@Override
	public DataXform getDataXform() {
		// This service provider doesn't use a DataXform so return null.
		return null;
	}

	@Override
    public JSONObject getRequestSchema(){
	    return this.requestSchema;
    }

    public void setRequestSchema(JSONObject requestSchema){
        this.requestSchema = requestSchema;
    }

    @Override
    public JSONObject getResponseSchema(){
        return this.responseSchema;
    }

    public void setResponseSchema(JSONObject responseSchema){
        this.responseSchema = responseSchema;
    }

	public void setTimezone(String timezone) {
		if(timezone == null){
			sdf.setTimeZone(TimeZone.getDefault());
		} else {
			sdf.setTimeZone(TimeZone.getTimeZone(timezone));
		}
	}

}
