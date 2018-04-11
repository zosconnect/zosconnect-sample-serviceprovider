package com.ibm.zosconnect.sample.provider;

import com.ibm.json.java.JSONObject;
import com.ibm.zosconnect.spi.*;

import java.util.List;
import java.util.Map;

public class SampleProvider implements ServiceController {

    //Store the current status of this service.
    private ServiceStatus status = new ServiceStatus();
    //The connection to the Cloudant database that this service uses.
    private SampleConnection connection;
    //Database name
    private String configName;

    private JSONObject requestSchema;
    private JSONObject responseSchema;

    public SampleProvider(SampleConnection connection, JSONObject requestSchema, JSONObject responseSchema, String configName) {
        status.setStatus(ServiceStatus.STARTED);
        this.connection = connection;
        this.requestSchema = requestSchema;
        this.responseSchema = responseSchema;
        this.configName = configName;
    }

    public Interceptor[] getInterceptors() {
        //Interceptors are defined in server.xml so there is no need to specify any here.
        return new Interceptor[0];
    }

    public DataXform getDataXform() {
        //If a DataXform implementation is used then the instance for this service is returned here.
        return null;
    }

    public String getProviderName() {
        return "sample-1.0";
    }

    public Map<String, Object> getData() throws ServiceException {
        return null;
    }

    public JSONObject getRequestSchema() throws ServiceException {
        //Return the request schema for this service.
        return requestSchema;
    }

    public JSONObject getResponseSchema() throws ServiceException {
        //Return the response schema for this service.
        return responseSchema;
    }

    public JSONObject getStatistics() throws ServiceException {
        return null;
    }

    public ServiceStatus status() throws ServiceException {
        //Return the current status of the service.
        return status;
    }

    public void invoke(Map<Object, Object> map, HttpZosConnectRequest httpZosConnectRequest, RequestData requestData, ResponseData responseData) throws ServiceException {

    }

    public ServiceStatus stop() throws ServiceException {
        //Set the status to stopped and return the updated status.
        status.setStatus(ServiceStatus.STOPPED);
        return status;
    }

    public ServiceStatus start() throws ServiceException {
        //Set the status to started and return the updated status.
        status.setStatus(ServiceStatus.STARTED);
        return status;
    }

    public List<ServiceArchiveData> getServiceArchiveData() throws ServiceException {
        return null;
    }

    public void setConnection(SampleConnection cc){
        this.connection = cc;
    }
}
