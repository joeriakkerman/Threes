package controller;

import input.Keyboard;
import input.Mouse;
import model.GameModel;
import view.Canvas;

public class GameController {
	
	private GameModel gameModel;
	private Canvas canvas;
	
	private Mouse mouse;
	private Keyboard keyboard;
	
	public GameController() {
		gameModel = new GameModel();
		mouse = new Mouse();
		keyboard = new Keyboard();
		canvas = new Canvas(mouse, keyboard, gameModel);
		gameModel.addObserver(canvas);
	}

}
