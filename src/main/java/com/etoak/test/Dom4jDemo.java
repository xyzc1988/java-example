package com.etoak.test;

import java.util.logging.Logger;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class Dom4jDemo {
	private static Logger logger = Logger.getLogger(Dom4jDemo.class.getName());
	public void test() throws DocumentException{
		Document doc = new SAXReader().read("d:/data/test.xml");
		Element root = doc.getRootElement();
		/*HelloWorld h = new HelloWorld();
		h.test();*/
	}
	
}
