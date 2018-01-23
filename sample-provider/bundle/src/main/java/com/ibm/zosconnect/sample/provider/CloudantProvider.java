package com.ibm.zosconnect.sample.provider;

import com.ibm.json.java.JSONObject;
import com.ibm.zosconnect.spi.*;

import java.util.List;
import java.util.Map;

public class CloudantProvider implements ServiceController {

    private ServiceStatus status = new ServiceStatus();

    public CloudantProvider() {
        status.setStatus(ServiceStatus.STOPPED);

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
        return null;
    }

    public void invoke(Map<Object, Object> map, HttpZosConnectRequest httpZosConnectRequest, RequestData requestData, ResponseData responseData) throws ServiceException {

    }

    public ServiceStatus stop() throws ServiceException {
        return null;
    }

    public ServiceStatus start() throws ServiceException {
        return null;
    }

    public List<ServiceArchiveData> getServiceArchiveData() throws ServiceException {
        return null;
    }
}
