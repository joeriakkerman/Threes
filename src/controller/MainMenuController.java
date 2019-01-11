package controller;

import view.MainMenu;

public class MainMenuController {
	
	public MainMenuController() {
		InputParser parser = new InputParser();
		new MainMenu(parser.getAllFilesFromResources());
	}

}
