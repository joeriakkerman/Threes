package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import model.Tile;

@SuppressWarnings("serial")
public class TopMenu extends JPanel {
	
	private int score;
	private int tileSize;
	
	public TopMenu(int height) {
		setSize(Canvas.WIDTH, height);
		setPreferredSize(new Dimension(Canvas.WIDTH, height));
		setBackground(Color.BLACK);
		tileSize = height / 2;
	}
	
	public void setScore(int score) {
		this.score = score;
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Tile tile = new Tile(-1, -1, score);
		g.setColor(tile.getColor());
		g.fillRect(getWidth() / 2 - tileSize / 2, getHeight() / 2 - tileSize / 2, tileSize, tileSize);
	}

}
