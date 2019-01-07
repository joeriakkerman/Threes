package model;

import java.util.Observable;

public class GameModel extends Observable {
	
	private int score, steps;
	private String direction = "";
	
	public void setScore(int score) {
		this.score = score;
		setChanged();
		notifyObservers();
	}
	
	public void addStep() {
		steps++;
		setChanged();
		notifyObservers();
	}
	
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	public int getSteps() {
		return steps;
	}
	
	public int getScore() {
		return score;
	}
	
	public String getDirection() {
		return direction;
	}

}
