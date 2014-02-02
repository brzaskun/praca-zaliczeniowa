/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entityfk.Konto;
import entityfk.Rzisuklad;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToMany;

/**
 *
 * @author Osito
 */
@Entity
public class Kontopozycjarzis implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lp")
    private int lp;
    private Konto konto;
    private Rzisuklad uklad;
    @Column(name = "pozycjaString")
    private String pozycjaString;
            
}
