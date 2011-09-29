package motife.undirected;

import motife.MotifRetriever;
import architecture.edge.Edge;
import architecture.graph.Graph;
import architecture.node.Node;

/**
 * for motif A--B, B--C, C--A (motif #2)
 * @author stefanie
 * based on work by Lachezar Krumov
 */
public class Clique3MotifAnalyzer extends MotifRetriever {

	public Clique3MotifAnalyzer(boolean edgeWeights, boolean nodeWeights) {
		super(1, new int[]{3}, new int[] {3}, edgeWeights, nodeWeights,false,null);
	}
	
	public Clique3MotifAnalyzer(boolean edgeWeights, boolean nodeWeights, String retrieve) {
		super(1, new int[]{3}, new int[] {3}, edgeWeights, nodeWeights,true,retrieve);
	}
	
	public Clique3MotifAnalyzer(boolean edgeWeights, boolean nodeWeights, String retrieve, int bound) {
		super(1, new int[]{3}, new int[] {3}, edgeWeights, nodeWeights,true,bound,retrieve);
	}

	@Override
	public void analyze(Graph g) {
		if (g.isDirected()){
			throw new IllegalArgumentException("Graph must be undirected");
		}
		Node[] nodes = g.getNodes();
		Edge[] neighbors;
		Node[] motifNodes = new Node[this.nodes[0]];
		Edge[] motifEdges = new Edge[this.edges[0]];
		for (int i = 0; i < nodes.length; i++){
			motifNodes[0] = nodes[i];
			neighbors = motifNodes[0].getNeighbors();
			for (int j = 0; j < neighbors.length; j++){
				motifEdges[0] = neighbors[j];
				motifNodes[1] = nodes[motifEdges[0].getNode()];
				if (motifNodes[1].getIndex() < motifNodes[0].getIndex()){
					continue;
				}
				for (int k = j+1; k < neighbors.length; k++){
					motifEdges[1] = neighbors[k];
					motifNodes[2] = nodes[motifEdges[1].getNode()];
					if (motifNodes[2].getIndex() < motifNodes[0].getIndex()){
						continue;
					}
					motifEdges[2] = motifNodes[1].getLink(motifEdges[1].getNode());  
					if (motifEdges[2] != null){
						this.evaluateMotif(0, motifNodes, motifEdges);
					}
				}
			}
		}

	}

}
