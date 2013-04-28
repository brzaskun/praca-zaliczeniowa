/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Dokcomparator;
import dao.DokDAO;
import embeddable.Kwartaly;
import embeddable.Mce;
import embeddable.VatUe;
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
    private List<VatUe> podsumowanie;
    
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
    public void init() throws Exception {
            Integer rok = wpisView.getRokWpisu();
            String mc = wpisView.getMiesiacWpisu();
            String podatnik = wpisView.getPodatnikWpisu();
            Podatnik pod = wpisView.getPodatnikObiekt();
            uzytkownik = wpisView.getWprowadzil();
            try {
                obiektDOKjsfSel.addAll(dokDAO.zwrocBiezacegoKlientaRok(wpisView.getPodatnikWpisu(),wpisView.getRokWpisu().toString()));
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
            dokvatmc.addAll(zmodyfikujliste(obiektDOKjsfSel, "kwartał"));
            //a teraz podsumuj klientów
            for(Dok p : dokvatmc){
                for(VatUe s : podsumowanie){
                       if(p.getKontr().getNip().equals(s.getKontrahent().getNip())&&p.getTypdokumentu().equals(s.getTransakcja())){
                           s.setNetto(p.getNetto()+s.getNetto());
                           s.setLiczbadok(s.getLiczbadok()+1);
                           s.getZawiera().add(p);
                           break;
                       }
                    }
                }
            System.out.println("l");
            }
    
      private List<Dok> zmodyfikujliste(List<Dok> listadokvat, String vatokres) throws Exception {
        Set<VatUe> klienty = new HashSet<>();
         if(vatokres.equals("blad")){
            throw new Exception("Nie ma ustawionego parametru vat za dany okres");
         } else if (vatokres.equals("miesięczne")){
             List<Dok> listatymczasowa = new ArrayList<>();
             for(Dok p : listadokvat){
                 if(p.getVatM().equals(wpisView.getMiesiacWpisu())){
                     listatymczasowa.add(p);
                 }
             }
             return listatymczasowa;
         } else {
             List<Dok> listatymczasowa = new ArrayList<>();
             Integer kwartal = Integer.parseInt(Kwartaly.getMapanrkw().get(Integer.parseInt(wpisView.getMiesiacWpisu())));
             List<String> miesiacewkwartale = Kwartaly.getMapakwnr().get(kwartal);
             for(Dok p : listadokvat){
                 if(p.getVatM().equals(miesiacewkwartale.get(0))||p.getVatM().equals(miesiacewkwartale.get(1))||p.getVatM().equals(miesiacewkwartale.get(2))){
                     listatymczasowa.add(p);
                 }
                 if(p.getTypdokumentu().equals("WNT")||p.getTypdokumentu().equals("WDT")){
                    klienty.add(new VatUe(p.getTypdokumentu(),p.getKontr(),0.0,0,new ArrayList<Dok>()));
             }
             }
             podsumowanie.addAll(klienty);
             return listatymczasowa;
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

    public List<VatUe> getPodsumowanie() {
        return podsumowanie;
    }

    public void setPodsumowanie(List<VatUe> podsumowanie) {
        this.podsumowanie = podsumowanie;
    }

   
   
 
}
