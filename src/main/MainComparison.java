package main;

import io.graph.EdgesOnlyReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

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
		int[] counts;

	}
	
	private static void processFiles(String graphFile1, String graphFile2, String edgeFile1, String edgeFile2){
		try {
			Graph a = new UndirectedNLGraph(new EdgesOnlyReader(graphFile1));
			Graph b = new UndirectedNLGraph(new EdgesOnlyReader(graphFile2));
			HashMap<Integer,Integer> map = getMap(a,b,edgeFile1,edgeFile2);
			Node[] nodesA = a.getNodes();
			Node[] nodesB = b.getNodes();
			int[] count = new int[6];
			int[][] diff = new int[6][7];
			NodeRetrieverUndirected r = new NodeRetrieverUndirected(false, false, false,null,null,null);
			NodeRetrieverUndirected r2 = new NodeRetrieverUndirected(false, false, false,null,null,null);
			
			for (int i = 0; i < nodesA.length; i++){
				r.setNode(nodesA[i]);
				boolean duo = false;
				Integer index = map.get(i);
				if (index != null){
				  r2.setNode(nodesB[index]);
				  duo = true;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
