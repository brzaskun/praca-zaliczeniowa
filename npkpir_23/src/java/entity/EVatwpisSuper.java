/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;

/**
 *
 * @author Osito
 */
@MappedSuperclass
public class EVatwpisSuper implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    protected long id;
    @JoinColumn(name = "ewidencja", referencedColumnName = "nazwa")
    @ManyToOne
    protected Evewidencja ewidencja;
    @Column(name = "netto")
    protected double netto;
    @Column(name = "vat")
    protected double vat;
    @Column(name = "estawka")
    protected String estawka;
    @Size(max = 2)
    @Column(name = "mcEw")
    protected String mcEw;
    @Size(max = 4)
    @Column(name = "rokEw")
    protected String rokEw;
}
