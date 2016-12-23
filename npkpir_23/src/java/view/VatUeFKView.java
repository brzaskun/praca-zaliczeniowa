/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansFK.ParamBean;
import comparator.Dokfkcomparator;
import dao.DeklaracjevatDAO;
import dao.PodatnikDAO;
import daoFK.DokDAOfk;
import daoFK.VatuepodatnikDAO;
import data.Data;
import embeddable.Kwartaly;
import embeddable.Parametr;
import embeddable.VatUe;
import entity.Podatnik;
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
import pdf.PdfVatUE;
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
    private VatuepodatnikDAO vatuepodatnikDAO;
    @Inject
    private DeklaracjevatDAO deklaracjevatDAO;
    private double sumawybranych;
    @Inject
    private PodatnikDAO podatnikDAO;
    private String opisvatuepkpir;

    public VatUeFKView() {
        klienciWDTWNT = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        List<Dokfk> listadokumentow = new ArrayList<>();
        //List<Dokfk> dokvatmc = new ArrayList<>();
        Integer rok = wpisView.getRokWpisu();
        String m = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        try {
            listadokumentow.addAll(dokDAOfk.findDokfkPodatnikRok(wpisView));
            Podatnik pod = podatnikDAO.findPodatnikByNIP(wpisView.getPodatnikObiekt().getNip());
            String vatokres = ParametrView.zwrocParametr(pod.getVatokres(), rok, m);
            String okresvat = vatokres;
            if (pod.getParamVatUE() != null && !pod.getParamVatUE().isEmpty()) {
                okresvat = ParamBean.zwrocParametr(pod.getParamVatUE(), rok, m);
            }
            opisvatuepkpir = wpisView.getPodatnikWpisu()+" Zestawienie dokumentów do deklaracji VAT-UE na koniec "+ rok+"/"+m+" rozliczenie "+okresvat;
            listadokumentow = zmodyfikujlisteMcKw(listadokumentow, okresvat);
        } catch (Exception e) { 
            E.e(e); 
        }
        //jest miesiecznie wiec nie ma co wybierac

        Collections.sort(listadokumentow, new Dokfkcomparator());
        //a teraz podsumuj klientów
        klienciWDTWNT.addAll(kontrahenci(listadokumentow));
        double sumanettovatue = 0.0;
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
                        break;
                    }
            }
        }
        VatUe rzadpodsumowanie = new VatUe("podsum.", null, (double) Math.round(sumanettovatue), 0, null);
        klienciWDTWNT.add(rzadpodsumowanie);
        zachowajwbazie(String.valueOf(rok), wpisView.getMiesiacWpisu(), podatnik);
//        try {
//            pobierzdanezdeklaracji();
//        } catch (Exception e) { E.e(e); 
//        }
    }
    
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
    
    private Set<VatUe> kontrahenci(List<Dokfk> listadokumentow) {
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
        if (Data.czyjestpo("2016-11-30", wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu())) {
            zwrot = p.getRodzajedok().getSkrot().equals("WNT") || p.getRodzajedok().getSkrot().equals("WDT")  || p.getRodzajedok().getSkrot().equals("UPTK100");
        } else {
            zwrot = p.getRodzajedok().getSkrot().equals("WNT") || p.getRodzajedok().getSkrot().equals("WDT")  || p.getRodzajedok().getSkrot().equals("UPTK");
        }
        return zwrot;
    }
    
    private List<Dokfk> zmodyfikujlisteMcKw(List<Dokfk> listadokvat, String vatokres) throws Exception {
        try {
            switch (vatokres) {
                case "blad":
                    Msg.msg("e", "Nie ma ustawionego parametru vat za dany okres. Nie można sporządzić ewidencji VAT.");
                    throw new Exception("Nie ma ustawionego parametru vat za dany okres");
                case "miesięczne": {
                    List<Dokfk> listatymczasowa = new ArrayList<>();
                    for (Dokfk p : listadokvat) {
                        if (p.getVatM().equals(wpisView.getMiesiacWpisu())) {
                            listatymczasowa.add(p);
                        }
                    }
                    return listatymczasowa;
                }
                default: {
                    List<Dokfk> listatymczasowa = new ArrayList<>();
                    Integer kwartal = Integer.parseInt(Kwartaly.getMapanrkw().get(Integer.parseInt(wpisView.getMiesiacWpisu())));
                    List<String> miesiacewkwartale = Kwartaly.getMapakwnr().get(kwartal);
                    for (Dokfk p : listadokvat) {
                        if (p.getVatM().equals(miesiacewkwartale.get(0)) || p.getVatM().equals(miesiacewkwartale.get(1)) || p.getVatM().equals(miesiacewkwartale.get(2))) {
                            listatymczasowa.add(p);
                        }
                    }
                    return listatymczasowa;
                }
            }
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Blada nietypowy plik VatView zmodyfikujliste ");
            return null;
        }
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
    
    public String sprawdzjakiokresvat() {
        Integer rok = wpisView.getRokWpisu();
        Integer mc = Integer.parseInt(wpisView.getMiesiacWpisu());
        List<Parametr> parametry = wpisView.getPodatnikObiekt().getVatokres();
        //odszukaj date w parametrze - kandydat na metode statyczna
        for (Parametr p : parametry) {
            if (p.getRokDo() != null && !p.getRokDo().equals("")) {
                int wynikPo = Data.compare(rok, mc, Integer.parseInt(p.getRokOd()), Integer.parseInt(p.getMcOd()));
                int wynikPrzed = Data.compare(rok, mc, Integer.parseInt(p.getRokDo()), Integer.parseInt(p.getMcDo()));
                if (wynikPo >= 0 && wynikPrzed <= 0) {
                    return p.getParametr();
                }
            } else {
                int wynik = Data.compare(rok, mc, Integer.parseInt(p.getRokOd()), Integer.parseInt(p.getMcOd()));
                if (wynik >= 0) {
                    return p.getParametr();
                }
            }
        }
        Msg.msg("e", "Problem z funkcja sprawdzajaca okres rozliczeniowy VAT VatView-269");
        return "blad";
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

//    public void pobierzdanezdeklaracji() throws Exception {
//        danezdeklaracji = new ArrayList<>();
//        Integer kwartal = Integer.parseInt(Kwartaly.getMapanrkw().get(Integer.parseInt(wpisView.getMiesiacWpisu())));
//        List<String> miesiacewkwartale = Kwartaly.getMapakwnr().get(kwartal);
//        List<Deklaracjevat> deklaracjevat = deklaracjevatDAO.findDeklaracjeWyslane(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
//        if (deklaracjevat != null){
//            //czesc niezbedna do usuwania pierwotnych deklaracji w przypadku istnienia korekt
//            List<Deklaracjevat> deklaracjevat2 = deklaracjevatDAO.findDeklaracjeWyslane(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
//            Iterator it = deklaracjevat.iterator();
//            while (it.hasNext()) {
//                Deklaracjevat p  = (Deklaracjevat) it.next();
//                    for(Deklaracjevat r : deklaracjevat2) {
//                        if (p.getMiesiac().equals(r.getMiesiac())) {
//                            if (p.getNrkolejny()<r.getNrkolejny()) {
//                                it.remove();
//                            }
//                        }
//                    }
//            }
//            int suma = 0;
//            for (Deklaracjevat p : deklaracjevat) {
//                if (miesiacewkwartale.contains(p.getMiesiac())) {
//                    Danezdekalracji dane = new Danezdekalracji();
//                    dane.setNazwa("deklaracja");
//                    dane.setMiesiac(p.getMiesiac());
//                    dane.setNetto(p.getPozycjeszczegolowe().getPoleI33());
//                    suma += dane.getNetto();
//                    danezdeklaracji.add(dane);
//                }
//            }
//            Danezdekalracji dane = new Danezdekalracji();
//            dane.setNazwa("podsumowanie");
//            dane.setMiesiac("wysłanych");
//            dane.setNetto(suma);
//            danezdeklaracji.add(dane);
//        }
//    }

    public void drukujewidencjeUEfk() {
      try {
          if (listawybranych != null && !listawybranych.isEmpty()) {
              PdfVatUE.drukujewidencje(listawybranych, wpisView);
          } else {
              PdfVatUE.drukujewidencje(klienciWDTWNT, wpisView);
          }
      }  catch (Exception e) { E.e(e); 
          
      }
    } 
   
    public void drukujewidencjeUEfkTabela() {
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
