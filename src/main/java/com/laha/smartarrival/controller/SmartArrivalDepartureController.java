package com.laha.smartarrival.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.laha.smartarrival.model.SmartADResponse;
import com.laha.smartarrival.model.TelematicsLocationRequest;
import com.laha.smartarrival.process.EventProcessor;

@RestController
public class SmartArrivalDepartureController {

	@Autowired
	EventProcessor<TelematicsLocationRequest, SmartADResponse> eventProcessor;

	@PostMapping(path = "/publish/location/data", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public SmartADResponse accountDetails(@RequestBody TelematicsLocationRequest request) {

		// TODO: add callable to execute on a (thread/instance/callable)
		return eventProcessor.execute(request);
	}

}
