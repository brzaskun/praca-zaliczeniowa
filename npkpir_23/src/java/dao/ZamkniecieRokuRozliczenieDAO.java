/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.ZamkniecieRokuRozliczenie;
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
public class ZamkniecieRokuRozliczenieDAO extends DAO implements Serializable {
    @Inject
    private SessionFacade wpisFacade;
    
    public ZamkniecieRokuRozliczenieDAO(){
        super(ZamkniecieRokuRozliczenie.class);
    }

    
    public  List<ZamkniecieRokuRozliczenie> findAll(){
        try {
            return wpisFacade.findAll(ZamkniecieRokuRozliczenie.class);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
   }

    public List<ZamkniecieRokuRozliczenie> findByRokPodatnik(WpisView wpisView) {
        return wpisFacade.findZakmniecieRokuByRokPodatnik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
    }
}
