package com.goldentwo.data;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum Waluta {
    @XmlEnumValue(value = "PLN") PLN,
    @XmlEnumValue(value = "EUR") EUR,
    @XmlEnumValue(value = "USD") USD
}
