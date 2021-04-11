/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Kartawynagrodzencomparator;
import dao.AngazFacade;
import dao.KartaWynagrodzenFacade;
import dao.PasekwynagrodzenFacade;
import embeddable.Mce;
import entity.Angaz;
import entity.Kartawynagrodzen;
import entity.Pasekwynagrodzen;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
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
    private AngazFacade angazFacade;
    @Inject
    private WpisView wpisView;
    private List<Angaz> listapracownikow;
    private Angaz selectedangaz;
    private List<Kartawynagrodzen> kartawynagrodzenlist;
    @Inject
    private PasekwynagrodzenFacade pasekwynagrodzenFacade;

    
    
    @PostConstruct
    private void init() {
        listapracownikow = angazFacade.findByFirma(wpisView.getFirma());
    }

        
       
    public void pobierzdane() {
        if (selectedangaz!=null) {
            wpisView.setPracownik(selectedangaz.getPracownik());
            kartawynagrodzenlist = pobierzkartywynagrodzen(selectedangaz, wpisView.getRokWpisu());
            aktualizujdane(kartawynagrodzenlist, wpisView.getRokWpisu(), selectedangaz);
            Msg.msg("Pobrano dane");
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
            kartaWynagrodzenFacade.createList(kartypobranezbazy);
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
        Kartawynagrodzen suma = new Kartawynagrodzen();
        suma.setMc("razem");
        for (Kartawynagrodzen p : kartawynagrodzenlist) {
            if (p.getId()!=null) {
                p.zeruj();
            }
            for (Iterator<Pasekwynagrodzen> it = paski.iterator(); it.hasNext();) {
                Pasekwynagrodzen pasek = it.next();
                if (pasek.getMc().equals(p.getMc())) {
                    p.dodaj(pasek);
                    suma.dodaj(pasek);
                    it.remove();
                }
            }
        }
        kartawynagrodzenlist.add(suma);
    }
    
    public void drukuj() {
        if (kartawynagrodzenlist!=null && kartawynagrodzenlist.size()>0) {
            PdfKartaWynagrodzen.drukuj(kartawynagrodzenlist, selectedangaz, wpisView.getRokWpisu());
            Msg.msg("Wydrukowano kartę wynagrodzeń");
        } else {
            Msg.msg("e","Błąd drukowania. Brak karty wynagrodze");
        }
    }

    public List<Angaz> getListapracownikow() {
        return listapracownikow;
    }

    public void setListapracownikow(List<Angaz> listapracownikow) {
        this.listapracownikow = listapracownikow;
    }

    public Angaz getSelectedangaz() {
        return selectedangaz;
    }

    public void setSelectedangaz(Angaz selectedangaz) {
        this.selectedangaz = selectedangaz;
    }

    public List<Kartawynagrodzen> getKartawynagrodzenlist() {
        return kartawynagrodzenlist;
    }

    public void setKartawynagrodzenlist(List<Kartawynagrodzen> kartawynagrodzenlist) {
        this.kartawynagrodzenlist = kartawynagrodzenlist;
    }

   
    
    

   
    
    
}
