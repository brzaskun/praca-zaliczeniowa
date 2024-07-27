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
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
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
    @JoinColumn(name = "jpkblob", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Jpkblob jpkblob;
    @JoinColumn(name = "deklaracja", referencedColumnName = "id")
    private Deklaracjevat deklaracja;
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
        this.jpkblob = new Jpkblob(jpk,this);
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

  
    public String getJpkWersja() {
        String zwrot = "pierw.";
        JPKSuper jpk = null;
        if (this.jpkblob!=null) {
            jpk = this.jpkblob.getJpk();
        }
        if (jpk != null) {
            try {
                if (jpk instanceof jpk201701.JPK) {
                    String cel = Byte.toString(((jpk201701.JPK)jpk).getNaglowek().getCelZlozenia());
                    if (cel.equals("2")) {
                        zwrot = "kor.";
                    }
                } else if (jpk instanceof jpk201801.JPK) {
                    int cel = ((jpk201801.JPK)jpk).getNaglowek().getCelZlozenia();
                    if (cel > 0) {
                        zwrot = "kor.";
                    }
                } else if (jpk instanceof pl.gov.crd.wzor._2020._05._08._9393.JPK) {
                    String cel = Byte.toString(((pl.gov.crd.wzor._2020._05._08._9393.JPK)jpk).getNaglowek().getCelZlozenia().getValue());
                    if (cel.equals("2")) {
                        zwrot = "kor.";
                    }
                } else if (jpk instanceof pl.gov.crd.wzor._2020._05._08._9394.JPK) {
                    String cel = Byte.toString(((pl.gov.crd.wzor._2020._05._08._9394.JPK)jpk).getNaglowek().getCelZlozenia().getValue());
                    if (cel.equals("2")) {
                        zwrot = "kor.";
                    }
                } else if (jpk instanceof pl.gov.crd.wzor._2021._12._27._11148.JPK) {
                    String cel = Byte.toString(((pl.gov.crd.wzor._2021._12._27._11148.JPK)jpk).getNaglowek().getCelZlozenia().getValue());
                    if (cel.equals("2")) {
                        zwrot = "kor.";
                    }
                } else if (jpk instanceof pl.gov.crd.wzor._2021._12._27._11149.JPK) {
                    String cel = Byte.toString(((pl.gov.crd.wzor._2021._12._27._11149.JPK)jpk).getNaglowek().getCelZlozenia().getValue());
                    if (cel.equals("2")) {
                        zwrot = "kor.";
                    }
                }
            } catch (Exception e){}
        }
        return zwrot;
    }
    
    public String getJpkNaglowek() {
        String zwrot = "nie pobrano";
        JPKSuper jpk = null;
        if (this.jpkblob!=null) {
            jpk = this.jpkblob.getJpk();
        }
        if (jpk != null) {
            try {
                Class kl = jpk.getClass();
                JAXBContext context = JAXBContext.newInstance(kl);
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                StringWriter sw = new StringWriter();
                marshaller.marshal(jpk,sw);
                zwrot = sw.toString();
            } catch (JAXBException ex) {
                // Logger.getLogger(UPO.class.getName()).log(Level.SEVERE, null, ex);
            }
//            if (jpk instanceof jpk201701.JPK) {
//                try {
//                    jpk201701.JPK jpkt = (jpk201701.JPK)jpk;
//                    JAXBContext context = JAXBContext.newInstance(jpk201701.JPK.class);
//                    Marshaller marshaller = context.createMarshaller();
//                    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//                    StringWriter sw = new StringWriter();
//                    marshaller.marshal(jpkt,sw);
//                    zwrot = sw.toString();
//                } catch (JAXBException ex) {
//                    // Logger.getLogger(UPO.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            } else {
//                try {
//                    jpk201801.JPK jpkt = (jpk201801.JPK)jpk;
//                    JAXBContext context = JAXBContext.newInstance(jpk201801.JPK.class);
//                    Marshaller marshaller = context.createMarshaller();
//                    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//                    StringWriter sw = new StringWriter();
//                    marshaller.marshal(jpkt,sw);
//                    zwrot = sw.toString();
//                } catch (JAXBException ex) {
//                    // Logger.getLogger(UPO.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
        }
        zwrot = zwrot.length() > 2000 ? zwrot.substring(0,1999) : zwrot;
        return zwrot;
    }

    public String czyjestdeklaracja() {
        String zwrot = "";
        if (this.deklaracja!=null) {
            zwrot = "tak";
        }
        return zwrot;
    }
    
  
    public void uzupelnij(Podatnik podatnik, WpisView wpisView, JPKSuper jpk) {
        try {
            this.jpkblob = new Jpkblob(jpk,this);
            this.podatnik = podatnik;
            this.miesiac = wpisView.getMiesiacWpisu();
            this.rok = wpisView.getRokWpisuSt();
            if (jpk != null) {
                if (jpk instanceof pl.gov.crd.wzor._2020._05._08._9393.JPK) {
                    this.wersja = ((pl.gov.crd.wzor._2020._05._08._9393.JPK)jpk).getNaglowek().getKodFormularza().getWersjaSchemy();
                } else if (jpk instanceof pl.gov.crd.wzor._2020._05._08._9394.JPK) {
                    this.wersja = ((pl.gov.crd.wzor._2020._05._08._9394.JPK)jpk).getNaglowek().getKodFormularza().getWersjaSchemy();
                } else if (jpk instanceof jpk201701.JPK) {
                    this.wersja = ((jpk201701.JPK)jpk).getNaglowek().getKodFormularza().getWersjaSchemy();
                } else {
                    this.wersja = ((jpk201801.JPK)jpk).getNaglowek().getKodFormularza().getWersjaSchemy();
                }
            }
        } catch (Exception e) {
            
        }
    }
    //<editor-fold defaultstate="collapsed" desc="comment">
    
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
        JPKSuper zwrot = null;
        if (this.jpkblob!=null) {
            zwrot = this.jpkblob.getJpk();
        }
        return zwrot;
    }
    
    
    public Deklaracjevat getDeklaracja() {
        return deklaracja;
    }
    
    public void setDeklaracja(Deklaracjevat deklaracja) {
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
    
    public Jpkblob getJpkblob() {
        return jpkblob;
    }
    
    public void setJpkblob(Jpkblob jpkblob) {
        this.jpkblob = jpkblob;
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
    
//</editor-fold>
    
    

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
        return "UPO{" + "podatnik=" + podatnik.getPrintnazwa() + ", rok=" + rok + ", miesiac=" + miesiac + ", potwierdzenie=" + potwierdzenie.getKodFormularza() + ", jpk=" + jpkblob.getId() + ", deklaracja=" + deklaracja + ", wersja=" + wersja + '}';
    }

    
    
    
}
