package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import input.Keyboard;
import input.Mouse;
import model.Board;
import model.GameModel;
import model.Tile;

@SuppressWarnings("serial")
public class Canvas extends JPanel implements Observer {
	
	private JFrame frame;
	private JLabel lSteps, lDirection, lName;
	
	private Board board;
	private GameModel model;
	
	public static int WIDTH, HEIGHT;
	
	public Canvas(Mouse mouse, Keyboard keyboard, GameModel model) {
		this.model = model;
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		WIDTH = gd.getDisplayMode().getHeight() / 2;
		HEIGHT = gd.getDisplayMode().getHeight() / 2;
		
		board = new Board(4, 4);
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				
			}
		});
		frame.setSize(WIDTH, HEIGHT + 200);
		frame.addMouseListener(mouse);
		frame.addKeyListener(keyboard);
		frame.setResizable(false);
		frame.setTitle("Current Score: 0");
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	@Override
	public void update(Observable obs, Object obj) {
		frame.setTitle("Current Score: " + model.getScore());
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
		}
	}

}
