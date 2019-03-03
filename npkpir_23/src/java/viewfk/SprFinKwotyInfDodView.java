/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.SprFinKwotyInfDodDAO;
import entityfk.SprFinKwotyInfDod;
import error.E;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import static jdk.nashorn.internal.objects.ArrayBufferView.buffer;
import msg.Msg;
import org.apache.commons.io.IOUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import plik.Plik;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class SprFinKwotyInfDodView  implements Serializable{
    private static final long serialVersionUID = 1L;
    private SprFinKwotyInfDod sprFinKwotyInfDod;
    @Inject
    private SprFinKwotyInfDodDAO sprFinKwotyInfDodDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    
    @PostConstruct
    private void init() {E.m(this);
        sprFinKwotyInfDod = sprFinKwotyInfDodDAO.findsprfinkwoty(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        if (sprFinKwotyInfDod==null) {
            sprFinKwotyInfDod = new SprFinKwotyInfDod(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        }
    }
    
    public void zapisz() {
        try {
            sprFinKwotyInfDodDAO.edit(sprFinKwotyInfDod);
            init();
            Msg.msg("Zachowano zmiany");
        } catch (Exception e) {
            Msg.msg("e","Wystąpił błąd, niezachowano zmian");
        }
    }
    
    public void zachowajplik(FileUploadEvent event) {
        try {
            UploadedFile uploadedFile = event.getFile();
            String filename = uploadedFile.getFileName();
            //String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
            //String dt = String.valueOf((new Date()).getTime());
            //String nazwakrotka = wpisView.getPodatnikObiekt().getNip()+"_"+dt+"_"+"logo."+extension;
            sprFinKwotyInfDod.setPlik(IOUtils.toByteArray(uploadedFile.getInputstream()));
            sprFinKwotyInfDod.setNazwapliku(filename);
            sprFinKwotyInfDodDAO.edit(sprFinKwotyInfDod);
            Msg.msg("Sukces. Plik " + filename + " został skutecznie załadowany");
        } catch (Exception ex) {
            E.e(ex);
            Msg.msg("e","Wystąpił błąd. Nie udało się załadowanać pliku");
        }
    }
    
    public void pokazplik() {
        OutputStream outStream = null;
        try {
            String nazwa = "informacjadodatkowa"+wpisView.getPodatnikObiekt().getNip();
            File targetFile = Plik.plik(nazwa+".pdf", true);
            outStream = new FileOutputStream(targetFile);
            outStream.write(sprFinKwotyInfDod.getPlik());
            String f = "pokazwydruk('"+nazwa+"');";
            RequestContext.getCurrentInstance().execute(f);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SprFinKwotyInfDodView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SprFinKwotyInfDodView.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                outStream.close();
            } catch (IOException ex) {
                Logger.getLogger(SprFinKwotyInfDodView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public SprFinKwotyInfDod getSprFinKwotyInfDod() {
        return sprFinKwotyInfDod;
    }

    public void setSprFinKwotyInfDod(SprFinKwotyInfDod sprFinKwotyInfDod) {
        this.sprFinKwotyInfDod = sprFinKwotyInfDod;
    }
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    
}
