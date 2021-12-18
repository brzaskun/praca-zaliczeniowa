/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.RodzajlistyplacFacade;
import dao.RodzajlistyplacRodzajwynagrodzeniaFacade;
import dao.RodzajwynagrodzeniaFacade;
import entity.Rodzajlistyplac;
import entity.RodzajlistyplacRodzajwynagrodzenia;
import entity.Rodzajwynagrodzenia;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class RodzajlistyplacRodzajwynagrodzeniaView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private RodzajlistyplacRodzajwynagrodzeniaFacade rodzajllistyplacRodzajwynagrodzeniaFacade;
    @Inject
    private RodzajlistyplacFacade rodzajlistyplacFacade;
    private List<Rodzajlistyplac> rodzajlistyplaclist;
    private Rodzajlistyplac rodzajlistyplacselected;
    @Inject
    private RodzajwynagrodzeniaFacade rodzajwynagrodzeniaFacade;
    private List<Rodzajwynagrodzenia> rodzajwynagrodzenialist;
    private org.primefaces.model.DualListModel<Rodzajwynagrodzenia> picklista;
    
    
    @PostConstruct
    private void init() {
        rodzajlistyplaclist = rodzajlistyplacFacade.findAktywne();
        picklista = new org.primefaces.model.DualListModel<>();
    }

    public void pobierzpary() {
        if (rodzajlistyplacselected!=null) {
            List<RodzajlistyplacRodzajwynagrodzenia> docel = rodzajllistyplacRodzajwynagrodzeniaFacade.findByRodzajwynagrodzenia(rodzajlistyplacselected);
            List<Rodzajwynagrodzenia> obecne = znajdzobecne(docel);
            List<Rodzajwynagrodzenia> wszytskiewynagrodzenia = rodzajwynagrodzeniaFacade.findAktywne();
            List<Rodzajwynagrodzenia> zrodlo = znajdzzrodlo(wszytskiewynagrodzenia, docel);
            picklista.setSource(zrodlo);
            picklista.setTarget(obecne);
            Msg.msg("Pobrano rodzaje wynagrodzeń");
        }
    }
    
    private List<Rodzajwynagrodzenia> znajdzobecne(List<RodzajlistyplacRodzajwynagrodzenia> docel) {
        List<Rodzajwynagrodzenia> zwrot = new ArrayList<>();
        if (docel!=null) {
            for (RodzajlistyplacRodzajwynagrodzenia p : docel) {
                zwrot.add(p.getRodzajwynagrodzenia());
            }
        }
        return zwrot;
    }
    
    private List<Rodzajwynagrodzenia> znajdzzrodlo(List<Rodzajwynagrodzenia> wszytskiewynagrodzenia, List<RodzajlistyplacRodzajwynagrodzenia> docel) {
        List<Rodzajwynagrodzenia> zwrot = new ArrayList<>();
        List<Rodzajwynagrodzenia> obecne = new ArrayList<>();
        for (RodzajlistyplacRodzajwynagrodzenia r : docel) {
            obecne.add(r.getRodzajwynagrodzenia());
        }
        if (docel!=null) {
            for (Rodzajwynagrodzenia p : wszytskiewynagrodzenia) {
                if (!obecne.contains(p)) {
                    zwrot.add(p);
                }
            }
        }
        return zwrot;
    }
 
    private List<RodzajlistyplacRodzajwynagrodzenia> zrobpozycje(Rodzajlistyplac rodzajlistyplacselected, List<Rodzajwynagrodzenia> wszytskiewynagrodzenia, List<Rodzajwynagrodzenia> obecne) {
        List<RodzajlistyplacRodzajwynagrodzenia> zwrot = new ArrayList<>();
        int i = 1;
        for (Rodzajwynagrodzenia p : wszytskiewynagrodzenia) {
            if (!obecne.contains(p)) {
                RodzajlistyplacRodzajwynagrodzenia nowy = new RodzajlistyplacRodzajwynagrodzenia();
                nowy.setRodzajlistyplac(rodzajlistyplacselected);
                nowy.setRodzajwynagrodzenia(p);
                nowy.setId(i++);
                zwrot.add(nowy);
            }
        }
        return zwrot;
    }    
    
   public void onTransfer(TransferEvent event) {
       if (event.isAdd()) {
           for (Object p : event.getItems()) {
               Rodzajwynagrodzenia rodz = (Rodzajwynagrodzenia) p;
                RodzajlistyplacRodzajwynagrodzenia nowy = new RodzajlistyplacRodzajwynagrodzenia();
                nowy.setRodzajlistyplac(rodzajlistyplacselected);
                nowy.setRodzajwynagrodzenia(rodz);
                rodzajllistyplacRodzajwynagrodzeniaFacade.create(nowy);
           }
           
       }
       if (event.isRemove()) {
           for (Object p : event.getItems()) {
               Rodzajwynagrodzenia rodz = (Rodzajwynagrodzenia) p;
               if (rodz.getId()!=null) {
                    rodzajllistyplacRodzajwynagrodzeniaFacade.usun(rodz);
               }
           }
           
       }
        Msg.msg("Zachowano zmiany");
   }

    public List<Rodzajlistyplac> getRodzajlistyplaclist() {
        return rodzajlistyplaclist;
    }

    public void setRodzajlistyplaclist(List<Rodzajlistyplac> rodzajlistyplaclist) {
        this.rodzajlistyplaclist = rodzajlistyplaclist;
    }

    public List<Rodzajwynagrodzenia> getRodzajwynagrodzenialist() {
        return rodzajwynagrodzenialist;
    }

    public void setRodzajwynagrodzenialist(List<Rodzajwynagrodzenia> rodzajwynagrodzenialist) {
        this.rodzajwynagrodzenialist = rodzajwynagrodzenialist;
    }

    public Rodzajlistyplac getRodzajlistyplacselected() {
        return rodzajlistyplacselected;
    }

    public void setRodzajlistyplacselected(Rodzajlistyplac rodzajlistyplacselected) {
        this.rodzajlistyplacselected = rodzajlistyplacselected;
    }

    public DualListModel<Rodzajwynagrodzenia> getPicklista() {
        return picklista;
    }

    public void setPicklista(DualListModel<Rodzajwynagrodzenia> picklista) {
        this.picklista = picklista;
    }

   
    
    
}
