/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.FakturadodelementyDAO;
import dao.FakturaelementygraficzneDAO;
import entity.Fakturadodelementy;
import entity.Fakturaelementygraficzne;
import error.E;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.context.RequestContext;
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
    private List<Fakturaelementygraficzne> fakturaelementygraficzne;
    @Inject
    private FakturaelementygraficzneDAO fakturaelementygraficzneDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @ManagedProperty(value = "#{fakturadodelementyView}")
    private FakturadodelementyView fakturadodelementyView;
    @Inject
    private FakturadodelementyDAO fakturadodelementyDAO;

    public FakturaelementygraficzneView() {
    }

    @PostConstruct
    private void init() {
        
//        try {
//            fakturaelementygraficzne = fakturaelementygraficzneDAO.findFaktElementyPodatnik(wpisView.getPodatnikWpisu());
//            if (fakturaelementygraficzne == null || fakturaelementygraficzne.isEmpty()) {
//                fakturaelementygraficzne = new ArrayList<>();
//            }
//            for (String p : elementy.keySet()) {
//                String podatnik = wpisView.getPodatnikWpisu();
//                FakturaelementygraficznePK fPK = new FakturaelementygraficznePK(podatnik, p);
//                Fakturaelementygraficzne f = new Fakturaelementygraficzne(fPK, elementy.get(p), false);
//                if (!fakturaelementygraficzne.contains(f)) {
//                    fakturaelementygraficzneDAO.dodaj(f);
//                    fakturaelementygraficzne.add(f);
//                }
//            }
//        } catch (Exception e) { E.e(e); 
//        }
    }

    public String aktualnelogo() {
        Fakturaelementygraficzne elementgraficzny = fakturaelementygraficzneDAO.findFaktElementyGraficznePodatnik(wpisView.getPodatnikWpisu());
        if (elementgraficzny != null) {
            return "/resources/images/logo/"+elementgraficzny.getFakturaelementygraficznePK().getNazwaelementu();
        } else {
            return "";
        }
    }
    
    public void zachowajzmiany() {
        try {
            for (Fakturaelementygraficzne p : fakturaelementygraficzne) {
                fakturaelementygraficzneDAO.dodaj(p);
            }
            Msg.msg("i", "Zachowano dodatkowe elementy faktury.");
        } catch (Exception e) { E.e(e); 
            for (Fakturaelementygraficzne p : fakturaelementygraficzne) {
                fakturaelementygraficzneDAO.edit(p);
            }
            Msg.msg("i", "Wyedytowano dodatkowe elementy faktury.");
        }
    }
    
    public boolean czydodatkowyelementjestAktywny (String element) {
        for (Fakturaelementygraficzne p : fakturaelementygraficzne) {
            if (p.getFakturaelementygraficznePK().getNazwaelementu().equals(element)) {
                return p.getAktywny();
            }
        }
        return false;
    }
    
    public String pobierzelementdodatkowy (String element) {
        for (Fakturaelementygraficzne p : fakturaelementygraficzne) {
            if (p.getFakturaelementygraficznePK().getNazwaelementu().equals(element)) {
                return p.getTrescelementu();
            }
        }
        return "nie odnaleziono";
    }
    
    public void zachowajZaladowanyPlik(FileUploadEvent event) {
        uploadedFile = event.getFile();
        String filename = uploadedFile.getFileName();
        Msg.msg("Sukces. Plik " + filename + " został skutecznie załadowany");
        String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
        Date d = new Date();
        String dt = String.valueOf(d.getTime());
        String nazwapliku = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/resources/images/logo/"+wpisView.getPodatnikObiekt().getNip()+"_"+dt+"_"+"logo."+extension;
        String nazwakrotka = wpisView.getPodatnikObiekt().getNip()+"_"+dt+"_"+"logo."+extension;
        Fakturaelementygraficzne element = fakturaelementygraficzneDAO.findFaktElementyGraficznePodatnik(wpisView.getPodatnikWpisu());
        File newfile = new File(nazwapliku);
        if (element != null) {
            String nazwaplikuzbazy = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/resources/images/logo/"+element.getFakturaelementygraficznePK().getNazwaelementu();
            File oldfile = new File(nazwaplikuzbazy);
            if (oldfile.isFile()) {
                oldfile.delete();
                Fakturaelementygraficzne f = new Fakturaelementygraficzne();
                f.getFakturaelementygraficznePK().setPodatnik(wpisView.getPodatnikWpisu());
                f.getFakturaelementygraficznePK().setNazwaelementu(element.getFakturaelementygraficznePK().getNazwaelementu());
                fakturaelementygraficzneDAO.destroy(f);
            }
        }
        try {
           FileUtils.copyInputStreamToFile(uploadedFile.getInputstream(), newfile);
        } catch (Exception e) { 
            E.e(e); 
        }
        Fakturaelementygraficzne f = new Fakturaelementygraficzne();
        f.getFakturaelementygraficznePK().setPodatnik(wpisView.getPodatnikWpisu());
        f.getFakturaelementygraficznePK().setNazwaelementu(nazwakrotka);
        fakturaelementygraficzneDAO.dodaj(f);
        RequestContext.getCurrentInstance().update("akordeon:formelementy");
        RequestContext.getCurrentInstance().update("akordeon:formelementygraficzne:panellogo");
    }
    
    public void usunlogo() {
        Fakturaelementygraficzne element = fakturaelementygraficzneDAO.findFaktElementyGraficznePodatnik(wpisView.getPodatnikWpisu());
        if (element != null) {
            String nazwaplikuzbazy = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/resources/images/logo/"+element.getFakturaelementygraficznePK().getNazwaelementu();
            File oldfile = new File(nazwaplikuzbazy);
            if (oldfile.isFile()) {
                oldfile.delete();
                Fakturaelementygraficzne f = new Fakturaelementygraficzne();
                f.getFakturaelementygraficznePK().setPodatnik(wpisView.getPodatnikWpisu());
                f.getFakturaelementygraficznePK().setNazwaelementu(element.getFakturaelementygraficznePK().getNazwaelementu());
                fakturaelementygraficzneDAO.destroy(f);
                odhaczuzycieloga();
            } else {
                fakturaelementygraficzneDAO.destroy(element);
                odhaczuzycieloga();
            }
        }
        RequestContext.getCurrentInstance().update("akordeon:formelementygraficzne:panellogo");
    }
    
    public void odhaczuzycieloga() {
        try {
            List<Fakturadodelementy> fakturadodelementy = fakturadodelementyDAO.findFaktElementyPodatnik(wpisView.getPodatnikWpisu());
            if (fakturadodelementy == null || fakturadodelementy.isEmpty()) {
                fakturadodelementy = new ArrayList<>();
            }
            for (Fakturadodelementy p : fakturadodelementy) {
                if (p.getFakturadodelementyPK().getNazwaelementu().equals("logo")) {
                    p.setAktywny(false);
                    fakturadodelementyDAO.edit(p);
                }
            }
            List<Fakturadodelementy> listaelementow = fakturadodelementyView.getFakturadodelementy();
            for (Fakturadodelementy p : listaelementow) {
                if (p.getFakturadodelementyPK().getNazwaelementu().equals("logo")) {
                    p.setAktywny(false);
                }
            }
            RequestContext.getCurrentInstance().update("akordeon:formelementy");
        } catch (Exception e) { E.e(e); 
        }
    }
    
    public static void main(String[] args) {
        Date d = new Date();
        String dt = String.valueOf(d.getTime());
        String nazwapliku = "./build/web/logo/logo.txt";
        File targetFile = new File(nazwapliku);
        try {
             FileUtils.writeStringToFile(targetFile, "lolo");
             //FileUtils.copyInputStreamToFile(null, targetFile);
        } catch (IOException ex) {
            Logger.getLogger(FakturaelementygraficzneView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    //<editor-fold defaultstate="collapsed" desc="comment">
    
    public FakturadodelementyView getFakturadodelementyView() {
        return fakturadodelementyView;
    }

    public void setFakturadodelementyView(FakturadodelementyView fakturadodelementyView) {
        this.fakturadodelementyView = fakturadodelementyView;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }
    
    
    public List<Fakturaelementygraficzne> getFakturaelementygraficzne() {
        return fakturaelementygraficzne;
    }

    public void setFakturaelementygraficzne(List<Fakturaelementygraficzne> fakturaelementygraficzne) {
        this.fakturaelementygraficzne = fakturaelementygraficzne;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    //</editor-fold>

}
