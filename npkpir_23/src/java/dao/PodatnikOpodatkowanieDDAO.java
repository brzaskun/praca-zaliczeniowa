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
public class PodatnikOpodatkowanieDDAO extends DAO implements Serializable{
    @Inject
    private SessionFacade sessionFacade;

    public PodatnikOpodatkowanieDDAO() {
        super(PodatnikOpodatkowanieD.class);
    }

    public List<PodatnikOpodatkowanieD> findOpodatkowaniePodatnik(WpisView wpisView) {
        return sessionFacade.findOpodatkowaniePodatnik(wpisView);
    }
    
    public PodatnikOpodatkowanieD findOpodatkowaniePodatnikRok(WpisView wpisView) {
        return sessionFacade.findOpodatkowaniePodatnikRok(wpisView);
    }
    
    public PodatnikOpodatkowanieD findOpodatkowaniePodatnikRok(Podatnik p, String rok) {
        return sessionFacade.findOpodatkowaniePodatnikRok(p, rok);
    }
    
    public PodatnikOpodatkowanieD findOpodatkowaniePodatnikRokPoprzedni(WpisView wpisView) {
        PodatnikOpodatkowanieD zwrot = null;
        try {
            zwrot = sessionFacade.findOpodatkowaniePodatnikRokPoprzedni(wpisView);
        } catch (Exception e) {
        }
        return zwrot;
    }

    public List<PodatnikOpodatkowanieD> findAll() {
        return sessionFacade.findAll(PodatnikOpodatkowanieD.class);
    }
    
}
