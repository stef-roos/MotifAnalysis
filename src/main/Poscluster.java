package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class Poscluster {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        String[] prefix = {"ces", "deu", "eng", "est", "fra", "ind", "ita", "lit", "nld", "por", "rus", "slv"};
//        String[] names = {"Transitivity", "M1", "M2", "M3", "M4", "M5", "M6"};
//        String[] namesS = {"T", "M1", "M2", "M3", "M4", "M5", "M6"};
//        for (int i = 0; i < prefix.length; i++){
//        	String line = "\\multirow{7}{*}{"+prefix[i]+"}";
//        	for (int j = 0; j < names.length; j++){
//        		line = line + " & " + namesS[j];
//        		double[] res = compareCluster("classVerb/"+prefix[i]+".txt", 
//        				"results/unsupos/mostDist/"+prefix[i]+names[j]+".txt");
//        		for (int k = 0; k < res.length; k++){
//        			line = line + " & " + Math.round(res[k]*10000)/(double)10000;
//        		}
//        		res = compareCluster("classVerb/"+prefix[i]+".txt", 
//        				"results/unsupos/mostDist/"+prefix[i]+names[j]+"Log.txt");
//        		for (int k = 0; k < res.length; k++){
//        			line = line + " & " + Math.round(res[k]*10000)/(double)10000;
//        		}
//        		System.out.println(line + " \\\\");
//        		line = "";
//        	}
//        	System.out.println("\\hline");
//        }
//		for (int j = 1; j <= 7; j++){
//		for (int i = 0; i < prefix.length; i++){
//		  Clustering.oneColumn("results/unsupos/resultsSum/"+prefix[i]+".txt",
//				  "results/unsupos/clusteringOne/"+prefix[i]+names[j-1]+".txt", j);
//		}
//		for (int i = 0; i < prefix.length; i++){
//			  Clustering.oneColumn("results/unsupos/resultsSum/"+prefix[i]+"Log.txt",
//					  "results/unsupos/clusteringOne/"+prefix[i]+names[j-1]+"Log.txt", j);
//			}
//		}
		
        for (int i = 0; i < prefix.length; i++){
		   Clustering.kMeans("results/unsupos/resultsSum/"+prefix[i]+".txt", 
				   "results/unsupos/cluster/"+prefix[i]+"All.txt", new int[]{0,1,2,3,4,5,6,7}, 1000);
        }
        for (int i = 0; i < prefix.length; i++){
 		   Clustering.kMeansLog("results/unsupos/resultsSum/"+prefix[i]+".txt", 
 				   "results/unsupos/cluster/"+prefix[i]+"AllLog.txt", new int[]{0,1,2,3,4,5,6,7}, 1000);
         }
        for (int i = 0; i < prefix.length; i++){
 		   Clustering.kMeans("results/unsupos/resultsSum/"+prefix[i]+".txt", 
 				   "results/unsupos/cluster/"+prefix[i]+"Motif.txt", new int[]{0,2,3,4,5,6,7}, 1000);
         }
         for (int i = 0; i < prefix.length; i++){
  		   Clustering.kMeansLog("results/unsupos/resultsSum/"+prefix[i]+".txt", 
  				   "results/unsupos/cluster/"+prefix[i]+"MotifLog.txt", new int[]{0,2,3,4,5,6,7}, 1000);
          }
         for (int i = 0; i < prefix.length; i++){
   		   Clustering.kMeans("results/unsupos/resultsSum/"+prefix[i]+".txt", 
   				   "results/unsupos/cluster/"+prefix[i]+"2-4.txt", new int[]{0,3,5}, 1000);
           }
           for (int i = 0; i < prefix.length; i++){
    		   Clustering.kMeansLog("results/unsupos/resultsSum/"+prefix[i]+".txt", 
    				   "results/unsupos/cluster/"+prefix[i]+"2-4Log.txt", new int[]{0,3,5}, 1000);
            }
           for (int i = 0; i < prefix.length; i++){
       		   Clustering.kMeans("results/unsupos/resultsSum/"+prefix[i]+".txt", 
       				   "results/unsupos/cluster/"+prefix[i]+"2-4-5-6.txt", new int[]{3,5,6,7}, 1000);
               }
               for (int i = 0; i < prefix.length; i++){
        		   Clustering.kMeansLog("results/unsupos/resultsSum/"+prefix[i]+".txt", 
        				   "results/unsupos/cluster/"+prefix[i]+"2-4-5-6Log.txt", new int[]{3,5,6,7}, 1000);
                } 
	}
	
	public static void makeOneFile(){
		//get languages
		String[] graphs = (new File("unsupos_graphs/")).list();
		HashMap<String, Vector<Integer>> map = new HashMap<String, Vector<Integer>>();
		for (int j = 0; j < graphs.length; j++){
			String prefix = graphs[j].split("3M")[0];
			Vector<Integer> vec = map.get(prefix);
			if (vec == null){
				vec = new Vector<Integer>();
				map.put(prefix, vec);
			}
			int number = Integer.parseInt(graphs[j].split("co_s.")[1]);
			vec.add(number);
		}
		
		String path = "results/unsupos/";
		String results = "results/unsupos/resultsSum/";
		(new File(results)).mkdir();
		//write result file per language
		Set<String> prefixes = map.keySet();
		Iterator<String> it = prefixes.iterator();
		while (it.hasNext()){
			String prefix = it.next();
			Vector<Integer> vec = map.get(prefix);
			String[] parts;
			String line;
			//write file
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(results+prefix + ".txt"));
				//iterate over graphs
				for (int i = 0; i < vec.size(); i++){
					String nextLine = vec.get(i) + " ";
					//get transitivity
					BufferedReader brT = new BufferedReader(new FileReader(path + "transitivity"+prefix+".txt"));
					brT.readLine();
					while ((line = brT.readLine()) != null){
						parts = line.split(":",2);
						if (Integer.parseInt(parts[0]) == vec.get(i)){
							nextLine = nextLine + parts[1].split("	")[4];
							break;
						}
					}
					brT.close();
					
					//get Motifs
					BufferedReader brM = new BufferedReader(new FileReader(path + prefix + "3M.co-s."+vec.get(i)+"Count.txt"));
					brM.readLine();
					while ((line = brM.readLine()) != null){
						parts = line.split(" ");
						if (parts.length > 1)
						nextLine = nextLine + " " + parts[2];
					}
					brM.close();
					bw.write(nextLine);
					bw.newLine();
				}
				bw.flush();
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}	
		
	}
	
	public static void makeOneFileLog(){
		//get languages
		String[] graphs = (new File("unsupos_graphs/")).list();
		HashMap<String, Vector<Integer>> map = new HashMap<String, Vector<Integer>>();
		for (int j = 0; j < graphs.length; j++){
			String prefix = graphs[j].split("3M")[0];
			Vector<Integer> vec = map.get(prefix);
			if (vec == null){
				vec = new Vector<Integer>();
				map.put(prefix, vec);
			}
			int number = Integer.parseInt(graphs[j].split("co_s.")[1]);
			vec.add(number);
		}
		
		String path = "results/unsupos/";
		String results = "results/unsupos/resultsSum/";
		(new File(results)).mkdir();
		//write result file per language
		Set<String> prefixes = map.keySet();
		Iterator<String> it = prefixes.iterator();
		while (it.hasNext()){
			String prefix = it.next();
			Vector<Integer> vec = map.get(prefix);
			String[] parts;
			String line;
			//write file
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(results+prefix + "Log.txt"));
				//iterate over graphs
				for (int i = 0; i < vec.size(); i++){
					String nextLine = vec.get(i) + " ";
					//get transitivity
					BufferedReader brT = new BufferedReader(new FileReader(path + "transitivity"+prefix+".txt"));
					brT.readLine();
					while ((line = brT.readLine()) != null){
						parts = line.split(":",2);
						if (Integer.parseInt(parts[0]) == vec.get(i)){
							nextLine = nextLine + Math.log(Double.parseDouble(parts[1].split("	")[4]));
							break;
						}
					}
					brT.close();
					
					//get Motifs
					BufferedReader brM = new BufferedReader(new FileReader(path + prefix + "3M.co-s."+vec.get(i)+"Count.txt"));
					brM.readLine();
					while ((line = brM.readLine()) != null){
						parts = line.split(" ");
						if (parts.length > 1)
						nextLine = nextLine + " " + Math.log(Double.parseDouble(parts[2]));
					}
					brM.close();
					bw.write(nextLine);
					bw.newLine();
				}
				bw.flush();
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}	
		
	}
	
	public static void getFiles(){
		//get languages
				String[] files = (new File("posclusters/")).list();
				String[] graphs = (new File("unsupos_graphs/")).list();
				HashMap<String, Vector<Integer>> map = new HashMap<String, Vector<Integer>>();
				for (int j = 0; j < graphs.length; j++){
					String prefix = graphs[j].split("3M")[0];
					Vector<Integer> vec = map.get(prefix);
					if (vec == null){
						vec = new Vector<Integer>();
						map.put(prefix, vec);
					}
					int number = Integer.parseInt(graphs[j].split("co_s.")[1]);
					vec.add(number);
				}
				for (int i = 0; i < files.length; i++){
					String prefix = files[i].split("_")[0];
					Vector<Integer> clusters = map.get(prefix);
					if (clusters == null) continue;
					System.out.println(prefix);
					try {
						BufferedWriter bw = new BufferedWriter(new FileWriter("posShort/"+files[i]));
						BufferedReader br = new BufferedReader(new FileReader("posclusters/"+files[i]));
						String line;
						while ((line = br.readLine()) != null){
							Integer number = Integer.parseInt(line.split("	")[0]);
							if (clusters.contains(number)){
								String line2 = "";
								String[] words = line.split(",");
								for (int w = 0; w < 10; w++){
									line2 = line2 + words[w] + ",";
								}
								bw.write(line2);
								bw.newLine();
							}
						}
						br.close();
						bw.flush();
						bw.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	}
	
	public static double[] compareCluster(String real, String res){
		try {
			BufferedReader brreal = new BufferedReader(new FileReader(real));
			String l1 = brreal.readLine();
			int[] cl1Real = makeArray(l1);
			String l2 = brreal.readLine();
			int[] cl2Real = makeArray(l2);
			brreal.close();
			
			BufferedReader bralgo = new BufferedReader(new FileReader(res));
			l1 = bralgo.readLine();
			int[] cl1Algo = makeArray(l1);
			l2 = bralgo.readLine();
			int[] cl2Algo = makeArray(l2);
			bralgo.close();
			
			if (cl1Real.length + cl2Real.length > cl1Algo.length + cl2Algo.length){
				throw new IllegalArgumentException("Unequal sets");
			}
			
			
			//compute accuracy: assumption c1 is verb
			int c1 = getContained(cl1Real,cl1Algo);
			int c2 = getContained(cl1Real,cl2Algo);
			int count = cl1Algo.length + cl2Algo.length;
			if (c2 > c1){
				int[] help = cl2Algo;
				cl2Algo = cl1Algo;
				cl1Algo = help;
			}
			int c = Math.max(c1, c2);
			//accuracy
			double acc = c + getContained(cl2Real,cl2Algo);
			acc = acc/(double)count;
			//precision and recall
			double pre = c/(double)cl1Algo.length;
			double recall = c/(double)cl1Real.length;
			
			return new double[] {acc, pre, recall};
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private static int[] makeArray(String line){
		String[] parts = line.split(";");
		int[] res = new int[parts.length];
		for (int i = 0; i < res.length; i++){
			res[i] = Integer.parseInt(parts[i]);
		}
		Arrays.sort(res);
		return res;
	}
	
	private static int getContained(int[] target, int[] value){
		int index = 0;
		int con = 0;
		for (int i = 0; i < value.length; i++){
			//System.out.println(value[i] + " " + index + " " + target[index]);
			while (index < target.length && target[index] < value[i]){
				index++; 
			}
			if (index == target.length) break;
			if (target[index] == value[i]){
				con++;
			} 
		}
		return con;
	}
	
	private static boolean sameSet(String fileA, String fileB){
		try{
		BufferedReader brreal = new BufferedReader(new FileReader(fileA));
		String l1 = brreal.readLine();
		int[] cl1Real = makeArray(l1);
		String l2 = brreal.readLine();
		int[] cl2Real = makeArray(l2);
		brreal.close();
		
		BufferedReader bralgo = new BufferedReader(new FileReader(fileB));
		l1 = bralgo.readLine();
		int[] cl1Algo = makeArray(l1);
		l2 = bralgo.readLine();
		int[] cl2Algo = makeArray(l2);
		bralgo.close();
		
		if (cl1Real.length + cl2Real.length != cl1Algo.length + cl2Algo.length){
			return false;
		}
		boolean same = true;
		for (int i = 0; i < cl1Algo.length; i++){
			if (!(Arrays.binarySearch(cl1Real, cl1Algo[i]) > -1 || Arrays.binarySearch(cl2Real, cl1Algo[i]) > -1)){
				same = false;
				return same;
			}
		}
		for (int i = 0; i < cl2Algo.length; i++){
			if (!(Arrays.binarySearch(cl1Real, cl2Algo[i]) > -1 || Arrays.binarySearch(cl2Real, cl2Algo[i]) > -1)){
				same = false;
				return same;
			}
		}
		return same;
		} catch (IOException e){
			e.printStackTrace();
		}
		return false;
	}

}
