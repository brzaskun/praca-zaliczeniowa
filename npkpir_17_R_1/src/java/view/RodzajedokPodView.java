/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PodatnikDAO;
import dao.RodzajedokDAO;
import embeddable.Rozrachunek;
import entity.Rodzajedok;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class RodzajedokPodView implements Serializable{
    @Inject private RodzajedokDAO rodzajedokDAO;
    @Inject private PodatnikDAO podatnikDAO;
    @Inject private Rodzajedok selected;
    private static HashMap<String, String> rodzajedokMap;
    
    private List<Rodzajedok> lista;

    public RodzajedokPodView() {
        lista = new ArrayList<>();
        rodzajedokMap = new HashMap<>();
       
    }

    @PostConstruct
    private void init(){
        Collection c = null;
        try{
        c = podatnikDAO.find(WpisView.getPodatnikWpisuS()).getDokumenty();
        if(c!=null){
        lista.addAll(c);
        }
        c = rodzajedokDAO.getDownloaded();
        Iterator it;
        it = c.iterator();
        while(it.hasNext()){
            Rodzajedok tmp = (Rodzajedok) it.next();
            if(!lista.contains(tmp)){
                lista.add(tmp);
            }
        }
        it = lista.iterator();
        while(it.hasNext()){
            Rodzajedok tmp = (Rodzajedok) it.next();
            rodzajedokMap.put(tmp.getSkrot(), tmp.getRodzajtransakcji());
        }
        } catch (Exception e){}
       
    }
    
     public void dodaj(Rodzajedok o){
         try{
         Object tmp = o;
         dokoncz
         //rodzajedokDAO.dodaj(selected);
         //lista.add(selected);
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dodatno nowy rodzaj dokumentu:", "");
         FacesContext.getCurrentInstance().addMessage("form:messages" , msg);
       
         } catch (Exception ex) {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niedodatno nowego rodzaju dokumentu. Sprawdz czy skrót się nie powtarza.", "");
         FacesContext.getCurrentInstance().addMessage("form:messages", msg);
       
         }
        
     }

    public List<Rodzajedok> getLista() {
        return lista;
    }

    public void setLista(List<Rodzajedok> lista) {
        this.lista = lista;
    }

    public Rodzajedok getSelected() {
        return selected;
    }

    public void setSelected(Rodzajedok selected) {
        this.selected = selected;
    }

    public HashMap<String, String> getRodzajedokMap() {
        return rodzajedokMap;
    }

    public void setRodzajedokMap(HashMap<String, String> rodzajedokMap) {
        RodzajedokPodView.rodzajedokMap = rodzajedokMap;
    }
    
     public static HashMap<String, String> getRodzajedokMapS() {
        return rodzajedokMap;
    } 
}
