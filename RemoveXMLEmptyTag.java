import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class RemoveXMLEmptyTag {

	public static void main(String[] args) {
		String xml = "    <form1>\n" +
                "        <GenInfo>\n" +
                "            <Section1>\n" +
                "                <EmployeeDet>\n" +
                "                    <Title>999990000</Title>\n" +
                "                    <Firstname>MIKE</Firstname>\n" +
                "                    <Surname>SPENCER</Surname>\n" +
                "                    <CoName/>\n" +
                "                    <EmpAdd>\n" +
                "                        <Address><Add1/><Add2/><Town/><County/><Pcode/></Address>\n" +
                "                    </EmpAdd>\n" +
                "                    <PosHeld>DEVELOPER</PosHeld>\n" +
                "                    <Email/>\n" +
                "                    <ConNo/>\n" +
                "                    <Nationality/>\n" +
                "                    <PPSNo/>\n" +
                "                    <EmpNo/>\n" +
                "                </EmployeeDet>\n" +
                "            </Section1>\n" +
                "        </GenInfo>\n" +
                "    </form1>";
		
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(xml)));
			
			removeEmptyNodes(doc.getDocumentElement());
			
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			
			System.out.println(writer.getBuffer().toString());
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	private static void removeEmptyNodes(Node node) {
		NodeList nodeList = node.getChildNodes();
		for(int i=0; i < nodeList.getLength(); i++){
			Node childNode = nodeList.item(i);
			if(childNode.getTextContent() != null && childNode.getTextContent().trim().equals("")){
				childNode.getParentNode().removeChild(childNode);
				i--;
			}
			removeEmptyNodes(childNode);
		}
	}
}
