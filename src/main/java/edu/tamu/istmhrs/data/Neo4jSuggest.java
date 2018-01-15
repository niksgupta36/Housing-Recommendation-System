package edu.tamu.istmhrs.data;

import java.util.HashMap;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;

public class Neo4jSuggest {

	//Get the restaurant suggestions from the database
	public HashMap<String, Integer> getRestaurantSuggestions(String zip) {
		Neo4jRepository datasource = null;
		HashMap<String, Integer> resultHash = null;
		try {
			datasource = new Neo4jRepository();
			datasource.connect();
			String cql = null;
			cql = "match(n:ZIPCode)-[:HAS_EATERY]->(e:eatery) where n.code =" + zip
					+ " return e.type as type, count(e.type) as count";
			StatementResult res = datasource.run(cql);
			resultHash = createHash(res);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			datasource.disconnect();
		}
		return resultHash;
	}

	//Get the hospital suggestions from the database
	public HashMap<String, Integer> getHospitalSuggestions(String zip) {
		Neo4jRepository datasource = null;
		HashMap<String, Integer> resultHash = null;
		try {
			datasource = new Neo4jRepository();
			datasource.connect();
			String cql = null;
			cql = "match(n:ZIPCode)-[:HAS_HOSPITAL]->(h:hospital) where n.code =" + zip
					+ " return h.type as type, count(h.type) as count";
			StatementResult res = datasource.run(cql);
			resultHash = createHash(res);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			datasource.disconnect();
		}
		return resultHash;
	}
	
	//Create Hashmap to store the type and the number of the hospitals/eateries
	public HashMap<String, Integer> createHash(StatementResult res) {
		HashMap<String, Integer> resultHash = new HashMap<String, Integer>();
		while (res.hasNext()) {
			Record record = res.next();
			String type = record.get("type").asString();
			int count = record.get("count").asInt();
			resultHash.put(type, count);
		}
		return resultHash;
	}

}