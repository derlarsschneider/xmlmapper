package de.consutor.xmlmapper.xml.shiporder;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Random;

import javax.xml.bind.JAXB;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.consutor.xmlmapper.xml.shiporder.Shiporder.Item;

@ContextConfiguration(locations = { "/spring/application.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ShipOrderModelTest {
	private static long orderId = 1;

	private static long getOrderId() {
		return orderId++;
	}

	@Test
	public void testUnmarshal() throws SAXException, IOException,
			ParserConfigurationException, TransformerException {
		String inXML = "xsd/shiporder.xml";
		Shiporder shiporder = JAXB.unmarshal(new File(inXML), Shiporder.class);
		assertNotNull(shiporder);
		String outXML = "xsd/shiporder.out.xml";
		JAXB.marshal(shiporder, new File(outXML));
		compareXML(inXML, outXML);
	}

	@Test
	public void testMarshal() {
		DateTime start = DateTime.now();
		ObjectFactory f = new ShiporderFactory();
		Shiporder shiporder = f.createShiporder();
		shiporder.setOrderid("" + getOrderId());
		int items = 30000;
		for (int i = 0; i < items; i++) {
			if (i % (items / 100) == 0) {
				System.out.println(i / (items / 100) + "%");
			}
			Random random = new Random();
			String note = createRandomString(50);
			DecimalFormat df = new DecimalFormat("#.##",
					DecimalFormatSymbols.getInstance(Locale.ENGLISH));
			String p = df.format(random.nextDouble() * 1000);
			BigDecimal price = new BigDecimal(p);
			BigInteger quantity = new BigInteger("" + random.nextInt(10));
			String title = createRandomString(20);
			Item item = f.createShiporderItem(title, note, quantity, price);

			shiporder.getItem().add(item);
		}
		long pojoTime = DateTime.now().minus(start.getMillis()).getMillis();
		System.out.println(pojoTime + "ms for creation of pojo.");
		System.out.println(new Double(pojoTime) / items
				+ "ms for creation of each pojo.");
		String outXML = "xsd/shiporder.bigrandom.xml";
		JAXB.marshal(shiporder, new File(outXML));
		long marshalTime = DateTime.now().minus(start.getMillis()).getMillis()
				- pojoTime;
		System.out.println(marshalTime + "ms for marshalling.");
		System.out.println(new Double(marshalTime) / items
				+ "ms for marshalling of each pojo.");
	}

	private String createRandomString(int size) {
		char[] chars = "abcdefghijklmnopqrstuvwxyz    ".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < size; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		return sb.toString();
	}

	private void compareXML(String file1, String file2) throws SAXException,
			IOException, ParserConfigurationException, TransformerException {

		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document document1 = docBuilder.parse(new File(file1));
		Document document2 = docBuilder.parse(new File(file2));
		assertTrue(compareNodes(document1.getDocumentElement(),
				document2.getDocumentElement()));
	}

	private boolean compareNodes(Node node1, Node node2) {
		// do something with the current node instead of System.out
		if (!node1.getNodeName().equals(node2.getNodeName())) {
			System.err.println(node1.getNodeName() + " should be equal to "
					+ node2.getNodeName());
			return false;
		}

		NodeList nodeList1 = node1.getChildNodes();
		NodeList nodeList2 = node2.getChildNodes();
		if (nodeList1.getLength() != nodeList2.getLength()) {
			System.err.println("Lists do not have the same size ("
					+ nodeList1.getLength() + " and " + nodeList2.getLength()
					+ ").");
			return false;
		}
		for (int i = 0; i < nodeList1.getLength(); i++) {
			Node currentNode1 = nodeList1.item(i);
			Node currentNode2 = nodeList2.item(i);
			if (currentNode1.getNodeType() != currentNode2.getNodeType()) {
				System.err.println(currentNode1.getNodeType()
						+ " (node type) should be equal to "
						+ currentNode2.getNodeType());
				return false;
			} else if (currentNode1.getNodeType() == Node.ELEMENT_NODE) {
				// calls this method for all the children which is Element
				if (!compareNodes(currentNode1, currentNode2)) {
					return false;
				}
			} else if (currentNode1.getNodeType() == Node.TEXT_NODE) {
				if (!currentNode1.getTextContent().trim()
						.equals(currentNode2.getTextContent().trim())) {
					System.err.println(currentNode1.getTextContent().trim()
							+ " (content) should be equal to "
							+ currentNode2.getTextContent().trim());
					return false;
				}
			}
		}
		return true;
	}
}
