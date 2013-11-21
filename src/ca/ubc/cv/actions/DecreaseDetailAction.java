package ca.ubc.cv.actions;

import org.eclipse.jface.action.Action;

import ca.ubc.cv.views.CouplingVisualizerView;

public class DecreaseDetailAction extends Action {

	public DecreaseDetailAction() {
		setToolTipText("Decreases level of detail.");
//		setImageDescriptor(disabledImage);
	}

	@Override
	public void run() {
		CouplingVisualizerView.detailLevel--;
		// reduce 0 to 1 if already 1.
		if (CouplingVisualizerView.detailLevel == 0) {CouplingVisualizerView.detailLevel = 1;}
    	IMethodHandler.execute(IMethodExtractor.lastMethod, CouplingVisualizerView.detailLevel);
	}
}