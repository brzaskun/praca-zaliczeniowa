/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.PIT11_29Bean;
import comparator.Kartawynagrodzencomparator;
import comparator.PITPolacomparator;
import dao.AngazFacade;
import dao.DeklaracjaPIT11SchowekFacade;
import dao.KartaWynagrodzenFacade;
import dao.PasekwynagrodzenFacade;
import data.Data;
import embeddable.Mce;
import entity.Angaz;
import entity.DeklaracjaPIT11Schowek;
import entity.FirmaKadry;
import entity.Kartawynagrodzen;
import entity.PITPola;
import entity.Pasekwynagrodzen;
import entity.Pracownik;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.primefaces.PrimeFaces;
import pdf.PdfKartaWynagrodzen;
import pdf.PdfPIT11;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class KartaWynagrodzenView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private KartaWynagrodzenFacade kartaWynagrodzenFacade;
    @Inject
    private DeklaracjaPIT11SchowekFacade deklaracjaSchowekFacade;
    @Inject
    private WpisView wpisView;
    private List<Kartawynagrodzen> kartawynagrodzenlist;
    @Inject
    private PasekwynagrodzenFacade pasekwynagrodzenFacade;
    @Inject
    private DeklaracjaPIT11SchowekFacade deklaracjaPIT11SchowekFacade;
    private List<Kartawynagrodzen> sumypracownicy;
    private List<Kartawynagrodzen> listaselected;
    private PITPola wybranypitpola;
    private List<PITPola> pitpola;
    @Inject
    private AngazFacade angazFacade;
    private List<DeklaracjaPIT11Schowek> listaPIT11;
    
     

    
    public void init() {
        pobierzdane(wpisView.getAngaz());
        pobierzdaneAll();
        listaPIT11 = deklaracjaPIT11SchowekFacade.findByRokFirma(wpisView.getRokWpisu(), wpisView.getFirma());
    }

        
       
    public void pobierzdane(Angaz angaz) {
        if (angaz!=null) {
            kartawynagrodzenlist = pobierzkartywynagrodzen(angaz, wpisView.getRokWpisu());
            aktualizujdane(kartawynagrodzenlist, wpisView.getRokWpisu(), angaz);
            Msg.msg("Pobrano dane wynagrodzeń");
        }
    }
    
     public void pobierzdaneAll() {
        sumypracownicy = new ArrayList<>();
        pitpola  = new ArrayList<>();
        List<Angaz> pracownicy = angazFacade.findByFirma(wpisView.getFirma());
        Kartawynagrodzen duzasuma = new Kartawynagrodzen();
        for (Angaz p: pracownicy) {
            if (p!=null) {
               List<Kartawynagrodzen> kartawynagrodzenlist = pobierzkartywynagrodzen(p, wpisView.getRokWpisu());
               List<Pasekwynagrodzen> paski = pasekwynagrodzenFacade.findByRokWyplAngaz(wpisView.getRokWpisu(), p);
                if (paski!=null && !paski.isEmpty()) {
                    Map<String,Kartawynagrodzen> sumy = new HashMap<>();
                    Kartawynagrodzen suma = sumuj(kartawynagrodzenlist, paski, p.getPracownik().getNazwiskoImie(), sumy, p);
                    suma.setJestPIT11(pobierzpotwierdzenie(p.getPracownik(), wpisView.getRokWpisu()));
                    sumypracownicy.add(suma);
                    pitpola.add(naniesnapola(suma, p.getPracownik()));
                    duzasuma.dodajkarta(suma);
                }
           }
        }
        Msg.msg("Pobrano dane wszystkich wynagrodzeń");
        if (pitpola!=null) {
            Collections.sort(pitpola, new PITPolacomparator());
        }
        sumypracownicy.add(duzasuma);
    }
     private PITPola naniesnapola(Kartawynagrodzen suma, Pracownik pracownik) {
        PITPola pITPola = new PITPola(pracownik, suma.getRok());
        pITPola.setPracownik(pracownik);
        Map<String, Kartawynagrodzen> sumy = suma.getSumy();
        pITPola.dodajprace(sumy.get("sumaUmowaoprace"));
        pITPola.dodajprace26zwolnione(sumy.get("sumaUmowaoprace26zwolnione"));
        pITPola.dodajpracekosztywysokie(sumy.get("sumaUmowaopracekosztypodwyzszone"));
        pITPola.dodajpelnieniefunkcji(sumy.get("sumaUmowapelnieniefunkcji"));
        pITPola.dodajzlecenie(sumy.get("sumaUmowazlecenia"));
        pITPola.dodajzlecenie26zwolnione(sumy.get("sumaUmowazlecenia26zwolnione"));
        return pITPola;
    }
    
    private List<Kartawynagrodzen> pobierzkartywynagrodzen(Angaz selectedangaz, String rok) {
        List<Kartawynagrodzen> kartypobranezbazy = kartaWynagrodzenFacade.findByAngazRok(selectedangaz, rok);
        Collections.sort(kartypobranezbazy, new Kartawynagrodzencomparator());
        if (kartypobranezbazy==null || kartypobranezbazy.isEmpty()) {
            kartypobranezbazy = new ArrayList<>();
            for (String mc : Mce.getMceListS()) {
                Kartawynagrodzen nowa = new Kartawynagrodzen();
                nowa.setAngaz(selectedangaz);
                nowa.setRok(rok);
                nowa.setMc(mc);
                kartypobranezbazy.add(nowa);
            }
        } else {
            kartaWynagrodzenFacade.removeList(kartypobranezbazy);
            kartypobranezbazy = new ArrayList<>();
            for (String mc : Mce.getMceListS()) {
                Kartawynagrodzen nowa = new Kartawynagrodzen();
                nowa.setAngaz(selectedangaz);
                nowa.setRok(rok);
                nowa.setMc(mc);
                kartypobranezbazy.add(nowa);
            }
        }
        return kartypobranezbazy;
    }
    
    private void aktualizujdane(List<Kartawynagrodzen> kartawynagrodzenlist, String rok, Angaz angaz) {
        List<Pasekwynagrodzen> paski = pasekwynagrodzenFacade.findByRokWyplAngaz(rok, angaz);
        if (paski!=null && !paski.isEmpty()) {
            Map<String,Kartawynagrodzen> sumy = new HashMap<>();
            sumuj(kartawynagrodzenlist, paski, wpisView.getPracownik().getNazwiskoImie(), sumy, angaz);
        }
    }

    private Kartawynagrodzen sumuj(List<Kartawynagrodzen> kartawynagrodzenlist, List<Pasekwynagrodzen> paski, String nazwiskoiimie, Map<String,Kartawynagrodzen> sumy, Angaz angaz) {
        Kartawynagrodzen sumaUmowaoprace = new Kartawynagrodzen();
        Kartawynagrodzen sumaUmowaoprace26zwolnione = new Kartawynagrodzen();
        Kartawynagrodzen sumaUmowaopracekosztypodwyzszone = new Kartawynagrodzen();
        Kartawynagrodzen sumaUmowapelnieniefunkcji  = new Kartawynagrodzen();
        Kartawynagrodzen sumaUmowazlecenia  = new Kartawynagrodzen();
        Kartawynagrodzen sumaUmowazlecenia26zwolnione  = new Kartawynagrodzen();
        Kartawynagrodzen suma = new Kartawynagrodzen();
        suma.setAngaz(angaz);
        suma.setMc("razem");
        int lata = 0;
        for (Kartawynagrodzen karta : kartawynagrodzenlist) {
            List<Angaz> angazzpaskow = new ArrayList<>();
            for (Iterator<Pasekwynagrodzen> it = paski.iterator(); it.hasNext();) {
                Pasekwynagrodzen pasek = it.next();
                lata = pasek.getLata();
                if (pasek.getMcwypl().equals(karta.getMc())) {
                    //tu sie dodaje paski do karty wynagrodzen
                    if (!angazzpaskow.contains(pasek.getKalendarzmiesiac().getAngaz())) {
                        angazzpaskow.add(pasek.getKalendarzmiesiac().getAngaz());
                        if (angazzpaskow.size()>1) {
                            karta.setKosztywieleumow(true);
                            suma.setKosztywieleumow(true);
                        }
                    }
                    if (pasek.getProcentkosztow()>100.0) {
                        karta.setKosztypodwyzszone(true);
                        suma.setKosztypodwyzszone(true);
                    }
//                    id,nazwa,typ
//                    1,"umowa o pracę",1
//                    2,"umowa zlecenia i o dzieło",2
//                    3,"pełnienie obowiązków",3
//                    4,zasiłki,4

                    if ((pasek.getRodzajWynagrodzenia()==1||pasek.getRodzajWynagrodzenia()==4||pasek.getRodzajWynagrodzenia()==1006)&&pasek.isDo26lat()==false) {
                        if (pasek.getProcentkosztow()>100.0) {
                            sumaUmowaopracekosztypodwyzszone.dodaj(pasek);
                        } else {
                            sumaUmowaoprace.dodaj(pasek);
                        }
                    } else if ((pasek.getRodzajWynagrodzenia()==1||pasek.getRodzajWynagrodzenia()==1006)&&pasek.isDo26lat()==true) {
                            sumaUmowaoprace26zwolnione.dodaj(pasek);
                    } else if (pasek.getRodzajWynagrodzenia()==2&&pasek.isDo26lat()==false) {
                        sumaUmowazlecenia.dodaj(pasek);
                    } else if (pasek.getRodzajWynagrodzenia()==2&&pasek.isDo26lat()==true) {
                        sumaUmowazlecenia26zwolnione.dodaj(pasek);
                    } else if (pasek.getRodzajWynagrodzenia()==15) {
                        sumaUmowapelnieniefunkcji.dodaj(pasek);
                    }
                    karta.dodaj(pasek);
                    suma.dodaj(pasek);
                    it.remove();
                }
            }
        }
        suma.setNazwiskoiimie(nazwiskoiimie);
        String ostatnidzien =  Data.ostatniDzien(wpisView.getRokWpisu(), "12");
        suma.setWieklata(Data.obliczwieklata(angaz.getPracownik().getDataurodzenia(),ostatnidzien));
        sumy.put("sumaUmowaoprace", sumaUmowaoprace);
        sumy.put("sumaUmowaoprace26zwolnione", sumaUmowaoprace26zwolnione);
        sumy.put("sumaUmowaopracekosztypodwyzszone", sumaUmowaopracekosztypodwyzszone);
        sumy.put("sumaUmowapelnieniefunkcji", sumaUmowapelnieniefunkcji);
        sumy.put("sumaUmowazlecenia", sumaUmowazlecenia);
        sumy.put("sumaUmowazlecenia26zwolnione", sumaUmowazlecenia26zwolnione);
        suma.setSumy(sumy);
        kartaWynagrodzenFacade.createEditList(kartawynagrodzenlist);
        kartawynagrodzenlist.add(suma);
        return suma;
    }
    
    public void drukuj() {
        if (kartawynagrodzenlist!=null && kartawynagrodzenlist.size()>0) {
            PdfKartaWynagrodzen.drukuj(kartawynagrodzenlist, wpisView.getAngaz(), wpisView.getRokWpisu());
            Msg.msg("Wydrukowano kartę wynagrodzeń");
        } else {
            Msg.msg("e","Błąd drukowania. Brak karty wynagrodzeń");
        }
    }


    public void pit11() {
        if (kartawynagrodzenlist!=null && kartawynagrodzenlist.size()>0) {
            Kartawynagrodzen kartawynagrodzen = kartawynagrodzenlist.get(12);
            FirmaKadry firma = kartawynagrodzen.getAngaz().getFirma();
            Pracownik pracownik = kartawynagrodzen.getAngaz().getPracownik();
            Object[] sciezka = beanstesty.PIT11_29Bean.generujXML(kartawynagrodzen, firma, pracownik, (byte)1, pracownik.getKodurzeduskarbowego(), kartawynagrodzen.getRok(), kartawynagrodzen.getSumy());
            pl.gov.crd.wzor._2022._11._09._11890.Deklaracja deklaracja = (pl.gov.crd.wzor._2022._11._09._11890.Deklaracja)sciezka[2];
            if (deklaracja!=null) {
                String polecenie = "wydrukXML(\""+(String)sciezka[0]+"\")";
                PrimeFaces.current().executeScript(polecenie);
                String nazwapliku = PdfPIT11.drukuj29(deklaracja, wpisView.getUzer().getImieNazwiskoTelefon(), null);
                DeklaracjaPIT11Schowek schowek = new DeklaracjaPIT11Schowek(deklaracja, firma, pracownik, wpisView.getRokWpisu(),"PIT11");
                deklaracjaSchowekFacade.create(schowek);
                polecenie = "wydrukPDF(\""+nazwapliku+"\")";
                PrimeFaces.current().executeScript(polecenie);
                Msg.msg("Wydrukowano PIT-11");
            }
        } else {
            Msg.msg("e","Błąd generowania PIT-11. Brak karty wynagrodzeń");
        }
    }
    
//    public void pit11All_27() {
//        List<Kartawynagrodzen> lista = f.l.l(sumypracownicy, listaselected, null);
//        if (lista!=null && lista.size()>0) {
//            for (Kartawynagrodzen karta : lista) {
//                if (karta.isJestPIT11()==false && karta.getAngaz()!=null) {
//                    Kartawynagrodzen kartawynagrodzen = karta;
//                    FirmaKadry firma = kartawynagrodzen.getAngaz().getFirma();
//                    Pracownik pracownik = kartawynagrodzen.getAngaz().getPracownik();
//                    Object[] sciezka = beanstesty.PIT11_27Bean.generujXML(kartawynagrodzen, firma, pracownik, (byte)1, pracownik.getKodurzeduskarbowego(), kartawynagrodzen.getRok(), kartawynagrodzen.getSumy());
//                    pl.gov.crd.wzor._2021._03._04._10477.Deklaracja deklaracja = (pl.gov.crd.wzor._2021._03._04._10477.Deklaracja)sciezka[2];
//                    if (deklaracja!=null) {
//                        String polecenie = "wydrukXML(\""+(String)sciezka[0]+"\")";
//                        PrimeFaces.current().executeScript(polecenie);
//                        String nazwapliku = PdfPIT11.drukuj(deklaracja, wpisView.getUzer().getImieNazwiskoTelefon());
//                        DeklaracjaPIT11Schowek schowek = new DeklaracjaPIT11Schowek(deklaracja, firma, pracownik, wpisView.getRokWpisu(),"PIT11");
//                        karta.setJestPIT11(true);
//                        deklaracjaSchowekFacade.create(schowek);
//                        listaPIT11.add(schowek);
//                        polecenie = "wydrukPDF(\""+nazwapliku+"\")";
//                        PrimeFaces.current().executeScript(polecenie);
//                        Msg.msg("Wydrukowano PIT-11");
//                    }
//                }
//            }
//            init();
//        } else {
//            Msg.msg("e","Błąd generowania PIT-11. Brak karty wynagrodzeń");
//        }
//    }
    
    public void pit11All() {
        if (listaselected!=null && listaselected.size()>0) {
            for (Kartawynagrodzen karta : listaselected) {
                if (karta.isJestPIT11()==false && karta.getAngaz()!=null) {
                    Kartawynagrodzen kartawynagrodzen = karta;
                    FirmaKadry firma = kartawynagrodzen.getAngaz().getFirma();
                    Pracownik pracownik = kartawynagrodzen.getAngaz().getPracownik();
                    Object[] sciezka = beanstesty.PIT11_29Bean.generujXML(kartawynagrodzen, firma, pracownik, (byte)1, pracownik.getKodurzeduskarbowego(), kartawynagrodzen.getRok(), kartawynagrodzen.getSumy());
                    pl.gov.crd.wzor._2022._11._09._11890.Deklaracja deklaracja = (pl.gov.crd.wzor._2022._11._09._11890.Deklaracja)sciezka[2];
                    if (deklaracja!=null) {
                        String polecenie = "wydrukXML(\""+(String)sciezka[0]+"\")";
                        PrimeFaces.current().executeScript(polecenie);
                        String nazwapliku = PdfPIT11.drukuj29(deklaracja, wpisView.getUzer().getImieNazwiskoTelefon(), null);
                        DeklaracjaPIT11Schowek schowek = new DeklaracjaPIT11Schowek(deklaracja, firma, pracownik, wpisView.getRokWpisu(),"PIT11");
                        karta.setJestPIT11(true);
                        deklaracjaSchowekFacade.create(schowek);
                        listaPIT11.add(schowek);
                        polecenie = "wydrukPDF(\""+nazwapliku+"\")";
                        PrimeFaces.current().executeScript(polecenie);
                        Msg.msg("Wydrukowano PIT-11");
                    }
                }
            }
            init();
        } else {
            Msg.msg("e","Błąd generowania PIT-11. Nie wybrano pracowników");
        }
    }
    
   private boolean pobierzpotwierdzenie(Pracownik pracownik, String rokWpisu) {
       boolean zwrot = false;
        if (pracownik!=null) {
           List<DeklaracjaPIT11Schowek> findByRokPracownik = deklaracjaPIT11SchowekFacade.findByRokPracownik(rokWpisu, pracownik);
           if (findByRokPracownik!=null&&findByRokPracownik.size()>0) {
               zwrot = true;
           }
        }
        return zwrot;
    }
    
   

    public void pokazpdf(DeklaracjaPIT11Schowek deklaracjaPIT11Schowek) {
        if (deklaracjaPIT11Schowek != null) {
            ObjectInputStream is = null;
            try {
                byte[] deklaracja = deklaracjaPIT11Schowek.getDeklaracja(); //        ByteArrayInputStream in = new ByteArrayInputStream(this.deklaracja);
                ByteArrayInputStream in = new ByteArrayInputStream(deklaracja);
                is = new ObjectInputStream(in);
                pl.gov.crd.wzor._2022._11._09._11890.Deklaracja dekl = (pl.gov.crd.wzor._2022._11._09._11890.Deklaracja) is.readObject();
                String nazwapliku = PdfPIT11.drukuj29(dekl, wpisView.getUzer().getImieNazwiskoTelefon(), deklaracjaPIT11Schowek);
                String polecenie = "wydrukPDF(\"" + nazwapliku + "\")";
                PrimeFaces.current().executeScript(polecenie);
                Msg.msg("Pobrano deklaracje");
            } catch (Exception ex) {
            } finally {
                try {
                    is.close();
                } catch (IOException ex) {
                }
            }
        } else {
            Msg.msg("e", "B\u0142\u0105d, nie pobrano dekalracji");
        }
    }

  
    public void usunPIT11(DeklaracjaPIT11Schowek deklaracjaPIT11Schowek) {
        if (deklaracjaPIT11Schowek != null) {
            deklaracjaPIT11SchowekFacade.remove(deklaracjaPIT11Schowek);
            listaPIT11.remove(deklaracjaPIT11Schowek);
            init();
            Msg.msg("Usuni\u0119to dekalracje");
        } else {
            Msg.msg("e", "B\u0142\u0105d, nie usuni\u0119to dekalracji");
        }
    }

    public void pokazPIT11(DeklaracjaPIT11Schowek deklaracjaPIT11Schowek) {
        if (deklaracjaPIT11Schowek != null) {
            ObjectInputStream is = null;
            try {
                byte[] deklaracja = deklaracjaPIT11Schowek.getDeklaracja(); //        ByteArrayInputStream in = new ByteArrayInputStream(this.deklaracja);
                ByteArrayInputStream in = new ByteArrayInputStream(deklaracja);
                is = new ObjectInputStream(in);
                pl.gov.crd.wzor._2022._11._09._11890.Deklaracja dekl = (pl.gov.crd.wzor._2022._11._09._11890.Deklaracja) is.readObject();
                String sciezka = PIT11_29Bean.marszajuldoplikuxml(dekl);
                String polecenie = "wydrukXML(\"" + sciezka + "\")";
                PrimeFaces.current().executeScript(polecenie);
                Msg.msg("Pobrano deklaracje");
            } catch (Exception ex) {
            } finally {
                try {
                    is.close();
                } catch (IOException ex) {
                }
            }
        } else {
            Msg.msg("e", "B\u0142\u0105d, nie pobrano dekalracji");
        }
    }
    
    

    
 public List<Kartawynagrodzen> getKartawynagrodzenlist() {
        return kartawynagrodzenlist;
    }

    public void setKartawynagrodzenlist(List<Kartawynagrodzen> kartawynagrodzenlist) {
        this.kartawynagrodzenlist = kartawynagrodzenlist;
    }


    public List<Kartawynagrodzen> getSumypracownicy() {
        return sumypracownicy;
    }

    public void setSumypracownicy(List<Kartawynagrodzen> sumypracownicy) {
        this.sumypracownicy = sumypracownicy;
    }

    public List<Kartawynagrodzen> getListaselected() {
        return listaselected;
    }

    public void setListaselected(List<Kartawynagrodzen> listaselected) {
        this.listaselected = listaselected;
    }

   

    public PITPola getWybranypitpola() {
        return wybranypitpola;
    }

    public void setWybranypitpola(PITPola wybranypitpola) {
        this.wybranypitpola = wybranypitpola;
    }

    public List<PITPola> getPitpola() {
        return pitpola;
    }

    public void setPitpola(List<PITPola> pitpola) {
        this.pitpola = pitpola;
    }

    public List<DeklaracjaPIT11Schowek> getListaPIT11() {
        return listaPIT11;
    }

    public void setListaPIT11(List<DeklaracjaPIT11Schowek> listaPIT11) {
        this.listaPIT11 = listaPIT11;
    }
   

   

   
   

   
    
    
}
