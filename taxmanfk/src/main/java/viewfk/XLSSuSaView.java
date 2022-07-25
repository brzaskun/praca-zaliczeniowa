/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import comparator.SaldoKontocomparator;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import msg.Msg;
import org.apache.poi.ss.usermodel.Workbook;
import view.WpisView;import xls.WriteXLSFile;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class XLSSuSaView implements Serializable{
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;

    public XLSSuSaView() {
         ////E.m(this);
    }
    
    
    
    public void zachowajSuSawXLS(List lista,List filter, List wybrane) {
        try {
            List podstawowa = f.l.l(lista, filter, wybrane);
            if (podstawowa !=null ) {
                Collections.sort(podstawowa, new SaldoKontocomparator());
                Map<String, List> listy = new ConcurrentHashMap<>();
                listy.put("kontasalda", podstawowa);
                Workbook workbook = WriteXLSFile.zachowajSuSaXLS(listy, wpisView);
                // Prepare response.
                FacesContext facesContext = FacesContext.getCurrentInstance();
                ExternalContext externalContext = facesContext.getExternalContext();
                externalContext.setResponseContentType("application/vnd.ms-excel");
                String filename = "kontasalda"+wpisView.getMiesiacWpisu()+wpisView.getRokWpisuSt()+".xlsx";
                externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
                // Write file to response body.
                workbook.write(externalContext.getResponseOutputStream());
                // Inform JSF that response is completed and it thus doesn't have to navigate.
                facesContext.responseComplete();
                Msg.msg("Przewtorzono listę sald do xls");
            } else {
                Msg.msg("e","Lista pusta");
            }
        } catch (IOException ex) {
            // Logger.getLogger(XLSSuSaView.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
    
      
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    
}
