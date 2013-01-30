/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.ZamknietemiesiaceDAO;
import embeddable.Mce;
import embeddable.Okresrozliczeniowy;
import embeddable.Roki;
import entity.Zamknietemiesiace;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class ZamkniecieView implements Serializable {

    private static List<Integer> lata;
    private static List<String> miesiace;
    private String podatnik;
    @Inject private Zamknietemiesiace zamknietemiesiace;
    @Inject private Roki roki;
    @Inject private Mce mce;
    private ArrayList<Okresrozliczeniowy> mapaokresow;
    private ArrayList<Okresrozliczeniowy> mapaokresowPobrane;
    @Inject private ZamknietemiesiaceDAO zDAO ;
    

    public ZamkniecieView() {
        lata = new ArrayList<>();
        miesiace = new ArrayList<>();
        mapaokresow = new ArrayList<>();
        mapaokresowPobrane = new ArrayList<>();
        
    }

    @PostConstruct
    private void init() {
        //Wrzucalistemiesiecy
        lata.addAll(roki.getRokiList());
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
            zamknietemiesiace = zDAO.findZM("TECHBUD");
            mapaokresowPobrane.addAll(zamknietemiesiace.getZamkniete());
        //przenoszenie danych od podatnika do tabeli tymczasowej
        } catch (Exception ex){
            //tworzenie archiwum dla podatnika
            zamknietemiesiace.setPodatnik("TECHBUD");
            zamknietemiesiace.setZamkniete(new ArrayList<Okresrozliczeniowy>());
            zamknietemiesiace.setZamkniete(mapaokresow);
            mapaokresowPobrane.addAll(zamknietemiesiace.getZamkniete());
            //utworzenie edycja jest nipotrzebna bo my tylko pobieramy
            zDAO.dodaj(zamknietemiesiace);
        }
    }

    public void zapisokresy(){
        zDAO.edit(zamknietemiesiace);
    }
   
    public ArrayList<Okresrozliczeniowy> getMapaokresowPobrane() {
        return mapaokresowPobrane;
    }

    public void setMapaokresowPobrane(ArrayList<Okresrozliczeniowy> mapaokresowPobrane) {
        this.mapaokresowPobrane = mapaokresowPobrane;
    }

  
 }
