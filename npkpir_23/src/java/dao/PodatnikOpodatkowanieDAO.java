/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Podatnik;
import entity.PodatnikOpodatkowanieD;
import java.io.Serializable;
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
public class PodatnikOpodatkowanieDAO extends DAO implements Serializable{
    @Inject
    private SessionFacade sessionFacade;

    public PodatnikOpodatkowanieDAO() {
        super(PodatnikOpodatkowanieD.class);
    }

    public List<PodatnikOpodatkowanieD> findOpodatkowaniePodatnik(WpisView wpisView) {
        return sessionFacade.findOpodatkowaniePodatnik(wpisView);
    }
    
    public List<PodatnikOpodatkowanieD> findOpodatkowaniePodatnikRokWiele(Podatnik p, String rok) {
        return sessionFacade.findOpodatkowaniePodatnikRokWiele(p, rok);
    }
    
    
    public PodatnikOpodatkowanieD findOpodatkowaniePodatnikRok(Podatnik p, String rok) {
        PodatnikOpodatkowanieD zwrot = null;
        try {
            zwrot = (PodatnikOpodatkowanieD) sessionFacade.getEntityManager().createNamedQuery("PodatnikOpodatkowanieD.findBypodatnikRok").setParameter("podatnik", p).setParameter("rok", rok).getSingleResult();
        } catch(Exception e) {
            
        }
        return zwrot;
    }
    

    public List<PodatnikOpodatkowanieD> findAll() {
        return sessionFacade.findAll(PodatnikOpodatkowanieD.class);
    }
    
}
