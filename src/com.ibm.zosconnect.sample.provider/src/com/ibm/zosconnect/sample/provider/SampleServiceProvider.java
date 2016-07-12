package com.ibm.zosconnect.sample.provider;

import java.util.List;
import java.util.Map;

import com.ibm.json.java.JSONObject;
import com.ibm.zosconnect.spi.HttpZosConnectRequest;
import com.ibm.zosconnect.spi.RequestData;
import com.ibm.zosconnect.spi.ResponseData;
import com.ibm.zosconnect.spi.Service;
import com.ibm.zosconnect.spi.ServiceArchiveData;
import com.ibm.zosconnect.spi.ServiceException;
import com.ibm.zosconnect.spi.ServiceStatus;

public class SampleServiceProvider implements Service{

	@Override
	public String getProviderName() {
		return "SampleServiceProvider";
	}

	@Override
	public Map<String, Object> getData() throws ServiceException {
		//This service provider doesn't support generating a SAR file
		//at runtime to return null here.
		return null;
	}

	@Override
	public JSONObject getRequestSchema() throws ServiceException {
		return null;
	}

	@Override
	public JSONObject getResponseSchema() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject getStatistics() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceStatus status() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void invoke(Map<Object, Object> requestStateMap, HttpZosConnectRequest httpZosConnectRequest,
			RequestData requestData, ResponseData responseData) throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ServiceStatus stop() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceStatus start() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ServiceArchiveData> getServiceArchiveData() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
