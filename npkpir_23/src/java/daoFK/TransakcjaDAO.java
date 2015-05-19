/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daoFK;

import dao.DAO;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Transakcja;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
@Stateless
public class TransakcjaDAO  extends DAO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private SessionFacade sessionFacade;

    public TransakcjaDAO(Class entityClass) {
        super(entityClass);
    }

    public TransakcjaDAO() {
        super(Transakcja.class);
    }
    
    public List<Transakcja> findAll() {
        return sessionFacade.findAll(Transakcja.class);
    }
    
    public void usunniezaksiegowane(String podatnik) {
        try {
            sessionFacade.usunTransakcjeNiezaksiegowane(podatnik);
        } catch (Exception e) { E.e(e); 
            
        }
    }
    
    //odnajduje istniejaca transakcje i ja edytuje. kluczem transakji jest boiem id a nie para sparowany/rozliczany
    public void edytujTransakcje(Transakcja transakcja) {
//        int rozliczany = transakcja.getRozliczany().getIdrozrachunku();
//        int sparowany = transakcja.getSparowany().getIdrozrachunku();
//        Transakcja poszukiwanaTransakcja = sessionFacade.findTransakcja(rozliczany,sparowany);
//        if (poszukiwanaTransakcja instanceof Transakcja) {
//            transakcja.setId(poszukiwanaTransakcja.getId());
//            sessionFacade.edit(transakcja);
//        } else {
//            sessionFacade.create(transakcja);
//        }
    }

    public List<Transakcja> findByRozliczonyID(int idrozrachunku) {
        return sessionFacade.findTransakcjeRozliczonyID(idrozrachunku);
    }
    
    public List<Transakcja> findBySparowanyID(int idrozrachunku) {
        return null;
//        List<Transakcja> odnalezione = new ArrayList<>();
//        List<Transakcja> transakcje = sessionFacade.findAll(Transakcja.class);
//        for(Transakcja f : transakcje) {
//            Map<Boolean, Rozrachunekfk> roz = f.getRozrachunki();
//            int idroz = ((Rozrachunekfk) roz.get(true)).getIdrozrachunku();
//            if (idroz == idrozrachunku && f.getKwotatransakcji() > 0) {
//                odnalezione.add(f);
//            }
//        }
//        return odnalezione;
    }
    
    public List<Transakcja> findByNowaTransakcja(StronaWiersza s) {
        return sessionFacade.findByNowaTransakcja(s);
    }
    
    public List<Transakcja> findByRozliczajacy(StronaWiersza s) {
        return sessionFacade.findByRozliczajacy(s);
    }

    public List<Transakcja> findByKonto(Konto wybraneKontoNode) {
        return sessionFacade.findByKonto(wybraneKontoNode);
    }

    public List<Transakcja> findPodatnikRok(WpisView wpisView) {
        return sessionFacade.findByPodatniRok(wpisView);
    }
    
    public List<Transakcja> findPodatnikBO(WpisView wpisView) {
        return sessionFacade.findByPodatnikBO(wpisView);
    }
    
    public List<Transakcja> findPodatnikRokRozniceKursowe(WpisView wpisView) {
        return sessionFacade.findByPodatniRokRozniceKursowe(wpisView);
    }
    
    public List<Transakcja> findPodatnikBORozniceKursowe(WpisView wpisView) {
        return sessionFacade.findByPodatnikBORozniceKursowe(wpisView);
    }
}
