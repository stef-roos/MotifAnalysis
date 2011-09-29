package io.gnuplot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import config.Config;


/**
 * class for creating plots for concrete situations,
 * using terminal defined in Config
 * @author stefanie
 *
 */
public class Plot {
	
	/**
	 * plot Motif vs. Counts for each file in folder/titles
	 * @param folder
	 * @param titles
	 * @param res: name of result, assumed to be in folder; is title of plot as well
	 * @param log
	 */
	public static void plotCounts(String folder, String[] titles, String res, boolean log){
		try {
			GnuplotWriter write = new GnuplotWriter(new FileWriter(folder + res + "gnuplot.txt"));
		    write.writeHeader(true, log, "Motif", "Count");
		    String[] files = new String[titles.length];
		    int[] columns = new int[titles.length];
		    for (int i = 0; i < files.length; i++){
		    	files[i] = folder + titles[i] + "Count.txt";
		    	columns[i] = 2; 
		    }
		    write.writeOutput(folder+res+Config.GNUPLOT_FORMAT, res, files, titles, columns, "lines", true);
		    write.close();
		    GnuplotWriter.execute(folder + res + "gnuplot.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * plot Motif vs. accumulated edge weight for each file in folder/titles
	 * @param folder
	 * @param titles
	 * @param res: name of result, assumed to be in folder; is title of plot as well
	 * @param log
	 */
	public static void plotEdgeWeight(String folder, String[] titles, String res, boolean log){
		try {
			GnuplotWriter write = new GnuplotWriter(new FileWriter(folder + res + "gnuplot.txt"));
		    write.writeHeader(Config.GNUPLOT_TERMINAL, true, log, "Motif", "Weight");
		    String[] files = new String[titles.length];
		    int[] columns = new int[titles.length];
		    for (int i = 0; i < files.length; i++){
		    	files[i] = folder + titles[i] + "EdgeWeight.txt";
		    	columns[i] = 2; 
		    }
		    write.writeOutput(folder+res+Config.GNUPLOT_FORMAT, res, files, titles, columns, "lines", true);
		    write.close();
		    GnuplotWriter.execute(folder + res + "gnuplot.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * plot Motif vs. accumulated node weight for each file in folder/titles
	 * @param folder
	 * @param titles
	 * @param res: name of result, assumed to be in folder; is title of plot as well
	 * @param log
	 */
	public static void plotNodeWeight(String folder, String[] titles, String res, boolean log){
		try {
			GnuplotWriter write = new GnuplotWriter(new FileWriter(folder + res + "gnuplot.txt"));
			write.writeHeader(Config.GNUPLOT_TERMINAL, true, log, "Motif", "Weight");
		    String[] files = new String[titles.length];
		    int[] columns = new int[titles.length];
		    for (int i = 0; i < files.length; i++){
		    	files[i] = folder + titles[i] + "NodeWeight.txt";
		    	columns[i] = 2; 
		    }
		    write.writeOutput(folder+res+Config.GNUPLOT_FORMAT, res, files, titles, columns, "lines", true);
		    write.close();
		    GnuplotWriter.execute(folder + res + "gnuplot.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * plot Motif vs. edge weight/count for each file in folder/titles
	 * @param folder
	 * @param titles
	 * @param res: name of result, assumed to be in folder; is title of plot as well
	 * @param log
	 */
	public static void plotEdgeWeightCount(String folder, String[] titles, String res, boolean log){
		try {
			GnuplotWriter write = new GnuplotWriter(new FileWriter(folder + res + "gnuplot.txt"));
			write.writeHeader(Config.GNUPLOT_TERMINAL, true, log, "Motif", "Weight/Count");
		    String[] files = new String[titles.length];
		    int[] columns = new int[titles.length];
		    for (int i = 0; i < files.length; i++){
		    	files[i] = folder + titles[i] + "EdgeWeight.txt";
		    	columns[i] = 3; 
		    }
		    write.writeOutput(folder+res+Config.GNUPLOT_FORMAT, res, files, titles, columns, "lines", true);
		    write.close();
		    GnuplotWriter.execute(folder + res + "gnuplot.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * plot Motif vs. edge weight/(count*#EdgesMotif) for each file in folder/titles
	 * @param folder
	 * @param titles
	 * @param res: name of result, assumed to be in folder; is title of plot as well
	 * @param log
	 */
	public static void plotEdgeWeightEdge(String folder, String[] titles, String res, boolean log){
		try {
			GnuplotWriter write = new GnuplotWriter(new FileWriter(folder + res + "gnuplot.txt"));
			write.writeHeader(Config.GNUPLOT_TERMINAL, true, log, "Motif", "Weight/Edge");
		    String[] files = new String[titles.length];
		    int[] columns = new int[titles.length];
		    for (int i = 0; i < files.length; i++){
		    	files[i] = folder + titles[i] + "EdgeWeight.txt";
		    	columns[i] = 4; 
		    }
		    write.writeOutput(folder+res+Config.GNUPLOT_FORMAT, res, files, titles, columns, "lines", true);
		    write.close();
		    GnuplotWriter.execute(folder + res + "gnuplot.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * plot Motif vs. (edge weight Motif)/(edge weight all Motifs)  for each file in folder/titles
	 * @param folder
	 * @param titles
	 * @param res: name of result, assumed to be in folder; is title of plot as well
	 * @param log
	 */
	
	public static void plotEdgeWeightPerc(String folder, String[] titles, String res, boolean log){
		try {
			GnuplotWriter write = new GnuplotWriter(new FileWriter(folder + res + "gnuplot.txt"));
			if (log){
			    write.writeHeader(Config.GNUPLOT_TERMINAL, true, log,  "Motif", "Percentage");
			} else {
				write.writeHeader(Config.GNUPLOT_TERMINAL, true, log, new double[]{0,100}, "Motif", "Percentage");
			}
			String[] files = new String[titles.length];
		    int[] columns = new int[titles.length];
		    for (int i = 0; i < files.length; i++){
		    	files[i] = folder + titles[i] + "EdgeWeight.txt";
		    	columns[i] = 5; 
		    }
		    write.writeOutput(folder+res+Config.GNUPLOT_FORMAT, res, files, titles, columns, "lines", true);
		    write.close();
		    GnuplotWriter.execute(folder + res + "gnuplot.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * plot Motif vs. node weight/count for each file in folder/titles
	 * @param folder
	 * @param titles
	 * @param res: name of result, assumed to be in folder; is title of plot as well
	 * @param log
	 */
	public static void plotNodeWeightCount(String folder, String[] titles, String res, boolean log){
		try {
			GnuplotWriter write = new GnuplotWriter(new FileWriter(folder + res + "gnuplot.txt"));
			write.writeHeader(Config.GNUPLOT_TERMINAL, true, log, "Motif", "Weight/Count");
		    String[] files = new String[titles.length];
		    int[] columns = new int[titles.length];
		    for (int i = 0; i < files.length; i++){
		    	files[i] = folder + titles[i] + "NodeWeight.txt";
		    	columns[i] = 3; 
		    }
		    write.writeOutput(folder+res+Config.GNUPLOT_FORMAT, res, files, titles, columns, "lines", true);
		    write.close();
		    GnuplotWriter.execute(folder + res + "gnuplot.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * plot Motif vs. nodeweight/(count*#nodesMotif) for each file in folder/titles
	 * @param folder
	 * @param titles
	 * @param res: name of result, assumed to be in folder; is title of plot as well
	 * @param log
	 */
	public static void plotNodeWeightNode(String folder, String[] titles, String res, boolean log){
		try {
			GnuplotWriter write = new GnuplotWriter(new FileWriter(folder + res + "gnuplot.txt"));
			write.writeHeader(Config.GNUPLOT_TERMINAL, true, log, "Motif", "Weight/Node");
		    String[] files = new String[titles.length];
		    int[] columns = new int[titles.length];
		    for (int i = 0; i < files.length; i++){
		    	files[i] = folder + titles[i] + "NodeWeight.txt";
		    	columns[i] = 4; 
		    }
		    write.writeOutput(folder+res+Config.GNUPLOT_FORMAT, res, files, titles, columns, "lines", true);
		    write.close();
		    GnuplotWriter.execute(folder + res + "gnuplot.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * plot Motif vs. (node weight Motif)/(node weight all Motifs)  for each file in folder/titles
	 * @param folder
	 * @param titles
	 * @param res: name of result, assumed to be in folder; is title of plot as well
	 * @param log
	 */
	public static void plotNodeWeightPerc(String folder, String[] titles, String res, boolean log){
		try {
			GnuplotWriter write = new GnuplotWriter(new FileWriter(folder + res + "gnuplot.txt"));
			if (log){
			 write.writeHeader(Config.GNUPLOT_TERMINAL, true, log,  "Motif", "Percentage");
			} else {
				write.writeHeader(Config.GNUPLOT_TERMINAL, true, log, new double[]{0,100}, "Motif", "Percentage");	
			}
			 String[] files = new String[titles.length];
		    int[] columns = new int[titles.length];
		    for (int i = 0; i < files.length; i++){
		    	files[i] = folder + titles[i] + "NodeWeight.txt";
		    	columns[i] = 5; 
		    }
		    write.writeOutput(folder+res+Config.GNUPLOT_FORMAT, res, files, titles, columns, "lines", true);
		    write.close();
		    GnuplotWriter.execute(folder + res + "gnuplot.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * plot Motif vs. (count)/(count all Motifs)  for each file in folder+titles[i]
	 * @param folder
	 * @param titles
	 * @param res: name of result, assumed to be in folder; is title of plot as well
	 * @param log
	 */
	public static void plotCountPerc(String folder, String[] titles, String res, boolean log){
		try {
			GnuplotWriter write = new GnuplotWriter(new FileWriter(folder + res + "gnuplot.txt"));
			if (log){
				write.writeHeader(Config.GNUPLOT_TERMINAL, true, log,  "Motif", "Percentage");	
			}else {
			write.writeHeader(Config.GNUPLOT_TERMINAL, true, log, new double[]{0,100}, "Motif", "Percentage");
			}
			String[] files = new String[titles.length];
		    int[] columns = new int[titles.length];
		    for (int i = 0; i < files.length; i++){
		    	files[i] = folder + titles[i] + "Count.txt";
		    	columns[i] = 3; 
		    }
		    write.writeOutput(folder+res+Config.GNUPLOT_FORMAT, res, files, titles, columns, "lines", true);
		    write.close();
		    GnuplotWriter.execute(folder + res + "gnuplot.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * all plots corresponding to edge weights dealing with single metrics (e.g. metrics that are
	 * measured by one value per motif)
	 * @param folder
	 * @param titles
	 * @param res
	 * @param log
	 */
	public static void plotAllEdgeWeight(String folder, String[] titles, String res, boolean log){
		plotEdgeWeight(folder,titles,res,log);
		plotEdgeWeightCount(folder,titles,res+"PerCount", log);
		plotEdgeWeightEdge(folder,titles,res+"PerEdge",log);
		plotEdgeWeightPerc(folder,titles,res+"Percentage",log);
	}
	/**
	 * all plots corresponding to node weights dealing with single metrics (e.g. metrics that are
	 * measured by one value per motif)
	 * @param folder
	 * @param titles
	 * @param res
	 * @param log
	 */
	public static void plotAllNodeWeight(String folder, String[] titles, String res, boolean log){
		plotNodeWeight(folder,titles,res,log);
		plotNodeWeightCount(folder,titles,res+"PerCount", log);
		plotNodeWeightNode(folder,titles,res+"PerNode",log);
		plotNodeWeightPerc(folder,titles,res+"Percentage",log);
	}
	
	/**
	 * plot edge weight vs. #Motifs for each file 'folder + titles[i] + "EdgeWeightDistMotif"+motifs[i]+".txt";'
	 * @param folder
	 * @param titles
	 * @param res
	 * @param motifs
	 */
	public static void plotEdgeWeightDistCount(String folder, String[] titles, String res, int[] motifs){
		try {
			titles = titles.clone();
			GnuplotWriter write = new GnuplotWriter(new FileWriter(folder + res + "gnuplot.txt"));
		    write.writeHeader(Config.GNUPLOT_TERMINAL, true, false, "Weight", "#Motifs");
		    String[] files = new String[titles.length];
		    int[] columns = new int[titles.length];
		    for (int i = 0; i < files.length; i++){
		    	files[i] = folder + titles[i] + "EdgeWeightDistMotif"+motifs[i]+".txt";
		    	titles[i] = titles[i] + "Motif"+motifs[i];
		    	columns[i] = 2; 
		    }
		    write.writeOutput(folder+res+Config.GNUPLOT_FORMAT, res, files, titles, columns, "lines", true);
		    write.close();
		    GnuplotWriter.execute(folder + res + "gnuplot.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * plot edge weight vs. percentage of Motifs for each file 'folder + titles[i] + "EdgeWeightDistMotif"+motifs[i]+".txt";'
	 * @param folder
	 * @param titles
	 * @param res
	 * @param motifs
	 */
	
	public static void plotEdgeWeightDistPerc(String folder, String[] titles, String res, int[] motifs){
		try {
			titles = titles.clone();
			GnuplotWriter write = new GnuplotWriter(new FileWriter(folder + res + "gnuplot.txt"));
		    write.writeHeader(Config.GNUPLOT_TERMINAL, true, false, new double[] {0,1}, "Weight", "Percentage");
		    String[] files = new String[titles.length];
		    int[] columns = new int[titles.length];
		    for (int i = 0; i < files.length; i++){
		    	files[i] = folder + titles[i] + "EdgeWeightDistMotif"+motifs[i]+".txt";
		    	titles[i] = titles[i] + "Motif"+motifs[i];
		    	columns[i] = 3; 
		    }
		    write.writeOutput(folder+res+Config.GNUPLOT_FORMAT, res, files, titles, columns, "lines", true);
		    write.close();
		    GnuplotWriter.execute(folder + res + "gnuplot.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * plot  CDF  of edge weight for each file 'folder + titles[i] + "EdgeWeightDistMotif"+motifs[i]+".txt";'
	 * @param folder
	 * @param titles
	 * @param res
	 * @param motifs
	 */
	public static void plotEdgeWeightDistCDF(String folder, String[] titles, String res, int[] motifs){
		try {
			titles = titles.clone();
			GnuplotWriter write = new GnuplotWriter(new FileWriter(folder + res + "gnuplot.txt"));
		    write.writeHeader(Config.GNUPLOT_TERMINAL, true, false, new double[] {0,1}, "Weight", "CDF");
		    String[] files = new String[titles.length];
		    int[] columns = new int[titles.length];
		    for (int i = 0; i < files.length; i++){
		    	files[i] = folder + titles[i] + "EdgeWeightDistMotif"+motifs[i]+".txt";
		    	titles[i] = titles[i] + "Motif"+motifs[i];
		    	columns[i] = 4; 
		    }
		    write.writeOutput(folder+res+Config.GNUPLOT_FORMAT, res, files, titles, columns, "lines", true);
		    write.close();
		    GnuplotWriter.execute(folder + res + "gnuplot.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * plot node weight vs. #Motifs for each file 'folder + titles[i] + "NodeWeightDistMotif"+motifs[i]+".txt";'
	 * @param folder
	 * @param titles
	 * @param res
	 * @param motifs
	 */
	public static void plotNodeWeightDistCount(String folder, String[] titles, String res, int[] motifs){
		try {
			titles = titles.clone();
			GnuplotWriter write = new GnuplotWriter(new FileWriter(folder + res + "gnuplot.txt"));
		    write.writeHeader(Config.GNUPLOT_TERMINAL, true, false, "Weight", "#Motifs");
		    String[] files = new String[titles.length];
		    int[] columns = new int[titles.length];
		    for (int i = 0; i < files.length; i++){
		    	files[i] = folder + titles[i] + "NodeWeightDistMotif"+motifs[i]+".txt";
		    	titles[i] = titles[i] + "Motif"+motifs[i];
		    	columns[i] = 2; 
		    }
		    write.writeOutput(folder+res+Config.GNUPLOT_FORMAT, res, files, titles, columns, "lines", true);
		    write.close();
		    GnuplotWriter.execute(folder + res + "gnuplot.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * plot node weight vs. percentage Motifs for each file 'folder + titles[i] + "NodeWeightDistMotif"+motifs[i]+".txt";'
	 * @param folder
	 * @param titles
	 * @param res
	 * @param motifs
	 */
	
	public static void plotNodeWeightDistPerc(String folder, String[] titles, String res, int[] motifs){
		try {
			titles = titles.clone();
			GnuplotWriter write = new GnuplotWriter(new FileWriter(folder + res + "gnuplot.txt"));
		    write.writeHeader(Config.GNUPLOT_TERMINAL, true, false, new double[] {0,1}, "Weight", "Percentage");
		    String[] files = new String[titles.length];
		    int[] columns = new int[titles.length];
		    for (int i = 0; i < files.length; i++){
		    	files[i] = folder + titles[i] + "NodeWeightDistMotif"+motifs[i]+".txt";
		    	titles[i] = titles[i] + "Motif"+motifs[i];
		    	columns[i] = 3; 
		    }
		    write.writeOutput(folder+res+Config.GNUPLOT_FORMAT, res, files, titles, columns, "lines", true);
		    write.close();
		    GnuplotWriter.execute(folder + res + "gnuplot.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * plot CDF of node weight for each file 'folder + titles[i] + "NodeWeightDistMotif"+motifs[i]+".txt";'
	 * @param folder
	 * @param titles
	 * @param res
	 * @param motifs
	 */
	public static void plotNodeWeightDistCDF(String folder, String[] titles, String res, int[] motifs){
		try {
			titles = titles.clone();
			GnuplotWriter write = new GnuplotWriter(new FileWriter(folder + res + "gnuplot.txt"));
		    write.writeHeader(Config.GNUPLOT_TERMINAL, true, false, new double[] {0,1}, "Weight", "CDF");
		    String[] files = new String[titles.length];
		    int[] columns = new int[titles.length];
		    for (int i = 0; i < files.length; i++){
		    	files[i] = folder + titles[i] + "NodeWeightDistMotif"+motifs[i]+".txt";
		    	titles[i] = titles[i] + "Motif"+motifs[i];
		    	columns[i] = 4; 
		    }
		    write.writeOutput(folder+res+Config.GNUPLOT_FORMAT, res, files, titles, columns, "lines", true);
		    write.close();
		    GnuplotWriter.execute(folder + res + "gnuplot.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * plot node weight of Motif per node vs. #Nodes for each file folder + titles[i] + "NodeNodeDistMotif"+motifs[i]+".txt";
	 * @param folder
	 * @param titles
	 * @param res
	 * @param motifs
	 */
	public static void plotNodeNodeDistCount(String folder, String[] titles, String res, int[] motifs){
		try {
			titles = titles.clone();
			GnuplotWriter write = new GnuplotWriter(new FileWriter(folder + res + "gnuplot.txt"));
		    write.writeHeader(Config.GNUPLOT_TERMINAL, true, false, "Weight", "#Motifs");
		    String[] files = new String[titles.length];
		    int[] columns = new int[titles.length];
		    for (int i = 0; i < files.length; i++){
		    	files[i] = folder + titles[i] + "NodeNodeDistMotif"+motifs[i]+".txt";
		    	titles[i] = titles[i] + "Motif"+motifs[i];
		    	columns[i] = 2; 
		    }
		    write.writeOutput(folder+res+Config.GNUPLOT_FORMAT, res, files, titles, columns, "points", false);
		    write.close();
		    GnuplotWriter.execute(folder + res + "gnuplot.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * plot node weight of Motif per node vs. percentage Nodes for each file folder + titles[i] + "NodeNodeDistMotif"+motifs[i]+".txt";
	 * @param folder
	 * @param titles
	 * @param res
	 * @param motifs
	 */
	public static void plotNodeNodeDistPerc(String folder, String[] titles, String res, int[] motifs){
		try {
			titles = titles.clone();
			GnuplotWriter write = new GnuplotWriter(new FileWriter(folder + res + "gnuplot.txt"));
		    write.writeHeader(Config.GNUPLOT_TERMINAL, true, false, new double[] {0,1}, "Weight", "Percentage");
		    String[] files = new String[titles.length];
		    int[] columns = new int[titles.length];
		    for (int i = 0; i < files.length; i++){
		    	files[i] = folder + titles[i] + "NodeNodeDistMotif"+motifs[i]+".txt";
		    	titles[i] = titles[i] + "Motif"+motifs[i];
		    	columns[i] = 3; 
		    }
		    write.writeOutput(folder+res+Config.GNUPLOT_FORMAT, res, files, titles, columns, "points", false);
		    write.close();
		    GnuplotWriter.execute(folder + res + "gnuplot.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * plot CDF of node weight of Motif per node  for each file folder + titles[i] + "NodeNodeDistMotif"+motifs[i]+".txt";
	 * @param folder
	 * @param titles
	 * @param res
	 * @param motifs
	 */
	public static void plotNodeNodeDistCDF(String folder, String[] titles, String res, int[] motifs){
		try {
			titles = titles.clone();
			GnuplotWriter write = new GnuplotWriter(new FileWriter(folder + res + "gnuplot.txt"));
		    write.writeHeader(Config.GNUPLOT_TERMINAL, true, false, new double[] {0,1}, "Weight", "CDF");
		    String[] files = new String[titles.length];
		    int[] columns = new int[titles.length];
		    for (int i = 0; i < files.length; i++){
		    	files[i] = folder + titles[i] + "NodeNodeDistMotif"+motifs[i]+".txt";
		    	titles[i] = titles[i] + "Motif"+motifs[i];
		    	columns[i] = 4; 
		    }
		    write.writeOutput(folder+res+Config.GNUPLOT_FORMAT, res, files, titles, columns, "lines", true);
		    write.close();
		    GnuplotWriter.execute(folder + res + "gnuplot.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * plot edge weight of Motif per node vs. #Nodes for each file folder + titles[i] + "NodeEdgeDistMotif"+motifs[i]+".txt";
	 * @param folder
	 * @param titles
	 * @param res
	 * @param motifs
	 */
	public static void plotNodeEdgeDistCount(String folder, String[] titles, String res, int[] motifs){
		try {
			titles = titles.clone();
			GnuplotWriter write = new GnuplotWriter(new FileWriter(folder + res + "gnuplot.txt"));
		    write.writeHeader(Config.GNUPLOT_TERMINAL, true, false, "Weight", "#Motifs");
		    String[] files = new String[titles.length];
		    int[] columns = new int[titles.length];
		    for (int i = 0; i < files.length; i++){
		    	files[i] = folder + titles[i] + "NodeEdgeDistMotif"+motifs[i]+".txt";
		    	titles[i] = titles[i] + "Motif"+motifs[i];
		    	columns[i] = 2; 
		    }
		    write.writeOutput(folder+res+Config.GNUPLOT_FORMAT, res, files, titles, columns, "points", false);
		    write.close();
		    GnuplotWriter.execute(folder + res + "gnuplot.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * plot edge weight of Motif per node vs. percentage Nodes for each file folder + titles[i] + "NodeEdgeDistMotif"+motifs[i]+".txt";
	 * @param folder
	 * @param titles
	 * @param res
	 * @param motifs
	 */
	public static void plotNodeEdgeDistPerc(String folder, String[] titles, String res, int[] motifs){
		try {
			titles = titles.clone();
			GnuplotWriter write = new GnuplotWriter(new FileWriter(folder + res + "gnuplot.txt"));
		    write.writeHeader(Config.GNUPLOT_TERMINAL, true, false, new double[] {0,1}, "Weight", "Percentage");
		    String[] files = new String[titles.length];
		    int[] columns = new int[titles.length];
		    for (int i = 0; i < files.length; i++){
		    	files[i] = folder + titles[i] + "NodeEdgeDistMotif"+motifs[i]+".txt";
		    	titles[i] = titles[i] + "Motif"+motifs[i];
		    	columns[i] = 3; 
		    }
		    write.writeOutput(folder+res+Config.GNUPLOT_FORMAT, res, files, titles, columns, "points", false);
		    write.close();
		    GnuplotWriter.execute(folder + res + "gnuplot.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * plot CDF of edge weight of Motif per node  for each file folder + titles[i] + "NodeEdgeDistMotif"+motifs[i]+".txt";
	 * @param folder
	 * @param titles
	 * @param res
	 * @param motifs
	 */
	public static void plotNodeEdgeDistCDF(String folder, String[] titles, String res, int[] motifs){
		try {
			titles = titles.clone();
			GnuplotWriter write = new GnuplotWriter(new FileWriter(folder + res + "gnuplot.txt"));
		    write.writeHeader(Config.GNUPLOT_TERMINAL, true, false, new double[] {0,1}, "Weight", "CDF");
		    String[] files = new String[titles.length];
		    int[] columns = new int[titles.length];
		    for (int i = 0; i < files.length; i++){
		    	files[i] = folder + titles[i] + "NodeEdgeDistMotif"+motifs[i]+".txt";
		    	titles[i] = titles[i] + "Motif"+motifs[i];
		    	columns[i] = 4; 
		    }
		    write.writeOutput(folder+res+Config.GNUPLOT_FORMAT, res, files, titles, columns, "lines", true);
		    write.close();
		    GnuplotWriter.execute(folder + res + "gnuplot.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * plot count of Motif per node vs. #Nodes for each file folder + titles[i] + "NodeDistMotif"+motifs[i]+".txt";
	 * @param folder
	 * @param titles
	 * @param res
	 * @param motifs
	 */
	public static void plotNodeDistCount(String folder, String[] titles, String res, int[] motifs){
		try {
			titles = titles.clone();
			GnuplotWriter write = new GnuplotWriter(new FileWriter(folder + res + "gnuplot.txt"));
		    write.writeHeader(Config.GNUPLOT_TERMINAL, true, false, "Weight", "#Motifs");
		    String[] files = new String[titles.length];
		    int[] columns = new int[titles.length];
		    for (int i = 0; i < files.length; i++){
		    	files[i] = folder + titles[i] + "NodeDistMotif"+motifs[i]+".txt";
		    	titles[i] = titles[i] + "Motif"+motifs[i];
		    	columns[i] = 2; 
		    }
		    write.writeOutput(folder+res+Config.GNUPLOT_FORMAT, res, files, titles, columns, "points", false);
		    write.close();
		    GnuplotWriter.execute(folder + res + "gnuplot.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * plot count of Motif per node vs. percentage Nodes for each file folder + titles[i] + "NodeDistMotif"+motifs[i]+".txt";
	 * @param folder
	 * @param titles
	 * @param res
	 * @param motifs
	 */
	public static void plotNodeDistPerc(String folder, String[] titles, String res, int[] motifs){
		try {
			titles = titles.clone();
			GnuplotWriter write = new GnuplotWriter(new FileWriter(folder + res + "gnuplot.txt"));
		    write.writeHeader(Config.GNUPLOT_TERMINAL, true, false, new double[] {0,1}, "Weight", "Percentage");
		    String[] files = new String[titles.length];
		    int[] columns = new int[titles.length];
		    for (int i = 0; i < files.length; i++){
		    	files[i] = folder + titles[i] + "NodeDistMotif"+motifs[i]+".txt";
		    	titles[i] = titles[i] + "Motif"+motifs[i];
		    	columns[i] = 3; 
		    }
		    write.writeOutput(folder+res+Config.GNUPLOT_FORMAT, res, files, titles, columns, "points", false);
		    write.close();
		    GnuplotWriter.execute(folder + res + "gnuplot.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * plot CDF of count per node for each file folder + titles[i] + "NodeDistMotif"+motifs[i]+".txt";
	 * @param folder
	 * @param titles
	 * @param res
	 * @param motifs
	 */
	public static void plotNodeDistCDF(String folder, String[] titles, String res, int[] motifs){
		try {
			titles = titles.clone();
			GnuplotWriter write = new GnuplotWriter(new FileWriter(folder + res + "gnuplot.txt"));
		    write.writeHeader(Config.GNUPLOT_TERMINAL, true, false, new double[] {0,1}, "Count", "CDF");
		    String[] files = new String[titles.length];
		    int[] columns = new int[titles.length];
		    for (int i = 0; i < files.length; i++){
		    	files[i] = folder + titles[i] + "NodeDistMotif"+motifs[i]+".txt";
		    	titles[i] = titles[i] + "Motif"+motifs[i];
		    	columns[i] = 4; 
		    }
		    write.writeOutput(folder+res+Config.GNUPLOT_FORMAT, res, files, titles, columns, "lines", true);
		    write.close();
		    GnuplotWriter.execute(folder + res + "gnuplot.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * plot Index Node vs percentage for motif in columns[i] each file folder + titles[i] +IndexCountPerc.txt;
	 * @param folder
	 * @param titles
	 * @param res
	 * @param columns of the file to use: e.g. all 2, plot for Motif 1
	 */
	public static void plotIndexCountPerc(String folder, String[] titles, String res, int[] columns){
		try {
			titles = titles.clone();
			GnuplotWriter write = new GnuplotWriter(new FileWriter(folder + res + "gnuplot.txt"));
		    write.writeHeader(Config.GNUPLOT_TERMINAL, true, false, new double[] {0,1}, "Node", "percentage");
		    String[] files = new String[titles.length];
		    for (int i = 0; i < files.length; i++){
		    	files[i] = folder + titles[i] + "IndexCountPerc.txt"; 
		    	titles[i] = titles[i] + "Motif"+(columns[i]-1);
		    }
		    write.writeOutput(folder+res+Config.GNUPLOT_FORMAT, res, files, titles, columns, "points", false);
		    write.close();
		    GnuplotWriter.execute(folder + res + "gnuplot.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * plot Index Node  for each file folder + titles[i] +IndexCount.txt;
	 * @param folder
	 * @param titles
	 * @param res
	 * @param columns of the file to use: e.g. all 2, plot for Motif 1
	 */
	public static void plotIndexCount(String folder, String[] titles, String res, int[] columns){
		try {
			titles = titles.clone();
			GnuplotWriter write = new GnuplotWriter(new FileWriter(folder + res + "gnuplot.txt"));
		    write.writeHeader(Config.GNUPLOT_TERMINAL, true, false,  "Node", "Count");
		    String[] files = new String[titles.length];
		    for (int i = 0; i < files.length; i++){
		    	files[i] = folder + titles[i] + "IndexCount.txt"; 
		    	titles[i] = titles[i] + "Motif"+(columns[i]-1);
		    }
		    write.writeOutput(folder+res+Config.GNUPLOT_FORMAT, res, files, titles, columns, "points", false);
		    write.close();
		    GnuplotWriter.execute(folder + res + "gnuplot.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * plot Index Node vs percentage edge weight for motif in columns[i] each file folder + titles[i] +IndexEdgeWeightPerc.txt;
	 * @param folder
	 * @param titles
	 * @param res
	 * @param columns of the file to use: e.g. all 2, plot for Motif 1
	 */
	public static void plotIndexEdgeWeightPerc(String folder, String[] titles, String res, int[] columns){
		try {
			titles = titles.clone();
			GnuplotWriter write = new GnuplotWriter(new FileWriter(folder + res + "gnuplot.txt"));
		    write.writeHeader(Config.GNUPLOT_TERMINAL, true, false, new double[] {0,1}, "Node", "percentage");
		    String[] files = new String[titles.length];
		    for (int i = 0; i < files.length; i++){
		    	files[i] = folder + titles[i] + "IndexEdgeWeightPerc.txt"; 
		    	titles[i] = titles[i] + "Motif"+(columns[i]-1);
		    }
		    write.writeOutput(folder+res+Config.GNUPLOT_FORMAT, res, files, titles, columns, "points", false);
		    write.close();
		    GnuplotWriter.execute(folder + res + "gnuplot.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * plot Index Node vs edge weight for each file folder + titles[i] +IndexEdgeWeight.txt;
	 * @param folder
	 * @param titles
	 * @param res
	 * @param columns of the file to use: e.g. all 2, plot for Motif 1
	 */
	public static void plotIndexEdgeWeight(String folder, String[] titles, String res, int[] columns){
		try {
			titles = titles.clone();
			GnuplotWriter write = new GnuplotWriter(new FileWriter(folder + res + "gnuplot.txt"));
		    write.writeHeader(Config.GNUPLOT_TERMINAL, true, false,  "Node", "Weight");
		    String[] files = new String[titles.length];
		    for (int i = 0; i < files.length; i++){
		    	files[i] = folder + titles[i] + "IndexEdgeWeight.txt"; 
		    	titles[i] = titles[i] + "Motif"+(columns[i]-1);
		    }
		    write.writeOutput(folder+res+Config.GNUPLOT_FORMAT, res, files, titles, columns, "points", false);
		    write.close();
		    GnuplotWriter.execute(folder + res + "gnuplot.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * plot Index Node vs percentage edge weight for motif in columns[i] each file folder + titles[i] +IndexNodeWeightPerc.txt;
	 * @param folder
	 * @param titles
	 * @param res
	 * @param columns of the file to use: e.g. all 2, plot for Motif 1
	 */
	public static void plotIndexNodeWeightPerc(String folder, String[] titles, String res, int[] columns){
		try {
			titles = titles.clone();
			GnuplotWriter write = new GnuplotWriter(new FileWriter(folder + res + "gnuplot.txt"));
		    write.writeHeader(Config.GNUPLOT_TERMINAL, true, false, new double[] {0,1}, "Node", "percentage");
		    String[] files = new String[titles.length];
		    for (int i = 0; i < files.length; i++){
		    	files[i] = folder + titles[i] + "IndexNodeWeightPerc.txt"; 
		    	titles[i] = titles[i] + "Motif"+(columns[i]-1);
		    }
		    write.writeOutput(folder+res+Config.GNUPLOT_FORMAT, res, files, titles, columns, "points", false);
		    write.close();
		    GnuplotWriter.execute(folder + res + "gnuplot.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * plot Index Node vs percentage edge weight for motif in columns[i] each file folder + titles[i] +IndexNodeWeightPerc.txt;
	 * @param folder
	 * @param titles
	 * @param res
	 * @param columns of the file to use: e.g. all 2, plot for Motif 1
	 */
	public static void plotIndexNodeWeight(String folder, String[] titles, String res, int[] columns){
		try {
			titles = titles.clone();
			GnuplotWriter write = new GnuplotWriter(new FileWriter(folder + res + "gnuplot.txt"));
		    write.writeHeader(Config.GNUPLOT_TERMINAL, false, false,  "Node", "Weight");
		    String[] files = new String[titles.length];
		    for (int i = 0; i < files.length; i++){
		    	files[i] = folder + titles[i] + "IndexNodeWeight.txt"; 
		    	titles[i] = titles[i] + "Motif"+(columns[i]-1);
		    }
		    write.writeOutput(folder+res+Config.GNUPLOT_FORMAT, res, files, titles, columns, "points", false);
		    write.close();
		    GnuplotWriter.execute(folder + res + "gnuplot.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
