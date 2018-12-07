package fwcd.fructose.math.graph;

public abstract class CompositeScalar implements ScalarTerm {
	protected abstract ScalarTerm[] getParents();
	
	protected abstract ScalarTerm partialDerivForParent(ScalarTerm parent);
	
	@Override
	public ScalarTerm partialDerivative(ScalarTerm arg) {
		ScalarTerm result = null;
		
		for (ScalarTerm parent : getParents()) {
			ScalarTerm deriv;
			
			if (parent.equals(arg)) {
				deriv = partialDerivForParent(parent);
			} else {
				deriv = partialDerivForParent(parent).multiply(parent.partialDerivative(arg)); // Chain rule
			}
			
			if (result == null) {
				result = deriv;
			} else {
				result = result.add(deriv);
			}
		}
		
		return result;
	}

	@Override
	public boolean isConstant() {
		for (ScalarTerm parent : getParents()) {
			if (!parent.isConstant()) {
				return false;
			}
		}
		
		return true;
	}
}
