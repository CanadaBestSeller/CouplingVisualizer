package ca.ubc.cv.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

import ca.ubc.cv.actions.DecreaseDepthAction;
import ca.ubc.cv.actions.DecreaseDetailAction;
import ca.ubc.cv.actions.IncreaseDepthAction;
import ca.ubc.cv.actions.IncreaseDetailAction;
import ca.ubc.cv.graph.MethodNodeToGraphConverter;

public class CouplingVisualizerView extends ViewPart {
	public static final String ID = "ca.ubc.cv.views.couplingvisualizerview";
	public static Graph graph;
	public static MethodNodeToGraphConverter mngc;
	public static Composite parent;
	private static int layout = 1;

	public void createPartControl(Composite compositeParent) {
		// Graph will hold all other objects
		graph = new Graph(compositeParent, SWT.NONE);
		parent = compositeParent;
		mngc = new MethodNodeToGraphConverter(CouplingVisualizerView.parent, CouplingVisualizerView.graph);
		
		// Add buttons
		IActionBars bars = getViewSite().getActionBars();
		bars.getToolBarManager().add(new IncreaseDepthAction());
		bars.getToolBarManager().add(new DecreaseDepthAction());
		bars.getToolBarManager().add(new IncreaseDetailAction());
		bars.getToolBarManager().add(new DecreaseDetailAction());
	}

	public static void setLayoutManager() {
		switch (layout) {
		case 1:
			graph.setLayoutAlgorithm(new TreeLayoutAlgorithm(
					LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
			layout++;
			break;
		case 2:
			graph.setLayoutAlgorithm(new SpringLayoutAlgorithm(
					LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
			layout = 1;
			break;

		}

	}

	public static void clearGraph() {

		// remove all the connections
		Object[] objects = graph.getConnections().toArray();

		for (int x = 0; x < objects.length; x++) {
			((GraphConnection) objects[x]).dispose();
		}

		// remove all the nodes
		objects = graph.getNodes().toArray();

		for (int x = 0; x < objects.length; x++) {
			((GraphNode) objects[x]).dispose();
		}

	}

	/** * Passing the focus request to the viewer's control. */

	public void setFocus() {
	}
}