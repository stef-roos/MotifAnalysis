package architecture.node;

import java.util.Vector;
import architecture.edge.Edge;
import architecture.edge.WeightedEdgeDouble;

/**
 * Node for adjacency matrix representation 
 * edge weights are double
 * @author stefanie
 *
 */

public class AdMatrixNodeDouble extends Node {

	private double[] neighbors;

	public AdMatrixNodeDouble(int i, String l) {
		super(i, l);
	}

	public AdMatrixNodeDouble(int i, String l, double w) {
		super(i, l, w);
	}
	
	public AdMatrixNodeDouble(int i, String l, double[] neighbors) {
		super(i, l);
		this.neighbors = neighbors;
	}
	
	public AdMatrixNodeDouble(int i, String l, double w, double[] neighbors) {
		super(i, l, w);
		this.neighbors = neighbors;
	}

	@Override
	public void addLink(int neighbor, int weight) {
		this.addLink(neighbor, (double)weight);

	}

	@Override
	public Edge getLink(int neighbor) {
		if (this.neighbors[neighbor] > 0){
			return new WeightedEdgeDouble(neighbor,this.neighbors[neighbor]);
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
			 edges[count] = new WeightedEdgeDouble(i,this.neighbors[i]);
			 count++;
			}
		}
		return edges;
	}
	
	@Override
	public void setNeighbors(Vector<Edge> edges, int graphSize) {
		this.neighbors = new double[graphSize];
		Edge edge;
		for (int i = 0; i < edges.size(); i++){
			edge = edges.get(i);
			this.neighbors[edge.getNode()] = edge.getWeight();
		}
		this.setDegree(edges.size());
	}

	@Override
	public void addLink(int neighbor) {
		
	}

	public void setNeighbors(double[] neighbors) {
		this.neighbors = neighbors;
	}

	@Override
	public void addLink(int neighbor, double weight) {
		if (this.neighbors == null){
			throw new IllegalArgumentException("Neighborhood not set");
		}
		this.neighbors[neighbor] = weight;
		
	}

}
