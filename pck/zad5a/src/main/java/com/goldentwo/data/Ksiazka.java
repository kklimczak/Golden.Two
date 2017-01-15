package com.goldentwo.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ksiazka {

    private String idKsiazki;
    private String tytul;
    private AutorKsiazki autorKsiazki;
    private DzialKsiazki dzialKsiazki;
    private Cena cena;
    private String isbn;

    public String getIdKsiazki() {
        return idKsiazki;
    }

    @XmlAttribute(name = "id-książki")
    public void setIdKsiazki(String idKsiazki) {
        this.idKsiazki = idKsiazki;
    }

    public String getTytul() {
        return tytul;
    }

    @XmlElement(name = "tytuł")
    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public AutorKsiazki getAutorKsiazki() {
        return autorKsiazki;
    }

    @XmlElement(name = "autor-książki")
    public void setAutorKsiazki(AutorKsiazki autorKsiazki) {
        this.autorKsiazki = autorKsiazki;
    }

    public DzialKsiazki getDzialKsiazki() {
        return dzialKsiazki;
    }

    @XmlElement(name = "dział-książki")
    public void setDzialKsiazki(DzialKsiazki dzialKsiazki) {
        this.dzialKsiazki = dzialKsiazki;
    }

    public Cena getCena() {
        return cena;
    }

    @XmlElement(name = "cena")
    public void setCena(Cena cena) {
        this.cena = cena;
    }

    public String getIsbn() {
        return isbn;
    }

    @XmlElement(name = "isbn")
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
