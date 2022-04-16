package com.laha.smartarrival.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.laha.smartarrival.commons.Utils;
import com.laha.smartarrival.model.Customer;
import com.laha.smartarrival.model.Location;
import com.laha.smartarrival.model.RemoteControl;
import com.laha.smartarrival.model.SmartADResponse;
import com.laha.smartarrival.model.TelematicsLocationRequest;
import com.laha.smartarrival.repository.MySqlConnectionApp;

@Component
public class SmartArrivalDepartureProcessor implements EventProcessor<TelematicsLocationRequest, SmartADResponse>  {

	@Autowired
	MySqlConnectionApp aap;
	
	@Override
	public SmartADResponse execute(TelematicsLocationRequest request) {
		
		SmartADResponse response = new SmartADResponse();
		Customer customer = null;
		
		if(request.getAccountId() != null)
		{
			 customer = aap.getCustomerInfo(request.getAccountId());
			
			if(customer == null || customer.getAcc_id() == null) {	
			response.setStatus("Error: No valid account found");
			return response;
			}
		}
		else 
		{
			response.setStatus("Error: AccountId cannot be null");
			return response;
		}
		
		Location location =  request.getLocation();
		if(location == null)
		{
			response.setStatus("Error:Location cannot be null");
			return response;
		}
		else
		{
			if(location.getLatitude() == null)
			{
				response.setStatus("Error:Location.latitude cannot be null");
				return response;
			}
			if(location.getLongitude() == null)
			{
				response.setStatus("Error:Location.longitude cannot be null");
				return response;
			}
			if(location.getLatitude()<-90 || location.getLatitude()>90) {
				response.setStatus("Error: Latitude values must range between -90 and 90");
			     return response;
			}
			if(location.getLongitude()<-180 || location.getLongitude()>180)
			{
				response.setStatus("Error: Longitude values must range between -180 and 180");
				return response;
			}
			
			
			
		}
		if(request.getMessageType() == null || !"keyoff".equalsIgnoreCase(request.getMessageType()) && !"keyon".equalsIgnoreCase(request.getMessageType()))
		{
			response.setStatus("Error: messageType cannot be null and Invalid");
			return response;
		}
		//process for doorlock unlock
		double finalDistance = Utils.distance(location.getLatitude(),customer.getLat(),location.getLongitude(),customer.getLongitude());
		
		
		RemoteControl remoteControl = new RemoteControl();
		
		if(finalDistance < 50 && "keyoff".equalsIgnoreCase(request.getMessageType())) {
			
			remoteControl.setDoorUnlock(true);
			response.setData(remoteControl);
			response.setStatus("Success");
		}
			else {
				remoteControl.setDoorUnlock(false);
				response.setData(remoteControl);
				response.setStatus("success");
			
		}
		//compile response and return it
		
		return response;
	}


}
