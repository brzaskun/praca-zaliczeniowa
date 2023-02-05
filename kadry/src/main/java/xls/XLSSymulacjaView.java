/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class XLSSymulacjaView implements Serializable{
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    
    public void zachowajSymulacjewXLS(int modulator) {
         ////E.m(this);
//        try {
//            List przychody = null;
//            Map<String, List> listy = new ConcurrentHashMap<>();
//            listy.put("p", przychody);
//            Workbook workbook = WriteXLSFile.zachowajXLS(listy, wpisView);
//            // Prepare response.
//            FacesContext facesContext = FacesContext.getCurrentInstance();
//            ExternalContext externalContext = facesContext.getExternalContext();
//            externalContext.setResponseContentType("application/vnd.ms-excel");
//            String filename = "wynikfin"+wpisView.getMiesiacWpisu()+wpisView.getRokWpisu()+".xlsx";
//            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
//            // Write file to response body.
//            workbook.write(externalContext.getResponseOutputStream());
//            // Inform JSF that response is completed and it thus doesn't have to navigate.
//            facesContext.responseComplete();
//        } catch (IOException ex) {
//            // Logger.getLogger(XLSSymulacjaView.class.getName()).log(Level.SEVERE, null, ex);
//            
//        }
    }
    
    
    
}
