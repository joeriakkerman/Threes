package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class InfoBar extends JPanel {

	private JLabel lSteps, lDirection, lName;
	
	public InfoBar(int height) {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setSize(Canvas.WIDTH, height);
		setBackground(Color.BLACK);
		lSteps = new JLabel("0");
		lSteps.setAlignmentX(Component.CENTER_ALIGNMENT);
		lSteps.setFont(new Font("Arial", Font.BOLD, 40));
		lDirection = new JLabel(" ");
		lDirection.setForeground(Color.YELLOW);
		lDirection.setAlignmentX(Component.CENTER_ALIGNMENT);
		lDirection.setFont(new Font("Arial", Font.BOLD, 40));
		lName = new JLabel("Joeri Akkerman, 2140695");
		lName.setForeground(Color.GREEN);
		lName.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lSteps);
		add(lDirection);
		add(lName);
	}
	
	public void update(int steps, String direction) {
		lSteps.setText("" + steps);
		lDirection.setText(direction);
	}

}
