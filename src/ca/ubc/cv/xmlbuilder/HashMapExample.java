package ca.ubc.cv.xmlbuilder;
import java.util.*;

import ca.ubc.cv.treebuilder.MethodNode;

public class HashMapExample{

    private HashMap<String, List<MethodNode> > methodNodes;

    public static void main(String[] args) {
        new HashMapExample();
    }

    public HashMapExample() {
    	
    	// HashMap of the method nodes
        methodNodes = new HashMap<String, List<MethodNode>>();
        
        HashMap<String, List<MethodNode>> parrotMethodsMap = null;
        HashMap<String, List<MethodNode>> utilityMethodsMap = null;
        HashMap<String, List<MethodNode>> mainMethodsMap = null;
        HashMap<String, List<MethodNode>> zooMethodsMap = null;
        HashMap<String, List<MethodNode>> systemMethodsMap = null;        
        
        MethodNode fly = null;
        MethodNode constructor = null;
        MethodNode greet = null;
        MethodNode main = null;
        MethodNode breedParrot = null;
        MethodNode println = null;
                
        // Class Parrot, fly() and constructor 
        fly.setClassName("Parrot");
		fly.setMethodName("fly");
		
		constructor.setClassName("Parrot");
		constructor.setMethodName("constructor");
		
		System.out.println(fly);
				 
		// Class Utility, greet()
		greet.setClassName("Utility");
		greet.setMethodName("greet");
		
		System.out.println(greet);	
		
		// Class Main, main() 
		main.setClassName("Main");
		main.setMethodName("main");
		
		System.out.println(main); 
		
		// Class Zoo, breedParrot
		breedParrot.setClassName("Zoo");
		breedParrot.setMethodName("breedParrot");
		
		System.out.println(breedParrot);
		
		// Class java.lang.System, println
		println.setClassName("java.lang.System");
		println.setMethodName("println");
		
		System.out.println(println);
		
		// List of Callers for fly()
		List<MethodNode> flyCallers = new ArrayList<MethodNode>();
		flyCallers = null;
		
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
		mainCallers = null;
		
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
		  
		// put into HashMap
		parrotMethodsMap.put( "Parrot", parrotMethods);
		utilityMethodsMap.put("Utility", utilityMethods);
		mainMethodsMap.put("Main", mainMethods);
		zooMethodsMap.put("Zoo", zooMethods);
		systemMethodsMap.put("java.lang.System", systemMethods);
		
    }

	
}