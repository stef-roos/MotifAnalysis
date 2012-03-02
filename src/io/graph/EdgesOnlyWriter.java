package io.graph;

import java.io.IOException;
import architecture.edge.Edge;
import architecture.graph.Graph;
import architecture.node.Node;
import config.Config;

/**
 * write a graph to a file using edges-only format
 * @author stefanie
 *
 */

public class EdgesOnlyWriter extends GraphWriter {

	public EdgesOnlyWriter(String file) throws IOException {
		super(file);
	}

	@Override
	public void writeGraph(Graph graph) {
		Node[] nodes = graph.getNodes();
        Edge[] edges;
        Edge edge;
        Node node;
        String line; 
        for (int i = 0; i < nodes.length; i++){
        	node = nodes[i];
        	edges = node.getNeighbors();
        	for (int j = 0; j < edges.length; j++){
        		edge = edges[j];
        		if ((graph.isDirected() && edge.getType() > -1) || (!graph.isDirected() && node.getIndex() < edge.getNode())){
        			line = node.getLabel() + " " + nodes[edge.getNode()].getLabel();
        			if (Config.COLUMNS == 3 || Config.COLUMNS == 5){
        				if (Config.EDGEWEIGHTTYPEINT){
        					line = line + " " + (int)edge.getWeight();
        				} else {
        				    line = line + " " + edge.getWeight();
        				}
        			}
        			if (Config.COLUMNS > 3){
        				line = line + " " + node.getWeight() + " " + nodes[edge.getNode()].getWeight();
        			}
        			
        			try {
        				if (i > 0 || j > 0){
        					this.newLine();
        				}
						this.write(line);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}
        	}
        }
        try {
			this.flush();
			this.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
