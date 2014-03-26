/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Pozycjenafakturze;
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
public class PozycjenafakturzeDAO  extends DAO implements Serializable {
    @Inject
    private SessionFacade pozycjeSession;

    public PozycjenafakturzeDAO() {
        super(Pozycjenafakturze.class);
    }
    
    public List<Pozycjenafakturze> findAll(){
                return pozycjeSession.findAll(Pozycjenafakturze.class);
    }
    
    /**
     *
     * @param podatnik
     * @return
     */
    public List<Pozycjenafakturze> findFakturyPodatnik(String podatnik){
        return pozycjeSession.findFakturyPodatnik(podatnik);
    }
}
