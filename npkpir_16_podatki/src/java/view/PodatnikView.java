/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PodatnikDAO;
import entity.Podatnik;
import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import mail.Mail;
import org.primefaces.component.panelgrid.PanelGrid;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class PodatnikView implements Serializable{
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private Podatnik selected;
    private String pojemnik;
    private List<String> pojList;
    private PanelGrid grid;
    private String[] listka;
    private List<String> listkakopia;
    
  
    public PodatnikView() {
        listka = new String[50] ;
        listka[0]="zero";
        listka[1]="jeden";
        listka[2]="dwa";
    }

    public List<String> getListkakopia() {
        return listkakopia;
    }

    public void setListkakopia(List<String> listkakopia) {
        this.listkakopia = listkakopia;
    }

    
    
    public String[] getListka() {
        return listka;
    }

    public void setListka(String[] listka) {
        this.listka = listka;
    }

   

   
    public List<String> getPojList() {
        return pojList;
    }

    public void setPojList(List<String> pojList) {
        this.pojList = pojList;
    }
    
    
    public PanelGrid getGrid() {
        return grid;
    }

    public void setGrid(PanelGrid grid) {
        this.grid = grid;
    }

    

    public String getPojemnik() {
        return pojemnik;
    }

    public void setPojemnik(String pojemnik) {
        this.pojemnik = pojemnik;
    }

    public Podatnik getSelected() {
        return selected;
    }

    public void setSelected(Podatnik selected) {
        this.selected = selected;
    }
    
     public void dodajNowyWpis(){
         System.out.println("Wpis do bazy zaczynam");
         sformatuj();
             try {
                 podatnikDAO.dodajNowyWpis(selected);
                 FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dodatno nowego podatnika.", selected.getNazwapelna());
                 FacesContext.getCurrentInstance().addMessage(null, msg);
                 Mail.nadajMail(selected.getEmail(), selected.getNazwapelna());
                 
             } catch (Exception e) {
                 System.out.println(e.getStackTrace().toString());
                 FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Uzytkownik o takim NIP już istnieje.", e.getStackTrace().toString());
                 FacesContext.getCurrentInstance().addMessage(null, msg);
             }
    }
   
    public void sformatuj(){
        String formatka=null;
        selected.setNazwapelna(selected.getNazwapelna().toUpperCase());
        selected.setWojewodztwo(selected.getWojewodztwo().substring(0,1).toUpperCase()+selected.getWojewodztwo().substring(1).toLowerCase());
        selected.setGmina(selected.getGmina().substring(0,1).toUpperCase()+selected.getGmina().substring(1).toLowerCase());
        selected.setUlica(selected.getUlica().substring(0,1).toUpperCase()+selected.getUlica().substring(1).toLowerCase());
        selected.setPowiat(selected.getPowiat().substring(0,1).toUpperCase()+selected.getPowiat().substring(1).toLowerCase());
        selected.setMiejscowosc(selected.getMiejscowosc().substring(0,1).toUpperCase()+selected.getMiejscowosc().substring(1).toLowerCase());
        selected.setPoczta(selected.getPoczta().substring(0,1).toUpperCase()+selected.getPoczta().substring(1).toLowerCase());
        selected.setImie(selected.getImie().substring(0,1).toUpperCase()+selected.getImie().substring(1).toLowerCase());
        selected.setNazwisko(selected.getNazwisko().substring(0,1).toUpperCase()+selected.getNazwisko().substring(1).toLowerCase());
        
    }
    
    public void dodajrzad(ActionEvent e){
        UIComponent wywolaneprzez = (UIComponent) e.getSource();
        //wywolaneprzez.setRendered(false);
        System.out.println("Form: "
                + wywolaneprzez.getNamingContainer().getClientId());
        System.out.println("Rodzic: "
                +(wywolaneprzez = wywolaneprzez.getParent()));
        System.out.println("Klientid: "+wywolaneprzez.getClientId());
        RequestContext.getCurrentInstance().update(wywolaneprzez.getClientId());
        UIComponent nowyinput = new HtmlInputText();
        UIComponent nowyinput1 = new HtmlInputText();
        //nowyinput.setId("nowyinput");
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ExpressionFactory ef = ExpressionFactory.newInstance();
        int rozmiar = 0;
        for(int i=0;i<listka.length;i++){
            if(listka[i]!=null){
                rozmiar++;
            }
        }
        int rozmiarS = rozmiar+1;
        final String binding = "#{podatnikView.listka["+rozmiar+"]}";
        final String bindingS = "#{podatnikView.listka["+rozmiarS+"]}";
        ValueExpression ve = ef.createValueExpression(elContext, binding, String.class);
        ValueExpression ve1 = ef.createValueExpression(elContext, bindingS, String.class);
        nowyinput.setValueExpression("value", ve);
        nowyinput1.setValueExpression("value", ve1);
        grid = getGrid();
        grid.getChildren().add(nowyinput);
        grid.getChildren().add(nowyinput1);
        RequestContext.getCurrentInstance().update(wywolaneprzez.getClientId());
        
        listkakopia = Arrays.asList(listka);
        System.out.println("To jest listka: "+listkakopia.toString());
    }
}
