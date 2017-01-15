package com.goldentwo.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DzialKsiazki {

    private Dzial dzial;

    public Dzial getDzial() {
        return dzial;
    }

    @XmlAttribute(name = "id-działu")
    @XmlIDREF
    public void setDzial(Dzial dzial) {
        this.dzial = dzial;
    }
}
