package edu.tamu.istmhrs.view;

import javax.swing.JFrame;

//Create and set commonly used properties of Frame

public class Frame {

	public JFrame create() {
		JFrame frame = new JFrame("Housing Recommendation System");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 700, 500);	
		return frame;
	}
}
