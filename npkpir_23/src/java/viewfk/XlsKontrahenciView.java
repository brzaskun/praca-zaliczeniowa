/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.KlienciDAO;
import entity.Klienci;
import entityfk.Kliencifk;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.poi.ss.usermodel.Workbook;
import view.WpisView;
import xls.WriteXLSFile;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class XlsKontrahenciView implements Serializable{
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private KlienciDAO klienciDAO;

    public XlsKontrahenciView() {
         ////E.m(this);
    }
    
    
    
    public void zachowajwXLS(List<Kliencifk> lista) {
        try {
            List<Klienci> kliencifkatury = przetworzliste(lista);
            Map<String, List> listy = new ConcurrentHashMap<>();
            listy.put("kontrahenci", kliencifkatury);
            Workbook workbook = WriteXLSFile.zachowajKontrahencikXLS(listy, wpisView);
            // Prepare response.
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            externalContext.setResponseContentType("application/vnd.ms-excel");
            String filename = "kontrahenci"+wpisView.getMiesiacWpisu()+wpisView.getRokWpisuSt()+".xlsx";
            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
            // Write file to response body.
            workbook.write(externalContext.getResponseOutputStream());
            // Inform JSF that response is completed and it thus doesn't have to navigate.
            facesContext.responseComplete();
        } catch (IOException ex) {
            // Logger.getLogger(XLSRodzajeDokView.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
   
    
    private List<Klienci> przetworzliste(List<Kliencifk> lista) {
        List<Klienci> wszyscy = klienciDAO.findAll();
        Set<String> nipfk = lista.stream().map(p->p.getNip()).distinct().collect(Collectors.toSet());
        List<Klienci> zwrot = wszyscy.stream().filter(p->nipfk.contains(p.getNip())).collect(Collectors.toList());
        return zwrot;
    }
    
      
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

   
    
}
