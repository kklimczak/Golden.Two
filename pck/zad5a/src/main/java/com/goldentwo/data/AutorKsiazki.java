package com.goldentwo.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AutorKsiazki {

    private Autor autor;

    public Autor getAutor() {
        return this.autor;
    }

    @XmlAttribute(name = "id-autora")
    @XmlIDREF
    public void setAutor(Autor autor) {
        this.autor = autor;
    }
}
