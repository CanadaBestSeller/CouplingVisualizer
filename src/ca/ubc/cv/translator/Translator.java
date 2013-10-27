package ca.ubc.cv.translator;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ca.ubc.cv.structs.MethodNode;

/**
 * Example Structure of XML tree:
 * 
 * <methodnode>
 * 		<methodname>addStudent</methodname>
 * 		<classname>School</classname>
 * 		<degree>1</degree
 * 		<parameters>
 * 			<parameter>String</parameter>
 * 			<parameter>Integer</parameter>
 * 		</parameters>
 *		<returntype>void</returntype>
 * 		<callers>
 * 			<methodnode>
 * 				<methodname>addListOfStudents</methodname>
 * 				<classname>School</classname>
 * 				<degree>2</degree>
 * 				<parameters>
 * 					<parameter>List<Student></parameter>
 * 				</parameters>
 * 				<returntype>void</returntype>
 * 			</methodnode>
 * 		</callers> 
 * </methodnode>
 * 
 *
 */
public class Translator {

	private static Document doc;
	

	public static void constructXmlMethodTree(MethodNode methodNode) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.newDocument();

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			
			Element rootElement = convertMethodNodeToElement(methodNode);
			doc.appendChild(rootElement);
			
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("C:\\file.xml"));

			transformer.transform(source, result);

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

	public static Element convertMethodNodeToElement(MethodNode methodNode) {
		Element methodElement = doc.createElement(XMLTags.METHOD_NODE_NAME);
		Element methodNameElement = doc.createElement(XMLTags.METHOD_NAME);
		Element classElement = doc.createElement(XMLTags.CLASS_NAME);
		Element degreeElement = doc.createElement(XMLTags.DEGREE_NAME);
		Element parametersElement = doc.createElement(XMLTags.PARAMETERS_LIST_NAME);
		Element returnTypeElement = doc.createElement(XMLTags.RETURN_TYPE_NAME);
		Element callersElement = doc.createElement(XMLTags.CALLERS_LIST_NAME);

		methodNameElement.appendChild(doc.createTextNode(methodNode.getMethodName()));
		classElement.appendChild(doc.createTextNode(methodNode.getClassName()));
		degreeElement.appendChild(doc.createTextNode(Integer.toString(methodNode.getDepth())));
		
		for (Class<?> parameterClass : methodNode.getParameters()) {
			Element parameterElement = doc.createElement(XMLTags.PARAMETER_NAME);
			parameterElement.appendChild(doc.createTextNode(parameterClass.toString()));
			parametersElement.appendChild(parameterElement);
		}
				
		returnTypeElement.appendChild(doc.createTextNode(methodNode.getReturnType().toString()));
		
		methodElement.appendChild(methodNameElement);
		methodElement.appendChild(classElement);
		methodElement.appendChild(degreeElement);
		methodElement.appendChild(callersElement);
		methodElement.appendChild(parametersElement);
		methodElement.appendChild(returnTypeElement);

		for (MethodNode callerMethodNode : methodNode.getCallerList()) {
			callersElement.appendChild(convertMethodNodeToElement(callerMethodNode));
		}
		
		return methodElement; 
		
	}
}
