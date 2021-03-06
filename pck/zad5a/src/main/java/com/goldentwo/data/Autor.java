package com.goldentwo.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Autor {

    private String imie;
    private String nazwisko;
    private String idAutora;

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

    public String getIdAutora() {
        return idAutora;
    }

    @XmlElement(name = "id-autora")
    @XmlID
    public void setIdAutora(String idAutora) {
        this.idAutora = idAutora;
    }
}
