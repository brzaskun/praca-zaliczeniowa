/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "EVatOpis")
public class EVatOpis implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_dok")
    private Long idDok;
    @Size(max = 20)
    @Column(name = "opis1")
    private String opis1;
    @Size(max = 20)
    @Column(name = "opis2")
    private String opis2;
    @Size(max = 20)
    @Column(name = "opis3")
    private String opis3;
    @Size(max = 20)
    @Column(name = "opis4")
    private String opis4;

    public EVatOpis() {
    }
    
    public EVatOpis(String opis1, String opis2, String opis3, String opis4) {
        this.opis1 = opis1;
        this.opis2 = opis2;
        this.opis3 = opis3;
        this.opis4 = opis4;
    }
    
    public String getOpis1() {
        return opis1;
    }

    public void setOpis1(String opis1) {
        this.opis1 = opis1;
    }

    public String getOpis2() {
        return opis2;
    }

    public void setOpis2(String opis2) {
        this.opis2 = opis2;
    }

    public String getOpis3() {
        return opis3;
    }

    public void setOpis3(String opis3) {
        this.opis3 = opis3;
    }

    public String getOpis4() {
        return opis4;
    }

    public void setOpis4(String opis4) {
        this.opis4 = opis4;
    }
    
    
    
}
