package edu.tamu.istmhrs.data;

import java.util.List;

import org.neo4j.driver.v1.*;

import java.util.ArrayList;

/*
* Class: Neo4jOperation
* Desc: Implementing all the Neo4j database operations
*/
public class Neo4jOperation implements CRUDOperations{

private Neo4jRepository repository;
	
	public Neo4jOperation(Neo4jRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public Result get(String query, Object... params) {
		Result result = null;
		Session session = null;
		try {
			session = repository.connect();  
			Value value = Values.parameters(params);
			Record record = session.run(query, value).single();
			result = new Neo4jResult(record);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		} finally {
			repository.disconnect(session);
		}
		return result;
	}

	@Override
	public List<Result> getAll(String query, Object... params) {
		List<Result> results = null;
		Session session = null;
		try {
			session = repository.connect();
			Value value = Values.parameters(params);
			List<Record> records = session.run(query, value).list();
			results = toResults(records);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		} finally {
			repository.disconnect(session);
		}
		return results;
	}

	@Override
	public void execute(String query, Object... params) {
		Session session = null;
		try {
			session = repository.connect();
			Value value = Values.parameters(params);
			session.run(query, value);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		} finally {
			repository.disconnect(session);
		}
	}
	
	private List<Result> toResults(List<Record> records) {
		List<Result> results = new ArrayList<Result>();
		for (Record record : records) {
			results.add(new Neo4jResult(record));
		}
		return results;
	}
	
}
