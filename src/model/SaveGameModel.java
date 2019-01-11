package model;

import javax.swing.JOptionPane;

import controller.InputParser;

public class SaveGameModel {
	
	private Tile[] tiles;
	
	public SaveGameModel(Tile[] tiles) {
		this.tiles = tiles;
	}
	
	public boolean saveGame(String fileName) {
		fileName = fileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
		InputParser parser = new InputParser();
		if(!parser.fileExists(fileName)) {
			return parser.save(fileName, tiles);
		} else {
			JOptionPane.showMessageDialog(null, "A file with this name already exists!", "File already exists", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

}
