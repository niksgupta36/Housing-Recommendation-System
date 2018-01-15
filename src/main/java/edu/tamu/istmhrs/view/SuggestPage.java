package edu.tamu.istmhrs.view;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import edu.tamu.istmhrs.data.Neo4jSuggest;

public class SuggestPage {
	JFrame frame;
	JLabel banner;
	JLabel zip;
	JTextField zipTextField;
	JButton searchBtn;
	Container contPane;

	public SuggestPage() {
		frame = new Frame().create();

		//Declare the components
		banner = new JLabel("Welcome to Housing Recommendation System");
		zip = new JLabel("Enter Zip Code");
		zipTextField = new JTextField();
		zipTextField.setFont(new Font("Serif", Font.BOLD, 17));
		searchBtn = new JButton("Search");
	}

	// Add the components to the frame
	public void create() {

		contPane = frame.getContentPane();
		contPane.setLayout(null);
		setZip();
		zipTextField.setBounds(270, 100, 200, 40);
		contPane.add(zipTextField);
		setSearchBttn();
		frame.setVisible(true);
	}

	public void setZip(){
		banner.setBounds(220, 50, 400, 40);
		zip.setBounds(110, 100, 150, 40);
		zip.setFont(new Font("Serif", Font.BOLD, 17));
		zip.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		contPane.add(zip);
	}

	public void setSearchBttn(){
		searchBtn.setBounds(220, 200, 140, 50);
		searchBtn.setFont(new Font("Serif", Font.BOLD, 17));
		contPane.add(searchBtn);
		searchBtn.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent evt) {
				validate("searchBtn");
		}});
	}	
	
	public void validate(String source) {

		String zipField = null;

		if (source.equals("searchBtn")) {
			zipField = zipTextField.getText();
			if (zipField.matches("[0123456789]+") == false || (zipField.length() != 5)) {
				JOptionPane.showMessageDialog(null, "Please enter the valid Zipcode");
			}

			else if (zipField.matches("[0123456789]+") == true && zipField.length() == 5) {
				contPane.setVisible(true);
				generateGraph();
			}
		}
	}

	public void generateGraph() {

		//Call suggestImprovements.java
		Neo4jSuggest suggest = new Neo4jSuggest();
		HashMap<String, Integer> mapHotel = new HashMap<String,Integer>();
		mapHotel = suggest.getRestaurantSuggestions(zipTextField.getText());
		HashMap<String, Integer> mapHospital = new HashMap<String,Integer>();
		mapHospital = suggest.getHospitalSuggestions(zipTextField.getText());

		int[] hotelCount = new int[mapHotel.size()];
		String[] hotelTypes = new String[mapHotel.size()];
		int[] hospitalCount = new int[mapHospital.size()];
		String[] hospitalTypes = new String[mapHospital.size()];
		
		//Converting to array
		hotelTypes = convertStrToArray(mapHotel);
		hotelCount = convertIntToArray(mapHotel);
		hospitalCount = convertIntToArray(mapHospital);
		hospitalTypes = convertStrToArray(mapHospital);
		Graph graph = new Graph(hotelCount, hospitalCount, hotelTypes, hospitalTypes);
		graph.draw();

	}
	
	public int[] convertIntToArray(HashMap<String,Integer> hash){
		int i=0;
		int[] count = new int[hash.size()];
		for(String key:hash.keySet())
		{
			count[i] = hash.get(key);
			i++;
		}
		return count;
	}
	
	public String[] convertStrToArray(HashMap<String,Integer> hash){
		int i=0;
		String[] types = new String[hash.size()];
		for(String key:hash.keySet())
		{
			types[i] = key;
			i++;
		}
		return types;
	}
}