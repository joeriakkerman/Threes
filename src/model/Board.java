package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

import view.Canvas;

public class Board extends Observable {
	
	private Tile[] tiles;
	private int tileSize, columns, rows;
	private int nextTile;
	
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
		initNextRandomTile();
	}
	
	public boolean transform(int offset, boolean horizontal, boolean test) {
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
		
		if(transformed && !test) {
			setChanged();
			notifyObservers();
		}
		return transformed;
	}
	
	public void addNextTile(int xo, boolean horizontal) {
		if(horizontal) {
			if(xo > 0) addRandomTileToColumn(0);
			else if(xo < 0) addRandomTileToColumn(columns-1);
		}else {
			if(xo > 0) addRandomTileToRow(0);
			else if(xo < 0) addRandomTileToRow(rows-1);
		}
	}
	
	public void initNextRandomTile() {
		nextTile = 1 + new Random().nextInt(3);
		setChanged();
		notifyObservers();
	}
	
	public int getScoreFromNextTile() {
		return nextTile;
	}
	
	private void addRandomTileToRow(int row) {
		List<Integer> ids = new ArrayList<Integer>();
		for(int i = 0; i < columns; i++) {
			if(tiles[row * columns + i].getScore() == 0) ids.add(row * columns + i);
		}
		
		if(ids.size() > 0) tiles[ids.get(new Random().nextInt(ids.size()))].setScore(nextTile);
	}
	
	private void addRandomTileToColumn(int column) {
		List<Integer> ids = new ArrayList<Integer>();
		for(int i = 0; i < rows; i++) {
			if(tiles[i * columns + column].getScore() == 0) ids.add(i * columns + column);
		}
		
		if(ids.size() > 0) tiles[ids.get(new Random().nextInt(ids.size()))].setScore(nextTile);
	}
	
	public boolean gameOver() {
		Tile[] tmp = new Tile[tiles.length];
		for(int i = 0; i < tiles.length; i++) tmp[i] = new Tile(tiles[i].getX(), tiles[i].getY(), tiles[i].getScore());
		if(!transform(1, true, true) && !transform(-1, true, true) && !transform(1, false, true) && !transform(-1, false, true)) return true;
		else {
			tiles = tmp;
			return false;
		}
	}
	
	public int getTotalScore() {
		int score = 0;
		for(Tile tile : tiles) score += tile.getScore();
		return score;
	}
	
	private boolean transformTile(Tile tile, Tile destination) {
		if(tile.getScore() == 0) return false;
		if(destination.getScore() > 0) {
			if((destination.getScore() == tile.getScore() && destination.getScore() + tile.getScore() >= 6) || destination.getScore() + tile.getScore() == 3) {
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
		return (tileSize / 50) > 0 ? (tileSize / 50) : 1;
	}
	
}
