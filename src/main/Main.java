package main;

import io.gnuplot.GnuplotWriter;
import io.gnuplot.Plot;
import io.graph.EdgesOnlyReader;
import io.graph.GTNAWriter;
import io.graph.GraphReader;
import io.graph.GraphWriter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import motife.MotifAnalyzer;
import motife.directed3.Directed3Analyzer;
import motife.undirected.FourChainMotifAnalyzer;
import motife.undirected.FourUndirectedMotifAnalyzer;
import architecture.graph.DirectedNLGraph;
import architecture.graph.Graph;
import architecture.graph.UndirectedNLGraph;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//mainGTNAGraph("words","data/POS_3lang_try3/", "../GTNA/POS_3lang_try3/");
		//mainNoEdges("unsupos", "unsupos_graphs/", args[0]);
		
		//plot all files in one
		Tests.gnuplotAllPattern("results/unsupos/", "Count.txt", "results/unsupos/gnuall", "All.eps");
		
		//plot by language 
		String[] langs = {"ces3M", "deu3M", "eng3M", "est3M", "fra3M", "ind3M", "ita3M","lit3M", "nld3M",
				"por3M", "rus3M", "slv3M"};
		for (int i = 0; i < langs.length; i++){
			Tests.gnuplotAllPattern("results/unsupos/", langs[i], "results/unsupos/gnu"+langs[i], langs[i]+".eps");
		}
		
		//plot each with verbs
        Tests.gnuploteachWith("results/unsupos/", new String[]{"de1M-POS.co-s.VERB.f2s30Count.txt",
        		"en1M-POS.co-s.VERB.f2s30Count.txt", "fr1M-POS.co-s.VERB.f2s30Count.txt"}, "results/unsupos/gnueach");

	}
	
	
	
	public static void rewriteOverlap(){
		//slen10-10.co_s.f2s10
				String title = "size";
				String[] names = {"s10", "s30"};
				int count = 0;
				for (int n = 0; n < names.length; n++){
					(new File("results/"+title+names[n])).mkdir();
				}
				String model = "real";
				String replace = "2gram";
				//String[] modelFiles = new String[names.length];
				String end = "Count.txt";
				String path = "graphs/size_comp/";
				String[] files = (new File(path)).list();
				for (int k = 0; k < names.length; k++){
					count = 0;
				for (int j = 0; j < files.length; j++){
					if (files[j].contains("edges") && files[j].contains(model) && files[j].contains(names[k])){
						count++;
					}
				}
				String[] pathNull = new String[count];
				String[] compared = new String[count];
				String[] resFiles = new String[count];
				String[] titles = new String[count];
				int c = 0;
				for (int i = 0;i< files.length; i++){
					if (files[i].contains("edges") && files[i].contains(model) && files[i].contains(names[k])){
					    String name = files[i].replace(".edges", "").replace("_", "-");
					    System.out.println("Start " + name);
					    pathNull[c] = "results/"+title+ names[k]+"/"+name + end;
				try{
				GraphReader read = new EdgesOnlyReader(path+files[i]);
				Graph g = new UndirectedNLGraph(read);
				MotifAnalyzer mo = new FourUndirectedMotifAnalyzer(true,false);
				mo.analyzeMotifs(false, false, "results/"+title+ names[k]+"/"+name, g);
        		} catch(IOException e){
					e.printStackTrace();
				}
				
				name = name.replace(model, replace);
			    compared[c] = "results/"+title+ names[k]+"/"+name + end;
			   
		try{
		GraphReader read = new EdgesOnlyReader(path+files[i].replace(model, replace));
//		Graph g = new UndirectedNLGraph(read);
//		MotifAnalyzer mo = new FourUndirectedMotifAnalyzer(true,false);
//		mo.analyzeMotifs(false, false, "results/"+title+ names[k]+"/"+name, g);
		} catch(IOException e){
			e.printStackTrace();
		}
		 name = name.replace("-"+replace+"-", "");
		  resFiles[c] = "results/"+title+ names[k]+"/"+name;
		  titles[c] = name;
		  c++;
				
							}
				}			
					//NullModel.rewrite4(pathNull, compared, 2, "results/"+title+ names[k]+"/rewrite4", resFiles, titles, title, 6);	
					
						
				
				//}		
					}
					
					
					
				
	}
	public static void mainGTNAGraph(String title, String path, String resFolder){
		String[] names = {""};
		int[] counts = new int[names.length];
		for (int n = 0; n < names.length; n++){
			(new File("results/"+title+names[n])).mkdir();
		}
		(new File(resFolder)).mkdir();
		//System.out.println(path);
		String[] files = (new File(path)).list();
		for (int i =0; i < files.length; i++){
			if (files[i].contains("edges") ){
				for (int j = 0; j < names.length; j++){
					if (files[i].contains(names[j])){
						counts[j]++;
					}
				}
			}
		}
		Vector<String[]> titles = new Vector<String[]>();
		for (int j = 0; j < names.length; j++){
			titles.add(new String[counts[j]]); 
		}
		int[] cur = new int[counts.length];
		
		for (int i = 0;i< files.length; i++){
			if (files[i].contains("edges") ){
				for (int j = 0; j < names.length; j++){
					if (files[i].contains(names[j])){
		System.out.println("Start " +files[i] + " " + new Date());
		String name = files[i].replace(".edges", "").replace("_", "-");
		
		
		try{
		GraphReader read = new EdgesOnlyReader(path+files[i]);
		
		//if (!(new File("results/"+title+ names[j]+"/"+name+ "Count.txt")).exists()) {
		Graph g = new UndirectedNLGraph(read);
		GraphWriter gtna = new GTNAWriter(resFolder+"/"+name,name,":", ";");
		gtna.writeGraph(g);
		gtna.close();
		//}
		titles.get(j)[cur[j]] = name;
		cur[j]++;
		} catch(IOException e){
			e.printStackTrace();
		}
					}
				}
		
		//}		
			}
			
			
			
	}
//		for(int s = 0; s < titles.size(); s++){
//			Plot.plotCountPerc("results/"+title+ names[s]+"/", titles.get(s), names[s]+"CountPerc", true);
//			Plot.plotCounts("results/"+title+ names[s]+"/", titles.get(s), names[s]+"Count", true);
//		}		
	}
	public static void mainOld(String title, String path){
		String[] names = {""};
		//(new File("../GTNA/"+title+"/")).mkdir();
		int[] counts = new int[names.length];
		for (int n = 0; n < names.length; n++){
			(new File("results/"+title+names[n])).mkdir();
		}
		//System.out.println(path);
		String[] files = (new File(path)).list();
		for (int i =0; i < files.length; i++){
			if (files[i].contains("edges") ){
				for (int j = 0; j < names.length; j++){
					if (files[i].contains(names[j])){
						counts[j]++;
					}
				}
			}
		}
		Vector<String[]> titles = new Vector<String[]>();
		for (int j = 0; j < names.length; j++){
			titles.add(new String[counts[j]]); 
		}
		int[] cur = new int[counts.length];
		
		for (int i = 0;i< files.length; i++){
			if (files[i].contains("edges") ){
				for (int j = 0; j < names.length; j++){
					if (files[i].contains(names[j])){
		System.out.println("Start " +files[i] + " " + new Date());
		String name = files[i].replace(".edges", "").replace("_", "-");
		
		
		try{
		GraphReader read = new EdgesOnlyReader(path+files[i]);
		
		if (!(new File("results/"+title+ names[j]+"/"+name+ "Count.txt")).exists()) {
		Graph g = new UndirectedNLGraph(read);
		//GraphWriter gtna = new GTNAWriter("../GTNA/"+title+"/"+name,name,":", ";");
		MotifAnalyzer mo = new FourUndirectedMotifAnalyzer(true,false);
		mo.analyzeMotifs(false, false, "results/"+title+ names[j]+"/"+name, g);
		//gtna.writeGraph(g);
		//gtna.close();
		}
		titles.get(j)[cur[j]] = name;
		cur[j]++;
		} catch(IOException e){
			e.printStackTrace();
		}
					}
				}
		
		//}		
			}
			
			
			
	}
//		for(int s = 0; s < titles.size(); s++){
//			Plot.plotCountPerc("results/"+title+ names[s]+"/", titles.get(s), names[s]+"CountPerc", true);
//			Plot.plotCounts("results/"+title+ names[s]+"/", titles.get(s), names[s]+"Count", true);
//		}		
	}
	
	public static void mainNoEdges(String title, String path, String ending){
		//for (int n = 0; n < names.length; n++){
			(new File("results/"+title)).mkdir();
		//}
		//System.out.println(path);
		String[] files = (new File(path)).list();
		
		
		for (int i = 0;i< files.length; i++){
			//if (files[i].contains("edges") ){
			//System.out.println(files[i].split("_s.").length + " " + files[i]);
			String index = files[i].split("_s.")[1];
			if (index.equals(ending)){
		System.out.println("Start " +files[i] + " " + new Date());
		String name = files[i].replace(".edges", "").replace("_", "-");
		
		
		try{
		GraphReader read = new EdgesOnlyReader(path+files[i]);
		
		if (!(new File("results/"+title+ "/"+name+ "Count.txt")).exists()) {
		Graph g = new UndirectedNLGraph(read);
		//GraphWriter gtna = new GTNAWriter("../GTNA/"+title+"/"+name,name,":", ";");
		MotifAnalyzer mo = new FourUndirectedMotifAnalyzer(true,false);
		mo.analyzeMotifs(false, false, "results/"+title+ "/"+name, g);
		//gtna.writeGraph(g);
		//gtna.close();
		}
		
		} catch(IOException e){
			e.printStackTrace();
		}
					}
			//	}
		}
		//}		
			
			
			
			
	
//		for(int s = 0; s < titles.size(); s++){
//			Plot.plotCountPerc("results/"+title+ names[s]+"/", titles.get(s), names[s]+"CountPerc", true);
//			Plot.plotCounts("results/"+title+ names[s]+"/", titles.get(s), names[s]+"Count", true);
//		}		
	}
	
	public static void mainPrefix(String title, String path, String[] names){
		(new File("../GTNA/"+title+"/")).mkdir();
		int[] counts = new int[names.length];
		for (int n = 0; n < names.length; n++){
			(new File("results/"+title+names[n])).mkdir();
		}
		//System.out.println(path);
		String[] files = (new File(path)).list();
		for (int i =0; i < files.length; i++){
			if (files[i].contains("edges") ){
				for (int j = 0; j < names.length; j++){
					if (files[i].contains(names[j])){
						counts[j]++;
					}
				}
			}
		}
		Vector<String[]> titles = new Vector<String[]>();
		for (int j = 0; j < names.length; j++){
			titles.add(new String[counts[j]]); 
		}
		int[] cur = new int[counts.length];
		
		for (int i = 0;i< files.length; i++){
			if (files[i].contains("edges") ){
				for (int j = 0; j < names.length; j++){
					if (files[i].contains(names[j])){
		System.out.println("Start " +files[i] + " " + new Date());
		String name = files[i].replace(".edges", "").replace("_", "-");
		
		
		try{
		GraphReader read = new EdgesOnlyReader(path+files[i]);
		
		if (!(new File("results/"+title+ names[j]+"/"+name+ "Count.txt")).exists()) {
		Graph g = new UndirectedNLGraph(read);
		MotifAnalyzer mo = new FourUndirectedMotifAnalyzer(true,false);
		mo.analyzeMotifs(false, false, "results/"+title+ names[j]+"/"+name, g);
		}
		titles.get(j)[cur[j]] = name;
		cur[j]++;
		} catch(IOException e){
			e.printStackTrace();
		}
					}
				}
		
		//}		
			}
			
			
			
	}
		for(int s = 0; s < titles.size(); s++){
			Plot.plotCountPerc("results/"+title+ names[s]+"/", titles.get(s), names[s]+"CountPerc", true);
			Plot.plotCounts("results/"+title+ names[s]+"/", titles.get(s), names[s]+"Count", true);
		}		
	}
	
	public static void mainplot(String title, String path){
		String[] names = {""};
		int[] counts = new int[names.length];
		for (int n = 0; n < names.length; n++){
			(new File("results/"+title+names[n])).mkdir();
		}
		//System.out.println(path);
		String[] files = (new File(path)).list();
		for (int i =0; i < files.length; i++){
			if (files[i].contains("edges") ){
				for (int j = 0; j < names.length; j++){
					if (files[i].contains(names[j])){
						counts[j]++;
					}
				}
			}
		}
		Vector<String[]> titles = new Vector<String[]>();
		for (int j = 0; j < names.length; j++){
			titles.add(new String[counts[j]]); 
		}
		int[] cur = new int[counts.length];
		
		for (int i = 0;i< files.length; i++){
			if (files[i].contains("edges") ){
				for (int j = 0; j < names.length; j++){
					if (files[i].contains(names[j])){
		System.out.println("Start " +files[i] + " " + new Date());
		String name = files[i].replace(".edges", "").replace("_", "-");
		
		
		try{
		GraphReader read = new EdgesOnlyReader(path+files[i]);
		
		//if (!(new File("results/"+title+ names[j]+"/"+name+ "Count.txt")).exists()) {
//		Graph g = new UndirectedNLGraph(read);
//		//GraphWriter gtna = new GTNAWriter("/Users/stefanie/workspace/GTNA/"+title+"/"+name,name,":", ";");
//		MotifAnalyzer mo = new FourUndirectedMotifAnalyzer(true,false);
//		mo.analyzeMotifs(false, false, "results/"+title+ names[j]+"/"+name, g);
		//gtna.writeGraph(g);
		//gtna.close();
		//}
		titles.get(j)[cur[j]] = name;
		cur[j]++;
		} catch(IOException e){
			e.printStackTrace();
		}
					}
				}
		
		//}		
			}
			
			
			
	}
		for(int s = 0; s < titles.size(); s++){
			Plot.plotCountPerc("results/"+title+ names[s]+"/", titles.get(s), names[s]+"CountPerc", true);
			Plot.plotCounts("results/"+title+ names[s]+"/", titles.get(s), names[s]+"Count", true);
		}		
	}
	
	public static void mainDirected(String title, String path){
		String[] names = {""};
		int[] counts = new int[names.length];
		for (int n = 0; n < names.length; n++){
			(new File("results/"+title+names[n])).mkdir();
		}
		String[] files = (new File(path)).list();
		for (int i =0; i < files.length; i++){
			if (files[i].contains("edges") ){
				for (int j = 0; j < names.length; j++){
					if (files[i].contains(names[j])){
						counts[j]++;
					}
				}
			}
		}
		Vector<String[]> titles = new Vector<String[]>();
		for (int j = 0; j < names.length; j++){
			titles.add(new String[counts[j]]); 
		}
		int[] cur = new int[counts.length];
		
		for (int i = 0;i< files.length; i++){
			if (files[i].contains("edges") ){
				for (int j = 0; j < names.length; j++){
					if (files[i].contains(names[j])){
		System.out.println("Start " +files[i] + " " + new Date());
		String name = files[i].replace(".edges", "").replace("_", "-");
		
		
		try{
		GraphReader read = new EdgesOnlyReader(path+files[i]);
		
		if (!(new File("results/"+title+ names[j]+"/"+name+ "Count.txt")).exists()) {
		Graph g = new DirectedNLGraph(read);
		//GraphWriter gtna = new GTNAWriter("/Users/stefanie/workspace/GTNA/"+title+"/"+name,name,":", ";");
		MotifAnalyzer mo = new Directed3Analyzer(true,false);
		mo.analyzeMotifs(false, false, "results/"+title+ names[j]+"/"+name, g);
		//gtna.writeGraph(g);
		//gtna.close();
		}
		titles.get(j)[cur[j]] = name;
		cur[j]++;
		} catch(IOException e){
			e.printStackTrace();
		}
					}
				}
		
		//}		
			}
			
			
			
	}
		for(int s = 0; s < titles.size(); s++){
			Plot.plotCountPerc("results/"+title+ names[s]+"/", titles.get(s), names[s]+"CountPerc", true);
			Plot.plotCounts("results/"+title+ names[s]+"/", titles.get(s), names[s]+"Count", true);
		}		
	}
	
	public static void mainCount(String title, String path){
		int minEdge = Integer.MAX_VALUE;
		//String title = "2grams30";
		String[] names = {""};
		int[] counts = new int[names.length];
		for (int n = 0; n < names.length; n++){
			(new File("results/"+title+names[n])).mkdir();
		}
		//String path = "2grams30/";
		String[] files = (new File(path)).list();
		for (int i =0; i < files.length; i++){
			if (files[i].contains("edges") ){
				for (int j = 0; j < names.length; j++){
					if (files[i].contains(names[j])){
						counts[j]++;
					}
				}
			}
		}
		Vector<String[]> titles = new Vector<String[]>();
		for (int j = 0; j < names.length; j++){
			titles.add(new String[counts[j]]); 
		}
		int[] cur = new int[counts.length];
		
		for (int i = 0;i< files.length; i++){
			if (files[i].contains("edges") ){
				for (int j = 0; j < names.length; j++){
					if (files[i].contains(names[j])){
		
		String name = files[i].replace(".edges", "").replace("_", "-");
		
		
		try{
		GraphReader read = new EdgesOnlyReader(path+files[i]);
		
		Graph g = new UndirectedNLGraph(read);
		//System.out.println(name + " Edges: " + g.getEdgecount());
		if (g.getEdgecount() < minEdge){
			minEdge = g.getEdgecount();
		}
		//GraphWriter gtna = new GTNAWriter("/Users/stefanie/workspace/GTNALog/manylangs/"+name,name,":", ";");
		
		//gtna.writeGraph(g);
		//gtna.close();
		
		titles.get(j)[cur[j]] = name;
		cur[j]++;
		} catch(IOException e){
			e.printStackTrace();
		}
					}
				}
		
		//}		
			}
			
		System.out.println(minEdge);	
			
	}
				
	}
	
	public static void mainDegree(){
		String folder = "graphs/";
		String resFolder = "degree/";
		(new File(resFolder)).mkdir();
		String[] degree = (new File(folder)).list();
		for (int j = 0; j < degree.length; j++){
		      String[] files = (new File(folder + degree[j])).list();
		      (new File(resFolder + degree[j])).mkdir();
		      for (int i = 0; i < files.length; i++){
		System.out.println("Start " +files[i] + " Degree " + degree[j] + " " + new Date());
		String name = files[i].replace(".edges", "").replace("_", "-");
		
		
		try{
		GraphReader read = new EdgesOnlyReader(folder + degree[j]+"/" +files[i]);
		if (!(new File(resFolder + degree[j]+"/"+ name+"Count.txt")).exists()) {
		Graph g = new UndirectedNLGraph(read);
		//GraphWriter gtna = new GTNAWriter("/Users/stefanie/workspace/GTNALog/manylangs/"+name,name,":", ";");
		MotifAnalyzer mo = new FourUndirectedMotifAnalyzer(true,false);
		mo.analyzeMotifs(false, false, resFolder + degree[j]+"/"+ name, g);
//		gtna.writeGraph(g);
//		gtna.close();
		}
		
		} catch(IOException e){
			e.printStackTrace();
		}
					}
				}
		
		//}		
			}
			
			
			
	
//		for(int s = 0; s < titles.size(); s++){
//			Plot.plotCountPerc("results/"+title+ names[s]+"/", titles.get(s), names[s]+"CountPerc", true);
//			Plot.plotCounts("results/"+title+ names[s]+"/", titles.get(s), names[s]+"Count", true);
//		}
		
	
	
	public static void mainFaktorSimple(){
		//slen10-10.co_s.f2s10
				String title = "sentleng";
				String[] names = {"slen10-10.co_s.f2s10", "slen10-10.co_s.f2s30", "slen10-30.co_s.f2s30",
						"slen20-20.co_s.f2s10", "slen20-20.co_s.f2s30", "slen30-30.co_s.f2s10", "slen30-30.co_s.f2s30",
						"slen5-20.co_s.f2s30"};
				int[] counts = new int[names.length];
				for (int n = 0; n < names.length; n++){
					(new File("results0412/"+title+names[n])).mkdir();
				}
				String model = "real";
				String[] modelFiles = new String[names.length];
				String end = "Count.txt";
				String path = "graphs/sentLen_intervals/";
				String[] files = (new File(path)).list();
//				for (int i =0; i < files.length; i++){
//					if (files[i].contains("edges") ){
//						for (int j = 0; j < names.length; j++){
//							if (files[i].contains(names[j]) && !files[i].contains(model)){
//								counts[j]++;
//							}
//						}
//					}
//				}
				System.out.println(counts[0]);
				Vector<String[]> titles = new Vector<String[]>();
				for (int j = 0; j < names.length; j++){
					titles.add(new String[counts[j]]); 
				}
				Vector<String[]> plotFiles = new Vector<String[]>();
				for (int j = 0; j < names.length; j++){ 
					plotFiles.add(new String[counts[j]]); 
				}
				int[] cur = new int[counts.length];
				
				for (int i = 0;i< files.length; i++){
					if (files[i].contains("edges") ){
						for (int j = 0; j < names.length; j++){
							if (files[i].contains(names[j])){
				System.out.println("Start " +files[i] + " " + new Date());
				String name = files[i].replace(".edges", "").replace("_", "-");
				
				
				try{
				GraphReader read = new EdgesOnlyReader(path+files[i]);
				Graph g = new UndirectedNLGraph(read);
				//if (name.contains("5-20")){
				MotifAnalyzer mo = new FourUndirectedMotifAnalyzer(true,false);
				mo.analyzeMotifs(false, false, "results/"+title+ names[j]+"/"+name, g);
				//}
				if (name.contains(model)){
					modelFiles[j] = "results/"+title+ names[j]+"/"+name+end;
				} else {
					plotFiles.get(j)[cur[j]] = "results/"+title+ names[j]+"/"+name+end;
				titles.get(j)[cur[j]] = name;
				cur[j]++;
				}
				} catch(IOException e){
					e.printStackTrace();
				}
							}
						}
				
				//}		
					}
					
					
					
			}
				for(int s = 0; s < titles.size(); s++){
					//NullModel.rewriteFaktor(modelFiles[s], plotFiles.get(s), 2, true, "results/"+title+ names[s]+"/NullModelLogFaktor", 
					//		titles.get(s), names[s]);
					
				}
	}
	
	private static void scripting(double nr){
		String path = "2grams10/";
		String[] files = ((new File(path))).list();
		String[] names = new String[files.length];
		for (int i = 0; i < files.length; i++){
			String[] parts = files[i].split("_");
			names[i] = parts[0] + "-"+parts[1];
		}
		String[] end = {"-norm.co-s.f2s10Count.txt", "-2gram-norm.co-s.f2s10Count.txt"};
		((new File("results/Comparison/"))).mkdir();
		((new File("results/Comparison/"+ nr ))).mkdir();
		try {
			GnuplotWriter gnu = new GnuplotWriter("results/Comparison/"+ nr + "/gnu.txt");
			gnu.writeHeader(true, true);
			for (int i = 0; i < names.length; i++){
				gnu.writeOutput("results/Comparison/"+ nr +"/"+ names[i]+".png", names[i], new String[]{"results/real/"+nr+"/"+names[i]+end[0], 
			    		"results/model/"+nr+"/"+names[i]+end[1]}, new String[]{"Real","2gram"}, new int[]{3,3}, "lines", true);
			}
			gnu.close();
			GnuplotWriter.execute("results/Comparison/"+ nr + "/gnu.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



private static void minMax(String folder, String res, int nr){
	String[] files = (new File(folder)).list();
	try {
		BufferedWriter bw = new BufferedWriter(new FileWriter(res));
		double[] average = new double[nr];
		int count = 0;
		double[] max = new double[nr];
		double[] min = new double[nr];
		for (int i = 0; i < nr; i++){
			min[i] = 100;
		}
		for (int i = 0; i < files.length; i++){
			if (files[i].contains("Count.txt")){
				count++;
				BufferedReader br = new BufferedReader(new FileReader(folder + files[i]));
				String line = br.readLine();
				int n;
				double m;
				while ((line = br.readLine())!= null){
					String[] parts = line.split(" ");
					n = Integer.parseInt(parts[0])-1;
					m = Double.parseDouble(parts[2]);
					average[n] = average[n] + m;
					if (m > max[n]){
						max[n] = m;
					}
					if (m < min[n]){
						min[n] = m;
					}
				}
				br.close();
			}
		}
		bw.write("# average,min,max");
		for (int j = 0; j < nr; j++){
			bw.newLine();
			bw.write((j+1) + " " + average[j]/count + " " + min[j] + " "+ max[j]);
		}
		bw.flush();
		bw.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public static void rewrite(String file){
	double[] old = new double[8];
	double[] newM = new double[6];
	try {
		BufferedReader br = new BufferedReader(new FileReader(file));
		br.readLine();
		String line;
		for (int i = 0; i < old.length; i++){
			line = br.readLine();
			old[i] = Double.parseDouble(line.split(" ")[2]);
		}
		br.close();
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		double sum12 = (old[0]+old[1])/(double)100;
		bw.write("# motif, %");
		for (int i = 1; i < 7; i++){
			bw.newLine();
			bw.write(i +" " + old[i+1]/(1-sum12));
		}
		bw.flush();
		bw.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public static void rewriteLabel(String nodeFile, String edgeFile, String result){
	try {
		BufferedReader br = new BufferedReader(new FileReader(nodeFile));
		HashMap<String,String> map = new HashMap<String,String>();
		String line;
		String[] parts;
		while ((line = br.readLine()) != null){
			parts = line.split("	");
			if (parts.length > 1){
				map.put(parts[0], parts[1]);
			}
			
		}
		br.close();
		
		br = new BufferedReader(new FileReader(edgeFile));
		BufferedWriter bw = new BufferedWriter(new FileWriter(result));
		while ((line = br.readLine()) != null){
			parts = line.split("	");
			if (parts.length > 1){
				bw.write(map.get(parts[0]) + "	" + map.get(parts[1]));
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
