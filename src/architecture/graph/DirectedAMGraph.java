package architecture.graph;

import io.graph.GraphReader;

/**
 * a directed graph using an adjacency matrix representation
 * @author stefanie
 *
 */

public class DirectedAMGraph extends Graph{

	public DirectedAMGraph(GraphReader read) {
		super(read, true, true);
	}
	
	

}
