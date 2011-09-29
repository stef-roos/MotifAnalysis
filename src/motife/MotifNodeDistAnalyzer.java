package motife;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import util.Stats;

import architecture.edge.Edge;
import architecture.graph.Graph;
import architecture.node.Node;

/**
 * motif analyzer that allows the following distributions over nodes in a graph
 * 
 * results into files prefix + ...+.txt:
 * if summed is true:
 *    NodeDist: Count vs #/percentage/CDF Nodes participating in this many nodes (one file per motif)
 *    NodeEdgeDist: Edge Weight vs #/percentage/CDF Nodes participating in this many nodes (one file per motif)
 *    NodeNodeDist: Node Weight vs #/percentage/CDF Nodes participating in this many nodes (one file per motif)
 *    (later two only if weights are considered)
 *    
 * if individual is true:
 *    IndexCount resp. IndexCountPerc: Index of Node vs Count resp. Count/(Weight all motifs)
 *    IndexEdgeWeight resp. IndexEdgeWeightPerc: Index of Node vs Weight resp. Weight/(Weight all motifs)
 *    IndexNodeWeight resp. IndexNodeWeightPerc: Index of Node vs Weight resp. Weight/(Weight all motifs)
 *    (later two only if weights are considered)
 *    
 * @author stefanie
 *
 */
public abstract class MotifNodeDistAnalyzer extends MotifRetriever{
	private int[][] countNode;
	private double[][] nodeweightNode;
	private double[][] edgeweightNode;
	//create Node index vs. X files
	private boolean individual;
	//create Count/Weight vs. #/percentage nodes files
	private boolean summed;
	
	public MotifNodeDistAnalyzer(int motifsNr, int[] edges, int[] nodes,
			boolean edgeWeights, boolean nodeWeights, boolean retrieve, int[] bounds, 
			String file, boolean individual, boolean summed) {
		super(motifsNr, edges, nodes, edgeWeights, nodeWeights, retrieve, bounds,file);
		this.individual = individual;
		this.summed = summed;
	}
	
	
	
	
	public MotifNodeDistAnalyzer(int motifsNr, int[] edges, int[] nodes,
			boolean edgeWeights, boolean nodeWeights, boolean retrieve, int bound, 
			String file, boolean individual, boolean summed) {
		this(motifsNr, edges, nodes, edgeWeights, nodeWeights,retrieve,
				makeBounds(bound,motifsNr),file,individual,summed);
	}
	
	public MotifNodeDistAnalyzer(int motifsNr, int[] edges, int[] nodes,
			boolean edgeWeights, boolean nodeWeights, boolean retrieve, 
			String file,boolean individual, boolean summed) {
		this(motifsNr,edges, nodes, edgeWeights,nodeWeights,retrieve,
				makeBounds(Integer.MAX_VALUE,motifsNr),file,individual,summed);
	}
	
	private static int[] makeBounds(int bound, int motifsNr){
		int[] bounds = new int[motifsNr];
		for (int i = 0; i < bounds.length; i++){
			bounds[i] = bound;
		}
		return bounds;
	}

	@Override
	public void analyzeMotifs(boolean edgedist, boolean nodedist, String prefix, Graph g){
		this.countNode = new int[this.counts.length][g.getNodes().length];
		this.nodeweightNode = new double[this.counts.length][countNode[0].length];
		this.edgeweightNode = new double[this.counts.length][countNode[0].length];
		super.analyzeMotifs(edgedist, nodedist, prefix, g);
		this.writeNodeDist(prefix);
	}
	
	/**
	 * writing the files as described above
	 * @param prefix
	 */
	private void writeNodeDist(String prefix){
		if (this.individual){
			this.writeIndividual(prefix);
		}
		if (this.summed){
		for (int i = 0; i < this.counts.length; i++){
		Stats.makeDistFunc(prefix+"NodeDistMotif"+(i+1)+".txt", this.countNode[i], 10000000);
		if (this.motifweights != null) {
		Stats.makeDistFunc(prefix+"NodeEdgeDistMotif"+(i+1)+".txt", this.edgeweightNode[i], 10000000);
		}
		if (this.motifnodeweights != null){
		Stats.makeDistFunc(prefix+"NodeNodeDistMotif"+(i+1)+".txt", this.nodeweightNode[i], 10000000);
		}
		}
		}
	}
	
	/**
	 * fill in arrays for node distribution additionally to method in super class
	 */
	@Override
	protected void evaluateMotif(int index, Node[] motifNodes, Edge[] motifEdges){
		super.evaluateMotif(index, motifNodes, motifEdges);
		for (int i = 0; i < this.nodes[index]; i++){
			this.countNode[index][motifNodes[i].getIndex()]++;
			if (this.motifweights != null) {
			this.edgeweightNode[index][motifNodes[i].getIndex()] = this.edgeweightNode[index][motifNodes[i].getIndex()]+ edgeweight;
			}
			if (this.motifnodeweights != null) {
			this.nodeweightNode[index][motifNodes[i].getIndex()] = this.nodeweightNode[index][motifNodes[i].getIndex()]+ nodeweight;
			}
		}
	}
	
	
	/**
	 * methods for constructing/writing Node Index vs. X files
	 */
	private void writeIndividual(String prefix){
		this.writeIndex(prefix +"IndexCount.txt", this.countNode);
		this.writeIndex(prefix + "IndexCountPerc.txt", this.getPerc(this.countNode));
		if (this.motifweights != null){
			this.writeIndex(prefix +"IndexEdgeWeight.txt", this.edgeweightNode);
			this.writeIndex(prefix + "IndexEdgeWeightPerc.txt", this.getPerc(this.edgeweightNode));
		}
		if (this.motifnodeweights != null){
			this.writeIndex(prefix +"IndexNodeWeight.txt", this.nodeweightNode);
			this.writeIndex(prefix + "IndexNodeWeightPerc.txt", this.getPerc(this.nodeweightNode));
		}
	}
	
	private void writeIndex(String file, int[][] array){
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			String line = "#";
			for (int j = 0; j < array.length; j++){
				line = line + " Motif " + (j+1);
			}
			for (int i = 0; i < array[0].length; i++){
				line = ""+i;
				for (int j = 0; j < array.length; j++){
					line = line + " " + array[j][i];
				}
				bw.write(line);
				bw.newLine();
			}
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeIndex(String file, double[][] array){
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			String line = "#";
			for (int j = 0; j < array.length; j++){
				line = line + " Motif " + (j+1);
			}
			for (int i = 0; i < array[0].length; i++){
				line = ""+i;
				for (int j = 0; j < array.length; j++){
					line = line + " " + array[j][i];
				}
				bw.write(line);
				bw.newLine();
			}
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * create array with percentages from array with counts
	 * @param array
	 * @return
	 */
	private double[][] getPerc(int[][] array){
		double[][] res = new double[array.length][array[0].length];
		int sum;
		for (int i = 0; i < array[0].length; i++){
			sum = 0;
			for (int j = 0; j < array.length; j++){
				sum = sum + array[j][i];
			}
			for (int j = 0; j < array.length; j++){
				res[j][i] = (double)array[j][i]/sum;
			}
		}
		return res;
	}
	
	private double[][] getPerc(double[][] array){
		double[][] res = new double[array.length][array[0].length];
		double sum;
		for (int i = 0; i < array[0].length; i++){
			sum = 0;
			for (int j = 0; j < array.length; j++){
				sum = sum + array[j][i];
			}
			for (int j = 0; j < array.length; j++){
				res[j][i] = (double)array[j][i]/sum;
			}
		}
		return res;
	}

	
}
