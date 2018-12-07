package fwcd.fructose.math.graph;

public class ScalarSum implements ScalarTerm {
	private final ScalarTerm[] summands;

	public ScalarSum(ScalarTerm summandA, ScalarTerm summandB) {
		summands = new ScalarTerm[] {summandA, summandB};
	}
	
	public ScalarTerm getSummandA() {
		return summands[0];
	}
	
	public ScalarTerm getSummandB() {
		return summands[1];
	}
	
	@Override
	public String toString() {
		return "(" + getSummandA().toString() + " + " + getSummandB().toString() + ")";
	}
	
	@Override
	public Double compute() {
		return getSummandA().compute() + getSummandB().compute();
	}

	@Override
	public ScalarTerm partialDerivative(ScalarTerm arg) {
		return getSummandA().partialDerivative(arg).add(getSummandB().partialDerivative(arg));
	}

	@Override
	public boolean isConstant() {
		return getSummandA().isConstant() && getSummandB().isConstant();
	}
}
