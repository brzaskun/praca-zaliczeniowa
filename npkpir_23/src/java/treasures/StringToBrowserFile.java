/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treasures;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author Osito
 */
public class StringToBrowserFile {
    public void robgrupa() {
        OutputStream out = null;
        try {
            List<String> log = new ArrayList<>();
            log.add("Rozpoczęto import pracowników firmy ");
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            externalContext.setResponseContentType("text/plain");
            String filename = "raport.txt";
            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
            // Write file to response body.
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            StringBuilder sb = new StringBuilder();
            for (String s : log) {
                sb.append(s);
            }
            byte[] array = sb.toString().getBytes();
            // Writes data to the output stream
            baos.write(array);
            out = externalContext.getResponseOutputStream();
            baos.writeTo(out);
            // Inform JSF that response is completed and it thus doesn't have to navigate.
            facesContext.responseComplete();
        } catch (IOException ex) {
            Logger.getLogger(StringToBrowserFile.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(StringToBrowserFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
}
