/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.StronaWierszaDAO;
import entityfk.StronaWiersza;
import error.E;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import org.apache.poi.ss.usermodel.Workbook;
import view.WpisView;
import interceptor.ConstructorInterceptor;
import xls.WriteXLSFile;

/**
 *
 * @author Osito
 */
@Named @Interceptors(ConstructorInterceptor.class)
@ViewScoped
public class XLSSinkosView implements Serializable{
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;
    private List<StronaWiersza> kontozapisy;
    private List<StronaWiersza> kontozapisyfiltered;
    private List<StronaWiersza> wybranezapisydosumowania;

    public XLSSinkosView() {
         ////E.m(this);
    }
    
    
    public void pobierzzapisy() {
        kontozapisy = stronaWierszaDAO.findStronaByPodatnikRokMcWynik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
    }
    
    public void zachowajwXLS() {
        try {
            Map<String, List<StronaWiersza>> listy = new ConcurrentHashMap<>();
            listy.put("xlssinkos", kontozapisy);
            Workbook workbook = WriteXLSFile.zachowajSinkosXLS(listy, wpisView);
            // Prepare response.
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            externalContext.setResponseContentType("application/vnd.ms-excel;charset=UTF-8");
            String filename = "sinkos"+wpisView.getMiesiacWpisu()+wpisView.getRokWpisuSt()+".xlsx";
            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
            // Write file to response body.
            workbook.write(externalContext.getResponseOutputStream());
            // Inform JSF that response is completed and it thus doesn't have to navigate.
            facesContext.responseComplete();
        } catch (IOException ex) {
            // Logger.getLogger(XLSRodzajeDokView.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
    
    public int sortZapisynaKoncie(Object o1, Object o2) {
        String datao1 = ((StronaWiersza) o1).getWiersz().getDokfk().getDatadokumentu();
        String datao2 = ((StronaWiersza) o2).getWiersz().getDokfk().getDatadokumentu();
        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date datao1date = null;
        Date datao2date = null;
        try {
            datao1date = formatter.parse(datao1);
            datao2date = formatter.parse(datao2);
            if (datao1date.before(datao2date)) {
                return -1;
            } else if (datao1date.after(datao2date)) {
                return 1;
            } else {
                return porownajseriedok(((StronaWiersza) o1),((StronaWiersza) o2));
            }
        } catch (Exception e) {  E.e(e);
            return 0;
        }
    }
    
    private int porownajseriedok(StronaWiersza o1, StronaWiersza o2) {
        String seriao1 = o1.getWiersz().getDokfk().getSeriadokfk();
        String seriao2 = o2.getWiersz().getDokfk().getSeriadokfk();
        if (seriao1.equals(seriao2)) {
            return porownajnrserii(o1,o2);
        } else {
            return seriao1.compareTo(seriao2);
        }
    }
    
    private int porownajnrserii(StronaWiersza o1, StronaWiersza o2) {
        int seriao1 = o1.getWiersz().getDokfk().getNrkolejnywserii();
        int seriao2 = o2.getWiersz().getDokfk().getNrkolejnywserii();
        if (seriao1 == seriao2) {
            return porownajnumerwiersza(o1,o2);
        } else if (seriao1 < seriao2){
            return -1;
        } else {
            return 1;
        }
    }
    
    private int porownajnumerwiersza(StronaWiersza o1, StronaWiersza o2) {
        int seriao1 = o1.getWiersz().getIdporzadkowy();
        int seriao2 = o2.getWiersz().getIdporzadkowy();
        if (seriao1 == seriao2) {
            return 0;
        } else if (seriao1 < seriao2){
            return -1;
        } else {
            return 1;
        }
    }

    public List<StronaWiersza> getKontozapisy() {
        return kontozapisy;
    }

    public void setKontozapisy(List<StronaWiersza> kontozapisy) {
        this.kontozapisy = kontozapisy;
    }

    public List<StronaWiersza> getKontozapisyfiltered() {
        return kontozapisyfiltered;
    }

    public void setKontozapisyfiltered(List<StronaWiersza> kontozapisyfiltered) {
        this.kontozapisyfiltered = kontozapisyfiltered;
    }

    public List<StronaWiersza> getWybranezapisydosumowania() {
        return wybranezapisydosumowania;
    }

    public void setWybranezapisydosumowania(List<StronaWiersza> wybranezapisydosumowania) {
        this.wybranezapisydosumowania = wybranezapisydosumowania;
    }
    
    
    
    
}
