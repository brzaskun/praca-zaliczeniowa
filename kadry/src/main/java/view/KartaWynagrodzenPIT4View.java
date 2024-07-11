/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Kartawynagrodzencomparator;
import dao.AngazFacade;
import dao.DeklaracjaPIT4SchowekFacade;
import dao.KartaWynagrodzenFacade;
import dao.PasekwynagrodzenFacade;
import embeddable.Mce;
import entity.Angaz;
import entity.DeklaracjaPIT4Schowek;
import entity.FirmaKadry;
import entity.Kartawynagrodzen;
import entity.Pasekwynagrodzen;
import entity.Umowa;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.primefaces.PrimeFaces;
import pdf.PdfPIT4;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class KartaWynagrodzenPIT4View  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private KartaWynagrodzenFacade kartaWynagrodzenFacade;
    @Inject
    private DeklaracjaPIT4SchowekFacade deklaracjaPIT4SchowekFacade;
    @Inject
    private WpisView wpisView;
    @Inject
    private PasekwynagrodzenFacade pasekwynagrodzenFacade;
    private TreeMap<String, Kartawynagrodzen> sumaUmowaoprace;
    private TreeMap<String, Kartawynagrodzen> sumaUmowaoprace26zwolnione;
    private TreeMap<String, Kartawynagrodzen> sumaUmowaopracekosztypodwyzszone;
    private TreeMap<String, Kartawynagrodzen> sumaUmowapelnieniefunkcji;
    private TreeMap<String, Kartawynagrodzen> sumaUmowazlecenia;
    private TreeMap<String, Kartawynagrodzen> sumaUmowazlecenia26zwolnione;
    private List<Kartawynagrodzen> sumapracownik;
    private Kartawynagrodzen wybranakarta;
    private double podatekrazem;
    private boolean tojestkorekta;
    @Inject
    private Pit4RView pit4RView;
    @Inject
    private AngazFacade angazFacade;
    private List<Kartawynagrodzen> kartawynagrodzenlist;
    
   

    
    @PostConstruct
    public void init() {
        sumaUmowaoprace = new TreeMap<>();
        sumaUmowaoprace26zwolnione = new TreeMap<>();
        sumaUmowaopracekosztypodwyzszone = new TreeMap<>();
        sumaUmowapelnieniefunkcji = new TreeMap<>();
        sumaUmowazlecenia = new TreeMap<>();
        sumaUmowazlecenia26zwolnione = new TreeMap<>();
        for (String mc : Mce.getMceListS()) {
            sumaUmowaoprace.put(mc, new Kartawynagrodzen());
            sumaUmowaoprace26zwolnione.put(mc, new Kartawynagrodzen());
            sumaUmowaopracekosztypodwyzszone.put(mc, new Kartawynagrodzen());
            sumaUmowapelnieniefunkcji.put(mc, new Kartawynagrodzen());
            sumaUmowazlecenia.put(mc, new Kartawynagrodzen());
            sumaUmowazlecenia26zwolnione.put(mc, new Kartawynagrodzen());
        }
        pobierzdaneAll();
        
    }

   public void aktywuj(FirmaKadry firma) {
        if (firma!=null) {
            wpisView.setFirma(firma);
            if (firma.getAngazList()==null||firma.getAngazList().isEmpty()) {
                wpisView.setPracownik(null);
                wpisView.setAngaz(null);
                wpisView.setUmowa(null);
            } else {
                Angaz angaz = firma.getAngazList().get(0);
                wpisView.setPracownik(angaz.getPracownik());
                wpisView.setAngaz(angaz);
                List<Umowa> umowy = angaz.getUmowaList();
                if (umowy!=null && umowy.size()==1) {
                    wpisView.setUmowa(umowy.get(0));
                } else if (umowy!=null) {
                    try {
                        wpisView.setUmowa(umowy.stream().filter(p->p.isAktywna()).findFirst().get());
                    } catch (Exception e){}
                }
            }
            init();
            Msg.msg("Aktywowano firmę "+firma.getNazwa());
        }
    }
    
     public void pobierzdaneAll() {
        List<Angaz> pracownicy = angazFacade.findByFirma(wpisView.getFirma());
        podatekrazem = 0.0;
        sumapracownik = new ArrayList<>();
        for (Angaz p: pracownicy) {
            if (p!=null) {
               List<Kartawynagrodzen> kartawynagrodzenlist = pobierzkartywynagrodzen(p, wpisView.getRokWpisu());
               List<Pasekwynagrodzen> paski = pasekwynagrodzenFacade.findByRokWyplAngaz(wpisView.getRokWpisu(), p);
                if (paski!=null && !paski.isEmpty()) {
                    Map<String,Kartawynagrodzen> sumy = new HashMap<>();
                    Kartawynagrodzen sumal = sumuj(kartawynagrodzenlist, paski, p.getPracownik().getNazwiskoImie(), sumy, p);
                    podatekrazem = podatekrazem+sumal.getPodatekdochodowy();
                    sumapracownik.add(sumal);
                }
           }
        }
        Msg.msg("Pobrano dane wszystkich wynagrodzeń");
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
    
  
    private Kartawynagrodzen sumuj(List<Kartawynagrodzen> kartawynagrodzenlist, List<Pasekwynagrodzen> paski, String nazwiskoiimie, Map<String,Kartawynagrodzen> sumy, Angaz angaz) {
        Kartawynagrodzen suma = new Kartawynagrodzen();
        suma.setAngaz(angaz);
        suma.setMc("razem");
        double podstawanarastajaco = 0.0;
        for (Kartawynagrodzen karta : kartawynagrodzenlist) {
            List<Angaz> angazzpaskow = new ArrayList<>();
            for (Iterator<Pasekwynagrodzen> it = paski.iterator(); it.hasNext();) {
                Pasekwynagrodzen pasek = it.next();
                if (pasek.getMcwypl().equals(karta.getMc()) && (pasek.getBrutto()>0.0)&& pasek.getAngaz().getPracownik().isNierezydent()==false) {
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
//                    id,nazwa,tyt_serial
//                    1,"umowa o pracę",1
//                    2,"umowa zlecenia i o dzieło",2
//                    3,"pełnienie obowiązków",15
//                    4,zasiłki,1006

                    if ((pasek.getRodzajWynagrodzenia()==1||pasek.getRodzajWynagrodzenia()==4||pasek.getRodzajWynagrodzenia()==1006)&&pasek.isDo26lat()==false) {
                        if (pasek.getProcentkosztow()>100.0) {
                            sumaUmowaopracekosztypodwyzszone.get(karta.getMc()).dodaj(pasek);
                        } else {
                            sumaUmowaoprace.get(karta.getMc()).dodaj(pasek);
                        }
                    } else if ((pasek.getRodzajWynagrodzenia()==1||pasek.getRodzajWynagrodzenia()==1006)&&pasek.isDo26lat()==true) {
                            sumaUmowaoprace26zwolnione.get(karta.getMc()).dodaj(pasek);
                    } else if (pasek.getRodzajWynagrodzenia()==2&&pasek.isDo26lat()==false) {
                        sumaUmowazlecenia.get(karta.getMc()).dodaj(pasek);
                    } else if (pasek.getRodzajWynagrodzenia()==2&&pasek.isDo26lat()==true) {
                        sumaUmowazlecenia26zwolnione.get(karta.getMc()).dodaj(pasek);
                    } else if (pasek.getRodzajWynagrodzenia()==15&&pasek.isDo26lat()==false) {
                        sumaUmowapelnieniefunkcji.get(karta.getMc()).dodaj(pasek);
                    }
                    podstawanarastajaco += pasek.getPodstawaopodatkowania();
                    karta.setPodstawaopodatkowanianarast(podstawanarastajaco);
                    karta.dodaj(pasek);
                    suma.dodaj(pasek);
                    it.remove();
                }
            }
        }
        suma.setNazwiskoiimie(nazwiskoiimie);
//        sumy.put("sumaUmowaoprace", sumaUmowaoprace);
//        sumy.put("sumaUmowaoprace26zwolnione", sumaUmowaoprace26zwolnione);
//        sumy.put("sumaUmowaopracekosztypodwyzszone", sumaUmowaopracekosztypodwyzszone);
//        sumy.put("sumaUmowapelnieniefunkcji", sumaUmowapelnieniefunkcji);
//        sumy.put("sumaUmowazlecenia", sumaUmowazlecenia);
//        sumy.put("sumaUmowazlecenia26zwolnione", sumaUmowazlecenia26zwolnione);
//        suma.setSumy(sumy);
        kartaWynagrodzenFacade.createEditList(kartawynagrodzenlist);
        kartawynagrodzenlist.add(suma);
        return suma;
    }
    
    public void pit4() {
        if (sumaUmowaoprace!=null && sumaUmowaopracekosztypodwyzszone!=null) {
            FirmaKadry firma = wpisView.getAngaz().getFirma();
            byte normalna1korekta2 = tojestkorekta?(byte)2:(byte)1;
            //Object[] sciezka = beanstesty.PIT4R_12Bean.generujXML(sumaUmowaoprace, sumaUmowaopracekosztypodwyzszone, sumaUmowaoprace26zwolnione, sumaUmowazlecenia, sumaUmowapelnieniefunkcji, firma, (byte)1, firma.getKodurzeduskarbowego(), wpisView.getRokWpisu());
            Object[] sciezka = beanstesty.PIT4R_13Bean.generujXML(sumaUmowaoprace, sumaUmowaopracekosztypodwyzszone, sumaUmowaoprace26zwolnione, sumaUmowazlecenia, 
                    sumaUmowapelnieniefunkcji, sumaUmowazlecenia26zwolnione, firma, normalna1korekta2, firma.getKodurzeduskarbowego(), wpisView.getRokWpisu());
            //pl.gov.crd.wzor._2021._04._02._10568.Deklaracja deklaracja = (pl.gov.crd.wzor._2021._04._02._10568.Deklaracja)sciezka[2];
            pl.gov.crd.wzor._2023._11._07._12978.Deklaracja deklaracja = (pl.gov.crd.wzor._2023._11._07._12978.Deklaracja)sciezka[2];
            if (deklaracja!=null) {
                String polecenie = "wydrukXML(\""+(String)sciezka[0]+"\")";
                PrimeFaces.current().executeScript(polecenie);
                DeklaracjaPIT4Schowek schowek = new DeklaracjaPIT4Schowek(deklaracja, wpisView.getFirma(), wpisView.getRokWpisu(),"PIT4R");
                schowek.setUz(wpisView.getUzer());
                deklaracjaPIT4SchowekFacade.create(schowek);
                String nazwapliku = PdfPIT4.drukuj(deklaracja, schowek);
                pit4RView.init();
                polecenie = "wydrukPDF(\""+nazwapliku+"\")";
                PrimeFaces.current().executeScript(polecenie);
                Msg.msg("Wydrukowano PIT-4R");
            }
        } else {
            Msg.msg("e","Błąd generowania PIT-4R. Brak sum");
        }
    }
    
    public Kartawynagrodzen getWybranakarta() {
        return wybranakarta;
    }

    public void setWybranakarta(Kartawynagrodzen wybranakarta) {
        this.wybranakarta = wybranakarta;
    }

    public TreeMap<String, Kartawynagrodzen> getSumaUmowaoprace() {
        return sumaUmowaoprace;
    }

    public void setSumaUmowaoprace(TreeMap<String, Kartawynagrodzen> sumaUmowaoprace) {
        this.sumaUmowaoprace = sumaUmowaoprace;
    }

    public TreeMap<String, Kartawynagrodzen> getSumaUmowaoprace26zwolnione() {
        return sumaUmowaoprace26zwolnione;
    }

    public void setSumaUmowaoprace26zwolnione(TreeMap<String, Kartawynagrodzen> sumaUmowaoprace26zwolnione) {
        this.sumaUmowaoprace26zwolnione = sumaUmowaoprace26zwolnione;
    }

    public TreeMap<String, Kartawynagrodzen> getSumaUmowaopracekosztypodwyzszone() {
        return sumaUmowaopracekosztypodwyzszone;
    }

    public void setSumaUmowaopracekosztypodwyzszone(TreeMap<String, Kartawynagrodzen> sumaUmowaopracekosztypodwyzszone) {
        this.sumaUmowaopracekosztypodwyzszone = sumaUmowaopracekosztypodwyzszone;
    }

    public TreeMap<String, Kartawynagrodzen> getSumaUmowapelnieniefunkcji() {
        return sumaUmowapelnieniefunkcji;
    }

    public void setSumaUmowapelnieniefunkcji(TreeMap<String, Kartawynagrodzen> sumaUmowapelnieniefunkcji) {
        this.sumaUmowapelnieniefunkcji = sumaUmowapelnieniefunkcji;
    }

    public TreeMap<String, Kartawynagrodzen> getSumaUmowazlecenia() {
        return sumaUmowazlecenia;
    }

    public void setSumaUmowazlecenia(TreeMap<String, Kartawynagrodzen> sumaUmowazlecenia) {
        this.sumaUmowazlecenia = sumaUmowazlecenia;
    }

    public TreeMap<String, Kartawynagrodzen> getSumaUmowazlecenia26zwolnione() {
        return sumaUmowazlecenia26zwolnione;
    }

    public void setSumaUmowazlecenia26zwolnione(TreeMap<String, Kartawynagrodzen> sumaUmowazlecenia26zwolnione) {
        this.sumaUmowazlecenia26zwolnione = sumaUmowazlecenia26zwolnione;
    }

    public boolean isTojestkorekta() {
        return tojestkorekta;
    }

    public void setTojestkorekta(boolean tojestkorekta) {
        this.tojestkorekta = tojestkorekta;
    }

   
    public double getPodatekrazem() {
        return podatekrazem;
    }

    public void setPodatekrazem(double podatekrazem) {
        this.podatekrazem = podatekrazem;
    }

    public List<Kartawynagrodzen> getSumapracownik() {
        return sumapracownik;
    }
    
    public void setSumapracownik(List<Kartawynagrodzen> sumapracownik) {
        this.sumapracownik = sumapracownik;
    }
    
}
