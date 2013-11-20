package ca.ubc.cv.treebuilder;

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
	private List<String> parametersList;
	private String returnType; 
	private String javaDocs; 
	
	public MethodNode() {
		callersList = new ArrayList<MethodNode>(); 
		parametersList = new ArrayList<String>(); 
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
	public String getSimpleMethodName() {
		return methodName + "()";
	}
	public String getDetailedMethodName() {
		String mnName = methodName + "(";
		List<String> params = this.getParameters();
		for (int i = 0; i < params.size(); i++) {
			if (i != params.size() - 1) {
				mnName += params.get(i) + ", "; 
			}
			else {
				mnName += params.get(i); 
			}
		}
		mnName += ") : " + this.getReturnType();
		return mnName; 
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public void addCaller(MethodNode m) {
		this.callersList.add(m); 
	}
	public List<MethodNode> getCallerList() {
		return callersList;
	}
	public void setCallerList(List<MethodNode> nodes) {
		this.callersList = nodes;
	}
	public List<String> getParameters() {
		return parametersList;
	}
	public void setParameters(List<String> parameters) {
		this.parametersList = parameters;
	}
	public void addParameter(String parameter) {
		this.parametersList.add(parameter);
	}
	public String getReturnType() {
		if (returnType == null) return "void"; 
		return returnType; 
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public void addParameterType(String parameterType){
		this.parametersList.add(parameterType);
	}
	public String getParametersAndReturnTypeInfo() {
		String methodInfo = "";
		if (this.parametersList.isEmpty()) {
			methodInfo += this.getSimpleMethodName() + " does not take in any parameters.";
		}
		else {
			methodInfo += this.getSimpleMethodName() + " takes in the follwing parameter(s): ";
			for (int i = 0; i < this.parametersList.size(); i++) {
				if (i == this.parametersList.size() - 1) {
					methodInfo += this.parametersList.get(i);
				}
				else {
					methodInfo += this.parametersList.get(i) + ", ";
				}
			}
		}
		
		methodInfo += "\n\n";
		if (this.getReturnType().equals("void")){
			methodInfo += this.getSimpleMethodName() + " returns nothing";
		}
		else {
			methodInfo += this.getSimpleMethodName() + " returns the following: " + this.getReturnType(); 
		}
		return methodInfo; 
	}
	public void setJavaDocs(String javaDocs) {
		this.javaDocs = javaDocs; 
	}
	public String getJavaDocs() {
		return javaDocs; 
	}
	
	@Override
	public String toString() {
		String val =
			"Method Name: " + methodName + " | " +
			"Class Name: " + className + " | " +
			"Parameter Types: ("; 
		
		for (int i = 0; i < parametersList.size(); i++) {
			if (i != parametersList.size() - 1) {
				val += parametersList.get(i) + " | ";
			}
			else { 
				val += parametersList.get(i); 
			}
		}
		
		val += ") | ";
		
		val += 
			"Return Type: " + returnType; 
			
		return val; 
		
	}
	@Override
	public boolean equals(Object obj) {
		MethodNode mnobj = (MethodNode) obj;
		
		if(!this.getMethodName().equals(mnobj.getMethodName())) return false;
		if(!this.getClassName().equals(mnobj.getClassName())) return false; 
		if(!this.getReturnType().equals(mnobj.getReturnType())) return false;
		if(!this.getParameters().equals(mnobj.getParameters())) return false;
		
		return true; 
	}
	
	/*
	 * Prints the subtree of this MethodNode. 
	 */
	public String subTreeToString(int depth) {
		String spacing = ""; 
		for (int i = 0; i < depth; i++) {
			spacing += "  "; 
		}
		
		String val = spacing + this.toString(); 
		val += "\n";
		
		
		for (MethodNode mn : this.getCallerList()) {
			val += mn.subTreeToString(depth + 1);
		}
		return val; 
	}
}
