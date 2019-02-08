package com.journaldev.spring;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.InputSource;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

@RestController
public class PersonController {
	
	@RequestMapping("/")
	public String welcome() {
		convert();
		return "Welcome to Spring Boot REST...";
	}
	
	public void convert() {
	      XStream xstream = new XStream(new StaxDriver());
	      
	      Student student = getStudentDetails();
	      
	      //Object to XML Conversion
	      String xml = xstream.toXML(student);
	      System.out.println(xml);
	      System.out.println(formatXml(xml));
	      
	      //XML to Object Conversion
	      Student student1 = (Student)xstream.fromXML(xml);
	      System.out.println(student1);
	   }
	   
	   public Student getStudentDetails() {
	   
	      Student student = new Student();
	      student.setFirstName("Mahesh");
	      student.setLastName("Parashar");
	      student.setRollNo(1);
	      student.setClassName("1st");

	      Address address = new Address();
	      address.setArea("H.No. 16/3, Preet Vihar.");
	      address.setCity("Delhi");
	      address.setState("Delhi");
	      address.setCountry("India");
	      address.setPincode(110012);

	      student.setAddress(address);
	      return student;
	   }
	   
	   public String formatXml(String xml) {
	   
	      try {
	         Transformer serializer = SAXTransformerFactory.newInstance().newTransformer();
	         
	         serializer.setOutputProperty(OutputKeys.INDENT, "yes");
	         serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
	         
	         Source xmlSource = new SAXSource(new InputSource(
	            new ByteArrayInputStream(xml.getBytes())));
	         StreamResult res =  new StreamResult(new ByteArrayOutputStream());            
	         
	         serializer.transform(xmlSource, res);
	         
	         return new String(((ByteArrayOutputStream)res.getOutputStream()).toByteArray());
	         
	      } catch(Exception e) {
	         return xml;
	      }
	   }
	   
}
