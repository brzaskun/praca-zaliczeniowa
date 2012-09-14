/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entity.Dok;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import session.DokFacade;

/**
 *
 * @author Osito
 */
@ManagedBean(name="DokumentView")
@SessionScoped
public class DokView implements Serializable{
    @EJB
    private DokFacade dokFacade;
    private Dok dokument;
    private Dok selDokument;
    private List<Dok> dokumentLista;
    private Map<String,Dok> dokumentNrDok;
    private boolean  dodajDok;
    private boolean edytujDok;
    

    public DokView() {
        dokument = new Dok();
        selDokument = new Dok();
        dokumentLista = new ArrayList<Dok>();
        dokumentNrDok = new HashMap<String, Dok>();
        setDodajDok(false);
        setEdytujDok(false);
    }

    public DokView(DokFacade dokFacade, Dok dokument, Dok selDokument, List<Dok> dokumentLista) {
        this.dokFacade = dokFacade;
        this.dokument = dokument;
        this.selDokument = selDokument;
        this.dokumentLista = dokumentLista;
    }

    public DokFacade getDokFacade() {
        return dokFacade;
    }

    public void setDokFacade(DokFacade dokFacade) {
        this.dokFacade = dokFacade;
    }

    public Dok getDokument() {
        return dokument;
    }

    public void setDokument(Dok dokument) {
        this.dokument = dokument;
    }

    public Dok getSelDokument() {
        return selDokument;
    }

    public void setSelDokument(Dok selDokument) {
        this.selDokument = selDokument;
    }

    public boolean isDodajDok() {
        return dodajDok;
    }

    public void setDodajDok(boolean dodajDok) {
        this.dodajDok = dodajDok;
    }

    public boolean isEdytujDok() {
        return edytujDok;
    }

    public void setEdytujDok(boolean edytujDok) {
        this.edytujDok = edytujDok;
    }

    
    public List<Dok> getDokumentLista() {
          if (dokumentLista.isEmpty()) {
            Collection c;
            try {
//                if(wybranaMarka!=null){ c = (Collection) carsFacade.findbyMan(wybranaMarka);} else { c = (Collection) carsFacade.findAll();}
                c = (Collection) dokFacade.findAll();
                FacesMessage msg = new FacesMessage("Zapelnianie DokumentLista poszlo ok");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (Exception notfound) {
                FacesMessage msg = new FacesMessage("Zapelnianie poszlo nie tak jak trzeba", notfound.toString());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return null;
            }
            dokumentLista.addAll(c);
        }
        return dokumentLista;
    }

    public void setDokumentLista(List<Dok> dokumentLista) {
        this.dokumentLista = dokumentLista;
    }

    public Map<String, Dok> getDokumentNrDok() {
        if(dokumentNrDok.isEmpty()==false){
          dokumentNrDok.clear();
            List<Dok> c = getDokumentLista();
            for(int i=0;i<c.size();i++){
            dokumentNrDok.put(c.get(i).getNrWlDk(),c.get(i));
            }
            FacesMessage msg = new FacesMessage("Lista haszowana Dok zrobiona",dokumentNrDok.toString());
                FacesContext.getCurrentInstance().addMessage(null, msg);
        }
            return dokumentNrDok;
    }       

    public void setDokumentNrDok(Map<String, Dok> dokumentNrDok) {
        this.dokumentNrDok = dokumentNrDok;
    }
    
     public Dok returnDokObject(String wybrano){
       Dok dokObject;
       dokObject = getDokumentNrDok().get(wybrano);
       return dokObject;
   }
    
}
