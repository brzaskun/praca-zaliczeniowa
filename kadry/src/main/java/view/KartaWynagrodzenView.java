/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.PIT11_29Bean;
import beanstesty.Pasekpomocnik;
import beanstesty.PasekwynagrodzenBean;
import comparator.DeklaracjaPIT11Schowekcomparator;
import comparator.Kartawynagrodzencomparator;
import comparator.PITPolacomparator;
import comparator.Pasekwynagrodzencomparator;
import dao.AngazFacade;
import dao.DeklaracjaPIT11SchowekFacade;
import dao.KartaWynagrodzenFacade;
import dao.PasekwynagrodzenFacade;
import dao.SMTPSettingsFacade;
import data.Data;
import embeddable.Mce;
import entity.Angaz;
import entity.DeklaracjaPIT11Schowek;
import entity.FirmaKadry;
import entity.Kartawynagrodzen;
import entity.PITPola;
import entity.Pasekwynagrodzen;
import entity.Pracownik;
import entity.SMTPSettings;
import error.E;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import msg.Msg;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
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
    @Inject
    private SMTPSettingsFacade sMTPSettingsFacade;
    private List<Kartawynagrodzen> sumypracownicy;
    private List<Kartawynagrodzen> listaselected;
    private PITPola wybranypitpola;
    private List<PITPola> pitpola;
    @Inject
    private AngazFacade angazFacade;
    private List<DeklaracjaPIT11Schowek> listaPIT11;
    private List<DeklaracjaPIT11Schowek> listaPIT11sorted;
    private List<Pasekwynagrodzen> listapaski;
    private Pracownik selected;
    private boolean brakkoduurzedu;
    
     

    public void init() {
        pobierzdane(wpisView.getAngaz());
        pobierzdaneAll();
        listaPIT11 = deklaracjaPIT11SchowekFacade.findByRokFirma(wpisView.getRokWpisu(), wpisView.getFirma());
        Collections.sort(listaPIT11, new DeklaracjaPIT11Schowekcomparator());
    }
    
     public void init2() {
        try {
            pobierzdaneAll();
            listaPIT11 = deklaracjaPIT11SchowekFacade.findByRokFirma(wpisView.getRokWpisu(), wpisView.getFirma());
            Collections.sort(listaPIT11, new DeklaracjaPIT11Schowekcomparator());
        } catch (Exception ex){
            System.out.println(E.e(ex));
        }
    }
 public void aktywujKartaWyn(FirmaKadry firma) {
        if (firma!=null) {
            wpisView.setFirma(firma);
//            if (firma.getAngazList()==null||firma.getAngazList().isEmpty()) {
//                wpisView.setPracownik(null);
//                wpisView.setAngaz(null);
//                wpisView.setUmowa(null);
//            } else {
//                Angaz angaz = firma.getAngazList().get(0);
//                wpisView.setPracownik(angaz.getPracownik());
//                wpisView.setAngaz(angaz);
//                List<Umowa> umowy = angaz.getUmowaList();
//                if (umowy!=null && umowy.size()==1) {
//                    wpisView.setUmowa(umowy.get(0));
//                } else if (umowy!=null) {
//                    try {
//                        wpisView.setUmowa(umowy.stream().filter(p->p.isAktywna()).findFirst().get());
//                    } catch (Exception e){}
//                }
//            }
            init();
            Msg.msg("Aktywowano firmę "+firma.getNazwa());
        }
    }
   
     public void edytujpasek(Pasekwynagrodzen pasek) {
        if (pasek!=null) {
            pasekwynagrodzenFacade.edit(pasek);
            Msg.dP();
        }
    }
       
    public void pobierzdane(Angaz angaz) {
        if (angaz!=null) {
            kartawynagrodzenlist = pobierzkartywynagrodzen(angaz, wpisView.getRokWpisu());
            aktualizujdane(kartawynagrodzenlist, wpisView.getRokWpisu(), angaz);
            selected = angaz.getPracownik();
            //Msg.msg("Pobrano dane wynagrodzeń");
        }
    }
    
     public void pobierzdaneAll() {
        brakkoduurzedu = false;
        sumypracownicy = new ArrayList<>();
        pitpola  = new ArrayList<>();
        List<DeklaracjaPIT11Schowek> pityfirma = deklaracjaPIT11SchowekFacade.findByRokFirma(wpisView.getRokWpisu(), wpisView.getFirma());
        List<Angaz> pracownicy = angazFacade.findByFirma(wpisView.getFirma());
        Kartawynagrodzen duzasuma = new Kartawynagrodzen();
        for (Angaz p: pracownicy) {
            if (p!=null) {
               List<Kartawynagrodzen> kartawynagrodzenlist = pobierzkartywynagrodzen(p, wpisView.getRokWpisu());
               List<Pasekwynagrodzen> paski = pasekwynagrodzenFacade.findByRokWyplAngaz(wpisView.getRokWpisu(), p);
                if (paski!=null && !paski.isEmpty()) {
                    Map<String,Kartawynagrodzen> sumy = new HashMap<>();
                    Kartawynagrodzen suma = sumuj(kartawynagrodzenlist, paski, p.getPracownik().getNazwiskoImie(), p.getPracownik().getDataurodzenia(), p.getPracownik().getPlec(), sumy, p);
                    suma.setId(p.getId());
                    suma.setJestPIT11(pobierzpotwierdzenie(p.getPracownik(), pityfirma));
                    suma.setRok(wpisView.getRokWpisu());
                    suma.setWyslano(pobierzstatus200(p.getPracownik(), pityfirma));
                    sumypracownicy.add(suma);
                    pitpola.add(naniesnapola(suma, p.getPracownik()));
                    duzasuma.dodajkarta(suma);
                }
                String kodurzeduskarbowego = p.getPracownik().getKodurzeduskarbowego();
                if (kodurzeduskarbowego==null||kodurzeduskarbowego.equals("")) {
                    brakkoduurzedu = true;
                }
           }
        }
        //Msg.msg("Pobrano dane wszystkich wynagrodzeń");
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
        pITPola.dodajumowaopraceEmeryt(sumy.get("sumaUmowaopraceEmeryt"));
        pITPola.dodajumowaopraceEmerytkosztypodwyzszone(sumy.get("sumaUmowaopraceEmerytkosztypodwyzszone"));
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
    
//    private List<Kartawynagrodzen> przygotujkartywynagrodzen(Angaz selectedangaz, String rok) {
//        List<Kartawynagrodzen> kartypobranezbazy = new ArrayList<>();
//            for (String mc : Mce.getMceListS()) {
//                Kartawynagrodzen nowa = new Kartawynagrodzen();
//                nowa.setAngaz(selectedangaz);
//                nowa.setRok(rok);
//                nowa.setMc(mc);
//                kartypobranezbazy.add(nowa);
//            }
//        
//        return kartypobranezbazy;
//    }
    
    private List<Kartawynagrodzen> pobierzkartywynagrodzenwydruk(Angaz selectedangaz, String rok) {
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
        }
        return kartypobranezbazy;
    }
    
    private void aktualizujdane(List<Kartawynagrodzen> kartawynagrodzenlist, String rok, Angaz angaz) {
        List<Pasekwynagrodzen> paski = pasekwynagrodzenFacade.findByRokWyplAngaz(rok, angaz);
        listapaski = new ArrayList<>(paski);
        Collections.sort(listapaski, new Pasekwynagrodzencomparator());
        if (paski!=null && !paski.isEmpty()) {
            Map<String,Kartawynagrodzen> sumy = new HashMap<>();
            Pracownik pracownik = angaz.getPracownik();
            sumuj(kartawynagrodzenlist, paski, pracownik.getNazwiskoImie(), pracownik.getDataurodzenia(), pracownik.getPlec(), sumy, angaz);
        }
    }
    //tutaj
    private Kartawynagrodzen sumuj(List<Kartawynagrodzen> kartawynagrodzenlist, List<Pasekwynagrodzen> paski, String nazwiskoiimie, String dataurodzenia, String plec, Map<String,Kartawynagrodzen> sumy, Angaz angaz) {
        Kartawynagrodzen sumaUmowaoprace = new Kartawynagrodzen();
        Kartawynagrodzen sumaUmowaopraceEmeryt = new Kartawynagrodzen();
        Kartawynagrodzen sumaUmowaopraceEmerytkosztypodwyzszone = new Kartawynagrodzen();
        Kartawynagrodzen sumaUmowaoprace26zwolnione = new Kartawynagrodzen();
        Kartawynagrodzen sumaUmowaopracekosztypodwyzszone = new Kartawynagrodzen();
        Kartawynagrodzen sumaUmowapelnieniefunkcji  = new Kartawynagrodzen();
        Kartawynagrodzen sumaUmowazlecenia  = new Kartawynagrodzen();
        Kartawynagrodzen sumaUmowazlecenia26zwolnione  = new Kartawynagrodzen();
        Kartawynagrodzen sumaUmowazleceniaEmeryt  = new Kartawynagrodzen();
        Kartawynagrodzen sumaZasilkiDorosly  = new Kartawynagrodzen();
        Kartawynagrodzen sumaZasilki26  = new Kartawynagrodzen();
        Kartawynagrodzen suma = new Kartawynagrodzen();
        suma.setAngaz(angaz);
        suma.setMc("razem");
        int lata = 0;
        String okresprzekroczenia = null;
        boolean badajpasek = false;
        double podstawanarastajaco = 0.0;
        for (Kartawynagrodzen karta : kartawynagrodzenlist) {
            List<Angaz> angazzpaskow = new ArrayList<>();
            for (Iterator<Pasekwynagrodzen> it = paski.iterator(); it.hasNext();) {
                Pasekwynagrodzen pasek = it.next();
                Pasekpomocnik sumujprzychodyzlisty = PasekwynagrodzenBean.sumujprzychodyzlisty(pasek);
                pasek.naniespomocnika(sumujprzychodyzlisty, pasek.isPrzekroczenieoddelegowanie());
                Data.obliczwiekNaniesnapasek(dataurodzenia, pasek);
                lata = pasek.getLata();
                if (lata==64||lata==65) {
                    System.out.println("");
                }
                if (pasek.isDo26lat()) {
                    badajpasek = true;
                }
                if (badajpasek && pasek.isDo26lat()==false&&okresprzekroczenia==null) {
                    okresprzekroczenia = pasek.getOkresWypl();
                }
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

                    if ((pasek.getRodzajWynagrodzenia()==1||pasek.getRodzajWynagrodzenia()==4)&&pasek.isDo26lat()==false) {
                        //emeryci
                        if ((plec.equals("M")&&lata>=65)||(plec.equals("K")&&lata>=60)) {
                            //koszty podwyzszone laduja tam gdzie normalne 20-12-2023
                            sumaUmowaopraceEmeryt.dodaj(pasek);
//                            if (pasek.getProcentkosztow()>100.0) {
//                                sumaUmowaopraceEmerytkosztypodwyzszone.dodaj(pasek);
//                            } else {
//                                sumaUmowaopraceEmeryt.dodaj(pasek);
//                            }
                        } else {
                            sumaUmowaoprace.dodaj(pasek);
                            
                            //koszty podwyzszone laduja tam gdzie normalne 20-12-2023
//                            if (pasek.getProcentkosztow()>100.0) {
//                                sumaUmowaopracekosztypodwyzszone.dodaj(pasek);
//                            } else {
//                                sumaUmowaoprace.dodaj(pasek);
//                            }
                        }
                    } else if (pasek.getRodzajWynagrodzenia()==1006&&pasek.isDo26lat()==false) {
                            sumaZasilkiDorosly.dodaj(pasek);
                    } else if (pasek.getRodzajWynagrodzenia()==1&&pasek.isDo26lat()==true) {
                            sumaUmowaoprace26zwolnione.dodaj(pasek);
                    } else if (pasek.getRodzajWynagrodzenia()==1006&&pasek.isDo26lat()==true) {
                            sumaZasilki26.dodaj(pasek);
                    } else if (pasek.getRodzajWynagrodzenia()==2&&(plec.equals("M")&&lata>=65)||(plec.equals("K")&&lata>=60)) {
                        sumaUmowazleceniaEmeryt.dodaj(pasek);
                    } else if (pasek.getRodzajWynagrodzenia()==2&&pasek.isDo26lat()==false) {
                        sumaUmowazlecenia.dodaj(pasek);
                    } else if (pasek.getRodzajWynagrodzenia()==2&&pasek.isDo26lat()==true) {
                        sumaUmowazlecenia26zwolnione.dodaj(pasek);
                    } else if (pasek.getRodzajWynagrodzenia()==15) {
                        sumaUmowapelnieniefunkcji.dodaj(pasek);
                    }
                    podstawanarastajaco += pasek.getPodstawaopodatkowania();
                    karta.setPodstawaopodatkowanianarast(podstawanarastajaco);
                    karta.dodaj(pasek);
                    suma.dodaj(pasek);
                    it.remove();
                }
                //tutaj 28.01.2024
                //pasekwynagrodzenFacade.editList(paski);
            }
        }
        suma.setNazwiskoiimie(nazwiskoiimie);
        suma.setOkresprzekroczenie26(okresprzekroczenia);
        String ostatnidzien =  Data.ostatniDzien(wpisView.getRokWpisu(), "12");
        suma.setWieklata(Data.obliczwieklata(angaz.getPracownik().getDataurodzenia(),ostatnidzien));
        sumy.put("sumaUmowaoprace", sumaUmowaoprace);
        sumy.put("sumaUmowaoprace26zwolnione", sumaUmowaoprace26zwolnione);
        sumy.put("sumaUmowaopracekosztypodwyzszone", sumaUmowaopracekosztypodwyzszone);
        sumy.put("sumaUmowapelnieniefunkcji", sumaUmowapelnieniefunkcji);
        sumy.put("sumaUmowazlecenia", sumaUmowazlecenia);
        sumy.put("sumaUmowazlecenia26zwolnione", sumaUmowazlecenia26zwolnione);
        sumy.put("sumaUmowaopraceEmeryt", sumaUmowaopraceEmeryt);
        sumy.put("sumaUmowaopraceEmerytkosztypodwyzszone", sumaUmowaopraceEmerytkosztypodwyzszone);
        sumy.put("sumaUmowazleceniaEmeryt", sumaUmowazleceniaEmeryt);
        sumy.put("sumaZasilkiDorosly", sumaZasilkiDorosly);
        sumy.put("sumaZasilki26", sumaZasilki26);
        suma.setSumy(sumy);
        //kartaWynagrodzenFacade.createEditList(kartawynagrodzenlist);
        kartawynagrodzenlist.add(suma);
        return suma;
    }
    
    public void drukuj() {
        if (kartawynagrodzenlist!=null && kartawynagrodzenlist.size()>0) {
            PdfKartaWynagrodzen.drukuj(kartawynagrodzenlist, wpisView.getFirma(), selected, wpisView.getRokWpisu());
            Msg.msg("Wydrukowano kartę wynagrodzeń");
        } else {
            Msg.msg("e","Błąd drukowania. Brak karty wynagrodzeń");
        }
    }

    //tutaj
    public void pit11() {
        if (kartawynagrodzenlist!=null && kartawynagrodzenlist.size()>0) {
            Kartawynagrodzen kartawynagrodzen = kartawynagrodzenlist.get(12);
            FirmaKadry firma = kartawynagrodzen.getAngaz().getFirma();
            Pracownik pracownik = kartawynagrodzen.getAngaz().getPracownik();
            if (pracownik.getKodurzeduskarbowego()!=null) {
                List<DeklaracjaPIT11Schowek> istniejacedeklaracje = deklaracjaPIT11SchowekFacade.findByRokFirmaPracownik(wpisView.getRokWpisu(), kartawynagrodzen.getAngaz().getPracownik(), firma);
                boolean korekta = false;
                if (istniejacedeklaracje!=null&&istniejacedeklaracje.size()>0) {
                    korekta = true;
                }
                byte normalna1korekta2 = korekta?(byte)2:(byte)1;
                boolean przekroczenie183 = kartawynagrodzen.getAngaz().getPrzekroczenierok()!=null;
                Object[] sciezka = beanstesty.PIT11_29Bean.generujXML(kartawynagrodzen, firma, pracownik, normalna1korekta2, pracownik.getKodurzeduskarbowego(), kartawynagrodzen.getRok(), kartawynagrodzen.getSumy(), przekroczenie183);
                pl.gov.crd.wzor._2022._11._09._11890.Deklaracja deklaracja = (pl.gov.crd.wzor._2022._11._09._11890.Deklaracja)sciezka[2];
                if (deklaracja!=null) {
                    String polecenie = "wydrukXML(\""+(String)sciezka[0]+"\")";
                    PrimeFaces.current().executeScript(polecenie);
                    String nazwapliku = PdfPIT11.drukuj29(deklaracja, wpisView.getUzer().getImieNazwiskoTelefon(), null);
                    DeklaracjaPIT11Schowek schowek = new DeklaracjaPIT11Schowek(deklaracja, firma, pracownik, wpisView.getRokWpisu(),"PIT11");
                    //dezaktywancja na potrzeby testow
                    deklaracjaSchowekFacade.create(schowek);
                    polecenie = "wydrukPDF(\""+nazwapliku+"\")";
                    PrimeFaces.current().executeScript(polecenie);
                    Msg.msg("Wydrukowano PIT-11");
                }
            } else {
                Msg.msg("e","Błąd generowania PIT-11. Brak kodu urzędu skarbowego");
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
    
    public void pit11All(boolean normalne0korekty1) {
        if (listaselected!=null && listaselected.size()>0) {
            for (Kartawynagrodzen karta : listaselected) {
                if (normalne0korekty1||(karta.isJestPIT11()==false && karta.getAngaz()!=null)) {
                    Kartawynagrodzen kartawynagrodzen = karta;
                    FirmaKadry firma = kartawynagrodzen.getAngaz().getFirma();
                    Pracownik pracownik = kartawynagrodzen.getAngaz().getPracownik();
                    List<DeklaracjaPIT11Schowek> istniejacedeklaracje = deklaracjaPIT11SchowekFacade.findByRokFirmaPracownik(wpisView.getRokWpisu(), karta.getAngaz().getPracownik(), firma);
                    boolean korekta = false;
                    if (istniejacedeklaracje!=null&&istniejacedeklaracje.size()>0) {
                        korekta = true;
                    }
                    try {
                        byte normalna1korekta2 = korekta?(byte)2:(byte)1;
                        boolean przekroczenie183 = kartawynagrodzen.getAngaz().getPrzekroczenierok()!=null&&kartawynagrodzen.getAngaz().getPrzekroczenierok().isBlank()==false;
                        Object[] sciezka = beanstesty.PIT11_29Bean.generujXML(kartawynagrodzen, firma, pracownik, normalna1korekta2, pracownik.getKodurzeduskarbowego(), kartawynagrodzen.getRok(), kartawynagrodzen.getSumy(), przekroczenie183);
                        pl.gov.crd.wzor._2022._11._09._11890.Deklaracja deklaracja = (pl.gov.crd.wzor._2022._11._09._11890.Deklaracja)sciezka[2];
                        if (deklaracja!=null) {
//                            String polecenie = "wydrukXML(\""+(String)sciezka[0]+"\")";
//                            PrimeFaces.current().executeScript(polecenie);
                            String nazwapliku = PdfPIT11.drukuj29(deklaracja, wpisView.getUzer().getImieNazwiskoTelefon(), null);
                            DeklaracjaPIT11Schowek schowek = new DeklaracjaPIT11Schowek(deklaracja, firma, pracownik, wpisView.getRokWpisu(),"PIT11");
                            if (karta.getPrzekroczenienowypodatek()>0.0) {
                                schowek.setPrzekroczeniekorekta(true);
                            }
                            schowek.setKorekta(korekta);
                            schowek.setUz(wpisView.getUzer());
                            schowek.setPrzekroczenie(karta.getPodstawaopodatkowaniazagranica()>0.0);
                            karta.setJestPIT11(true);
                            deklaracjaSchowekFacade.create(schowek);
                            listaPIT11.add(schowek);
                            String polecenie = "wydrukPDF(\""+nazwapliku+"\")";
                            PrimeFaces.current().executeScript(polecenie);
                            Msg.msg("Wydrukowano PIT-11");
                        }
                    } catch (Exception e) {
                        Msg.msg("e","Bląd generowania. Proszę sprawdzić dane firmy i dane osobowe pracownika, adres itp.");
                        Msg.msg("e","Przy jdg sprawdź datę urodzenia");
                    }
                }
            }
            init();
        } else {
            Msg.msg("e","Błąd generowania PIT-11. Nie wybrano pracowników");
        }
    }
    
   private boolean pobierzpotwierdzenie(Pracownik pracownik, List<DeklaracjaPIT11Schowek> pityfirma) {
       boolean zwrot = false;
        if (pracownik!=null && pityfirma!=null) {
           for (DeklaracjaPIT11Schowek p : pityfirma) {
               if (p.getPracownik().equals(pracownik)) {
                   zwrot = true;
                   break;
               }
           }
        }
        return zwrot;
    }
   
   private boolean pobierzstatus200(Pracownik pracownik, List<DeklaracjaPIT11Schowek> pityfirma) {
       boolean zwrot = false;
        if (pracownik!=null && pityfirma!=null) {
           for (DeklaracjaPIT11Schowek p : pityfirma) {
               if (p.getPracownik().equals(pracownik) && p.getStatus()!=null && p.getStatus().equals("200")) {
                   zwrot = true;
                   break;
               }
           }
        }
        return zwrot;
    }
    
   
    
    public void mailwszystkie() {
        if (listaPIT11sorted!=null && listaPIT11sorted.size()>0) {
            ByteArrayInputStream drukujwszystkiePIT11 = drukujwszystkiePIT11();
            if (drukujwszystkiePIT11 != null) {
                try {
                    byte[] bytes = IOUtils.toByteArray(drukujwszystkiePIT11);
                    SMTPSettings findSprawaByDef = sMTPSettingsFacade.findSprawaByDef();
                    findSprawaByDef.setUseremail(wpisView.getUzer().getEmail());
                    findSprawaByDef.setPassword(wpisView.getUzer().getEmailhaslo());
                    String nazwa = wpisView.getFirma().getNip() + "_DRA" + wpisView.getRokWpisu()+ wpisView.getMiesiacWpisu() + "_" + ".pdf";
                    mail.Mail.mailPIT11Zbiorcze(wpisView.getFirma(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu(), wpisView.getFirma().getEmail(), null, findSprawaByDef, bytes, nazwa, wpisView.getUzer().getEmail());
                    Msg.msg("Wysłano zbiorczo PIT11 do pracodawcy");
                } catch (Exception e){}
            } else {
                Msg.msg("e", "Błąd dwysyki zbiorczo PIT11");
            }
        } else {
            Msg.msg("e","Błąd wysyłki PIT-11. Nie wybrano pracowników");
        }
    }
   
    public ByteArrayInputStream drukujwszystkiePIT11() {
        ByteArrayInputStream zwrot = null;
         if (listaPIT11sorted!=null && listaPIT11sorted.size()>0) {
             try {
                List<InputStream> pojedynczy = new ArrayList<>();
                for (DeklaracjaPIT11Schowek p : listaPIT11sorted) {
                    ByteArrayInputStream drukujPIT11 = drukujPIT11(p, false);
                    pojedynczy.add(drukujPIT11);

                }
                PDFMergerUtility uti = new PDFMergerUtility();
                uti.addSources(pojedynczy);
                ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                String bazwa = wpisView.getFirma().getNip()+"_PIT11_zbiorcze.pdf";
                String realPath = ctx.getRealPath("/")+"resources\\pdf\\"+bazwa;
                uti.setDestinationFileName(realPath);
                uti.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());
                String polecenie = "wydrukPDF(\"" + bazwa + "\")";
                PrimeFaces.current().executeScript(polecenie);
                zwrot = new ByteArrayInputStream(FileUtils.readFileToByteArray(new File(realPath)));
             } catch (Exception e){}
         } else {
            Msg.msg("e","Błąd drukowania PIT-11. Nie wybrano pracowników");
        }
         return zwrot;
    }
    
      public ByteArrayInputStream drukujwszystkiePIT11kartawyn() {
        ByteArrayInputStream zwrot = null;
        List<Angaz> angaze = angazFacade.findByFirmaAktywni(wpisView.getFirma());
         if (listaPIT11sorted!=null && listaPIT11sorted.size()>0) {
             try {
               
                List<InputStream> pojedynczy = new ArrayList<>();
                for (DeklaracjaPIT11Schowek p : listaPIT11sorted) {
                     Kartawynagrodzen suma = new Kartawynagrodzen();
                    ByteArrayInputStream drukujPIT11 = drukujPIT11(p, false);
                    pojedynczy.add(drukujPIT11);
                    Angaz angaz = pobierzangaz(angaze, p.getPracownik());
                    List<Kartawynagrodzen> listakartypracownika = pobierzkartywynagrodzenwydruk(angaz, p.getRok());
                    for (Kartawynagrodzen karta : listakartypracownika) {
                        suma.dodajkarta(karta);
                    }
                    listakartypracownika.add(suma);
                    ByteArrayInputStream kartawyn = PdfKartaWynagrodzen.drukujScilent(listakartypracownika, p.getFirma(), p.getPracownik(), p.getRok());
                    pojedynczy.add(kartawyn);
                }
                PDFMergerUtility uti = new PDFMergerUtility();
                uti.addSources(pojedynczy);
                ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                String bazwa = wpisView.getFirma().getNip()+"_PIT11_zbiorcze.pdf";
                String realPath = ctx.getRealPath("/")+"resources\\pdf\\"+bazwa;
                uti.setDestinationFileName(realPath);
                uti.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());
                String polecenie = "wydrukPDF(\"" + bazwa + "\")";
                PrimeFaces.current().executeScript(polecenie);
                zwrot = new ByteArrayInputStream(FileUtils.readFileToByteArray(new File(realPath)));
             } catch (Exception e){}
         } else {
            Msg.msg("e","Błąd drukowania PIT-11. Nie wybrano pracowników");
        }
         return zwrot;
    }
      
    private List<Kartawynagrodzen> pobierzdanewydruk(List<Kartawynagrodzen> sumypracownicy, Pracownik pracownik) {
        List<Kartawynagrodzen> zwrot = new ArrayList<>();
        for (Kartawynagrodzen suma : sumypracownicy) {
            if (suma.getAngaz()!=null&&suma.getAngaz().getPracownik().equals(pracownik)) {
                zwrot = new ArrayList<>(suma.getSumy().values());
                break;
            }
        }
        return zwrot;
    }

            
   
    public ByteArrayInputStream drukujPIT11(DeklaracjaPIT11Schowek deklaracjaPIT11Schowek, boolean pokaz) {
        ByteArrayInputStream zwrot = null;
        if (deklaracjaPIT11Schowek != null) {
            ObjectInputStream is = null;
            try {
                byte[] deklaracja = deklaracjaPIT11Schowek.getDeklaracja(); //        ByteArrayInputStream in = new ByteArrayInputStream(this.deklaracja);
                ByteArrayInputStream in = new ByteArrayInputStream(deklaracja);
                is = new ObjectInputStream(in);
                pl.gov.crd.wzor._2022._11._09._11890.Deklaracja dekl = (pl.gov.crd.wzor._2022._11._09._11890.Deklaracja) is.readObject();
                String nazwapliku = PdfPIT11.drukuj29(dekl, wpisView.getUzer().getImieNazwiskoTelefon(), deklaracjaPIT11Schowek);
                if (pokaz) {
                    String polecenie = "wydrukPDF(\"" + nazwapliku + "\")";
                    PrimeFaces.current().executeScript(polecenie);
                }
                Msg.msg("Pobrano deklaracje");
                ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                String realPath = ctx.getRealPath("/")+"resources\\pdf\\"+nazwapliku;
                zwrot = new ByteArrayInputStream(FileUtils.readFileToByteArray(new File(realPath)));
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
        return zwrot;
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

    public void pokazPIT11XML(DeklaracjaPIT11Schowek deklaracjaPIT11Schowek) {
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
    
    private Angaz pobierzangaz(List<Angaz> angaze, Pracownik pracownik) {
        Angaz zwrot = null;
        for (Angaz angaz : angaze) {
            if (angaz.getPracownik().equals(pracownik)) {
                zwrot = angaz;
            }
        }
        return zwrot;
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

    public List<Pasekwynagrodzen> getListapaski() {
        return listapaski;
    }

    public void setListapaski(List<Pasekwynagrodzen> listapaski) {
        this.listapaski = listapaski;
    }

    public List<DeklaracjaPIT11Schowek> getListaPIT11sorted() {
        return listaPIT11sorted;
    }

    public void setListaPIT11sorted(List<DeklaracjaPIT11Schowek> listaPIT11sorted) {
        this.listaPIT11sorted = listaPIT11sorted;
    }

    public Pracownik getSelected() {
        return selected;
    }

    public void setSelected(Pracownik selected) {
        this.selected = selected;
    }

    public boolean isBrakkoduurzedu() {
        return brakkoduurzedu;
    }

    public void setBrakkoduurzedu(boolean brakkoduurzedu) {
        this.brakkoduurzedu = brakkoduurzedu;
    }

    

   
    

   
    
    
}
