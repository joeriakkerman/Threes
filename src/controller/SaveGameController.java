package controller;

import javax.swing.JFrame;

import model.SaveGameModel;
import model.Tile;
import view.SaveGame;

public class SaveGameController {
	
	private SaveGameModel model;
	
	public SaveGameController(JFrame frame, Tile[] tiles) {
		model = new SaveGameModel(tiles);
		new SaveGame(frame, model);
	}

}
