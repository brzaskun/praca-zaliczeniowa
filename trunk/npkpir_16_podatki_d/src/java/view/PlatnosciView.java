/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PlatnosciDAO;
import dao.PodatnikDAO;
import dao.ZobowiazanieDAO;
import entity.Platnosci;
import entity.PlatnosciPK;
import entity.Podatnik;
import entity.Zobowiazanie;
import entity.Zusstawki;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Osito
 */
@ManagedBean(name="PlatnosciView")
@ViewScoped
public class PlatnosciView implements Serializable{
  
    @Inject
    PodatnikDAO podatnikDAO;
    @Inject
    PlatnosciDAO platnosciDAO;
    @Inject
    private Podatnik selected;
    private static Platnosci selectedZob;
    @Inject
    private ZobowiazanieDAO zv;
    @ManagedProperty(value="#{wpisView}")
    private WpisView wpisView;

    private boolean edytujplatnosc;

    public PlatnosciView() {
    }
    
    @PostConstruct
    private void init(){
        String nazwapodatnika = GuestView.getPodatnikString();
        try{
        selected = podatnikDAO.find(nazwapodatnika);
        } catch (Exception e){}
    }
    
    public void pokazzobowiazania(){
        WpisView wV = new WpisView();
        selectedZob = new Platnosci();
        PlatnosciPK platnosciPK = new PlatnosciPK();
        platnosciPK.setRok(wV.getRokWpisu().toString());
        platnosciPK.setMiesiac(wV.getMiesiacWpisu());
        platnosciPK.setPodatnik(selected.getNazwapelna());
        selectedZob.setPlatnosciPK(platnosciPK);
         try {
           selectedZob = platnosciDAO.findPK(platnosciPK);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Platnosci były już raz zachowane. Pobrano je z archiwum", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            edytujplatnosc = true;
            RequestContext.getCurrentInstance().update("form:formZob");
        } catch (Exception e) {
           nowezobowiazanie();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Wprowadź nowe płatnosci.", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            RequestContext.getCurrentInstance().update("form:formZob:wiad");
        }
    }
            
    public void nowezobowiazanie(){
        List<Zusstawki> listapobrana = selected.getZusparametr();
        Zusstawki zusstawki = new Zusstawki();
        Iterator it;
        it = listapobrana.iterator();
        while(it.hasNext()){
            Zusstawki tmp = (Zusstawki) it.next();
            if(tmp.getZusstawkiPK().getRok().equals(selectedZob.getPlatnosciPK().getRok())&&
                    tmp.getZusstawkiPK().getMiesiac().equals(selectedZob.getPlatnosciPK().getMiesiac())){
                zusstawki = tmp;
            }
        }
        selectedZob.setZus51(zusstawki.getZus51ch());
        selectedZob.setZus52(zusstawki.getZus52());
        selectedZob.setZus53(zusstawki.getZus53());
        List<Zobowiazanie> terminy = new ArrayList<>();
        terminy.addAll(zv.getDownloaded());
        Zobowiazanie termin = new Zobowiazanie();
        Iterator itx;
        itx = terminy.iterator();
        while(itx.hasNext()){
            Zobowiazanie tmp = (Zobowiazanie) itx.next();
            if(tmp.getZobowiazaniePK().getRok().equals(selectedZob.getPlatnosciPK().getRok())&&
                    tmp.getZobowiazaniePK().getMc().equals(selectedZob.getPlatnosciPK().getMiesiac())){
                termin = tmp;
            }
        }
        selectedZob.setTerminzuz(termin.getZusday1());
        selectedZob.setZus51ods(0.0);
        selectedZob.setZus52ods(0.0);
        selectedZob.setZus53ods(0.0);
        selectedZob.setZus51suma(selectedZob.getZus51()+selectedZob.getZus51ods());
        selectedZob.setZus52suma(selectedZob.getZus52()+selectedZob.getZus51ods());
        selectedZob.setZus53suma(selectedZob.getZus53()+selectedZob.getZus51ods());
        }
   

    public void przeliczodsetki(int opcja){
        String datatmp = selectedZob.getPlatnosciPK().getRok()+"-"+selectedZob.getPlatnosciPK().getMiesiac()+"-"+selectedZob.getTerminzuz();
        Date datatmp51 = selectedZob.getZus51zapl();
        Date datatmp52 = selectedZob.getZus52zapl();
        Date datatmp53 = selectedZob.getZus53zapl();
        Date dataod;
        Date datado;
        try {
            DateFormat formatter;
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            dataod = (Date) formatter.parse(datatmp);
            selectedZob.setZus51ods(odsetki(dataod, datatmp51,selectedZob.getZus51().toString()));
            selectedZob.setZus52ods(odsetki(dataod, datatmp52,selectedZob.getZus52().toString()));
            selectedZob.setZus53ods(odsetki(dataod, datatmp53,selectedZob.getZus53().toString()));
            selectedZob.setZus51suma(selectedZob.getZus51()+selectedZob.getZus51ods());
            selectedZob.setZus52suma(selectedZob.getZus52()+selectedZob.getZus52ods());
            selectedZob.setZus53suma(selectedZob.getZus53()+selectedZob.getZus53ods());
        } catch (ParseException e) {
            System.out.println("Exception :" + e);
        }
        try{
            if(opcja==1){
                platnosciDAO.dodajNowyWpis(selectedZob);
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Platnosci zachowane - PodatekView", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                RequestContext.getCurrentInstance().update("form:formZob:wiad");
            } else {
                platnosciDAO.edit(selectedZob);
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Platnosci ponownie zachowane - PodatekView", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                RequestContext.getCurrentInstance().update("form:formZob:wiad");
            }
        } catch (Exception e){
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Platnosci nie zachowane - PodatekView", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            RequestContext.getCurrentInstance().update("form:formZob:wiad");
        }
    }
  
    private Double odsetki(Date dataod, Date datado, String podstawa){
            if(datado!=null){
                BigDecimal odsetki = new BigDecimal(".13");
                odsetki = odsetki.divide(new BigDecimal("365"),20,RoundingMode.HALF_EVEN);
                long x=datado.getTime(); 
                long y=dataod.getTime(); 
                Long wynik=Math.abs(x-y); 
                wynik=wynik/(1000*60*60*24);
                BigDecimal kwota = odsetki.multiply(new BigDecimal(podstawa)).multiply(new BigDecimal(wynik.toString())).setScale(0, RoundingMode.HALF_EVEN);
                return kwota.doubleValue();
            } else {
                return 0.0;
            }
}
    
   
    
    public PodatnikDAO getPodatnikDAO() {
        return podatnikDAO;
    }

    public void setPodatnikDAO(PodatnikDAO podatnikDAO) {
        this.podatnikDAO = podatnikDAO;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public Podatnik getSelected() {
        return selected;
    }

    public void setSelected(Podatnik selected) {
        this.selected = selected;
    }

    public ZobowiazanieDAO getZv() {
        return zv;
    }

    public void setZv(ZobowiazanieDAO zv) {
        this.zv = zv;
    }

    public Platnosci getSelectedZob() {
        return selectedZob;
    }

    public void setSelectedZob(Platnosci selectedZob) {
        this.selectedZob = selectedZob;
    }

    public PlatnosciDAO getPlatnosciDAO() {
        return platnosciDAO;
    }

    public void setPlatnosciDAO(PlatnosciDAO platnosciDAO) {
        this.platnosciDAO = platnosciDAO;
    }

    public boolean isEdytujplatnosc() {
        return edytujplatnosc;
    }

    public void setEdytujplatnosc(boolean edytujplatnosc) {
        this.edytujplatnosc = edytujplatnosc;
    }
    
    
}
