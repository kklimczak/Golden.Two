package com.goldentwo.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Wlasciciel {

    private String imie;
    private String nazwisko;
    private String numerIndexu;

    public String getImie() {
        return imie;
    }

    @XmlElement(name = "imię")
    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    @XmlElement(name = "nazwisko")
    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getNumerIndexu() {
        return numerIndexu;
    }

    @XmlAttribute(name = "numer-indexu")
    public void setNumerIndexu(String numerIndexu) {
        this.numerIndexu = numerIndexu;
    }
}
