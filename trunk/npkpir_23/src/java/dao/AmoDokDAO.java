/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import data.Data;
import entity.Amodok;
import java.io.Serializable;
import java.util.ArrayList;
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
    
    public  List<Amodok> findAll(){
        try {
            System.out.println("Pobieram AmodokDAO");
            return amodokFacade.findAll(Amodok.class);
        } catch (Exception e) {
            return null;
        }
   }
    
     public  Amodok findMR(String pod, Integer rok, String mc){
        try {
            System.out.println("Pobieram AmodokDAO za mc i rok");
            return amodokFacade.findMR(pod,rok,mc);
        } catch (Exception e) {
            return null;
        }
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
    
     public void destroy(String podatnik, int rok, int mc){
        List<Amodok> lista = amodokFacade.findAmodok(podatnik);
        for(Amodok tmp : lista){
            int wynikporownywania = Data.compare(tmp.getAmodokPK().getRok(), tmp.getAmodokPK().getMc(), rok, mc);
            if(wynikporownywania >= 0) {
                amodokFacade.remove(tmp);
            }
        }
    }
    
    public List<Amodok> amodokklient(String klient){
        return amodokFacade.findPod(klient);
    }
}
