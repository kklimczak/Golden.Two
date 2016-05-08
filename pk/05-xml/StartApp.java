package serializacja;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;;

public class StartApp {

	public static void main(String[] args) {
		
		Bug bug = new Bug("Error", "Error exist", 1234567890);
		objectToXml(bug);
		
		Bug bugFromXml = xmlToObject();
		System.out.println(bugFromXml);
	}
	
	private static void objectToXml (Bug bug) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(bug.getClass());
			Marshaller marshaller = jaxbContext.createMarshaller();

			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(bug, new File ("bug.xml"));
		} catch (JAXBException jaxbException) {
			jaxbException.printStackTrace();
		}
	}
	
	private static Bug xmlToObject () {
		Bug bug = new Bug();
		try {
			File file = new File ("bug.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(bug.getClass());
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			bug = (Bug) unmarshaller.unmarshal(file);
			return bug;
		} catch (JAXBException jaxbException) {
			jaxbException.printStackTrace();
			return null;
		}
	}

}
