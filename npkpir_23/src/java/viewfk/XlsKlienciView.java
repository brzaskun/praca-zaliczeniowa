/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.KlienciDAO;
import entity.Klienci;
import interceptor.ConstructorInterceptor;
import java.io.IOException;
import java.io.Serializable;
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
import xls.WriteXLSFile;

/**
 *
 * @author Osito
 */
@Named @Interceptors(ConstructorInterceptor.class)
@ViewScoped
public class XlsKlienciView implements Serializable{
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private KlienciDAO klienciDAO;

    public XlsKlienciView() {
         ////E.m(this);
    }
    
    
    
    public void zachowajwXLS(List<Klienci> lista, boolean niemieckienaglowki) {
        try {
            Map<String, List> listy = new ConcurrentHashMap<>();
            listy.put("klienci", lista);
            Workbook workbook = WriteXLSFile.zachowajKlienciWartoscXLS(listy, wpisView, niemieckienaglowki);
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
    
    
   
    
   
      
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

   
    
}
