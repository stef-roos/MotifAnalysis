package architecture.graph;


import config.Config;
import io.graph.GraphReader;
import architecture.node.Node;

/**
 * class graph:
 * basically defined by its Nodes
 * @author stefanie
 *
 */

public class Graph {
	
	private Node[] nodes;
	private boolean directed;
	//representation a adjacency matrix or list?
	private boolean matrix;
	private int nodecount;
	private int edgecount;
	
	/**
	 * configuration parameters for reading and run are displayed:
	 * avoid mistakes by forgetting to reset
	 * @param read: a Graph Reader that constructs the nodes and edges
	 * @param dir
	 * @param matrix
	 */
	public Graph(GraphReader read, boolean dir, boolean matrix){
		System.out.println("USED CONFIGURATION PARAMETERS:");
		System.out.println("Deliminator: '"+Config.DELIMINATOR + "'");
		System.out.println("Columns: " + Config.COLUMNS);
		System.out.println("EdgeWeightTypeInt: " + Config.EDGEWEIGHTTYPEINT);
		this.nodes = read.readGraph(dir,matrix);
		this.directed = dir;
		this.matrix = matrix;
		this.nodecount = this.nodes.length;
		this.getEdgeCount();
		System.out.println("Reading done");
	}
	
	/**
	 * give nodes directly
	 * @param nodes
	 * @param dir
	 * @param matrix
	 */
     public Graph(Node[] nodes, boolean dir, boolean matrix){
		this.nodes = nodes;
		this.directed = dir;
		this.matrix = matrix;
		this.nodecount = this.nodes.length;
		this.getEdgeCount();
	}
	
	private void getEdgeCount() {
		edgecount = 0;
		for (int i = 0; i < this.nodes.length; i++){
			edgecount = edgecount + this.nodes[i].getDegree();
		}
		if (!this.directed){
			edgecount = edgecount/2;
		}
	}

	public int getNodecount() {
		return nodecount;
	}



	public int getEdgecount() {
		return edgecount;
	}



	public Node[] getNodes() {
		return nodes;
	}

	public boolean isDirected() {
		return directed;
	}

	public boolean isMatrix() {
		return matrix;
	}

}
