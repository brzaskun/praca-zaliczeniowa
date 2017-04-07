/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.PodatnikOpodatkowanieD;
import entity.PodatnikUdzialy;
import error.E;
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
    
    public PodatnikOpodatkowanieD findOpodatkowaniePodatnikRokPoprzedni(WpisView wpisView) {
        try {
            return sessionFacade.findOpodatkowaniePodatnikRokPoprzedni(wpisView);
        } catch (Exception e) {
            E.e(e);
        }
        return null;
    }
    
}
