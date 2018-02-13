package com.fwcd.fructose.game.ai;

import com.fwcd.fructose.annotation.WIP;
import com.fwcd.fructose.exception.Rethrow;
import com.fwcd.fructose.game.GameMove;
import com.fwcd.fructose.game.GameRole;
import com.fwcd.fructose.game.GameState;
import com.fwcd.fructose.genetic.core.ManualPopulation;
import com.fwcd.fructose.genetic.operators.Decoder;
import com.fwcd.fructose.genetic.operators.Encoder;
import com.fwcd.fructose.genetic.operators.GaussianFloatMutator;
import com.fwcd.fructose.ml.neural.SimplePerceptron;
import com.fwcd.fructose.time.Timer;

/**
 * An experimental game AI that combines a seletive, genetic
 * algorithm with feed-forward perceptrons.
 * 
 * @author Fredrik
 *
 * @param <M> - The game move type
 * @param <R> - The game role type
 */
@WIP(usable = false)
public class GeneticNeuralGameAI<M extends GameMove, R extends GameRole> extends EvaluatingGameAI<M, R> {
	private final ManualPopulation population = new ManualPopulation();
	private final SimplePerceptron neuralNet;
	
	private final Encoder<float[], GameState<M, R>> neuralEncoder;
	private final Decoder<float[], Float> neuralDecoder;
	
	private boolean debugOutput = false;
	
	// TODO: This class needs a lot more testing and experimentation
	// TODO: Currently there are a lot of "fixed" hyperparameters here - these should be adjustable in the future
	
	/**
	 * Create a new GeneticNeuralAI.
	 * 
	 * <p><b>The first value in the networkLayerSizes NEEDS to
	 * match the float array size produced by the encoder and
	 * the last value in the networkLayerSizes NEEDS to match
	 * the float array size taken by the decoder!!</b></p>
	 * 
	 * @param networkLayerSizes - Contains the neural net sizes
	 * @param neuralEncoder - The encoder (converts a game-state to a neural net input)
	 * @param neuralDecoder - The decoder (converts the neural net output to a game-state rating)
	 */
	public GeneticNeuralGameAI(
			int[] networkLayerSizes,
			Encoder<float[], GameState<M, R>> neuralEncoder,
			Decoder<float[], Float> neuralDecoder
	) {
		this.neuralEncoder = neuralEncoder;
		this.neuralDecoder = neuralDecoder;
		
		neuralNet = new SimplePerceptron(networkLayerSizes);
		population.setMutationChance(1F);
		population.setMutator(new GaussianFloatMutator(-500, 500, 0.5F, 1));
		population.spawn(20, () -> new SimplePerceptron(networkLayerSizes).getWeights());
		sampleNetwork();
	}
	
	private void sampleNetwork() {
		neuralNet.setWeights(population.selectBestGenes());
	}

	@Override
	public void onGameStart(GameState<M, R> initialState, R role) {
		sampleNetwork();
	}
	
	@Override
	public void onGameEnd(GameState<M, R> finalState, R role) {
		int moveCount = finalState.getMoveCount();
		int fitness = finalState.getWinners().contains(role) ? (100 - moveCount) : (-100 + moveCount);
		population.setFitness(neuralNet.getWeights(), fitness);
		population.evolve();
		if (debugOutput) {
			System.out.println(population);
		}
	}

	@Override
	protected double rateMove(GameState<M, R> gameBeforeMove, M move, Timer timer) {
		try {
			GameState<M, R> gameAfterMove = gameBeforeMove.spawnChild(move);
			return neuralDecoder.decode(neuralNet.compute(neuralEncoder.encode(gameAfterMove)));
		} catch (Exception e) {
			throw new Rethrow("An error occurred while rating the move.", e);
		}
	}
	
	public void setDebugOutput(boolean enabled) {
		debugOutput = enabled;
	}
}
