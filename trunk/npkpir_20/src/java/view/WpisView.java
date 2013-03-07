/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.UzDAO;
import dao.WpisDAO;
import entity.Uz;
import entity.Wpis;
import java.io.Serializable;
import java.security.Principal;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Osito
 */
@ManagedBean(name="WpisView")
@RequestScoped
public class WpisView implements Serializable{

    private String podatnikWpisu;
    private Integer rokWpisu;
    private String miesiacWpisu;
    @Inject private Uz wprowadzil;
    private String miesiacOd;
    private String miesiacDo;
    private boolean srodkTrw;
    
    @Inject private Wpis wpis;
    @Inject private WpisDAO wpisDAO;
    @Inject private UzDAO uzDAO;


       
    @PostConstruct
    private void init(){
        if(miesiacDo==null&&miesiacWpisu==null){
            miesiacDo = miesiacWpisu;
            miesiacOd = miesiacWpisu;
        }
        HttpServletRequest request;
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Principal principal = request.getUserPrincipal();
        String wprowadzilX = principal.getName();
        if(wprowadzilX!=null){
        wprowadzil = uzDAO.find(wprowadzilX);
        wpis = wpisDAO.find(wprowadzilX);
        this.podatnikWpisu = wpis.getPodatnikWpisu();
        if(wpis.getPodatnikWpisu()==null){
            this.miesiacWpisu = "GRZELCZYK";
            wpis.setPodatnikWpisu("GRZELCZYK");
        } else {
            this.miesiacWpisu = wpis.getMiesiacWpisu();
        }
        this.rokWpisu = wpis.getRokWpisu();
        try {
            if(miesiacOd==null){
                this.miesiacOd = wpis.getMiesiacOd();
                this.miesiacDo = wpis.getMiesiacDo();
            }
        } catch (Exception e){
        this.miesiacOd = wpis.getMiesiacOd();
        this.miesiacDo = wpis.getMiesiacDo();
        }
    }
    }

    
    public void wpisAktualizuj(){
        findWpis();
    }
    
    public void findWpis(){
        HttpServletRequest request;
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Principal principal = request.getUserPrincipal();
        String wprowadzilX = principal.getName();
        wprowadzil = uzDAO.find(wprowadzilX);
        wpis = wpisDAO.find(wprowadzilX);
        wpis.setPodatnikWpisu(podatnikWpisu);
        wpis.setMiesiacWpisu(miesiacWpisu);
        wpis.setRokWpisu(rokWpisu);
        wpis.setMiesiacOd(miesiacOd);
        wpis.setMiesiacDo(miesiacDo);
        wpisDAO.edit(wpis);
        HttpSession sessionX = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        sessionX.setAttribute("miesiacWpisu", miesiacWpisu);
        sessionX.setAttribute("podatnikWpisu", podatnikWpisu);
        sessionX.setAttribute("rokWpisu", rokWpisu);
        sessionX.setAttribute("wprowadzil", wprowadzil);
    }

      public String findNazwaPodatnika(){
        HttpServletRequest request;
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Principal principal = request.getUserPrincipal();
        String wprowadzilX = principal.getName();
        wprowadzil = uzDAO.find(wprowadzilX);
        wpis = wpisDAO.find(wprowadzilX);
        return wpis.getPodatnikWpisu();
    }
    
     public Wpis findWpisX(){
     HttpServletRequest request;
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Principal principal = request.getUserPrincipal();
        String wprowadzilX = principal.getName();
        wprowadzil = uzDAO.find(wprowadzilX);
        return wpisDAO.find(wprowadzilX);
     
    }    
      
      
    public String getPodatnikWpisu() {
        return podatnikWpisu;
    }

    public void setPodatnikWpisu(String podatnikWpisu) {
        this.podatnikWpisu = podatnikWpisu;
    }

    public Integer getRokWpisu() {
        return rokWpisu;
    }

    public void setRokWpisu(Integer rokWpisu) {
        this.rokWpisu = rokWpisu;
    }

    public String getMiesiacWpisu() {
        return miesiacWpisu;
    }

    public void setMiesiacWpisu(String miesiacWpisu) {
        this.miesiacWpisu = miesiacWpisu;
    }

    public Uz getWprowadzil() {
        return wprowadzil;
    }

    public void setWprowadzil(Uz wprowadzil) {
        this.wprowadzil = wprowadzil;
    }

    public String getMiesiacOd() {
        return miesiacOd;
    }

    public void setMiesiacOd(String miesiacOd) {
        this.miesiacOd = miesiacOd;
    }

    public String getMiesiacDo() {
        return miesiacDo;
    }

    public void setMiesiacDo(String miesiacDo) {
        this.miesiacDo = miesiacDo;
    }

    public boolean isSrodkTrw() {
        return srodkTrw;
    }

    public void setSrodkTrw(boolean srodkTrw) {
        this.srodkTrw = srodkTrw;
    }

    public Wpis getWpis() {
        return wpis;
    }

    public void setWpis(Wpis wpis) {
        this.wpis = wpis;
    }

    public WpisDAO getWpisDAO() {
        return wpisDAO;
    }

    public void setWpisDAO(WpisDAO wpisDAO) {
        this.wpisDAO = wpisDAO;
    }

    
   
}
