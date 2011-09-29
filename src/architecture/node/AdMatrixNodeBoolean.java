package architecture.node;


import java.util.Vector;

import architecture.edge.Edge;

/**
 * Node for adjacency matrix representation 
 * without weighted edges!!
 * @author stefanie
 *
 */

public class AdMatrixNodeBoolean extends Node {
	private boolean[] neighbors;

	public AdMatrixNodeBoolean(int i, String l) {
		super(i, l);
	}

	public AdMatrixNodeBoolean(int i, String l, double w) {
		super(i, l, w);
	}
	
	public AdMatrixNodeBoolean(int i, String l, boolean[] neighbors) {
		super(i, l);
		this.neighbors = neighbors;
	}
	
	public AdMatrixNodeBoolean(int i, String l, double w, boolean[] neighbors) {
		super(i, l, w);
		this.neighbors = neighbors;
	}

	@Override
	public void addLink(int neighbor, double weight) {
		this.addLink(neighbor);

	}

	@Override
	public Edge getLink(int neighbor) {
		if (this.neighbors[neighbor]){
			return new Edge(neighbor);
		}
		return null;
	}

	@Override
	public Edge[] getNeighbors() {
		int count = 0;
		for (int i = 0; i < this.neighbors.length; i++){
			if (this.neighbors[i]){
			 count++;
			}
		}
		Edge[] edges = new Edge[count];
		count = 0;
		for (int i = 0; i < this.neighbors.length; i++){
			if (this.neighbors[i]){
			 edges[count] = new Edge(i);
			 count++;
			}
		}
		return edges;
	}
	
	@Override
	public void setNeighbors(Vector<Edge> edges, int graphSize) {
		this.neighbors = new boolean[graphSize];
		Edge edge;
		for (int i = 0; i < edges.size(); i++){
			edge = edges.get(i);
			this.neighbors[edge.getNode()] = true;
		}
		this.setDegree(edges.size());
	}

	@Override
	public void addLink(int neighbor) {
		if (this.neighbors == null){
			throw new IllegalArgumentException("Neighborhood not set");
		}
		this.neighbors[neighbor] = true;
	}

	

	@Override
	public void addLink(int neighbor, int weight) {
		this.addLink(neighbor);
		
	}

	

	

}
