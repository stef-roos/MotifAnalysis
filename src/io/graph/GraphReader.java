package io.graph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import architecture.node.Node;

/**
 * abstract class for reading in a graph
 * can be used for various formats e.g. edges-only
 * @author stefanie
 *
 */
public abstract class GraphReader extends BufferedReader {

	
	public GraphReader(String file) throws FileNotFoundException{
		super(new FileReader(file));
	}
	
	public abstract Node[] readGraph( boolean dir, boolean matrix);

	
	

}
