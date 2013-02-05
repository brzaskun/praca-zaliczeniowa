/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Amodok;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
public class AmoDokDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade amodokFacade;

    public AmoDokDAO() {
        super(Amodok.class);
    }
    
    public void destroy(String podatnik){
        List<Amodok> lista = amodokFacade.findAmodok(podatnik);
        Iterator it;
        it = lista.iterator();
        while(it.hasNext()){
            Amodok tmp = (Amodok) it.next();
            amodokFacade.remove(tmp);
        }
    }
}
