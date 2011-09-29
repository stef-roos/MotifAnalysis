package motife.undirected;

import motife.MotifRetriever;
import architecture.edge.Edge;
import architecture.graph.Graph;
import architecture.node.Node;


 /**
  * A--B, A--C, A--D, B--C, B--D, C--D (motif #8)
  * @author stefanie
  * based on work by Lachezar Krumov
  */
public class Clique4MotifAnalyzer extends MotifRetriever {

	public Clique4MotifAnalyzer(boolean edgeWeights, boolean nodeWeights) {
		super(1, new int[] {6}, new int[] {4}, edgeWeights, nodeWeights,false,null);
	}
	
	public Clique4MotifAnalyzer(boolean edgeWeights, boolean nodeWeights, String file) {
		super(1, new int[] {6}, new int[] {4}, edgeWeights, nodeWeights,true,file);
	}
	
	public Clique4MotifAnalyzer(boolean edgeWeights, boolean nodeWeights, String file, int bound) {
		super(1, new int[] {6}, new int[] {4}, edgeWeights, nodeWeights,true,bound,file);
	}

	@Override
	public void analyze(Graph g) {
		if (g.isDirected()){
			throw new IllegalArgumentException("Graph must be undirected");
		}
		Node[] nodes = g.getNodes();
		Edge[] neighbors, neighbors2;
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
		    	neighbors2 = motifNodes[1].getNeighbors();
		    	for (int k = j+1; k < neighbors.length; k++){
		    		motifEdges[1] = neighbors[k];
			    	motifNodes[2] = nodes[motifEdges[1].getNode()];
			    	if (motifNodes[2].getIndex() < motifNodes[0].getIndex()){
			    		continue;
			    	}
			    	for (int l = 0; l < neighbors2.length; l++){
			    		motifEdges[2] = neighbors2[l];
			    		motifNodes[3] = nodes[motifEdges[2].getNode()];
			    		if (motifNodes[3].getIndex() == i){
			    			continue;
			    		}
			    		if (motifNodes[3].getIndex() < motifNodes[0].getIndex()){
				    		continue;
				    	}
			    		if (motifNodes[3].getIndex() < motifNodes[1].getIndex()){
				    		continue;
				    	}
			    		if (motifNodes[3].getIndex() < motifNodes[2].getIndex()){
				    		continue;
				    	}
			    		motifEdges[3] = motifNodes[2].getLink(motifNodes[3].getIndex());
			    		if (motifEdges[3] == null){
			    			continue;
			    		}
			    		motifEdges[4] = motifNodes[1].getLink(motifNodes[2].getIndex());
			    		if (motifEdges[4] != null){
			    		    motifEdges[5] = motifNodes[0].getLink(motifNodes[3].getIndex());
			    			if (motifEdges[5] != null){
			    				this.evaluateMotif(0, motifNodes, motifEdges);
			    			}
			    		}
			    	}
		    	}
		    }
		}


	}

}
