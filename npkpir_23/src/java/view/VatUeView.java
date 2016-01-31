/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Dokcomparator;
import dao.DeklaracjevatDAO;
import dao.DokDAO;
import daoFK.VatuepodatnikDAO;
import embeddable.Kwartaly;
import embeddable.VatUe;
import entity.Deklaracjevat;
import entity.Dok;
import entityfk.Vatuepodatnik;
import entityfk.VatuepodatnikPK;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import pdf.PdfVatUE;

/**
 *
 * @author Osito
 */
@ManagedBean(name = "vatUeView")
@ViewScoped
public class VatUeView implements Serializable {

    //lista gdzie beda podsumowane wartosci
    private List<VatUe> klienciWDTWNT;
    private List<VatUe> listawybranych;
    private List<Danezdekalracji> danezdeklaracji;
    private boolean niemoznadrukowac;
  
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private VatuepodatnikDAO vatuepodatnikDAO;
    @Inject
    private DeklaracjevatDAO deklaracjevatDAO;

    public VatUeView() {
        klienciWDTWNT = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        List<Dok> obiektDOKjsfSel = new ArrayList<>();
        List<Dok> dokvatmc = new ArrayList<>();
        Integer rok = wpisView.getRokWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        try {
            obiektDOKjsfSel.addAll(dokDAO.zwrocBiezacegoKlientaRok(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu().toString()));
            //sortowanie dokumentów
            Collections.sort(obiektDOKjsfSel, new Dokcomparator());
            //
            int numerkolejny = 1;
            for (Dok p : obiektDOKjsfSel) {
                p.setNrWpkpir(numerkolejny++);
            }
        } catch (Exception e) { E.e(e); 
        }
        String m = wpisView.getMiesiacWpisu();
        Integer m1 = Integer.parseInt(m);
        try {
            dokvatmc.addAll(zmodyfikujliste(obiektDOKjsfSel, "kwartał"));
        } catch (Exception ex) {
            Logger.getLogger(VatUeView.class.getName()).log(Level.SEVERE, null, ex);
        }
        //a teraz podsumuj klientów
        double sumanettovatue = 0.0;
        for (Dok p : dokvatmc) {
            for (VatUe s : klienciWDTWNT) {
                if (p.getKontr().getNip().equals(s.getKontrahent().getNip()) && p.getTypdokumentu().equals(s.getTransakcja())) {
                    s.setNetto(p.getNetto() + s.getNetto());
                    s.setLiczbadok(s.getLiczbadok() + 1);
                    s.getZawiera().add(p);
                    sumanettovatue += (double) Math.round(p.getNetto());
                    break;
                }
            }
        }
        for (VatUe r : klienciWDTWNT) {
            if (r.getKontrahent().getKrajkod() == null) {
                niemoznadrukowac = true;
            }
        }
        VatUe rzadpodsumowanie = new VatUe("podsum.", null, sumanettovatue, 0, null);
        klienciWDTWNT.add(rzadpodsumowanie);
        zachowajwbazie(String.valueOf(rok), m, podatnik);
        try {
            pobierzdanezdeklaracji();
        } catch (Exception e) { E.e(e); 
        }
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
        } catch (Exception e) { 
            E.e(e); 
        };
        try {
            vatuepodatnikDAO.dodaj(vatuepodatnik);
            Msg.msg("i", "Zachowano dane do VAT-EU");
        } catch (Exception e) { 
            E.e(e); 
            Msg.msg("e", "Błąd podczas zachowywania danych do VAT-UE");
        }
    }

    private List<Dok> zmodyfikujliste(List<Dok> listadokvat, String vatokres) throws Exception {
        // zeby byl unikalny zestaw klientow
        Set<VatUe> klienty = new HashSet<>();
        switch (vatokres) {
            case "blad":
                throw new Exception("Nie ma ustawionego parametru vat za dany okres");
            case "miesięczne": {
                List<Dok> listatymczasowa = new ArrayList<>();
                for (Dok p : listadokvat) {
                    if (p.getVatM().equals(wpisView.getMiesiacWpisu()) && (p.getTypdokumentu().equals("WNT") || p.getTypdokumentu().equals("WDT"))) {
                        //pobieramy dokumenty z danego okresu do sumowania
                        listatymczasowa.add(p);
                        //wyszukujemy dokumenty WNT i WDT dodajemu do sumy
                        if (p.getTypdokumentu().equals("WNT") || p.getTypdokumentu().equals("WDT")) {
                            klienty.add(new VatUe(p.getTypdokumentu(), p.getKontr(), 0.0, 0, new ArrayList<Dok>()));
                        }
                    }

                }
                //potem do tych wyciagnietych klientow bedziemy przyporzadkowywac faktury i je sumowac
                klienciWDTWNT.addAll(klienty);
                return listatymczasowa;
            }
            default: {
                List<Dok> listatymczasowa = new ArrayList<>();
                Integer kwartal = Integer.parseInt(Kwartaly.getMapanrkw().get(Integer.parseInt(wpisView.getMiesiacWpisu())));
                List<String> miesiacewkwartale = Kwartaly.getMapakwnr().get(kwartal);
                for (Dok p : listadokvat) {
                    if (p.getVatM().equals(miesiacewkwartale.get(0)) || p.getVatM().equals(miesiacewkwartale.get(1)) || p.getVatM().equals(miesiacewkwartale.get(2)) && (p.getTypdokumentu().equals("WNT") || p.getTypdokumentu().equals("WDT"))) {
                        listatymczasowa.add(p);
                        //wyszukujemy dokumenty WNT i WDT dodajemu do sumy
                        if (p.getTypdokumentu().equals("WNT") || p.getTypdokumentu().equals("WDT")) {
                            klienty.add(new VatUe(p.getTypdokumentu(), p.getKontr(), 0.0, 0, new ArrayList<Dok>()));
                        }
                    }
                }
                klienciWDTWNT.addAll(klienty);
                return listatymczasowa;
            }
        }
    }

    public void pobierzdanezdeklaracji() throws Exception {
        danezdeklaracji = new ArrayList<>();
        Integer kwartal = Integer.parseInt(Kwartaly.getMapanrkw().get(Integer.parseInt(wpisView.getMiesiacWpisu())));
        List<String> miesiacewkwartale = Kwartaly.getMapakwnr().get(kwartal);
        List<Deklaracjevat> deklaracjevat = deklaracjevatDAO.findDeklaracjeWyslane(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        if (deklaracjevat != null){
            //czesc niezbedna do usuwania pierwotnych deklaracji w przypadku istnienia korekt
            List<Deklaracjevat> deklaracjevat2 = deklaracjevatDAO.findDeklaracjeWyslane(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
            Iterator it = deklaracjevat.iterator();
            while (it.hasNext()) {
                Deklaracjevat p  = (Deklaracjevat) it.next();
                    for(Deklaracjevat r : deklaracjevat2) {
                        if (p.getMiesiac().equals(r.getMiesiac())) {
                            if (p.getNrkolejny()<r.getNrkolejny()) {
                                it.remove();
                            }
                        }
                    }
            }
            int suma = 0;
            for (Deklaracjevat p : deklaracjevat) {
                if (miesiacewkwartale.contains(p.getMiesiac())) {
                    Danezdekalracji dane = new Danezdekalracji();
                    dane.setNazwa("deklaracja");
                    dane.setMiesiac(p.getMiesiac());
                    dane.setNetto(p.getPozycjeszczegolowe().getPoleI33());
                    suma += dane.getNetto();
                    danezdeklaracji.add(dane);
                }
            }
            Danezdekalracji dane = new Danezdekalracji();
            dane.setNazwa("podsumowanie");
            dane.setMiesiac("wysłanych");
            dane.setNetto(suma);
            danezdeklaracji.add(dane);
        }
    }

    public void drukujewidencjeUE() {
      try {
          PdfVatUE.drukujewidencje(klienciWDTWNT, wpisView);
      }  catch (Exception e) { E.e(e); 
          
      }
    } 
   
     public void drukujewidencjeUETabela() {
      try {
          if (listawybranych != null && !listawybranych.isEmpty()) {
              PdfVatUE.drukujewidencjeTabela(listawybranych, wpisView);
          } else {
              PdfVatUE.drukujewidencjeTabela(klienciWDTWNT, wpisView);
          }
      }  catch (Exception e) { E.e(e); 
          
      }
    } 

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
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

    public List<Danezdekalracji> getDanezdeklaracji() {
        return danezdeklaracji;
    }

    public void setDanezdeklaracji(List<Danezdekalracji> danezdeklaracji) {
        this.danezdeklaracji = danezdeklaracji;
    }

    public boolean isNiemoznadrukowac() {
        return niemoznadrukowac;
    }

    public void setNiemoznadrukowac(boolean niemoznadrukowac) {
        this.niemoznadrukowac = niemoznadrukowac;
    }

 

    public static class Danezdekalracji {
        private String  nazwa;
        private String miesiac;
        private int netto;
        
        public Danezdekalracji() {
        }

//<editor-fold defaultstate="collapsed" desc="comment">
        
        public String getNazwa() {
            return nazwa;
        }

        public void setNazwa(String nazwa) {
            this.nazwa = nazwa;
        }

        public String getMiesiac() {
            return miesiac;
        }
        
        public void setMiesiac(String miesiac) {
            this.miesiac = miesiac;
        }
        
        public int getNetto() {
            return netto;
        }
        
        public void setNetto(int netto) {
            this.netto = netto;
        }
//</editor-fold>
        
        
    }

}
