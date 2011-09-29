package motife.undirected;

import motife.MotifRetriever;
import architecture.edge.Edge;
import architecture.graph.Graph;
import architecture.node.Node;

/**
 * A--B, B--C (motif #1)
 * @author stefanie
 * based on work by Lachezar Krumov
 */
public class SemiClique3MotifAnalyzer extends MotifRetriever {

	public SemiClique3MotifAnalyzer(boolean edgeWeights, boolean nodeWeights) {
		super(1, new int[]{2}, new int[]{3}, edgeWeights, nodeWeights,false,null);
	}
	
	public SemiClique3MotifAnalyzer(boolean edgeWeights, boolean nodeWeights,String file) {
		super(1, new int[]{2}, new int[]{3}, edgeWeights, nodeWeights,true,file);
	}
	
	public SemiClique3MotifAnalyzer(boolean edgeWeights, boolean nodeWeights,String file, int bound) {
		super(1, new int[]{2}, new int[]{3}, edgeWeights, nodeWeights,true,bound,file);
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
				for (int k = j+1; k < neighbors.length; k++){
					motifEdges[1] = neighbors[k];
					if (motifNodes[1].getLink(motifEdges[1].getNode()) == null){
						motifNodes[2] = nodes[motifEdges[1].getNode()];
						this.evaluateMotif(0, motifNodes, motifEdges);
					}
				}
			}
		}

	}

}
