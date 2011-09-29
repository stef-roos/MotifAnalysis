package motife.undirected;

import motife.MotifRetriever;
import architecture.edge.Edge;
import architecture.graph.Graph;
import architecture.node.Node;

/**
 * A--B, A--C, A--D (motif #3)
 * @author stefanie
 * based on work by Lachezar Krumov
 */
public class TwoVMotifAnalyzer extends MotifRetriever {

	public TwoVMotifAnalyzer(boolean edgeWeights, boolean nodeWeights) {
		super(1, new int[] {3}, new int[]{4}, edgeWeights, nodeWeights, false,null);
	}
	
	public TwoVMotifAnalyzer(boolean edgeWeights, boolean nodeWeights, String file) {
		super(1, new int[] {3}, new int[]{4}, edgeWeights, nodeWeights,true,file);
	}
	
	public TwoVMotifAnalyzer(boolean edgeWeights, boolean nodeWeights, String file, int bound) {
		super(1, new int[] {3}, new int[]{4}, edgeWeights, nodeWeights,true,bound,file);
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
			for (int j = 0; j < neighbors.length-2; j++){
				motifEdges[0] = neighbors[j];
				motifNodes[1] = nodes[motifEdges[0].getNode()];
				for (int k = j+1; k < neighbors.length-1; k++){
					motifEdges[1] = neighbors[k];
					motifNodes[2] = nodes[motifEdges[1].getNode()];
					if (motifNodes[1].getLink(motifNodes[2].getIndex()) != null){
						continue;
					}
					for (int l = k + 1; l < neighbors.length; l++){
						motifEdges[2] = neighbors[l];
						motifNodes[3] = nodes[motifEdges[2].getNode()];
						if (motifNodes[1].getLink(motifNodes[3].getIndex()) != null || 
							motifNodes[2].getLink(motifNodes[3].getIndex()) != null){
							continue;
						}
						this.evaluateMotif(0, motifNodes, motifEdges);
					}
				}
			}
		}
		

	}

}
