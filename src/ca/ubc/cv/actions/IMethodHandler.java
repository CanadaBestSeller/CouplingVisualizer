package ca.ubc.cv.actions;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;

import ca.ubc.cv.views.CouplingVisualizerView;

public class IMethodHandler {

	public static void execute(IMethod m) {
		System.out.println("IMETHOD name is: " + m.toString());
		
	    CouplingVisualizerView.clearGraph();

		GraphNode node1 = new GraphNode(CouplingVisualizerView.graph, SWT.NONE, "Jim");
		GraphNode node2 = new GraphNode(CouplingVisualizerView.graph, SWT.NONE, "Jack");
		GraphNode node3 = new GraphNode(CouplingVisualizerView.graph, SWT.NONE, "Joe");
		GraphNode node4 = new GraphNode(CouplingVisualizerView.graph, SWT.NONE, "Bill");

		// Lets have a directed connection
		new GraphConnection(CouplingVisualizerView.graph, ZestStyles.CONNECTIONS_DIRECTED, node1,
				node2);
		// Lets have a dotted graph connection
		new GraphConnection(CouplingVisualizerView.graph, ZestStyles.CONNECTIONS_DOT, node2, node3);
		// Standard connection
		new GraphConnection(CouplingVisualizerView.graph, SWT.NONE, node3, node1);
		// Change line color and line width
		GraphConnection graphConnection = new GraphConnection(CouplingVisualizerView.graph, SWT.NONE,
				node1, node4);
		// Also set a text
		graphConnection.setText("This is a text");
		graphConnection.setLineWidth(3);

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
