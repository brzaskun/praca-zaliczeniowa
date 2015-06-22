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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(catalog = "pkpir", schema = "", name = "mutliusersettings", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"PODATNIK_nip", "USER_login"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MultiuserSettings.findAll", query = "SELECT m FROM MultiuserSettings m"),
    @NamedQuery(name = "MultiuserSettings.findById", query = "SELECT m FROM MultiuserSettings m WHERE m.id = :id"),
    @NamedQuery(name = "MultiuserSettings.findByLevel", query = "SELECT m FROM MultiuserSettings m WHERE m.level = :level"),
    @NamedQuery(name = "MultiuserSettings.findByUser", query = "SELECT m FROM MultiuserSettings m WHERE m.user = :user"),
    @NamedQuery(name = "MultiuserSettings.findByPodatnik", query = "SELECT m FROM MultiuserSettings m WHERE m.podatnik = :podanik")
	})
public class MultiuserSettings  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false, name = "id")
    private Integer id;
    @JoinColumn(name = "USER_login", referencedColumnName = "login")
    @ManyToOne
    private Uz user;
    @JoinColumn(name = "PODATNIK_nip", referencedColumnName = "nip")
    @ManyToOne
    private Podatnik podatnik;
    private int level;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Uz getUser() {
        return user;
    }

    public void setUser(Uz user) {
        this.user = user;
    }

    public Podatnik getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    
    
    
    
}
