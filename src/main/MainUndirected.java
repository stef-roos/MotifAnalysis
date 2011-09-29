package main;

import io.gnuplot.Plot;
import io.graph.EdgesOnlyReader;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import motife.MotifAnalyzer;
import motife.MotifNodeRetriever;
import motife.undirected.AllNodeDistMotifAnalyzer;
import motife.undirected.AllUndirectedMotifAnalyzer;
import motife.undirected.Clique3MotifAnalyzer;
import motife.undirected.NodeAnalyzerUndirected;
import architecture.graph.Graph;
import architecture.graph.UndirectedNLGraph;
import architecture.node.Node;
import config.Config;

public class MainUndirected {
	public static String[] files = {  "ER0.05.graph", "ER0.1.graph",
			"ER0.2.graph", "ER0.5.graph" };
	public static String[] titles = { "ER0.05", "ER0.1", "ER0.2", "ER0.5" };
	public static String folder = "graphs/";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Create result folder if it does not exist
		String result = "results/";
		File dir = new File(result);
		dir.mkdir();
        
		// run test case
		Counts();
		EdgeWeights();
		Weights();
		EdgeWeightsDist();
		WeightsDist();
		EdgeWeightsDistMotif2();
		WeightsDistMotif2();
		Retrieve();
		RetrieveAll();
		Node();
		NodeDist();
		NodeDistIndex();
		NodeDistIndexM2();
	}

	/**
	 * test for counting all motif instances
	 */
	public static void Counts() {
		Config.COLUMNS = 2;
		String result = "results/Counts";
		File dir = new File(result);
		dir.mkdir();
		MotifAnalyzer mo = new AllUndirectedMotifAnalyzer(false, false);
		for (int i = 0; i < files.length; i++) {
			try {
				Graph g = new UndirectedNLGraph(new EdgesOnlyReader(folder
						+ files[i]));
				mo
						.analyzeMotifs(false, false, "results/Counts/"
								+ titles[i], g);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Plot.plotCounts("results/Counts/", titles, "Count", false);
		Plot.plotCounts("results/Counts/", titles, "CountLog", true);
		Plot.plotCountPerc("results/Counts/", titles, "CountPercentage",false);
		Plot.plotCountPerc("results/Counts/", titles, "CountPercentageLog",true);
	}

	/**
	 * test for counting all motif instances + edge weights
	 */
	public static void EdgeWeights() {
		Config.COLUMNS = 3;
		String result = "results/EdgeWeights";
		File dir = new File(result);
		dir.mkdir();
		MotifAnalyzer mo = new AllUndirectedMotifAnalyzer(true, false);
		for (int i = 0; i < files.length; i++) {
			try {
				Graph g = new UndirectedNLGraph(new EdgesOnlyReader(folder
						+ files[i]));
				mo.analyzeMotifs(false, false, "results/EdgeWeights/"
						+ titles[i], g);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Plot.plotCounts("results/EdgeWeights/", titles, "Count", false);
		Plot.plotCounts("results/EdgeWeights/", titles, "CountLog", true);
		Plot.plotCountPerc("results/EdgeWeights/", titles, "CountPercentage", false);
		Plot.plotCountPerc("results/EdgeWeights/", titles, "CountPercentageLog",false);
		Plot.plotAllEdgeWeight("results/EdgeWeights/", titles, "EdgeWeight",
				false);
		Plot.plotAllEdgeWeight("results/EdgeWeights/", titles, "EdgeWeightLog",
				true);
	}

	/**
	 * test for counting all motif instances + edge weights + node weights
	 */
	public static void Weights() {
		Config.COLUMNS = 5;
		String result = "results/Weights";
		File dir = new File(result);
		dir.mkdir();
		MotifAnalyzer mo = new AllUndirectedMotifAnalyzer(true, true);
		for (int i = 0; i < files.length; i++) {
			try {
				Graph g = new UndirectedNLGraph(new EdgesOnlyReader(folder
						+ files[i]));
				mo.analyzeMotifs(false, false, "results/Weights/" + titles[i],
						g);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Plot.plotCounts("results/Weights/", titles, "Count", false);
		Plot.plotCounts("results/Weights/", titles, "CountLog", true);
		Plot.plotCountPerc("results/Weights/", titles, "CountPercentage", false);
		Plot.plotCountPerc("results/Weights/", titles, "CountPercentageLog",true);
		Plot.plotAllEdgeWeight("results/Weights/", titles, "EdgeWeight", false);
		Plot.plotAllEdgeWeight("results/Weights/", titles, "EdgeWeightLog",
				true);
		Plot.plotAllNodeWeight("results/Weights/", titles, "NodeWeight", false);
		Plot.plotAllNodeWeight("results/Weights/", titles, "NodeWeightLog",
				true);
	}

	/**
	 * test for counting all motif instances + edge weights + distribution over
	 * edge weights for 1 (!) file (currently: ER0.2.graph)
	 */
	public static void EdgeWeightsDist() {
		String file = files[2];
		String titel = titles[2];
		Config.COLUMNS = 3;
		String result = "results/EdgeWeightsDist";
		File dir = new File(result);
		dir.mkdir();
		MotifAnalyzer mo = new AllUndirectedMotifAnalyzer(true, false);

		try {
			Graph g = new UndirectedNLGraph(new EdgesOnlyReader(folder + file));
			mo
					.analyzeMotifs(true, false, "results/EdgeWeightsDist/"
							+ titel, g);

		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] title = new String[] { titel };
		String[] titles = new String[8];
		for (int i = 0; i < titles.length; i++) {
			titles[i] = title[0];
		}
		Plot.plotCounts("results/EdgeWeightsDist/", title, "Count", false);
		Plot.plotCounts("results/EdgeWeightsDist/", title, "CountLog", true);
		Plot
				.plotCountPerc("results/EdgeWeightsDist/", title,
						"CountPercentage", false);
		Plot.plotCountPerc("results/Counts/", titles, "CountPercentageLog",true);
		Plot.plotAllEdgeWeight("results/EdgeWeightsDist/", title, "EdgeWeight",
				false);
		Plot.plotAllEdgeWeight("results/EdgeWeightsDist/", title,
				"EdgeWeightLog", true);
		Plot.plotEdgeWeightDistCount("results/EdgeWeightsDist/", titles,
				"EdgeWeightDistCount", new int[] { 1, 2, 3, 4, 5, 6, 7, 8 });
		Plot.plotEdgeWeightDistPerc("results/EdgeWeightsDist/", titles,
				"EdgeWeightDistPerc", new int[] { 1, 2, 3, 4, 5, 6, 7, 8 });
		Plot.plotEdgeWeightDistCDF("results/EdgeWeightsDist/", titles,
				"EdgeWeightDistCDF", new int[] { 1, 2, 3, 4, 5, 6, 7, 8 });
	}

	/**
	 * test for counting all motif instances + edge weights + node weights +
	 * distributions for 1 file (currently ER0.2.graph)
	 */
	public static void WeightsDist() {
		String file = files[2];
		String titel = titles[2];
		Config.COLUMNS = 5;
		String result = "results/WeightsDist";
		File dir = new File(result);
		dir.mkdir();
		MotifAnalyzer mo = new AllUndirectedMotifAnalyzer(true, true);
		try {
			Graph g = new UndirectedNLGraph(new EdgesOnlyReader(folder + file));
			mo.analyzeMotifs(true, true, "results/WeightsDist/" + titel, g);

		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] title = new String[] { titel };
		String[] titles = new String[8];
		for (int i = 0; i < titles.length; i++) {
			titles[i] = title[0];
		}
		Plot.plotCounts("results/WeightsDist/", titles, "Count", false);
		Plot.plotCounts("results/WeightsDist/", titles, "CountLog", true);
		Plot.plotCountPerc("results/WeightsDist/", titles, "CountPercentage", false);
		Plot.plotCountPerc("results/Counts/", titles, "CountPercentageLog",true);
		Plot.plotAllEdgeWeight("results/WeightsDist/", titles, "EdgeWeight",
				false);
		Plot.plotAllEdgeWeight("results/WeightsDist/", titles, "EdgeWeightLog",
				true);
		Plot.plotAllNodeWeight("results/WeightsDist/", titles, "NodeWeight",
				false);
		Plot.plotAllNodeWeight("results/WeightsDist/", titles, "NodeWeightLog",
				true);
		Plot.plotEdgeWeightDistCount("results/WeightsDist/", titles,
				"EdgeWeightDistCount", new int[] { 1, 2, 3, 4, 5, 6, 7, 8 });
		Plot.plotEdgeWeightDistPerc("results/WeightsDist/", titles,
				"EdgeWeightDistPerc", new int[] { 1, 2, 3, 4, 5, 6, 7, 8 });
		Plot.plotEdgeWeightDistCDF("results/WeightsDist/", titles,
				"EdgeWeightDistCDF", new int[] { 1, 2, 3, 4, 5, 6, 7, 8 });
		Plot.plotNodeWeightDistCount("results/WeightsDist/", titles,
				"NodeWeightDistCount", new int[] { 1, 2, 3, 4, 5, 6, 7, 8 });
		Plot.plotNodeWeightDistPerc("results/WeightsDist/", titles,
				"NodeWeightDistPerc", new int[] { 1, 2, 3, 4, 5, 6, 7, 8 });
		Plot.plotNodeWeightDistCDF("results/WeightsDist/", titles,
				"NodeWeightDistCDF", new int[] { 1, 2, 3, 4, 5, 6, 7, 8 });
	}

	/**
	 * test for counting all Clique3 Motifs, edge weight + edge weight
	 * distribution
	 */
	public static void EdgeWeightsDistMotif2() {
		Config.COLUMNS = 3;
		String result = "results/EdgeWeightsDistM2";
		File dir = new File(result);
		dir.mkdir();
		MotifAnalyzer mo = new Clique3MotifAnalyzer(true, false);
		int[] num = new int[files.length];
		for (int i = 0; i < files.length; i++) {
			num[i] = 1;
			try {
				Graph g = new UndirectedNLGraph(new EdgesOnlyReader(folder
						+ files[i]));
				mo.analyzeMotifs(true, false, "results/EdgeWeightsDistM2/"
						+ titles[i], g);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		Plot.plotEdgeWeightDistCount("results/EdgeWeightsDistM2/", titles,
				"EdgeWeightDistCount", num);
		Plot.plotEdgeWeightDistPerc("results/EdgeWeightsDistM2/", titles,
				"EdgeWeightDistPerc", num);
		Plot.plotEdgeWeightDistCDF("results/EdgeWeightsDistM2/", titles,
				"EdgeWeightDistCDF", num);

	}

	/**
	 * test for counting all Clique3 Motifs, edge weight + weight distributions
	 */
	public static void WeightsDistMotif2() {
		Config.COLUMNS = 5;
		String result = "results/WeightsDistM2";
		File dir = new File(result);
		dir.mkdir();
		MotifAnalyzer mo = new Clique3MotifAnalyzer(true, true);
		int[] num = new int[files.length];
		for (int i = 0; i < files.length; i++) {
			num[i] = 1;
			try {
				Graph g = new UndirectedNLGraph(new EdgesOnlyReader(folder
						+ files[i]));
				mo.analyzeMotifs(true, true, "results/WeightsDistM2/"
						+ titles[i], g);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		Plot.plotEdgeWeightDistCount("results/WeightsDistM2/", titles,
				"EdgeWeightDistCount", num);
		Plot.plotEdgeWeightDistPerc("results/WeightsDistM2/", titles,
				"EdgeWeightDistPerc", num);
		Plot.plotEdgeWeightDistCDF("results/WeightsDistM2/", titles,
				"EdgeWeightDistCDF", num);
		
		Plot.plotNodeWeightDistCount("results/WeightsDistM2/", titles,
				"NodeWeightDistCount", num);
		Plot.plotNodeWeightDistPerc("results/WeightsDistM2/", titles,
				"NodeWeightDistPerc", num);
		Plot.plotNodeWeightDistCDF("results/WeightsDistM2/", titles,
				"NodeWeightDistCDF", num);
	}

	/**
	 * test for retrieving 10 first encountered motif instances for 1 file
	 * (current ER0.2.graph)
	 */
	public static void Retrieve() {
		String file = files[2];
		String titel = titles[2];
		Config.COLUMNS = 5;
		String result = "results/Retrieve";
		File dir = new File(result);
		dir.mkdir();
		MotifAnalyzer mo = new AllUndirectedMotifAnalyzer(true, true,
				"results/Retrieve/Retrieve", 10);

		try {
			Graph g = new UndirectedNLGraph(new EdgesOnlyReader(folder + file));
			mo.analyzeMotifs(false, false, "results/Retrieve/" + titel, g);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * test for retrieving all motif instances for 1 file (current ER0.05.graph)
	 */
	public static void RetrieveAll() {
		String file = files[0];
		String titel = titles[0];
		Config.COLUMNS = 5;
		String result = "results/RetrieveAll";
		File dir = new File(result);
		dir.mkdir();
		MotifAnalyzer mo = new AllUndirectedMotifAnalyzer(true, true,
				"results/RetrieveAll/Retrieve");
		try {
			Graph g = new UndirectedNLGraph(new EdgesOnlyReader(folder + file));
			mo.analyzeMotifs(false, false, "results/RetrieveAll/" + titel, g);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * motif signatures of 5 randomly selected nodes for one file (currently
	 * ER0.2.graph)
	 */
	public static void Node() {
		String file = files[2];
		String[] titel = new String[5];
		Config.COLUMNS = 5;
		String result = "results/Node";
		File dir = new File(result);
		dir.mkdir();
		Random rand = new Random();
		MotifNodeRetriever mo = new NodeAnalyzerUndirected(true, true, null);
		try {
			Graph g = new UndirectedNLGraph(new EdgesOnlyReader(folder + file));
			for (int i = 0; i < 5; i++) {
				Node node = g.getNodes()[rand.nextInt(g.getNodes().length)];
				mo.setNode(node);
				titel[i] = "Node" + node.getIndex();
				mo.analyzeMotifs(false, false, "results/Node/" + titel[i],
						g);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		Plot.plotCounts("results/Node/", titel, "Count", false);
		Plot.plotCounts("results/Node/", titel, "CountLog", true);
		Plot.plotCountPerc("results/Node/", titel, "CountPercentage", false);
		Plot.plotCountPerc("results/Counts/", titles, "CountPercentageLog",true);
		Plot.plotAllEdgeWeight("results/Node/", titel, "EdgeWeight", false);
		Plot.plotAllEdgeWeight("results/Node/", titel, "EdgeWeightLog",
				true);
		Plot.plotAllNodeWeight("results/Node/", titel, "NodeWeight", false);
		Plot.plotAllNodeWeight("results/Node/", titel, "NodeWeightLog",
				true);
	}
	
	/**
	 *distributions of counts + weights over nodes (currently
	 * ER0.2.graph)
	 */
	public static void NodeDist() {
		String file = files[2];
		String[] titel = new String[8];
		int[] motifs = new int[8];
		for (int i = 0; i < 8; i++){
			titel[i] = titles[2];
			motifs[i] = i+1;
		}
		Config.COLUMNS = 5;
		String result = "results/NodeDist";
		File dir = new File(result);
		dir.mkdir();
		Random rand = new Random();
		AllNodeDistMotifAnalyzer mo = new AllNodeDistMotifAnalyzer(true, true, false, true);
		try {
			Graph g = new UndirectedNLGraph(new EdgesOnlyReader(folder + file));
			
				mo.analyzeMotifs(false, false, "results/NodeDist/"+titel[0],
						g);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		Plot.plotNodeDistCount("results/NodeDist/", titel, "NodeDistCount", motifs);
		Plot.plotNodeDistPerc("results/NodeDist/", titel, "NodeDistCountPerc", motifs);
		Plot.plotNodeDistCDF("results/NodeDist/", titel, "NodeDistCountCDF", motifs);
		Plot.plotNodeEdgeDistCount("results/NodeDist/", titel, "NodeDistEdge", motifs);
		Plot.plotNodeEdgeDistPerc("results/NodeDist/", titel, "NodeDistEdgePerc", motifs);
		Plot.plotNodeEdgeDistCDF("results/NodeDist/", titel, "NodeDistEdgeCDF", motifs);
		Plot.plotNodeNodeDistCount("results/NodeDist/", titel, "NodeDistNode", motifs);
		Plot.plotNodeNodeDistPerc("results/NodeDist/", titel, "NodeDistNodePerc", motifs);
		Plot.plotNodeNodeDistCDF("results/NodeDist/", titel, "NodeDistNodeCDF", motifs);
	}
	
	/**
	 *node index vs. X plots
	 */
	public static void NodeDistIndex() {
		String file = files[2];
		String[] titel = new String[8];
		int[] motifs = new int[8];
		for (int i = 0; i < 8; i++){
			titel[i] = titles[2];
			motifs[i] = i+2;
		}
		Config.COLUMNS = 5;
		String result = "results/NodeDistIndex";
		File dir = new File(result);
		dir.mkdir();
		Random rand = new Random();
		AllNodeDistMotifAnalyzer mo = new AllNodeDistMotifAnalyzer(true, true, true, false);
		try {
			Graph g = new UndirectedNLGraph(new EdgesOnlyReader(folder + file));
			
				mo.analyzeMotifs(false, false, "results/NodeDistIndex/"+titel[0],
						g);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		Plot.plotIndexCount("results/NodeDistIndex/", titel, "CountDist", motifs);
		Plot.plotIndexCountPerc("results/NodeDistIndex/", titel, "CountDistPerc", motifs);
		Plot.plotIndexEdgeWeight("results/NodeDistIndex/", titel, "EdgeWeightDist", motifs);
		Plot.plotIndexEdgeWeightPerc("results/NodeDistIndex/", titel, "EdgeWeightDistPerc", motifs);
		Plot.plotIndexNodeWeight("results/NodeDistIndex/", titel, "NodeWeightDist", motifs);
		Plot.plotIndexNodeWeightPerc("results/NodeDistIndex/", titel, "NodeWeightDistPerc", motifs);
	}
	
	/**
	 *node index vs. X plots
	 */
	public static void NodeDistIndexM2() {
		int[] motifs = new int[4];
		for (int i = 0; i < 4; i++){
			motifs[i] = 3;
		}
		Config.COLUMNS = 5;
		String result = "results/NodeDistIndexM2";
		File dir = new File(result);
		dir.mkdir();
		Random rand = new Random();
		AllNodeDistMotifAnalyzer mo = new AllNodeDistMotifAnalyzer(true, true, true, false);
		try {
			for (int i = 0; i < files.length; i++){
			Graph g = new UndirectedNLGraph(new EdgesOnlyReader(folder + files[i]));
			
				mo.analyzeMotifs(false, false, "results/NodeDistIndexM2/"+titles[i],
						g);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		Plot.plotIndexCount("results/NodeDistIndexM2/", titles, "CountDist", motifs);
	}
}
