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
    private WpisView wpisView;
    private List<Kartawynagrodzen> kartawynagrodzenlist;
    @Inject
    private PasekwynagrodzenFacade pasekwynagrodzenFacade;

    
    
    @PostConstruct
    public void init() {
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
            kartaWynagrodzenFacade.createList(kartypobranezbazy);
        } else {
            for (Iterator<Kartawynagrodzen> it = kartypobranezbazy.iterator();it.hasNext();) {
                Kartawynagrodzen k = it.next();
                if (k.getId()==null) {
                    it.remove();
                }
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
            PdfKartaWynagrodzen.drukuj(kartawynagrodzenlist, wpisView.getAngaz(), wpisView.getRokWpisu());
            Msg.msg("Wydrukowano kartę wynagrodzeń");
        } else {
            Msg.msg("e","Błąd drukowania. Brak karty wynagrodze");
        }
    }


    public List<Kartawynagrodzen> getKartawynagrodzenlist() {
        return kartawynagrodzenlist;
    }

    public void setKartawynagrodzenlist(List<Kartawynagrodzen> kartawynagrodzenlist) {
        this.kartawynagrodzenlist = kartawynagrodzenlist;
    }

   
    
    

   
    
    
}
