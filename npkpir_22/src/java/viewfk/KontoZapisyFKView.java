/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.KontoZapisyFKDAO;
import entityfk.Konto;
import entityfk.Kontozapisy;
import java.io.Serializable;
import java.util.ArrayList;
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
    @Inject private KontoZapisyFKDAO kontoZapisyFKDAO;
    @Inject private Konto numerkonta;

    public KontoZapisyFKView() {
        kontozapisy = new ArrayList<>();
    }
    
    @PostConstruct
    private void init(){
        kontozapisy = kontoZapisyFKDAO.findAll();
    }
    
    public void selekcjakont(){
        kontozapisy = new ArrayList<>();
        if(numerkonta.isMapotomkow()){
            kontozapisy = kontoZapisyFKDAO.findZapisyKontoMacierzyste(numerkonta.getPelnynumer());
        }
        kontozapisy.addAll(kontoZapisyFKDAO.findZapisyKonto(numerkonta.getPelnynumer()));
        RequestContext.getCurrentInstance().update("formD:dataList");
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

  
}
