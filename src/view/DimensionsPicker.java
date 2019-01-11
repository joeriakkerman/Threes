package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.GameController;
import controller.MainMenuController;

public class DimensionsPicker {

	public DimensionsPicker() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getHeight() / 4;
		int height = gd.getDisplayMode().getHeight() / 4;

		JFrame frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				new MainMenuController();
				frame.dispose();
			}
		});
		
		JPanel columnsPanel = new JPanel();
		columnsPanel.setPreferredSize(new Dimension(width, height/3));
		columnsPanel.setSize(width, height/3);
		columnsPanel.setLayout(new FlowLayout());
		
		columnsPanel.add(new JLabel("Columns:"));
		JTextField columns = new JTextField(6);
		columnsPanel.add(columns);
		
		JPanel rowsPanel = new JPanel();
		rowsPanel.setPreferredSize(new Dimension(width, height/3));
		rowsPanel.setSize(width, height/3);
		rowsPanel.setLayout(new FlowLayout());
		
		rowsPanel.add(new JLabel("Rows:"));
		JTextField rows = new JTextField(6);
		rowsPanel.add(rows);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(width, height/3));
		buttonPanel.setSize(width, height/3);
		buttonPanel.setLayout(new FlowLayout());
		
		JButton button = new JButton("Start");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int c = Integer.parseInt(columns.getText().toString());
					int r = Integer.parseInt(rows.getText().toString());
					
					if(c > 12 || r > 12 || c < 4 || r < 4) {
						JOptionPane.showMessageDialog(null, "Columns and rows must contain a number between 4 and 12", "Invalid number(s)", JOptionPane.ERROR_MESSAGE);
						return;
					}
					new GameController(null, c, r);
					frame.dispose();
				}catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "The entered values are not numbers!", "NFE", JOptionPane.ERROR);
				}
			}
		});
		buttonPanel.add(button);

		frame.add(columnsPanel);
		frame.add(rowsPanel);
		frame.add(buttonPanel);
		
		frame.setTitle("Save Game");
		frame.setLayout(new FlowLayout());
		frame.setSize(width, height);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
