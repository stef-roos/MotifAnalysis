package io.graph;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import architecture.edge.Edge;
import architecture.edge.WeightedEdge;
import architecture.edge.WeightedEdgeDouble;
import architecture.node.AdMatrixNodeBoolean;
import architecture.node.AdMatrixNodeDouble;
import architecture.node.AdMatrixNodeInteger;
import architecture.node.NeighborListNode;
import architecture.node.Node;
import config.Config;

/**
 * a reader for files using edges-only format:
 * i.e. the file is of the form
 * label(node1) label(node2) (edgeweight) (nodeweight1 nodeweight2)
 * @author stefanie
 *
 */
public class EdgesOnlyReader extends GraphReader {

	public EdgesOnlyReader(String file)
			throws FileNotFoundException {
		super(file);
	}

	@Override
	public Node[] readGraph(boolean directed, boolean matrix) {
		int index = 0;
		//maps between labels of nodes and its edges/object node
		HashMap<String, Vector<Edge>> map = new HashMap<String,Vector<Edge>>();
		HashMap<String, Node> mapNode = new HashMap<String,Node>();
		try {
			String line = this.readLine();
			String[] parts;
			Node node1,node2;
			Vector<Edge> edges1, edges2;
			Edge newEdge;
			int index1,index2;
			while (line != null){
				parts = line.split(Config.DELIMINATOR);
				if (parts.length < Config.COLUMNS){
					throw new IllegalArgumentException("Not enough columns in your file");
				}
				
				//check if node A already exists
				node1 = mapNode.get(parts[0]);
				if (node1 == null){
				node1 = this.generateNode1(index, parts, matrix);
				mapNode.put(parts[0], node1);
				edges1 = new Vector<Edge>();
				map.put(parts[0], edges1);
				index++;
				} else {
					edges1 = map.get(parts[0]);
				}
				//check if node B already exists
				node2 = mapNode.get(parts[1]);
				if (node2 == null){
				node2 = this.generateNode2(index, parts, matrix);
				mapNode.put(parts[1], node2);
				edges2 = new Vector<Edge>();
				map.put(parts[1], edges2);
				index++;
				} else {
					edges2 = map.get(parts[1]);
				}
				 
				
				//construct and add new edge
				//avoid parallel edges
				index2 = node2.getIndex();
				if (Config.COLUMNS == 2 || Config.COLUMNS == 4){
					newEdge = new Edge(index2);
				}else {
					if (Config.EDGEWEIGHTTYPEINT){
						newEdge = new WeightedEdge(index2,Integer.parseInt(parts[2]));
					}else {
						newEdge = new WeightedEdgeDouble(index2,Double.parseDouble(parts[2]));
					}
				}
				if (!edges1.contains(newEdge)){
					edges1.add(newEdge);
				}
				//add edge in other direction if graph is undirected
				if (!directed){
					index1 = node1.getIndex();
					if (Config.COLUMNS == 2 || Config.COLUMNS == 4){
						newEdge = new Edge(index1);
					}else {
						if (Config.EDGEWEIGHTTYPEINT){
							newEdge = new WeightedEdge(index1,Integer.parseInt(parts[2]));
						}else {
							newEdge = new WeightedEdgeDouble(index1,Double.parseDouble(parts[2]));
						}
					}
					if (!edges2.contains(newEdge)){
						edges2.add(newEdge);
					}
				}
				
				line = this.readLine();
			}
			this.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//build arrays
		Node[] nodes = new Node[map.size()];
		Set<String> set = map.keySet();
		Iterator<String> it = set.iterator();
		Node n;
		Vector<Edge> ed;
		String label;
		while (it.hasNext()){
			label = it.next();
			n = mapNode.get(label);
			ed = map.get(label);
			n.setNeighbors(ed, map.size());
			nodes[n.getIndex()] = n;
			
		}
		return nodes;
	}
	
	/**
	 * generate a node from the information in a line
	 * @param count: index of new node
	 * @param data: information in line
	 * @param matrix: graph representation an adjacency matrix?
	 * @return
	 */
	private Node generateNode1(int count,String[] data, boolean matrix){
		if (matrix){
			switch (Config.COLUMNS){
			case 2: return new AdMatrixNodeBoolean(count,data[0]);
			case 3: if (Config.EDGEWEIGHTTYPEINT){
				      return new AdMatrixNodeInteger(count,data[0]);
			        } else {
			          return new AdMatrixNodeDouble(count,data[0]);  
			        }
			case 4: return new AdMatrixNodeBoolean(count,data[0],Double.parseDouble(data[2]));
			case 5: if (Config.EDGEWEIGHTTYPEINT){
			            return new AdMatrixNodeInteger(count,data[0],Double.parseDouble(data[3]));
	                 } else {
	                   return new AdMatrixNodeDouble(count,data[0],Double.parseDouble(data[3]));  
	                 }
			default: throw new IllegalArgumentException("columns value not possible, must be between 2 and 5");	
			}
		} else {
			switch (Config.COLUMNS){
			case 2: 
			case 3: return new NeighborListNode(count,data[0]);
			case 4: return new NeighborListNode(count,data[0],Double.parseDouble(data[2]));
			case 5: return new NeighborListNode(count,data[0], Double.parseDouble(data[3])); 
			default: throw new IllegalArgumentException("columns value not possible, must be between 2 and 5");	
			}
		}
	}
	
	private Node generateNode2(int count,String[] data, boolean matrix){
		if (matrix){
			switch (Config.COLUMNS){
			case 2: return new AdMatrixNodeBoolean(count,data[1]);
			case 3: if (Config.EDGEWEIGHTTYPEINT){
				      return new AdMatrixNodeInteger(count,data[1]);
			        } else {
			          return new AdMatrixNodeDouble(count,data[1]);  
			        }
			case 4: return new AdMatrixNodeBoolean(count,data[1],Double.parseDouble(data[3]));
			case 5: if (Config.EDGEWEIGHTTYPEINT){
			            return new AdMatrixNodeInteger(count,data[1],Double.parseDouble(data[4]));
	                 } else {
	                   return new AdMatrixNodeDouble(count,data[1],Double.parseDouble(data[4]));  
	                 }
			default: throw new IllegalArgumentException("columns value not possible, must be between 2 and 5");	
			}
		} else {
			switch (Config.COLUMNS){
			case 2: 
			case 3: return new NeighborListNode(count,data[1]);
			case 4: return new NeighborListNode(count,data[1],Double.parseDouble(data[3]));
			case 5: return new NeighborListNode(count,data[1], Double.parseDouble(data[4])); 
			default: throw new IllegalArgumentException("columns value not possible, must be between 2 and 5");	
			}
		}
	}
	
	

	}
