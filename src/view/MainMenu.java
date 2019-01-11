package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import controller.GameController;
import controller.InputParser;

@SuppressWarnings("serial")
public class MainMenu extends JPanel {
	
	public MainMenu(String[] games) {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getHeight() / 4;
		int height = gd.getDisplayMode().getHeight() / 4;
		
		setPreferredSize(new Dimension(width, height));
		setSize(width, height);
		setLayout(new FlowLayout());

		JFrame frame = new JFrame();
		
		DefaultListModel<String> model = new DefaultListModel<String>();
		for(int i = 0; i < games.length; i++) model.addElement(games[i]);
		JList<String> list = new JList<String>(model);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		list.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        if (evt.getClickCount() == 2) {
		            int index = list.locationToIndex(evt.getPoint());
		            if(index < list.getModel().getSize() && index >= 0)
		            	new GameController(model.getElementAt(index));
		            else new GameController(null);
		            frame.dispose();
		        }
		    }
		});
		
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(width, height - 100));
		add(listScroller);
		
		JButton deleteGame = new JButton("Delete Game(s)");
		deleteGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<String> values = list.getSelectedValuesList();
				
				InputParser parser = new InputParser();
				for(int i = 0; i < values.size(); i++) {
					parser.removeFile(values.get(i));
					model.removeElement(values.get(i));
				}
			}
		});
		add(deleteGame);
		
		JButton newGame = new JButton("New Game");
		newGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new DimensionsPicker();
	            frame.dispose();
			}
		});
		add(newGame);
		
		frame.setTitle("Menu");
		frame.setLayout(new FlowLayout());
		frame.setSize(width, height);
		frame.add(this);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
