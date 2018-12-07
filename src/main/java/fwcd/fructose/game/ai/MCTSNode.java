package fwcd.fructose.game.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import fwcd.fructose.game.GameMove;
import fwcd.fructose.game.GameRole;
import fwcd.fructose.game.GameState;
import fwcd.fructose.game.MoveChooser;
import fwcd.fructose.game.RandomMoveChooser;
import fwcd.fructose.structs.TreeNode;

/**
 * A node in the game tree which is capable of performing
 * monte-carlo searches.
 * 
 * @author Fredrik
 *
 */
public class MCTSNode<M extends GameMove, R extends GameRole> implements TreeNode, Comparable<MCTSNode<M, R>> {
	private static final Random RANDOM = new Random();
	private static final float EPSILON = 1e-8F; // Small value to prevent NaN's
	
	private final MCTSNode<M, R> parent;
	private final GameRole ourPlayer;
	private final M move;
	private final GameState<M, R> stateAfterMove;
	private List<MCTSNode<M, R>> exploredChilds = null;
	
	private MoveChooser<M, R> moveChooser = new RandomMoveChooser<>();
	
	private int explorationWeight = 2;
	private int maxSimulationDepth = 36;
	
	private int wins = 0;
	private int simulations = 0;
	
	/**
	 * Creates a new root MCTS-node.
	 * 
	 * @param ourPlayer - The player this tree search is playing for
	 * @param state - The game state
	 */
	public MCTSNode(GameRole ourPlayer, GameState<M, R> state) {
		parent = null;
		move = null;
		stateAfterMove = state;
		this.ourPlayer = ourPlayer;
	}
	
	/**
	 * Creates a new non-root MCTS-node.
	 * 
	 * @param parent - The parent node
	 * @param state - The game state
	 */
	private MCTSNode(MCTSNode<M, R> parent, M move, GameState<M, R> state) {
		this.parent = parent;
		this.move = move;
		stateAfterMove = state;
		ourPlayer = parent.ourPlayer;
	}
	
	private void addWins(int wins) {
		simulations++;
		this.wins += wins;
	}
	
	/**
	 * Fetches an upper confidence bound value for this node,
	 * which balances exploration and exploitation of the tree.
	 * 
	 * @return An UCT value
	 */
	private float uct() {
		return getWinRate()
				+ (RANDOM.nextFloat() * EPSILON)
				+ (explorationWeight * (float) Math.sqrt(Math.log(parent.simulations + 1) / (float) simulations + EPSILON));
	}
	
	public float getWinRate() {
		return (float) wins / (float) simulations + EPSILON;
	}
	
	/**
	 * Changes the move chooser used when simulating
	 * playouts. Will affect performance heavily so it
	 * is up to you to optimize this one. ;)
	 * 
	 * @param chooser - The move chooser
	 */
	public void setMoveChooser(MoveChooser<M, R> chooser) {
		moveChooser = chooser;
	}
	
	public boolean isRoot() {
		return parent == null;
	}
	
	@Override
	public boolean isLeaf() {
		return exploredChilds == null || exploredChilds.isEmpty();
	}
	
	/**
	 * Performs one iteration of the monte-carlo-tree-search algorithm.
	 */
	public void performIteration() {
		expand();
		
		MCTSNode<M, R> leaf = select();
		leaf.expand();
		
		int result = leaf.simulate();
		
		if (result > 0) {
			leaf.backpropagate(1);
		} else if (result < 0) {
			leaf.backpropagate(0);
		}
	}
	
	private MCTSNode<M, R> select() {
		MCTSNode<M, R> child = Collections.max(exploredChilds);
		
		if (child.isLeaf()) {
			return child;
		} else {
			return child.select();
		}
	}
	
	private void backpropagate(int winsDelta) {
		addWins(winsDelta);
		
		if (!isRoot()) {
			parent.backpropagate(winsDelta);
		}
	}
	
	/**
	 * Simulates this game.
	 * 
	 * @return 1: Win for our player - 0: Not determined - -1: Win for opponent
	 */
	private int simulate() {
		GameState<M, R> simulation = stateAfterMove.copy();
		
		int i = 0;
		while (!simulation.isGameOver() && i < maxSimulationDepth) {
			simulation.perform(moveChooser.chooseMove(simulation));
			i++;
		}
		
		Set<? extends GameRole> winners = simulation.getWinners();
		
		if (winners.contains(ourPlayer)) {
			return 1;
		} else if (!winners.isEmpty()) {
			return -1;
		} else {
			return 0;
		}
	}
	
	private void expand() {
		if (exploredChilds == null) {
			exploredChilds = new ArrayList<>();
			
			for (M move : stateAfterMove.getLegalMoves()) {
				exploredChilds.add(new MCTSNode<>(this, move, stateAfterMove.spawnChild(move)));
			}
		}
	}
	
	/**
	 * Fetches the move that lead to this game state.
	 * 
	 * @return The game move associated with this node
	 */
	public M getMove() {
		return move;
	}
	
	@Override
	public int compareTo(MCTSNode<M, R> o) {
		return Float.compare(uct(), o.uct());
	}
	
	/**
	 * Fetches the "best" child node.
	 * 
	 * @return The most explored/simulated child node
	 */
	public MCTSNode<M, R> mostExploredChild() {
		return Collections.max(exploredChilds, (a, b) -> Integer.compare(a.simulations, b.simulations));
	}

	@Override
	public List<? extends TreeNode> getChildren() {
		return exploredChilds;
	}

	@Override
	public String getLabel() {
		return Integer.toString(wins) + "/" + Integer.toString(simulations);
	}
	
	@Override
	public String toString() {
		if (exploredChilds == null) {
			return getLabel();
		} else {
			return getLabel() + " -> " + exploredChilds.toString();
		}
	}
	
	public int totalNodeCount() {
		int nodes = 1;
		
		if (exploredChilds != null) {
			for (MCTSNode<M, R> child : exploredChilds) {
				nodes += child.totalNodeCount();
			}
		}
		
		return nodes;
	}
}
