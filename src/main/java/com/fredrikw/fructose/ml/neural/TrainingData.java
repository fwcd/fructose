package com.fredrikw.fructose.ml.neural;

import java.util.Set;

public interface TrainingData<I> {
	Set<I> getInputs();
}
