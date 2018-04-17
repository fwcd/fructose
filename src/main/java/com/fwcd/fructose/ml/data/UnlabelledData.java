package com.fwcd.fructose.ml.data;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class UnlabelledData<I> implements DataSet<I> {
	private final Set<I> inputs;
	
	@SafeVarargs
	public UnlabelledData(I... inputs) {
		this.inputs = Arrays.stream(inputs).collect(Collectors.toSet());
	}
	
	public UnlabelledData(Set<I> inputs) {
		this.inputs = inputs;
	}
	
	public UnlabelledData() {
		inputs = new HashSet<>();
	}
	
	public void add(I input) {
		inputs.add(input);
	}
	
	@Override
	public Set<I> getInputs() {
		return inputs;
	}
}
