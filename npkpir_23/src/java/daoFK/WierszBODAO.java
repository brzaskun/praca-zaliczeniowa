/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daoFK;

import dao.DAO;
import entity.Podatnik;
import entityfk.Konto;
import entityfk.WierszBO;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;
import view.WpisView;
/**
 *
 * @author Osito
 */
@Named

public class WierszBODAO extends DAO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private SessionFacade wierszBOFacade;
    
    public List<WierszBO> lista(String grupa, WpisView wpisView, boolean likwidacja) {
        try {
            if (likwidacja) {
                return wierszBOFacade.findBOLista0likwidacja(grupa, wpisView);
            } else {
                return wierszBOFacade.findBOLista0(grupa, wpisView);
            }
        } catch (Exception e) { E.e(e); 
            return Collections.synchronizedList(new ArrayList<>());
        }
    }
    
    
    
    public List<WierszBO> listaRokMc(WpisView wpisView) {
        try {
            return wierszBOFacade.findBOListaRokMc(wpisView);
        } catch (Exception e) { E.e(e); 
            return Collections.synchronizedList(new ArrayList<>());
        }
    }
    
    public List<WierszBO> findAll() {
        return wierszBOFacade.findAll(WierszBO.class);
    }
    
    public List<WierszBO> findPodatnikRok(Podatnik podatnik, String rok) {
        return wierszBOFacade.findWierszBOPodatnikRok(podatnik, rok);
    }
    
    public int deletePodatnikRok(Podatnik podatnik, String rok) {
        return wierszBOFacade.deleteWierszBOPodatnikRok(podatnik, rok);
    }
    
    public int deletePodatnikRokMc(Podatnik podatnik, String rok, String mc) {
        return wierszBOFacade.deleteWierszBOPodatnikRokMc(podatnik, rok, mc);
    }
    
    public List<WierszBO> findPodatnikRokRozrachunkowe(Podatnik podatnik, String rok) {
        return wierszBOFacade.findWierszBOPodatnikRokRozrachunkowe(podatnik, rok);
    }
    //jets lista bo w BO moze byc klika wierszy z tym samym kontem
    public List<WierszBO> findPodatnikRokKonto(Podatnik podatnikObiekt, String rokWpisuSt, Konto konto) {
        return wierszBOFacade.findWierszBOPodatnikRokKonto(podatnikObiekt, rokWpisuSt, konto);
    }
    
    public List<WierszBO> findPodatnikRokKontoWaluta(Podatnik podatnikObiekt, String rokWpisuSt, Konto konto, String waluta) {
        return wierszBOFacade.findWierszBOPodatnikRokKontoWaluta(podatnikObiekt, rokWpisuSt, konto, waluta);
    }
}
