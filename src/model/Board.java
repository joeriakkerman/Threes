package model;

import java.util.Observable;

import view.Canvas;

public class Board extends Observable {
	
	private Tile[] tiles;
	private int tileSize, columns, rows;
	
	public Board(int columns, int rows) {
		this.columns = columns;
		this.rows = rows;
		int tw = Canvas.HEIGHT / columns;
		int th = Canvas.HEIGHT / rows + 2;
		tileSize = tw;
		if(th < tw) tileSize = th;
		Canvas.WIDTH = columns * tileSize;
		Canvas.HEIGHT = rows * tileSize;
		
		tiles = new Tile[columns * rows];
		
		for(int y = 0; y < rows; y++) {
			for(int x = 0; x < columns; x++) {
				tiles[y * columns + x] = new Tile(x, y);
			}
		}
	}
	
	public boolean transform(int offset, boolean horizontal) {
		boolean transformed = false;
		if(offset > 0) {
			if(horizontal) {//right
				for(int y = 0; y < rows; y++) {
					for(int x = columns - 2; x >= 0; x--) {
						boolean b = transformTile(tiles[y * columns + x], tiles[y * columns + x + offset]);
						if(!transformed && b) transformed = b;
					}
				}
			}else {//down
				for(int x = 0; x < columns; x++) {
					for(int y = rows - 2; y >= 0; y--) {
						boolean b = transformTile(tiles[y * columns + x], tiles[(y+offset) * columns + x]);
						if(!transformed && b) transformed = b;
					}
				}
			}
		}else if(offset < 0) {
			if(horizontal) {//left
				for(int y = 0; y < rows; y++) {
					for(int x = 1; x < columns; x++) {
						boolean b = transformTile(tiles[y * columns + x], tiles[y * columns + x + offset]);
						if(!transformed && b) transformed = b;
					}
				}
			}else {//up
				for(int x = 0; x < columns; x++) {
					for(int y = 1; y < rows; y++) {
						boolean b = transformTile(tiles[y * columns + x], tiles[(y+offset) * columns + x]);
						if(!transformed && b) transformed = b;
					}
				}
			}
		}
		
		if(transformed) {
			setChanged();
			notifyObservers();
		}
		return transformed;
	}
	
	public int getTotalScore() {
		int score = 0;
		for(Tile tile : tiles) score += tile.getScore();
		return score;
	}
	
	private boolean transformTile(Tile tile, Tile destination) {
		if(tile.getScore() == 0) return false;
		if(destination.getScore() > 0) {
			if((destination.getScore() == tile.getScore() && destination.getScore() + tile.getScore() >= 3) || destination.getScore() + tile.getScore() == 3) {
				destination.setScore(destination.getScore() + tile.getScore());
				tile.setScore(0);
			}else return false;
		}else {
			destination.setScore(tile.getScore());
			tile.setScore(0);
		}
		return true;
	}
	
	public void setScore(int id, int score) {
		tiles[id].setScore(score);
	}
	
	public Tile[] getTiles() {
		return tiles;
	}
	
	public int getColumns() {
		return columns;
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getTileSize() {
		return tileSize;
	}
	
	public int getTileMargin() {
		return tileSize / 50;
	}
	
}
