/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Dokcomparator;
import dao.DokDAO;
import daoFK.VatuepodatnikDAO;
import embeddable.Kwartaly;
import embeddable.Mce;
import embeddable.VatUe;
import entity.Dok;
import entity.Podatnik;
import entity.Uz;
import entityfk.Vatuepodatnik;
import entityfk.VatuepodatnikPK;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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

/**
 *
 * @author Osito
 */
@ManagedBean(name = "vatUeView")
@ViewScoped
public class VatUeView implements Serializable {
    //lista gdzie beda podsumowane wartosci
    private static List<VatUe> klienciWDTWNT;
    private static List<VatUe> listawybranych;

    public static List<VatUe> getKlienciWDTWNTS() {
        return klienciWDTWNT;
    }
    //</editor-fold>


    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private Uz uzytkownik;
    @Inject private VatuepodatnikDAO vatuepodatnikDAO;

    public VatUeView() {
        klienciWDTWNT = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        List<Dok> obiektDOKjsfSel = new ArrayList<>();
        List<Dok> dokvatmc = new ArrayList<>();
        Integer rok = wpisView.getRokWpisu();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        Podatnik pod = wpisView.getPodatnikObiekt();
        uzytkownik = wpisView.getWprowadzil();
        try {
            obiektDOKjsfSel.addAll(dokDAO.zwrocBiezacegoKlientaRok(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu().toString()));
            //sortowanie dokumentów
            Collections.sort(obiektDOKjsfSel, new Dokcomparator());
            //
            int numerkolejny = 1;
            for (Dok p : obiektDOKjsfSel) {
                p.setNrWpkpir(numerkolejny++);
            }
        } catch (Exception e) {
        }
        String m = wpisView.getMiesiacWpisu();
        Integer m1 = Integer.parseInt(m);
        String mn = Mce.getMapamcy().get(m1);
        Integer r = wpisView.getRokWpisu();
        try {
            dokvatmc.addAll(zmodyfikujliste(obiektDOKjsfSel, "kwartał"));
        } catch (Exception ex) {
            Logger.getLogger(VatUeView.class.getName()).log(Level.SEVERE, null, ex);
        }
        //a teraz podsumuj klientów
        for (Dok p : dokvatmc) {
            for (VatUe s : klienciWDTWNT) {
                if (p.getKontr().getNip().equals(s.getKontrahent().getNip()) && p.getTypdokumentu().equals(s.getTransakcja())) {
                    s.setNetto(p.getNetto() + s.getNetto());
                    s.setLiczbadok(s.getLiczbadok() + 1);
                    s.getZawiera().add(p);
                    break;
                }
            }
        }
        zachowajwbazie(String.valueOf(rok), m, podatnik);
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
        try {
            vatuepodatnikDAO.edit(vatuepodatnik);
            Msg.msg("i", "Zachowano dane do VAT-EU");
        } catch (Exception e) {
            Msg.msg("e", "Błąd podczas zachowywania danych do VAT-UE");
        }
    }

    private List<Dok> zmodyfikujliste(List<Dok> listadokvat, String vatokres) throws Exception {
        // zeby byl unikalny zestaw klientow
        Set<VatUe> klienty = new HashSet<>();
        switch (vatokres) {
            case "blad":
                throw new Exception("Nie ma ustawionego parametru vat za dany okres");
            case "miesięczne":
            {
                List<Dok> listatymczasowa = new ArrayList<>();
                for (Dok p : listadokvat) {
                    if (p.getVatM().equals(wpisView.getMiesiacWpisu())&&(p.getTypdokumentu().equals("WNT") || p.getTypdokumentu().equals("WDT"))) {
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
            default:
            {
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
    

    //<editor-fold defaultstate="collapsed" desc="comment">
    public WpisView getWpisView() {
        return wpisView;
    }
    
    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<VatUe> getListawybranych() {
        return listawybranych;
    }

    public void setListawybranych(List<VatUe> listawybranych) {
        VatUeView.listawybranych = listawybranych;
    }
    
    
    public List<VatUe> getKlienciWDTWNT() {
        return klienciWDTWNT;
    }
    
    public void setKlienciWDTWNT(List<VatUe> klienciWDTWNT) {
        VatUeView.klienciWDTWNT = klienciWDTWNT;
    }
    
}
