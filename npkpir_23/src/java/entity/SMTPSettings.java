/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Objects;
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
import javax.validation.constraints.NotNull;

/**
 *
 * @author Osito
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames={"uzytkownik"})})
@NamedQueries({
    @NamedQuery(name = "SMTPSettings.findByUzytkownik", query = "SELECT s FROM SMTPSettings s WHERE s.uzytkownik = :uzytkownik"),
    @NamedQuery(name = "SMTPSettings.findByDef", query = "SELECT s FROM SMTPSettings s WHERE s.glowne = '1'")
})
public class SMTPSettings  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private int id;
    @JoinColumn(name = "uzytkownik",referencedColumnName = "login", nullable = true)
    @ManyToOne
    private Uz uzytkownik;
    @Column(name = "username")
    private String username;
    @Column(name = "useremail")
    private String useremail;
    @Column(name = "password")
    private String password;
    @Column(name = "nazwafirmy")
    private String nazwafirmy;
    @Column(name = "smtphost")
    private String smtphost;
    @Column(name = "smtpport")
    private String smtpport;
    @Column(name = "smtpauth")
    private boolean smtpauth;
    @Column(name = "starttlsenable")
    private boolean starttlsenable;
    @Column(name = "sslenable")
    private boolean sslenable;
    @Column(name = "glowne")
    private boolean glowne;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.id;
        hash = 89 * hash + Objects.hashCode(this.uzytkownik);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SMTPSettings other = (SMTPSettings) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.uzytkownik, other.uzytkownik)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SMTPSettings{" + "uzytkownik=" + uzytkownik.getLogin() + ", username=" + username + ", password=" + password + ", smtphost=" + smtphost + ", smtpport=" + smtpport + ", smtpauth=" + smtpauth + ", starttlsenable=" + starttlsenable + ", glowne=" + glowne + '}';
    }

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Uz getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(Uz uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSmtphost() {
        return smtphost;
    }

    public void setSmtphost(String smtphost) {
        this.smtphost = smtphost;
    }

    public String getSmtpport() {
        return smtpport;
    }

    public void setSmtpport(String smtpport) {
        this.smtpport = smtpport;
    }

    public boolean isSmtpauth() {
        return smtpauth;
    }

    public void setSmtpauth(boolean smtpauth) {
        this.smtpauth = smtpauth;
    }

    public boolean isStarttlsenable() {
        return starttlsenable;
    }

    public void setStarttlsenable(boolean starttlsenable) {
        this.starttlsenable = starttlsenable;
    }

    public boolean isSslenable() {
        return sslenable;
    }

    public void setSslenable(boolean sslenable) {
        this.sslenable = sslenable;
    }

    public boolean isGlowne() {
        return glowne;
    }

    public void setGlowne(boolean glowne) {
        this.glowne = glowne;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getNazwafirmy() {
        return nazwafirmy;
    }

    public void setNazwafirmy(String nazwafirmy) {
        this.nazwafirmy = nazwafirmy;
    }
 
    
}
