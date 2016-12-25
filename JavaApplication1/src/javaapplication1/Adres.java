/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import com.sun.xml.internal.txw2.annotation.XmlNamespace;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Osito
 */

public class Adres {
    @XmlElement(name = "miasto")
    private String miasto;
    @XmlElement(name = "ulica")
    private String ulica;

    public Adres() {
    }

    public Adres(String miasto, String ulica) {
        this.miasto = miasto;
        this.ulica = ulica;
    }

    @Override
    public String toString() {
        return "Adres{" + "miasto=" + miasto + ", ulica=" + ulica + '}';
    }
    
    

}
