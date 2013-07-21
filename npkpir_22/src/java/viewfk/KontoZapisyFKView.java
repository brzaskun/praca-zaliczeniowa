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
    @Inject private KontoZapisyFKDAO kontoZapisyFKDAO;
    @Inject private KontoDAOfk kontoDAOfk;
    @Inject private Konto numerkonta;
    private Double sumaWn;
    private Double sumaMa;
    private Double saldoWn;
    private Double saldoMa;
    

    public KontoZapisyFKView() {
        kontozapisy = new ArrayList<>();
    }
    
    @PostConstruct
    private void init(){
        kontozapisy = kontoZapisyFKDAO.findAll();
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

    
   
}
