package com.ibm.zosconnect.sample.provider;

import com.ibm.json.java.JSONObject;
import com.ibm.zosconnect.spi.*;

import java.util.List;
import java.util.Map;

public class CloudantProvider implements ServiceController {

    private ServiceStatus status = new ServiceStatus();
    private CloudantConnection connection;

    public CloudantProvider(CloudantConnection connection) {
        status.setStatus(ServiceStatus.STARTED);
        this.connection = connection;
    }

    public Interceptor[] getInterceptors() {
        return new Interceptor[0];
    }

    public DataXform getDataXform() {
        return null;
    }

    public String getProviderName() {
        return "cloudant-1.0";
    }

    public Map<String, Object> getData() throws ServiceException {
        return null;
    }

    public JSONObject getRequestSchema() throws ServiceException {
        return null;
    }

    public JSONObject getResponseSchema() throws ServiceException {
        return null;
    }

    public JSONObject getStatistics() throws ServiceException {
        return null;
    }

    public ServiceStatus status() throws ServiceException {
        return status;
    }

    public void invoke(Map<Object, Object> map, HttpZosConnectRequest httpZosConnectRequest, RequestData requestData, ResponseData responseData) throws ServiceException {

    }

    public ServiceStatus stop() throws ServiceException {
        status.setStatus(ServiceStatus.STOPPED);
        return status;
    }

    public ServiceStatus start() throws ServiceException {
        status.setStatus(ServiceStatus.STARTED);
        return status;
    }

    public List<ServiceArchiveData> getServiceArchiveData() throws ServiceException {
        return null;
    }

    public void setConnection(CloudantConnection cc){
        this.connection = cc;
    }
}
