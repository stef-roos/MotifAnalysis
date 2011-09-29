package util;

import architecture.edge.Edge;
import architecture.edge.WeightedEdge;
import architecture.edge.WeightedEdgeDouble;

/**
 * static methods for expanding an array to desired size
 * @author stefanie
 *
 */
public class ArrayExpand {
	
	public static String[] expand(String[] array, int size) {
	    String[] temp = new String[size];
	    System.arraycopy(array, 0, temp, 0, array.length);
	    for(int j = array.length; j < size; j++)
	        temp[j] = "";
	    return temp;
	}
	
	public static int[] expand(int[] array, int size) {
	    int[] temp = new int[size];
	    System.arraycopy(array, 0, temp, 0, array.length);
	    for(int j = array.length; j < size; j++)
	        temp[j] = 0;
	    return temp;
	}
	
	public static double[] expand(double[] array, int size) {
	    double[] temp = new double[size];
	    System.arraycopy(array, 0, temp, 0, array.length);
	    for(int j = array.length; j < size; j++)
	        temp[j] = 0;
	    return temp;
	}
	
	public static Edge[] expand(Edge[] array, int size) {
	    Edge[] temp = new Edge[size];
	    System.arraycopy(array, 0, temp, 0, array.length);
	    return temp;
	}
	
	/**
	 * expand by one entry
	 * @param array
	 * @param index: index of new entry 
	 * @param weight: weight of edge
	 * @return
	 */
	public static Edge[] expand(Edge[] array, int index, int weight) {
	    Edge[] temp = new Edge[array.length+1];
	    System.arraycopy(array, 0, temp, 0, array.length);
	    temp[temp.length-1] = new WeightedEdge(index,weight);
	    return temp;
	}
	
	/**
	 * expand by one entry
	 * @param array
	 * @param index: index of new entry 
	 * @param weight: weight of edge
	 * @return
	 */
	public static Edge[] expand(Edge[] array, int index, double weight) {
	    Edge[] temp = new Edge[array.length+1];
	    System.arraycopy(array, 0, temp, 0, array.length);
	    temp[temp.length-1] = new WeightedEdgeDouble(index,weight);
	    return temp;
	}
	
	/**
	 * expand by one entry
	 * @param array
	 * @param index: index of new entry 
	 * @param weight: weight of edge
	 * @return
	 */
	public static Edge[] expand(Edge[] array, int index, boolean one) {
	    if (!one){
	    	return expand(array,index);
	    }
		Edge[] temp = new Edge[array.length+1];
	    System.arraycopy(array, 0, temp, 0, array.length);
	    temp[temp.length-1] = new Edge(index);
	    return temp;
	}
	
	

}
