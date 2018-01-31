package com.fwcd.fructose.structs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A basic tree (node) implementation using strings
 * as labels.
 * 
 * @author Fredrik
 *
 */
public class StringTreeNode implements TreeNode, Iterable<StringTreeNode> {
	private final List<StringTreeNode> childs = new ArrayList<>();
	private String label;
	
	public StringTreeNode(String label) {
		this.label = label;
	}
	
	public StringTreeNode addChild(String childLabel) {
		StringTreeNode child = new StringTreeNode(childLabel);
		childs.add(child);
		return child;
	}
	
	public StringTreeNode addChild(StringTreeNode node) {
		childs.add(node);
		return node;
	}
	
	public void removeChild(StringTreeNode node) {
		childs.remove(node);
	}
	
	@Override
	public List<? extends TreeNode> getChildren() {
		return childs;
	}
	
	@Override
	public String toString() {
		return label;
	}
	
	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public Iterator<StringTreeNode> iterator() {
		return childs.iterator();
	}
}
