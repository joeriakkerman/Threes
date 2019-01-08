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
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import input.Keyboard;
import input.Mouse;
import model.Board;
import model.GameModel;
import model.InputParser;
import model.Tile;

@SuppressWarnings("serial")
public class Canvas extends JPanel implements Observer {
	
	private JFrame frame;
	
	private Board board;
	private InfoBar infoBar;
	private GameModel model;
	
	public static int WIDTH, HEIGHT;
	
	public Canvas(Mouse mouse, Keyboard keyboard, GameModel model) {
		this.model = model;
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		WIDTH = gd.getDisplayMode().getHeight() / 2;
		HEIGHT = gd.getDisplayMode().getHeight() / 2;
		
		InputParser parser = new InputParser();
		board = parser.readInput();
		if(board == null) {
			board = new Board(4, 4);
			Random random = new Random();
			int amount = board.getColumns() * board.getRows();
			for(int i = 0; i < 7; i++) {
				board.setScore(random.nextInt(amount), random.nextInt(3) + 1);
			}
		}
		board.addObserver(this);
		this.model.setScore(board.getTotalScore());
		infoBar = new InfoBar(200);
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				parser.save(board.getTiles());
			}
		});
		frame.setLayout(new FlowLayout());
		frame.setSize(WIDTH, HEIGHT + infoBar.getHeight());
		frame.addMouseListener(mouse);
		frame.addKeyListener(keyboard);
		frame.setResizable(false);
		frame.setTitle("Current Score: 0");
		frame.add(this);
		frame.add(infoBar);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.getContentPane().setBackground(Color.BLACK);
	}

	@Override
	public void update(Observable obs, Object obj) {
		frame.setTitle("Current Score: " + model.getScore());
		infoBar.update(model.getSteps(), model.getDirection());
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
		if(board.transform(offset, horizontal)) {
			model.addStep();
			model.setScore(board.getTotalScore());
			if(horizontal) {
				if(offset > 0) infoBar.update(model.getSteps(), "That's RIGHT!");
				else if(offset < 0) infoBar.update(model.getSteps(), "That's LEFT!");
			}else {
				if(offset > 0) infoBar.update(model.getSteps(), "Hands DOWN!");
				else if(offset < 0) infoBar.update(model.getSteps(), "Hands UP!");
			}
		}
	}
	
	public int getTotalScore() {
		return board.getTotalScore();
	}

}
