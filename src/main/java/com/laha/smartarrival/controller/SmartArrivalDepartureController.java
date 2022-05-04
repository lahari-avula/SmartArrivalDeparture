package com.laha.smartarrival.controller;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

import com.laha.smartarrival.model.SmartADResponse;
import com.laha.smartarrival.model.TelematicsLocationRequest;
import com.laha.smartarrival.process.EventProcessor;

@RestController
public class SmartArrivalDepartureController {
	
	private static final Logger logger = LogManager.getLogger(SmartArrivalDepartureController.class);

	@Autowired
	EventProcessor<TelematicsLocationRequest, SmartADResponse> eventProcessor;
	
	//java
	
	@PostMapping(path = "/publish/location/data", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public SmartADResponse accountDetails(@RequestBody TelematicsLocationRequest request) {
		
		logger.info("REQUEST:{}",request);
		Callable<SmartADResponse> task = new Callable<SmartADResponse>() {
			@Override
			public SmartADResponse call() throws Exception {
				return eventProcessor.execute(request);

			};
		};
		
		ExecutorService executorservice = Executors.newFixedThreadPool(10);
		
		Future<SmartADResponse> future = executorservice.submit(task);
		

			try {
				logger.info("RESPONSE:{}",future.get()); // it's blocking call
				return future.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		logger.info("RESPONSE:{}","null");
		//add failure response
		return null;
	}
	
	//spring
	
	@PostMapping(path = "/publish/location/data/v2", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public WebAsyncTask<SmartADResponse> checkArrivalDeparture(@RequestBody TelematicsLocationRequest request) {
		
		logger.info("REQUEST:{}",request);
		Callable<SmartADResponse> callable = new Callable<SmartADResponse>() {
			@Override
			public SmartADResponse call() throws Exception {
				return eventProcessor.execute(request);

			};
		};

		WebAsyncTask<SmartADResponse>  response =  new WebAsyncTask<>(10000L, callable);
		
		response.onTimeout(new Callable<SmartADResponse>() {
			@Override
			public SmartADResponse call() throws Exception {
				SmartADResponse response = new SmartADResponse(); 
				response.setStatus("Error: Call timedout!");
				return response;
			}
		});
		
		return response;
		
	}
}
