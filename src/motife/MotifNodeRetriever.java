package motife;

import architecture.graph.Graph;
import architecture.node.Node;

/**
 * analyzing motif a specific node participates in 
 * @author stefanie
 *
 */
public abstract class MotifNodeRetriever extends MotifRetriever {
	//node that should be analyzed
	private Node node;

	public MotifNodeRetriever(int motifsNr, int[] edges, int[] nodes,
			boolean edgeWeights, boolean nodeWeights, boolean retrieve,
			int bound, String file, Node node) {
		super(motifsNr, edges, nodes, edgeWeights, nodeWeights, retrieve, bound, file);
		this.setNode(node);
	}
	
	public MotifNodeRetriever(int motifsNr, int[] edges, int[] nodes,
			boolean edgeWeights, boolean nodeWeights, boolean retrieve,
			int[] bound, String file, Node node) {
		super(motifsNr, edges, nodes, edgeWeights, nodeWeights, retrieve, bound, file);
		this.setNode(node);
	}
	
	public MotifNodeRetriever(int motifsNr, int[] edges, int[] nodes,
			boolean edgeWeights, boolean nodeWeights, boolean retrieve,
			 String file, Node node) {
		super(motifsNr, edges, nodes, edgeWeights, nodeWeights, retrieve, file);
		this.setNode(node);
	}

	public void setNode(Node node) {
		this.node = node;
		this.counts = new long[this.counts.length];
		if (this.motifweights != null){
		  this.motifweights = new double[this.counts.length];
		}
		if (this.motifnodeweights != null){
		  this.motifnodeweights = new double[this.counts.length];
		}
	}

	public Node getNode() {
		return node;
	}

	

}
