package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Tests {
	
	public static void gnuplotAllPattern(String folder, String pattern, String gnuplot, String title){
		try{
		 BufferedWriter bw = new BufferedWriter(new FileWriter(gnuplot));
		 bw.write("reset"); bw.newLine();
		 bw.write("set terminal postscript eps color enhanced"); bw.newLine();
		 bw.write("set log y"); bw.newLine();
		 bw.write("set xlabel 'Motif'"); bw.newLine();
		 bw.write("set ylabel 'Fraction of Motif x'"); bw.newLine();
		 bw.write("set xrange [0.5:6.5]"); bw.newLine();
		 bw.newLine();
		 bw.write("set output '" + title + "'"); bw.newLine();
		 String[] files = (new File(folder)).list();
		 String plot = "plot ";
		 int c = 1;
		 for (int i = 0; i < files.length; i++){
			 if (!files[i].contains("Count.txt")) continue;
			 if (files[i].contains(pattern)){
				 plot = plot + "'"+files[i] + "' using 1:3 title '" + files[i].replace("Count.txt", "")
						 + "' with linespoints lt " + c + " pt " + c + " , ";
				 c++;
			 }
		 }
		 plot = plot.substring(0, plot.length()-2);
		 bw.write(plot);
		 bw.flush();
		 bw.close();
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public static void gnuploteachWith(String folder, String[] comparison, String gnuplot){
		try{
		 BufferedWriter bw = new BufferedWriter(new FileWriter(gnuplot));
		 bw.write("reset"); bw.newLine();
		 bw.write("set terminal postscript eps color enhanced"); bw.newLine();
		 bw.write("set log y"); bw.newLine();
		 bw.write("set xlabel 'Motif'"); bw.newLine();
		 bw.write("set ylabel 'Fraction of Motif x'"); bw.newLine();
		 bw.write("set xrange [0.5:6.5]"); bw.newLine();
		 bw.newLine();
		 
		 String[] files = (new File(folder)).list();
		 String plot = "plot ";
		 for (int j = 0; j < comparison.length; j++){
			 plot = plot + "'"+comparison[j] + "' using 1:3 title '" + comparison[j].replace("Count.txt", "")
					 + "' with linespoints, ";
		 }
		 for (int i = 0; i < files.length; i++){
			 if (!files[i].contains("Count.txt")) continue;
			 boolean found = false;
			 for (int j = 0; j < comparison.length; j++){
				 if (comparison[j].equals(files[i])){
					 found = true;
					 break;
				 }
			 }
			 if (!found){
				 String title = files[i].replace("Count.txt", "");
				 bw.write("set output '" + title + ".eps'"); bw.newLine();
				 bw.write(plot + "'"+files[i]+"' using 1:3 title '"+title + "' with linespoints");
				 bw.newLine(); bw.newLine();
			 }
		 }
		 
		 bw.flush();
		 bw.close();
		}catch (IOException e){
			e.printStackTrace();
		}
	}

}
