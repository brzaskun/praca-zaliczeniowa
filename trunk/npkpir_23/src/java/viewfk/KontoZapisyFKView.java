/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.KontoDAOfk;
import daoFK.KontoZapisyFKDAO;
import entityfk.Konto;
import entityfk.Kontozapisy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import org.jboss.weld.util.collections.ArraySet;
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
    private List<Kontozapisy> kontorozrachunki;
    @Inject private KontoZapisyFKDAO kontoZapisyFKDAO;
    @Inject private KontoDAOfk kontoDAOfk;
    @Inject private Konto wybranekonto;
    private Double sumaWn;
    private Double sumaMa;
    private Double saldoWn;
    private Double saldoMa;
    

    public KontoZapisyFKView() {
        kontozapisy = new ArrayList<>();
    }
    
    @PostConstruct
    private void init(){
    }
    
      public void pobierzZapisyNaKoncie() {
         kontozapisy = new ArrayList<>();
         kontozapisy = kontoZapisyFKDAO.findZapisyKontoPodatnik("Kowalski", wybranekonto.getPelnynumer());
     }
    
    public void selekcjakont(){
        kontozapisy = new ArrayList<>();
        List<Konto> konta = kontoDAOfk.findAll();
        for(Konto p : konta){
            if(p.getPelnynumer().startsWith(wybranekonto.getPelnynumer())){
                kontozapisy.addAll(kontoZapisyFKDAO.findZapisyKonto(p.getPelnynumer()));
            }
        }
        sumazapisow();
        RequestContext.getCurrentInstance().update("formB:sumy");
        RequestContext.getCurrentInstance().update("formD:dataList");
        RequestContext.getCurrentInstance().update("formE:dataList");
    }
    
    public void selekcjakontrozrachunki(){
        kontorozrachunki = new ArrayList<>();
//        List<Konto> konta = kontoDAOfk.findAll();
//        for(Konto p : konta){
//            if(p.getPelnynumer().startsWith(wybranyzapis.getKonto()){
                kontorozrachunki.addAll(kontoZapisyFKDAO.findZapisyKonto(wybranyzapis.getKonto()));
                boolean wn = (wybranyzapis.getKwotawn() > 0 ? true : false);
                Iterator it;
                it = kontorozrachunki.iterator();
                while(it.hasNext()){
                    Kontozapisy p = (Kontozapisy) it.next();
                    if(wn && p.getKwotawn()>0){
                        it.remove();
                    }
                    if(!wn && p.getKwotama()>0){
                        it.remove();
                    }
                }
//            }
//        }
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

    public Konto getWybranekonto() {
        return wybranekonto;
    }

    public void setWybranekonto(Konto wybranekonto) {
        this.wybranekonto = wybranekonto;
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

    public List<Kontozapisy> getKontorozrachunki() {
        return kontorozrachunki;
    }

    public void setKontorozrachunki(List<Kontozapisy> kontorozrachunki) {
        this.kontorozrachunki = kontorozrachunki;
    }

    public Kontozapisy getWybranyzapis() {
        return wybranyzapis;
    }

    public void setWybranyzapis(Kontozapisy wybranyzapis) {
        this.wybranyzapis = wybranyzapis;
    }

   
}

