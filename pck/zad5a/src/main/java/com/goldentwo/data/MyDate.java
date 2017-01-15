package com.goldentwo.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyDate {

    private int dzien;
    private int miesiac;
    private int rok;

    public int getDzien() {
        return dzien;
    }

    @XmlElement(name = "dzień")
    public void setDzien(int dzien) {
        this.dzien = dzien;
    }

    public int getMiesiac() {
        return miesiac;
    }

    @XmlElement(name = "miesiąc")
    public void setMiesiac(int miesiac) {
        this.miesiac = miesiac;
    }

    public int getRok() {
        return rok;
    }

    @XmlElement(name = "rok")
    public void setRok(int rok) {
        this.rok = rok;
    }
}
