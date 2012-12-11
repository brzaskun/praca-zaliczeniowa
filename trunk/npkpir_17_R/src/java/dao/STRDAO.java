/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.SrodekTrw;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;


/**
 *
 * @author Osito
 */
@Named
public class STRDAO implements Serializable{
   
    @Inject
    private SessionFacade strFacade;
    //tablica wciagnieta z bazy danych
    
    private List<SrodekTrw> downloadedSTR;

    public STRDAO() {
        downloadedSTR = new ArrayList<SrodekTrw>();
    }
    
    

    public List<SrodekTrw> getdownloadedSTR() {
        return downloadedSTR;
    }

    public void setdownloadedSTR(List<SrodekTrw> downloadedSTR) {
        this.downloadedSTR = downloadedSTR;
    }

    @PostConstruct
    public void init(){
        Collection c = null;
        try {
            c = strFacade.findAll(SrodekTrw.class);
        } catch (Exception e) {
            System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + e.toString());
        }
        if(c.size()>0){
        downloadedSTR.addAll(c);
        System.out.println("Pobrano z bazy danych." + c.toString());
        }
    }
           
   
//    
//    public SrodekTrw znajdzDuplikat(SrodekTrw selD) throws Exception{
//        SrodekTrw tmp = null;
//        tmp = strFacade.duplicat(selD);
//        return tmp;
//        }
   
    
     public void dodajNowyWpis(SrodekTrw selectedSTR){
        try {
            strFacade.create(selectedSTR);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Nowy srodek trwaly zachowany", selectedSTR.getNazwa().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println("Nie utworzono srodka "+e.toString());
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"SrodekTrwaly nie uwtorzony DAO"+e.toString(),"");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            
        }
    }
    
    public void destroy(SrodekTrw selectedSTR) {
        try {
        strFacade.remove(selectedSTR);
        } catch (Exception e) {
            System.out.println("Nie usnieto "+selectedSTR.getNazwa()+" "+e.toString());
        }
    }
        
     
    public void edit(SrodekTrw selectedSTR){
        try {
            strFacade.edit(selectedSTR);
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
        }
     }
    
    
    
    
}
