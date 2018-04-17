package com.fwcd.fructose.structs;

import java.util.Set;

/**
 * Represents an undirected graph.
 * 
 * @author Fredrik
 *
 */
public interface Graph<T> {
	Set<T> getNodes();
	
	Set<GraphEdge<T>> getEdges();
}
