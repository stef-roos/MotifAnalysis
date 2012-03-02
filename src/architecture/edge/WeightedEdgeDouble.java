package architecture.edge;

/**
 * edge with double weight
 * @author stefanie
 *
 */

public class WeightedEdgeDouble extends Edge {
	double weight;

	public WeightedEdgeDouble(int node, double w) {
		super(node);
		this.weight = w;
	}
	
	public WeightedEdgeDouble(int node, double w, byte t) {
		super(node, t);
		this.weight = w;
	}
	
	@Override
	public double getWeight(){
		return this.weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

}
