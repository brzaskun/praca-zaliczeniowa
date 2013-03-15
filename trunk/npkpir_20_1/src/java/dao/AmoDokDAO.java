/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

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
    
    public List<Amodok> amodokklient(String klient){
        List<Amodok> lista = new ArrayList<>();
        for (Object p : amodokFacade.findAll(Amodok.class)){
            if(((Amodok) p).getPodatnik().equals(klient)){
                lista.add((Amodok) p);
            }
        }
        return lista;
    }
}
