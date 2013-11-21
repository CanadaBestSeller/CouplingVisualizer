package ca.ubc.cv.actions;

import org.eclipse.jface.action.Action;

import ca.ubc.cv.views.CouplingVisualizerView;

public class IncreaseDetailAction extends Action {

	public IncreaseDetailAction() {
		setToolTipText("Increases level of detail.");
//		setImageDescriptor(disabledImage);
	}

	@Override
	public void run() {
		CouplingVisualizerView.detailLevel++;
		// reduce 4 to 3 if already 3.
		if (CouplingVisualizerView.detailLevel == 4) {CouplingVisualizerView.detailLevel = 3;}
    	IMethodHandler.execute(IMethodExtractor.lastMethod, CouplingVisualizerView.detailLevel);
	}
}