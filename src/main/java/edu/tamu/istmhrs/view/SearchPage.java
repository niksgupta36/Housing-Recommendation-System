package edu.tamu.istmhrs.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import edu.tamu.istmhrs.data.Neo4jRecommend;
import edu.tamu.istmhrs.data.RecommendResult;

/*
 * Class SearchPage Desc: Creates the UI where user gives the input
 */

class SearchPage {
	JFrame frame;
	JLabel banner;
	JLabel zip;
	JTextField zipTextField;
	JButton searchBtn;
	JButton advSearchBtn;
	JCheckBox checkbox1;
	JCheckBox checkbox2;
	JCheckBox checkbox3;
	Container contPane;

	public SearchPage() {
		frame = new Frame().create();

		// Declare the components
		banner = new JLabel("Welcome to Housing Recommendation System");
		zip = new JLabel("Enter Zip Code");
		zipTextField = new JTextField();
		searchBtn = new JButton("Search");
		advSearchBtn = new JButton("Advance Search");
		checkbox1 = new JCheckBox("Within 5 miles from grocery?");
		checkbox2 = new JCheckBox("Proximity to Bus stop");
		checkbox3 = new JCheckBox("Price less than $1000?");
	}

	public void createSearchPage() {
		contPane = frame.getContentPane();
		contPane.setLayout(null);
		banner.setBounds(220, 50, 400, 40);
		addZip();
		addCheckBox();
		addButtons();
		frame.setVisible(true);
	}

	public void addZip() {
		zip.setBounds(120, 100, 120, 40);
		zip.setFont(new Font("Serif", Font.BOLD, 17));
		contPane.add(zip);
		zipTextField.setBounds(270, 100, 200, 40);
		zipTextField.setFont(new Font("Serif", Font.BOLD, 17));
		contPane.add(zipTextField);
	}

	public void addCheckBox() {
		checkbox1.setBounds(180, 350, 300, 40);
		checkbox1.setFont(new Font("Serif", Font.BOLD, 17));
		checkbox2.setBounds(180, 300, 300, 40);
		checkbox2.setFont(new Font("Serif", Font.BOLD, 17));
		checkbox3.setBounds(180, 250, 300, 40);
		checkbox3.setFont(new Font("Serif", Font.BOLD, 17));
		
		contPane.add(checkbox1);
		contPane.add(checkbox2);
		contPane.add(checkbox3);

		checkbox1.setVisible(false);
		checkbox2.setVisible(false);
		checkbox3.setVisible(false);
	}

	public void addButtons() {
		searchBtn.setBounds(130, 180, 140, 50);
		searchBtn.setFont(new Font("Serif", Font.BOLD, 17));

		contPane.add(searchBtn);
		searchBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				validate("searchBtn");
			}
		});

		advSearchBtn.setBounds(310, 180, 150, 50);
		advSearchBtn.setFont(new Font("Serif", Font.BOLD, 17));
		contPane.add(advSearchBtn);
		advSearchBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				validate("advSearchBtn");
			}
		});

	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * Desc: Function called on click of search button
	 */

	public void validate(String source) {

		String zipField = null;
		int flag = 0;

		if (source.equals("searchBtn")) {
			zipField = zipTextField.getText();
			if (zipField.matches("[0123456789]+") == false || (zipField.length() != 5)) {
				JOptionPane.showMessageDialog(null, "Please enter the valid Zipcode");
			}

			else if (zipField.matches("[0123456789]+") == true && zipField.length() == 5) {
				contPane.setVisible(true);
				flag = 1;
				insertTable(zipField, flag);
			}
		}
		advSearchButton(source);
	}

	public void advSearchButton(String source) {
		if (source.equals("advSearchBtn")) {
			advSearchBtn.setEnabled(false);
			checkbox1.setVisible(true);
			checkbox2.setVisible(true);
			checkbox3.setVisible(true);
		}
	}

	public void insertTable(String zipField, int flag) {
		boolean groceryCheck = checkbox1.isSelected();
		boolean busStopCheck = checkbox2.isSelected();
		boolean priceCheck = checkbox3.isSelected();
		if (flag == 1) {
			JFrame frame = new Frame().create();
			Container containerObj = frame.getContentPane();
			containerObj.add(new JLabel("Top recommendations are"));
			JTable jt = new JTable();
			DefaultTableModel table = new DefaultTableModel();
			table.addColumn("Apartment Name");
			table.addColumn("Address");
			jt.setModel(table);
			containerObj.add(new JScrollPane(jt));

			ArrayList<RecommendResult> result = new Neo4jRecommend().recommendApartment(Integer.parseInt(zipField),
					groceryCheck, busStopCheck, priceCheck);
			for (RecommendResult r : result) {
				table.addRow(new Object[] { r.getApartmentName(), r.getAddress() });
			}
			frame.setVisible(true);
		}

	}
}