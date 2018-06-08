package plagiarismdetector.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class SparseGrid implements IGrid {

	private Map<String, Map<String, Integer>> connections;

	public SparseGrid() {
		connections = new HashMap<String, Map<String, Integer>>();
	}

	@Override
	public void addConnection(String n1, String n2, int strength) {
		if (!connections.containsKey(n1)) {
			connections.put(n1, new HashMap<String, Integer>());
		}
		connections.get(n1).put(n2, strength);
	}

	@Override
	public boolean areConnected(String n1, String n2) {
		// awesome use of short-circuiting boolean logic!
		// holy crap, that's super-cool!
		return connections.containsKey(n1)
				&& connections.get(n1).containsKey(n2);
	}

	@Override
	public int connectionStrength(String n1, String n2) {
		if (!connections.containsKey(n1)) {
			return 0;
		}
		if (!connections.get(n1).containsKey(n2)) {
			return 0;
		}
		return connections.get(n1).get(n2);
	}

	@Override
	public Collection<String> allConnections(String n) {
		if (!connections.containsKey(n)) {
			throw new IllegalArgumentException("No connections for " + n);
		}
		// I think this works...
		// But I think the keyset returned is backed by the original
		// map, so we may want to make a defensive copy of it
		return connections.get(n).keySet();
	}
	
	@Override
	public List<String> getMinConnections(int n) {
		List<String> res = new ArrayList<String>();
		
		for(String file1: connections.keySet()){
			for(String file2: connections.get(file1).keySet()){
				if(connections.get(file1).get(file2)>=n){
					res.add(file1 + " " + file2 + " " + connections.get(file1).get(file2));
				}
			}
		}
		return res;
	}
}