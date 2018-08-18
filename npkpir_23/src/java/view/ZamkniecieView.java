/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Okresrozliczeniowycomparator;
import dao.DokDAO;
import dao.ZamknietemiesiaceDAO;
import embeddable.Mce;
import embeddable.Okresrozliczeniowy;
import embeddable.Roki;
import entity.Dok;
import entity.Podatnik;
import entity.Zamknietemiesiace;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class ZamkniecieView implements Serializable {

    private String podatnik;
    @Inject private Zamknietemiesiace zamknietemiesiace;
    @Inject private Roki roki;
    @Inject private Mce mce;
    private List<Okresrozliczeniowy> mapaokresow;
    private List<Okresrozliczeniowy> mapaokresowPobrane;
    private List<Okresrozliczeniowy> mapaokresowPobraneZapas;
    @Inject private ZamknietemiesiaceDAO zDAO ;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    @Inject DokDAO dokDAO;
    private boolean moznaksiegowac;
     
    

    public ZamkniecieView() {
        mapaokresow = Collections.synchronizedList(new ArrayList<>());
        mapaokresowPobrane = Collections.synchronizedList(new ArrayList<>());
        mapaokresowPobraneZapas = Collections.synchronizedList(new ArrayList<>());
    }

    @PostConstruct
    private void init() {
        //Wrzucalistemiesiecy
        List<Integer> lata = roki.getRokiList();
        Collections.sort(lata);
        List<String> miesiace =  mce.getMceList();
        for (Integer tmp : lata){
            for (String tmpx : miesiace){
                Okresrozliczeniowy okres = new Okresrozliczeniowy();
                okres.setRok(tmp.toString());
                okres.setMiesiac(tmpx);
                mapaokresow.add(okres);
            }
        }
         //wyszukiewani aktualnych okresow u podatnika, jak nie ma kopiowanie okresow
        try {
            //pobieram cały rekord dlatego potem moge go zachowac
            zamknietemiesiace = zDAO.findZM(wpisView.getPodatnikWpisu());
            mapaokresowPobrane.addAll(zamknietemiesiace.getZamkniete());
            //dodamy brakujacy rok bo z jakiegos powodu znika?
            for (Okresrozliczeniowy p : mapaokresow) {
                if (!mapaokresowPobrane.contains(p)) {
                    Okresrozliczeniowy t = new Okresrozliczeniowy();
                    t.setRok(p.getRok());
                    t.setMiesiac(p.getMiesiac());
                    t.setZamkniety(false);
                    t.setEdytuj(false);
                    mapaokresowPobrane.add(t);
                }
            }
            zamknietemiesiace.setZamkniete(mapaokresowPobrane);
            zDAO.edit(zamknietemiesiace);
             Iterator it = mapaokresowPobrane.iterator();
            while (it.hasNext()) {
              Okresrozliczeniowy p = (Okresrozliczeniowy) it.next();
                if((p.getRok().equals(wpisView.getRokWpisu().toString()))&&p.getMiesiac().equals(wpisView.getMiesiacWpisu())){
                    moznaksiegowac = p.isZamkniety();
                }
                if (!p.getRok().equals(wpisView.getRokWpisuSt())) { 
                    it.remove();
                }//dwd
            }
        //przenoszenie danych od podatnika do tabeli tymczasowej
        } catch (Exception ex){
            //tworzenie archiwum dla podatnika
            zamknietemiesiace.setPodatnik(wpisView.getPodatnikWpisu());
            zamknietemiesiace.setZamkniete(new ArrayList<Okresrozliczeniowy>());
            zamknietemiesiace.setZamkniete(mapaokresow);
            mapaokresowPobrane.addAll(zamknietemiesiace.getZamkniete());
            //utworzenie edycja jest niepotrzebna bo my tworzymy zupelnie nowa
            zDAO.edit(zamknietemiesiace);
            Msg.msg("Stworzono nowe archiwum zamykania miesięcy.");
        }
        Collections.sort(mapaokresowPobrane, new Okresrozliczeniowycomparator());
        Collections.sort(mapaokresowPobraneZapas, new Okresrozliczeniowycomparator());
    }

    public void zapisokresy(){
        Msg.msg("i", "Trwa wprowadzanie zmian", "form:messages");
        zaksiegujDokumenty();
        zamknietemiesiace.setZamkniete(mapaokresowPobrane);
        for(Okresrozliczeniowy p : mapaokresowPobrane){
             p.setEdytuj(false);
        }
        zDAO.edit(zamknietemiesiace);
        Iterator it = mapaokresowPobrane.iterator();
            while (it.hasNext()) {
              Okresrozliczeniowy p = (Okresrozliczeniowy) it.next();
                if((p.getRok().equals(wpisView.getRokWpisu().toString()))&&p.getMiesiac().equals(wpisView.getMiesiacWpisu())){
                    moznaksiegowac = p.isZamkniety();
                }
            }
        RequestContext.getCurrentInstance().update("form:dataTable");
    }
    
     public void zapisokresyedit(AjaxBehaviorEvent e){
        Object em = e.getSource();
        zamknietemiesiace.setZamkniete(mapaokresowPobrane);
        int i = 0;
        for(Okresrozliczeniowy p : mapaokresowPobrane){
             if(p.isEdytuj()){
                 i++;
             };
        }
        if(i==2){
            Msg.msg("e", "Nie wolno edytować dwóch miesięcy na raz", "form:messages");
            for(Okresrozliczeniowy p : mapaokresowPobrane){
                p.setEdytuj(false);
            }
            //RequestContext.getCurrentInstance().update(e.getSource().toString());
        } else {
            zDAO.edit(zamknietemiesiace);
            Msg.msg("i", "Edycja miesiąca", "form:messages");
        }
    }
    
    private void zaksiegujDokumenty(){
        List<Okresrozliczeniowy> roznice = Collections.synchronizedList(new ArrayList<>());
        mapaokresowPobraneZapas.addAll(zDAO.findZM(wpisView.getPodatnikWpisu()).getZamkniete());
        int rozmiar = 0;
        while(rozmiar<mapaokresowPobrane.size()){
            if(mapaokresowPobrane.get(rozmiar).isZamkniety() != mapaokresowPobraneZapas.get(rozmiar).isZamkniety()){
                roznice.add(mapaokresowPobrane.get(rozmiar));
            }
            rozmiar++;
        }
        for(Okresrozliczeniowy pozycjaNowa : roznice){
           if(pozycjaNowa.isZamkniety()==true){
               ksieguj(wpisView.getPodatnikObiekt(),pozycjaNowa.getRok(), pozycjaNowa.getMiesiac(), "ksiegi");
           } else {
               ksieguj(wpisView.getPodatnikObiekt(),pozycjaNowa.getRok(), pozycjaNowa.getMiesiac(), "bufor");
           }
        }
    }
    
    private void ksieguj(Podatnik podatnik, String rok, String mc, String stan){
        List<Dok> lista = dokDAO.zwrocBiezacegoKlientaRokMC(podatnik, rok,mc);
        for(Dok dokument : lista){
            dokument.setStatus(stan);
        }
        dokDAO.editList(lista);
    }
    
     public boolean sprawdzMiesiacRok() {
        String rok = wpisView.getRokWpisuSt();
        String mc = wpisView.getMiesiacWpisu();
        for (Okresrozliczeniowy p : mapaokresowPobrane) {
            if(p.getRok().equals(rok)&&(p.getMiesiac().equals(mc))) {
                return p.isZamkniety();
            }
        }
        Msg.msg("i", "Sprawdzono miesiac");
        return false;
    }

      
     //<editor-fold defaultstate="collapsed" desc="comment">
     public List<Okresrozliczeniowy> getMapaokresowPobrane() {
         return mapaokresowPobrane;
     }
     
     public void setMapaokresowPobrane(List<Okresrozliczeniowy> mapaokresowPobrane) {
         this.mapaokresowPobrane = mapaokresowPobrane;
     }
     
     public WpisView getWpisView() {
         return wpisView;
     }
     
     public void setWpisView(WpisView wpisView) {
         this.wpisView = wpisView;
     }
     
     public boolean isMoznaksiegowac() {
         return moznaksiegowac;
     }
     
     public void setMoznaksiegowac(boolean moznaksiegowac) {
         this.moznaksiegowac = moznaksiegowac;
     }
     //</editor-fold>
    
 }
