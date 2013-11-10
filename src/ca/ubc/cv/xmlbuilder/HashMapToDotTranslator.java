package ca.ubc.cv.xmlbuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.eclipse.swt.internal.ole.win32.VARIANT;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ca.ubc.cv.treebuilder.MethodNode;
import ca.ubc.cv.xmlbuilder.HashMapExample;;

public class HashMapToDotTranslator extends HashMapExample {

	
	/** Wanted result for dot 
	 * 
	 * 	digraph { 
	 * 			subgraph cluster_0 { 
	 * 				label="Subgraph A";
	 * 				a -> b;
	 * 				b -> c;
	 * 				c -> d; 
	 * 		  } subgraph cluster_1 { 
	 * 				label="Subgraph B";
	 * 				a -> f;
	 * 				f -> c;
	 * 		  } 
	 * }
	 */
	
	/** Can I just print out the items?  
	 * Justin used document... look at what doc. does -!!!
	 * 
	 * 	System.out.println("digraph {") 
	 *  
	 * 			subgraph cluster_0 {
	 * 				label="Class 1";
	 * 				methodNode1 -> methodNode2;
	 * 				methodNode2 -> methodNode3;
	 * 				methodNode3 -> methodNode4; 
	 * 		  } subgraph cluster_1 { 
	 * 				label="Class 2";
	 * 				methodNode1 -> methodNode5;
	 * 				methodNode5 -> methodNode3;
	 * 		  } 
	 * }
	 */

	private static Document doc; 
	private HashMap<String, List<MethodNode> > methodNodes;
	
	public void convertHashMapToDot() {
		
	/*
		HashMapExample hashmap = new HashMapExample(); 
	 
		for(initialization; Boolean_expression; update)
		{
		   //Statements
		}
    
		for each key-value entry in the hashmap:
			create rectangle with name equal to the key
			
		for each methodnode in the value:
		    create circle method that points to another circle method
    */
	}
	private void printMethod(String string) {
		// TODO Auto-generated method stub
		 for (String name:methodNodes.keySet()) {
	            System.out.println(string);
		 }
	}
}
