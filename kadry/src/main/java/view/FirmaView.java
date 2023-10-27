/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.FirmaKadrycomparator;
import dao.FirmaKadryFacade;
import dao.PasekwynagrodzenFacade;
import entity.FirmaKadry;
import entity.Pasekwynagrodzen;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@SessionScoped
public class FirmaView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private FirmaKadryFacade firmaKadryFacade;
    @Inject
    private PasekwynagrodzenFacade pasekwynagrodzenFacade;
    private List<FirmaKadry> lista;
    private FirmaKadry selectedeast;
    @Inject
    private WpisView wpisView;
    private boolean ukryj;
    
    @PostConstruct
    public void init() {
        lista  = firmaKadryFacade.findAll();
        if (!wpisView.getUzer().getLogin().equals("mariola")&&!wpisView.getUzer().getLogin().equals("2")) {
            for (Iterator<FirmaKadry> it = lista.iterator();it.hasNext();) {
                FirmaKadry f = it.next();
                if (f.getNip().equals("8511005008")) {
                    it.remove();
                    break;
                }
            }
        }
        if (ukryj) {
            List<Pasekwynagrodzen> paski = pasekwynagrodzenFacade.findByRokMc(wpisView.getOkreswpisupoprzedni().getRok(), wpisView.getOkreswpisupoprzedni().getMc());
            Set<FirmaKadry> firmy = new HashSet<>();
            for (Iterator<Pasekwynagrodzen> it = paski.iterator();it.hasNext();) {
                Pasekwynagrodzen p = it.next();
                if (p.getSporzadzil().equals(wpisView.getUzer().getLogin())||p.getSporzadzil().equals(wpisView.getUzer().getImieNazwisko())) {
                    firmy.add(p.getAngaz().getFirma());
                }
            }
            for (Iterator<FirmaKadry> it = lista.iterator();it.hasNext();) {
                FirmaKadry f = it.next();
                if (firmy.contains(f)==false) {
                    it.remove();
                }
            }
        }
        Collections.sort(lista,new FirmaKadrycomparator());
        if (wpisView.getFirma()!=null) {
            selectedeast = wpisView.getFirma();
        }
    }

    public boolean isUkryj() {
        return ukryj;
    }

    public void setUkryj(boolean ukryj) {
        this.ukryj = ukryj;
    }
    
    

    public List<FirmaKadry> getLista() {
        return lista;
    }

    public void setLista(List<FirmaKadry> lista) {
        this.lista = lista;
    }

    public FirmaKadry getSelectedeast() {
        return selectedeast;
    }

    public void setSelectedeast(FirmaKadry selectedeast) {
        this.selectedeast = selectedeast;
    }
    
    
    
}
