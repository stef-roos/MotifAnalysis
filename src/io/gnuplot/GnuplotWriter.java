package io.gnuplot;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;

import config.Config;

/**
 * write files for gnuplot + execute them
 * @author stefanie
 *
 */

public class GnuplotWriter extends BufferedWriter {

	public GnuplotWriter(Writer out) {
		super(out);
	}
	
	public GnuplotWriter(String file) throws IOException {
		super(new FileWriter(file));
	}
	
	/**
	 * setting parameters in header of gnuplot file
	 * @param terminal: terminal type, 
	 * @param lines: use predefined styles for lines
	 * @param logy: log scale y axis
	 * @param logx: log scale x axis
	 * @param xlabel
	 * @param ylabel
	 * @param yrange
	 * @throws IOException
	 */
	public void writeHeader(String terminal, boolean lines, boolean logy, boolean logx, 
			String xlabel, String ylabel, double[] yrange) throws IOException{
		this.write("reset");
		this.newLine();
		this.write("set terminal " + terminal);
		this.newLine();
		if (lines){
			String[] colors = {"\"red \"", "\"orange \"", "\"black \"", "\"green \"",
					"\"cyan \"", "\"blue \"", "\"violet \"", "\"brown \"", "\"gold \""};
			for (int i = 1; i < 4; i++){
				for (int j = 0; j < colors.length; j++){
					this.write("set style line "+((i-1)*colors.length + (j+1))+" lt "+ i + " lc rgb "+ 
				colors[j] + " lw " + i);
                    this.newLine();
				}
			}
//		 this.write("set style line 1 lt rgb \"red \" lw 3");
//		 this.newLine();
//		 this.write("set style line 2 lt rgb \"orange \" lw 3");
//		 this.newLine();
//		 this.write("set style line 3 lt rgb \"black \" lw 3");
//		 this.newLine();
//		 this.write("set style line 4 lt rgb \"green \" lw 3");
//		 this.newLine();
//		 this.write("set style line 5 lt rgb \"cyan \" lw 3");
//		 this.newLine();
//		 this.write("set style line 6 lt rgb \"blue \" lw 3");
//		 this.newLine();
//		 this.write("set style line 7 lt rgb \"violet \" lw 3");
//		 this.newLine();
//		 this.write("set style line 8 lt rgb \"brown \" lw 3");
//		 this.newLine();
//		 this.write("set style line 9 lt rgb \"gold \" lw 3");
//		 this.newLine();
		}
		if (xlabel != null){
		  this.write("set xlabel '" + xlabel + "'");
		  this.newLine();
		}
		if (ylabel != null){
		  this.write("set ylabel '" + ylabel + "'");
		  this.newLine();
		}
		if (logy){
			this.write("set log y");
			this.newLine();	
		}
		if (logx){
			this.write("set log x");
			this.newLine();	
		}
		if (yrange != null){
			this.write("set yrange ["+yrange[0] + ":" + yrange[1]+"]");
			this.newLine();
		}
		this.write("set key bottom left");
		  this.newLine();
		this.newLine();
		this.newLine();
	}
	
	/**
	 *write headers with only part of the settings 
	 *
	 */
	
	public void writeHeader(String terminal, boolean lines,boolean log) throws IOException{
		this.writeHeader(terminal, lines, log, false, null, null, null);
	}
	
	public void writeHeader(String terminal, boolean lines,boolean log, double[] yrange) throws IOException{
		this.writeHeader(terminal, lines, log, false, null, null, yrange);
	}
	
	public void writeHeader(String terminal, boolean lines,boolean log, double[] yrange, String xlabel, String ylabel) throws IOException{
		this.writeHeader(terminal, lines, log, false, xlabel, ylabel,yrange);
	}
	
	public void writeHeader(String terminal, boolean lines,boolean log,  
			String xlabel, String ylabel) throws IOException{
		this.writeHeader(terminal, lines, log, false, xlabel, ylabel, null);
	}
	
	/**
	 *write files with terminal as defined in Config
	 */
	
	public void writeHeader(boolean lines, boolean logy, boolean logx, 
			String xlabel, String ylabel, double[] yrange) throws IOException{
		this.writeHeader(Config.GNUPLOT_TERMINAL, lines, logy, logx, xlabel, ylabel, yrange);
	}
	
	public void writeHeader( boolean lines,boolean logy) throws IOException{
		this.writeHeader(Config.GNUPLOT_TERMINAL, lines, logy, false, null, null, null);
	}
	
	public void writeHeader(boolean lines,boolean logy, double[] yrange) throws IOException{
		this.writeHeader(Config.GNUPLOT_TERMINAL, lines, logy, false, null, null, yrange);
	}
	
	public void writeHeader(boolean lines,boolean logy, double[] yrange, String xlabel, String ylabel) throws IOException{
		this.writeHeader(Config.GNUPLOT_TERMINAL, lines, logy, false, xlabel, ylabel,yrange);
	}
	
	public void writeHeader(boolean lines,boolean logy,String xlabel, String ylabel) throws IOException{
		this.writeHeader(Config.GNUPLOT_TERMINAL, lines, logy, false, xlabel, ylabel, null);
	}
	
	
	/**
	 * write gnuplot code for one plot
	 * @param output: output file
	 * @param plotTitle: title of the graphic
	 * @param files: files that should be plotted
	 * @param titles: titles for each file
	 * @param columns: columns of file that should be plotted
	 * @param type: plot type e.g. lines
	 * @param style: use predefined style (note that this requires lines to be set in writeHeader)
	 * @throws IOException
	 */
	public void writeOutput(String output, String plotTitle, String[] files, String[] titles, int[] columns, String type, boolean style) throws IOException{
		this.write("set output \""+output+"\"");
		this.newLine();
		this.newLine();
		this.write("set title  \""+plotTitle+"\"");
		this.newLine();
		String plot = "plot ";
		for (int j = 0; j < titles.length; j++){
			if (j > 0){
				plot = plot + ", ";
			}
			
			plot = plot + "'"+files[j]+"' using 1:"+columns[j]+" title "
			        + "'" + titles[j] + "' with " + type;
			if (style){
				plot = plot + " ls " + (j % 27 +1);
			}
		}
		this.write(plot);
		this.newLine();
		this.newLine();
	}
	
	@Override
	public void close()throws IOException{
		
			this.flush();
		
		super.close();
	}
	
	/**
	 * execute command in given environment
	 * @param command
	 * @param envp
	 * @return
	 */
	public static boolean execute(String command, String[] envp) {
		try {
			Process p = Runtime.getRuntime().exec(command, envp);
			InputStream errS = p.getErrorStream();
			InputStreamReader isr = new InputStreamReader(errS);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
            p.waitFor();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * execute a gnuplot code file using the settings in Config
	 * @param file
	 * @return
	 */
	public static boolean execute(String file) {
		try {
			Process p = Runtime.getRuntime().exec(Config.GNUPLOT_PATH + " "+ file, Config.GNUPLOT_ENVP);
			InputStream stderr = p.getErrorStream();
			InputStreamReader isr = new InputStreamReader(stderr);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
            p.waitFor();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}
}
