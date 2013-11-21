package ca.ubc.cv.actions;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;

import ca.ubc.cv.CouplingVisualizer;
import ca.ubc.cv.graph.MethodNodeToGraphConverter;
import ca.ubc.cv.treebuilder.MethodNode;
import ca.ubc.cv.treebuilder.MethodTreeBuilder;
import ca.ubc.cv.views.CouplingVisualizerView;

public class IMethodHandler {

	public static void execute(IMethod m) {
	    CouplingVisualizerView.clearGraph();
	    
	    MethodTreeBuilder chg = new MethodTreeBuilder();
        MethodNode rootMethodNode = chg.constructMethodTree(m);
        
        CouplingVisualizerView.mngc.methodNodeTreeToGraph(rootMethodNode, MethodNodeToGraphConverter.CLASS_METHOD);

		CouplingVisualizerView.graph.setLayoutAlgorithm(new SpringLayoutAlgorithm(
				LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
		// Selection listener on graphConnect or GraphNode is not supported
		// see https://bugs.eclipse.org/bugs/show_bug.cgi?id=236528
		CouplingVisualizerView.graph.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println(e);
			}

		});
	}
}
