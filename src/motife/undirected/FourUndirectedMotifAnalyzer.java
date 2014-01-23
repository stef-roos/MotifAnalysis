package motife.undirected;

import architecture.edge.Edge;
import architecture.graph.Graph;
import architecture.node.Node;
import motife.MotifRetriever;

/**
 * retrieve or analyze all undirected 4 nodes motifs
 * 1: A--B, A--C, A--D (Star)
 * 2: A--B, B--C, C--D (FourChain)
 * 3: A--B, B--C, C--A, A--D (ThreeLoopOut)
 * 4: A--B, B--C, C--D, D--A (FoutLoop)
 * 5: A--B, B--C, C--D, D--A, A--C (SemiClique4)
 * 6: A--B, A--C, A--D, B--C, B--D, C--D (Clique4)
 * @author stefanie
 * based on work by Lachezar Krumov
 */
public class FourUndirectedMotifAnalyzer extends MotifRetriever {

	
	

		public FourUndirectedMotifAnalyzer(boolean edgeWeights, boolean nodeWeights) {
			super(6, new int[] {3,3,4,4,5,6}, new int[]{4,4,4,4,4,4}, edgeWeights, nodeWeights, false,null);
		}
		
		public FourUndirectedMotifAnalyzer(boolean edgeWeights, boolean nodeWeights, String retrieve) {
			super(6, new int[] {3,3,4,4,5,6}, new int[]{4,4,4,4,4,4}, edgeWeights, nodeWeights, true,retrieve);
		}
		
		public FourUndirectedMotifAnalyzer(boolean edgeWeights, boolean nodeWeights, String retrieve, int[] bounds) {
			super(6, new int[] {3,3,4,4,5,6}, new int[]{4,4,4,4,4,4}, edgeWeights, nodeWeights, 
					true, bounds, retrieve);
		}
		
		public FourUndirectedMotifAnalyzer(boolean edgeWeights, boolean nodeWeights, String retrieve, int bounds) {
			super(6, new int[] {3,3,4,4,5,6}, new int[]{4,4,4,4,4,4}, edgeWeights, nodeWeights, 
					true, bounds, retrieve);
		}

		@Override
		public void analyze(Graph g) {
			if (g.isDirected()){
				throw new IllegalArgumentException("Graph must be undirected");
			}
			Node[] nodes = g.getNodes();
			Edge[] neighbors, neighbors2;
			//maximal number of edges, nodes => only those that are actual part of motif are evaluated 
			//(compare MotifAnalyzer.evaluateMotif)
			Node[] motifNodes = new Node[4];
			Edge[] motifEdges = new Edge[6];
	        for (int i = 0; i < nodes.length; i++){
	        	motifNodes[0] = nodes[i];
	        	neighbors = nodes[i].getNeighbors();
	        	for (int j = 0; j < neighbors.length-1; j++){
	        		motifEdges[0] = neighbors[j];
	        		motifNodes[1] = nodes[neighbors[j].getNode()];
	        		for (int k = j + 1; k < neighbors.length; k++){
	        			motifEdges[1] = neighbors[k];
	        			motifNodes[2] = nodes[neighbors[k].getNode()];
	        			motifEdges[2] = motifNodes[1].getLink(motifNodes[2].getIndex());
	        			if (motifEdges[2] == null){
	        				
	        				//determine TwoV (motif #3) 
	        				for (int l = k + 1; l < neighbors.length; l++){
	        					motifEdges[2] = neighbors[l];
	                			motifNodes[3] = nodes[neighbors[l].getNode()];
	                			motifEdges[3] = motifNodes[3].getLink(motifNodes[1].getIndex());
	                			motifEdges[4] = motifNodes[3].getLink(motifNodes[2].getIndex());
	                		    if (motifEdges[3] == null){
	                		    	if (motifEdges[4] == null){
	                		    	   this.evaluateMotif(0, motifNodes, motifEdges);
	                		    	} 
	                		    } 
	                		    if (motifEdges[3] != null && motifEdges[4] != null){
	                		    	if (motifNodes[0].getIndex() < motifNodes[3].getIndex()
	                		    		&& motifNodes[0].getIndex() < motifNodes[1].getIndex()){
	                		    	this.evaluateMotif(4, motifNodes, motifEdges);
	                		    	}
	                		    }
	        				}
	        				
	        				//determine 4Chain (#4) + 4Loop (#6)
	        				if (motifNodes[0].getIndex() < motifNodes[1].getIndex()){
	        				neighbors2 = motifNodes[1].getNeighbors();
	        				for (int l = 0; l < neighbors2.length; l++){
	        					if (neighbors2[l].getNode() == i){
	        						continue;
	        					}
	        					motifEdges[2] = neighbors2[l];
	                			motifNodes[3] = nodes[neighbors2[l].getNode()];
	                			if (motifNodes[3].getLink(motifNodes[0].getIndex()) != null){
	                				continue;
	                			}
	                			motifEdges[3] = motifNodes[3].getLink(motifNodes[2].getIndex());
	                			if (motifEdges[3] == null){
	                				this.evaluateMotif(1, motifNodes, motifEdges);
	                			} else {
	                				if(motifNodes[0].getIndex() < motifNodes[3].getIndex()){
	                					this.evaluateMotif(3, motifNodes, motifEdges);
	                				}
	                			}
	        				}
	        				}
	        				
	        				//determine 4Chain (#4) other direction
	        				if (motifNodes[0].getIndex() < motifNodes[2].getIndex()){
	        				neighbors2 = motifNodes[2].getNeighbors();
	        				for (int l = 0; l < neighbors2.length; l++){
	        					if (neighbors2[l].getNode() == i){
	        						continue;
	        					}
	        					motifEdges[2] = neighbors2[l];
	                			motifNodes[3] = nodes[neighbors2[l].getNode()];
	                			if (motifNodes[3].getLink(motifNodes[0].getIndex()) != null){
	                				continue;
	                			}
	                			motifEdges[3] = motifNodes[3].getLink(motifNodes[1].getIndex());
	                			if (motifEdges[3] == null){
	                				this.evaluateMotif(1, motifNodes, motifEdges);
	                			} 
	        				}
	        				}
	        			} else {
	        				//found motif #2 (need to break symmetry)
	        				if (motifNodes[0].getIndex() < motifNodes[1].getIndex() &&
	        						motifNodes[0].getIndex() < motifNodes[2].getIndex()){
	        					
	        					
	        					//determine Semi4Clique + 4Clique (#8)
	        					for (int l = k+1; l < neighbors.length; l++){
	        						motifEdges[3] = neighbors[l];
	        						motifNodes[3] = nodes[neighbors[l].getNode()];
	        						motifEdges[4] = motifNodes[1].getLink(motifNodes[3].getIndex());
	        						motifEdges[5] = motifNodes[2].getLink(motifNodes[3].getIndex());
	        						if (motifEdges[4] == null && motifEdges[5] == null){
	        							
	        						} else {
	        							    if (motifEdges[4] == null || motifEdges[5] == null){
	        							    	if (motifEdges[4] == null){
	        							    		motifEdges[4] = motifEdges[5];
	        							    	}
	        									this.evaluateMotif(4, motifNodes, motifEdges);
	        								}else {
	        									if(motifNodes[0].getIndex() < motifNodes[3].getIndex()) {
	        									 this.evaluateMotif(5, motifNodes, motifEdges);
	        									} 
	        								}
	        							
	        								
	        						}
	        					}
	        					
	        					//determine Semi4Clique
	            				neighbors2 = motifNodes[1].getNeighbors();
	            				for (int l = 0; l < neighbors2.length; l++){
	            					if (neighbors2[l].getNode() == i){
	            						continue;
	            					}
	            					motifEdges[3] = neighbors2[l];
	                    			motifNodes[3] = nodes[neighbors2[l].getNode()];
	                    			if (motifNodes[3].getLink(motifNodes[0].getIndex()) != null){
	                    				continue;
	                    			}
	                    			motifEdges[4] = motifNodes[3].getLink(motifNodes[2].getIndex());
	                    			if (motifEdges[4] != null){
	                    				if(motifNodes[0].getIndex() < motifNodes[3].getIndex()){
	                    					this.evaluateMotif(4, motifNodes, motifEdges);
	                    				}
	                    			}
	            				}
	            				
	        					
	        					
	        				}
	        				
	        				//determine 3LoopOut
	        				for (int l = 0; l < neighbors.length; l++){
	    						motifEdges[3] = neighbors[l];
	    						motifNodes[3] = nodes[neighbors[l].getNode()];
	    						motifEdges[4] = motifNodes[1].getLink(motifNodes[3].getIndex());
	    						motifEdges[5] = motifNodes[2].getLink(motifNodes[3].getIndex());
	    						if (motifEdges[4] == null && motifEdges[5] == null){
	    							this.evaluateMotif(2, motifNodes, motifEdges);
	    						} 
	    					}
	        			}
	        		}
	        	}
	        }
		}
	

}
