package com.laha.smartarrival.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.laha.smartarrival.model.SmartADResponse;
import com.laha.smartarrival.model.TelematicsLocationRequest;
import com.laha.smartarrival.process.EventProcessor;
import com.laha.smartarrival.process.Greeting;
import com.laha.smartarrival.process.SmartArrivalDepartureProcessor;

@RestController
public class GreetingController {
	
	@Autowired
	EventProcessor<TelematicsLocationRequest, SmartADResponse> eventProcessor;

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

	@PostMapping(path="/publish/location/data", consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public SmartADResponse accountDetails(@RequestBody TelematicsLocationRequest request) {
		//TODO: add callable to execute on a (thread/instance/callable)
	
		return eventProcessor.execute(request);
	}

}
