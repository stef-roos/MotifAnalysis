package motife.directed3;

import motife.MotifRetriever;
import architecture.edge.Edge;
import architecture.graph.Graph;
import architecture.node.Node;

/**
 * 
 * @author stef
 *
 *13 directed 3-nodes motifs:
 *1: A -> B, A->C
 *2: B -> A, A -> C
 *3: A -- B, A -> C
 *4: B -> A, C -> A
 *5: A -> B, B -> C, A -> C
 *6: A -- B, A -> C, B -> C
 *7: A -- B, C -> A
 *8: A -- B, A -- C
 *9: A -> B, B -> C,C -> A
 *10: A -- B, A -> C, C -> B
 *11: A -- B, C -> A, C -> B
 *12: A -- B, A -- C, B -> C
 *13: A -- B, A -- C, B -- C
 */

public class Directed3Analyzer extends MotifRetriever {
	
	public Directed3Analyzer(boolean edgeWeights, boolean nodeWeights) {
		super(13, new int[]{2,2,2,2,3,3,2,2,3,3,3,3,3}, new int[] {3,3,3, 3,3,3, 3,3,3, 3,3,3, 3}, edgeWeights, nodeWeights, false,null);
	}
	
	public Directed3Analyzer(boolean edgeWeights, boolean nodeWeights, String retrieve) {
		super(13,  new int[]{2,2,2,2,3,3,2,2,3,3,3,3,3},new int[] {3,3,3, 3,3,3, 3,3,3, 3,3,3, 3}, edgeWeights, nodeWeights, true,retrieve);
	}
	
	public Directed3Analyzer(boolean edgeWeights, boolean nodeWeights, String retrieve, int[] bounds) {
		super(13, new int[]{2,2,2,2,3,3,2,2,3,3,3,3,3}, new int[] {3,3,3, 3,3,3, 3,3,3, 3,3,3, 3}, edgeWeights, nodeWeights, 
				true, bounds, retrieve);
	}
	
	public Directed3Analyzer(boolean edgeWeights, boolean nodeWeights, String retrieve, int bounds) {
		super(13, new int[]{2,2,2,2,3,3,2,2,3,3,3,3,3},new int[] {3,3,3, 3,3,3, 3,3,3, 3,3,3, 3}, edgeWeights, nodeWeights, 
				true, bounds, retrieve);
	}

	@Override
	public void analyze(Graph g) {
		Node[] nodes = g.getNodes();
		Node[] curNodes = new Node[3];
		Edge[] curEdges = new Edge[3];
        for (int i = 0; i < nodes.length; i++){
        	//System.out.println("i " + i);
        	Edge[] neighbors = nodes[i].getNeighbors();
        	curNodes[0] = nodes[i];
        	for (int j = 0; j < neighbors.length-1; j++){
        		
        		curEdges[0] = neighbors[j];
        		curNodes[1] = nodes[curEdges[0].getNode()];
        		//System.out.println("j " +curNodes[1].getIndex());
        		for (int k = j+1; k < neighbors.length; k++){
        			curEdges[1] = neighbors[k];
            		curNodes[2] = nodes[curEdges[1].getNode()];
            		//System.out.println("k " +curNodes[2].getIndex());
            		curEdges[2] = curNodes[1].getLink(curNodes[2].getIndex());
            		if (curEdges[2] == null){
            			//System.out.println("Triplet " + curNodes[0].getIndex() + " " + curNodes[1].getIndex() + " " + curNodes[2].getIndex());
            			analyzeTriple(curNodes,curEdges);
            		} else {
            			if (curNodes[0].getIndex() < curNodes[1].getIndex() && curNodes[0].getIndex() < curNodes[2].getIndex()){
            				//System.out.println("Triangle " + curNodes[0].getIndex() + " " + curNodes[1].getIndex() + " " + curNodes[2].getIndex());
                			analyzeTriangle(curNodes,curEdges);
            			}
            		}
        		}
        	}
        }
	}
	
	private void analyzeTriple(Node[] motifNodes, Edge[] motifEdges){
		byte[] t = {motifEdges[0].getType(), motifEdges[1].getType()};
		
		if (t[0] == 1 && t[1] == 1 ){
			this.evaluateMotif(0, motifNodes, motifEdges);
			return;
		}
		if (t[0] == 0 && t[1] == 1 ){
			this.evaluateMotif(2, motifNodes, motifEdges);
			return;
		}
		if (t[0] == -1 && t[1] == 1 ){
			this.evaluateMotif(1, motifNodes, motifEdges);
			return;
		}
		if (t[0] == 1 && t[1] == 0 ){
			this.evaluateMotif(2, motifNodes, motifEdges);
			return;
		}
		if (t[0] == 0 && t[1] == 0 ){
			this.evaluateMotif(7, motifNodes, motifEdges);
			return;
		}
		if (t[0] == -1 && t[1] == 0 ){
			this.evaluateMotif(6, motifNodes, motifEdges);
			return;
		}
		if (t[0] == 1 && t[1] == -1 ){
			this.evaluateMotif(1, motifNodes, motifEdges);
			return;
		}
		if (t[0] == 0 && t[1] == -1 ){
			this.evaluateMotif(6, motifNodes, motifEdges);
			return;
		}
		if (t[0] == -1 && t[1] == -1 ){
			this.evaluateMotif(3, motifNodes, motifEdges);
			return;
		}
	}
	
    private void analyzeTriangle(Node[] motifNodes, Edge[] motifEdges){
    	//edge 0: view A
    	//edge 1: view A
    	//edge 2: view B
		byte[] t = {motifEdges[0].getType(), motifEdges[1].getType(), motifEdges[2].getType()};
		if (t[0] == 1 && t[1] == 1 && t[2] == 1){
			this.evaluateMotif(4, motifNodes, motifEdges);
			return;
		}
		if (t[0] == 1 && t[1] == 1 && t[2] == 0){
			this.evaluateMotif(10, motifNodes, motifEdges);
			return;
		}
		if (t[0] == 1 && t[1] == 1 && t[2] == -1){
			this.evaluateMotif(4, motifNodes, motifEdges);
			return;
		}
		if (t[0] == 1 && t[1] == 0 && t[2] == -1){
			this.evaluateMotif(9, motifNodes, motifEdges);
			return;
		}
		if (t[0] == 1 && t[1] == 0 && t[2] == 0){
			this.evaluateMotif(11, motifNodes, motifEdges);
			return;
		}
		if (t[0] == 1 && t[1] == 0 && t[2] == -1){
			this.evaluateMotif(5, motifNodes, motifEdges);
			return;
		}
		if (t[0] == 1 && t[1] == -1 && t[2] == 1){
			this.evaluateMotif(8, motifNodes, motifEdges);
			return;
		}
		if (t[0] == 1 && t[1] == -1 && t[2] == 0){
			this.evaluateMotif(9, motifNodes, motifEdges);
			return;
		}
		if (t[0] == 1 && t[1] == -1 && t[2] == -1){
			this.evaluateMotif(4, motifNodes, motifEdges);
			return;
		}
		
		if (t[0] == 0 && t[1] == 1 && t[2] == 1){
			this.evaluateMotif(5, motifNodes, motifEdges);
			return;
		}
		if (t[0] == 0 && t[1] == 1 && t[2] == 0){
			this.evaluateMotif(11, motifNodes, motifEdges);
			return;
		}
		if (t[0] == 0 && t[1] == 1 && t[2] == -1){
			this.evaluateMotif(9, motifNodes, motifEdges);
			return;
		}
		if (t[0] == 0 && t[1] == 0 && t[2] == 1){
			this.evaluateMotif(11, motifNodes, motifEdges);
			return;
		}
		if (t[0] == 0 && t[1] == 0 && t[2] == 0){
			this.evaluateMotif(12, motifNodes, motifEdges);
			return;
		}
		if (t[0] == 0 && t[1] == 0 && t[2] == -1){
			this.evaluateMotif(11, motifNodes, motifEdges);
			return;
		}
		if (t[0] == 0 && t[1] == -1 && t[2] == 1){
			this.evaluateMotif(9, motifNodes, motifEdges);
			return;
		}
		if (t[0] == 0 && t[1] == -1 && t[2] == 0){
			this.evaluateMotif(11, motifNodes, motifEdges);
			return;
		}
		if (t[0] == 0 && t[1] == -1 && t[2] == -1){
			this.evaluateMotif(10, motifNodes, motifEdges);
			return;
		}
		
		if (t[0] == -1 && t[1] == 1 && t[2] == 1){
			this.evaluateMotif(4, motifNodes, motifEdges);
			return;
		}
		if (t[0] == -1 && t[1] == 1 && t[2] == 0){
			this.evaluateMotif(9, motifNodes, motifEdges);
			return;
		}
		if (t[0] == -1 && t[1] == 1 && t[2] == -1){
			this.evaluateMotif(8, motifNodes, motifEdges);
			return;
		}
		if (t[0] == -1 && t[1] == 0 && t[2] == 1){
			this.evaluateMotif(10, motifNodes, motifEdges);
			return;
		}
		if (t[0] == -1 && t[1] == 0 && t[2] == 0){
			this.evaluateMotif(11, motifNodes, motifEdges);
			return;
		}
		if (t[0] == -1 && t[1] == 0 && t[2] == -1){
			this.evaluateMotif(9, motifNodes, motifEdges);
			return;
		}
		if (t[0] == -1 && t[1] == -1 && t[2] == 1){
			this.evaluateMotif(4, motifNodes, motifEdges);
			return;
		}
		if (t[0] == -1 && t[1] == -1 && t[2] == 0){
			this.evaluateMotif(5, motifNodes, motifEdges);
			return;
		}
		if (t[0] == -1 && t[1] == -1 && t[2] == -1){
			this.evaluateMotif(4, motifNodes, motifEdges);
			return;
		}
	}

}
