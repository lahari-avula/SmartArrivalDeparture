package com.laha.smartarrival.process;

public interface EventProcessor<R,T> {

	public T execute(R request) throws InterruptedException;

}
