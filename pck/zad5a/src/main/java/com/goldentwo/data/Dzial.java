package com.goldentwo.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dzial {

    private String nazwa;
    private Regal regal;
    private String idDzialu;

    public String getNazwa() {
        return nazwa;
    }

    @XmlElement(name = "nazwa")
    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Regal getRegal() {
        return regal;
    }

    @XmlElement(name = "regał")
    public void setRegal(Regal regal) {
        this.regal = regal;
    }

    public String getIdDzialu() {
        return idDzialu;
    }

    @XmlElement(name = "id-działu")
    @XmlID
    public void setIdDzialu(String idDzialu) {
        this.idDzialu = idDzialu;
    }
}
