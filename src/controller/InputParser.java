package controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import model.Board;
import model.Tile;

public class InputParser {
	
	public Board readInput() {
		int columns = 0, rows = 0;
		try {
			List<String> lines = Files.readAllLines(Paths.get(getClass().getClassLoader().getResource("input.txt").toURI()));
			List<Tile> tiles = new ArrayList<Tile>();
			for(String line : lines) {
				String[] arr = line.split(" ");
				if(arr.length != 3) {
					return null;
				}
				
				int y = Integer.parseInt(arr[0]);
				if(y < 0) return null;
				int x = Integer.parseInt(arr[1]);
				if(x < 0) return null;
				int score = Integer.parseInt(arr[2]);
				if(score < 0) return null;
				
				if(x > columns) columns = x;
				if(y > rows) rows = y;
				
				tiles.add(new Tile(x, y, score));
			}
			if(tiles.size() > 0) {
				columns++;
				rows++;
				if(tiles.size() != columns * rows) return null;
				Board board = new Board(columns, rows);
				for(Tile tile : tiles) {
					board.setScore(tile.getY() * columns + tile.getX(), tile.getScore());
				}
				return board;
			}else return null;
		} catch (IOException e) {
			System.err.println("Could not read file input.txt");
			e.printStackTrace();
			return null;
		} catch (NumberFormatException e) {
			System.err.println("Could not parse file input.txt");
			e.printStackTrace();
			return null;
		} catch (URISyntaxException e) {
			System.err.println("Could not read file input.txt");
			e.printStackTrace();
			return null;
		}
	}
	
	public void save(Tile[] tiles) {
		String txt = "";
		for(int i = 0; i < tiles.length; i++) {
			Tile tile = tiles[i];
			txt += tile.getY() + " " + tile.getX() + " " + tile.getScore();
			if(i < tiles.length - 1) txt += System.lineSeparator();
		}
		try {
			Files.write(Paths.get(getClass().getClassLoader().getResource("input.txt").toURI()), txt.getBytes());
		} catch (IOException e) {
			System.err.println("Could not write to file input.txt");
			e.printStackTrace();
		} catch (URISyntaxException e) {
			System.err.println("Could not write to file input.txt");
			e.printStackTrace();
		}
	}

}
