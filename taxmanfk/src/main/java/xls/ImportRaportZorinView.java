/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import dao.KliencifkDAO;
import entityfk.Kliencifk;
import error.E;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.List;
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
    @Inject
    private KliencifkDAO kliencifkDAO;
    private int ilefaktur;
    
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
                ilefaktur = tabela.getREJESTRYSPRZEDAZYVAT().getREJESTRSPRZEDAZYVAT().size();
            }
            if (tabela != null && tabela.getKONTRAHENCI() != null && !tabela.getKONTRAHENCI().getKONTRAHENT().isEmpty()) {
                pobierzoznaczeniakontrahentow(tabela.getKONTRAHENCI().getKONTRAHENT());
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

    private void pobierzoznaczeniakontrahentow(List<ROOT.KONTRAHENCI.KONTRAHENT> kontrahent) {
        //List<Kliencifk> pobraneklienty = kliencifkDAO.znajdzkontofkKlientBanksymbol(wpisView.getPodatnikObiekt());
        List<Kliencifk> pobraneklienty = kliencifkDAO.znajdzkontofkKlient(wpisView.getPodatnikObiekt());
        for (Kliencifk k : pobraneklienty) {
            for (ROOT.KONTRAHENCI.KONTRAHENT p : kontrahent) {
                try {
                    if (!p.getIDZRODLA().equals("00000000-0009-0002-0001-000000000000")) {
                        ROOT.KONTRAHENCI.KONTRAHENT.ADRESY.ADRES dane = p.getADRESY().getADRES();
                        String nipskladany = null;
                        if (dane.getNIPKRAJ()!=null) {
                            nipskladany = dane.getNIPKRAJ()+dane.getNIP();
                        } else {
                            nipskladany = dane.getNIP();
                        }
                        if (nipskladany.equals(k.getNip())) {
                            k.setBanksymbol(dane.getAKRONIM());
                            kliencifkDAO.edit(k);
                            break;
                        }
                    }
                } catch (Exception e){
                    System.out.println("");
                }
            }
        }
        
    }

    public int getIlefaktur() {
        return ilefaktur;
    }

    public void setIlefaktur(int ilefaktur) {
        this.ilefaktur = ilefaktur;
    }
    
    
}
