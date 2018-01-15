package edu.tamu.istmhrs.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Graph extends JPanel  {

	private int[] hospValues;
	private int[] hotelValues;
	private String[] hotelLabels;
	private String[] hospitalLabels;
	

	public Graph(int[] hotelvalues, int[] hospitalValues, String[] hotelLabels, String[] hospitalLabels) {
		this.hotelLabels = new String[hotelLabels.length];
		this.hotelLabels = hotelLabels;
		this.hospitalLabels = new String[hospitalLabels.length];
		this.hospitalLabels = hospitalLabels;
		this.hospValues = new int[hospitalValues.length];
		this.hospValues = hospitalValues;
		this.hotelValues = new int[hotelvalues.length];
		this.hotelValues = hotelvalues;
	}
    
	// Rotate the label on the bar graph
	public static void drawRotate(Graphics2D g2d, double x, double y, String text) 
	{    
	    g2d.translate((float)x,(float)y);
	    g2d.rotate(Math.toRadians(270));
	    g2d.drawString(text,0,0);
	    g2d.rotate(-Math.toRadians(270));
	    g2d.translate(-(float)x,-(float)y);
	} 
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Color[] colors = new Color[] { Color.red, Color.orange, Color.yellow, Color.green, Color.blue };
		Dimension newDim = getSize();
		int panelWidth = newDim.width;
		int panelHeight = newDim.width;
		int barWidth = (panelWidth) / (hotelValues.length + hospValues.length + 5);
		int top = processTitle(g,panelWidth);
		int bottom = processLabel(g);

		double barScale = getBarScale(top,bottom,panelHeight);
		Graphics2D g2d = (Graphics2D) g;
		//Draw the hotel graph
		drawGraph(g,g2d,hotelValues,hotelLabels,barWidth,top,0,barScale,colors,panelHeight);
		//Draw a separator bar 
		printSeparator(g,barWidth,top);
		//Draw the hospital graph
		int beginWidth = (hotelValues.length * barWidth) + (2 * barWidth);
		drawGraph(g,g2d,hospValues,hospitalLabels,barWidth,top,beginWidth,barScale,colors,panelHeight);
	}
	
	// Draw the graph at the give x and y location with the values and labels passed as parameters
	public void drawGraph(Graphics g, Graphics2D g2d, int [] values,String [] labels, int barWidth, int top, int displace, double barScale,Color[] colors, int panelHeight){
		for (int j = 0; j < values.length; j++) {
			int height = (int) (values[j] * barScale);
			int valueP = displace + (j * barWidth + 1);
			int valueQ = top + panelHeight - height;
			printBar(g,g2d,valueP,valueQ,barWidth,height,colors,j,top,labels,values);
		}
	}
	
	//populate the minimum value of the value-set to determine scale
	public int getMinBarValue(int[] hotelvalues, int[] hospitalValues){
		int minHotelValue = 0, minHospValue = 0;
		for (int i = 0; i < hotelValues.length; i++) {
			if (minHotelValue > hotelValues[i])
				minHotelValue = hotelValues[i];
		}
		for (int i = 0; i < hospValues.length; i++) {
			if (minHospValue > hospValues[i])
				minHospValue = hospValues[i];
		}
		return Math.min(minHotelValue,minHospValue);
	}
	
	//populate the maximum value of the value-set to determine scale
	public int getMaxBarValue(int[] hotelValues, int[] hospitalValues){
		int maxHotelValue = 0, maxHospValue = 0;
		for (int i = 0; i < hotelValues.length; i++) {
			if (maxHotelValue < hotelValues[i])
				maxHotelValue = hotelValues[i];
		}
		for (int i = 0; i < hospValues.length; i++) {
			if (maxHospValue < hospValues[i])
				maxHospValue = hospValues[i];
		}
		return Math.max(maxHospValue,maxHospValue);
	}
	
	// Calculate the scale of the graph. The length of the bar is a multiplied with the scale
	public double getBarScale(int top, int bottom, int panelHeight){
		int maxBarValue = getMaxBarValue(hotelValues, hospValues);
		int minBarValue = getMinBarValue(hotelValues, hospValues);
		double scale = (panelHeight - top - bottom) / ((maxBarValue - minBarValue)*2);
		return scale;
	}
	
	public int processTitle(Graphics g,int panelWidth){
		String title1 = "Hotels"+ " (" + hotelLabels.length + ")";
		String title2 = "Hospitals" + " (" + hospValues.length + ")";
		Font titleFont = new Font("Book Antiqua", Font.BOLD, 14);
		FontMetrics titleFontMetrics = g.getFontMetrics(titleFont);
		printTitle(g,title1,titleFont,panelWidth/4,titleFontMetrics.getAscent());
		printTitle(g,title2,titleFont,panelWidth/2,titleFontMetrics.getAscent());
		int titleHeight = titleFontMetrics.getHeight();
		return titleHeight;
	}
	
	// Draw the labels and set the properties
	public int processLabel(Graphics g){
		Font labelFont = new Font("Book Antiqua", Font.BOLD, 14);
		FontMetrics labelFontMetrics = g.getFontMetrics(labelFont);
		int labelHeight = labelFontMetrics.getHeight();
		g.setFont(labelFont);
		return labelHeight;
	}
	
	// Draw the title of the graph
	public void printTitle(Graphics g, String title, Font titleFont, int stringWidth, int stringHeight){
		g.setFont(titleFont);
		g.drawString(title, stringWidth + 10, stringHeight);
	}
	
	//Add each bar based for every value of the value-set. Set the color for it using the colors[] 
	public void printBar(Graphics g, Graphics2D g2d,int x, int y, int width, int height, Color[] colors, int arrNum, int top, String [] labels, int [] values)
	{
		g.setColor(colors[arrNum]);
		g.fillRect(x, y, width - 2, height);
		g.setColor(Color.black);
		g.drawRect(x, y, width - 2, height);
		drawRotate(g2d,x + (width/2+1), y - top, labels[arrNum]);
		g.drawString(Integer.toString(values[arrNum]), x + (width/2-5), y + 100);
		
	}
	
	// Draw the separator section between the hospital and the restaurant graphs
	public void printSeparator(Graphics g, int width, int titleHeight)
	{
		int beginWidth = hotelValues.length * width;
		for (int j = 0; j < 2; j++) {
			int valueP = beginWidth + (j * width + 1);
			int valueQ = titleHeight;
			int height = 5;
			g.setColor(getBackground());
			g.fillRect(valueP, valueQ + 50, width, height);
			g.drawRect(valueP, valueQ + 50, width, height);
		}
	}
	
	// add the graph to a frame and display the frame
	public void draw() {

		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("Housing Recommendation System");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 1000);

		Graph bc = new Graph(hotelValues, hospValues, hotelLabels, hospitalLabels);
		frame.add(bc);
		frame.setVisible(true);
	}
}