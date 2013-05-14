/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Kontocomparator;
import dao.KontoDAO;
import entityfk.Konto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class PlanKontView implements Serializable{
 
    private List<Konto> wykazkont;
    private static List<Konto> wykazkontS;
    private static String opiskonta;
    private static String pelnynumerkonta;
    @Inject private Konto selected;
    @Inject private Konto nowe;
    @Inject private KontoDAO kontoDAO;

    public PlanKontView() {
        this.wykazkont = new ArrayList<>();
    }

    @PostConstruct
    private void init(){
     List<Konto> wykazkonttmp = kontoDAO.findAll();
     for(Konto p : wykazkonttmp){
            p.setRozwin(false);
            kontoDAO.edit(p);
        }
     wykazkontS = kontoDAO.findAll();
     opiskonta="";
     pelnynumerkonta="";
     for(Konto t : wykazkontS){
         opiskonta = opiskonta+t.getNazwapelna()+",";
         pelnynumerkonta = pelnynumerkonta+t.getPelnynumer()+",";
     }
     //wyszukujemy syntetyczne
     Collections.sort(wykazkonttmp, new Kontocomparator());
     for(Konto p : wykazkonttmp){
         if(p.getSyntetyczne().equals("syntetyczne")){
            wykazkont.add(p);
         } 
     }
     Collections.sort(wykazkont, new Kontocomparator());
    }
    
       
    private void przebuduj(Konto macierzyste){
     List<Konto> wykazkonttmp = kontoDAO.findAll();
     //wyszukujemy syntetyczne
     Collections.sort(wykazkonttmp, new Kontocomparator());
     for(Konto p : wykazkonttmp){
         if(p.getMacierzyste().equals(macierzyste.getPelnynumer())) {
            wykazkont.add(p);
         }
     }
     Collections.sort(wykazkont, new Kontocomparator());
    }
    
    public void rozwinwszystkie(){
     wykazkont.clear();
     List<Konto> wykazkonttmp = kontoDAO.findAll();
     //wyszukujemy syntetyczne
     Collections.sort(wykazkonttmp, new Kontocomparator());
     for(Konto p : wykazkonttmp){
            p.setRozwin(true);
            wykazkont.add(p);
     }
     Collections.sort(wykazkont, new Kontocomparator());
    }  
    
    public void zwinwszystkie(){
     wykazkont.clear();
     List<Konto> wykazkonttmp = kontoDAO.findAll();
     //wyszukujemy syntetyczne
     Collections.sort(wykazkonttmp, new Kontocomparator());
     for(Konto p : wykazkonttmp){
            p.setRozwin(false);
            if(p.getSyntetyczne().equals("syntetyczne")){
            wykazkont.add(p);
         } 
     }
     Collections.sort(wykazkont, new Kontocomparator());
    }    
    
    
     private void przebudujX(Konto macierzyste){
     List<Konto> wykazkonttmp = kontoDAO.findAll();
     //wyszukujemy syntetyczne
     Collections.sort(wykazkonttmp, new Kontocomparator());
     for(Konto p : wykazkonttmp){
         if(p.getMacierzyste().equals(macierzyste.getPelnynumer())) {
            wykazkont.remove(p);
         }
     }
     Collections.sort(wykazkont, new Kontocomparator());
    }
    
    public void dodaj(){
        if(nowe.getBilansowewynikowe()!=null){
            nowe.setSyntetyczne("syntetyczne");
        } else {
            nowe.setSyntetyczne("analityczne");
            nowe.setBilansowewynikowe(selected.getBilansowewynikowe());
            nowe.setZwyklerozrachszczegolne(selected.getZwyklerozrachszczegolne());
        }
        System.out.println("Dodaje konto");
        nowe.setPodatnik("Testowy");
        //dla syntetycznego informacja jest pusta a dla analitycznego bierzekonto
        if(nowe.getSyntetyczne().equals("syntetyczne")){
            nowe.setMacierzyste("");
        } else {
            nowe.setMacierzyste(selected.getPelnynumer());
            //zaznacza w macierzystym ze sa potomkowie
            selected.setMapotomkow(true);
            kontoDAO.edit(selected);
        }
        if(nowe.getMacierzyste().equals("")){
            nowe.setAnalityka(0);
        } else {
            int i = 1;
            i += StringUtils.countMatches(nowe.getMacierzyste(), "-");
            nowe.setAnalityka(i);
        }
        nowe.setMapotomkow(false);
        obliczpelnynumerkonta();
        if(znajdzduplikat()==0){
        kontoDAO.dodaj(nowe);
        wykazkont.add(nowe);
        nowe = new Konto();
        Collections.sort(wykazkont, new Kontocomparator());
        Msg.msg("i", "Dodaje konto","formX:messages");
        } else {
            Msg.msg("e", "Konto o takim numerze juz istnieje!","formX:messages");
            nowe = new Konto();
        }
    };
     private int znajdzduplikat() {
            if(wykazkont.contains(nowe)){
            return 1;
        } else {
            return 0;
        }
    }
    private void obliczpelnynumerkonta(){
       if(nowe.getAnalityka()==0){
            nowe.setPelnynumer(nowe.getNrkonta());
        } else {
            nowe.setPelnynumer(nowe.getMacierzyste()+"-"+nowe.getNrkonta());
        }
    }
    public void usun(){
        if(selected!=null){
            kontoDAO.destroy(selected);
            wykazkont.remove(selected);
            Msg.msg("i", "Usuwam konto","formX:messages");
        } else {
            Msg.msg("e", "Nie wybrano konta","formX:messages");
        }
    }
    
     public List<Konto> complete(String query) {  
        List<Konto> results = new ArrayList<>();
        List<Konto> lista = new ArrayList<>();
        lista.addAll(kontoDAO.findAll());
        try{
            String q = query.substring(0,1);
            int i = Integer.parseInt(q);
        for(Konto p : lista) {  
             if(p.getPelnynumer().startsWith(query)) {
                 results.add(p);
             }
        }} catch (Exception e){
          for(Konto p : lista) {  
             if(p.getNazwapelna().toLowerCase().contains(query.toLowerCase())) {
                 results.add(p);
             }
        }   
        }
        return results;  
    }
     
    public void rozwinkonto(){
         if(selected!=null){
            selected.setRozwin(true);
            kontoDAO.edit(selected);
            przebuduj(selected);
            Msg.msg("i", "Rozwijam konto","formX:messages");
        } else {
            Msg.msg("e", "Nie wybrano konta","formX:messages");
        }
    }
    public void zwinkonto(){
         if(selected!=null){
            selected.setRozwin(false);
            kontoDAO.edit(selected);
            przebudujX(selected);
            Msg.msg("i", "Zwijam konto","formX:messages");
        } else {
            Msg.msg("e", "Nie wybrano konta","formX:messages");
        }
    }
    
    public List<Konto> getWykazkont() {
        return wykazkont;
    }
    
    public static List<Konto> getWykazkontS() {
        return wykazkontS;
    }

    public void setWykazkont(List<Konto> wykazkont) {
        this.wykazkont = wykazkont;
    }

    public Konto getSelected() {
        return selected;
    }

    public void setSelected(Konto selected) {
        this.selected = selected;
    }

    public Konto getNowe() {
        return nowe;
    }

    public void setNowe(Konto nowe) {
        this.nowe = nowe;
    }

   private String wewy;

    public String getWewy() {
        return wewy;
    }

    public void setWewy(String wewy) {
        this.wewy = wewy;
    }
   
   private String listajs;
   
//   static{
//       listajs = new String[2];
//       listajs[0] = "jeden";
//       listajs[1] = "dwa";
//   }

    public String getListajs() {
        return "jeden,dwa,trzy,cztery,piec,szesc,siedem,osiem,dziewiec,dziesiec";
    }

    public String getOpiskonta() {
        return opiskonta;
    }

    public String getPelnynumerkonta() {
        return pelnynumerkonta;
    }

    
    
   
    
}
