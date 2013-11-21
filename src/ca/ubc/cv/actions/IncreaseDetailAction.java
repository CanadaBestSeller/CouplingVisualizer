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
		setToolTipText("More Details");
		Bundle bundle = Platform.getBundle("CouplingVisualizer");

		URL enabled_string = BundleUtility.find(bundle, "icons/zoom_in.png");
		setImageDescriptor(ImageDescriptor.createFromURL(enabled_string));

		URL disabled_string = BundleUtility.find(bundle, "icons/zoom_in_disabled.png");
		setDisabledImageDescriptor(ImageDescriptor.createFromURL(disabled_string));
	}

	@Override
	public void run() {
		CouplingVisualizerView.mngc.increaseDetaillevel();
		if (!CouplingVisualizerView.mngc.canIncreaseDetailLevel()) { CouplingVisualizerView.ila.setEnabled(false); }
		if (CouplingVisualizerView.mngc.canDecreaseDetailLevel()) { CouplingVisualizerView.dla.setEnabled(true); }
	}
}