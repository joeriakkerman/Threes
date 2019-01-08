package model;

import java.awt.Color;

public class Tile {
	
	private int x, y;
	private int score;
	
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
		score = 0;
	}
	
	public Tile(int x, int y, int score) {
		this.x = x;
		this.y = y;
		this.score = score;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public Color getColor() {
		if(score == 0) return new Color(0x404040);
		else if(score == 1) return new Color(0xC8C8FF);
		else if(score == 2) return new Color(0xFFC8C8);
		else return new Color(0xEEEEEE);
	}
	
}
