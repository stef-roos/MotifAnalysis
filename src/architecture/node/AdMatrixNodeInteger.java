package architecture.node;

import java.util.Vector;
import architecture.edge.Edge;
import architecture.edge.WeightedEdge;

/**
 * Node for adjacency matrix representation 
 * edge weights are integer
 * @author stefanie
 *
 */

public class AdMatrixNodeInteger extends Node {

	private int[] neighbors;

	public AdMatrixNodeInteger(int i, String l) {
		super(i, l);
	}

	public AdMatrixNodeInteger(int i, String l, double w) {
		super(i, l, w);
	}
	
	public AdMatrixNodeInteger(int i, String l, int[] neighbors) {
		super(i, l);
		this.neighbors = neighbors;
	}
	
	public AdMatrixNodeInteger(int i, String l, double w, int[] neighbors) {
		super(i, l, w);
		this.neighbors = neighbors;
	}

	@Override
	public void addLink(int neighbor, double weight) {
		this.addLink(neighbor, (int)weight);

	}

	@Override
	public Edge getLink(int neighbor) {
		if (this.neighbors[neighbor] > 0){
			return new WeightedEdge(neighbor,this.neighbors[neighbor]);
		}
		return null;
	}

	
	@Override
	public Edge[] getNeighbors() {
		int count = 0;
		for (int i = 0; i < this.neighbors.length; i++){
			if (this.neighbors[i] > 0){
			 count++;
			}
		}
		Edge[] edges = new Edge[count];
		count = 0;
		for (int i = 0; i < this.neighbors.length; i++){
			if (this.neighbors[i] > 0){
			 edges[count] = new WeightedEdge(i,this.neighbors[i]);
			 count++;
			}
		}
		return edges;
	}
	
	@Override
	public void setNeighbors(Vector<Edge> edges, int graphSize) {
		this.neighbors = new int[graphSize];
		Edge edge;
		for (int i = 0; i < edges.size(); i++){
			edge = edges.get(i);
			this.neighbors[edge.getNode()] = (int)edge.getWeight();
		}
		this.setDegree(edges.size());
	}

	@Override
	public void addLink(int neighbor) {
		
	}


	@Override
	public void addLink(int neighbor, int weight) {
		if (this.neighbors == null){
			throw new IllegalArgumentException("Neighborhood not set");
		}
		this.neighbors[neighbor] = weight;
		
	}

}
