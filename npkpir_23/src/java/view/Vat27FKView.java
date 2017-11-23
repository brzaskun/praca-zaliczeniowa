/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Dokfkcomparator;
import dao.Deklaracjavat27DAO;
import dao.PodatnikDAO;
import daoFK.DokDAOfk;
import daoFK.VatuepodatnikDAO;
import embeddable.VatUe;
import entity.Deklaracjavat27;
import entityfk.Dokfk;
import entityfk.EVatwpisFK;
import entityfk.Vatuepodatnik;
import entityfk.VatuepodatnikPK;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import pdf.PdfVAT27dekl;
import pdf.PdfVatUE;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class Vat27FKView implements Serializable {

    //lista gdzie beda podsumowane wartosci
    private List<VatUe> klienciWDTWNT;
    private List<VatUe> listawybranych;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private VatuepodatnikDAO vatuepodatnikDAO;
    private double sumawybranych;
    private String opisvatuepkpir;
    private boolean deklaracja0korekta1;
    private List<Deklaracjavat27> deklaracjevat27;
    @Inject
    private Deklaracjavat27DAO deklaracjavat27DAO;
    @Inject
    private Deklaracjavat27 dekl27selected;
    @Inject
    private PodatnikDAO podatnikDAO;


    public Vat27FKView() {
        klienciWDTWNT = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        List<Dokfk> listadokumentow = new ArrayList<>();
        //List<Dokfk> dokvatmc = new ArrayList<>();
        Integer rok = wpisView.getRokWpisu();
        String  mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        try {
            listadokumentow = dokDAOfk.findDokfkPodatnikRokMc(wpisView);
        } catch (Exception e) { 
            E.e(e); 
        }
        opisvatuepkpir = wpisView.getPodatnikWpisu()+" Zestawienie dokumentów do deklaracji VAT-27 na koniec "+ rok+"/"+mc+" rozliczenie miesięczne";
        //jest miesiecznie wiec nie ma co wybierac
        if (listadokumentow != null) {
            Collections.sort(listadokumentow, new Dokfkcomparator());
            //a teraz podsumuj klientów
            klienciWDTWNT.addAll(kontrahenciUE(listadokumentow));
            double sumanettovatue = 0.0;
            double sumanettovatuewaluta = 0.0;
            for (Dokfk p : listadokumentow) {
                for (VatUe s : klienciWDTWNT) {
                    if (p.getKontr().getNip().equals(s.getKontrahent().getNip()) && p.getRodzajedok().getSkrot().equals(s.getTransakcja())) {
                            double[] t = pobierzwartosci(p.getEwidencjaVAT());
                            double netto = t[0];
                            double nettowaluta = t[1];
                            s.setNetto(netto + s.getNetto());
                            s.setNettowaluta(nettowaluta + s.getNettowaluta());
                            s.setLiczbadok(s.getLiczbadok() + 1);
                            s.getZawierafk().add(p);
                            String nazwawal = p.getWalutadokumentu() != null ? p.getWalutadokumentu().getSymbolwaluty() : "";
                            s.setNazwawaluty(nazwawal);
                            sumanettovatue += netto;
                            sumanettovatuewaluta += nettowaluta;
                            break;
                        }
                }
            }
             VatUe rzadpodsumowanie = new VatUe("podsum.", null, Z.z(sumanettovatue), Z.z(sumanettovatuewaluta));
            klienciWDTWNT.add(rzadpodsumowanie);
            try {
                pobierzdeklaracje27();
                Deklaracjavat27 d = deklaracjavat27DAO.findbyPodatnikRokMc(wpisView);
                if (d != null) {
                    deklaracja0korekta1 = true;
                } else {
                    deklaracja0korekta1 = false;
                }
            } catch (Exception e) {
                E.e(e);
            }
            //zachowajwbazie(String.valueOf(rok), wpisView.getMiesiacWpisu(), podatnik);
        }
    }
        
    public void pobierzdeklaracje27()  {
       deklaracjevat27 = deklaracjavat27DAO.findbyPodatnikRok(wpisView);
       if (deklaracjevat27 == null) {
           deklaracjevat27 = new ArrayList<>();
       }
    }
       
//        try {
//            pobierzdanezdeklaracji();
//        } catch (Exception e) { E.e(e); 
//        }
    
    private double[] pobierzwartosci(List<EVatwpisFK> lista) {
        double netto = 0.0;
        double nettowaluta = 0.0;
        for (EVatwpisFK p : lista) {
            netto += p.getNetto();
            nettowaluta += p.getNettowwalucie();
        }
        return new double[]{Z.z(netto),Z.z(nettowaluta)};
    }

    private void zachowajwbazie(String rok, String symbolokresu, String klient) {
        Vatuepodatnik vatuepodatnik = new Vatuepodatnik();
        VatuepodatnikPK vatuepodatnikPK = new VatuepodatnikPK();
        vatuepodatnikPK.setRok(rok);
        vatuepodatnikPK.setSymbolokresu(symbolokresu);
        vatuepodatnikPK.setKlient(klient);
        vatuepodatnik.setVatuepodatnikPK(vatuepodatnikPK);
        vatuepodatnik.setKlienciwdtwnt(klienciWDTWNT);
        vatuepodatnik.setMc0kw1(Boolean.TRUE);
        vatuepodatnik.setRozliczone(Boolean.FALSE);
        //bo czasami nie edytowalo nie wiem dlaczego
        try {
            vatuepodatnikDAO.destroy(vatuepodatnik);
        } catch (Exception e) { E.e(e); };
        try {
            vatuepodatnikDAO.dodaj(vatuepodatnik);
            Msg.msg("i", "Zachowano dane do VAT-EU");
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Błąd podczas zachowywania danych do VAT-UE");
        }
    }
    
    private Set<VatUe> kontrahenciUE(List<Dokfk> listadokumentow) {
        Set<VatUe> klienty = new HashSet<>();
        for (Dokfk p : listadokumentow) {
            if (warunekkontrahenci(p)) {
                //wyszukujemy dokumenty WNT i WDT dodajemu do sumy
                VatUe veu = new VatUe(p.getRodzajedok().getSkrot(), p.getKontr(), 0.0, 0);
                veu.setZawierafk(new ArrayList<>());
                klienty.add(veu);
            }
        }
        return klienty;
    }
    
    private boolean warunekkontrahenci(Dokfk p) {
        boolean zwrot = false;
        zwrot = p.getRodzajedok().getRodzajtransakcji().equals("odwrotne obciążenie sprzedawca");
        return zwrot;
    }
    
   
    
    public void podsumuj() {
        sumawybranych = 0.0;
        for (VatUe p : listawybranych) {
            sumawybranych += p.getNetto();
        }
    }
    

    public void drukujewidencjeUEfk() {
      try {
          if (listawybranych != null && !listawybranych.isEmpty()) {
              PdfVatUE.drukujewidencje(listawybranych, wpisView,"VAT-27");
          } else {
              PdfVatUE.drukujewidencje(klienciWDTWNT, wpisView,"VAT-27");
          }
      }  catch (Exception e) { E.e(e); 
          
      }
    } 
   
    public void drukujewidencjeUEfkTabela() {
      try {
          if (listawybranych != null && !listawybranych.isEmpty()) {
              PdfVatUE.drukujewidencjeTabela(listawybranych, wpisView,"VAT-27");
          } else {
              PdfVatUE.drukujewidencjeTabela(klienciWDTWNT, wpisView,"VAT-27");
          }
      }  catch (Exception e) { E.e(e); 
          
      }
    } 
    
    public void usundekl(Deklaracjavat27 d) {
        try {
            deklaracjavat27DAO.destroy(d);
            deklaracjevat27.remove(d);
            try {
                pobierzdeklaracje27();
                Deklaracjavat27 d2 = deklaracjavat27DAO.findbyPodatnikRokMc(wpisView);
                if (d2 != null) {
                    deklaracja0korekta1 = true;
                } else {
                    deklaracja0korekta1 = false;
                }
            } catch (Exception e) {
                E.e(e);
            }
            Msg.dP();
        } catch (Exception e) {
            Msg.dPe();
        }
    }
    
    public void drukuj(Deklaracjavat27 d) {
        try {
            if (d == null) {
                Msg.msg("e", "Nie wybrano deklaracji");
            } else {
                PdfVAT27dekl.drukujVAT(podatnikDAO, d, wpisView);
                Msg.msg("Wydrukowano deklaracje");
            }
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd, nie wydrukowano ewidencji");
        }
    }
   
//<editor-fold defaultstate="collapsed" desc="comment">
    
    public WpisView getWpisView() {
        return wpisView;
    }
    
    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public String getOpisvatuepkpir() {
        return opisvatuepkpir;
    }

    public void setOpisvatuepkpir(String opisvatuepkpir) {
        this.opisvatuepkpir = opisvatuepkpir;
    }
    
    public List<VatUe> getKlienciWDTWNT() {
        return klienciWDTWNT;
    }
    
    public void setKlienciWDTWNT(List<VatUe> klienciWDTWNT) {
        this.klienciWDTWNT = klienciWDTWNT;
    }
    
    public List<VatUe> getListawybranych() {
        return listawybranych;
    }
    
    public void setListawybranych(List<VatUe> listawybranych) {
        this.listawybranych = listawybranych;
    }

    public boolean isDeklaracja0korekta1() {
        return deklaracja0korekta1;
    }

    public void setDeklaracja0korekta1(boolean deklaracja0korekta1) {
        this.deklaracja0korekta1 = deklaracja0korekta1;
    }

    public List<Deklaracjavat27> getDeklaracjevat27() {
        return deklaracjevat27;
    }

    public void setDeklaracjevat27(List<Deklaracjavat27> deklaracjevat27) {
        this.deklaracjevat27 = deklaracjevat27;
    }

    public Deklaracjavat27 getDekl27selected() {
        return dekl27selected;
    }

    public void setDekl27selected(Deklaracjavat27 dekl27selected) {
        this.dekl27selected = dekl27selected;
    }
    
    
    public double getSumawybranych() {
        return sumawybranych;
    }
    
    public void setSumawybranych(double sumawybranych) {
        this.sumawybranych = sumawybranych;
    }
//</editor-fold>
    


}
