package com.fwcd.fructose.ml.rl.qlearn.core;

import java.util.Collections;

import com.fwcd.fructose.ml.function.LearningFunction;
import com.fwcd.fructose.ml.math.NNVector;
import com.fwcd.fructose.ml.rl.qlearn.env.QAction;
import com.fwcd.fructose.ml.rl.qlearn.env.QState;

/**
 * A Q-function implementation that uses a neural network (or
 * any other function approximator) to learn the state-action mapping.
 */
public class QNeuralFunction<S extends QState<S, A>, A extends QAction> implements QFunction<S, A> {
	private static final long serialVersionUID = 5082670792164126133L;
	private final LearningFunction<NNVector, NNVector> neuralNet;
	
	/**
	 * Creates a QNeuralFunction using the given function approximator.
	 * 
	 * <p><b>Make sure that the approximator always takes in exactly as many neurons
	 * as the QState will supply and that it has exactly one neuron in
	 * the output layer.</b></p>
	 * 
	 * @param neuralNet - The function approximator to be used
	 */
	public QNeuralFunction(LearningFunction<NNVector, NNVector> neuralNet) {
		this.neuralNet = neuralNet;
	}
	
	@Override
	public double predict(S state, A action) {
		return neuralNet.compute(state.toNeurons(action)).get(0);
	}

	@Override
	public void teach(S state, A action, double output) {
		neuralNet.teach(Collections.singletonMap(state.toNeurons(action), new NNVector((float) output)));
	}
}
