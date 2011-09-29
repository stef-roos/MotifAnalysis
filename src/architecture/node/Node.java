package architecture.node;

import java.util.Vector;

import architecture.edge.Edge;

/**
 * abstract class for nodes:
 * a node has:
 *   an index (in array nodes of corresponding graph)
 *   a label
 *   possibly a weight
 * neighbors, but these are usually set later
 * 
 *   neighbor representation depends on subtype
 * @author stefanie
 *
 */

public abstract class Node {
	private int index;
	private double weight;
	private String label;
	//degree is set after neighbors are set
	private int degree = 0;
	
	public Node(int i, String l, double w){
		this.index = i;
		this.weight = w;
		this.label = l;
	}
	
	public Node(int i, String l){
		this.index = i;
		this.weight = 0;
		this.label = l;
	}
	
	
	/**
	 * returns all outgoing edges of a node
	 * @return
	 */
	public abstract Edge[] getNeighbors();
	
	/**
	 * set the neighbors
	 * @param edges
	 * @param graphSize: #nodes in corresponding graph
	 */
	public abstract void setNeighbors(Vector<Edge> edges,int graphSize);
	
	/**
	 * add a edge without weight
	 * @param neighbor
	 */
    public abstract void addLink(int neighbor);
	
	/**
	 * add a edge
	 * @param neighbor
	 * @param weight
	 */
    public abstract void addLink(int neighbor, int weight);
	
	/**
	 * add a edge
	 * @param neighbor
	 * @param weight
	 */
    public abstract void addLink(int neighbor, double weight);
    
    /**
     * return edge if it exists and null otherwise
     * @param neighbor
     * @param weight
     * @return
     */
    public abstract Edge getLink(int neighbor);

	public int getIndex() {
		return index;
	}


	public double getWeight() {
		return weight;
	}

	public String getLabel() {
		return label;
	}
	
	public int getDegree() {
		return this.degree;
	}
	
	protected void setDegree(int deg){
		this.degree = deg;
	}
	
	/**
	 * equals for determining if a node has already been added (EdgesOnlyReader)
	 */
	@Override
	public boolean equals(Object n){
		if (n instanceof Node){
			Node node = (Node) n;
			if (node.getLabel().equals(this.getLabel()) && node.getWeight() == this.getWeight()){
				return true;
			}
		}
		
		return false;
	}

	
	

}
