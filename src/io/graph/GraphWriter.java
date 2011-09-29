package io.graph;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import architecture.graph.Graph;

/**
 * abstract class for writing a graph description
 * @author stefanie
 *
 */

public abstract class GraphWriter extends BufferedWriter {

	public GraphWriter(String file) throws IOException {
		super(new FileWriter(file));
	}
	
    public abstract void writeGraph(Graph graph);
    
   

}
