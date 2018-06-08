package plagiarismdetector.impl;

import java.util.Collection;
import java.util.List;

public interface IGrid {

	public void addConnection(String n1, String n2, int strength);

	// creates connection from n1 to n2 with given strength

	public boolean areConnected(String n1, String n2);

	// returns whether nodes n1 and n2 are connected

	public int connectionStrength(String n1, String n2);

	// returns strength of the connection between n1 and n2
	// 0 = no edge

	public Collection<String> allConnections(String n);
	// returns collection of all neighbors of node n
	
	public List<String> getMinConnections(int n);
	// returns collection of minimum strength n
}