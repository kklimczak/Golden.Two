package com.goldentwo.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cena {

    private Waluta waluta;
    private double wartosc;

    public Waluta getWaluta() {
        return waluta;
    }

    @XmlAttribute(name = "waluta")
    public void setWaluta(Waluta waluta) {
        this.waluta = waluta;
    }

    public double getWartosc() {
        return wartosc;
    }

    @XmlValue
    public void setWartosc(double wartosc) {
        this.wartosc = wartosc;
    }
}
