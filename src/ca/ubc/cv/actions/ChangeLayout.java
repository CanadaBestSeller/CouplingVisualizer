package ca.ubc.cv.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.handlers.HandlerUtil;

import ca.ubc.cv.views.CouplingVisualizerView;

public class ChangeLayout extends AbstractHandler {

  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    IViewPart findView = HandlerUtil.getActiveWorkbenchWindow(event)
        .getActivePage().findView("ca.ubc.cv.views.couplingvisualizerview");
    CouplingVisualizerView cpv = (CouplingVisualizerView) findView;
    cpv.setLayoutManager();
    return null;
  }
} 