package ca.ubc.cv.actions;

import java.util.Iterator;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.internal.ui.actions.SelectionConverter;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

public class IMethodExtractor implements IObjectActionDelegate {
	
	private Shell shell;

	/**
	 * Constructor for Action1.
	 */
	public IMethodExtractor() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		try {

			IEditorPart part = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage()
					.getActiveEditor();
			IStructuredSelection lSelection;
			lSelection = SelectionConverter.getStructuredSelection(part);

			if (!lSelection.isEmpty()) {
				Iterator<IJavaElement> lJavaElementIterator = lSelection.iterator();
				IMethod iMethod = (IMethod) lJavaElementIterator.next();

				IMethodHandler.execute(iMethod);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}
}