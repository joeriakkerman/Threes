package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.MainMenuController;
import model.SaveGameModel;

@SuppressWarnings("serial")
public class SaveGame extends JPanel {
	
	public SaveGame(JFrame parent, SaveGameModel model) {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getHeight() / 4;
		int height = gd.getDisplayMode().getHeight() / 8;
		
		setPreferredSize(new Dimension(width, height));
		setSize(width, height);
		setLayout(new FlowLayout());

		JFrame frame = new JFrame();
		
		JTextField fileName = new JTextField("Game " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
		JLabel label = new JLabel("Name:");
		JButton button = new JButton("Save");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(model.saveGame(fileName.getText().toString())) {
					new MainMenuController();
					frame.dispose();
					parent.dispose();
				}
			}
		});
		add(label);
		add(fileName);
		add(button);
		
		frame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				new MainMenuController();
				frame.dispose();
				parent.dispose();
			}
		});
		
		frame.setTitle("Save Game");
		frame.setLayout(new FlowLayout());
		frame.setSize(width, height);
		frame.add(this);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
