/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DokDAO;
import dao.ZamknietemiesiaceDAO;
import embeddable.Mce;
import embeddable.Okresrozliczeniowy;
import embeddable.Roki;
import entity.Dok;
import entity.Zamknietemiesiace;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

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
    private ArrayList<Okresrozliczeniowy> mapaokresow;
    private ArrayList<Okresrozliczeniowy> mapaokresowPobrane;
    private ArrayList<Okresrozliczeniowy> mapaokresowZmiany;
    @Inject private ZamknietemiesiaceDAO zDAO ;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    @Inject DokDAO dokDAO;
     
    

    public ZamkniecieView() {
        mapaokresow = new ArrayList<>();
        mapaokresowPobrane = new ArrayList<>();
        mapaokresowZmiany = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        //Wrzucalistemiesiecy
        List<Integer> lata = new ArrayList<>();
        lata.addAll(roki.getRokiList());
        List<String> miesiace = new ArrayList<>();
        miesiace.addAll(mce.getMceList());
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
        //przenoszenie danych od podatnika do tabeli tymczasowej
        } catch (Exception ex){
            //tworzenie archiwum dla podatnika
            zamknietemiesiace.setPodatnik(wpisView.getPodatnikWpisu());
            zamknietemiesiace.setZamkniete(new ArrayList<Okresrozliczeniowy>());
            zamknietemiesiace.setZamkniete(mapaokresow);
            mapaokresowPobrane.addAll(zamknietemiesiace.getZamkniete());
            //utworzenie edycja jest nipotrzebna bo my tylko pobieramy
            zDAO.dodaj(zamknietemiesiace);
        }
    }

    public void zapisokresy(){
        zaksiegujDokumenty();
        zamknietemiesiace.setZamkniete(mapaokresowPobrane);
        zDAO.edit(zamknietemiesiace);
    }
    
    private void zaksiegujDokumenty(){
        List<Okresrozliczeniowy> roznice = new ArrayList<>();
        for(Okresrozliczeniowy pozycjaNowa : mapaokresowPobrane){
           if(pozycjaNowa.isZamkniety()==true){
               ksieguj(wpisView.getPodatnikWpisu(),pozycjaNowa.getRok(), pozycjaNowa.getMiesiac(), "ksiegi");
           } else {
               ksieguj(wpisView.getPodatnikWpisu(),pozycjaNowa.getRok(), pozycjaNowa.getMiesiac(), "bufor");
           }
        }
    }
    
    private void ksieguj(String podatnik, String rok, String mc, String stan){
        List<Dok> lista = dokDAO.zwrocBiezacegoKlientaRokMC(podatnik, rok,mc);
        for(Dok dokument : lista){
            dokument.setStatus(stan);
            dokDAO.edit(dokument);
        }
    }
   
    public ArrayList<Okresrozliczeniowy> getMapaokresowPobrane() {
        return mapaokresowPobrane;
    }

    public void setMapaokresowPobrane(ArrayList<Okresrozliczeniowy> mapaokresowPobrane) {
        this.mapaokresowPobrane = mapaokresowPobrane;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    
 }
