package motife;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import util.ArrayExpand;
import util.RetrieveDone;
import util.Stats;
import architecture.edge.Edge;
import architecture.graph.Graph;
import architecture.node.Node;
import config.Config;

/**
 * abstract class for analyzing motif distribution of a graph
 * @author stefanie
 * based on work by Lachezar Krumov
 * 
 * creates the following files:
 * prefix + Count.txt: MotifNr Count Count/(Count all Motifs)
 * prefix + EdgeWeight.txt: MotifNr Weight(sum of edge weights in motif) Weight/Count 
 *                          Weight/(Count*#edges) Weight/(Weight all Motifs)
  * prefix + NodeWeight.txt: MotifNr Weight(sum of node weights in motif) Weight/Count 
 *                          Weight/(Count*#edges) Weight/(Weight all Motifs)
 *          (later two only if weights are used)  
 *          
 *  optional:
 *  prefix + EdgeWeightDistMotif + i + .txt for all motifs: edge weight distribution 
 *                                 over motif instances (in Counts, percentage, CDF) 
 *  prefix + NodeWeightDistMotif + i + .txt for all motifs: node weight distribution 
 *                                 over motif instances (in Counts, percentage, CDF) 
 */
public abstract class MotifAnalyzer {
	//count motifs of analyzer
	protected long[] counts;
	//sum of motif weights
	protected double[] motifweights;
    //sum of motif node weights
	protected double[] motifnodeweights;
	//edges per motif
	protected int[] edges;
	//nodes per motif
	protected int[] nodes;
	//weight distributions
	protected int[][] edgeDist;
	protected int[][] nodeDist;
	protected double edgeweight;
	protected double nodeweight;
	
	/**
	 * 
	 * @param motifsNr: number of motifs that should be analyzed
	 * @param edges: #edges contained in motif
	 * @param nodes: #nodes contained in motif
	 * @param edgeWeights: consider edge weights for analysis
	 * @param nodeWeights: consider node weights for analysis
	 */
	public MotifAnalyzer(int motifsNr, int[] edges, int[] nodes, boolean edgeWeights, boolean nodeWeights){
		counts = new long[motifsNr];
		if (edgeWeights) motifweights = new double[motifsNr];
		if (nodeWeights) motifnodeweights = new double[motifsNr];
		this.edges = edges;
		this.nodes = nodes;
	}
	
	/**
	 * analyze the graph g, write results to prefix + respective postfix for each metrics
	 * @param edgedist: write a distribution of edge weights over motifs
	 * @param nodedist: write a distribution of node weights over motifs
	 * @param prefix
	 * @param g
	 */
	public void analyzeMotifs(boolean edgedist, boolean nodedist, String prefix, Graph g){
		if (edgedist){
			if (this.motifweights != null){
			this.edgeDist = new int[this.motifweights.length][0];
			if (!Config.EDGEWEIGHTTYPEINT){
				System.out.println("WARNING:edge weights will be floored to next integer");
			}
			} else {
				System.out.println("Cannot get a weight distribution without weights");
			}
		}
		if (nodedist){
			if (this.motifnodeweights != null){
				this.nodeDist = new int[this.motifweights.length][0];
				System.out.println("WARNING: node weights will be floored to next integer");
				} else {
					System.out.println("Cannot get a weight distribution without weights");
				}
		}
		
		this.analyze(g);
		this.writeCountFile(prefix);
		if (this.motifweights != null){
			this.writeEdgeWFile(prefix);
		}
		if (this.motifnodeweights != null){
			this.writeNodeWFile(prefix);
		}
		this.writeWeightDist(prefix);
		
	}
	
	/**
	 * actual analysis
	 * @param g
	 */
	public abstract void analyze(Graph g);
	
	
	
	/**
	 * write the file prefix+Count.txt
	 * MotifNr Count Count/(Count all Motifs)
	 * @param prefix
	 */
	private void writeCountFile(String prefix){
		double[] perc = new double[counts.length];
		long count = 0;
		for (int i = 0; i < counts.length; i++){
			count = count + counts[i];
		}
		for (int i = 0; i < counts.length; i++){
			perc[i] = (double) counts[i]/count;
		}
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(prefix+"Count.txt"));
			bw.write("# motif, count, percentage");
			for (int i = 0; i < counts.length; i++){
				String line = "" + (i+1) + " " + counts[i]+" "+ perc[i]*100;
				bw.newLine();
				bw.write(line);
			}   
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * write edge weight singles to prefix + EdgeWeight.txt
	 * motifNr weight weight/count weight/(count*motifedges) weight/(weight all motifs)
	 * @param prefix
	 */
	private void writeEdgeWFile(String prefix){
		double[] perc = new double[this.motifweights.length];
		double[] perMotif = new double[this.motifweights.length];
		double[] perEdge = new double[this.motifweights.length];
		double count = 0;
		for (int i = 0; i < this.motifweights.length; i++){
			count = count + this.motifweights[i];
		}
		for (int i = 0; i < this.motifweights.length; i++){
			perc[i] = (double) this.motifweights[i]/count;
			if (this.counts[i] > 0){
			perMotif[i] = (double)this.motifweights[i]/this.counts[i];
			perEdge[i] = perMotif[i]/(this.edges[i]);
			} else {
				perMotif[i] = 0;
				perEdge[i] = 0;
			}
		}
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(prefix+"EdgeWeight.txt"));
			bw.write("# motif, count, per Motif, per Edge, percentage");
			for (int i = 0; i < this.motifweights.length; i++){
				String line = "" + (i+1) + " " + this.motifweights[i]+" "+ perMotif[i] +
				" " + perEdge[i] + " " +perc[i]*100;
				bw.newLine();
				bw.write(line);
			}   
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * write node weight singles to prefix + NodeWeight.txt
	 * motifNr weight weight/count weight/(count*#motifnodes) weight/(weight all motifs)
	 * @param prefix
	 */
	private void writeNodeWFile(String prefix){
		double[] perc = new double[this.motifnodeweights.length];
		double[] perMotif = new double[this.motifnodeweights.length];
		double[] perNode = new double[this.motifnodeweights.length];
		double count = 0;
		for (int i = 0; i < this.motifnodeweights.length; i++){
			count = count + this.motifnodeweights[i];
		}
		for (int i = 0; i < this.motifnodeweights.length; i++){
			perc[i] = (double) this.motifnodeweights[i]/count;
			if (this.counts[i] > 0){
			perMotif[i] = (double)this.motifnodeweights[i]/this.counts[i];
			perNode[i] = (double)this.motifnodeweights[i]/(this.counts[i]*this.nodes[i]);
			} else {
				perMotif[i] = 0;
				perNode[i] = 0;
			}
		}
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(prefix+"NodeWeight.txt"));
			bw.write("# motif, count, per Motif, per Edge, percentage");
			for (int i = 0; i < this.motifweights.length; i++){
				String line = "" + (i+1) + " " + this.motifnodeweights[i]+" "+ perMotif[i] +
				" " + perNode[i] +" "+ perc[i]*100;
				bw.newLine();
				bw.write(line);
			}   
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * write weight distributions if desired
	 * @param prefix
	 */
	private void writeWeightDist(String prefix){
		if (this.edgeDist != null){
			for (int i = 0; i < edgeDist.length; i++){
			Stats.writeDistFunc(prefix + "EdgeWeightDistMotif"+(i+1)+".txt", edgeDist[i]);
			}
		}

		if (this.nodeDist != null){
			for (int i = 0; i < nodeDist.length; i++){
				Stats.writeDistFunc(prefix + "NodeWeightDistMotif"+(i+1)+".txt", nodeDist[i]);
				}
		}
	}
	
	
	/**
	 * analyze a motif defined by the nodes in motifNodes and edges in motifEdges
	 * @param index: index of the motif in array
	 * @param motifNodes: #nodes in a motif (might be larger than actual #, then only first this.nodes[index] are used)
	 * @param motifEdges: #edges in a motif (might be larger than actual #, then only first this.edges[index] are used)
	 */
	protected void evaluateMotif(int index, Node[] motifNodes, Edge[] motifEdges){
		this.counts[index]++;
		if (this.motifnodeweights != null){
			this.nodeweight = 0;
			for (int j = 0; j < this.nodes[index]; j++){
				 this.nodeweight = this.nodeweight  + motifNodes[j].getWeight();
			}
			this.motifnodeweights[index] = this.motifnodeweights[index] + this.nodeweight;
			if (this.nodeDist != null){
				this.nodeDist[index] = this.inc((int)nodeweight, this.nodeDist[index]);
			}
		}
		if (this.motifweights != null){
			this.edgeweight = 0;
			for (int j = 0; j < this.edges[index]; j++){
				this.edgeweight = this.edgeweight  + motifEdges[j].getWeight();
			}
			this.motifweights[index] = this.motifweights[index] + this.edgeweight;
			if (this.edgeDist != null){
				this.edgeDist[index] = this.inc((int)edgeweight, this.edgeDist[index]);
			}
		}
	}
	
	/**
	 * increment position pos in an array
	 * possibly expand array for this
	 * @param pos
	 * @param array
	 * @return
	 */
	protected int[] inc(int pos, int[] array){
		if (pos > array.length -1){
			array = ArrayExpand.expand(array, pos+1);
		}
		array[pos]++;
		return array;
	}
	
	

}
