package main;

import io.gnuplot.GnuplotWriter;
import io.gnuplot.Plot;
import io.graph.EdgesOnlyReader;
import io.graph.GraphReader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Vector;

import motife.MotifAnalyzer;
import motife.directed3.Directed3Analyzer;
import motife.undirected.AllUndirectedMotifAnalyzer;
import motife.undirected.FourUndirectedMotifAnalyzer;
import architecture.graph.DirectedNLGraph;
import architecture.graph.Graph;
import architecture.graph.UndirectedNLGraph;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        //rewriteOverlap();
		mainDirected("2gram", "2gram/");
//		System.exit(0);
		//scripting(10);
		
		System.out.println("Done " + new Date());
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
	
	public static void mainOld(String title, String path){
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
		
		//if (!(new File("results/"+title+ names[j]+"/"+name+ "Count.txt")).exists()) {
		Graph g = new UndirectedNLGraph(read);
		//GraphWriter gtna = new GTNAWriter("/Users/stefanie/workspace/GTNA/"+title+"/"+name,name,":", ";");
		MotifAnalyzer mo = new AllUndirectedMotifAnalyzer(true,false);
		mo.analyzeMotifs(false, false, "results/"+title+ names[j]+"/"+name, g);
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

}
