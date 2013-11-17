package ca.ubc.cv.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphContainer;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

import ca.ubc.cv.treebuilder.MethodNode;

public class MethodNodeToGraphConverter extends ViewPart {
	private Graph graph;

	public Map<MethodNode, GraphNode> methodToGraphMap;
	public Map<String, GraphContainer> classToGraphMap;

	public static final int CLASS_ONLY = 1;
	public static final int CLASS_METHOD = 2; // Default
	public static final int CLASS_METHOD_PARAMETERS = 3;
	
	private Composite parent; 

	public MethodNodeToGraphConverter(Composite parent) {
		this.parent = parent; 
	}
	
	public Graph methodNodeTreeToGraph(MethodNode rootNode, int detailLevel) {
		graph = new Graph(parent, SWT.NONE);
		methodToGraphMap = new HashMap<MethodNode, GraphNode>();
		classToGraphMap = new HashMap<String, GraphContainer>(); 

		int rootDetailLevel;
		if (detailLevel == 3) {
			rootDetailLevel = 3;
		}
		else {
			rootDetailLevel = 2; 
		}

		GraphNode rootGraphNode = getGraphNode(rootNode, rootDetailLevel);
		Display display = Display.getCurrent();
		Color yellow = display.getSystemColor(SWT.COLOR_YELLOW);
		Color orange = display.getSystemColor(SWT.COLOR_LIST_BACKGROUND);
		//Do something special with the rootGraphNode
		rootGraphNode.setBackgroundColor(yellow);
		classToGraphMap.get(rootNode.getClassName()).setBackgroundColor(orange);

		this.constructGraphVisualizer(rootNode, detailLevel);


		graph.setLayoutAlgorithm(new SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
		
		for (GraphContainer gc : classToGraphMap.values()){
			gc.setLayoutAlgorithm(new SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
			gc.open(true);
		}
		return graph; 
	}

	public void constructGraphVisualizer(MethodNode mn, int detailLevel) {
		GraphConnection gc;
		GraphNode fromGn;
		GraphNode toGn;

		for (MethodNode toMn : mn.getCallerList()) {

			if (detailLevel == CLASS_ONLY) {
				GraphContainer fromGc = getGraphContainer(mn);
				GraphContainer toGc = getGraphContainer(toMn);
				if (fromGc.equals(toGc)) continue; 
				gc = new GraphConnection(graph, ZestStyles.CONNECTIONS_DIRECTED, toGc,
						fromGc);
			}
			else {
				fromGn = getGraphNode(mn, detailLevel);
				toGn = getGraphNode(toMn, detailLevel);

				gc = new GraphConnection(graph, ZestStyles.CONNECTIONS_DIRECTED, toGn,
						fromGn);
			}
			gc.changeLineColor(parent.getDisplay().getSystemColor(SWT.COLOR_BLACK));
			constructGraphVisualizer(toMn, detailLevel);
		}
	}

	public GraphNode getGraphNode(MethodNode mn, int detailLevel) {
		GraphNode gn = methodToGraphMap.get(mn);
		if (gn == null) {
			switch (detailLevel){

			case(CLASS_METHOD_PARAMETERS):
				gn = new GraphNode(getGraphContainer(mn), SWT.NONE, mn.getDetailedMethodName()); 
			break;
			default:
				gn = new GraphNode(getGraphContainer(mn), SWT.NONE, mn.getSimpleMethodName());
				break; 

			}
			methodToGraphMap.put(mn, gn);
		}

		return gn; 
	}

	public GraphContainer getGraphContainer(MethodNode mn) {
		GraphContainer gc = classToGraphMap.get(mn.getClassName()); 
		if (gc == null) {
			gc = new GraphContainer(graph, SWT.NONE, mn.getClassName());
			classToGraphMap.put(mn.getClassName(), gc);
		}

		return gc; 
	}

	public void createPartControl(Composite parent) {
		this.parent = parent; 
		graph = methodNodeTreeToGraph(example(), 1);
		graph.setLayoutAlgorithm(new SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
	}
	
	
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

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
	}
} 