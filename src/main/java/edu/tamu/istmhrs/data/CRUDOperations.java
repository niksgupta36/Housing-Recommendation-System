package edu.tamu.istmhrs.data;

import java.util.List;

public interface CRUDOperations {
	void execute(String query, Object... params);
	Result get(String query, Object... params);
	List<Result> getAll(String query, Object... params);
}
