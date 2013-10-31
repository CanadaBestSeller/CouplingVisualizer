/* ConcernMapper - A concern modeling plug-in for Eclipse
 * Copyright (C) 2006  McGill University (http://www.cs.mcgill.ca/~martin/cm)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * $Revision: 1.3 $
 */

package ca.ubc.cv.xmlbuilder;

/**
 * Stores tags for elements and attibutes in the XML representation
 * of concerns.
 */
public class XmlTags 
{
	public static final String METHOD_NODE_NAME = "methodnode";
	public static final String METHOD_NAME = "methodname";
	public static final String CLASS_NAME = "class";
	public static final String DEGREE_NAME = "degree";
	public static final String CALLERS_LIST_NAME = "callers";
	public static final String CALLER_NAME = "caller";
	public static final String PARAMETERS_LIST_NAME = "parameters";
	public static final String PARAMETER_NAME = "parameter";
	public static final String RETURN_TYPE_NAME = "returntype";
	
	/**
	 * XML Elements.
	 */
	public enum Elements
	{
		METHOD, CLASSNAME, DEGREE, CALLERSLIST, PARAMETERSLIST, RETURNTYPE; 
		
		/** 
		 * @see java.lang.Enum#toString()
		 * {@inheritDoc}
		 */
		public String toString()
		{
			return name().toLowerCase();
		}
	}
	
}
