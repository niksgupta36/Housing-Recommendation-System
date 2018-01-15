package edu.tamu.istmhrs.data;

public class Neo4jInitiator {

	static Neo4jRepository datasource;

	public Neo4jInitiator() {
		datasource = null;
	}

	/*
	 * Function: main() Return: Void Desc: Function handles loading of data from CSV file in the resources folder to Neo4j
	 * the database
	 */
	public static void main(String[] args) {
		try {
						
			System.out.println("Loading csv");
			Utilities util = new Utilities();
			util.loadCSV();
			System.out.println("Loading dataset");
			datasource = new Neo4jRepository();
			CRUDOperations operation = new Neo4jOperation(datasource);
			deleteAll(operation);
			LoadCSVRelationship(operation);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (datasource != null)
				datasource.disconnect();
		}
	}

	/*
	 * Function: deleteAll() Return: Void Desc: delete nodes and relationships from
	 * the database
	 */
	public static void deleteAll(CRUDOperations operation) {
		operation = new Neo4jOperation(datasource);
		System.out.println("Deleting nodes");
		String cql = "MATCH (n) DETACH DELETE n";
		operation.execute(cql);
	}

	/*
	 * Function: LoadCSVRelationship() Return: Void Desc: Adding nodes and
	 * relationships into the database
	 */
	public static void LoadCSVRelationship(CRUDOperations operation) {

		System.out.println("Adding nodes and relationships");
		String cql = "LOAD CSV WITH HEADERS FROM \'file:///ApartmentMain.csv\' AS line "
				+ "CREATE (apartmentname:Apartment {name: UPPER(line.ApartmentName), address: UPPER(line.Address), size: TOINT(line.Size), rent: TOINT(line.Rent), dbs: ToFloat(line.DistanceBusStop), zipcode: line.ZipCode, brcount: TOINT(line.BathroomCount), rating: ToFloat(line.Reviews)}) "
				+ "MERGE (zipcode:ZIPCode {code: TOINT(line.ZipCode), crimerate: TOINT(line.CrimeRate), aqi: TOINT(line.AirQualityIndex), pd: TOINT(line.PopulationDensity)}) "
				+ "MERGE (agent:Agent {name: UPPER(line.AgentName), phone: line.AgentPhone, email: line.AgentEmail }) "
				+ "MERGE (bathroomcount:Bathroom {brcount: TOINT(line.BathroomCount) }) "
				+ "MERGE (bedroomcount:Bedroom {bdcount: TOINT(line.BedroomCount) }) "
				+ "MERGE (floor:Floors {floorcount: TOINT(line.Floor) }) "
				+ "MERGE (mall:Mall {name: UPPER(line.Mall) }) " + "MERGE (store:Store {name: UPPER(line.Store) }) "
				+ "MERGE (zipcode)-[:CONTAINS]->(apartmentname) " + "MERGE (agent)-[:ASSIGNED_TO]->(apartmentname) "
				+ "MERGE (apartmentname)-[:DISTANCE_FROM {dist: ToFloat(line.DistanceMall)}]->(mall) "
				+ "MERGE (apartmentname)-[:DISTANCE_FROM {dist: ToFloat(line.DistanceGroceryStore)}]->(store) "
				+ "MERGE (zipcode)-[:HAS_STORE]->(store) " + "MERGE (zipcode)-[:HAS_MALL]->(mall) "
				+ "MERGE (apartmentname)-[:INCLUDES]->(bathroomcount) "
				+ "MERGE (apartmentname)-[:INCLUDES]->(bedroomcount) " + "MERGE (apartmentname)-[:HAS]->(floor) "
				+ "MERGE (eatery:eatery {name: UPPER(line.RestaurantName), type: UPPER(line.Type) })"
				+ "MERGE (zipcode)-[:HAS_EATERY]->(eatery)"
				+ "MERGE (hospital:hospital {name: UPPER(line.Hospitals), type: UPPER(line.HospType) })"
				+ "MERGE (zipcode)-[:HAS_HOSPITAL]->(hospital)";
		operation.execute(cql);
	}
}
