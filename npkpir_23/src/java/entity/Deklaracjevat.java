/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import beansVAT.EwidPoz;
import embeddable.EVatwpisSuma;
import embeddable.Vatpoz;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
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
    @NamedQuery(name = "Deklaracjevat.findByPodatnikWyslaneRokMc", query = "SELECT d FROM Deklaracjevat d WHERE d.podatnik = :podatnik AND NOT d.identyfikator = :identyfikator AND d.rok = :rok AND d.miesiac = :mc ORDER BY d.datazlozenia ASC"),
    @NamedQuery(name = "Deklaracjevat.findByPodatnikWyslaneRokMcJPK", query = "SELECT d FROM Deklaracjevat d WHERE d.podatnik = :podatnik AND d.rok = :rok AND d.miesiac = :mc AND (d.status = 388 OR d.status = 399) ORDER BY d.datazlozenia ASC"),
    @NamedQuery(name = "Deklaracjevat.findByPodatnikWyslaneRok200", query = "SELECT d FROM Deklaracjevat d WHERE d.podatnik = :podatnik AND NOT d.identyfikator = :identyfikator AND d.status = 200 AND d.rok = :rok AND d.testowa = 0"),
    @NamedQuery(name = "Deklaracjevat.findByPodatnikWyslaneRokMc200", query = "SELECT d FROM Deklaracjevat d WHERE d.status = :status AND d.rok = :rok AND d.miesiac = :mc AND d.testowa = 0"),
    @NamedQuery(name = "Deklaracjevat.findByPodatnikWyslaneRokMc303", query = "SELECT d FROM Deklaracjevat d WHERE d.status = :status AND d.rok = :rok AND d.miesiac = :mc AND d.testowa = 0 AND d.podatnik = :podatnik")
})
public class Deklaracjevat extends DeklSuper implements Serializable {
   private static final long serialVersionUID = 1L;
   
    @Lob
    @Column(name = "podsumowanieewidencji")
    private HashMap<String, EVatwpisSuma>  podsumowanieewidencji;
    @Lob
    @Column(name = "selected")
    private Vatpoz selected;
    @Column(name = "ordzu")
    private String ordzu;
    @Column(name = "vatzt")
    private String vatzt;
    @Column(name = "vatzz")
    private String vatzz;
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
    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "wniosekVATZDEntity", referencedColumnName = "id")
    private WniosekVATZDEntity wniosekVATZDEntity;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "deklaracja", cascade = CascadeType.ALL,  orphanRemoval=true)
    private List<EwidPoz> ewidpozlista;
    @Transient
    private String kwotaautoryzacja;  
    
    
    
    public Deklaracjevat() {
    }

    public Deklaracjevat(Integer id) {
        this.id = id;
    }
    
//    public String getDeklaracjaxml() {
//       String xmlString = "";
//       try {
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            factory.setNamespaceAware(true);
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            Document doc = builder.parse(new ByteArrayInputStream(this.deklaracja.getBytes()));
//            Transformer transformer = TransformerFactory.newInstance().newTransformer();
//            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
//            //initialize StreamResult with File object to save to file
//            StreamResult result = new StreamResult(new StringWriter());
//            DOMSource source = new DOMSource(doc);
//            transformer.transform(source, result);
//            xmlString = result.getWriter().toString();
//            error.E.s(xmlString);
//       } catch (Exception ex) {
//           Logger.getLogger(Deklaracjevat.class.getName()).log(Level.SEVERE, null, ex);
//       }
//       return xmlString;
//    }

   @Override
    public Integer getId() {
        return id;
    }

   @Override
    public void setId(Integer id) {
        this.id = id;
    }

   @Override
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

    public Date getDatasporzadzenia() {
        return datasporzadzenia;
    }

    public void setDatasporzadzenia(Date datasporzadzenia) {
        this.datasporzadzenia = datasporzadzenia;
    }

    public String getKwotaautoryzacja() {
        return kwotaautoryzacja;
    }

    public void setKwotaautoryzacja(String kwotaautoryzacja) {
        this.kwotaautoryzacja = kwotaautoryzacja;
    }

    public WniosekVATZDEntity getWniosekVATZDEntity() {
        return wniosekVATZDEntity;
    }

    public void setWniosekVATZDEntity(WniosekVATZDEntity wniosekVATZDEntity) {
        this.wniosekVATZDEntity = wniosekVATZDEntity;
    }

    public List<EwidPoz> getEwidpozlista() {
        return ewidpozlista;
    }

    public void setEwidpozlista(List<EwidPoz> ewidpozlista) {
        this.ewidpozlista = ewidpozlista;
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
    }
   
    
}
