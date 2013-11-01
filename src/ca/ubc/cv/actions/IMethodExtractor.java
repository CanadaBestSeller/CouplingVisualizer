
package ca.ubc.cv.actions;

import java.util.Iterator;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.action.IMenuCreator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class IMethodExtractor implements IObjectActionDelegate, IMenuCreator, IEditorActionDelegate {

	//private Shell shell;
	private ISelection aSelection;
	private Menu aMenu;
	private IEditorPart aJavaEditor;
	private static final String SHOW_METHOD_DEPENDENCIES = "Show Method Dependencies/Dependents";
	private static final String SHOW_UML_DIAGRAM = "Show UML Diagram";
	
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
		//shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
//		System.out.println("RUNNING THE ACTION COMMAND IN TESTACTION");
//		MessageDialog.openInformation(
//			shell,
//			"TestWorld",
//			"New Action2 was executed.");
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged( IAction action, ISelection selection ) {
		action.setMenuCreator( this );
		aSelection = selection;
	}
	
	/**
	 * Populates the pulldown menu item when right clicking on code
	 */
	private void fillMenu()
	{
		MenuItem showMethodDependencies = new MenuItem( aMenu, SWT.PUSH );
		showMethodDependencies.setText( SHOW_METHOD_DEPENDENCIES );
		//showMethodDependencies.addSelectionListener( new ShowDependenciesMenuItemListener() )
		
		MenuItem showUmlDiagram = new MenuItem( aMenu, SWT.PUSH );
		showUmlDiagram.setText( SHOW_UML_DIAGRAM );
		//showUmlDiagram.addSelectionListener( new ShowUmlMenuItemListener() )
	}

	@Override
	public void dispose() {
		// No need to dispose of menu items, since they're permanent.
	    if (aMenu != null)
	    {
	      aMenu.dispose();
	    }
	}

	@Override
	public Menu getMenu(Control arg0) {
		return null;
	}
	
	/**
	 * @see org.eclipse.jface.action.IMenuCreator#getMenu(org.eclipse.swt.widgets.Menu)
	 * @param pParent See above.
	 * @return See above.
	 */
	public Menu getMenu( Menu pParent )
	{
		aMenu = new Menu( pParent );
		fillMenu();
		return aMenu;
	}

	@Override
	public void setActiveEditor(IAction arg0, IEditorPart arg1) {
	}
	
}
