package edu.tamu.istmhrs.data;

import java.io.*;
import java.util.Properties;

public class Utilities {
	
	//load CSV into database
	public void loadCSV() {
		Properties properties = new Properties();
		InputStream stream = Neo4jRepository.class.getClassLoader().getResourceAsStream("neo4j.properties");
		try {
			properties.load(stream);
		} catch (Exception e) {
			System.out.println("Error loading properties file");
		}
		String sourcePath = populateSource();
		String destPath = populateDestination() + new File(sourcePath).getName();
		createFileFolders(sourcePath, destPath);
	}
	
	//source- CSV file is located in the resources folder of the project. Populate the system path for it
	public String populateSource() {
		String filename = "ApartmentMain.csv";
		ClassLoader loader = getClass().getClassLoader();
		File source = new File(loader.getResource(filename).getFile());
		String sourcePath = source.getAbsolutePath();
		sourcePath = sourcePath.replace("%20", " ");
		return sourcePath;
	}
	
	// Destination- Neo4j Import folder path based on the operating system
	public String populateDestination() {
		String OS = findOS();
		String destPath = "";
		if (OS.contains("Windows")) {
			String userHome = System.getProperty("user.home");
			destPath = userHome + "\\Documents\\Neo4j\\default.graphdb\\import\\";
			destPath = destPath.replace("%20", " ");
		} else if (OS.contains("MAC OS")) {
			// Warning -  code for MAC. Untested
			String userHome = System.getProperty("user.home");
			destPath = userHome + "//Documents//Neo4j//import//";
			destPath = destPath.replace("%20", " ");
		}
		return destPath;
	}

	//Create Import folder if required and initiate copy from source to destination
	public void createFileFolders(String sourcePath, String destPath) {
		File dest = new File(destPath);
		File source = new File(sourcePath);
		try {
			if (!dest.getParentFile().exists()) {
				dest.getParentFile().mkdir();
				dest.createNewFile();
				copyFileUsingStream(source, dest);
			} else {
				if (dest.exists()) {
					System.out.println("File exists. Delete before re-creating");
					dest.delete();
				}
				dest.createNewFile();
				copyFileUsingStream(source, dest);
			}
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
	}

	public String findOS() {
		return System.getProperty("os.name");
	}

	//Perform file system copy
	public void copyFileUsingStream(File source, File dest) throws IOException {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(source);
			System.out.println("Source found");
			os = new FileOutputStream(dest);
			System.out.println("dest found");
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		} catch (Exception e) {
			System.out.println("Catch File exception" + e.getClass());
		} finally {
			is.close();
			os.close();
		}
	}
}