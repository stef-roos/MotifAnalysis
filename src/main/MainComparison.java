package main;

import io.graph.EdgesOnlyReader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import motife.undirected.NodeRetrieverUndirected;
import architecture.graph.Graph;
import architecture.graph.UndirectedNLGraph;
import architecture.node.Node;

public class MainComparison {

	public static String DELIMINATOR = "	";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//int[] counts;
		System.out.println("Start " + new Date());
        processFiles("real/real/deu_news_norm.co_s.f2s30.edges", "2gram/2gram/deu_news_2gram_norm.co_s.f2s30.edges", "real/real/deu_news_norm.nodes",
        		"2gram/2gram/deu_news_2gram_norm.nodes", "NewsRealTo2gram.txt");
        System.out.println("Done " + new Date());
//		processFiles("testA", "testB", "testMapA",
//        		"testMapA", "test.txt");
	}
	
	private static void processFiles(String graphFile1, String graphFile2, String nodeFile1, String nodeFile2, String result){
		try {
			Graph a = new UndirectedNLGraph(new EdgesOnlyReader(graphFile1));
			Graph b = new UndirectedNLGraph(new EdgesOnlyReader(graphFile2));
			HashMap<Integer,Integer> map = getMap(a,b,nodeFile1,nodeFile2);
			Node[] nodesA = a.getNodes();
			Node[] nodesB = b.getNodes();
			long[] count = new long[6];
			long[][] diff = new long[6][7];
//			HashMap<Integer, String> checkMap = getSecondFile(a,nodeFile1);
//			HashMap<Integer, String> checkMap2 = getSecondFile(b,nodeFile2);
//			for (int i = 0; i < a.getNodes().length; i++){
//				String n = checkMap.get(i);
//				Integer image = map.get(i);
//				if (image != null){
//					if(image > b.getNodes().length-1){
//						System.out.println(checkMap2.get(image) + " " + n + " " + image + " " + b.getNodes().length);
//					}
//				} 
//			}
			NodeRetrieverUndirected r = new NodeRetrieverUndirected(false, false, false,null,null,null);
			NodeRetrieverUndirected r2 = new NodeRetrieverUndirected(false, false, false,null,null,null);
			Vector<Vector<int[]>> vec1,vec2;
			Vector<int[]> curVec;
			for (int i = 0; i < nodesA.length; i++){
				r.setNode(nodesA[i]);
				vec1 = new Vector<Vector<int[]>>();
				for (int m = 0; m < 6; m++){
					vec1.add(r.analyzeMotif(a, m+3));
					count[m] = count[m] + vec1.get(m).size();
				}
				Integer index = map.get(i);
				if (index != null){
					for (int m = 0; m < 6; m++){
						curVec = vec1.remove(0);
						vec1.add(rewriteArray(curVec,map));
					}
				  r2.setNode(nodesB[index]);
				  vec2 = new Vector<Vector<int[]>>();
				  for (int m = 0; m < 6; m++){
						vec2.add(r2.analyzeMotif(b, m+3));
				  }
				  for (int m = 0; m < 6; m++){
					  curVec = vec1.get(m);
					  for (int n = 0; n < curVec.size(); n++){
						  boolean found = false;
						  for (int p = 0; p < 6; p++){
							  if (containsIntArray(curVec.get(n),vec2.get(p))){
								  found = true;
								  diff[m][p]++;
								  break;
							  }
						  }
						  if (!found){
							  diff[m][6]++;
						  }
					  }
				  }
				} else {
					for (int m = 0; m < 6; m++){
						diff[m][6] = diff[m][6] + vec1.get(m).size();
					}
				}
				  
			}
			double[][] perc = new double[6][7];
			for (int m = 0; m < count.length; m++){
				count[m] = count[m]/4;
				for (int n = 0; n < 7; n++){
					diff[m][n] = diff[m][n]/4;
					perc[m][n] = (double)diff[m][n]/(double)count[m];
				}
			}
				
			BufferedWriter bw = new BufferedWriter(new FileWriter(result));
			for (int m = 0; m < count.length; m++){
				bw.write( (m+1) + " & " + count[m] + " \\\\");
				bw.newLine();
			}
			bw.newLine();
			
			String line = " ";
			for (int m = 0; m < 7; m++){
				line = line + " & " + (m+1);
			}
			line = line + " \\\\";
			bw.write(line);
			for (int m = 0; m < 6; m++){
				line = (m+1) +"";
				for (int n = 0; n < diff[m].length; n++){
					line = line + " & " + diff[m][n];
				}
				line = line + " \\\\";
				bw.newLine();
				bw.write(line);
			}
			
			bw.newLine(); bw.newLine();
			line = " ";
			for (int m = 0; m < 7; m++){
				line = line + " & " + (m+1);
			}
			line = line + " \\\\";
			bw.write(line);
			for (int m = 0; m < 6; m++){
				line = (m+1) +"";
				for (int n = 0; n < perc[m].length; n++){
					line = line + " & " + perc[m][n];
				}
				line = line + " \\\\";
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
	
	private static Vector<int[]> rewriteArray(Vector<int[]> arrays, HashMap<Integer,Integer> map){
		Vector<int[]> res = new Vector<int[]>(arrays.size());
		int[] cur;
		int[] old;
		Integer val;
		for (int i = 0; i < arrays.size(); i++){
			old = arrays.get(i);
			cur = new int[old.length];
			boolean found = true;
			for (int j = 0; j < old.length; j++){
				val = map.get(old[j]);
				
				if (val == null){
					found = false;
					break;
				} else {
					cur[j] = val;
				}
			}
			if (found){
				res.add(cur);
			}
		}
		return res;
	}
	
	private static boolean containsIntArray(int[] array, Vector<int[]> coll){
		int[] cur;
		for (int j = 0; j < coll.size(); j++){
			cur = coll.get(j);
			if (cur.length == array.length){
				boolean equals = true;
				for (int i = 0; i < array.length; i++){
					if (array[i] != cur[i]){
						equals = false;
						break;
					}
				}
				if (equals){
					return true;
				}
			}
		}
		return false;
	}
	
	private static HashMap<Integer,Integer> getMap(Graph a, Graph b,String fileA, String fileB){
		HashMap<Integer, Integer> map = new HashMap<Integer,Integer>();
		HashMap<Integer, String> intStr = getSecondFile(a,fileA);
		HashMap<String, Integer> strInt = getOneFile(b,fileB);
		for (int i = 0; i < a.getNodes().length; i++){
			Integer p = strInt.get(intStr.get(i));
			if (p != null){
			   map.put(i, p);
			}
		}
		return map;
	}
	
	private static HashMap<String, Integer> getOneFile(Graph g, String file){
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		HashMap<String, String> wordLabel = new HashMap<String, String>();
		HashMap<String, Integer> labelIndex = new HashMap<String, Integer>();
		Node[] nodes = g.getNodes();
		for (int i = 0; i < nodes.length; i++){
			labelIndex.put(nodes[i].getLabel(), nodes[i].getIndex());
		}
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null){
				String[] parts = line.split(DELIMINATOR);
				map.put(parts[1], labelIndex.get(parts[0]));
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	private static HashMap<Integer, String> getSecondFile(Graph g, String file){
		HashMap<Integer, String> indexWord = new HashMap<Integer, String>();
		HashMap<String, Integer> labelIndex = new HashMap<String, Integer>();
		Node[] nodes = g.getNodes();
		for (int i = 0; i < nodes.length; i++){
			labelIndex.put(nodes[i].getLabel(), nodes[i].getIndex());
		}
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null){
				String[] parts = line.split(DELIMINATOR);
				indexWord.put(labelIndex.get(parts[0]),parts[1]);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return indexWord;
	}

}
