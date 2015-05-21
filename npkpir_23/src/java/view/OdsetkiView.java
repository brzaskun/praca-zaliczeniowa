/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.OdsetkiDAO;
import entity.Odsetki;
import error.E;
import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class OdsetkiView implements Serializable{
    @Inject
    private OdsetkiDAO odsetkiDAO;
    @Inject
    private Odsetki selected;
    private List<Odsetki> lista;

    public OdsetkiView() {
        lista = new ArrayList<>();
    }
    
    @PostConstruct
    private void init(){
        lista.addAll(odsetkiDAO.findAll());
    }
    
    public void dodaj(){
        try{
         Format formatterX = new SimpleDateFormat("yyyy-MM-dd");
         String ndX = formatterX.format(selected.getDataodD());
         selected.setDataod(ndX);
        } catch (Exception e) { E.e(e); }
         if(sprawdz()==0){
         odsetkiDAO.dodaj(selected);
         lista.add(selected);
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dodatno odsetki od daty:", selected.getDataod() );
         FacesContext.getCurrentInstance().addMessage(":formzus:msgzus" , msg);
         } else {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niedodatno parametru odsetek. Sprawdz daty.", "");
         FacesContext.getCurrentInstance().addMessage(":formzus:msgzus", msg);
         }
    }
    
     private int sprawdz() {
        Odsetki ostatniparametr;
          if(lista.isEmpty()) {
            return 0;
        } else {
            ostatniparametr = lista.get(lista.size()-1);
            long x= ostatniparametr.getDataodD().getTime(); 
            long y= selected.getDataodD().getTime();    
            if(x>=y){
                return 1;
            }
           odsetkiDAO.destroy(ostatniparametr);
           lista.remove(ostatniparametr);
           ostatniparametr.setDatadoD(new Date(selected.getDataodD().getTime()-(1000*24*60*60)));
           Format formatterX = new SimpleDateFormat("yyyy-MM-dd");
           String ndX = formatterX.format(ostatniparametr.getDatadoD());
           ostatniparametr.setDatado(ndX);
           odsetkiDAO.dodaj(ostatniparametr);
           lista.add(ostatniparametr);
           return 0;
        }
     }
         
         
           
    
    public void usun(){
        int index = lista.size()-1;
        selected = lista.get(index);
        odsetkiDAO.destroy(selected);
        lista.remove(index);
        index = lista.size()-1;
        selected = lista.get(index);
        selected.setDatadoD(null);
        selected.setDatado(null);
        odsetkiDAO.edit(selected);
        lista.remove(index);
        lista.add(selected);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usunieto parametru odsetek:", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<Odsetki> getLista() {
        return lista;
    }

    public void setLista(List<Odsetki> lista) {
        this.lista = lista;
    }
    
    
    public Odsetki getSelected() {
        return selected;
    }

    public void setSelected(Odsetki selected) {
        this.selected = selected;
    }
    
    
}
