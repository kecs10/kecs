package util;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XMLParser {
	
	public HashMap getNodeListValue (String strXML, String xpath, int index)
	{
		HashMap returnMap = null;
		try
		{
			Document document = validateReceipt(strXML);
			NodeList nodes = XPathAPI.selectNodeList(document, xpath);
			NodeList sun;
			NodeList children;
			Node node;
			Node sunNode;
			Node childNode;
			// value를 리턴하기 위해서 string 변수를 선언
			String value = null;

			// 여러개의 element에서 하나의 태그가 빠지면 nodes.getLength()의 갯수가 아예 줄어든다
			// al.add(Integer.toString(nodes.getLength()));
			for (int i = 0; i < nodes.getLength(); i++)
			{
				returnMap = new HashMap();
				
				if (index >= 0 && i != index) continue;

				node = nodes.item(i);

				if (node.hasChildNodes())
				{
					// child node가 있는 node type은 element와 document뿐이다
					if (node.getNodeType() == Node.ELEMENT_NODE)
					{
						sun = node.getChildNodes();
						
						for (int k = 0; k < sun.getLength(); k++) {
							sunNode = sun.item(k);
							
							if (sunNode.getNodeType() == Node.ELEMENT_NODE) {
								System.out.println("sunNode.getNodeName() : " + sunNode.getNodeName());
								// Element 인 경우에는 자식노드를 검색해서 자식 노드중 텍스트 노드, 그러니까
								// 태그 사이의 텍스트 값을 리턴하자 일단.
								children = sunNode.getChildNodes();
		
								for (int j = 0; j < children.getLength(); j++)
								{
									childNode = children.item(j);
		
									if (childNode.getNodeType() == Node.TEXT_NODE)
									{
										// 자식노드를 순환하다가 TextNode 발견하면 value로 세팅.
										// out.println(childNode.getNodeName());
										value = childNode.getNodeValue();
		//								cScript.doLog("logs","childNode.getNodeValue().TEXT_NODE : " + value);
		//								dec.setValue(value);
//										System.out.println("value : " + value);
										returnMap.put(sunNode.getNodeName(), value);
									}else if (childNode.getNodeType() == Node.CDATA_SECTION_NODE)
									{
										// 자식노드를 순환하다가 TextNode 발견하면 value로 세팅.
										// out.println(childNode.getNodeName());
										value = childNode.getNodeValue();
		//								cScript.doLog("logs","childNode.getNodeValue().CDATA_SECTION_NODE : " + value);
		//								dec.setValue(value);
//										System.out.println("value : " + value);
										returnMap.put(sunNode.getNodeName(), value);
									}
								}
							}
						}
					}
					else
					{
						/*
						<personnel>
						 	<person id="Big.Boss">
						 		<name><family>Boss</family> <given>Big</given></name>
						 		<email>chief@foo.com</email>
						 		<link subordinates="one.worker two.worker three.worker four.worker five.worker"/>
						    </person>
					    </personnel>
					    -- person이나 name일 경우 값이 없다.
					    */
						// Document 인 경우에는 Node value라는 개념이 애매하니까..
						// 전체 String 을 리턴하거나 null값을리턴해야 하는데 일단 null을 리턴하기로 하자.
						// Do nothing
					}
				}
				else
				{
					// 자식노드가 없는 Attribute나 Text노드, CDATASection등의 값을 질의한 경우. getNodeValue를 이용.
					value = node.getNodeValue();
//					cScript.doLog("logs","node.getNodeValue() : " + value);
//					dec.setValue(value);
					System.out.println("value : " + value);
				}
			}

			return returnMap;
		}
		catch(Exception e)
		{
			LogWrapper.biz.error("Parsing Error!!! : " + Utils.getLogStackTrace(e));
            return returnMap;
		}
	}
	
	
	public void getNodeValue (String strXML, String xpath, int index)
	{
		try
		{
			Document document = validateReceipt(strXML);
			NodeList nodes = XPathAPI.selectNodeList(document, xpath);
			NodeList children;
			Node node;
			Node childNode;
			// value를 리턴하기 위해서 string 변수를 선언
			String value = null;
			
			// 여러개의 element에서 하나의 태그가 빠지면 nodes.getLength()의 갯수가 아예 줄어든다
			// al.add(Integer.toString(nodes.getLength()));
			for (int i = 0; i < nodes.getLength(); i++)
			{
				if (index >= 0 && i != index) continue;
				
				node = nodes.item(i);
				
				if (node.hasChildNodes())
				{
					// child node가 있는 node type은 element와 document뿐이다
					if (node.getNodeType() == Node.ELEMENT_NODE)
					{
						// Element 인 경우에는 자식노드를 검색해서 자식 노드중 텍스트 노드, 그러니까
						// 태그 사이의 텍스트 값을 리턴하자 일단.
						children = node.getChildNodes();
						
						for (int j = 0; j < children.getLength(); j++)
						{
							childNode = children.item(j);
							
							if (childNode.getNodeType() == Node.TEXT_NODE)
							{
								// 자식노드를 순환하다가 TextNode 발견하면 value로 세팅.
								// out.println(childNode.getNodeName());
								value = childNode.getNodeValue();
//								cScript.doLog("logs","childNode.getNodeValue().TEXT_NODE : " + value);
//								dec.setValue(value);
							}else if (childNode.getNodeType() == Node.CDATA_SECTION_NODE)
							{
								// 자식노드를 순환하다가 TextNode 발견하면 value로 세팅.
								// out.println(childNode.getNodeName());
								value = childNode.getNodeValue();
//								cScript.doLog("logs","childNode.getNodeValue().CDATA_SECTION_NODE : " + value);
//								dec.setValue(value);
							}
						}
					}
					else
					{
					}
				}
				else
				{
					// 자식노드가 없는 Attribute나 Text노드, CDATASection등의 값을 질의한 경우. getNodeValue를 이용.
					value = node.getNodeValue();
					System.out.println("value : " + value);
				}
			}
			
		}
		catch(Exception e)
		{
			LogWrapper.biz.error("Parsing Error!!! : " + Utils.getLogStackTrace(e));
		}
	}
	
	private Document validateReceipt(String strXML)
	{
		boolean validation			= false;
		boolean ignoreWhitespace	= false;
		boolean ignoreComments		= false;
		boolean putCDATAIntoText	= false;
		boolean createEntityRefs	= false;


		Document doc= null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		dbf.setValidating(validation);

/*
		dbf.setIgnoringComments(ignoreComments);
		dbf.setCoalescing(putCDATAIntoText);
		dbf.setExpandEntityReferences(!createEntityRefs);
*/



		DocumentBuilder db = null;

		try 
		{
			db = dbf.newDocumentBuilder();
		} 
		catch (ParserConfigurationException pce) 
		{
			return null;
		}



		try 
		{
			StringReader sr = new StringReader(strXML); 

			InputSource	inSource = new InputSource();
			inSource.setCharacterStream(sr); 

			doc = db.parse(inSource);
		} 
		catch (SAXParseException err) 
		{ 
			return null;
		} 
		catch (SAXException se) 
		{
			return null;
		} 
		catch (IOException ioe) 
		{
			return null;
		}

		return doc;
	}
	
	public ArrayList getNodeListMaps (String strXML, String xpath, int index)
	{
		ArrayList returnList = null;
		HashMap returnMap = null;
		try
		{
			Document document = validateReceipt(strXML);
			NodeList nodes = XPathAPI.selectNodeList(document, xpath);
			NodeList sun;
			NodeList children;
			Node node;
			Node sunNode;
			Node childNode;
			// value를 리턴하기 위해서 string 변수를 선언
			String value = null;
			returnList = new ArrayList();

			// 여러개의 element에서 하나의 태그가 빠지면 nodes.getLength()의 갯수가 아예 줄어든다
			// al.add(Integer.toString(nodes.getLength()));
			LogWrapper.biz.debug("nodes.getLength() : " + nodes.getLength());
			for (int i = 0; i < nodes.getLength(); i++)
			{
				returnMap = new HashMap();
				
				if (index >= 0 && i != index) continue;

				node = nodes.item(i);

				if (node.hasChildNodes())
				{
					// child node가 있는 node type은 element와 document뿐이다
					if (node.getNodeType() == Node.ELEMENT_NODE)
					{
						sun = node.getChildNodes();
						
						for (int k = 0; k < sun.getLength(); k++) {
							sunNode = sun.item(k);
							
							if (sunNode.getNodeType() == Node.ELEMENT_NODE) {
								System.out.println("sunNode.getNodeName() : " + sunNode.getNodeName());
								// Element 인 경우에는 자식노드를 검색해서 자식 노드중 텍스트 노드, 그러니까
								// 태그 사이의 텍스트 값을 리턴하자 일단.
								children = sunNode.getChildNodes();
		
								for (int j = 0; j < children.getLength(); j++)
								{
									childNode = children.item(j);
		
									if (childNode.getNodeType() == Node.TEXT_NODE)
									{
										// 자식노드를 순환하다가 TextNode 발견하면 value로 세팅.
										// out.println(childNode.getNodeName());
										value = childNode.getNodeValue();
		//								cScript.doLog("logs","childNode.getNodeValue().TEXT_NODE : " + value);
		//								dec.setValue(value);
//										System.out.println("value : " + value);
										returnMap.put(sunNode.getNodeName(), value);
									}else if (childNode.getNodeType() == Node.CDATA_SECTION_NODE)
									{
										// 자식노드를 순환하다가 TextNode 발견하면 value로 세팅.
										// out.println(childNode.getNodeName());
										value = childNode.getNodeValue();
		//								cScript.doLog("logs","childNode.getNodeValue().CDATA_SECTION_NODE : " + value);
		//								dec.setValue(value);
//										System.out.println("value : " + value);
										returnMap.put(sunNode.getNodeName(), value);
									}
								}
							}
						}
						
						returnList.add(returnMap);
					}
					else
					{
						/*
						<personnel>
						 	<person id="Big.Boss">
						 		<name><family>Boss</family> <given>Big</given></name>
						 		<email>chief@foo.com</email>
						 		<link subordinates="one.worker two.worker three.worker four.worker five.worker"/>
						    </person>
					    </personnel>
					    -- person이나 name일 경우 값이 없다.
					    */
						// Document 인 경우에는 Node value라는 개념이 애매하니까..
						// 전체 String 을 리턴하거나 null값을리턴해야 하는데 일단 null을 리턴하기로 하자.
						// Do nothing
					}
				}
				else
				{
					// 자식노드가 없는 Attribute나 Text노드, CDATASection등의 값을 질의한 경우. getNodeValue를 이용.
					value = node.getNodeValue();
//					cScript.doLog("logs","node.getNodeValue() : " + value);
//					dec.setValue(value);
					System.out.println("value : " + value);
				}
			}

			return returnList;
		}
		catch(Exception e)
		{
			LogWrapper.biz.error("Parsing Error!!! : " + Utils.getLogStackTrace(e));
            return returnList;
		}
	}

}
