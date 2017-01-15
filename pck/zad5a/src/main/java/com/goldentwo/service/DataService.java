package com.goldentwo.service;


import com.goldentwo.data.Ksiazka;
import com.goldentwo.data.Ksiegarnia;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface DataService {

    public Ksiegarnia getKsiegarnia();

    public Ksiegarnia uploadXmlFileAndReturnParsedJson(MultipartFile file);

    public byte[] getXmlFile();

    public Ksiazka createOrUpdateKsiazka(Ksiazka ksiazka);

    public void deleteKsiazka (String idKsiazki);

    public byte[] getXhtmlFile(byte[] inputXml);
}
