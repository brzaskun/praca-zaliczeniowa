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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import msg.Msg;
 import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;



/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class FakturaelementygraficzneView implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private List<Fakturaelementygraficzne> fakturaelementygraficzne;
    @Inject
    private FakturaelementygraficzneDAO fakturaelementygraficzneDAO;
    @Inject
    private WpisView wpisView;
    @Inject
    private FakturadodelementyView fakturadodelementyView;
    @Inject
    private FakturadodelementyDAO fakturadodelementyDAO;
    @Inject
    private LogofakturaDAO logofakturaDAO;
    private String logoszerokosc;
    private String logowysokosc;
    private String elementgraficznyszerokosc;
    private String elementgraficznywysokosc;
    private String aktualnelogo;
    private String aktualnyelementgraficzny;

    public FakturaelementygraficzneView() {
    }

    @PostConstruct
    private void init() { //E.m(this);
        Fakturaelementygraficzne logo = fakturaelementygraficzneDAO.findFaktElementyGraficznePodatnik(wpisView.getPodatnikWpisu());
        if (logo != null) {
            logoszerokosc = logo.getSzerokosc();
            logowysokosc = logo.getWysokosc();
            sprawdzczyniezniknalplik(logo.getFakturaelementygraficznePK().getNazwaelementu(),0);
        }
        Fakturaelementygraficzne elementgraficzny = fakturaelementygraficzneDAO.findFaktElementyGraficznedodatkowe(wpisView.getPodatnikWpisu());
        if (elementgraficzny != null) {
            elementgraficznyszerokosc = elementgraficzny.getSzerokosc();
            elementgraficznywysokosc = elementgraficzny.getWysokosc();
            sprawdzczyniezniknalplik(elementgraficzny.getFakturaelementygraficznePK().getNazwaelementu(),1);
        }
        aktualnelogo = aktualnelogoSzukaj();
        aktualnyelementgraficzny = aktualnyelementgraficznySzukaj();
    }

    public String aktualnelogoSzukaj() {
        Fakturaelementygraficzne logo = fakturaelementygraficzneDAO.findFaktElementyGraficznePodatnik(wpisView.getPodatnikWpisu());
        if (logo != null) {
            logoszerokosc = logo.getSzerokosc() != null ? logo.getSzerokosc() : "100";
            logowysokosc = logo.getWysokosc() != null ? logo.getWysokosc() : "100";
            return "/resources/images/logo/"+logo.getFakturaelementygraficznePK().getNazwaelementu();
        } else {
            return "";
        }
    }
    
    public String aktualnyelementgraficznySzukaj() {
        Fakturaelementygraficzne elementgraficzny = fakturaelementygraficzneDAO.findFaktElementyGraficznedodatkowe(wpisView.getPodatnikWpisu());
        if (elementgraficzny != null) {
            elementgraficznyszerokosc = elementgraficzny.getSzerokosc() != null ? elementgraficzny.getSzerokosc() : "100";
            elementgraficznywysokosc = elementgraficzny.getWysokosc() != null ? elementgraficzny.getWysokosc() : "100";
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
    
     public void zapiszwymiaryelementgraficzny() {
        try {
            Fakturaelementygraficzne elementgraficzny = fakturaelementygraficzneDAO.findFaktElementyGraficznedodatkowe(wpisView.getPodatnikWpisu());
            if (elementgraficzny != null) {
                elementgraficzny.setSzerokosc(elementgraficznyszerokosc);
                elementgraficzny.setWysokosc(elementgraficznywysokosc);
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
            logofakturaDAO.usun(wpisView.getPodatnikObiekt(),0);
            logofakturaDAO.edit(new Logofaktura(wpisView.getPodatnikObiekt(),nazwakrotka,extension,uploadedFile.getContent(),0));
            usunlogo();
            uzycielogaelementu(true,"logo");
            zachowajpliknadyskuLogo(uploadedFile.getInputStream(), dt, extension);
            fakturaelementygraficzneDAO.create(new Fakturaelementygraficzne(wpisView.getPodatnikWpisu(),nazwakrotka, true));
            aktualnelogo = aktualnelogoSzukaj();
            PrimeFaces.current().ajax().update("akordeon:formelementy");
            PrimeFaces.current().ajax().update("akordeon:formelementygraficzne");
            Msg.msg("Sukces. Plik " + filename + " został skutecznie załadowany");
        } catch (Exception ex) {
            E.e(ex);
            Msg.msg("e","Wystąpił błąd. Nie udało się załadowanać pliku");
        }
    }
    
     public void zachowajZaladowanaGrafike(FileUploadEvent event) {
        try {
            UploadedFile uploadedFile = event.getFile();
            String filename = uploadedFile.getFileName();
            String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
            String dt = String.valueOf((new Date()).getTime());
            String nazwakrotka = wpisView.getPodatnikObiekt().getNip()+"_"+dt+"_"+"grafika."+extension;
            logofakturaDAO.usun(wpisView.getPodatnikObiekt(),1);
            logofakturaDAO.edit(new Logofaktura(wpisView.getPodatnikObiekt(),nazwakrotka,extension,uploadedFile.getContent(),1));
            usunelementgraficzny();
            uzycielogaelementu(true,"element graficzny");
            zachowajpliknadyskuLogo(uploadedFile.getInputStream(), dt, extension);
            fakturaelementygraficzneDAO.create(new Fakturaelementygraficzne(wpisView.getPodatnikWpisu(),nazwakrotka, false));
            aktualnyelementgraficzny = aktualnyelementgraficznySzukaj();
            PrimeFaces.current().ajax().update("akordeon:formelementy");
            PrimeFaces.current().ajax().update("akordeon:formelementygraficznea");
            PrimeFaces.current().ajax().update("akordeon:formelementygraficznea2");
            Msg.msg("Sukces. Plik " + filename + " został skutecznie załadowany");
        } catch (Exception ex) {
            E.e(ex);
            Msg.msg("e","Wystąpił błąd. Nie udało się załadowanać pliku");
        }
    }
    
    public void usunlogo() {
        Fakturaelementygraficzne element = fakturaelementygraficzneDAO.findFaktElementyGraficznePodatnik(wpisView.getPodatnikWpisu());
        if (element != null) {
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = ctx.getRealPath("/");
            String nazwaplikuzbazy = realPath+"resources/images/logo/"+element.getFakturaelementygraficznePK().getNazwaelementu();
            File oldfile = new File(nazwaplikuzbazy);
            if (oldfile.isFile()) {
                oldfile.delete();
                Fakturaelementygraficzne f = new Fakturaelementygraficzne();
                f.getFakturaelementygraficznePK().setPodatnik(wpisView.getPodatnikWpisu());
                f.getFakturaelementygraficznePK().setNazwaelementu(element.getFakturaelementygraficznePK().getNazwaelementu());
                f.setLogo(true);
                fakturaelementygraficzneDAO.remove(f);
                aktualnelogo = aktualnelogoSzukaj();
                uzycielogaelementu(false,"logo");
            } else {
                fakturaelementygraficzneDAO.remove(element);
                aktualnelogo = aktualnelogoSzukaj();
                uzycielogaelementu(false,"logo");
            }
            logofakturaDAO.usun(wpisView.getPodatnikObiekt(),0);
        }
        PrimeFaces.current().ajax().update("akordeon:formelementygraficzne:panellogo");
    }
    
    public void usunelementgraficzny() {
        Fakturaelementygraficzne element = fakturaelementygraficzneDAO.findFaktElementyGraficznedodatkowe(wpisView.getPodatnikWpisu());
        if (element != null) {
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = ctx.getRealPath("/");
            String nazwaplikuzbazy = realPath+"resources/images/logo/"+element.getFakturaelementygraficznePK().getNazwaelementu();
            File oldfile = new File(nazwaplikuzbazy);
            if (oldfile.isFile()) {
                oldfile.delete();
                Fakturaelementygraficzne f = new Fakturaelementygraficzne();
                f.getFakturaelementygraficznePK().setPodatnik(wpisView.getPodatnikWpisu());
                f.getFakturaelementygraficznePK().setNazwaelementu(element.getFakturaelementygraficznePK().getNazwaelementu());
                f.setLogo(false);
                fakturaelementygraficzneDAO.remove(f);
                aktualnyelementgraficzny = aktualnyelementgraficznySzukaj();
                uzycielogaelementu(false,"element graficzny");
            } else {
                aktualnyelementgraficzny = aktualnyelementgraficznySzukaj();
                fakturaelementygraficzneDAO.remove(element);
                uzycielogaelementu(false,"element graficzny");
            }
            logofakturaDAO.usun(wpisView.getPodatnikObiekt(),1);
        }
        PrimeFaces.current().ajax().update("akordeon:formelementygraficznea:panelgrafika");
    }
    
    public void uzycielogaelementu(boolean zaznacz1odznacz0, String nazwa) {
        try {
            List<Fakturadodelementy> fakturadodelementy = fakturadodelementyDAO.findFaktElementyPodatnik(wpisView.getPodatnikWpisu());
            if (fakturadodelementy == null || fakturadodelementy.isEmpty()) {
                fakturadodelementy = Collections.synchronizedList(new ArrayList<>());
            }
            for (Fakturadodelementy p : fakturadodelementy) {
                if (p.getFakturadodelementyPK().getNazwaelementu().equals(nazwa)) {
                    p.setAktywny(zaznacz1odznacz0);
                    fakturadodelementyDAO.edit(p);
                }
            }
            List<Fakturadodelementy> listaelementow = fakturadodelementyView.getFakturadodelementy();
            for (Fakturadodelementy p : listaelementow) {
                if (p.getFakturadodelementyPK().getNazwaelementu().equals(nazwa)) {
                    p.setAktywny(zaznacz1odznacz0);
                }
            }
            PrimeFaces.current().ajax().update("akordeon:formelementy");
        } catch (Exception e) { E.e(e); 
        }
    }
    
    private void zachowajpliknadyskuLogo(InputStream is,String dt, String extension) {
        try {
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = ctx.getRealPath("/");
           String nazwapliku = realPath+"resources/images/logo/"+wpisView.getPodatnikObiekt().getNip()+"_"+dt+"_"+"logo."+extension;
           FileUtils.copyInputStreamToFile(is, new File(nazwapliku));
        } catch (Exception e) { 
            E.e(e); 
        }
    }
    
    private void zachowajpliknaelementGraficzny(InputStream is,String dt, String extension) {
        try {
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = ctx.getRealPath("/");
           String nazwapliku = realPath+"resources/images/logo/"+wpisView.getPodatnikObiekt().getNip()+"_"+dt+"_"+"grafika."+extension;
           FileUtils.copyInputStreamToFile(is, new File(nazwapliku));
        } catch (Exception e) { 
            E.e(e); 
        }
    }
    
    private void sprawdzczyniezniknalplik(String nazwa, int nrkolejny) {
        try {
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = ctx.getRealPath("/");
            String nazwapliku = realPath+"resources/images/logo/"+nazwa;
            File oldfile = new File(nazwapliku);
            if (!oldfile.isFile()) {
                Logofaktura logofaktura = logofakturaDAO.findByPodatnik(wpisView.getPodatnikObiekt(),nrkolejny);
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
             FileUtils.writeStringToFile(targetFile, "lolo", StandardCharsets.UTF_8);
             //FileUtils.copyInputStreamToFile(null, targetFile);
        } catch (IOException ex) {
            // Logger.getLogger(FakturaelementygraficzneView.class.getName()).log(Level.SEVERE, null, ex);
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

    public String getElementgraficznyszerokosc() {
        return elementgraficznyszerokosc;
    }

    public void setElementgraficznyszerokosc(String elementgraficznyszerokosc) {
        this.elementgraficznyszerokosc = elementgraficznyszerokosc;
    }

    public String getElementgraficznywysokosc() {
        return elementgraficznywysokosc;
    }

    public void setElementgraficznywysokosc(String elementgraficznywysokosc) {
        this.elementgraficznywysokosc = elementgraficznywysokosc;
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

    public String getAktualnelogo() {
        return aktualnelogo;
    }

    public void setAktualnelogo(String aktualnelogo) {
        this.aktualnelogo = aktualnelogo;
    }

    public String getAktualnyelementgraficzny() {
        return aktualnyelementgraficzny;
    }

    public void setAktualnyelementgraficzny(String aktualnyelementgraficzny) {
        this.aktualnyelementgraficzny = aktualnyelementgraficzny;
    }

}
