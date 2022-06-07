/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityplatnik;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "UBEZP_RPA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UbezpRpa.findAll", query = "SELECT u FROM UbezpRpa u"),
    @NamedQuery(name = "UbezpRpa.findById", query = "SELECT u FROM UbezpRpa u WHERE u.id = :id"),
    @NamedQuery(name = "UbezpRpa.findByIdDokument", query = "SELECT u FROM UbezpRpa u WHERE u.idDokument = :idDokument"),
    @NamedQuery(name = "UbezpRpa.findByIdPlatnik", query = "SELECT u FROM UbezpRpa u WHERE u.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "UbezpRpa.findByIdUbezpieczony", query = "SELECT u FROM UbezpRpa u WHERE u.idUbezpieczony = :idUbezpieczony"),
    @NamedQuery(name = "UbezpRpa.findByIdDokZus", query = "SELECT u FROM UbezpRpa u WHERE u.idDokZus = :idDokZus"),
    @NamedQuery(name = "UbezpRpa.findByNrPoddokumentu", query = "SELECT u FROM UbezpRpa u WHERE u.nrPoddokumentu = :nrPoddokumentu"),
    @NamedQuery(name = "UbezpRpa.findByIdPlZus", query = "SELECT u FROM UbezpRpa u WHERE u.idPlZus = :idPlZus"),
    @NamedQuery(name = "UbezpRpa.findByIdUbUPlat", query = "SELECT u FROM UbezpRpa u WHERE u.idUbUPlat = :idUbUPlat"),
    @NamedQuery(name = "UbezpRpa.findByIdUbZus", query = "SELECT u FROM UbezpRpa u WHERE u.idUbZus = :idUbZus"),
    @NamedQuery(name = "UbezpRpa.findByIdWewDra", query = "SELECT u FROM UbezpRpa u WHERE u.idWewDra = :idWewDra"),
    @NamedQuery(name = "UbezpRpa.findByStatusKontroli", query = "SELECT u FROM UbezpRpa u WHERE u.statusKontroli = :statusKontroli"),
    @NamedQuery(name = "UbezpRpa.findByIOkresRaportu", query = "SELECT u FROM UbezpRpa u WHERE u.iOkresRaportu = :iOkresRaportu"),
    @NamedQuery(name = "UbezpRpa.findByIiiNazwisko", query = "SELECT u FROM UbezpRpa u WHERE u.iiiNazwisko = :iiiNazwisko"),
    @NamedQuery(name = "UbezpRpa.findByIiiImiePierwsze", query = "SELECT u FROM UbezpRpa u WHERE u.iiiImiePierwsze = :iiiImiePierwsze"),
    @NamedQuery(name = "UbezpRpa.findByIiiRodzajDok", query = "SELECT u FROM UbezpRpa u WHERE u.iiiRodzajDok = :iiiRodzajDok"),
    @NamedQuery(name = "UbezpRpa.findByIiiIdentDok", query = "SELECT u FROM UbezpRpa u WHERE u.iiiIdentDok = :iiiIdentDok"),
    @NamedQuery(name = "UbezpRpa.findByIiiKodUbezp", query = "SELECT u FROM UbezpRpa u WHERE u.iiiKodUbezp = :iiiKodUbezp"),
    @NamedQuery(name = "UbezpRpa.findByIiiPrawoDoEme", query = "SELECT u FROM UbezpRpa u WHERE u.iiiPrawoDoEme = :iiiPrawoDoEme"),
    @NamedQuery(name = "UbezpRpa.findByIiiKodStopniaNiep", query = "SELECT u FROM UbezpRpa u WHERE u.iiiKodStopniaNiep = :iiiKodStopniaNiep"),
    @NamedQuery(name = "UbezpRpa.findByIiiPrzyRokE1", query = "SELECT u FROM UbezpRpa u WHERE u.iiiPrzyRokE1 = :iiiPrzyRokE1"),
    @NamedQuery(name = "UbezpRpa.findByIiiPrzyKwotaE1", query = "SELECT u FROM UbezpRpa u WHERE u.iiiPrzyKwotaE1 = :iiiPrzyKwotaE1"),
    @NamedQuery(name = "UbezpRpa.findByIiiPrzyRokE2", query = "SELECT u FROM UbezpRpa u WHERE u.iiiPrzyRokE2 = :iiiPrzyRokE2"),
    @NamedQuery(name = "UbezpRpa.findByIiiPrzyKwotaE2", query = "SELECT u FROM UbezpRpa u WHERE u.iiiPrzyKwotaE2 = :iiiPrzyKwotaE2"),
    @NamedQuery(name = "UbezpRpa.findByIiiPrzyRokE3", query = "SELECT u FROM UbezpRpa u WHERE u.iiiPrzyRokE3 = :iiiPrzyRokE3"),
    @NamedQuery(name = "UbezpRpa.findByIiiPrzyKwotaE3", query = "SELECT u FROM UbezpRpa u WHERE u.iiiPrzyKwotaE3 = :iiiPrzyKwotaE3"),
    @NamedQuery(name = "UbezpRpa.findByIiiPrzyRokW1", query = "SELECT u FROM UbezpRpa u WHERE u.iiiPrzyRokW1 = :iiiPrzyRokW1"),
    @NamedQuery(name = "UbezpRpa.findByIiiPrzyKwotaW1", query = "SELECT u FROM UbezpRpa u WHERE u.iiiPrzyKwotaW1 = :iiiPrzyKwotaW1"),
    @NamedQuery(name = "UbezpRpa.findByIiiPrzyRokW2", query = "SELECT u FROM UbezpRpa u WHERE u.iiiPrzyRokW2 = :iiiPrzyRokW2"),
    @NamedQuery(name = "UbezpRpa.findByIiiPrzyKwotaW2", query = "SELECT u FROM UbezpRpa u WHERE u.iiiPrzyKwotaW2 = :iiiPrzyKwotaW2"),
    @NamedQuery(name = "UbezpRpa.findByIiiPrzyRokW3", query = "SELECT u FROM UbezpRpa u WHERE u.iiiPrzyRokW3 = :iiiPrzyRokW3"),
    @NamedQuery(name = "UbezpRpa.findByIiiPrzyKwotaW3", query = "SELECT u FROM UbezpRpa u WHERE u.iiiPrzyKwotaW3 = :iiiPrzyKwotaW3"),
    @NamedQuery(name = "UbezpRpa.findByIiiPrzyKwotaNB", query = "SELECT u FROM UbezpRpa u WHERE u.iiiPrzyKwotaNB = :iiiPrzyKwotaNB"),
    @NamedQuery(name = "UbezpRpa.findByIiiPrzyRokN1", query = "SELECT u FROM UbezpRpa u WHERE u.iiiPrzyRokN1 = :iiiPrzyRokN1"),
    @NamedQuery(name = "UbezpRpa.findByIiiPrzyKwotaN1", query = "SELECT u FROM UbezpRpa u WHERE u.iiiPrzyKwotaN1 = :iiiPrzyKwotaN1"),
    @NamedQuery(name = "UbezpRpa.findByIiiPrzyRokN2", query = "SELECT u FROM UbezpRpa u WHERE u.iiiPrzyRokN2 = :iiiPrzyRokN2"),
    @NamedQuery(name = "UbezpRpa.findByIiiPrzyKwotaN2", query = "SELECT u FROM UbezpRpa u WHERE u.iiiPrzyKwotaN2 = :iiiPrzyKwotaN2"),
    @NamedQuery(name = "UbezpRpa.findByIiiPrzyRokN3", query = "SELECT u FROM UbezpRpa u WHERE u.iiiPrzyRokN3 = :iiiPrzyRokN3"),
    @NamedQuery(name = "UbezpRpa.findByIiiPrzyKwotaN3", query = "SELECT u FROM UbezpRpa u WHERE u.iiiPrzyKwotaN3 = :iiiPrzyKwotaN3"),
    @NamedQuery(name = "UbezpRpa.findByIiiNauOkresOd1", query = "SELECT u FROM UbezpRpa u WHERE u.iiiNauOkresOd1 = :iiiNauOkresOd1"),
    @NamedQuery(name = "UbezpRpa.findByIiiNauOkresDo1", query = "SELECT u FROM UbezpRpa u WHERE u.iiiNauOkresDo1 = :iiiNauOkresDo1"),
    @NamedQuery(name = "UbezpRpa.findByIiiNauLiczWC1", query = "SELECT u FROM UbezpRpa u WHERE u.iiiNauLiczWC1 = :iiiNauLiczWC1"),
    @NamedQuery(name = "UbezpRpa.findByIiiNauMianWC1", query = "SELECT u FROM UbezpRpa u WHERE u.iiiNauMianWC1 = :iiiNauMianWC1"),
    @NamedQuery(name = "UbezpRpa.findByIiiNauOkresOd2", query = "SELECT u FROM UbezpRpa u WHERE u.iiiNauOkresOd2 = :iiiNauOkresOd2"),
    @NamedQuery(name = "UbezpRpa.findByIiiNauOkresDo2", query = "SELECT u FROM UbezpRpa u WHERE u.iiiNauOkresDo2 = :iiiNauOkresDo2"),
    @NamedQuery(name = "UbezpRpa.findByIiiNauLiczWC2", query = "SELECT u FROM UbezpRpa u WHERE u.iiiNauLiczWC2 = :iiiNauLiczWC2"),
    @NamedQuery(name = "UbezpRpa.findByIiiNauMianWC2", query = "SELECT u FROM UbezpRpa u WHERE u.iiiNauMianWC2 = :iiiNauMianWC2"),
    @NamedQuery(name = "UbezpRpa.findByInserttmp", query = "SELECT u FROM UbezpRpa u WHERE u.inserttmp = :inserttmp")})
public class UbezpRpa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Column(name = "ID_DOKUMENT")
    private Integer idDokument;
    @Column(name = "ID_PLATNIK")
    private Integer idPlatnik;
    @Column(name = "ID_UBEZPIECZONY")
    private Integer idUbezpieczony;
    @Column(name = "ID_DOK_ZUS")
    private Integer idDokZus;
    @Column(name = "NR_PODDOKUMENTU")
    private Integer nrPoddokumentu;
    @Column(name = "ID_PL_ZUS")
    private Integer idPlZus;
    @Column(name = "ID_UB_U_PLAT")
    private Integer idUbUPlat;
    @Column(name = "ID_UB_ZUS")
    private Integer idUbZus;
    @Column(name = "ID_WEW_DRA")
    private Integer idWewDra;
    @Size(max = 1)
    @Column(name = "STATUS_KONTROLI", length = 1)
    private String statusKontroli;
    @Column(name = "I_OKRES_RAPORTU")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iOkresRaportu;
    @Size(max = 50)
    @Column(name = "III_NAZWISKO", length = 50)
    private String iiiNazwisko;
    @Size(max = 50)
    @Column(name = "III_IMIE_PIERWSZE", length = 50)
    private String iiiImiePierwsze;
    @Size(max = 1)
    @Column(name = "III_RODZAJ_DOK", length = 1)
    private String iiiRodzajDok;
    @Size(max = 50)
    @Column(name = "III_IDENT_DOK", length = 50)
    private String iiiIdentDok;
    @Size(max = 4)
    @Column(name = "III_KOD_UBEZP", length = 4)
    private String iiiKodUbezp;
    @Size(max = 1)
    @Column(name = "III_PRAWO_DO_EME", length = 1)
    private String iiiPrawoDoEme;
    @Size(max = 1)
    @Column(name = "III_KOD_STOPNIA_NIEP", length = 1)
    private String iiiKodStopniaNiep;
    @Column(name = "III_PRZY_ROK_E_1")
    private Integer iiiPrzyRokE1;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "III_PRZY_KWOTA_E_1", precision = 15, scale = 2)
    private BigDecimal iiiPrzyKwotaE1;
    @Column(name = "III_PRZY_ROK_E_2")
    private Integer iiiPrzyRokE2;
    @Column(name = "III_PRZY_KWOTA_E_2", precision = 15, scale = 2)
    private BigDecimal iiiPrzyKwotaE2;
    @Column(name = "III_PRZY_ROK_E_3")
    private Integer iiiPrzyRokE3;
    @Column(name = "III_PRZY_KWOTA_E_3", precision = 15, scale = 2)
    private BigDecimal iiiPrzyKwotaE3;
    @Column(name = "III_PRZY_ROK_W_1")
    private Integer iiiPrzyRokW1;
    @Column(name = "III_PRZY_KWOTA_W_1", precision = 15, scale = 2)
    private BigDecimal iiiPrzyKwotaW1;
    @Column(name = "III_PRZY_ROK_W_2")
    private Integer iiiPrzyRokW2;
    @Column(name = "III_PRZY_KWOTA_W_2", precision = 15, scale = 2)
    private BigDecimal iiiPrzyKwotaW2;
    @Column(name = "III_PRZY_ROK_W_3")
    private Integer iiiPrzyRokW3;
    @Column(name = "III_PRZY_KWOTA_W_3", precision = 15, scale = 2)
    private BigDecimal iiiPrzyKwotaW3;
    @Column(name = "III_PRZY_KWOTA_N_B", precision = 15, scale = 2)
    private BigDecimal iiiPrzyKwotaNB;
    @Column(name = "III_PRZY_ROK_N_1")
    private Integer iiiPrzyRokN1;
    @Column(name = "III_PRZY_KWOTA_N_1", precision = 15, scale = 2)
    private BigDecimal iiiPrzyKwotaN1;
    @Column(name = "III_PRZY_ROK_N_2")
    private Integer iiiPrzyRokN2;
    @Column(name = "III_PRZY_KWOTA_N_2", precision = 15, scale = 2)
    private BigDecimal iiiPrzyKwotaN2;
    @Column(name = "III_PRZY_ROK_N_3")
    private Integer iiiPrzyRokN3;
    @Column(name = "III_PRZY_KWOTA_N_3", precision = 15, scale = 2)
    private BigDecimal iiiPrzyKwotaN3;
    @Column(name = "III_NAU_OKRES_OD_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiNauOkresOd1;
    @Column(name = "III_NAU_OKRES_DO_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiNauOkresDo1;
    @Size(max = 3)
    @Column(name = "III_NAU_LICZ_W_C_1", length = 3)
    private String iiiNauLiczWC1;
    @Size(max = 3)
    @Column(name = "III_NAU_MIAN_W_C_1", length = 3)
    private String iiiNauMianWC1;
    @Column(name = "III_NAU_OKRES_OD_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiNauOkresOd2;
    @Column(name = "III_NAU_OKRES_DO_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iiiNauOkresDo2;
    @Size(max = 3)
    @Column(name = "III_NAU_LICZ_W_C_2", length = 3)
    private String iiiNauLiczWC2;
    @Size(max = 3)
    @Column(name = "III_NAU_MIAN_W_C_2", length = 3)
    private String iiiNauMianWC2;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public UbezpRpa() {
    }

    public UbezpRpa(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdDokument() {
        return idDokument;
    }

    public void setIdDokument(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public Integer getIdPlatnik() {
        return idPlatnik;
    }

    public void setIdPlatnik(Integer idPlatnik) {
        this.idPlatnik = idPlatnik;
    }

    public Integer getIdUbezpieczony() {
        return idUbezpieczony;
    }

    public void setIdUbezpieczony(Integer idUbezpieczony) {
        this.idUbezpieczony = idUbezpieczony;
    }

    public Integer getIdDokZus() {
        return idDokZus;
    }

    public void setIdDokZus(Integer idDokZus) {
        this.idDokZus = idDokZus;
    }

    public Integer getNrPoddokumentu() {
        return nrPoddokumentu;
    }

    public void setNrPoddokumentu(Integer nrPoddokumentu) {
        this.nrPoddokumentu = nrPoddokumentu;
    }

    public Integer getIdPlZus() {
        return idPlZus;
    }

    public void setIdPlZus(Integer idPlZus) {
        this.idPlZus = idPlZus;
    }

    public Integer getIdUbUPlat() {
        return idUbUPlat;
    }

    public void setIdUbUPlat(Integer idUbUPlat) {
        this.idUbUPlat = idUbUPlat;
    }

    public Integer getIdUbZus() {
        return idUbZus;
    }

    public void setIdUbZus(Integer idUbZus) {
        this.idUbZus = idUbZus;
    }

    public Integer getIdWewDra() {
        return idWewDra;
    }

    public void setIdWewDra(Integer idWewDra) {
        this.idWewDra = idWewDra;
    }

    public String getStatusKontroli() {
        return statusKontroli;
    }

    public void setStatusKontroli(String statusKontroli) {
        this.statusKontroli = statusKontroli;
    }

    public Date getIOkresRaportu() {
        return iOkresRaportu;
    }

    public void setIOkresRaportu(Date iOkresRaportu) {
        this.iOkresRaportu = iOkresRaportu;
    }

    public String getIiiNazwisko() {
        return iiiNazwisko;
    }

    public void setIiiNazwisko(String iiiNazwisko) {
        this.iiiNazwisko = iiiNazwisko;
    }

    public String getIiiImiePierwsze() {
        return iiiImiePierwsze;
    }

    public void setIiiImiePierwsze(String iiiImiePierwsze) {
        this.iiiImiePierwsze = iiiImiePierwsze;
    }

    public String getIiiRodzajDok() {
        return iiiRodzajDok;
    }

    public void setIiiRodzajDok(String iiiRodzajDok) {
        this.iiiRodzajDok = iiiRodzajDok;
    }

    public String getIiiIdentDok() {
        return iiiIdentDok;
    }

    public void setIiiIdentDok(String iiiIdentDok) {
        this.iiiIdentDok = iiiIdentDok;
    }

    public String getIiiKodUbezp() {
        return iiiKodUbezp;
    }

    public void setIiiKodUbezp(String iiiKodUbezp) {
        this.iiiKodUbezp = iiiKodUbezp;
    }

    public String getIiiPrawoDoEme() {
        return iiiPrawoDoEme;
    }

    public void setIiiPrawoDoEme(String iiiPrawoDoEme) {
        this.iiiPrawoDoEme = iiiPrawoDoEme;
    }

    public String getIiiKodStopniaNiep() {
        return iiiKodStopniaNiep;
    }

    public void setIiiKodStopniaNiep(String iiiKodStopniaNiep) {
        this.iiiKodStopniaNiep = iiiKodStopniaNiep;
    }

    public Integer getIiiPrzyRokE1() {
        return iiiPrzyRokE1;
    }

    public void setIiiPrzyRokE1(Integer iiiPrzyRokE1) {
        this.iiiPrzyRokE1 = iiiPrzyRokE1;
    }

    public BigDecimal getIiiPrzyKwotaE1() {
        return iiiPrzyKwotaE1;
    }

    public void setIiiPrzyKwotaE1(BigDecimal iiiPrzyKwotaE1) {
        this.iiiPrzyKwotaE1 = iiiPrzyKwotaE1;
    }

    public Integer getIiiPrzyRokE2() {
        return iiiPrzyRokE2;
    }

    public void setIiiPrzyRokE2(Integer iiiPrzyRokE2) {
        this.iiiPrzyRokE2 = iiiPrzyRokE2;
    }

    public BigDecimal getIiiPrzyKwotaE2() {
        return iiiPrzyKwotaE2;
    }

    public void setIiiPrzyKwotaE2(BigDecimal iiiPrzyKwotaE2) {
        this.iiiPrzyKwotaE2 = iiiPrzyKwotaE2;
    }

    public Integer getIiiPrzyRokE3() {
        return iiiPrzyRokE3;
    }

    public void setIiiPrzyRokE3(Integer iiiPrzyRokE3) {
        this.iiiPrzyRokE3 = iiiPrzyRokE3;
    }

    public BigDecimal getIiiPrzyKwotaE3() {
        return iiiPrzyKwotaE3;
    }

    public void setIiiPrzyKwotaE3(BigDecimal iiiPrzyKwotaE3) {
        this.iiiPrzyKwotaE3 = iiiPrzyKwotaE3;
    }

    public Integer getIiiPrzyRokW1() {
        return iiiPrzyRokW1;
    }

    public void setIiiPrzyRokW1(Integer iiiPrzyRokW1) {
        this.iiiPrzyRokW1 = iiiPrzyRokW1;
    }

    public BigDecimal getIiiPrzyKwotaW1() {
        return iiiPrzyKwotaW1;
    }

    public void setIiiPrzyKwotaW1(BigDecimal iiiPrzyKwotaW1) {
        this.iiiPrzyKwotaW1 = iiiPrzyKwotaW1;
    }

    public Integer getIiiPrzyRokW2() {
        return iiiPrzyRokW2;
    }

    public void setIiiPrzyRokW2(Integer iiiPrzyRokW2) {
        this.iiiPrzyRokW2 = iiiPrzyRokW2;
    }

    public BigDecimal getIiiPrzyKwotaW2() {
        return iiiPrzyKwotaW2;
    }

    public void setIiiPrzyKwotaW2(BigDecimal iiiPrzyKwotaW2) {
        this.iiiPrzyKwotaW2 = iiiPrzyKwotaW2;
    }

    public Integer getIiiPrzyRokW3() {
        return iiiPrzyRokW3;
    }

    public void setIiiPrzyRokW3(Integer iiiPrzyRokW3) {
        this.iiiPrzyRokW3 = iiiPrzyRokW3;
    }

    public BigDecimal getIiiPrzyKwotaW3() {
        return iiiPrzyKwotaW3;
    }

    public void setIiiPrzyKwotaW3(BigDecimal iiiPrzyKwotaW3) {
        this.iiiPrzyKwotaW3 = iiiPrzyKwotaW3;
    }

    public BigDecimal getIiiPrzyKwotaNB() {
        return iiiPrzyKwotaNB;
    }

    public void setIiiPrzyKwotaNB(BigDecimal iiiPrzyKwotaNB) {
        this.iiiPrzyKwotaNB = iiiPrzyKwotaNB;
    }

    public Integer getIiiPrzyRokN1() {
        return iiiPrzyRokN1;
    }

    public void setIiiPrzyRokN1(Integer iiiPrzyRokN1) {
        this.iiiPrzyRokN1 = iiiPrzyRokN1;
    }

    public BigDecimal getIiiPrzyKwotaN1() {
        return iiiPrzyKwotaN1;
    }

    public void setIiiPrzyKwotaN1(BigDecimal iiiPrzyKwotaN1) {
        this.iiiPrzyKwotaN1 = iiiPrzyKwotaN1;
    }

    public Integer getIiiPrzyRokN2() {
        return iiiPrzyRokN2;
    }

    public void setIiiPrzyRokN2(Integer iiiPrzyRokN2) {
        this.iiiPrzyRokN2 = iiiPrzyRokN2;
    }

    public BigDecimal getIiiPrzyKwotaN2() {
        return iiiPrzyKwotaN2;
    }

    public void setIiiPrzyKwotaN2(BigDecimal iiiPrzyKwotaN2) {
        this.iiiPrzyKwotaN2 = iiiPrzyKwotaN2;
    }

    public Integer getIiiPrzyRokN3() {
        return iiiPrzyRokN3;
    }

    public void setIiiPrzyRokN3(Integer iiiPrzyRokN3) {
        this.iiiPrzyRokN3 = iiiPrzyRokN3;
    }

    public BigDecimal getIiiPrzyKwotaN3() {
        return iiiPrzyKwotaN3;
    }

    public void setIiiPrzyKwotaN3(BigDecimal iiiPrzyKwotaN3) {
        this.iiiPrzyKwotaN3 = iiiPrzyKwotaN3;
    }

    public Date getIiiNauOkresOd1() {
        return iiiNauOkresOd1;
    }

    public void setIiiNauOkresOd1(Date iiiNauOkresOd1) {
        this.iiiNauOkresOd1 = iiiNauOkresOd1;
    }

    public Date getIiiNauOkresDo1() {
        return iiiNauOkresDo1;
    }

    public void setIiiNauOkresDo1(Date iiiNauOkresDo1) {
        this.iiiNauOkresDo1 = iiiNauOkresDo1;
    }

    public String getIiiNauLiczWC1() {
        return iiiNauLiczWC1;
    }

    public void setIiiNauLiczWC1(String iiiNauLiczWC1) {
        this.iiiNauLiczWC1 = iiiNauLiczWC1;
    }

    public String getIiiNauMianWC1() {
        return iiiNauMianWC1;
    }

    public void setIiiNauMianWC1(String iiiNauMianWC1) {
        this.iiiNauMianWC1 = iiiNauMianWC1;
    }

    public Date getIiiNauOkresOd2() {
        return iiiNauOkresOd2;
    }

    public void setIiiNauOkresOd2(Date iiiNauOkresOd2) {
        this.iiiNauOkresOd2 = iiiNauOkresOd2;
    }

    public Date getIiiNauOkresDo2() {
        return iiiNauOkresDo2;
    }

    public void setIiiNauOkresDo2(Date iiiNauOkresDo2) {
        this.iiiNauOkresDo2 = iiiNauOkresDo2;
    }

    public String getIiiNauLiczWC2() {
        return iiiNauLiczWC2;
    }

    public void setIiiNauLiczWC2(String iiiNauLiczWC2) {
        this.iiiNauLiczWC2 = iiiNauLiczWC2;
    }

    public String getIiiNauMianWC2() {
        return iiiNauMianWC2;
    }

    public void setIiiNauMianWC2(String iiiNauMianWC2) {
        this.iiiNauMianWC2 = iiiNauMianWC2;
    }

    public Integer getInserttmp() {
        return inserttmp;
    }

    public void setInserttmp(Integer inserttmp) {
        this.inserttmp = inserttmp;
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
        if (!(object instanceof UbezpRpa)) {
            return false;
        }
        UbezpRpa other = (UbezpRpa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.UbezpRpa[ id=" + id + " ]";
    }
    
}
