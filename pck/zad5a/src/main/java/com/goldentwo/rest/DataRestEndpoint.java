package com.goldentwo.rest;


import com.goldentwo.data.Ksiazka;
import com.goldentwo.data.Ksiegarnia;
import com.goldentwo.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class DataRestEndpoint {

    @Autowired
    DataService dataService;

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    @ResponseBody
    public Ksiegarnia getKsiegarnia() {
        return dataService.getKsiegarnia();
    }

    @RequestMapping(value = "/file/upload", method = RequestMethod.POST)
    @ResponseBody
    public Ksiegarnia uploadXmlFile(@RequestParam(value = "myfile") MultipartFile file) {
        return dataService.uploadXmlFileAndReturnParsedJson(file);
    }

    @RequestMapping(value = "/file/download", method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloadXmlFile() {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_XML);
        return new ResponseEntity<>(dataService.getXmlFile(), httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/data", method = RequestMethod.POST)
    public Ksiazka createOrUpdateKsiazka(@RequestBody Ksiazka ksiazka) {
        return dataService.createOrUpdateKsiazka(ksiazka);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/data/{id}", method = RequestMethod.DELETE)
    public void deleteKsiazka(@PathVariable String id) {
        dataService.deleteKsiazka(id);
    }

    @RequestMapping(value = "/file/download/generated", method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloadGeneratedXhtmlFile() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_HTML);
        return new ResponseEntity<>(dataService.getXhtmlFile(dataService.getXmlFile()), httpHeaders, HttpStatus.OK);
    }
}
