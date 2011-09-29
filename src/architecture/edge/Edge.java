package architecture.edge;

/**
 * edge to node 
 * @author stefanie
 *
 */

public class Edge implements Comparable{
	private int node;
	
	public Edge(int node){
		this.node = node;
	}

	public int getNode() {
		return node;
	}

	public void setNode(int node) {
		this.node = node;
	}

	/**
	 * needs to be reimplemented by derived classes
	 * @return
	 */
	public double getWeight() {
		return 0;
	}

	

	/**
	 * Comparison function for sorting edges ascending by index of node
	 */
	@Override
	public int compareTo(Object arg0) {
		if (arg0 instanceof Edge){
			Edge edge = (Edge)arg0;
			if (edge.getNode() < this.getNode()){
				return 1;
			}
			if (edge.getNode() == this.getNode()){
				return 0;
			}
			if (edge.getNode() > this.getNode()){
				return -1;
			}
		}
		return 0;
		
	}
	
	/**
	 * edges are equal if they have the same end node
	 * => allows fast checking for existence of an edge 
	 * => no parallel edges
	 */
	@Override
	public boolean equals(Object e){
		if (e instanceof Edge){
			Edge edge = (Edge) e;
			if (edge.getNode() == this.getNode()){
				return true;
			}
		}
		return false;
	}
	
	

}
