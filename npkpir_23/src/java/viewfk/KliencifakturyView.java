/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import comparator.Kliencicomparator;
import dao.DokDAO;
import dao.DokDAOfk;
import entity.Dok;
import entity.Klienci;
import entity.KwotaKolumna1;
import entityfk.Dokfk;
import entityfk.Wiersz;
import interceptor.ConstructorInterceptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
 import javax.interceptor.Interceptors;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named @Interceptors(ConstructorInterceptor.class)
@ViewScoped
public class KliencifakturyView implements Serializable {

    private static final long serialVersionUID = 1L;
    private String dataod;
    private String datado;
    private boolean niemieckienaglowki;
    private boolean podwykonawcyniemcy;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private DokDAO dokDAO;
    private List<Dokfk> listadokumentowfk;
    private List<Dok> listadokumentow;
    private List<Klienci> listawszystkichklientow;
    @Inject
    private WpisView wpisView;

    public KliencifakturyView() {
          dataod = data.Data.aktualnyRok()+"-01-01";
    }

  public void pobierzFK() { 
    // E.m(this);
    if (wpisView != null && wpisView.isKsiegirachunkowe()) { // Ensure wpisView is not null and ksiegirachunkowe is true
        try {
            listadokumentowfk = null; // Initialize to avoid unintentional usage of stale data
            if (wpisView.getPodatnikObiekt() != null && dataod != null && datado != null && dokDAOfk != null) {
                // Ensure dokDAOfk and input data are not null
                listadokumentowfk = dokDAOfk.findDokfkByDateAndType(
                    wpisView.getPodatnikObiekt(), 
                    dataod, 
                    datado, 
                    Arrays.asList(1, 3)
                );
                if (listadokumentowfk != null && podwykonawcyniemcy) {
                    // Ensure listadokumentowfk is not null before filtering
                    listadokumentowfk = listadokumentowfk.stream()
                        .filter(item -> item != null && "RACH".equals(item.getSeriadokfk())) // Avoid null pointer on item or getSeriadokfk
                        .collect(Collectors.toList());
                }
                if (listadokumentowfk != null) {
                    sumujdokumentyWaluta(listadokumentowfk); // Ensure method is not called with a null list
                    dokDAOfk.editList(listadokumentowfk); // Ensure dokDAOfk is available
                }
                uzupelnijDaneKlientowFk(); // Call only if relevant conditions are met
                System.out.println("");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log exception for debugging
        }
    }
}
public void pobierz() { 
    // E.m(this);
    if (wpisView != null && wpisView.isKsiegirachunkowe()==false) { // Ensure wpisView is not null and ksiegirachunkowe is true
        try {
            listadokumentow = null; // Initialize to avoid unintentional usage of stale data
            if (wpisView.getPodatnikObiekt() != null && dataod != null && datado != null && dokDAO != null) {
                // Ensure dokDAOfk and input data are not null
                listadokumentow = dokDAO.findDokfkByDateAndType(
                    wpisView.getPodatnikObiekt(), 
                    dataod, 
                    datado, 
                    Arrays.asList(0, 1, 3)
                );
                if (listadokumentow != null && podwykonawcyniemcy) {
                    // Ensure listadokumentowfk is not null before filtering
                    listadokumentow = listadokumentow.stream()
                        .filter(item -> item != null && "RACHDE".equals(item.getRodzajedok().getSkrotNazwyDok())) // Avoid null pointer on item or getSeriadokfk
                        .collect(Collectors.toList());
                }
                if (listadokumentow != null) {
                    sumujdokumentyWalutaPkpir(listadokumentow); // Ensure method is not called with a null list
                     dokDAO.editList(listadokumentow);// Ensure dokDAOfk is available
                }
                uzupelnijDaneKlientow(); // Call only if relevant conditions are met
                System.out.println("");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log exception for debugging
        }
    }
}

   
   private void uzupelnijDaneKlientow() {
    if (listadokumentow != null && !listadokumentow.isEmpty()) {
        // Mapa do przechowywania danych klientów
        Map<Klienci, KlientDane> mapaKlientow = new HashMap<>();

        for (Dok dokument : listadokumentow) {
            Klienci klient = dokument.getKontr();
            KlientDane dane = mapaKlientow.getOrDefault(klient, new KlientDane());
            
            // Aktualizacja dat i sumy wartości dokumentów
            String dataOperacji = dokument.getDataSprz();
            dane.setDataOd(dane.getDataOd() == null || dane.getDataOd().compareTo(dataOperacji) > 0 ? dataOperacji : dane.getDataOd());
            dane.setDataDo(dane.getDataDo() == null || dane.getDataDo().compareTo(dataOperacji) < 0 ? dataOperacji : dane.getDataDo());
            dane.setSumaDokumentow(dane.getSumaDokumentow() + dokument.getWartoscdokumentuwaluta());
            
            mapaKlientow.put(klient, dane);
        }

        // Uzupełnienie danych w obiektach klasy Klienci
        for (Map.Entry<Klienci, KlientDane> entry : mapaKlientow.entrySet()) {
            Klienci klient = entry.getKey();
            KlientDane dane = entry.getValue();

            klient.setDataod(dane.getDataOd());
            klient.setDatado(dane.getDataDo());
            klient.setSumadokumentow(dane.getSumaDokumentow());
        }

        // Tworzenie listy unikalnych klientów
        listawszystkichklientow = new ArrayList<>(mapaKlientow.keySet());
        Collections.sort(listawszystkichklientow, new Kliencicomparator());
    }
}
   
    private void uzupelnijDaneKlientowFk() {
    if (listadokumentowfk != null && !listadokumentowfk.isEmpty()) {
        // Mapa do przechowywania danych klientów
        Map<Klienci, KlientDane> mapaKlientow = new HashMap<>();

        for (Dokfk dokument : listadokumentowfk) {
            Klienci klient = dokument.getKontr();
            KlientDane dane = mapaKlientow.getOrDefault(klient, new KlientDane());
            
            // Aktualizacja dat i sumy wartości dokumentów
            String dataOperacji = dokument.getDataoperacji();
            dane.setDataOd(dane.getDataOd() == null || dane.getDataOd().compareTo(dataOperacji) > 0 ? dataOperacji : dane.getDataOd());
            dane.setDataDo(dane.getDataDo() == null || dane.getDataDo().compareTo(dataOperacji) < 0 ? dataOperacji : dane.getDataDo());
            dane.setSumaDokumentow(dane.getSumaDokumentow() + dokument.getWartoscdokumentuwaluta());
            
            mapaKlientow.put(klient, dane);
        }

        // Uzupełnienie danych w obiektach klasy Klienci
        for (Map.Entry<Klienci, KlientDane> entry : mapaKlientow.entrySet()) {
            Klienci klient = entry.getKey();
            KlientDane dane = entry.getValue();

            klient.setDataod(dane.getDataOd());
            klient.setDatado(dane.getDataDo());
            klient.setSumadokumentow(dane.getSumaDokumentow());
        }

        // Tworzenie listy unikalnych klientów
        listawszystkichklientow = new ArrayList<>(mapaKlientow.keySet());
        Collections.sort(listawszystkichklientow, new Kliencicomparator());
    }
}
   
   private void sumujdokumentyWalutaPkpir(List<Dok> dokumenty) {
        for (Dok d : dokumenty) {
            double sumawiersza = 0.0;
            for (KwotaKolumna1 p : d.getListakwot1()) {
                sumawiersza = sumawiersza + Z.z(p.getNettowaluta()+p.getVatwaluta());
            }
            d.setWartoscdokumentuwaluta(Z.z(sumawiersza));
        }
    }
   
   private void sumujdokumentyWaluta(List<Dokfk> dokumenty) {
        for (Dokfk d : dokumenty) {
            d.setWartoscdokumentuwaluta(0.0);
            for (Wiersz p : d.getListawierszy()) {
                double sumawiersza = dodajKwotyWierszaDoSumyDokumentu(p);
                d.setWartoscdokumentuwaluta(Z.z(d.getWartoscdokumentuwaluta()+sumawiersza));
            }
        }
    }
    
     public double dodajKwotyWierszaDoSumyDokumentu(Wiersz biezacywiersz) {
        double suma = 0.0;
        try {//robimy to bo sa nowy wiersz jest tez podsumowywany, ale moze byc przeciez pusty wiec wyrzuca blad
            int typwiersza = biezacywiersz.getTypWiersza();
            double wn = biezacywiersz.getStronaWn() != null ? biezacywiersz.getStronaWn().getKwota() : 0.0;
            double ma = biezacywiersz.getStronaMa() != null ? biezacywiersz.getStronaMa().getKwota() : 0.0;
            
            if (typwiersza == 1) {
                suma += wn;
            } else if (typwiersza == 2) {
                suma += ma;
            } else {
                double kwotaWn = wn;
                double kwotaMa = ma;
                if (Math.abs(kwotaMa) > Math.abs(kwotaWn)) {
                    suma += wn;
                } else {
                    suma += ma;
                }
            }
        } catch (Exception e) {
            System.out.println("");
        }
        return Z.z(suma);
    }

// Klasa pomocnicza do przechowywania danych klientów
private static class KlientDane {
    private String dataOd;
    private String dataDo;
    private double sumaDokumentow;

    public String getDataOd() {
        return dataOd;
    }

    public void setDataOd(String dataOd) {
        this.dataOd = dataOd;
    }

    public String getDataDo() {
        return dataDo;
    }

    public void setDataDo(String dataDo) {
        this.dataDo = dataDo;
    }

    public double getSumaDokumentow() {
        return sumaDokumentow;
    }

    public void setSumaDokumentow(double sumaDokumentow) {
        this.sumaDokumentow = sumaDokumentow;
    }
}


    public List<Klienci> getListawszystkichklientow() {
        return listawszystkichklientow;
    }

    public void setListawszystkichklientow(List<Klienci> listawszystkichklientow) {
        this.listawszystkichklientow = listawszystkichklientow;
    }

    public String getDataod() {
        return dataod;
    }

    public void setDataod(String dataod) {
        this.dataod = dataod;
    }

    public String getDatado() {
        return datado;
    }

    public void setDatado(String datado) {
        this.datado = datado;
    }

    public boolean isNiemieckienaglowki() {
        return niemieckienaglowki;
    }

    public void setNiemieckienaglowki(boolean niemieckienaglowki) {
        this.niemieckienaglowki = niemieckienaglowki;
    }

    public boolean isPodwykonawcyniemcy() {
        return podwykonawcyniemcy;
    }

    public void setPodwykonawcyniemcy(boolean podwykonawcyniemcy) {
        this.podwykonawcyniemcy = podwykonawcyniemcy;
    }

    
    
}
