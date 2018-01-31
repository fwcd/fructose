package com.fwcd.fructose.ml.data;

import java.io.Serializable;
import java.util.Set;

public interface DataSet<I> extends Serializable {
	Set<I> getInputs();
}
