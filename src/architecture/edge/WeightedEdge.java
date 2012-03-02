package architecture.edge;

/**
 * edge with integer weight
 * @author stefanie
 *
 */
public class WeightedEdge extends Edge {
   private int weight;
	
	public WeightedEdge(int node, int weight) {
		super(node);
		this.weight = weight;
	}
	
	public WeightedEdge(int node, int weight, byte t) {
		super(node,t);
		this.weight = weight;
	}
	
	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public double getWeight(){
		return (double) this.weight;
	}

}
