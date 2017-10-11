/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import embeddable.EVatwpisSuma;
import embeddable.PozycjeSzczegoloweVAT;
import embeddable.Vatpoz;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "deklaracjevat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Deklaracjevat.findAll", query = "SELECT d FROM Deklaracjevat d"),
    @NamedQuery(name = "Deklaracjevat.findById", query = "SELECT d FROM Deklaracjevat d WHERE d.id = :id"),
    @NamedQuery(name = "Deklaracjevat.findByIdentyfikator", query = "SELECT d FROM Deklaracjevat d WHERE d.identyfikator = :identyfikator"),
    @NamedQuery(name = "Deklaracjevat.findByIdentyfikatorPodatnik", query = "SELECT d FROM Deklaracjevat d WHERE d.identyfikator = :identyfikator AND d.podatnik = :podatnik"),
    @NamedQuery(name = "Deklaracjevat.findByKodurzedu", query = "SELECT d FROM Deklaracjevat d WHERE d.kodurzedu = :kodurzedu"),
    @NamedQuery(name = "Deklaracjevat.findByMiesiac", query = "SELECT d FROM Deklaracjevat d WHERE d.miesiac = :miesiac"),
    @NamedQuery(name = "Deklaracjevat.findByNrkolejny", query = "SELECT d FROM Deklaracjevat d WHERE d.nrkolejny = :nrkolejny"),
    @NamedQuery(name = "Deklaracjevat.findByOpis", query = "SELECT d FROM Deklaracjevat d WHERE d.opis = :opis"),
    @NamedQuery(name = "Deklaracjevat.findByPodatnik", query = "SELECT d FROM Deklaracjevat d WHERE d.podatnik = :podatnik"),
    @NamedQuery(name = "Deklaracjevat.findByRok", query = "SELECT d FROM Deklaracjevat d WHERE d.rok = :rok"),
    @NamedQuery(name = "Deklaracjevat.findByStatus", query = "SELECT d FROM Deklaracjevat d WHERE d.status = :status"),
    @NamedQuery(name = "Deklaracjevat.findByDatazlozenia", query = "SELECT d FROM Deklaracjevat d WHERE d.datazlozenia = :datazlozenia"),
    @NamedQuery(name = "Deklaracjevat.findByDataupo", query = "SELECT d FROM Deklaracjevat d WHERE d.dataupo = :dataupo"),
    @NamedQuery(name = "Deklaracjevat.findByRokMcPod", query = "SELECT d FROM Deklaracjevat d WHERE d.rok = :rok AND d.miesiac = :miesiac AND d.podatnik = :podatnik"),
    @NamedQuery(name = "Deklaracjevat.findByRokMc", query = "SELECT d FROM Deklaracjevat d WHERE d.rok = :rok AND d.miesiac = :miesiac"),
    @NamedQuery(name = "Deklaracjevat.findByPodatnikWysylka", query = "SELECT d FROM Deklaracjevat d WHERE d.podatnik = :podatnik AND d.identyfikator = :identyfikator"),
    @NamedQuery(name = "Deklaracjevat.findByPodatnikWyslane", query = "SELECT d FROM Deklaracjevat d WHERE d.podatnik = :podatnik AND NOT d.identyfikator = :identyfikator"),
    @NamedQuery(name = "Deklaracjevat.findByPodatnikWyslaneRok", query = "SELECT d FROM Deklaracjevat d WHERE d.podatnik = :podatnik AND NOT d.identyfikator = :identyfikator AND d.rok = :rok"),
    @NamedQuery(name = "Deklaracjevat.findByPodatnikWyslaneRok200", query = "SELECT d FROM Deklaracjevat d WHERE d.podatnik = :podatnik AND NOT d.identyfikator = :identyfikator AND d.status = 200 AND d.rok = :rok AND d.testowa = 0")
})
public class Deklaracjevat implements Serializable {
   private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "deklaracja")
    private String deklaracja;
    @Column(name="miesiackwartal")
    private boolean miesiackwartal;
    @Size(max = 4)
    @Column(name = "nrkwartalu")
    private String nrkwartalu;
    @Size(max = 255)
    @Column(name = "identyfikator")
    private String identyfikator;
    @Size(max = 255)
    @Column(name = "kodurzedu")
    private String kodurzedu;
    @Size(max = 255)
    @Column(name = "miesiac")
    private String miesiac;
    @Column(name = "nrkolejny")
    private int nrkolejny;
    @Size(max = 255)
    @Column(name = "podatnik")
    private String podatnik;
    @Lob
    @Column(name = "podsumowanieewidencji")
    private HashMap<String, EVatwpisSuma>  podsumowanieewidencji;
    @Lob
    @Column(name = "pozycjeszczegolowe")
    private PozycjeSzczegoloweVAT pozycjeszczegolowe;
    @Size(max = 255)
    @Column(name = "rok")
    private String rok;
    @Lob
    @Column(name = "selected")
    private Vatpoz selected;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "upo")
    private String upo;
    @Size(max = 255)
    @Column(name = "status")
    private String status;
    @Size(max = 255)
    @Column(name = "opis")
    private String opis;
    @Column(name = "datazlozenia")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datazlozenia;
    @Column(name = "dataupo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataupo;
    @Size(max = 100)
    @Column(name = "sporzadzil")
    private String sporzadzil;
    @Column(name = "ordzu")
    private String ordzu;
    @Column(name = "vatzt")
    private String vatzt;
    @Column(name = "vatzz")
    private String vatzz;
    @Column(name="testowa")
    private boolean testowa;
    @Column(name = "wzorschemy")
    private String wzorschemy;
    @JoinColumn(name = "schemaobj", referencedColumnName = "id")
    @ManyToOne
    private DeklaracjaVatSchema schemaobj;
    @OneToOne(cascade = {CascadeType.ALL})
    private VATDeklaracjaKorektaDok vatDeklaracjaKorektaDokWykaz;
    @Column(name = "kwotadoprzeniesienia")
    private int kwotadoprzeniesienia;
    @Lob
    @Column(name = "schemawierszsumarycznylista")
    private List<DeklaracjaVatSchemaWierszSum> schemawierszsumarycznylista;
    @Column(name="jestcertyfikat")
    private boolean jestcertyfikat;
    @Transient
    private byte[] deklaracjapodpisana;
    
   public Deklaracjevat() {
    }

    public Deklaracjevat(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeklaracja() {
        return deklaracja;
    }

    public void setDeklaracja(String deklaracja) {
        this.deklaracja = deklaracja;
    }

    public boolean isMiesiackwartal() {
        return miesiackwartal;
    }

    public void setMiesiackwartal(boolean miesiackwartal) {
        this.miesiackwartal = miesiackwartal;
    }

    public String getNrkwartalu() {
        return nrkwartalu;
    }

    public void setNrkwartalu(String nrkwartalu) {
        this.nrkwartalu = nrkwartalu;
    }

    public String getIdentyfikator() {
        return identyfikator;
    }

    public void setIdentyfikator(String identyfikator) {
        this.identyfikator = identyfikator;
    }

    public String getKodurzedu() {
        return kodurzedu;
    }

    public void setKodurzedu(String kodurzedu) {
        this.kodurzedu = kodurzedu;
    }

    public String getMiesiac() {
        return miesiac;
    }

    public void setMiesiac(String miesiac) {
        this.miesiac = miesiac;
    }

    public byte[] getDeklaracjapodpisana() {
        return deklaracjapodpisana;
    }

    public void setDeklaracjapodpisana(byte[] deklaracjapodpisana) {
        this.deklaracjapodpisana = deklaracjapodpisana;
    }

    public int getNrkolejny() {
        return nrkolejny;
    }

    public void setNrkolejny(int nrkolejny) {
        this.nrkolejny = nrkolejny;
    }

    public String getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
    }

    public HashMap<String, EVatwpisSuma> getPodsumowanieewidencji() {
        return podsumowanieewidencji;
    }

    public void setPodsumowanieewidencji(HashMap<String, EVatwpisSuma> podsumowanieewidencji) {
        this.podsumowanieewidencji = podsumowanieewidencji;
    }

    public PozycjeSzczegoloweVAT getPozycjeszczegolowe() {
        return pozycjeszczegolowe;
    }

    public void setPozycjeszczegolowe(PozycjeSzczegoloweVAT pozycjeszczegolowe) {
        this.pozycjeszczegolowe = pozycjeszczegolowe;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public Vatpoz getSelected() {
        return selected;
    }

    public void setSelected(Vatpoz selected) {
        this.selected = selected;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getUpo() {
        return upo;
    }

    public void setUpo(String upo) {
        this.upo = upo;
    }

    public Date getDatazlozenia() {
        return datazlozenia;
    }

    public void setDatazlozenia(Date datazlozenia) {
        this.datazlozenia = datazlozenia;
    }

    public Date getDataupo() {
        return dataupo;
    }

    public void setDataupo(Date dataupo) {
        this.dataupo = dataupo;
    }

    public String getSporzadzil() {
        return sporzadzil;
    }

    public void setSporzadzil(String sporzadzil) {
        this.sporzadzil = sporzadzil;
    }

    public String getOrdzu() {
        return ordzu;
    }

    public void setOrdzu(String ordzu) {
        this.ordzu = ordzu;
    }

    public String getVatzt() {
        return vatzt;
    }

    public void setVatzt(String vatzt) {
        this.vatzt = vatzt;
    }
    
    public boolean isTestowa() {
        return testowa;
    }

    public void setTestowa(boolean testowa) {
        this.testowa = testowa;
    }

    public String getWzorschemy() {
        return wzorschemy;
    }

    public void setWzorschemy(String wzorschemy) {
        this.wzorschemy = wzorschemy;
    }

    public VATDeklaracjaKorektaDok getVatDeklaracjaKorektaDokWykaz() {
        return vatDeklaracjaKorektaDokWykaz;
    }

    public void setVatDeklaracjaKorektaDokWykaz(VATDeklaracjaKorektaDok vatDeklaracjaKorektaDokWykaz) {
        this.vatDeklaracjaKorektaDokWykaz = vatDeklaracjaKorektaDokWykaz;
    }

    public int getKwotadoprzeniesienia() {
        return kwotadoprzeniesienia;
    }

    public void setKwotadoprzeniesienia(int kwotadoprzeniesienia) {
        this.kwotadoprzeniesienia = kwotadoprzeniesienia;
    }

    public List<DeklaracjaVatSchemaWierszSum> getSchemawierszsumarycznylista() {
        return schemawierszsumarycznylista;
    }

    public void setSchemawierszsumarycznylista(List<DeklaracjaVatSchemaWierszSum> schemawierszsumarycznylista) {
        this.schemawierszsumarycznylista = schemawierszsumarycznylista;
    }

    public String getVatzz() {
        return vatzz;
    }

    public void setVatzz(String vatzz) {
        this.vatzz = vatzz;
    }

    public DeklaracjaVatSchema getSchemaobj() {
        return schemaobj;
    }

    public void setSchemaobj(DeklaracjaVatSchema schemaobj) {
        this.schemaobj = schemaobj;
    }

    public boolean isJestcertyfikat() {
        return jestcertyfikat;
    }

    public void setJestcertyfikat(boolean jestcertyfikat) {
        this.jestcertyfikat = jestcertyfikat;
    }

    

    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Deklaracjevat)) {
            return false;
        }
        Deklaracjevat other = (Deklaracjevat) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Deklaracjevat{" + "deklaracja=" + deklaracja + '}';
    }

    public static void main(String args[]) {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
	String date = sdf.format(new Date()); 
        System.out.println(date);
    }
   
    
}
