package com.goldentwo.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAttribute;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Regal {

    private int numer;

    public int getNumer() {
        return numer;
    }

    @XmlAttribute(name = "numer")
    public void setNumer(int numer) {
        this.numer = numer;
    }
}
