package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Clustering {
	
	
	public static void oneColumn(String file, String result, int column){
		double[][] vals = getColumns(file, new int[]{0,column});
		HashMap<Double,Double> map = makeHashMap(vals);
		double[] col = new double[vals.length];
		for (int i = 0; i < vals.length; i++){
			col[i] = vals[i][1];
		}
		Arrays.sort(col);
		double max = 0;
		int index = 0;
		for (int i = 1; i < col.length; i++){
			if (col[i]-col[i-1] > max){
				max = col[i]-col[i-1];
				index = i;
			}
		}
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(result));
			String line1 = "";
			for (int i = 0; i < index; i++){
				line1 = line1 + (int)Math.round(map.get(col[i])) + ";";
			}
			bw.write(line1);
			bw.newLine();
			String line2 = "";
			for (int i = index; i < col.length; i++){
				line2 = line2 + (int)Math.round(map.get(col[i])) + ";";
			}
			bw.write(line2);
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void kMeans(String file, String result, int[] columns, int maxIter){
		double[][] vals = getColumns(file, columns);
		//HashMap<Double,Double> map = makeHashMap(vals);
		//set centroids
		double[] c1 = new double[columns.length-1];
		double[] c2 = new double[columns.length-1];
		Random rand = new Random();
		for (int d = 0; d < c1.length; d++){
			double min = vals[0][d+1];
			double max = min;
			for (int j = 1; j < vals.length; j++){
				if (vals[j][d+1] > max){
					max = vals[j][d+1];
				}
				if (vals[j][d+1] < min){
					min = vals[j][d+1];
				}
			}
			c1[d] = min + rand.nextDouble()*(max-min);
			c2[d] = min + rand.nextDouble()*(max-min);
		}
		
		int step = 0;
		boolean[] cl = new boolean[vals.length];
		int s1 = 0;
		while (step < maxIter){
			//compute cluster for each data point
			boolean didchange = false;
			for (int i = 0; i < vals.length; i++){
				double dist1 = 0;
				double dist2 = 0;
				for (int d = 0; d < c1.length; d++){
					dist1 = dist1 + Math.pow(c1[d]-vals[i][d+1], 2);
					dist2 = dist2 + Math.pow(c2[d]-vals[i][d+1], 2);
				}
				if (dist2 < dist1){
					if (cl[i] == false) didchange = true; 
					cl[i] = true;
				} else {
					if (cl[i]) didchange = true; 
					cl[i] = false;
					s1++;
				}
			}
			int s2 = vals.length-s1;
			//check if termination
			if (!didchange && step > 0) break;
			
			//compute new centers
			for (int d = 0; d < c1.length; d++){
				c1[d] = 0;
				c2[d] = 0;
				for (int i = 0; i < cl.length; i++){
					if (cl[i]){
						c2[d] = c2[d] + vals[i][d+1];
					} else {
						c1[d] = c1[d]+ vals[i][d+1]; 
					}
				}
				c1[d]=c1[d]/(double)s1;
				c2[d]=c2[d]/(double)s2;
			}
			step++;
		}
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(result));
			String line1 = "";
			String line2 = "";
			for (int i = 0; i < vals.length; i++){
				if (cl[i]){
					line2 = line2 + (int)vals[i][0]+ ";";
				} else {
					line1 = line1 + (int)vals[i][0]+ ";";
				}
			}
			bw.write(line1);
			bw.newLine();
			bw.write(line2);
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void kMeansLog(String file, String result, int[] columns, int maxIter){
		double[][] vals = getColumns(file, columns);
		//HashMap<Double,Double> map = makeHashMap(vals);
		//set centroids
		for (int j = 1; j < vals[0].length; j++){
			for (int i = 0; i < vals.length; i++){
				vals[i][j] = Math.log(vals[i][j]);
			}
		}
		double[] c1 = new double[columns.length-1];
		double[] c2 = new double[columns.length-1];
		Random rand = new Random();
		for (int d = 0; d < c1.length; d++){
			double min = vals[0][d+1];
			double max = min;
			for (int j = 1; j < vals.length; j++){
				if (vals[j][d+1] > max){
					max = vals[j][d+1];
				}
				if (vals[j][d+1] < min){
					min = vals[j][d+1];
				}
			}
			c1[d] = min + rand.nextDouble()*(max-min);
			c2[d] = min + rand.nextDouble()*(max-min);
		}
		
		int step = 0;
		boolean[] cl = new boolean[vals.length];
		int s1 = 0;
		while (step < maxIter){
			//compute cluster for each data point
			boolean didchange = false;
			for (int i = 0; i < vals.length; i++){
				double dist1 = 0;
				double dist2 = 0;
				for (int d = 0; d < c1.length; d++){
					dist1 = dist1 + Math.pow(c1[d]-vals[i][d+1], 2);
					dist2 = dist2 + Math.pow(c2[d]-vals[i][d+1], 2);
				}
				if (dist2 < dist1){
					if (cl[i] == false) didchange = true; 
					cl[i] = true;
				} else {
					if (cl[i]) didchange = true; 
					cl[i] = false;
					s1++;
				}
			}
			int s2 = vals.length-s1;
			//check if termination
			if (!didchange && step > 0) break;
			
			//compute new centers
			for (int d = 0; d < c1.length; d++){
				c1[d] = 0;
				c2[d] = 0;
				for (int i = 0; i < cl.length; i++){
					if (cl[i]){
						c2[d] = c2[d] + vals[i][d+1];
					} else {
						c1[d] = c1[d]+ vals[i][d+1]; 
					}
				}
				c1[d]=c1[d]/(double)s1;
				c2[d]=c2[d]/(double)s2;
			}
			step++;
		}
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(result));
			String line1 = "";
			String line2 = "";
			for (int i = 0; i < vals.length; i++){
				if (cl[i]){
					line2 = line2 + (int)vals[i][0]+ ";";
				} else {
					line1 = line1 + (int)vals[i][0]+ ";";
				}
			}
			bw.write(line1);
			bw.newLine();
			bw.write(line2);
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static HashMap<Double,Double> makeHashMap(double[][] vals){
		HashMap<Double,Double> map = new HashMap<Double,Double>();
		for (int i = 0; i < vals.length; i++){
			map.put(vals[i][1], vals[i][0]);
		}
		return map;
	}
	
	
	public static double[][] getColumns(String file, int[] columns){
	   	try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			ArrayList<double[]> list = new ArrayList<double[]>();
			String line;
			while ((line = br.readLine()) != null){
				String[] parts = line.split(" ");
				double[] set = new double[columns.length];
				for (int i = 0; i < set.length; i++){
					set[i] = Double.parseDouble(parts[columns[i]]);
				}
				list.add(set);
			}
			br.close();
			double[][] re = new double[list.size()][];
			for (int i = 0; i < re.length; i++){
				re[i] = list.get(i);
			}
			//double[][] re = (double[][]) list.toArray();
			return re;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   	return null;
	}

}
