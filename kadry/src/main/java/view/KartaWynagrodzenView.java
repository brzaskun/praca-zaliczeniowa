/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Kartawynagrodzencomparator;
import dao.KartaWynagrodzenFacade;
import dao.PasekwynagrodzenFacade;
import embeddable.Mce;
import entity.Angaz;
import entity.FirmaKadry;
import entity.Kartawynagrodzen;
import entity.Pasekwynagrodzen;
import entity.Pracownik;
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
    private WpisView wpisView;
    private List<Kartawynagrodzen> kartawynagrodzenlist;
    private Map<String,Kartawynagrodzen> sumy;
    private Kartawynagrodzen sumaUmowaoprace;
    private Kartawynagrodzen sumaUmowaopracekosztypodwyzszone;
    private Kartawynagrodzen sumaUmowaoprace26;
    private Kartawynagrodzen sumaUmowaopracekosztypodwyzszone26;
    private Kartawynagrodzen sumaUmowapelnieniefunkcji;
    private Kartawynagrodzen sumaUmowazlecenia;
    private Kartawynagrodzen sumaUmowazlecenia26;
    @Inject
    private PasekwynagrodzenFacade pasekwynagrodzenFacade;

    
    
    public void init() {
        sumy = new HashMap<>();
        pobierzdane();
    }

        
       
    public void pobierzdane() {
        if (wpisView.getAngaz()!=null) {
            kartawynagrodzenlist = pobierzkartywynagrodzen(wpisView.getAngaz(), wpisView.getRokWpisu());
            aktualizujdane(kartawynagrodzenlist, wpisView.getRokWpisu(), wpisView.getAngaz());
            Msg.msg("Pobrano dane wynagrodzeń");
        }
    }
    
    private List<Kartawynagrodzen> pobierzkartywynagrodzen(Angaz selectedangaz, String rok) {
        List<Kartawynagrodzen> kartypobranezbazy = selectedangaz.getKartawynagrodzenList();
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
        List<Pasekwynagrodzen> paski = pasekwynagrodzenFacade.findByRokAngaz(rok, angaz);
        if (paski!=null && !paski.isEmpty()) {
            sumuj(kartawynagrodzenlist, paski);
        }
    }

    private void sumuj(List<Kartawynagrodzen> kartawynagrodzenlist, List<Pasekwynagrodzen> paski) {
        sumaUmowaoprace = new Kartawynagrodzen();
        sumaUmowaoprace26 = new Kartawynagrodzen();
        sumaUmowaopracekosztypodwyzszone = new Kartawynagrodzen();
        sumaUmowaopracekosztypodwyzszone26 = new Kartawynagrodzen();
        sumaUmowapelnieniefunkcji  = new Kartawynagrodzen();
        sumaUmowazlecenia  = new Kartawynagrodzen();
        sumaUmowazlecenia26  = new Kartawynagrodzen();
        Kartawynagrodzen suma = new Kartawynagrodzen();
        suma.setMc("razem");
        for (Kartawynagrodzen karta : kartawynagrodzenlist) {
            List<Angaz> angazzpaskow = new ArrayList<>();
            for (Iterator<Pasekwynagrodzen> it = paski.iterator(); it.hasNext();) {
                Pasekwynagrodzen pasek = it.next();
                if (pasek.getMc().equals(karta.getMc())) {
                    //tu sie dodaje paski do karty wynagrodzen
                    if (angazzpaskow.contains(pasek.getKalendarzmiesiac().getUmowa().getAngaz())) {
                        karta.setKosztywieleumow(true);
                        suma.setKosztywieleumow(true);
                    } else {
                        angazzpaskow.add(pasek.getKalendarzmiesiac().getUmowa().getAngaz());
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

                    if (pasek.getRodzajWynagrodzenia()==1&&pasek.isDo26lat()==false) {
                        if (pasek.getProcentkosztow()>100.0) {
                            sumaUmowaopracekosztypodwyzszone.dodaj(pasek);
                        } else {
                            sumaUmowaoprace.dodaj(pasek);
                        }
                    } else if (pasek.getRodzajWynagrodzenia()==1&&pasek.isDo26lat()==true) {
                        if (pasek.getProcentkosztow()>100.0) {
                            sumaUmowaopracekosztypodwyzszone26.dodaj(pasek);
                        } else {
                            sumaUmowaoprace26.dodaj(pasek);
                        }
                    } else if (pasek.getRodzajWynagrodzenia()==2&&pasek.isDo26lat()==false) {
                        sumaUmowazlecenia.dodaj(pasek);
                    } else if (pasek.getRodzajWynagrodzenia()==2&&pasek.isDo26lat()==true) {
                        sumaUmowazlecenia26.dodaj(pasek);
                    } else if (pasek.getRodzajWynagrodzenia()==3&&pasek.isDo26lat()==false) {
                        sumaUmowapelnieniefunkcji.dodaj(pasek);
                    }
                    karta.dodaj(pasek);
                    suma.dodaj(pasek);
                    it.remove();
                }
            }
        }
        kartaWynagrodzenFacade.createEditList(kartawynagrodzenlist);
        kartawynagrodzenlist.add(suma);
        sumy.put("sumaUmowaoprace", sumaUmowaoprace);
        sumy.put("sumaUmowaoprace26", sumaUmowaoprace26);
        sumy.put("sumaUmowaopracekosztypodwyzszone", sumaUmowaopracekosztypodwyzszone);
        sumy.put("sumaUmowaopracekosztypodwyzszone26", sumaUmowaopracekosztypodwyzszone26);
        sumy.put("sumaUmowapelnieniefunkcji", sumaUmowapelnieniefunkcji);
        sumy.put("sumaUmowazlecenia", sumaUmowazlecenia);
        sumy.put("sumaUmowazlecenia26", sumaUmowazlecenia26);
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
            Kartawynagrodzen kartawynagrodzen = kartawynagrodzenlist.get(0);
            FirmaKadry firma = kartawynagrodzen.getAngaz().getFirma();
            Pracownik pracownik = kartawynagrodzen.getAngaz().getPracownik();
            String[] sciezka = beanstesty.PIT11_27Bean.generujXML(kartawynagrodzen, firma, pracownik, (byte)1, "3220", kartawynagrodzen.getRok(), sumy);
            String polecenie = "wydrukXML(\""+sciezka[0]+"\")";
            PrimeFaces.current().executeScript(polecenie);
            Msg.msg("Wydrukowano PIT-11");
        } else {
            Msg.msg("e","Błąd generowania PIT-11. Brak karty wynagrodzeń");
        }
    }
    
   
    
    public List<Kartawynagrodzen> getKartawynagrodzenlist() {
        return kartawynagrodzenlist;
    }

    public void setKartawynagrodzenlist(List<Kartawynagrodzen> kartawynagrodzenlist) {
        this.kartawynagrodzenlist = kartawynagrodzenlist;
    }

   

   

   
   

   
    
    
}
