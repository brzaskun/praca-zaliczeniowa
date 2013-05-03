/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entityfk.Konto;
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
public class KontoDAO extends DAO implements Serializable{
    @Inject
    private SessionFacade kontoFacade;

    public KontoDAO() {
        super(Konto.class);
    }

    public  List<Konto> findAll(){
        try {
            System.out.println("Pobieram KontoDAO");
            return kontoFacade.findAll(Konto.class);
        } catch (Exception e) {
            return null;
        }
   }
}
