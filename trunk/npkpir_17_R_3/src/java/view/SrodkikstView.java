/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.SrodkikstDAO;
import entity.Srodkikst;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScope
public class SrodkikstView implements Serializable {
    private static List<Srodkikst> lista;
    @Inject private SrodkikstDAO srodkikstDAO;

    public SrodkikstView() {
        lista = new ArrayList<>();
    }
    
    @PostConstruct
    private void init(){
        lista.addAll(srodkikstDAO.getDownloaded());
    }
    
     public List<Srodkikst> complete(String query) {  
        List<Srodkikst> results = new ArrayList<>();  
         for(Srodkikst p : lista) {  
            if(p.getNazwa().contains(query)) {
                 results.add(p);
             }
        }  
        return results;  
    }  
     
    public void usun(){
        Iterator it;
        it = lista.iterator();
        while(it.hasNext()){
            Srodkikst tmp = (Srodkikst) it.next();
            Srodkikst tmpX = new Srodkikst();
            tmpX.setId(tmp.getId());
            tmpX.setNazwa(tmp.getNazwa().trim());
            tmpX.setSymbol(tmp.getSymbol().trim());
            String s = tmp.getStawka().trim();
            s = s.replace(",", ".");
            tmpX.setStawka(s);
            srodkikstDAO.edit(tmpX);
        }
    } 

     public void maly(){
        Iterator it;
        it = lista.iterator();
        while(it.hasNext()){
            Srodkikst tmp = (Srodkikst) it.next();
            Srodkikst tmpX = new Srodkikst();
            tmpX.setId(tmp.getId());
            tmpX.setNazwa(tmp.getNazwa().toLowerCase());
            tmpX.setSymbol(tmp.getSymbol());
            tmpX.setStawka(tmp.getStawka());
            srodkikstDAO.edit(tmpX);
        }
    } 
    
    public List<Srodkikst> getLista() {
        return lista;
    }

    public void setLista(List<Srodkikst> lista) {
        this.lista = lista;
    }
    
    public static List<Srodkikst> getListaS() {
        return lista;
    }
    
}
