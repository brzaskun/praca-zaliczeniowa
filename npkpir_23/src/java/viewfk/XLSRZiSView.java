/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import embeddablefk.TreeNodeExtended;
import entityfk.PozycjaRZiS;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.apache.poi.ss.usermodel.Workbook;
import view.WpisView;
import xls.WriteXLSFile;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class XLSRZiSView implements Serializable{
    private static final long serialVersionUID = 1L;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    
    public void zachowajRZiSwXLS(TreeNodeExtended rootProjektRZiS) {
        try {
            ArrayList<PozycjaRZiS> pozycje = new ArrayList<>();
            rootProjektRZiS.getFinallChildren(pozycje);
            Map<String, List> listy = new HashMap<>();
            listy.put("rzisinter", pozycje);
            Workbook workbook = WriteXLSFile.zachowajRZiSInterXLS(listy, wpisView);
            // Prepare response.
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            externalContext.setResponseContentType("application/vnd.ms-excel");
            String filename = "rzisinter.xlsx";
            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
            // Write file to response body.
            workbook.write(externalContext.getResponseOutputStream());
            // Inform JSF that response is completed and it thus doesn't have to navigate.
            facesContext.responseComplete();
        } catch (Exception ex) {
            Logger.getLogger(XLSRZiSView.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
    
      
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    
}
