/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.FakturadodelementyDAO;
import dao.FakturaelementygraficzneDAO;
import dao.LogofakturaDAO;
import entity.Fakturadodelementy;
import entity.Fakturaelementygraficzne;
import entity.Logofaktura;
import error.E;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import javax.faces.context.FacesContext;
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
    
    private List<Fakturaelementygraficzne> fakturaelementygraficzne;
    @Inject
    private FakturaelementygraficzneDAO fakturaelementygraficzneDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @ManagedProperty(value = "#{fakturadodelementyView}")
    private FakturadodelementyView fakturadodelementyView;
    @Inject
    private FakturadodelementyDAO fakturadodelementyDAO;
    @Inject
    private LogofakturaDAO logofakturaDAO;
    private String logoszerokosc;
    private String logowysokosc;

    public FakturaelementygraficzneView() {
    }

    @PostConstruct
    private void init() {
        Fakturaelementygraficzne elementgraficzny = fakturaelementygraficzneDAO.findFaktElementyGraficznePodatnik(wpisView.getPodatnikWpisu());
        if (elementgraficzny != null) {
            logoszerokosc = elementgraficzny.getSzerokosc();
            logowysokosc = elementgraficzny.getWysokosc();
            sprawdzczyniezniknalplik(elementgraficzny.getFakturaelementygraficznePK().getNazwaelementu());
        }
    }

    public String aktualnelogo() {
        Fakturaelementygraficzne elementgraficzny = fakturaelementygraficzneDAO.findFaktElementyGraficznePodatnik(wpisView.getPodatnikWpisu());
        if (elementgraficzny != null) {
            logoszerokosc = elementgraficzny.getSzerokosc() != null ? elementgraficzny.getSzerokosc() : "100";
            logowysokosc = elementgraficzny.getWysokosc() != null ? elementgraficzny.getWysokosc() : "100";
            return "/resources/images/logo/"+elementgraficzny.getFakturaelementygraficznePK().getNazwaelementu();
        } else {
            return "";
        }
    }
    
    public void zapiszwymiarylogo() {
        try {
            Fakturaelementygraficzne elementgraficzny = fakturaelementygraficzneDAO.findFaktElementyGraficznePodatnik(wpisView.getPodatnikWpisu());
            if (elementgraficzny != null) {
                elementgraficzny.setSzerokosc(logoszerokosc);
                elementgraficzny.setWysokosc(logowysokosc);
                fakturaelementygraficzneDAO.edit(elementgraficzny);
                FacesContext context = FacesContext.getCurrentInstance();
                FakturadodelementyView fd = (FakturadodelementyView) context.getELContext().getELResolver().getValue(context.getELContext(), null, "fakturadodelementyView");
                fd.init();
                Msg.msg("Zachowano nowe wymiary");
            }
        } catch (Exception e) {
            E.e(e);
            Msg.dPe();
        }
    }
    
   
    public void zachowajZaladowaneLogo(FileUploadEvent event) {
        try {
            UploadedFile uploadedFile = event.getFile();
            String filename = uploadedFile.getFileName();
            String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
            String dt = String.valueOf((new Date()).getTime());
            String nazwakrotka = wpisView.getPodatnikObiekt().getNip()+"_"+dt+"_"+"logo."+extension;
            logofakturaDAO.usun(wpisView.getPodatnikObiekt());
            logofakturaDAO.edit(new Logofaktura(wpisView.getPodatnikObiekt(),nazwakrotka,extension,uploadedFile.getContents()));
            usunlogo();
            uzycieloga(true);
            zachowajpliknadysku(uploadedFile.getInputstream(), dt, extension);
            fakturaelementygraficzneDAO.dodaj(new Fakturaelementygraficzne(wpisView.getPodatnikWpisu(),nazwakrotka));
            RequestContext.getCurrentInstance().update("akordeon:formelementy");
            RequestContext.getCurrentInstance().update("akordeon:formelementygraficzne:panellogo");
            Msg.msg("Sukces. Plik " + filename + " został skutecznie załadowany");
        } catch (Exception ex) {
            E.e(ex);
            Msg.msg("e","Wystąpił błąd. Nie udało się załadowanać pliku");
        }
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
                uzycieloga(false);
            } else {
                fakturaelementygraficzneDAO.destroy(element);
                uzycieloga(false);
            }
            logofakturaDAO.usun(wpisView.getPodatnikObiekt());
        }
        RequestContext.getCurrentInstance().update("akordeon:formelementygraficzne:panellogo");
    }
    
    public void uzycieloga(boolean zaznacz1odznacz0) {
        try {
            List<Fakturadodelementy> fakturadodelementy = fakturadodelementyDAO.findFaktElementyPodatnik(wpisView.getPodatnikWpisu());
            if (fakturadodelementy == null || fakturadodelementy.isEmpty()) {
                fakturadodelementy = new ArrayList<>();
            }
            for (Fakturadodelementy p : fakturadodelementy) {
                if (p.getFakturadodelementyPK().getNazwaelementu().equals("logo")) {
                    p.setAktywny(zaznacz1odznacz0);
                    fakturadodelementyDAO.edit(p);
                }
            }
            List<Fakturadodelementy> listaelementow = fakturadodelementyView.getFakturadodelementy();
            for (Fakturadodelementy p : listaelementow) {
                if (p.getFakturadodelementyPK().getNazwaelementu().equals("logo")) {
                    p.setAktywny(zaznacz1odznacz0);
                }
            }
            RequestContext.getCurrentInstance().update("akordeon:formelementy");
        } catch (Exception e) { E.e(e); 
        }
    }
    
    private void zachowajpliknadysku(InputStream is,String dt, String extension) {
        try {
           String nazwapliku = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/resources/images/logo/"+wpisView.getPodatnikObiekt().getNip()+"_"+dt+"_"+"logo."+extension;
           FileUtils.copyInputStreamToFile(is, new File(nazwapliku));
        } catch (Exception e) { 
            E.e(e); 
        }
    }
    
    private void sprawdzczyniezniknalplik(String nazwa) {
        try {
            String nazwapliku = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/resources/images/logo/"+nazwa;
            File oldfile = new File(nazwapliku);
            if (!oldfile.isFile()) {
                Logofaktura logofaktura = logofakturaDAO.findByPodatnik(wpisView.getPodatnikObiekt());
                FileUtils.copyInputStreamToFile(new ByteArrayInputStream(logofaktura.getPliklogo()), new File(nazwapliku));
            }
        } catch (Exception e) {
            E.e(e);
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

    public String getLogoszerokosc() {
        return logoszerokosc;
    }

    public void setLogoszerokosc(String logoszerokosc) {
        this.logoszerokosc = logoszerokosc;
    }

    public String getLogowysokosc() {
        return logowysokosc;
    }

    public void setLogowysokosc(String logowysokosc) {
        this.logowysokosc = logowysokosc;
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
