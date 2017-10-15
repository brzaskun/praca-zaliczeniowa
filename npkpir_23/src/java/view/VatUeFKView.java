/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DeklaracjevatDAO;
import dao.DokDAO;
import dao.PodatnikDAO;
import daoFK.DokDAOfk;
import daoFK.VatuepodatnikDAO;
import daoFK.ViesDAO;
import data.Data;
import embeddable.Kwartaly;
import embeddable.Parametr;
import embeddable.VatUe;
import entity.Deklaracjevat;
import entity.Dok;
import entity.DokSuper;
import entityfk.Dokfk;
import entityfk.Vatuepodatnik;
import entityfk.VatuepodatnikPK;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import pdf.PdfVatUE;
import pdffk.PdfVIES;
import vies.VIESCheckBean;
import vies.Vies;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean(name = "vatUeFKView")
@ViewScoped
public class VatUeFKView implements Serializable {

    //lista gdzie beda podsumowane wartosci
    private List<VatUe> klienciWDTWNT;
    private List<VatUe> listawybranych;
    private List<Danezdekalracji> danezdeklaracji;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private VatuepodatnikDAO vatuepodatnikDAO;
    @Inject
    private DeklaracjevatDAO deklaracjevatDAO;
    private double sumawybranych;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private ViesDAO viesDAO;
    private String opisvatuepkpir;

    public VatUeFKView() {
        klienciWDTWNT = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        List<DokSuper> listadokumentowUE = new ArrayList<>();
        //List<Dokfk> dokvatmc = new ArrayList<>();
        Integer rok = wpisView.getRokWpisu();
        String m = wpisView.getMiesiacWpisu();
        try {
            String vatUEokres = ParametrView.zwrocParametr(wpisView.getPodatnikObiekt().getParamVatUE(), rok, m);
            if (vatUEokres.equals("miesięczne")) {
                if (wpisView.isKsiegirachunkowe()) {
                    listadokumentowUE = dokDAOfk.findDokfkPodatnikRokMc(wpisView);
                } else {
                    listadokumentowUE = dokDAO.zwrocBiezacegoKlientaRokMC(wpisView);
                }
                opisvatuepkpir = wpisView.getPodatnikWpisu()+" Miesięczne zestawienie dokumentów do deklaracji VAT-UE na koniec "+ rok+"/"+m;
            } else {
                if (wpisView.isKsiegirachunkowe()) {
                    listadokumentowUE = dokDAOfk.findDokfkPodatnikRokMc(wpisView);
                } else {
                    listadokumentowUE = dokDAO.zwrocBiezacegoKlientaRokKW(wpisView);
                }
                opisvatuepkpir = wpisView.getPodatnikWpisu()+" Kwartalne zestawienie dokumentów do deklaracji VAT-UE na koniec "+ rok+"/"+m;
            }            
        } catch (Exception e) { 
            E.e(e); 
        }
        //jest miesiecznie wiec nie ma co wybierac
        if (listadokumentowUE != null) {
            //a teraz podsumuj klientów
            klienciWDTWNT.addAll(kontrahenciUE(listadokumentowUE));
            double sumanettovatue = 0.0;
            double sumanettovatuewaluta = 0.0;
            for (DokSuper p : listadokumentowUE) {
                for (VatUe s : klienciWDTWNT) {
                    if (p.getKontr()!= null && p.getKontr().getNip().equals(s.getKontrahent().getNip()) && p.getTypdokumentu().equals(s.getTransakcja())) {
                            double[] t = p.pobierzwartosci();
                            double netto = t[0];
                            double nettowaluta = t[1];
                            s.setNetto(netto + s.getNetto());
                            s.setNettowaluta(nettowaluta + s.getNettowaluta());
                            s.setLiczbadok(s.getLiczbadok() + 1);
                            if (p.getClass().getSimpleName().equals("Dokfk")) {
                                s.getZawierafk().add((Dokfk)p);
                            } else {
                                s.getZawiera().add((Dok) p);
                            }
                            String nazwawal = p.getWalutadokumentu() != null ? p.getWalutadokumentu().getSymbolwaluty() : "";
                            s.setNazwawaluty(nazwawal);
                            sumanettovatue += netto;
                            sumanettovatuewaluta += nettowaluta;
                            break;
                        }
                }
            }
            if (klienciWDTWNT.size() > 0) {
                VatUe rzadpodsumowanie = new VatUe("podsum.", null, Z.z(sumanettovatue), Z.z(sumanettovatuewaluta));
                klienciWDTWNT.add(rzadpodsumowanie);
                zachowajwbazie(String.valueOf(rok), wpisView.getMiesiacWpisu(), wpisView.getPodatnikWpisu());
            }
        }
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
        } catch (Exception e) { E.e(e); };
        try {
            vatuepodatnikDAO.dodaj(vatuepodatnik);
            Msg.msg("i", "Zachowano dane do VAT-EU");
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Błąd podczas zachowywania danych do VAT-UE");
        }
    }
    
    private Set<VatUe> kontrahenciUE(List<DokSuper> listadokumentow) {
        Set<VatUe> klienty = new HashSet<>();
        for (DokSuper p : listadokumentow) {
            if (warunekkontrahenci(p)) {
                //wyszukujemy dokumenty WNT i WDT dodajemu do sumy
                VatUe veu = new VatUe(p.getTypdokumentu(), p.getKontr(), 0.0, 0);
                veu.setZawierafk(new ArrayList<>());
                klienty.add(veu);
            }
        }
        return klienty;
    }
    
    private boolean warunekkontrahenci(DokSuper p) {
        System.out.println("dok "+p.toString());
        boolean zwrot = false;
        if (Data.czyjestpo("2016-11-30", wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu())) {
            zwrot = p.getTypdokumentu().equals("WNT") || p.getTypdokumentu().equals("WDT")  || p.getTypdokumentu().equals("UPTK100");
        } else {
            zwrot = p.getTypdokumentu().equals("WNT") || p.getTypdokumentu().equals("WDT")  || p.getTypdokumentu().equals("UPTK");
        }
        return zwrot;
    }
    
   
//    private boolean dobrymiesiac(Dokfk p) {
//        boolean dobry = false;
//        if (p.getEwidencjaVAT() != null && !p.getEwidencjaVAT().isEmpty()) {
//            String mc = null;
//            for (EVatwpisFK r : p.getEwidencjaVAT()) {
//                mc = r.getMcEw();
//            }
//            if (mc.equals(wpisView.getMiesiacWpisu())) {
//                dobry = true;
//                System.out.println("dobry mc "+p.getDokfkSN());
//            }
//        }
//        return dobry;
//    }
    public void podsumuj() {
        sumawybranych = 0.0;
        for (VatUe p : listawybranych) {
            sumawybranych += p.getNetto();
        }
    }
    
    private String sprawdzjakiokresvat() {
        Integer rok = wpisView.getRokWpisu();
        Integer mc = Integer.parseInt(wpisView.getMiesiacWpisu());
        List<Parametr> parametry = wpisView.getPodatnikObiekt().getVatokres();
        return ParametrView.zwrocParametr(parametry, rok, mc);
    }
    
   

//    private List<Dok> zmodyfikujliste(List<Dok> listadokvat, String vatokres) throws Exception {
//        // zeby byl unikalny zestaw klientow
//        Set<VatUe> klienty = new HashSet<>();
//        switch (vatokres) {
//            case "blad":
//                throw new Exception("Nie ma ustawionego parametru vat za dany okres");
//            case "miesięczne": {
//                List<Dok> listatymczasowa = new ArrayList<>();
//                for (Dok p : listadokvat) {
//                    if (p.getVatM().equals(wpisView.getMiesiacWpisu()) && (p.getTypdokumentu().equals("WNT") || p.getTypdokumentu().equals("WDT"))) {
//                        //pobieramy dokumenty z danego okresu do sumowania
//                        listatymczasowa.add(p);
//                        //wyszukujemy dokumenty WNT i WDT dodajemu do sumy
//                        if (p.getTypdokumentu().equals("WNT") || p.getTypdokumentu().equals("WDT")) {
//                            klienty.add(new VatUe(p.getTypdokumentu(), p.getKontr(), 0.0, 0, new ArrayList<Dok>()));
//                        }
//                    }
//
//                }
//                //potem do tych wyciagnietych klientow bedziemy przyporzadkowywac faktury i je sumowac
//                klienciWDTWNT.addAll(klienty);
//                return listatymczasowa;
//            }
//            default: {
//                List<Dok> listatymczasowa = new ArrayList<>();
//                Integer kwartal = Integer.parseInt(Kwartaly.getMapanrkw().get(Integer.parseInt(wpisView.getMiesiacWpisu())));
//                List<String> miesiacewkwartale = Kwartaly.getMapakwnr().get(kwartal);
//                for (Dok p : listadokvat) {
//                    if (p.getVatM().equals(miesiacewkwartale.get(0)) || p.getVatM().equals(miesiacewkwartale.get(1)) || p.getVatM().equals(miesiacewkwartale.get(2)) && (p.getTypdokumentu().equals("WNT") || p.getTypdokumentu().equals("WDT"))) {
//                        listatymczasowa.add(p);
//                        //wyszukujemy dokumenty WNT i WDT dodajemu do sumy
//                        if (p.getTypdokumentu().equals("WNT") || p.getTypdokumentu().equals("WDT")) {
//                            klienty.add(new VatUe(p.getTypdokumentu(), p.getKontr(), 0.0, 0, new ArrayList<Dok>()));
//                        }
//                    }
//                }
//                klienciWDTWNT.addAll(klienty);
//                return listatymczasowa;
//            }
//        }
//    }

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

    public void drukujewidencjeUEfk() {
      try {
          if (listawybranych != null && !listawybranych.isEmpty()) {
              PdfVatUE.drukujewidencje(listawybranych, wpisView,"VAT-UE");
          } else {
              PdfVatUE.drukujewidencje(klienciWDTWNT, wpisView,"VAT-UE");
          }
      }  catch (Exception e) { E.e(e); 
          
      }
    } 
   
    public void drukujewidencjeUEfkTabela() {
      try {
          if (listawybranych != null && !listawybranych.isEmpty()) {
              PdfVatUE.drukujewidencjeTabela(listawybranych, wpisView,"VAT-UE");
          } else {
              PdfVatUE.drukujewidencjeTabela(klienciWDTWNT, wpisView,"VAT-UE");
          }
      }  catch (Exception e) { E.e(e); 
          
      }
    } 
    
    public void sprawdzVIES() {
       try {
        VIESCheckBean.sprawdz(klienciWDTWNT, viesDAO, wpisView.getPodatnikObiekt(), wpisView.getWprowadzil());
        Msg.msg("Sprawdzono VIES");
       } catch (Exception e) {
           E.e(e);
       }
    }
   
    public void drukujVIES() {
        try {
            if (klienciWDTWNT == null) {
                Msg.msg("e", "Lista VIES pusta - nie ma czego drukować");
            } else {
                List<Vies> lista = new ArrayList<>();
                for (VatUe p : klienciWDTWNT) {
                    if (p.getVies() != null && p.getVies().getPodatnik() != null) {
                        lista.add(p.getVies());
                    }
                }
                PdfVIES.drukujVIES(lista, wpisView);
            }
        }  catch (Exception e) { 
            E.e(e); 
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

    public String getOpisvatuepkpir() {
        return opisvatuepkpir;
    }

    public void setOpisvatuepkpir(String opisvatuepkpir) {
        this.opisvatuepkpir = opisvatuepkpir;
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

    public double getSumawybranych() {
        return sumawybranych;
    }

    public void setSumawybranych(double sumawybranych) {
        this.sumawybranych = sumawybranych;
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
