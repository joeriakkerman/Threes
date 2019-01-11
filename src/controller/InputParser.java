package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.Board;
import model.Tile;

public class InputParser {
	
	public Board readInput(String fileName) {
		int columns = 0, rows = 0;
		try {
			List<String> lines = new ArrayList<String>();
			String dir = getClass().getClassLoader().getResource("").getFile();
			dir = new File(dir).getParentFile().getAbsolutePath() + File.separator + "res" + File.separator + fileName + ".txt";
			File file = new File(dir);
			if(!file.exists()) return null;
			
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			String s;
			while((s = br.readLine()) != null){
			     lines.add(s);
			}
			br.close();
			
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
		}
	}
	
	public boolean removeFile(String fileName) {
		String dir = getClass().getClassLoader().getResource("").getFile();
		dir = new File(dir).getParentFile().getAbsolutePath() + File.separator + "res" + File.separator + fileName + ".txt";
		File file = new File(dir);
		if(file.exists()) {
			file.delete();
			return true;
		}else return false;
	}
	
	public String[] getAllFilesFromResources(){
		String dir = getClass().getClassLoader().getResource("").getFile();
		dir = new File(dir).getParentFile().getAbsolutePath() + File.separator + "res";
		File file = new File(dir);
		String[] files =  file.list(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				if(name.endsWith(".txt")) return true;
				return false;
			}
		});
		
		for(int i = 0; i < files.length; i++)
			files[i] = files[i].substring(0, files[i].length() - 4);

		return files;
	}
	
	public boolean fileExists(String fileName) {
		String dir = getClass().getClassLoader().getResource("").getFile();
		dir = new File(dir).getParentFile().getAbsolutePath() + File.separator + "res" + File.separator + fileName + ".txt";
		File file = new File(dir);
		return file.exists();
	}
	
	public boolean save(String fileName, Tile[] tiles) {
		String txt = "";
		for(int i = 0; i < tiles.length; i++) {
			Tile tile = tiles[i];
			txt += tile.getY() + " " + tile.getX() + " " + tile.getScore();
			if(i < tiles.length - 1) txt += System.lineSeparator();
		}
		
		try {
			String dir = getClass().getClassLoader().getResource("").getFile();
			dir = new File(dir).getParentFile().getAbsolutePath() + File.separator + "res" + File.separator + fileName + ".txt";
			File file = new File(dir);
			file.createNewFile();
			FileWriter writer = new FileWriter(file, false);
			BufferedWriter bw = new BufferedWriter(writer); 
			bw.write(txt);
			bw.close();
			return true;
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Something went wrong when trying to save game", "Oops...", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Could not save game. Please try again!", "Oops...", JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}

}
