package com.fredrikw.fructose.parsers;

import com.fredrikw.fructose.structs.ArrayStack;
import com.fredrikw.fructose.structs.Stack;
import com.fredrikw.fructose.structs.StringTreeNode;

/**
 * Creates a string tree from a sequence of nested
 * parentheses.
 * 
 * @author Fredrik
 *
 */
public class ParenthesesParser implements StringParser<StringTreeNode> {
	private char openingSymbol;
	private char closingSymbol;
	
	public ParenthesesParser() {
		this('(', ')');
	}
	
	public ParenthesesParser(char openingSymbol, char closingSymbol) {
		setSymbols(openingSymbol, closingSymbol);
	}
	
	public void setSymbols(char openingSymbol, char closingSymbol) {
		this.openingSymbol = openingSymbol;
		this.closingSymbol = closingSymbol;
	}
	
	@Override
	public StringTreeNode parse(String raw) {
		StringTreeNode tree = new StringTreeNode("");
		parse(raw, tree);
		return tree;
	}
	
	private void parse(String raw, StringTreeNode root) {
		Stack<StringTreeNode> nodes = new ArrayStack<>(root);
		StringBuilder builder = new StringBuilder();
		
		for (char c : raw.toCharArray()) {
			if (c == openingSymbol || c == closingSymbol) {
				if (builder.length() > 0) {
					nodes.peek().addChild(builder.toString());
					builder = new StringBuilder();
				}
				
				if (c == openingSymbol) {
					nodes.push(nodes.peek().addChild(""));
				} else if (c == closingSymbol) {
					nodes.pop();
				}
			} else {
				builder.append(c);
			}
		}
		
		if (builder.length() > 0) {
			nodes.peek().addChild(builder.toString());
			builder = new StringBuilder();
		}
	}
}
