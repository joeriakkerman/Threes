package model;

import view.Canvas;

public class Board {
	
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
