/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.RozrachunkiDAO;
import daoFK.KontoDAOfk;
import daoFK.KontoZapisyFKDAO;
import entityfk.Rozrachunki;
import entityfk.Konto;
import entityfk.Kontozapisy;
import entityfk.RozrachunkiPK;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class KontoZapisyFKView implements Serializable{
    private List<Kontozapisy> kontozapisy;
    @Inject private Kontozapisy wybranyzapis;
    private List<Rozrachunki> kontorozrachunki;
    @Inject private KontoZapisyFKDAO kontoZapisyFKDAO;
    @Inject private KontoDAOfk kontoDAOfk;
    @Inject private Konto numerkonta;
    private Double sumaWn;
    private Double sumaMa;
    private Double saldoWn;
    private Double saldoMa;
    @Inject private RozrachunkiDAO rozrachunkiDAO;
    

    public KontoZapisyFKView() {
        kontozapisy = new ArrayList<>();
    }
    
    @PostConstruct
    private void init(){
        kontozapisy = kontoZapisyFKDAO.findAll();
        kontorozrachunki = new ArrayList<>();
    }
    
    public void selekcjakont(){
        kontozapisy = new ArrayList<>();
        List<Konto> konta = kontoDAOfk.findAll();
        for(Konto p : konta){
            if(p.getPelnynumer().startsWith(numerkonta.getPelnynumer())){
                kontozapisy.addAll(kontoZapisyFKDAO.findZapisyKonto(p.getPelnynumer()));
            }
        }
        sumazapisow();
        RequestContext.getCurrentInstance().update("formB:sumy");
        RequestContext.getCurrentInstance().update("formD:dataList");
        RequestContext.getCurrentInstance().update("formE:dataList");
    }
    
    public void selekcjakontrozrachunki() {
        kontorozrachunki = new ArrayList<>();
        List<Kontozapisy> zapisywszystie = kontoZapisyFKDAO.findZapisyKonto(wybranyzapis.getKonto());
        List<Rozrachunki> rozliczone = rozrachunkiDAO.findRozliczany(wybranyzapis.getId());
        boolean wn = (wybranyzapis.getKwotawn() > 0 ? true : false);
        Iterator it;
        it = zapisywszystie.iterator();
        while (it.hasNext()) {
            Kontozapisy p = (Kontozapisy) it.next();
            if (wn && p.getKwotawn() > 0) {
                it.remove();
            }
            if (!wn && p.getKwotama() > 0) {
                it.remove();
            }
        }
        for (Kontozapisy r : zapisywszystie){
            RozrachunkiPK klucz = new RozrachunkiPK();
            klucz.setZapisrozliczany(wybranyzapis.getId());
            klucz.setZapissparowany(r.getId());
            Rozrachunki nowyrozrachunek = new Rozrachunki();
            nowyrozrachunek.setRozrachunkiPK(klucz);
            nowyrozrachunek.setZapisrozliczany(wybranyzapis);
            nowyrozrachunek.setZapissparowany(r);
            nowyrozrachunek.setKwotarozrachunku(0);
            kontorozrachunki.add(nowyrozrachunek);
            r.getRozrachunkiZapisrozliczany().add(nowyrozrachunek);
            kontoZapisyFKDAO.edit(r);
        }
        RequestContext.getCurrentInstance().update("formB:sumy");
        RequestContext.getCurrentInstance().update("formD:dataList");
        RequestContext.getCurrentInstance().update("formE");
    }
    
    private void sumazapisow(){
        sumaWn = 0.0;
        sumaMa = 0.0;
        for(Kontozapisy p : kontozapisy){
            sumaWn = sumaWn + p.getKwotawn();
            sumaMa = sumaMa + p.getKwotama();
        }
        saldoWn = 0.0;
        saldoMa = 0.0;
        if(sumaWn>sumaMa){
            saldoWn = sumaWn-sumaMa;
        } else {
            saldoMa = sumaMa-sumaWn;
        }
    }

    public List<Kontozapisy> getKontozapisy() {
        return kontozapisy;
    }

    public void setKontozapisy(List<Kontozapisy> kontozapisy) {
        this.kontozapisy = kontozapisy;
    }

    public KontoZapisyFKDAO getKontoZapisyFKDAO() {
        return kontoZapisyFKDAO;
    }

    public void setKontoZapisyFKDAO(KontoZapisyFKDAO kontoZapisyFKDAO) {
        this.kontoZapisyFKDAO = kontoZapisyFKDAO;
    }

    public Konto getNumerkonta() {
        return numerkonta;
    }

    public void setNumerkonta(Konto numerkonta) {
        this.numerkonta = numerkonta;
    }

    public Double getSumaWn() {
        return sumaWn;
    }

    public void setSumaWn(Double sumaWn) {
        this.sumaWn = sumaWn;
    }

    public Double getSumaMa() {
        return sumaMa;
    }

    public void setSumaMa(Double sumaMa) {
        this.sumaMa = sumaMa;
    }

    public Double getSaldoWn() {
        return saldoWn;
    }

    public void setSaldoWn(Double saldoWn) {
        this.saldoWn = saldoWn;
    }

    public Double getSaldoMa() {
        return saldoMa;
    }

    public void setSaldoMa(Double saldoMa) {
        this.saldoMa = saldoMa;
    }

    public List<Rozrachunki> getKontorozrachunki() {
        return kontorozrachunki;
    }

    public void setKontorozrachunki(List<Rozrachunki> kontorozrachunki) {
        this.kontorozrachunki = kontorozrachunki;
    }

   

    public Kontozapisy getWybranyzapis() {
        return wybranyzapis;
    }

    public void setWybranyzapis(Kontozapisy wybranyzapis) {
        this.wybranyzapis = wybranyzapis;
    }

   
}

