package com.goldentwo.data;

import com.goldentwo.utils.DataAdapter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DaneDodatkowe {

    private String spis;
    private double wersja;
    private List<Wlasciciel> wlasciciele;
    private Date dataModyfikacji;

    public String getSpis() {
        return spis;
    }

    @XmlElement(name = "spis")
    public void setSpis(String spis) {
        this.spis = spis;
    }

    public double getWersja() {
        return wersja;
    }

    @XmlElement(name = "wersja")
    public void setWersja(double wersja) {
        this.wersja = wersja;
    }

    public List<Wlasciciel> getWlasciciele() {
        return wlasciciele;
    }

    @XmlElementWrapper(name="właściciele")
    @XmlElement(name = "właściciel")
    public void setWlasciciele(List<Wlasciciel> wlasciciele) {
        this.wlasciciele = wlasciciele;
    }

    public Date getDataModyfikacji() {
        return dataModyfikacji;
    }

    @XmlElement(name = "data-modyfikacji")
    @XmlJavaTypeAdapter(value = DataAdapter.class)
    public void setDataModyfikacji(Date dataModyfikacji) {
        this.dataModyfikacji = dataModyfikacji;
    }
}
