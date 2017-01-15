package com.goldentwo.service;

import com.goldentwo.data.Ksiazka;
import com.goldentwo.data.Ksiegarnia;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

@Service
public class DataServiceImpl implements DataService {

    private Ksiegarnia ksiegarnia;

    private int highestId = 0;

    public Ksiegarnia getKsiegarnia() {
        return ksiegarnia;
    }

    public Ksiegarnia uploadXmlFileAndReturnParsedJson(MultipartFile file) {
        try {
            File xmlFile = new File(file.getOriginalFilename());
            xmlFile.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(xmlFile);
            fileOutputStream.write(file.getBytes());
            fileOutputStream.close();

            JAXBContext jaxbContext = JAXBContext.newInstance(Ksiegarnia.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            ksiegarnia = (Ksiegarnia) unmarshaller.unmarshal(xmlFile);

            for (Ksiazka ksiazka : ksiegarnia.getKsiazki()) {
                int id = Integer.parseInt(ksiazka.getIdKsiazki().substring(1));
                if (id > highestId) {
                    highestId = id;
                }
            }

            return ksiegarnia;
        } catch (IOException e) {
            System.out.println("Sorry");
            return null;
        } catch (JAXBException e) {
            System.out.println("Sorry v2");
            return null;
        }
    }

    public byte[] getXmlFile() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Ksiegarnia.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            if (ksiegarnia == null) {
                ksiegarnia = new Ksiegarnia();
            }

            marshaller.marshal(ksiegarnia, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (JAXBException e) {
            System.out.println(e.getMessage() + e.toString());
            return null;
        }
    }

    public Ksiazka createOrUpdateKsiazka(Ksiazka ksiazka) {

        if (ksiazka.getIdKsiazki() == null) {
            ksiazka.setIdKsiazki("K" + ++highestId);
        }

        for (int i = 0; i < ksiegarnia.getKsiazki().size(); i++) {
            if (ksiegarnia.getKsiazki().get(i).getIdKsiazki().equals(ksiazka.getIdKsiazki())) {
                ksiegarnia.getKsiazki().set(i, ksiazka);
            }
        }

        return ksiazka;
    }

    public void deleteKsiazka (String idKsiazki) {
        for (int i = 0; i < ksiegarnia.getKsiazki().size(); i++) {
            if (ksiegarnia.getKsiazki().get(i).getIdKsiazki().equals(idKsiazki)) {
                ksiegarnia.getKsiazki().remove(i);
            }
        }
    }

    public byte[] getXhtmlFile(byte[] inputXml) {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        ClassLoader classLoader = DataServiceImpl.class.getClassLoader();
        File fileXsltXml = new File(classLoader.getResource("static/trans1.xsl").getFile());
        File fileXsltXhtml = new File(classLoader.getResource("static/trans2.xsl").getFile());

        Source xmlSource = new StreamSource(new ByteArrayInputStream(inputXml));

        Source xmlRaportSource = new StreamSource(fileXsltXml);
        Source xhtmlRaportSource = new StreamSource(fileXsltXhtml);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Result resultXml = new StreamResult(outputStream);

        try {
            Transformer transformer = transformerFactory.newTransformer(xmlRaportSource);
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(xmlSource, resultXml);

            Source xmlGeneratedSource = new StreamSource(new ByteArrayInputStream(outputStream.toByteArray()));

            ByteArrayOutputStream outputStream1 = new ByteArrayOutputStream();
            Result resultXhtml = new StreamResult(outputStream1);

            Transformer transformer1 = transformerFactory.newTransformer(xhtmlRaportSource);
            transformer1.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer1.transform(xmlGeneratedSource, resultXhtml);
            return outputStream1.toByteArray();
        } catch (Exception e) {
            e.getStackTrace();
        }

        return null;
    }
}
