package com.fredrikw.fructose.ml.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("serial")
public class LabelledData<I, O> implements DataSet<I> {
	private final Map<I, O> data;
	
	public LabelledData() {
		data = new HashMap<>();
	}
	
	public LabelledData(Map<I, O> data) {
		this.data = data;
	}
	
	public LabelledData(I singleInput, O singleOutput) {
		data = new HashMap<>();
		data.put(singleInput, singleOutput);
	}
	
	public void add(I input, O output) {
		data.put(input, output);
	}
	
	@Override
	public Set<I> getInputs() {
		return data.keySet();
	}
	
	public O getOutput(I input) {
		return data.get(input);
	}
}
