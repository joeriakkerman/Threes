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
		
		mouse = new Mouse(new Mouse.IMouse() {
			
			@Override
			public void onSwipe(int offset, boolean horizontal) {
				canvas.transform(offset, horizontal);
			}
		});

		keyboard = new Keyboard(new Keyboard.IKeyboard() {
			
			@Override
			public void up() {
				canvas.transform(-1, false);
			}
			
			@Override
			public void right() {
				canvas.transform(1, true);
			}
			
			@Override
			public void left() {
				canvas.transform(-1, true);
			}
			
			@Override
			public void down() {
				canvas.transform(1, false);
			}
		});
		
		canvas = new Canvas(mouse, keyboard, gameModel);
		gameModel.addObserver(canvas);
		
		new SoundController();
	}

}
