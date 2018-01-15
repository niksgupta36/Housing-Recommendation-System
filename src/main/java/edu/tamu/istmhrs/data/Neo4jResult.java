package edu.tamu.istmhrs.data;

import org.neo4j.driver.v1.Record;

public class Neo4jResult implements Result {

	private Record record;

	public Neo4jResult(Record record) {
		this.record = record;
	}

	@Override
	public String get(String key) {
		return String.valueOf(record.get(key));
	}

	@Override
	public String get(int index) {
		return String.valueOf(record.get(index));
	}

}