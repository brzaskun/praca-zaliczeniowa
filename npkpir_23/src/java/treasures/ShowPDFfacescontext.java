/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treasures;

import java.io.IOException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import msg.Msg;
import org.apache.poi.ss.usermodel.Workbook;
import pl.com.cdn.optima.offline.ROOT;

/**
 *
 * @author Osito
 */
public class ShowPDFfacescontext {
     public void zachowajRaportXLS(ROOT.REJESTRYSPRZEDAZYVAT lista) {
        try {
            if (lista !=null ) {
                Workbook workbook = null;
                // Prepare response.
                FacesContext facesContext = FacesContext.getCurrentInstance();
                ExternalContext externalContext = facesContext.getExternalContext();
                externalContext.setResponseContentType("application/vnd.ms-excel");
                String filename = "zorinraport"+"mc"+"rok"+".xlsx";
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
}
