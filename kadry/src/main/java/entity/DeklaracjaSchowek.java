/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import pl.gov.crd.wzor._2021._03._04._10477.Deklaracja;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "deklaracjaschowek")
@NamedQueries({
   @NamedQuery(name = "DeklaracjaSchowek.findByRokPracownik", query = "SELECT r FROM DeklaracjaSchowek r WHERE r.rok = :rok AND r.pracownik = :pracownik")})
public class DeklaracjaSchowek implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @Column(name = "klasa")
    private String klasa;
    @Column(name = "nazwa")
    private String nazwa;
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "rok")
    private String rok;
    @JoinColumn(name = "pracownik", referencedColumnName = "id")
    @NotNull
    @ManyToOne
    private Pracownik pracownik;
    @NotNull
    @Lob
    @Size(max = 1024000000)
    @Column(name = "deklaracja")
    private byte[] deklaracja;
    @NotNull
    @Lob
    @Size(max = 1024000000)
    @Column(name = "upo")
    private byte[] upo;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datawysylki")
    private Date datawysylki;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dataupo")
    private Date dataupo;

    public DeklaracjaSchowek() {
    }

    
//    public static byte[] serialize(Object obj) throws IOException {
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        ObjectOutputStream os = new ObjectOutputStream(out);
//        os.writeObject(obj);
//        return out.toByteArray();
//    }
//
//    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
//        ByteArrayInputStream in = new ByteArrayInputStream(data);
//        ObjectInputStream is = new ObjectInputStream(in);
//        return is.readObject();
//    }
    
    public DeklaracjaSchowek(Deklaracja deklaracja, Pracownik pracownik, String rokWpisu, String nazwa) {
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        try (ObjectOutputStream ois = new ObjectOutputStream(boas)) {
            ois.writeObject(deklaracja);
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
        this.deklaracja = boas.toByteArray();
        this.rok = rokWpisu;
        this.pracownik = pracownik;
        this.datawysylki = new Date();
        this.klasa = deklaracja.getClass().getCanonicalName();
        this.nazwa = nazwa;
//        ByteArrayInputStream in = new ByteArrayInputStream(this.deklaracja);
//        ObjectInputStream is;
//        try {
//            is = new ObjectInputStream(in);
//            Object readObject = is.readObject();
//            Deklaracja deklaracja2 = (Deklaracja) readObject;
//            System.out.println("");
//        } catch (Exception ex) {
//            Logger.getLogger(DeklaracjaSchowek.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKlasa() {
        return klasa;
    }

    public void setKlasa(String klasa) {
        this.klasa = klasa;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public Pracownik getPracownik() {
        return pracownik;
    }

    public void setPracownik(Pracownik pracownik) {
        this.pracownik = pracownik;
    }

    public byte[] getDeklaracja() {
        return deklaracja;
    }

    public void setDeklaracja(byte[] deklaracja) {
        this.deklaracja = deklaracja;
    }

    public byte[] getUpo() {
        return upo;
    }

    public void setUpo(byte[] upo) {
        this.upo = upo;
    }

    public Date getDatawysylki() {
        return datawysylki;
    }

    public void setDatawysylki(Date datawysylki) {
        this.datawysylki = datawysylki;
    }

    public Date getDataupo() {
        return dataupo;
    }

    public void setDataupo(Date dataupo) {
        this.dataupo = dataupo;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + this.id;
        hash = 13 * hash + Objects.hashCode(this.rok);
        hash = 13 * hash + Objects.hashCode(this.pracownik);
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
        final DeklaracjaSchowek other = (DeklaracjaSchowek) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.pracownik, other.pracownik)) {
            return false;
        }
        return true;
    }
    
    
}
