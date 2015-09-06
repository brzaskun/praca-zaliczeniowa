/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Osito
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames={"nazwapozycji"})
})
public class DeklaracjaVatWierszSumaryczny implements Serializable {
   private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nazwapozycji")
    private String nazwapozycji;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNazwapozycji() {
        return nazwapozycji;
    }

    public void setNazwapozycji(String nazwapozycji) {
        this.nazwapozycji = nazwapozycji;
    }

    @Override
    public String toString() {
        return "DeklaracjaVatSumaryczne{" + "id=" + id + ", nazwapozycji=" + nazwapozycji + '}';
    }

    
}
