/*
 *  Class Neo4JRecommend: contains function to recommend house to the user
 *  
 *  Function: recommendApartment
 *  Desc: Recommends house to the user as per the inputs given
*/
package edu.tamu.istmhrs.data;

import java.util.ArrayList;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;

public class Neo4jRecommend {

	int busDistance;
	ArrayList<RecommendResult> results;

	public Neo4jRecommend() {
		results = new ArrayList<RecommendResult>();
	}

	public void setbusDistance(int dist) {
		busDistance = dist;
	}

	int getbusDistance() {
		return busDistance;
	}

	// Logic for use case1. Populate the cql query based on the checkboxes selected
	// in the UI and fetch the recommendation list

	public ArrayList<RecommendResult> recommendApartment(int zip, boolean groceryCheck, boolean busStopCheck,
			boolean priceCheck) {
		Neo4jRepository datasource = null;
		try {
			datasource = new Neo4jRepository();
			datasource.connect();
			String cql = null;
			
			//Populate the cql query based on the choices in the UI
			if (groceryCheck == false && busStopCheck == false && priceCheck == false)
				cql = "MATCH (n:ZIPCode{code:" + zip
						+ "})-[:CONTAINS]->(a:Apartment) return a.name, a.address limit 25";
			else {
				cql = "MATCH (n:ZIPCode{code:" + zip + "})-[:CONTAINS]->(a:Apartment)-[df:DISTANCE_FROM]->(str:Store) ";
				if (groceryCheck == true)
					cql += "WHERE df.dist <= 5 ";
				if (busStopCheck == true) {
					if (cql.contains("WHERE"))
						cql += "AND a.dbs <= 2 ";
					else
						cql += "WHERE a.dbs <= 2 ";
				}
				if (priceCheck == true) {
					if (cql.contains("WHERE"))
						cql += "AND a.rent < 1000 ";
					else
						cql += "WHERE a.rent < 1000 ";
				}
				cql += "RETURN a.name, a.address limit 25";
			}
			StatementResult res = datasource.run(cql);
			results = populateResults(res);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			datasource.disconnect();
		}
		return results;
	}

	// Create the list of recommendations and return the result
	public ArrayList<RecommendResult> populateResults(StatementResult res) {
		RecommendResult reRes;
		while (res.hasNext()) {
			Record record = res.next();
			String aptName = record.get("a.name").asString();
			String aptAdd = record.get("a.address").asString();
			reRes = new RecommendResult(aptName, aptAdd);
			results.add(reRes);
		}
		return results;
	}
}