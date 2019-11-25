/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import deklaracje.upo.Potwierdzenie;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import jpk201701.JPK;
import view.WpisView;
/**
 *
 * @author Osito
 */
@Entity
@Table(name = "upo")
@NamedQueries({
    @NamedQuery(name = "UPO.findUPOPodatnikRok", query = "SELECT a FROM UPO a WHERE a.podatnik = :podatnik AND a.rok = :rok"),
    @NamedQuery(name = "UPO.findUPORokMc", query = "SELECT a FROM UPO a WHERE a.rok = :rok AND a.miesiac =:mc"),
    @NamedQuery(name = "UPO.findUPOBez200", query = "SELECT a FROM UPO a WHERE a.code != 200")
})
public class UPO  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private int id;
    @JoinColumn(name = "podatnik", referencedColumnName = "id")
    @ManyToOne
    private Podatnik podatnik;
    @Basic(optional = false)
    @NotNull
    @Size(min = 4, max = 4)
    @Column(name = "rok", nullable = false, length = 4)
    private String rok;
    @Column(name = "miesiac")
    private String miesiac;
    @Lob
    @Column (name = "potwierdzenie")
    private Potwierdzenie potwierdzenie;
    @Lob
    @Column (name = "jpk")
    private JPKSuper jpk;
    @Lob
    @Column (name = "deklaracja")
    private DeklaracjaSuper deklaracja;
    @Column (name = "wersja")
    private String wersja;
    @Column (name = "code")
    private Integer code;
    @Column (name = "description")
    private String description;
    @Column (name = "details")
    private String details;
    @Column (name = "timestamp")
    private String timestamp;
    @Column (name = "upoString")
    private String upoString;
    @Column (name = "referenceNumber")
    private String referenceNumber;
    @Column(name = "datajpk")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datajpk;
    @Column(name = "dataupo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataupo;
    @JoinColumn(name = "wprowadzil", referencedColumnName = "login")
    @ManyToOne
    private Uz wprowadzil;
    
    public UPO() {
    }

    
    public UPO(WpisView wpisView, JPK jpk) {
        this.jpk = jpk;
        this.podatnik = wpisView.getPodatnikObiekt();
        this.miesiac = wpisView.getMiesiacWpisu();
        this.rok = wpisView.getRokWpisuSt();
        if (jpk != null) {
            this.wersja = jpk.getNaglowek().getKodFormularza().getWersjaSchemy();
        }
    }

    public UPO(Podatnik p, String mcpop, String rokpop) {
        this.podatnik = p;
        this.miesiac = mcpop;
        this.rok = rokpop;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Podatnik getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMiesiac() {
        return miesiac;
    }

    public void setMiesiac(String miesiac) {
        this.miesiac = miesiac;
    }

    public Potwierdzenie getPotwierdzenie() {
        return potwierdzenie;
    }

    public void setPotwierdzenie(Potwierdzenie potwierdzenie) {
        this.potwierdzenie = potwierdzenie;
    }

    public JPKSuper getJpk() {
        return jpk;
    }
    
    public String getJpkWersja() {
        String zwrot = "pierw.";
        if (jpk != null) {
            if (jpk instanceof jpk201701.JPK) {
                String cel = Byte.toString(((jpk201701.JPK)jpk).getNaglowek().getCelZlozenia());
                if (cel.equals("2")) {
                    zwrot = "kor.";
                }
            } else {
                int cel = ((jpk201801.JPK)jpk).getNaglowek().getCelZlozenia();
                if (cel > 0) {
                    zwrot = "kor.";
                }
            }
        }
        return zwrot;
    }
    
    public String getJpkNaglowek() {
        String zwrot = "nie pobrano";
        if (jpk != null) {
            if (jpk instanceof jpk201701.JPK) {
                try {
                    jpk201701.JPK jpkt = (jpk201701.JPK)jpk;
                    JAXBContext context = JAXBContext.newInstance(jpk201701.JPK.class);
                    Marshaller marshaller = context.createMarshaller();
                    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                    StringWriter sw = new StringWriter();
                    marshaller.marshal(jpkt,sw);
                    zwrot = sw.toString();
                } catch (JAXBException ex) {
                    Logger.getLogger(UPO.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    jpk201801.JPK jpkt = (jpk201801.JPK)jpk;
                    JAXBContext context = JAXBContext.newInstance(jpk201801.JPK.class);
                    Marshaller marshaller = context.createMarshaller();
                    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                    StringWriter sw = new StringWriter();
                    marshaller.marshal(jpkt,sw);
                    zwrot = sw.toString();
                } catch (JAXBException ex) {
                    Logger.getLogger(UPO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        zwrot = zwrot.length() > 900 ? zwrot.substring(0,899) : zwrot;
        return zwrot;
    }

    public void setJpk(JPKSuper jpk) {
        this.jpk = jpk;
    }

    public DeklaracjaSuper getDeklaracja() {
        return deklaracja;
    }

    public void setDeklaracja(DeklaracjaSuper deklaracja) {
        this.deklaracja = deklaracja;
    }

    public String getWersja() {
        return wersja;
    }

    public void setWersja(String wersja) {
        this.wersja = wersja;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTimestamp() {
        return timestamp;
    }
    
    public String getTimestampData() {
        String zwrot = timestamp;
        if (zwrot!=null) {
            zwrot = timestamp.substring(0,16);
            zwrot = zwrot.replace("T", " ");
        }
        return zwrot;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUpoString() {
        return upoString;
    }

    public void setUpoString(String upoString) {
        this.upoString = upoString;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public Date getDatajpk() {
        return datajpk;
    }

    public void setDatajpk(Date datajpk) {
        this.datajpk = datajpk;
    }

    public Date getDataupo() {
        return dataupo;
    }

    public void setDataupo(Date dataupo) {
        this.dataupo = dataupo;
    }

    public Uz getWprowadzil() {
        return wprowadzil;
    }

    public void setWprowadzil(Uz wprowadzil) {
        this.wprowadzil = wprowadzil;
    }
    
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
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
        final UPO other = (UPO) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String deklaracja = this.deklaracja!=null ? this.deklaracja.toString() : "";
        return "UPO{" + "podatnik=" + podatnik.getPrintnazwa() + ", rok=" + rok + ", miesiac=" + miesiac + ", potwierdzenie=" + potwierdzenie.getKodFormularza() + ", jpk=" + jpk + ", deklaracja=" + deklaracja + ", wersja=" + wersja + '}';
    }

    public void uzupelnij(Podatnik podatnik, WpisView wpisView, JPKSuper jpk) {
        try {
            this.jpk = jpk;
            this.podatnik = podatnik;
            this.miesiac = wpisView.getMiesiacWpisu();
            this.rok = wpisView.getRokWpisuSt();
            if (jpk != null) {
                if (jpk instanceof jpk201701.JPK) {
                    this.wersja = ((jpk201701.JPK)jpk).getNaglowek().getKodFormularza().getWersjaSchemy();
                } else {
                    this.wersja = ((jpk201801.JPK)jpk).getNaglowek().getKodFormularza().getWersjaSchemy();
                }
            }
        } catch (Exception e) {
            
        }
    }
    
    
    
    
}
