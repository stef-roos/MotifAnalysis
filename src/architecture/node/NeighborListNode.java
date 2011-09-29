package architecture.node;

import java.util.Arrays;
import java.util.Vector;

import util.ArrayExpand;
import architecture.edge.Edge;
import architecture.edge.WeightedEdge;
import architecture.edge.WeightedEdgeDouble;

/**
 * node for adjacency list representation
 * @author stefanie
 *
 */
public class NeighborListNode extends Node {
	private Edge[] neighbors;

	public NeighborListNode(int i, String l, double w) {
		super(i, l, w);
	}
	
	public NeighborListNode(int i, String l) {
		super(i, l);
	}
	
	public NeighborListNode(int i, String l, double w, Edge[] n) {
		super(i, l, w);
		this.neighbors = n;
		Arrays.sort(this.neighbors);
	}
	
	public NeighborListNode(int i, String l, Edge[] n) {
		super(i, l);
		this.neighbors = n;
		Arrays.sort(this.neighbors);
	}

	@Override
	public Edge[] getNeighbors() {
		return this.neighbors;
	}

	@Override
	public void addLink(int neighbor, double weight) {
		if (this.neighbors == null){
			this.neighbors = new Edge[]{new WeightedEdgeDouble(neighbor,weight)};
		} else {
			this.neighbors = ArrayExpand.expand(this.neighbors, neighbor, weight);
		}
		Arrays.sort(this.neighbors);
		
	}
	
	@Override
	public void addLink(int neighbor) {
		if (this.neighbors == null){
			this.neighbors = new Edge[]{new Edge(neighbor)};
		} else {
			this.neighbors = ArrayExpand.expand(this.neighbors, neighbor,true);
		}
		Arrays.sort(this.neighbors);
		
	}

	@Override
	public void addLink(int neighbor, int weight) {
		if (this.neighbors == null){
			this.neighbors = new Edge[]{new WeightedEdge(neighbor,weight)};
		} else {
			this.neighbors = ArrayExpand.expand(this.neighbors, neighbor, weight);
		}
		Arrays.sort(this.neighbors);
		
	}

	@Override
	public Edge getLink(int neighbor) {
		int index = Arrays.binarySearch(this.neighbors, new Edge(neighbor));
		if (index < 0){
			return null;
		}else {
			return this.neighbors[index];
		}
		
	}

	@Override
	public void setNeighbors(Vector<Edge> edges, int graphSize) {
		this.neighbors = new Edge[edges.size()];
		for (int i = 0; i < edges.size(); i++){
			this.neighbors[i] = edges.get(i);
		}
		this.setDegree(edges.size());
		Arrays.sort(this.neighbors);
	}

	

}
