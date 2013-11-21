package ca.ubc.cv.actions;

import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.internal.util.BundleUtility;
import org.osgi.framework.Bundle;

import ca.ubc.cv.views.CouplingVisualizerView;

public class IncreaseDetailAction extends Action {

	public IncreaseDetailAction() {
		setToolTipText("Increases level of detail.");
		Bundle bundle = Platform.getBundle("CouplingVisualizer");
		URL fullPathString = BundleUtility.find(bundle, "icons/zoom_in.png");
		setImageDescriptor(ImageDescriptor.createFromURL(fullPathString));
	}

	@Override
	public void run() {
		CouplingVisualizerView.mngc.increaseDetaillevel();
	}
}