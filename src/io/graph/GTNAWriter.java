package io.graph;

import java.io.IOException;

import config.Config;

import architecture.edge.Edge;
import architecture.graph.Graph;
import architecture.node.Node;

public class GTNAWriter extends GraphWriter{
	private String name;
	private String sep1,sep2;

	public GTNAWriter(String file, String name, String sep1, String sep2) throws IOException {
		super(file);
		this.name = name;
		this.sep1 = sep1;
		this.sep2 = sep2;
	}

	@Override
	public void writeGraph(Graph graph) {
		Node[] nodes = graph.getNodes();
        Edge[] edges;
        Edge edge;
        Node node;
        String line; 
        try{ 
        this.write(this.name);
        this.newLine();
        this.write(""+graph.getNodecount());
        this.newLine();
        this.write(""+graph.getEdgecount());
        for (int i = 0; i < nodes.length; i++){
        	node = nodes[i];
        	edges = node.getNeighbors();
        	line = i + sep1;
        	for (int j = 0; j < edges.length; j++){
        		edge = edges[j];
        		line = line + edge.getNode();
        		if (j < edges.length -1){
        			line = line + sep2;
        		}
        	}
        	this.newLine();
        	this.write(line);
        }
       
			this.flush();
			this.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
