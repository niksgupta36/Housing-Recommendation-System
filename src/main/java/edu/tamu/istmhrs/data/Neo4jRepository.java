package edu.tamu.istmhrs.data;

import java.io.*;
import java.util.*;
import org.neo4j.driver.v1.*;

public class Neo4jRepository implements Repository {
	
	private static final String PROPS_FILE = "neo4j.properties";
	private static final String URL = "url";
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";	
	private Driver driver;

	public Neo4jRepository() {
		try {
			this.driver = init();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public Session connect() {
		return driver.session();
	}
	
	public StatementResult run(String cql) {
		StatementResult res = driver.session().run(cql);
		return res;
	}

	public void disconnect() {
		if (driver != null) {
			driver.close();
		}
	}
	
	public void disconnect(Session session) {
		if (session != null) {
			if (session.isOpen()) {
				session.close();
			}
		}
	}
	
	private Driver init() throws IOException {
		
		Properties properties = new Properties();
		InputStream stream = Neo4jRepository.class.getClassLoader()
				.getResourceAsStream(PROPS_FILE);
		properties.load(stream);
		
		String url = properties.getProperty(URL);
		String username = properties.getProperty(USERNAME);
		String password = properties.getProperty(PASSWORD);
		
		AuthToken token = AuthTokens.basic(username, password);
		return GraphDatabase.driver(url, token);		
	}

}