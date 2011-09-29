package config;

/**
 * parameters used in programm
 * -Replace with the ones for your system/files !!!
 * @author stefanie
 *
 */

public class Config {
	//deliminator in edges-only representation of graphs
	public static String DELIMINATOR = "	";
	//columns relevant for input
	// 2 -> not weighted
	// 3 -> edge weights
	// 4 -> node weights
	// 5 -> edge + node weights
	public static int COLUMNS = 3;
	
	//type of edge weights integer?
	public static boolean EDGEWEIGHTTYPEINT = true; 
	
	//gnuplot: Path,environment, terminal, output format
	//Mac settings:
	//public static String GNUPLOT_PATH = "/usr/local/bin/gnuplot";
	//public static String[] GNUPLOT_ENVP = {"PATH=/usr/local/bin"};
	//Ubuntu 10.4 settings:
	public static String GNUPLOT_PATH = "/usr/bin/gnuplot";
	public static String[] GNUPLOT_ENVP = {"PATH=/usr/bin"};
	
	public static String GNUPLOT_TERMINAL = " postscript eps color enhanced";
	public static String GNUPLOT_FORMAT = ".eps";


}
