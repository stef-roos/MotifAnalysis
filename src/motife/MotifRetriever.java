package motife;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import util.RetrieveDone;
import architecture.edge.Edge;
import architecture.graph.Graph;
import architecture.node.Node;

/**
 * abstract class extending MotifAnalyzer that can optionally create files
 * prefix + Motif+i+.txt containing a the first x retrieved instances of a motif
 * if x is not given all instances are retrieved
 * @author stefanie
 *
 */
public abstract class MotifRetriever extends MotifAnalyzer {
     private boolean retrieve;
     private int[] bounds;
     private BufferedWriter[] bws;
	
     /**
      * 
      * @param motifsNr
      * @param edges
      * @param nodes
      * @param edgeWeights
      * @param nodeWeights
      * @param retrieve: motif retrieved or analyzed (as in class MotifAnalyzer)?
      * @param bounds: bounds for how many instances of each motif should be retrieved
      * @param file: prefix for files with retrieved motifs
      */
	public MotifRetriever(int motifsNr, int[] edges, int[] nodes,
			boolean edgeWeights, boolean nodeWeights, boolean retrieve, int[] bounds, String file) {
		super(motifsNr, edges, nodes, edgeWeights, nodeWeights);
		this.retrieve = retrieve;
		this.bounds = bounds;
		if (retrieve){
			bws = new BufferedWriter[motifsNr];
		 if (motifsNr == 1){
			try {
				bws[0] = new BufferedWriter(new FileWriter(file));
			} catch (IOException e) {
				e.printStackTrace();
			}
		 } else {
			 for (int i = 0;i < bws.length; i++){
				 try {
						bws[i] = new BufferedWriter(new FileWriter(file +"Motif"+(i+1)+".txt"));
					} catch (IOException e) {
						e.printStackTrace();
					}	 
			 }
		 }
		}
	}
	
	/**
	 * as above, but with identical bound on all motifs
	 */
	public MotifRetriever(int motifsNr, int[] edges, int[] nodes,
			boolean edgeWeights, boolean nodeWeights, boolean retrieve, int bound, String file) {
		this(motifsNr, edges, nodes, edgeWeights, nodeWeights,retrieve,makeBounds(bound,motifsNr),file);
	}
	
	public MotifRetriever(int motifsNr, int[] edges, int[] nodes,
			boolean edgeWeights, boolean nodeWeights, boolean retrieve, String file) {
		this(motifsNr,edges, nodes, edgeWeights,nodeWeights,retrieve,makeBounds(Integer.MAX_VALUE,motifsNr),file);
	}
	
	private static int[] makeBounds(int bound, int motifsNr){
		int[] bounds = new int[motifsNr];
		for (int i = 0; i < bounds.length; i++){
			bounds[i] = bound;
		}
		return bounds;
	}

	protected boolean isRetrieve() {
		return retrieve;
	}

	protected void setRetrieve(boolean retrieve) {
		this.retrieve = retrieve;
	}
	
	protected int getBound(int index){
		return this.bounds[index];
	}
	
	/**
	 * close writers if necessary
	 * catch exception thrown in case all desired instances are found
	 */
	@Override
	public void analyzeMotifs(boolean edgedist, boolean nodedist, String prefix, Graph g){
		try{
			super.analyzeMotifs(edgedist, nodedist, prefix, g);
			if (bws != null){
			for (int i = 0; i < bws.length; i++){
				try {
					bws[i].flush();
					bws[i].close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			}
		} catch (RetrieveDone e){
			
		}
	}
	

	/**
	 * write down motif after analyzing it
	 */
	@Override
	protected void evaluateMotif(int index, Node[] motifNodes, Edge[] motifEdges){
		super.evaluateMotif(index, motifNodes, motifEdges);
		if (this.isRetrieve()){
		  if (this.counts[index] <= this.bounds[index]){
				BufferedWriter bw = this.bws[index];
				String line = "";
				for (int i = 0; i < this.nodes[index]; i++){
					line = line + motifNodes[i].getLabel() + " ";
				}
				line = line + "[EdgeWeight = " + this.edgeweight + "] [NodeWeight = " + this.nodeweight + "]";
				try {
					bw.write(line);
					bw.newLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			//check if done
			boolean finished = true;
			for (int i = 0; i < this.counts.length; i++){
				if (this.counts[i] < this.bounds[i]){
					finished = false;
					break;
				}
			}
			
			if (finished){
				for (int i = 0; i < bws.length; i++){
					try {
						bws[i].flush();
						bws[i].close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				throw new RetrieveDone();
			}
		}
	
	}

}
