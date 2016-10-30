/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.StowNaliczenie;
import java.io.Serializable;
import java.util.List;
import view.WpisView;

/**
 *
 * @author Osito
 */
public class StowNaliczenieDAO  extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;

    public StowNaliczenieDAO() {
        super(StowNaliczenie.class);
    }
    
    public StowNaliczenieDAO(Class entityClass) {
        super(entityClass);
    }
    

    public List<StowNaliczenie> findAll() {
        return sessionFacade.findAll(StowNaliczenie.class);
    }

    public void usunnaliczeniemc(WpisView wpisView, String kategoria) {
         sessionFacade.getEntityManager().createNamedQuery("StowNaliczenie.DeleteNaliczoneMcRok").setParameter("podatnikObj",wpisView.getPodatnikObiekt()).setParameter("rok",wpisView.getRokWpisuSt()).setParameter("mc",wpisView.getMiesiacWpisu()).setParameter("kategoria",kategoria).executeUpdate();
    }
    
}
