# CouplingVisualizer
#### Installation Instructions

1. At the Java Eclipse menu bar, click:
Help -> Install New Software

2. Paste the following link as the site to work with:    
http://couplingvisualizer.co.nf/site.xml

3. Select CouplingVisualizer. 

-OR-

1. Go to our plugin's Eclipse marketplace site:   
https://marketplace.eclipse.org/node/1312280

2. Drag the installation icon to your eclipse workbench.

#### Plugin Overview
Thank you for taking the time to check out our plugin.

Quick video showcasing features:   
https://www.youtube.com/watch?v=b9K553hAGn0

This plugin started as a UBC CPSC410 project, it visually represents code coupling, specifically class and method dependencies. You can use this plugin to generate a web diagram which centers around a selected method and displays connectivity to its method/class dependants. The output of our visualization aims to aid developers in understanding coupling relationships.

Developers: David Li, Justin Mew, Michelle Dow, and Kit Wan.

# Components
#### External Components
A. Project View
    The project view is an Eclipse component that is responsible for parsing raw java code into an Abstract Syntax Tree (AST). The AST is then translated into ITypes, which would later allow for the extraction of classes or specific methods or fields. The IType is a java interface that is native to Eclipse. Through our project view component a tree view of ITypes are outputted to the IMethod Extractor.

D. org.eclipse.jdt.internal.corext.callhierarchy [1]
    This component is an internal Eclipse package that helps the Tree Builder extract callers of specific methods. The package accepts a code member, in our a case an IMethod, and returns all callers of the code member.

F. Visualizer
    The visualizer is responsible for reading in the XML file provided by the translator and outputting our visualization. The visualization will be a coupling web that displays the coupling relationships of a method and all of its callees.

#### Internal Components
B. IMethod Extractor
    The IMethod Extractor is responsible for taking in ITypes from the package explorer and extracting out an IMethod. IMethods are Java representation of methods. IMethods allow Eclipse to use an internal Eclipse package to extract out call hierarchy relationships relating to the selected method. The method is selected either by a drag-and-drop operation into our view window or by right-clicking the element in the Eclipse package directory and clicking on the button called how Method Dependencies/Dependents.�The IMethod Extractor outputs a single IMethod Java object to be used to display our coupling visualization.

C. Tree Builder 
    The Tree Builder is responsible for receiving one IMethod from the IMethod Extractor and outputting a method tree to be used by the Translator. The Tree Builder uses the eclipse JDT internal classes to find callers of the method (Component D). Then by adding the returned callers to the tree and recursively calling Component D again, the tree builder will build our method tree with the selected IMethod and all if its callees. This can be further explained by the following:

- Builds a tree of height 0, where root is the starting IMethod
- Takes the IMethod
- Retrieves callers of the IMethod using Eclipse call hierarchy package (Component D)
- For every caller inside of IMethod:
- create a method node for caller
- connect caller to IMethod in result tree
- Recursively repeat step 1-4 with the callers, which are also IMethods themselves.
- After reaching the desired depth, returns the result tree of IMethods.
 
E. Translator
    The translator is responsible for converting the method tree outputted by the tree building into an XML file to be used by the visualizer. 

#### Datatypes being passed by modules
1. ITypes in Package Explorer
- ITypes are accessed through the package explorer
- Abstract Java interface 
- Representing different types of code units, ie. IClasses, IMethods, Ifields etc. 
- Native Eclipse interface
- Enables internal renderings
- Draggable and right-clickable functionality.[2]

2. Root IMethod
- Child of the IType
- Passed from IMethod to the Tree Builder as the root of the tree
- IMethods contain useful information (accessible programmatically) 
- Such as parameter types, return types, etc.

3. Leaf IMethods
- passed to the internal Eclipse call hierarchy package
 - used as the method of interest for the hierarchy call
- Recursively provided as a leaf IMethod to build the next level of the tree.

4. Leaf IMethod callers  
- IMethods returned by the call hierarchy package
- Represents all the methods that call the selected method.
- Builds the next level of the tree 
- Recursively passed back as Leaf IMethods

5. Tree IMethod nodes
- N-ary tree structure of MethodNode 
- Root node is the method of interest
- Descendents are callers of the method
- Directly or indirectly 
- Defined where a node children calls the parent node. 

6. XML
- XML format to be used for visualization
- Universal format compatible with many visualization tools available.

#### Deployment Instructions
1. new -> feature project -> Finish
2. feature.xml has information (descr, copyright, etc.)
can add plugins
can add dependencies (match with cv.plugin.xml's dependencies)

1. new -> update site -> Finish
2. site.xml has:
	category properties
	site Map -> point to previously created feature file (add feature) -> build all

Webserver must be able to host: sub-domain compatible
	Must be able to match feature XML
	eg) webserver.com/features/CouplingVisualizerFeatures_1.12391239.jar
