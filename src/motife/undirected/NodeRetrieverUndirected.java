package motife.undirected;

import java.util.Arrays;
import java.util.Vector;

import motife.MotifNodeRetriever;
import architecture.edge.Edge;
import architecture.graph.Graph;
import architecture.node.Node;

/**
 * analyze motifs a specific node participates in
 * 1: A--B, B--C (SemiClique3)
 * 2: A--B, B--C, C--A (Clique3)
 * 3: A--B, A--C, A--D (TwoV)
 * 4: A--B, B--C, C--D (FourChain)
 * 5: A--B, B--C, C--A, A--D (ThreeLoopOut)
 * 6: A--B, B--C, C--D, D--A (FoutLoop)
 * 7: A--B, B--C, C--D, D--A, A--C (SemiClique4)
 * 8: A--B, A--C, A--D, B--C, B--D, C--D (Clique4)
 * @author stefanie
 * based on work by Lachezar Krumov
 */
public class NodeRetrieverUndirected extends MotifNodeRetriever {
	//which of the 8 motifs should be analyzed
	//default: all
	private boolean[] motifs;
	
    public NodeRetrieverUndirected(boolean edgeWeights, boolean nodeWeights, boolean retrieve,
			int bound, String file, Node node, boolean[] motifs) {
		super(8,new int[] {2,3,3,3,4,4,5,6}, new int[]{3,3,4,4,4,4,4,4},edgeWeights, nodeWeights, retrieve, bound, file,
				node);
		this.motifs = motifs;
	}
	
	public NodeRetrieverUndirected(boolean edgeWeights, boolean nodeWeights, boolean retrieve,
			int bound, String file, Node node) {
		this(edgeWeights,nodeWeights, retrieve, bound, file,node, new boolean[]{true, true, true,true,true,true,true,true});
	}
	
	public NodeRetrieverUndirected(boolean edgeWeights, boolean nodeWeights, boolean retrieve,
			int[] bound, String file, Node node, boolean[] motifs) {
		super(8,new int[] {2,3,3,3,4,4,5,6}, new int[]{3,3,4,4,4,4,4,4},edgeWeights, nodeWeights, retrieve, bound, file,
				node);
		this.motifs = motifs;
	}
	
	public NodeRetrieverUndirected(boolean edgeWeights, boolean nodeWeights, boolean retrieve,
			int[] bound, String file, Node node) {
		this(edgeWeights,nodeWeights, retrieve, bound, file,node, new boolean[]{true, true, true,true,true,true,true,true});
	}
	
	public NodeRetrieverUndirected(boolean edgeWeights, boolean nodeWeights,  Node node, boolean[] motifs) {
		super(8,new int[] {2,3,3,3,4,4,5,6}, new int[]{3,3,4,4,4,4,4,4},edgeWeights, nodeWeights, false, null,
				node);
		this.motifs = motifs;
	}
	
	public NodeRetrieverUndirected(boolean edgeWeights, boolean nodeWeights, Node node) {
		this(edgeWeights,nodeWeights,node, new boolean[]{true, true, true,true,true,true,true,true});
	}
	
	public NodeRetrieverUndirected(boolean edgeWeights, boolean nodeWeights,  Node node, String file, boolean[] motifs) {
		super(8,new int[] {2,3,3,3,4,4,5,6}, new int[]{3,3,4,4,4,4,4,4},edgeWeights, nodeWeights, true, file,
				node);
		this.motifs = motifs;
	}
	
	public NodeRetrieverUndirected(boolean edgeWeights, boolean nodeWeights, Node node, String file) {
		this(edgeWeights,nodeWeights,node,file, new boolean[]{true, true, true,true,true,true,true,true});
	}
	
	

	@Override
	public void analyze(Graph g) {
		if (this.motifs[0]){
			this.analyzeMotif1(g);
		}
		if (this.motifs[1]){
			this.analyzeMotif2(g);
		}
		if (this.motifs[2]){
			this.analyzeMotif3(g);
		}
		if (this.motifs[3]){
			this.analyzeMotif4(g);
        }
		if (this.motifs[4]){
			this.analyzeMotif5(g);
		}
		if (this.motifs[5]){
			this.analyzeMotif6(g);
		}
		if (this.motifs[6]){
			this.analyzeMotif7(g);
		}
		if (this.motifs[7]){
			this.analyzeMotif8(g);
		}
	}
	
	public Vector<int[]> analyzeMotif(Graph g, int motif) {
		if (motif==1){
			return this.analyzeMotif1(g);
		}
		if (motif==2){
			return this.analyzeMotif2(g);
		}
		if (motif==3){
			return this.analyzeMotif3(g);
		}
		if (motif==4){
			return this.analyzeMotif4(g);
        }
		if (motif==5){
			return this.analyzeMotif5(g);
		}
		if (motif==6){
			return this.analyzeMotif6(g);
		}
		if (motif==7){
			return this.analyzeMotif7(g);
		}
		if (motif==8){
			return this.analyzeMotif8(g);
		}
		throw new IllegalArgumentException("There is no motif with index " + motif);
	}
	
	public Vector<int[]> analyzeMotif1(Graph g){
		int count = 0;
		Node[] nodes = g.getNodes();
		Edge[] neighbors, neighbors2;
		Node[] motifNodes = new Node[3];
		Edge[] motifEdges = new Edge[2];
		Vector<int[]> res = new Vector<int[]>();
		int[] index = new int[motifNodes.length]; 
		
		motifNodes[0] = this.getNode();
		index[0] = motifNodes[0].getIndex();
		neighbors = motifNodes[0].getNeighbors();

		for (int j = 0; j < neighbors.length; j++){
			motifEdges[0] = neighbors[j];
			motifNodes[1] = nodes[neighbors[j].getNode()];
			index[1] = motifNodes[1].getIndex();
			
			//case 1: node has degree 2 in Motif
			for (int k = j + 1; k < neighbors.length; k++){
				motifEdges[1] = neighbors[k];
				motifNodes[2] = nodes[neighbors[k].getNode()];
				index[2] = motifNodes[2].getIndex();
				if (motifNodes[1].getLink(motifNodes[2].getIndex()) == null){
					//this.evaluateMotif(0, motifNodes, motifEdges);
					count++;
					int[] clone = index.clone();
					Arrays.sort(clone);
					res.add(clone);
				}
			}
			
			//case 2: node has degree 1 in Motif
			neighbors2 = motifNodes[1].getNeighbors();
			for (int k = 0; k < neighbors2.length; k++){
				if (neighbors2[k].getNode() == this.getNode().getIndex()){
					continue;
				}
				motifEdges[1] = neighbors2[k];
				motifNodes[2] = nodes[neighbors2[k].getNode()];
				index[2] = motifNodes[2].getIndex();
				if (motifNodes[0].getLink(motifNodes[2].getIndex()) == null){
					//this.evaluateMotif(0, motifNodes, motifEdges);
					count++;
					int[] clone = index.clone();
					Arrays.sort(clone);
					res.add(clone);
				}
			}
		}
		
		return res;
	}
	
	public Vector<int[]> analyzeMotif2(Graph g){
		int count = 0;
		Node[] nodes = g.getNodes();
		Edge[] neighbors, neighbors2;
		Node[] motifNodes = new Node[3];
		Edge[] motifEdges = new Edge[3];
		Vector<int[]> res = new Vector<int[]>();
		int[] index = new int[motifNodes.length]; 
		
		motifNodes[0] = this.getNode();
		index[0] = motifNodes[0].getIndex();
		neighbors = motifNodes[0].getNeighbors();

		for (int j = 0; j < neighbors.length; j++){
			motifEdges[0] = neighbors[j];
			motifNodes[1] = nodes[neighbors[j].getNode()];
			index[1] = motifNodes[1].getIndex();
			//case 1: node has degree 2 in Motif
			for (int k = j + 1; k < neighbors.length; k++){
				motifEdges[1] = neighbors[k];
				motifNodes[2] = nodes[neighbors[k].getNode()];
				index[2] = motifNodes[2].getIndex();
				
				motifEdges[2] = motifNodes[1].getLink(motifNodes[2].getIndex()); 
				if (motifEdges[2] != null){
					//this.evaluateMotif(1, motifNodes, motifEdges);
					count++;
					int[] clone = index.clone();
					Arrays.sort(clone);
					res.add(clone);
				}
			}
			
			
		}
		
		return res;
	}
	
	public Vector<int[]> analyzeMotif3(Graph g){
		int count = 0;
		Node[] nodes = g.getNodes();
		Edge[] neighbors, neighbors2;
		Node[] motifNodes = new Node[4];
		Edge[] motifEdges = new Edge[3];
		Vector<int[]> res = new Vector<int[]>();
		int[] index = new int[motifNodes.length]; 
		
		motifNodes[0] = this.getNode();
		index[0] = motifNodes[0].getIndex();
		neighbors = motifNodes[0].getNeighbors();

		for (int j = 0; j < neighbors.length; j++){
			motifEdges[0] = neighbors[j];
			motifNodes[1] = nodes[neighbors[j].getNode()];
			index[1] = motifNodes[1].getIndex();
			//case 1: node has degree 3 in Motif
			for (int k = j + 1; k < neighbors.length-1; k++){
				motifEdges[1] = neighbors[k];
				motifNodes[2] = nodes[neighbors[k].getNode()];
				index[2] = motifNodes[2].getIndex();
				if (motifNodes[1].getLink(motifNodes[2].getIndex()) != null){
					continue;
				}
				
				for (int l = k + 1; l < neighbors.length; l++){
					motifEdges[2] = neighbors[l];
					motifNodes[3] = nodes[neighbors[l].getNode()];
					index[3] = motifNodes[3].getIndex();
					if (motifNodes[1].getLink(motifNodes[3].getIndex()) != null
						|| 	motifNodes[2].getLink(motifNodes[3].getIndex()) != null){
						continue;
					}
					
					//this.evaluateMotif(2, motifNodes, motifEdges);
					count++;
					int[] clone = index.clone();
					Arrays.sort(clone);
					res.add(clone);
				}
			
			}
			
			//case 2: node has degree 1 in Motif
			neighbors2 = motifNodes[1].getNeighbors();
			for (int k = 0; k < neighbors2.length-1; k++){
				motifEdges[1] = neighbors2[k];
				motifNodes[2] = nodes[neighbors2[k].getNode()];
				index[2] = motifNodes[2].getIndex();
				if (motifNodes[2].getIndex() == motifNodes[0].getIndex() ||
					motifNodes[0].getLink(motifNodes[2].getIndex()) != null	){
					continue;
				}
				for (int l = k + 1; l < neighbors2.length; l++){
					motifEdges[2] = neighbors2[l];
					motifNodes[3] = nodes[neighbors2[l].getNode()];
					index[3] = motifNodes[3].getIndex();
					if (motifNodes[3].getIndex() == motifNodes[0].getIndex() ||
						motifNodes[0].getLink(motifNodes[3].getIndex()) != null	||
						motifNodes[2].getLink(motifNodes[3].getIndex()) != null){
						continue;
					}	
					
					//this.evaluateMotif(2, motifNodes, motifEdges);
					count++;
					int[] clone = index.clone();
					Arrays.sort(clone);
					res.add(clone);
				}
			}
		}
		
		return res;
	}
	
	public Vector<int[]> analyzeMotif4(Graph g){
		int count = 0;
		Node[] nodes = g.getNodes();
		Edge[] neighbors, neighbors2, neighbors3;
		Node[] motifNodes = new Node[4];
		Edge[] motifEdges = new Edge[3];
		Vector<int[]> res = new Vector<int[]>();
		int[] index = new int[motifNodes.length]; 
		
		motifNodes[0] = this.getNode();
		index[0] = motifNodes[0].getIndex();
		neighbors = motifNodes[0].getNeighbors();

		for (int j = 0; j < neighbors.length; j++){
			motifEdges[0] = neighbors[j];
			motifNodes[1] = nodes[neighbors[j].getNode()];
			index[1] = motifNodes[1].getIndex();
			
			neighbors2 = motifNodes[1].getNeighbors();
			for (int k = 0; k < neighbors2.length; k++){
				motifEdges[1] = neighbors2[k];
				motifNodes[2] = nodes[neighbors2[k].getNode()];
				index[2] = motifNodes[2].getIndex();
				if (motifNodes[2].getIndex() == motifNodes[0].getIndex() ||
					motifNodes[0].getLink(motifNodes[2].getIndex()) != null	){
					continue;
				}
				
				//case 1: Node has degree 2 in motif
				for (int l = 0; l < neighbors.length; l++){
					motifEdges[2] = neighbors[l];
					motifNodes[3] = nodes[neighbors[l].getNode()];
					index[3] = motifNodes[3].getIndex();
					if (motifNodes[3].getIndex() == motifNodes[1].getIndex() ||
						motifNodes[1].getLink(motifNodes[3].getIndex()) != null	||
						motifNodes[2].getLink(motifNodes[3].getIndex()) != null	){
						continue;
					}
					
					//this.evaluateMotif(3, motifNodes, motifEdges);
					count++;
					int[] clone = index.clone();
					Arrays.sort(clone);
					res.add(clone);
				}
				
				//case: Nodes has degree 1 in motif
				neighbors3 = motifNodes[2].getNeighbors();
				for (int l = 0; l < neighbors3.length; l++){
					motifEdges[2] = neighbors3[l];
					motifNodes[3] = nodes[neighbors3[l].getNode()];
					index[3] = motifNodes[3].getIndex();
					if (motifNodes[3].getIndex() == motifNodes[1].getIndex() ||
						motifNodes[0].getLink(motifNodes[3].getIndex()) != null	||
						motifNodes[1].getLink(motifNodes[3].getIndex()) != null	){
						continue;
					}
					
					//this.evaluateMotif(3, motifNodes, motifEdges);
					count++;
					int[] clone = index.clone();
					Arrays.sort(clone);
					res.add(clone);
				}
			}
		}
		
		return res;
	}
	
	public Vector<int[]> analyzeMotif5(Graph g){
		int count = 0;
		Node[] nodes = g.getNodes();
		Edge[] neighbors, neighbors2;
		Node[] motifNodes = new Node[4];
		Edge[] motifEdges = new Edge[4];
		Vector<int[]> res = new Vector<int[]>();
		int[] index = new int[motifNodes.length]; 
		
		motifNodes[0] = this.getNode();
		index[0] = motifNodes[0].getIndex();
		neighbors = motifNodes[0].getNeighbors();

		for (int j = 0; j < neighbors.length; j++){
			motifEdges[0] = neighbors[j];
			motifNodes[1] = nodes[neighbors[j].getNode()];
			index[1] = motifNodes[1].getIndex();
			
			for (int k = j + 1; k < neighbors.length; k++){
				motifEdges[1] = neighbors[k];
				motifNodes[2] = nodes[neighbors[k].getNode()];
				index[2] = motifNodes[2].getIndex();
				motifEdges[2] = motifNodes[1].getLink(motifNodes[2].getIndex()); 
				if (motifEdges[2] == null){
					continue;
				}
				
				//case 1: Node has degree 3 in Motif
				for (int l = 0; l < neighbors.length;l++){
					if  (l == k || l == j){
						continue;
					}
					motifEdges[3] = neighbors[l];
					motifNodes[3] = nodes[neighbors[l].getNode()];
					index[3] = motifNodes[3].getIndex();
					if (motifNodes[1].getLink(motifNodes[3].getIndex()) != null	||
						motifNodes[2].getLink(motifNodes[3].getIndex()) != null	){
						continue;
					}
					
					//this.evaluateMotif(4, motifNodes, motifEdges);
					count++;
					int[] clone = index.clone();
					Arrays.sort(clone);
					res.add(clone);
				}	
				
				//case 2: node has degree 2
				neighbors2 = motifNodes[1].getNeighbors();
				for (int l = 0; l < neighbors2.length;l++){
					motifEdges[3] = neighbors2[l];
					motifNodes[3] = nodes[neighbors2[l].getNode()];
					index[3] = motifNodes[3].getIndex();
					if (motifNodes[3].getIndex() == motifNodes[0].getIndex() ||
						motifNodes[3].getIndex() == motifNodes[2].getIndex() ||
						motifNodes[0].getLink(motifNodes[3].getIndex()) != null	||
						motifNodes[2].getLink(motifNodes[3].getIndex()) != null	){
						continue;
					}
					
					//this.evaluateMotif(4, motifNodes, motifEdges);
					count++;
					int[] clone = index.clone();
					Arrays.sort(clone);
					res.add(clone);
				}
				
				neighbors2 = motifNodes[2].getNeighbors();
				for (int l = 0; l < neighbors2.length;l++){
					motifEdges[3] = neighbors2[l];
					motifNodes[3] = nodes[neighbors2[l].getNode()];
					index[3] = motifNodes[3].getIndex();
					if (motifNodes[3].getIndex() == motifNodes[0].getIndex() ||
						motifNodes[3].getIndex() == motifNodes[1].getIndex() ||
						motifNodes[0].getLink(motifNodes[3].getIndex()) != null	||
						motifNodes[1].getLink(motifNodes[3].getIndex()) != null	){
						continue;
					}
					
					//this.evaluateMotif(4, motifNodes, motifEdges);
					count++;
					int[] clone = index.clone();
					Arrays.sort(clone);
					res.add(clone);
				}
			}
			
			//node has degree 1 in motif
			neighbors2 = motifNodes[1].getNeighbors();
			for (int k = 0; k < neighbors2.length; k++){
				motifEdges[1] = neighbors2[k];
				motifNodes[2] = nodes[neighbors2[k].getNode()];
				index[2] = motifNodes[2].getIndex();
				if (motifNodes[2].getIndex() == motifNodes[0].getIndex() ||
					motifNodes[2].getLink(motifNodes[0].getIndex()) != null){
					continue;
				}
				
				for (int l = k + 1; l < neighbors2.length; l++){
					motifEdges[2] = neighbors2[l];
					motifNodes[3] = nodes[neighbors2[l].getNode()];
					index[3] = motifNodes[3].getIndex();
					if (motifNodes[3].getIndex() == motifNodes[0].getIndex() ){
						continue;
					}
					motifEdges[3] = motifNodes[3].getLink(motifNodes[2].getIndex());
					
					if (motifEdges[3] == null ||
						motifNodes[3].getLink(motifNodes[0].getIndex()) != null ){
						continue;
					}
					
					//this.evaluateMotif(4, motifNodes, motifEdges);
					count++;
					int[] clone = index.clone();
					Arrays.sort(clone);
					res.add(clone);
				}
				
			}
			
		}
		
		return res;
	}
	
	public Vector<int[]> analyzeMotif6(Graph g){
		int count = 0;
		Node[] nodes = g.getNodes();
		Edge[] neighbors, neighbors2;
		Node[] motifNodes = new Node[4];
		Edge[] motifEdges = new Edge[4];
		Vector<int[]> res = new Vector<int[]>();
		int[] index = new int[motifNodes.length]; 
		
		motifNodes[0] = this.getNode();
		index[0] = motifNodes[0].getIndex();
		neighbors = motifNodes[0].getNeighbors();

		for (int j = 0; j < neighbors.length-1; j++){
			motifEdges[0] = neighbors[j];
			motifNodes[1] = nodes[neighbors[j].getNode()];
			index[1] = motifNodes[1].getIndex();
			for (int k = j + 1; k < neighbors.length; k++){
				motifEdges[1] = neighbors[k];
				motifNodes[2] = nodes[neighbors[k].getNode()];
				index[2] = motifNodes[2].getIndex();
				if (motifNodes[1].getLink(motifNodes[2].getIndex()) != null){
					continue;
				}
				
				neighbors2 = motifNodes[1].getNeighbors();
				for (int l = 0; l < neighbors2.length; l++){
					motifEdges[2] = neighbors2[l];
					motifNodes[3] = nodes[neighbors2[l].getNode()];
					index[3] = motifNodes[3].getIndex();
					if (motifNodes[3].getIndex() == motifNodes[0].getIndex()){
						continue;
					}
					
					motifEdges[3] = motifNodes[2].getLink(motifNodes[3].getIndex());
					if (motifEdges[3] == null ||
						motifNodes[0].getLink(motifNodes[3].getIndex()) != null){
						continue;
					}
					
					//this.evaluateMotif(5, motifNodes, motifEdges);
					count++;
					int[] clone = index.clone();
					Arrays.sort(clone);
					res.add(clone);
				}
			}
			
			
		}
		
		return res;
	}
	
	public Vector<int[]> analyzeMotif7(Graph g){
		int count = 0;
		Node[] nodes = g.getNodes();
		Edge[] neighbors, neighbors2;
		Node[] motifNodes = new Node[4];
		Edge[] motifEdges = new Edge[5];
		Vector<int[]> res = new Vector<int[]>();
		int[] index = new int[motifNodes.length]; 
		
		motifNodes[0] = this.getNode();
		index[0] = motifNodes[0].getIndex();
		neighbors = motifNodes[0].getNeighbors();

		for (int j = 0; j < neighbors.length-1; j++){
			motifEdges[0] = neighbors[j];
			motifNodes[1] = nodes[neighbors[j].getNode()];
			index[1] = motifNodes[1].getIndex();
			for (int k = j + 1; k < neighbors.length; k++){
				motifEdges[1] = neighbors[k];
				motifNodes[2] = nodes[neighbors[k].getNode()];
				index[2] = motifNodes[2].getIndex();
				motifEdges[2] = motifNodes[1].getLink(motifNodes[2].getIndex()); 
				if ( motifEdges[2] != null){
					
					//case 1: Node has degree 2 in motif
					neighbors2 = motifNodes[1].getNeighbors();
					for (int l = 0; l < neighbors2.length; l++){
						motifEdges[3] = neighbors2[l];
						motifNodes[3] = nodes[neighbors2[l].getNode()];
						index[3] = motifNodes[3].getIndex();
						if (motifNodes[3].getIndex() == motifNodes[0].getIndex()){
							continue;
						}
						
						motifEdges[4] = motifNodes[2].getLink(motifNodes[3].getIndex());
						if (motifEdges[4] == null ||
							motifNodes[0].getLink(motifNodes[3].getIndex()) != null){
							continue;
						}
						
						//this.evaluateMotif(6, motifNodes, motifEdges);
						count++;
						int[] clone = index.clone();
						Arrays.sort(clone);
						res.add(clone);
					}
				} else {
					//case 2: Node has degree 3 in motif
					neighbors2 = motifNodes[1].getNeighbors();
					for (int l = 0; l < neighbors2.length; l++){
						motifEdges[2] = neighbors2[l];
						motifNodes[3] = nodes[neighbors2[l].getNode()];
						index[3] = motifNodes[3].getIndex();
						if (motifNodes[3].getIndex() == motifNodes[0].getIndex() ||
								motifNodes[3].getIndex() == motifNodes[2].getIndex()){
							continue;
						}
						
						motifEdges[3] = motifNodes[2].getLink(motifNodes[3].getIndex());
						if (motifEdges[3] == null ){
							continue;
						}
						motifEdges[4] = motifNodes[0].getLink(motifNodes[3].getIndex());
						if (motifEdges[4] == null){
							continue;
						}
						
						//this.evaluateMotif(6, motifNodes, motifEdges);
						count++;
						int[] clone = index.clone();
						Arrays.sort(clone);
						res.add(clone);
					}
				}
				
				
			}
			
			
		}
		
		return res;
	}
	
	public Vector<int[]> analyzeMotif8(Graph g){
		int count = 0;
		Node[] nodes = g.getNodes();
		Edge[] neighbors;
		Node[] motifNodes = new Node[4];
		Edge[] motifEdges = new Edge[6];
		Vector<int[]> res = new Vector<int[]>();
		int[] index = new int[motifNodes.length]; 
		
		motifNodes[0] = this.getNode();
		index[0] = motifNodes[0].getIndex();
		neighbors = motifNodes[0].getNeighbors();

		for (int j = 0; j < neighbors.length-2; j++){
			motifEdges[0] = neighbors[j];
			motifNodes[1] = nodes[neighbors[j].getNode()];
			index[1] = motifNodes[1].getIndex();
			for (int k = j + 1; k < neighbors.length-1; k++){
				motifEdges[1] = neighbors[k];
				motifNodes[2] = nodes[neighbors[k].getNode()];
				index[2] = motifNodes[2].getIndex();
				motifEdges[2] = motifNodes[1].getLink(motifNodes[2].getIndex()); 
				if (motifEdges[2] == null){
					continue;
				}
			
			
			for (int l = k + 1; l < neighbors.length; l++){
				motifEdges[3] = neighbors[l];
				motifNodes[3] = nodes[neighbors[l].getNode()];
				index[3] = motifNodes[3].getIndex();
				motifEdges[4] = motifNodes[1].getLink(motifNodes[3].getIndex()); 
				if (motifEdges[4] == null){
					continue;
				}
				motifEdges[5] = motifNodes[2].getLink(motifNodes[3].getIndex()); 
				if (motifEdges[5] == null){
					continue;
				}
				//this.evaluateMotif(7, motifNodes, motifEdges);
				count++;
				int[] clone = index.clone();
				Arrays.sort(clone);
				res.add(clone);
			}
			}
		}
		
		return res;
	}

}

