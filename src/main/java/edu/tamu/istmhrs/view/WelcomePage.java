package edu.tamu.istmhrs.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/*
 * Welcome Class
 * Description: Creating the Main Screen for Housing Recommendation System
 */

public class WelcomePage {
	public void createPage() {

		// Create a frame with 2 links to perform use cases 1 & 2
		JFrame frame = new Frame().create();
		Container container = frame.getContentPane();

		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		container.setLayout(gridbag);
		// Add a banner
		addBanner(gridbag, constraints, container);
		// Add a button to perform use case 1
		addButton(gridbag, constraints, container, "Housing", "Click here to view housing recommendations");
		// Add a button to perform use case 2
		addButton(gridbag, constraints, container, "Suggestion", "Click here to view improvement suggestions");

		frame.setVisible(true);
	}

	public void addBanner(GridBagLayout gridbag, GridBagConstraints constraints, Container container) {
		JLabel banner = new JLabel("Welcome to Housing Recommendation System");
		banner.setFont(new Font("Serif", Font.BOLD, 20));
		constraints.anchor = GridBagConstraints.PAGE_START;
		constraints.ipady = 80;
		gridbag.setConstraints(banner, constraints);
		container.add(banner);
	}

	public void addButton(GridBagLayout gridbag, GridBagConstraints constraints, Container container, String option,
			String title) {
		JButton button = new JButton();
		button.setText(title);
		button.setFont(new Font("Serif", Font.BOLD, 17));
		button.setForeground(Color.blue);
		gridbag.setConstraints(button, constraints);
		button.setBorderPainted(false);
		button.setOpaque(false);
		button.setBackground(Color.WHITE);
		constraints.ipady = 20;
		if (option == "Housing")
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					displaySearch();
				}
			});
		else if (option == "Suggestion")
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					displaySuggest();
				}
			});
		container.add(button);
	}

	public void displaySearch() {

		SearchPage page = new SearchPage();
		page.createSearchPage();
	}

	public void displaySuggest() {
		LoginPage page = new LoginPage();
		page.create();
	}
}