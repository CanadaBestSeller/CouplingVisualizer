package ca.ubc.cv.structs;

import java.util.ArrayList;
import java.util.List;


/**
 * This node structure abstracts the IMethod object
 * found in a call hierarchy tree. The Node class contains
 * following fields:
 * 
 * className - the class that the method belongs to. 
 * depth - the degrees of separation from the main method
 * 		 that you are interested in. 
 * nodesList - its list of children, which represent the list of
 * 		IMethods that call it. 
 * parametersList - the list of parameters the node takes. 
 *
 */
public class MethodNode {

	private String methodName; 
	private String className;
	private int degree;
	private List<MethodNode> callersList; 
	private List<Class<?>> parametersList;
	private Class<?> returnType; 
	
	public MethodNode() {
		callersList = new ArrayList<MethodNode>(); 
		parametersList = new ArrayList<Class<?>>(); 
	}
	
	public int getDepth() {
		return degree;
	}
	public void setDepth(int depth) {
		this.degree = depth;
	}
	public String getMethodName() {
		return methodName; 
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName; 
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public List<MethodNode> getCallerList() {
		return callersList;
	}
	public void setCallerList(List<MethodNode> nodes) {
		this.callersList = nodes;
	}
	public List<Class<?>> getParameters() {
		return parametersList;
	}
	public void setParameters(List<Class<?>> parameters) {
		this.parametersList = parameters;
	}
	public Class<?> getReturnType() {
		return returnType; 
	}
	public void setReturnType(Class<?> returnType) {
		this.returnType = returnType;
	}
	
	
}
