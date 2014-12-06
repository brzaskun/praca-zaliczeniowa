/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.FakturadodelementyDAO;
import entity.Fakturadodelementy;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import msg.Msg;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class FakturaelementygraficzneView implements Serializable {

    private UploadedFile uploadedFile;
    private List<Fakturadodelementy> fakturadodelementy;
    @Inject
    private FakturadodelementyDAO fakturadodelementyDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;

    public FakturaelementygraficzneView() {
    }

    @PostConstruct
    private void init() {
//        try {
//            fakturadodelementy = fakturadodelementyDAO.findFaktElementyPodatnik(wpisView.getPodatnikWpisu());
//            if (fakturadodelementy == null || fakturadodelementy.isEmpty()) {
//                fakturadodelementy = new ArrayList<>();
//            }
//            for (String p : elementy.keySet()) {
//                String podatnik = wpisView.getPodatnikWpisu();
//                FakturadodelementyPK fPK = new FakturadodelementyPK(podatnik, p);
//                Fakturadodelementy f = new Fakturadodelementy(fPK, elementy.get(p), false);
//                if (!fakturadodelementy.contains(f)) {
//                    fakturadodelementyDAO.dodaj(f);
//                    fakturadodelementy.add(f);
//                }
//            }
//        } catch (Exception e) {
//        }
    }

    public void zachowajzmiany() {
        try {
            for (Fakturadodelementy p : fakturadodelementy) {
                fakturadodelementyDAO.dodaj(p);
            }
            Msg.msg("i", "Zachowano dodatkowe elementy faktury.");
        } catch (Exception e) {
            for (Fakturadodelementy p : fakturadodelementy) {
                fakturadodelementyDAO.edit(p);
            }
            Msg.msg("i", "Wyedytowano dodatkowe elementy faktury.");
        }
    }
    
    public boolean czydodatkowyelementjestAktywny (String element) {
        for (Fakturadodelementy p : fakturadodelementy) {
            if (p.getFakturadodelementyPK().getNazwaelementu().equals(element)) {
                return p.getAktywny();
            }
        }
        return false;
    }
    
    public String pobierzelementdodatkowy (String element) {
        for (Fakturadodelementy p : fakturadodelementy) {
            if (p.getFakturadodelementyPK().getNazwaelementu().equals(element)) {
                return p.getTrescelementu();
            }
        }
        return "nie odnaleziono";
    }
    
    public void zachowajZaladowanyPlik(FileUploadEvent event) {
        FacesMessage message = new FacesMessage("Sukces", "Plik "+event.getFile().getFileName() + " został skutecznie załadowany");
        FacesContext.getCurrentInstance().addMessage(null, message);
        uploadedFile = event.getFile();
        String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
        String nazwapliku = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/logo/"+wpisView.getPodatnikObiekt().getNip()+"logo."+extension;
        File targetFile = new File(nazwapliku);
        try {
           FileUtils.copyInputStreamToFile(uploadedFile.getInputstream(), targetFile);
        } catch (Exception e) {
        }
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }
    
    
    public List<Fakturadodelementy> getFakturadodelementy() {
        return fakturadodelementy;
    }

    public void setFakturadodelementy(List<Fakturadodelementy> fakturadodelementy) {
        this.fakturadodelementy = fakturadodelementy;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    //</editor-fold>

}
