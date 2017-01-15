package com.goldentwo.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "księgarnia")
public class Ksiegarnia {

    private DaneDodatkowe daneDodatkowe;
    private List<Dzial> dzialy;
    private List<Autor> autorzy;
    private List<Ksiazka> ksiazki;

    public DaneDodatkowe getDaneDodatkowe() {
        return daneDodatkowe;
    }

    @XmlElement(name = "dane-dodatkowe")
    public void setDaneDodatkowe(DaneDodatkowe daneDodatkowe) {
        this.daneDodatkowe = daneDodatkowe;
    }

    public List<Dzial> getDzialy() {
        return dzialy;
    }

    @XmlElementWrapper(name="działy")
    @XmlElement(name = "dział")
    public void setDzialy(List<Dzial> dzialy) {
        this.dzialy = dzialy;
    }

    public List<Autor> getAutorzy() {
        return autorzy;
    }

    @XmlElementWrapper(name="autorzy")
    @XmlElement(name = "autor")
    public void setAutorzy(List<Autor> autorzy) {
        this.autorzy = autorzy;
    }

    public List<Ksiazka> getKsiazki() {
        return ksiazki;
    }

    @XmlElementWrapper(name="książki")
    @XmlElement(name = "książka")
    public void setKsiazki(List<Ksiazka> ksiazki) {
        this.ksiazki = ksiazki;
    }
}
