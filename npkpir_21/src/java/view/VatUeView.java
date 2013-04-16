/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Dokcomparator;
import dao.DokDAO;
import embeddable.Mce;
import entity.Dok;
import entity.Klienci;
import entity.Podatnik;
import entity.Uz;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean(name = "vatUeView")
@RequestScoped
public class VatUeView implements Serializable {
    //tablica obiektów

    private List<Dok> obiektDOKjsf;
    //tablica obiektw danego klienta
    private List<Dok> obiektDOKjsfSel;
    //tablica obiektw danego klienta
    private List<Dok> obiektDOKjsfSelRok;
    //dokumenty o tym samym okresie vat
    private List<Dok> dokvatmc;
  
    /*pkpir*/
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject private DokDAO dokDAO;
    private List<Dok> listawybranych;
    @Inject private Uz uzytkownik;
    //lista gdzie beda podsumowane wartosci
    private List<Dok> podsumowanie;
    
    public VatUeView() {
        //dokumenty podatnika
        obiektDOKjsfSel = new ArrayList<>();
        //dokumenty podatnika z roku
        obiektDOKjsfSelRok = new ArrayList<>();
        //dekumenty o tym samym okresie vat
        dokvatmc = new ArrayList<>();
        listawybranych = new ArrayList<>();
        podsumowanie = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
            Integer rok = wpisView.getRokWpisu();
            String mc = wpisView.getMiesiacWpisu();
            String podatnik = wpisView.getPodatnikWpisu();
            Podatnik pod = wpisView.getPodatnikObiekt();
            uzytkownik = wpisView.getWprowadzil();
            try {
                obiektDOKjsfSel.addAll(dokDAO.zwrocBiezacegoKlienta(wpisView.getPodatnikWpisu()));
                //sortowanie dokumentów
                    Collections.sort(obiektDOKjsfSel, new Dokcomparator());
                //
                int numerkolejny = 1;
                for(Dok p : obiektDOKjsfSel){
                    p.setNrWpkpir(numerkolejny++);
                }
            } catch (Exception e) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + e.toString());
            }
            String m = wpisView.getMiesiacWpisu();
            Integer m1 = Integer.parseInt(m);
            String mn = Mce.getMapamcy().get(m1);
            Integer r = wpisView.getRokWpisu();
            Set<Klienci> klienty = new HashSet<>();
            int numerkolejny = 1;
            for(Dok tmpx : obiektDOKjsfSel){
                if (tmpx.getPkpirR().equals(r.toString())) {
                    tmpx.setNrWpkpir(numerkolejny++);
                    obiektDOKjsfSelRok.add(tmpx);
                    if (tmpx.getVatM().equals(mn)) {
                        if(tmpx.getTypdokumentu().equals("WNT")||tmpx.getTypdokumentu().equals("WDT")){
                        dokvatmc.add(tmpx);
                        klienty.add(tmpx.getKontr());
                        }
                    }
                }
            }
            //a teraz podsumuj klientów
            ListIterator itr;
            itr = dokvatmc.listIterator();
            while(itr.hasNext()){
                Dok p = (Dok) itr.next();
                if(podsumowanie.isEmpty()){
                    podsumowanie.add(p);
                    itr.remove();
                } else {
                ListIterator it;
                it = podsumowanie.listIterator();
                 while(it.hasNext()){
                        Dok s = (Dok) it.next();
                        double tmp = 0.0;
                       if(p.getKontr().getNip().equals(s.getKontr().getNip())){
                           tmp = s.getNetto();
                           tmp += p.getNetto();
                           s.setNetto(tmp);
                           break;
                       } else {
                           it.add(p);
                           break;
                       }
                    }
                }
            }
    }
            

    public List<Dok> getDokvatmc() {
        return dokvatmc;
    }

    public void setDokvatmc(List<Dok> dokvatmc) {
        this.dokvatmc = dokvatmc;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<Dok> getListawybranych() {
        return listawybranych;
    }

    public void setListawybranych(List<Dok> listawybranych) {
        this.listawybranych = listawybranych;
    }

    public List<Dok> getPodsumowanie() {
        return podsumowanie;
    }

    public void setPodsumowanie(List<Dok> podsumowanie) {
        this.podsumowanie = podsumowanie;
    }

   
 
}
