package ca.ubc.cv.treebuilder;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.internal.corext.callhierarchy.CallHierarchy;
import org.eclipse.jdt.internal.corext.callhierarchy.MethodWrapper;


@SuppressWarnings("restriction")
public class MethodTreeBuilder {
	
	private Set<MethodNode> methodNodeSet; 
	
	public MethodTreeBuilder() {
		methodNodeSet = new HashSet<MethodNode>(); 
	}
	
	/*
	 * Constructs (recursively) the MethodNode tree
	 * TODO: Check for cycles (eg. foo1 might use foo2, which
	 * would use foo1. Currently, it doesn't check for that. 
	 */
	public MethodNode constructMethodTree(IMethod iMethod) {
		MethodNode methodNode = constructMethodNode(iMethod);
		Set<IMethod> iMethodSet = new HashSet<IMethod>();   
        iMethodSet = this.getCallersOf(iMethod);

        methodNodeSet.add(methodNode); 
		
		for (IMethod im : iMethodSet) {
			MethodNode mn = constructMethodTree(im); 
			methodNode.addCaller(mn); 
		}
		
		return methodNode; 
	}
	
	/*
	 * Temporary method. Used to determine if m already has
	 * n has a child. 
	 * 
	 * TODO: Migrate this method over to MethodNode. THat's where
	 * it belongs.
	 * TODO: Actually, maybe not. Think about this some more. 
	 */
	public boolean contains(MethodNode m, MethodNode n) {
		return false; 
	}
	
	
	/*
	 * Constructs a MethodNode from the given IMethod
	 */
	public MethodNode constructMethodNode(IMethod m) {
		
		MethodNode methodNode = new MethodNode();
		
		String methodName = m.getElementName(); 
		methodNode.setMethodName(methodName);
		
		String className = m.getDeclaringType().getElementName();
		methodNode.setClassName(className);
		
		String returnType;
		try {
			returnType = Signature.toString(m.getReturnType());
			methodNode.setReturnType(returnType);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String[] parameterTypes = m.getParameterTypes();
		for (int j=0; j<parameterTypes.length; ++j) {
			String parameterType = Signature.toString(parameterTypes[j]);
			
			methodNode.addParameterType(parameterType);
		}
		
		
		return methodNode; 
	}
	

	public HashSet<IMethod> getCallersOf(IMethod m) {

		CallHierarchy callHierarchy = CallHierarchy.getDefault();

		IMember[] members = { m };

		MethodWrapper[] methodWrappers = callHierarchy.getCallerRoots(members);
		HashSet<IMethod> callers = new HashSet<IMethod>();
		for (MethodWrapper mw : methodWrappers) {
			MethodWrapper[] mw2 = mw.getCalls(new NullProgressMonitor());
			HashSet<IMethod> temp = getIMethods(mw2);
			callers.addAll(temp);
		}

		return callers;
	}

	public HashSet<IMethod> getIMethods(MethodWrapper[] methodWrappers) {
		HashSet<IMethod> c = new HashSet<IMethod>();
		for (MethodWrapper m : methodWrappers) {
			IMethod im = getIMethodFromMethodWrapper(m);
			if (im != null) {
				c.add(im);
			}
		}
		return c;
	}

	public IMethod getIMethodFromMethodWrapper(MethodWrapper m) {
		try {
			IMember im = m.getMember();
			if (im.getElementType() == IJavaElement.METHOD) {
				return (IMethod) m.getMember();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}