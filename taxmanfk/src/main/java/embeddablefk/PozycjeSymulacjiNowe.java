/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddablefk;

import entity.Podatnik;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Osito
 */
public class PozycjeSymulacjiNowe  implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private int id;
    private Podatnik podatnik;
    private String udzialowiec;
    private String udzialowiecid;
    private double udzial;
    private String rok;
    private String mc;
    private double przychody;
    private double koszty;
    private double wynikfinansowy;
    private double przychodyPodatkowe;
    private double kosztyPodatkowe;
    private double wynikPodatkowyWstępny;
    private double nkup;
    private double kupmn;
    private double kupmn_poprzedniemce;
    private double npup;
    private double pmn;
    private double pmn_poprzedniemce;
    private double wynikpodatkowy;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.podatnik);
        hash = 79 * hash + Objects.hashCode(this.udzialowiec);
        hash = 79 * hash + Objects.hashCode(this.rok);
        hash = 79 * hash + Objects.hashCode(this.mc);
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
        final PozycjeSymulacjiNowe other = (PozycjeSymulacjiNowe) obj;
        if (!Objects.equals(this.udzialowiec, other.udzialowiec)) {
            return false;
        }
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.mc, other.mc)) {
            return false;
        }
        if (!Objects.equals(this.podatnik, other.podatnik)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PozycjeSymulacjiNowe{" + "podatnik=" + podatnik.getNazwapelna() + ", udzialowiec=" + udzialowiec + ", rok=" + rok + ", mc=" + mc + ", przychody=" + przychody + ", koszty=" + koszty + ", wynikfinansowy=" + wynikfinansowy + ", nkup=" + nkup + ", kupmn=" + kupmn + ", kupmn_poprzedniemce=" + kupmn_poprzedniemce + ", npup=" + npup + ", pmn=" + pmn + ", pmn_poprzedniemce=" + pmn_poprzedniemce + ", wynikpodatkowy=" + wynikpodatkowy + '}';
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

    public String getUdzialowiec() {
        return udzialowiec;
    }

    public void setUdzialowiec(String udzialowiec) {
        this.udzialowiec = udzialowiec;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public double getPrzychody() {
        return przychody;
    }

    public void setPrzychody(double przychody) {
        this.przychody = przychody;
    }

    public double getKoszty() {
        return koszty;
    }

    public void setKoszty(double koszty) {
        this.koszty = koszty;
    }

    public double getWynikfinansowy() {
        return wynikfinansowy;
    }

    public void setWynikfinansowy(double wynikfinansowy) {
        this.wynikfinansowy = wynikfinansowy;
    }

    public double getPrzychodyPodatkowe() {
        return przychodyPodatkowe;
    }

    public void setPrzychodyPodatkowe(double przychodyPodatkowe) {
        this.przychodyPodatkowe = przychodyPodatkowe;
    }

    public double getKosztyPodatkowe() {
        return kosztyPodatkowe;
    }

    public void setKosztyPodatkowe(double kosztyPodatkowe) {
        this.kosztyPodatkowe = kosztyPodatkowe;
    }

    public double getWynikPodatkowyWstępny() {
        return wynikPodatkowyWstępny;
    }

    public void setWynikPodatkowyWstępny(double wynikPodatkowyWstępny) {
        this.wynikPodatkowyWstępny = wynikPodatkowyWstępny;
    }

    public double getNkup() {
        return nkup;
    }

    public void setNkup(double nkup) {
        this.nkup = nkup;
    }

    public double getKupmn() {
        return kupmn;
    }

    public void setKupmn(double kupmn) {
        this.kupmn = kupmn;
    }

    public double getKupmn_poprzedniemce() {
        return kupmn_poprzedniemce;
    }

    public void setKupmn_poprzedniemce(double kupmn_poprzedniemce) {
        this.kupmn_poprzedniemce = kupmn_poprzedniemce;
    }

    public double getNpup() {
        return npup;
    }

    public void setNpup(double npup) {
        this.npup = npup;
    }

    public double getPmn() {
        return pmn;
    }

    public void setPmn(double pmn) {
        this.pmn = pmn;
    }

    public double getPmn_poprzedniemce() {
        return pmn_poprzedniemce;
    }

    public void setPmn_poprzedniemce(double pmn_poprzedniemce) {
        this.pmn_poprzedniemce = pmn_poprzedniemce;
    }

    public double getWynikpodatkowy() {
        return wynikpodatkowy;
    }

    public void setWynikpodatkowy(double wynikpodatkowy) {
        this.wynikpodatkowy = wynikpodatkowy;
    }

    public double getUdzial() {
        return udzial;
    }

    public void setUdzial(double udzial) {
        this.udzial = udzial;
    }

    public String getUdzialowiecid() {
        return udzialowiecid;
    }

    public void setUdzialowiecid(String udzialowiecid) {
        this.udzialowiecid = udzialowiecid;
    }
    
    
    
    
}
