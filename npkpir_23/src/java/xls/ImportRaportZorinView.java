/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import error.E;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import msg.Msg;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import pl.com.cdn.optima.offline.ROOT;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class ImportRaportZorinView implements Serializable {
    private static final long serialVersionUID = 1L;
    private byte[] pobranyplik;
    @Inject
    private WpisView wpisView;
    
     public void zachowajplik(FileUploadEvent event) {
        try {
            UploadedFile uploadedFile = event.getFile();
            String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
            if (extension.equals("csv")||extension.equals("xls")||extension.equals("xlsx")||extension.equals("xml")) {
                String filename = uploadedFile.getFileName();
                pobranyplik = uploadedFile.getContents();
                PrimeFaces.current().ajax().update("panelplik");
            } else {
                Msg.msg("e","Niewłaściwy typ pliku");
            }
        } catch (Exception ex) {
            E.e(ex);
            Msg.msg("e","Wystąpił błąd. Nie udało się załadowanać pliku");
        }
    }
    
    public void raport() {
        try {
            InputStream file = new ByteArrayInputStream(pobranyplik);
            InputStreamReader reader = new InputStreamReader(file, "Windows-1250");
            JAXBContext jaxbContext = JAXBContext.newInstance(ROOT.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            ROOT tabela = (ROOT) jaxbUnmarshaller.unmarshal(reader);
            //Create Workbook instance holding reference to .xlsx file  TYLKO NOWE XLSX
            if (tabela != null && tabela.getREJESTRYSPRZEDAZYVAT() != null && !tabela.getREJESTRYSPRZEDAZYVAT().getREJESTRSPRZEDAZYVAT().isEmpty()) {
                zachowajRaportXLS(tabela.getREJESTRYSPRZEDAZYVAT());
            }
        } catch (Exception e) {
        }
    }
    
    public void zachowajRaportXLS(ROOT.REJESTRYSPRZEDAZYVAT lista) {
        try {
            if (lista !=null ) {
                Workbook workbook = WriteXLSFile.zachowajRaportZorin(lista, wpisView);
                // Prepare response.
                FacesContext facesContext = FacesContext.getCurrentInstance();
                ExternalContext externalContext = facesContext.getExternalContext();
                externalContext.setResponseContentType("application/vnd.ms-excel");
                String filename = "zorinraport"+wpisView.getMiesiacWpisu()+wpisView.getRokWpisuSt()+".xlsx";
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
