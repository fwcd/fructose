package com.fwcd.fructose.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import com.fwcd.fructose.structs.TreeNode;

/**
 * A graphical tree plotter using Swing.
 */
public class TreePlotter implements Viewable, Rendereable {
	private JComponent view;
	private TreeNode treeRoot;
	private int depth = Integer.MAX_VALUE; // The plotting depth of the tree
	private Color branchColor = Color.GRAY;
	
	/**
	 * Constructs a new, empty TreePlotter.
	 */
	public TreePlotter() {
		view = new RenderPanel(this);
	}
	
	/**
	 * Changes and repaints the tree used by this plotter.
	 * 
	 * @param treeRoot - The new tree
	 */
	public void setTree(TreeNode treeRoot) {
		this.treeRoot = treeRoot;
		view.repaint();
	}
	
	public void setPlottingDepth(int depth) {
		this.depth = depth;
	}
	
	/**
	 * Fetches the center x-position of the window.
	 * 
	 * @return The center x-position
	 */
	private int centerX() {
		return view.getWidth() / 2;
	}
	
	/**
	 * Renders the tree.
	 * 
	 * @param g2d - The graphics context the tree will be rendered in
	 */
	@Override
	public void render(Graphics2D g2d, Dimension canvasSize) {
		if (treeRoot != null) {
			render(g2d, treeRoot, centerX(), 20, 0);
		}
	}
	
	/**
	 * Internal, recursive tree drawing method.
	 * 
	 * @param g2d - The graphics context
	 * @param node - The node to be rendered
	 * @param x - The start x-position
	 * @param y - The start y-position
	 * @param incrementalDepth - The current depth of the tree
	 */
	private void render(Graphics2D g2d, TreeNode node, int x, int y, int incrementalDepth) {
		String nodeDesc = node.getLabel();
		FontMetrics metrics = g2d.getFontMetrics();
		
		int topLeftX = x - (metrics.stringWidth(nodeDesc) / 2);
		int topLeftY = y - (metrics.getHeight() / 2);
		
		g2d.setColor(Color.BLACK);
		g2d.drawString(nodeDesc, topLeftX, topLeftY);
		
		if (incrementalDepth < depth && !node.isLeaf()) {
			int children = node.getChildren().size() - 1;
			int step = (int) ((1D / (incrementalDepth + 1)) * 50D);
			
			int i = 0;
			for (TreeNode child : node.getChildren()) {
				int childX = x - ((children * step) / 2) + (i * step);
				int childY = y + metrics.getHeight() * 3;
				
				g2d.setColor(branchColor );
				g2d.drawLine(x, y, childX, childY);
				
				render(g2d, child, childX, childY, incrementalDepth + 1);
				
				i++;
			}
		}
	}

	@Override
	public JComponent getView() {
		return view;
	}
}
