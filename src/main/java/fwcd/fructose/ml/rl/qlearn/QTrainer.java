package fwcd.fructose.ml.rl.qlearn;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import fwcd.fructose.exception.SerializationException;
import fwcd.fructose.ml.rl.qlearn.as.BoltzmannSelector;
import fwcd.fructose.ml.rl.qlearn.as.QActionSelector;
import fwcd.fructose.ml.rl.qlearn.core.CompositeLearner;
import fwcd.fructose.ml.rl.qlearn.core.QFunction;
import fwcd.fructose.ml.rl.qlearn.core.QLearner;
import fwcd.fructose.ml.rl.qlearn.core.QTable;
import fwcd.fructose.ml.rl.qlearn.env.QAction;
import fwcd.fructose.ml.rl.qlearn.env.QState;
import fwcd.fructose.ml.rl.qlearn.env.QStep;
import fwcd.fructose.ml.rl.qlearn.env.QStepResult;

/**
 * Automatically trains {@link QLearner}-agents using Q-learning.
 * 
 * @author Fredrik
 *
 * @param <S> - The state type
 * @param <A> - The action type
 */
public class QTrainer<S extends QState<S, A>, A extends QAction> {
	private QLearner<S, A> agent;
	private double learnFactor = 0.1D;
	private double discountFactor = 0.9D;

	public QTrainer() {
		agent = new CompositeLearner<S, A>(new QTable<>(), new BoltzmannSelector<>()); // TODO: Do not use QTables here (rather a neural net maybe?)
	}

	public QTrainer(File savedAgent) {
		loadAgent(savedAgent);
	}

	public QTrainer(QLearner<S, A> agent) {
		this.agent = agent;
	}

	public QTrainer(QFunction<S, A> qFunction, QActionSelector<A> actionSelector) {
		agent = new CompositeLearner<>(qFunction, actionSelector);
	}

	public void train(S state, int episodes) {
		train(state, episodes, Integer.MAX_VALUE);
	}

	public void train(S state, int episodes, int maxSteps) {
		for (int i=0; i<episodes; i++) {
			trainEpisode(state, maxSteps);
		}
	}

	private void trainEpisode(S initialState, int maxSteps) {
		S state = initialState;
		S nextState = state;
		int i = 0;

		while (!state.isFinalState() && i < maxSteps) {
			state = nextState;
			QFunction<S, A> qFunc = agent.getQFunction();
			QStep<S, A> step = agent.pickStep(state, i);
			QStepResult<S, A> stepRes = state.spawnChild(step);
			nextState = stepRes.getNextState();

			// Bellman equation
			double learnedQ;
			
			if (nextState.isFinalState()) {
				learnedQ = stepRes.getReward();
			} else {
				learnedQ = stepRes.getReward() + (discountFactor * agent.maxQ(stepRes.getNextState()));
			}
			
			double newQ = ((1D - learnFactor) * step.getQValue()) + (learnFactor * learnedQ);
			
			qFunc.teach(state, step.getAction(), newQ);
			i++;
		}
	}

	public QLearner<S, A> getAgent() {
		return agent;
	}

	public void saveAgent(File file) {
		try (OutputStream out = new FileOutputStream(file); ObjectOutputStream oos = new ObjectOutputStream(out)) {
			oos.writeObject(agent);
		} catch (IOException e) {
			throw new SerializationException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public void loadAgent(File file) {
		try (InputStream in = new FileInputStream(file); ObjectInputStream ois = new ObjectInputStream(in)) {
			agent = (QLearner<S, A>) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw new SerializationException(e);
		}
	}

	public double getDiscountFactor() {
		return discountFactor;
	}

	public void setDiscountFactor(double discountFactor) {
		this.discountFactor = discountFactor;
	}

	public double getLearnFactor() {
		return learnFactor;
	}

	public void setLearnFactor(double learnFactor) {
		this.learnFactor = learnFactor;
	}
}
