package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.InputParser;
import controller.MainMenuController;
import controller.SaveGameController;
import input.Keyboard;
import input.Mouse;
import model.Board;
import model.GameModel;
import model.Tile;

@SuppressWarnings("serial")
public class Canvas extends JPanel implements Observer {
	
	private JFrame frame;
	
	private Board board;
	private InfoBar infoBar;
	private TopMenu topMenu;
	private GameModel model;
	
	public static int WIDTH, HEIGHT;
	
	public Canvas(Mouse mouse, Keyboard keyboard, GameModel model) {
		this.model = model;
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		WIDTH = gd.getDisplayMode().getHeight() / 2;
		HEIGHT = gd.getDisplayMode().getHeight() / 2;
		
		if(model.getFileName() != null) {
			InputParser parser = new InputParser();
			board = parser.readInput(model.getFileName());
			if(board == null) reset();
		}else reset();
		
		board.addObserver(this);
		this.model.setScore(board.getTotalScore());
		infoBar = new InfoBar(200);
		topMenu = new TopMenu(board.getTileSize());
		topMenu.setScore(board.getScoreFromNextTile());
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				if(model.getFileName() != null) {
					InputParser parser = new InputParser();
					parser.save(model.getFileName(), board.getTiles());
					frame.dispose();
					new MainMenuController();
				}else {
					int result = JOptionPane.showOptionDialog(null, "Do you want to save this current game?", "Save Game", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
					if(result == JOptionPane.YES_OPTION) {
						new SaveGameController(frame, board.getTiles());
					}
				}
			}
		});
		frame.setLayout(new FlowLayout());
		frame.setSize(WIDTH, HEIGHT + infoBar.getHeight() + topMenu.getHeight());
		frame.addMouseListener(mouse);
		frame.addKeyListener(keyboard);
		frame.setResizable(false);
		frame.add(topMenu);
		frame.add(this);
		frame.add(infoBar);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.getContentPane().setBackground(Color.BLACK);
		
		if(board.gameOver()) showGameOver();
	}
	
	@Override
	public void update(Observable obs, Object obj) {
		frame.setTitle("Current Score: " + model.getScore());
		infoBar.update(model.getSteps(), model.getDirection());
		topMenu.setScore(board.getScoreFromNextTile());
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Canvas.WIDTH, board.getRows() * board.getTileSize());
		
		Tile[] tiles = board.getTiles();
		int ts = board.getTileSize(), margin = board.getTileMargin();
		for(int i = 0; i < tiles.length; i++) {
			g.setColor(tiles[i].getColor());
			g.fillRect(tiles[i].getX() * ts + margin, tiles[i].getY() * ts + margin, ts - margin*2, ts - margin*2);
			if(tiles[i].getScore() > 0) {
				g.setColor(Color.BLACK);
				g.setFont(new Font("Arial", Font.BOLD, 40));
				drawCenteredString("" + tiles[i].getScore(), tiles[i].getX() * ts, tiles[i].getY() * ts, ts, ts, g);
			}
		}
	}
	
	private void drawCenteredString(String s, int x, int y, int w, int h, Graphics g) {
	    FontMetrics fm = g.getFontMetrics();
	    x += (w - fm.stringWidth(s)) / 2;
	    y += (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
	    g.drawString(s, x, y);
	}
	
	public void transform(int offset, boolean horizontal) {
		if(board.transform(offset, horizontal, false)) {
			if(horizontal) {
				if(offset > 0) model.setDirection("That's RIGHT!");
				else if(offset < 0) model.setDirection("That's LEFT!");
			}else {
				if(offset > 0) model.setDirection("Hands DOWN!");
				else if(offset < 0) model.setDirection("Hands UP!");
			}
			infoBar.update(model.getSteps(), model.getDirection());
		}
		
		board.addNextTile(offset, horizontal);
		board.initNextRandomTile();
		model.addStep();
		model.setScore(board.getTotalScore());
		if(board.gameOver()) showGameOver();
	}
	
	private void showGameOver() {
		int result = JOptionPane.showOptionDialog(frame, "You scored " + board.getTotalScore() + " points" + System.lineSeparator() + "Would you like to play again?", "Game Over",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		
		if(result == JOptionPane.YES_OPTION) {
			reset();
			board.addObserver(this);
			this.model.setScore(board.getTotalScore());
			update(null, null);
		} else {
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		}
	}
	
	private void reset() {
		board = new Board(4, 4);
		Random random = new Random();
		List<Tile> tiles = new ArrayList<Tile>();
		Tile[] bt = board.getTiles();
		for(int i = 0; i < bt.length; i++)
			tiles.add(bt[i]);
		for(int i = 0; i < 7; i++) {
			Tile t = tiles.get(random.nextInt(tiles.size()));
			board.setScore(t.getY() * board.getColumns() + t.getX(), random.nextInt(3) + 1);
			tiles.remove(t);
		}
		model.setSteps(0);
	}
	
}
