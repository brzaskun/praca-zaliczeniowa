/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entity.Podatnik;
import entityfk.Konto;
import entityfk.KontopozycjaZapis;
import entityfk.UkladBR;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named

public class KontopozycjaZapisDAO extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Inject
    private SessionFacade sessionFacade;

    public KontopozycjaZapisDAO() {
        super(KontopozycjaZapis.class);
    }

    public KontopozycjaZapisDAO(Class entityClass) {
        super(entityClass);
    }
    
    
    public List<KontopozycjaZapis> findKontaPozycjaZapisPodatnikUklad (UkladBR uklad, String rb) {
       try {
            return sessionFacade.findKontaZapisPodatnikUklad(uklad, rb);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
//    public List<KontopozycjaZapis> findKontaPozycjaZapisPodatnikUkladWzorzec (UkladBR uklad, String rb) {
//       try {
//            return sessionFacade.findKontaZapisPodatnikUkladWzorzec(uklad, rb);
//        } catch (Exception e) { E.e(e); 
//            return null;
//        }
//    }
    
    public void usunZapisaneKontoPozycjaPodatnikUklad(UkladBR uklad, String rb) {
        try {
            sessionFacade.usunZapisaneKontoPozycjaPodatnikUklad(uklad, rb);
        } catch (Exception e) {
            E.e(e); 
        }
    }
    
     public void usunKontoPozycjaPodatnikUladKonto(UkladBR wybranyuklad, List<Konto> wykazkontf) {
        if (wykazkontf!=null) {
            for (Konto p : wykazkontf) {
                sessionFacade.usunZapisaneKontoPozycjaPodatnikUkladByKonto(wybranyuklad, p);
            }
        }
    }
    
        
    public KontopozycjaZapis findByKonto(Konto konto, UkladBR ukladBR) {
        KontopozycjaZapis kontopozycjaZapis = null;
        try {
            kontopozycjaZapis = sessionFacade.fintKontoPozycjaZapisByKonto(konto, ukladBR);
        } catch (Exception e) {
            E.e(e);
        }
        return kontopozycjaZapis;
    }

    public List<KontopozycjaZapis> findByKontoOnly(Konto konto) {
        List<KontopozycjaZapis> kontopozycjaZapis = Collections.synchronizedList(new ArrayList<>());
        try {
            kontopozycjaZapis = sessionFacade.findByKontoOnly(konto);
        } catch (Exception e) {
            E.e(e);
        }
        return kontopozycjaZapis;
    }

    public List<KontopozycjaZapis> findKontaPozycjaZapisPodatnikRok(Podatnik podatnik, String rok) {
        try {
            return sessionFacade.findKontaPozycjaZapisPodatnikRok(podatnik, rok);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
}
