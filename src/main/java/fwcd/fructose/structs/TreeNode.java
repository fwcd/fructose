package fwcd.fructose.structs;

import java.util.List;

/**
 * Represents a tree (node) carrying an item.
 * 
 * @author Fredrik
 *
 */
public interface TreeNode {
	List<? extends TreeNode> getChildren();
	
	default String getLabel() {
		return toString();
	}
	
	default boolean isLeaf() {
		return getChildren().isEmpty();
	}
}
