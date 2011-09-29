package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * static statistical methods
 * @author stefanie
 *
 */
public class Stats {
	
	/**
	 * write following to file:
	 * i counts[i] counts[i]/(sum_j counts[j]) CDF (i.e. sum_{k <= i} counts[k]/(sum_j counts[j]))
	 * @param file
	 * @param counts
	 */
	public static void writeDistFunc(String file, int[] counts){
		int count = 0;
		double[] perc = new double[counts.length];
		double[] f = new double[counts.length];
		for (int i = 0; i < counts.length; i++){
			count = count + counts[i];
		}
		for (int i = 0; i < counts.length; i++){
			perc[i] = (double)counts[i]/count;
			if (i > 0){
				f[i] = f[i-1] + perc[i];
			}else{
				f[i] = perc[i];
			}
		}
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		    bw.write("# total, percentage, cdf");
		    for (int i = 0; i < counts.length; i++){
		    	bw.newLine();
		    	bw.write(i + " " +counts[i] + " " + perc[i] + " " +f[i]);
		    }
		    bw.flush();
		    bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * write distribution function (accumulated and not) for values in array
	 * (bins if values are larger than bin)
	 * @param file
	 * @param array
	 */
	public static void makeDistFunc(String file, int[] array, int bin){
		Arrays.sort(array);
		int min = array[0];
		int max = array[array.length-1];
		int factor;
        if (max -min < bin){
        	factor = 1;
        } else {
        	factor = (max-min)/bin + 1;
        	max = max/factor;
        	min = min/factor;
        }
		int[] counts = new int[max-min+1];
		for (int i = 0; i < array.length; i++){
			counts[array[i]/factor-min]++;
		}
		int count = 0;
		double[] perc = new double[counts.length];
		double[] f = new double[counts.length];
		for (int i = 0; i < counts.length; i++){
			count = count + counts[i];
		}
		for (int i = 0; i < counts.length; i++){
			perc[i] = (double)counts[i]/count;
			if (i > 0){
				f[i] = f[i-1] + perc[i];
			}else{
				f[i] = perc[i];
			}
		}
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		    bw.write("# total, percentage, cdf");
		    for (int i = 0; i < counts.length; i++){
		    	bw.newLine();
		    	bw.write((i+min)*factor + " " +counts[i] + " " + perc[i] + " " +f[i]);
		    }
		    bw.flush();
		    bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void makeDistFunc(String file, double[] array, int bin){
		Arrays.sort(array);
		double min = array[0];
		double max = array[array.length-1];
		int factor;
        if (max -min < bin){
        	factor = 1;
        } else {
        	factor = (int)((max-min)/bin) + 1;
        	max = max/factor;
        	min = min/factor;
        }
        int up = (int) max;
        int down = (int)min;
		int[] counts = new int[up-down+1];
		for (int i = 0; i < array.length; i++){
			counts[(int)array[i]/factor-down]++;
		}
		int count = 0;
		double[] perc = new double[counts.length];
		double[] f = new double[counts.length];
		for (int i = 0; i < counts.length; i++){
			count = count + counts[i];
		}
		for (int i = 0; i < counts.length; i++){
			perc[i] = (double)counts[i]/count;
			if (i > 0){
				f[i] = f[i-1] + perc[i];
			}else{
				f[i] = perc[i];
			}
		}
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		    bw.write("# total, percentage, cdf");
		    for (int i = 0; i < counts.length; i++){
		    	bw.newLine();
		    	bw.write((i+min)*factor + " " +counts[i] + " " + perc[i] + " " +f[i]);
		    }
		    bw.flush();
		    bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
