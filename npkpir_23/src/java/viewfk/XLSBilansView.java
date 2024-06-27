/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import embeddablefk.TreeNodeExtended;
import entityfk.PozycjaRZiS;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import org.apache.poi.ss.usermodel.Workbook;
import view.WpisView;
import wydajnosc.ConstructorInterceptor;
import xls.WriteXLSFile;

/**
 *
 * @author Osito
 */
@Named @Interceptors(ConstructorInterceptor.class)
@ViewScoped
public class XLSBilansView implements Serializable{
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;

    public XLSBilansView() {
         ////E.m(this);
    }
    
    
    
    public void zachowajBilanswXLS(TreeNodeExtended rootProjektRZiS) {
        try {
            List<PozycjaRZiS> pozycje = Collections.synchronizedList(new ArrayList<>());
            List<PozycjaRZiS> listy = new ArrayList<>();
            rootProjektRZiS.getChildrenTree(pozycje, listy);
            Workbook workbook = WriteXLSFile.zachowajBilansInterXLS(listy, wpisView);
            // Prepare response.
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            externalContext.setResponseContentType("application/vnd.ms-excel");
            String filename = "bilansinter.xlsx";
            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
            // Write file to response body.
            workbook.write(externalContext.getResponseOutputStream());
            // Inform JSF that response is completed and it thus doesn't have to navigate.
            facesContext.responseComplete();
        } catch (Exception ex) {
            // Logger.getLogger(XLSBilansView.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
    
      
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    
}
