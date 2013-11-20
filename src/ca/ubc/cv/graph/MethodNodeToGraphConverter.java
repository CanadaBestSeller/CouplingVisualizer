package ca.ubc.cv.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphContainer;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;

import ca.ubc.cv.treebuilder.MethodNode;
import ca.ubc.cv.views.CouplingVisualizerView;

/**
 * MethodNodeToGraphConverter is responsible for taking in
 * the root MethodNode, and constructing a Graph object that
 * displays the components.  
 *
 */
public class MethodNodeToGraphConverter {

	/*
	 * Fields used to map MethodNodes to GraphNodes.
	 * This is to insure that we do not create duplicate
	 * nodes even if we see them. 
	 */
	private Map<MethodNode, GraphNode> methodToGraphNodeMap;
	private Map<String, GraphContainer> classToGraphContainerMap;

	private Map<GraphConnection, Integer> graphConnectionDepths; 
	private Map<GraphNode, Integer> graphNodeDepths;
	private Map<GraphContainer, Integer> graphContainersDepths; 

	private int max_depth = 0; 
	private int currentDetailLevel;

	private MethodNode rootMethodNode; 

	/**
	 * Alters the level of abstraction
	 * CLASS_ONLY: This level only illustrates the classes 
	 * 	that use the method.
	 * CLASS_METHOD: This level showcases the method 
	 * 	dependencies.
	 * CLASS_METHOD_PARAMETERS: This level illustrates
	 * 	method dependencies and displays the parameters
	 * 	and return types of each method. 
	 */
	public static final int CLASS_ONLY = 1;
	public static final int CLASS_METHOD = 2; 
	public static final int CLASS_METHOD_PARAMETERS = 3;

	private Composite parent;
	private Graph graph; 

	/**
	 * Constructor
	 * @param parent
	 * @param graph
	 */
	public MethodNodeToGraphConverter(Composite parent, Graph graph) {
		this.graph = graph; 
		this.parent = parent; 
	}

	/**
	 * Constructs the Graph based on the rootnode and detailLevel. It
	 * transverses the MethodNode tree, creating each methodNode and adding
	 * them to the Graph. 
	 * @param rootNode
	 * @param detailLevel
	 * @return
	 */
	public Graph methodNodeTreeToGraph(MethodNode rootNode, int detailLevel) {
		methodToGraphNodeMap = new HashMap<MethodNode, GraphNode>();
		classToGraphContainerMap = new HashMap<String, GraphContainer>();

		graphConnectionDepths = new HashMap<GraphConnection, Integer>(); 
		graphNodeDepths = new HashMap<GraphNode, Integer>();
		graphContainersDepths = new HashMap<GraphContainer, Integer>();

		currentDetailLevel = detailLevel;
		rootMethodNode = rootNode; 

		//Handle the detail level for the root node. Even if 
		//we're set to CLASS_ONLY, we certainly want the detail
		//level of the root to be at least CLASS_METHOD; 
		int rootDetailLevel;
		if (detailLevel == CLASS_METHOD_PARAMETERS) {
			rootDetailLevel = CLASS_METHOD_PARAMETERS;
		}
		else {
			rootDetailLevel = CLASS_METHOD; 
		}

		GraphNode rootGraphNode = this.getGraphNode(rootNode, rootDetailLevel, 0);
		Display display = Display.getCurrent();
		Color yellow = display.getSystemColor(SWT.COLOR_MAGENTA);
		Color orange = display.getSystemColor(SWT.COLOR_LIST_BACKGROUND);

		//To distinguish the root node, color it differently. 
		rootGraphNode.setBackgroundColor(yellow);
		classToGraphContainerMap.get(rootNode.getClassName()).setBackgroundColor(orange);

		//Start assembling the graph. 
		this.constructGraphVisualizer(rootNode, detailLevel, 1);

		graph.setLayoutAlgorithm(
				new SpringLayoutAlgorithm(
						LayoutStyles.NO_LAYOUT_NODE_RESIZING), 
						true
				);

		for (GraphContainer gc : classToGraphContainerMap.values()){
			gc.setLayoutAlgorithm(
					new SpringLayoutAlgorithm(
							LayoutStyles.NO_LAYOUT_NODE_RESIZING), 
							true
					);
			gc.open(true);
		}
		//this.adjustDetailLevel(1); //Testing purposes. TODO: Change this
		return graph; 
	}

	/**
	 * Recursive function that will iterate through each MethodNode in 
	 * the tree. 
	 * @param originMn
	 * @param detailLevel
	 * @param depth
	 */
	public void constructGraphVisualizer(MethodNode originMn, int detailLevel, int depth) {
		GraphConnection gc;
		GraphNode toGraphNode;
		GraphNode fromGraphNode;

		if (max_depth < depth) {
			max_depth = depth; 
		}

		for (MethodNode fromMn : originMn.getCallerList()) {
			boolean exist = false;

			//If CLASS_ONLY is toggled, then make the directed
			//connections append to the class rectangles
			if (detailLevel == CLASS_ONLY) {
				//Check <code>fromMn</code>. If we've already created
				//a GraphContainer for it, that means we no longer have to
				//call this.constructGraphVisualizer() on <code>fromMn</code>
				//This is to prevent a cycle. 
				if (classToGraphContainerMap.containsKey(fromMn.getClassName())){
					exist = true;
				}

				GraphContainer fromGc = this.getGraphContainer(originMn, depth);
				GraphContainer toGc = this.getGraphContainer(fromMn, depth);
				if (fromGc.equals(toGc)) continue; 
				gc = new GraphConnection(graph, ZestStyles.CONNECTIONS_DIRECTED, toGc,
						fromGc);
				Label label = new Label(fromMn.getClassName() + " uses " + 
						originMn.getSimpleMethodName()); 
				gc.setTooltip(label);
			}
			//Else, make the directed connections append to the
			//method ovals. 
			else {
				//Check <code>fromMn</code>. If we've already created
				//a GraphNode for it, that means we no longer have to
				//call this.constructGraphVisualizer() on <code>fromMn</code>
				//This is to prevent a cycle. 
				if (methodToGraphNodeMap.containsKey(fromMn)){
					exist = true;
				}
				toGraphNode = this.getGraphNode(originMn, detailLevel, depth);
				fromGraphNode = this.getGraphNode(fromMn, detailLevel, depth);

				gc = new GraphConnection(graph, ZestStyles.CONNECTIONS_DIRECTED, fromGraphNode,
						toGraphNode);
				Label label = new Label(fromMn.getSimpleMethodName() + " uses " 
						+ originMn.getSimpleMethodName());
				gc.setTooltip(label);
			}
			gc.changeLineColor(parent.getDisplay().getSystemColor(SWT.COLOR_BLACK));
			graphConnectionDepths.put(gc, depth); 

			//Cycle check
			if (!exist) {
				this.constructGraphVisualizer(fromMn, detailLevel, depth + 1);
			}
		}
	}

	/**
	 * Check to see if we've created a GraphNode for the following
	 * MethodNode. If we had, then return it. If not, create it 
	 * and add it to our Map of Methods to GraphNodes. 
	 * @param mn
	 * @param detailLevel
	 * @param depth
	 * @return
	 */
	public GraphNode getGraphNode(MethodNode mn, int detailLevel, int depth) {
		GraphNode gn = methodToGraphNodeMap.get(mn);
		if (gn == null) {
			switch (detailLevel){
			case(CLASS_METHOD_PARAMETERS):
				gn = new GraphNode(
						this.getGraphContainer(mn, depth), 
						SWT.NONE,
						mn.getDetailedMethodName()
						); 
			break;
			default:
				gn = new GraphNode(
						this.getGraphContainer(mn, depth), 
						SWT.NONE, 
						mn.getSimpleMethodName()
						);
				break; 
			}
			graphNodeDepths.put(gn, depth);
			Label label = new Label(mn.getParametersAndReturnTypeInfo());  
			gn.setTooltip(label);
			methodToGraphNodeMap.put(mn, gn);
		}

		return gn; 
	}

	/**
	 * Check to see if we've created a GraphContainer for the 
	 * following class. If we had, then return it. If not, create it
	 * and add it to our Map of Classes to GraphContainers. 
	 * @param mn
	 * @param depth
	 * @return
	 */
	public GraphContainer getGraphContainer(MethodNode mn, int depth) {
		GraphContainer gc = classToGraphContainerMap.get(mn.getClassName()); 
		if (gc == null) {
			gc = new GraphContainer(graph, SWT.NONE, mn.getClassName());
			classToGraphContainerMap.put(mn.getClassName(), gc);
			graphContainersDepths.put(gc, depth);
		}
		return gc; 
	}

	/**
	 * Used to adjust the detailLevel of the Graph. If
	 * we alter between CLASS_ONLY and CLASS_METHOD, then
	 * we simply re-draw the canvas. Otherwise, we go
	 * back into each GraphNode (which represent MethodNodes),
	 * and then change the text of them to either become
	 * simplified (just the method name) or detailed 
	 * (method name, parameters, and return type). 
	 * @param detailLevel
	 */
	public void adjustDetailLevel(int detailLevel) {
		if (detailLevel < CLASS_ONLY) {
			return;
		}
		if (detailLevel > CLASS_METHOD_PARAMETERS) {
			return; 
		}

		if ((detailLevel == CLASS_ONLY && currentDetailLevel == CLASS_METHOD) 
				|| (detailLevel == CLASS_METHOD && currentDetailLevel == CLASS_ONLY)){
			CouplingVisualizerView.clearGraph();
			currentDetailLevel = detailLevel; 
			methodNodeTreeToGraph(rootMethodNode, currentDetailLevel);
			return; 
		}
		currentDetailLevel = detailLevel; 
		if (detailLevel == CLASS_METHOD) {
			for (Map.Entry<MethodNode, GraphNode> nodeEntry : methodToGraphNodeMap.entrySet()) {
				nodeEntry.getValue().setText(nodeEntry.getKey().getSimpleMethodName());
			}
		}
		else if (detailLevel == CLASS_METHOD_PARAMETERS) {
			for (Map.Entry<MethodNode, GraphNode> nodeEntry : methodToGraphNodeMap.entrySet()) {
				nodeEntry.getValue().setText(nodeEntry.getKey().getDetailedMethodName());
			}
		}
	}


	/**
	 * Used to alter depth of what graph objects should be seen. For example,
	 * if a user passes in refreshDepth(0), then the user will only see the
	 * root method. If a user passes in refresh(2), then the user will only see
	 * methods that are two degrees of separation from the root node. 
	 * Instead of redrawing the objects, we've put the Graph objects in hashmaps
	 * so that we can iterate through them and set visibilities. This saves
	 * time and increases speed.  
	 * @param depth
	 */
	public void refreshDepth(int depth) {
		int level = depth; 

		if (depth > max_depth) {
			level = max_depth;
		}
		if (depth < 0) {
			level = 0; 
		}

		for (Map.Entry<GraphConnection, Integer> connectionEntry : graphConnectionDepths.entrySet()) {
			if (connectionEntry.getValue() <= level) {
				connectionEntry.getKey().setVisible(true);
			}
			else {
				connectionEntry.getKey().setVisible(false);
			}
		}
		for (Map.Entry<GraphContainer, Integer> containerEntry : graphContainersDepths.entrySet()) {
			if (containerEntry.getValue() <= level) {
				containerEntry.getKey().setVisible(true);
			}
			else {
				containerEntry.getKey().setVisible(false);
			}
		}
		for (Map.Entry<GraphNode, Integer> nodeEntry : graphNodeDepths.entrySet()) {
			if (nodeEntry.getValue() <= level) {
				nodeEntry.getKey().setVisible(true);
			}
			else {
				nodeEntry.getKey().setVisible(false);
			}
		}
	}

	/**
	 * Testing purposes.
	 */
	public MethodNode example() {

		MethodNode main = new MethodNode();

		MethodNode c1f1 = new MethodNode();
		MethodNode c1f2 = new MethodNode();
		MethodNode c2f1 = new MethodNode();
		MethodNode c2f2 = new MethodNode();

		main.setClassName("Main");
		main.setMethodName("main");
		main.setReturnType("int");
		main.addParameter("String");

		c1f1.setClassName("ClassOne");
		c1f1.setMethodName("C1FooOne");
		c1f1.setReturnType("void");

		c1f2.setClassName("ClassOne");
		c1f2.setMethodName("C1FooTwo");
		c1f2.setReturnType("String");

		c2f1.setClassName("ClassTwo");
		c2f1.setMethodName("C2FooOne");
		c2f1.setReturnType("void");
		c2f1.addParameter("int");
		c2f1.addParameter("List<String>");

		c2f2.setClassName("ClassTwo");
		c2f2.setMethodName("C2FooTwo");
		c1f1.setReturnType("int");

		List<MethodNode> maincallers = new ArrayList<MethodNode>();
		maincallers.add(c1f1);
		maincallers.add(c2f1); 
		main.setCallerList(maincallers);

		List<MethodNode> c1f1callers = new ArrayList<MethodNode>();
		c1f1callers.add(c1f2);
		c1f1.setCallerList(c1f1callers);

		List<MethodNode> c1f2callers = new ArrayList<MethodNode>();
		c1f2.setCallerList(c1f2callers);

		List<MethodNode> c2f1callers = new ArrayList<MethodNode>();
		c2f1callers.add(c2f2);
		c2f1.setCallerList(c2f1callers);

		List<MethodNode> c2f2callers = new ArrayList<MethodNode>();
		c2f2.setCallerList(c2f2callers);


		return main; 
	}

} 