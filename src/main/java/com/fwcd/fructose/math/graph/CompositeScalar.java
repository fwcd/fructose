package com.fwcd.fructose.math.graph;

public abstract class CompositeScalar implements Scalar {
	protected abstract Scalar[] getParents();
	
	protected abstract Scalar partialDerivForParent(Scalar parent);
	
	@Override
	public Scalar partialDerivative(Scalar arg) {
		Scalar result = null;
		
		for (Scalar parent : getParents()) {
			Scalar deriv;
			
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
		for (Scalar parent : getParents()) {
			if (!parent.isConstant()) {
				return false;
			}
		}
		
		return true;
	}
}
