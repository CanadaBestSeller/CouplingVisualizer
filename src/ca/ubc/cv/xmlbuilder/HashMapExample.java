package ca.ubc.cv.xmlbuilder;

import java.util.*;
import java.util.Map.Entry;

import ca.ubc.cv.treebuilder.MethodNode;

public class HashMapExample{

    public HashMap<String, List<MethodNode> > methodNodes;

    
    public static void main(String[] args) {
        new HashMapExample();
    }

    public HashMapExample() {
    	
    	// HashMap of the method nodes
        methodNodes = new HashMap<String, List<MethodNode>>();
        
        MethodNode fly = new MethodNode();
        MethodNode constructor = new MethodNode();
        MethodNode greet = new MethodNode();
        MethodNode main = new MethodNode();
        MethodNode breedParrot = new MethodNode();
        MethodNode println = new MethodNode();
                
        // Class Parrot, fly() and constructor 
        fly.setClassName("Parrot");
		fly.setMethodName("fly");
		
		constructor.setClassName("Parrot");
		constructor.setMethodName("<<Constructor>>");
		
		// Class Utility, greet()
		greet.setClassName("Utility");
		greet.setMethodName("greet");
		
		// Class Main, main() 
		main.setClassName("Main");
		main.setMethodName("main");
		
		// Class Zoo, breedParrot
		breedParrot.setClassName("Zoo");
		breedParrot.setMethodName("breedParrot");
		
		// Class java.lang.System, println
		println.setClassName("java.lang.System");
		println.setMethodName("println");
		
		// List of Callers for fly()
		List<MethodNode> flyCallers = new ArrayList<MethodNode>();
		fly.setCallerList(flyCallers);   
		 
		
		// List of Callers for Constructor  
		// breedParrot from Zoo
		List<MethodNode> constructorCallers = new ArrayList<MethodNode>();
		constructorCallers.add(breedParrot);
		constructor.setCallerList(constructorCallers);   
		 		
		// List of Callers for greet() 
		// Constructor from Parrot and main() from Main
		List<MethodNode> greetCallers = new ArrayList<MethodNode>();
		greetCallers.add(main);
		greetCallers.add(constructor);
		
		greet.setCallerList(greetCallers);   		
		
		// List of Callers for main()
		// none   
		List<MethodNode> mainCallers = new ArrayList<MethodNode>();
		main.setCallerList(mainCallers);   
				
		// List of Callers for breedParrot()
		// main() from Main 
		List<MethodNode> breedParrotCallers = new ArrayList<MethodNode>();
		breedParrotCallers.add(main); 
		breedParrot.setCallerList(breedParrotCallers);   	
		
		// List of Callers for println()
		// greet() from Utility and fly() from Parrot 
		List<MethodNode> printlnCallers = new ArrayList<MethodNode>();
		printlnCallers.add(greet);
		printlnCallers.add(fly);
		printlnCallers.add(main);
		println.setCallerList(printlnCallers);  
		
		// assemble the list of MethodNodes for each class
		List<MethodNode> parrotMethods = new ArrayList<MethodNode>();
		parrotMethods.add(fly);
		parrotMethods.add(constructor);
		
		List<MethodNode> utilityMethods = new ArrayList<MethodNode>();
		utilityMethods.add(greet); 
		
		List<MethodNode> mainMethods = new ArrayList<MethodNode>();
		mainMethods.add(main); 
		
		List<MethodNode> zooMethods = new ArrayList<MethodNode>();
		zooMethods.add(breedParrot); 
		
		List<MethodNode> systemMethods = new ArrayList<MethodNode>();
		systemMethods.add(println); 
		
		methodNodes.put("Utility", utilityMethods);
		methodNodes.put("Parrot", parrotMethods);
		methodNodes.put("Main", mainMethods);
		methodNodes.put("Zoo", zooMethods);
		methodNodes.put("java.lang.System", systemMethods);
		
		for (Map.Entry<String, List<MethodNode>> mapEntry : methodNodes.entrySet()) {
			printClass(mapEntry);
			printMethods(mapEntry);
		}
		System.out.println();
	
    }

    private void printClass(Map.Entry<String, List<MethodNode>> mapEntry) {
    	System.out.println(mapEntry.getKey());
    }

    private void printMethods(Map.Entry<String, List<MethodNode>> mapEntry) {
    	for (MethodNode mn : mapEntry.getValue()) {
    		System.out.println("\t" + mn.getMethodName());
    	}
	}
    
	
}