package ca.ubc.cv.actions;

import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.internal.util.BundleUtility;
import org.osgi.framework.Bundle;

import ca.ubc.cv.views.CouplingVisualizerView;

public class DecreaseDetailAction extends Action {

	public DecreaseDetailAction() {
		setToolTipText("Less Details");
		Bundle bundle = Platform.getBundle("CouplingVisualizer");

		URL enabled_string = BundleUtility.find(bundle, "icons/zoom_out.png");
		setImageDescriptor(ImageDescriptor.createFromURL(enabled_string));

		URL disabled_string = BundleUtility.find(bundle, "icons/zoom_out_disabled.png");
		setDisabledImageDescriptor(ImageDescriptor.createFromURL(disabled_string));
	}

	@Override
	public void run() {
		CouplingVisualizerView.mngc.decreaseDetailLevel();
		if (!CouplingVisualizerView.mngc.canDecreaseDetailLevel()) { CouplingVisualizerView.dla.setEnabled(false); }
		if (CouplingVisualizerView.mngc.canIncreaseDetailLevel()) { CouplingVisualizerView.ila.setEnabled(true); }
	}
}