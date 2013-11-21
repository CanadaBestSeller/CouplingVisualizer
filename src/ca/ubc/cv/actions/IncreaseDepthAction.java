package ca.ubc.cv.actions;

import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.internal.util.BundleUtility;
import org.osgi.framework.Bundle;

import ca.ubc.cv.views.CouplingVisualizerView;

public class IncreaseDepthAction extends Action {

	public IncreaseDepthAction() {
		setToolTipText("Increase Depth");
		Bundle bundle = Platform.getBundle("CouplingVisualizer");

		URL enabled_string = BundleUtility.find(bundle, "icons/increase_depth.png");
		setImageDescriptor(ImageDescriptor.createFromURL(enabled_string));

		URL disabled_string = BundleUtility.find(bundle, "icons/increase_depth_disabled.png");
		setDisabledImageDescriptor(ImageDescriptor.createFromURL(disabled_string));
	}

	@Override
	public void run() {
		CouplingVisualizerView.mngc.increaseDepthLevel();
		if (!CouplingVisualizerView.mngc.canIncreaseDepthLevel()) { CouplingVisualizerView.iha.setEnabled(false); }
		if (CouplingVisualizerView.mngc.canDecreaseDepthLevel()) { CouplingVisualizerView.dha.setEnabled(true); }
	}
}