/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daoFK;

import dao.DAO;
import entity.Podatnik;
import entityfk.Cechazapisu;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named

public class CechazapisuDAOfk extends DAO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Inject private SessionFacade sessionFacade;

    public CechazapisuDAOfk() {
        super(Cechazapisu.class);
    }
    
    
    public List<Cechazapisu> findAll() {
        return sessionFacade.findAll(Cechazapisu.class);
    }
    
    public List<Cechazapisu> findPodatnikOnly(Podatnik podatnikObiekt) {
        return sessionFacade.findCechaZapisuByPodatnikOnly(podatnikObiekt);
    }
    
    public List<Cechazapisu> findPodatnikOnlyAktywne(Podatnik podatnikObiekt) {
        return sessionFacade.findCechaZapisuByPodatnikOnlyAktywne(podatnikObiekt);
    }
    
    public List<Cechazapisu> findPodatnikOnlyStatystyczne(Podatnik podatnikObiekt) {
        return sessionFacade.findCechaZapisuByPodatnikOnlyStatystyczne(podatnikObiekt);
    }

    public List<Cechazapisu> findPodatnik(Podatnik podatnikObiekt) {
        return sessionFacade.findCechaZapisuByPodatnik(podatnikObiekt);
    }

    public Cechazapisu findPodatniknkup() {
        return sessionFacade.findCechaZapisuByPodatnikNKUP();
    }

    
}
