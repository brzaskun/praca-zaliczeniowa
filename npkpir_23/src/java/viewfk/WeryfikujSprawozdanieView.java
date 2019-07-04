/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import error.E;
import java.io.InputStream;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import msg.Msg;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import xml.XMLValid;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class WeryfikujSprawozdanieView  implements Serializable {
    private static final long serialVersionUID = 1L;
    private InputStream inputstream;
    
    public void zachowajplik(FileUploadEvent event) {
        try {
            UploadedFile uploadedFile = event.getFile();
            String filename = uploadedFile.getFileName();
            //String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
            //String dt = String.valueOf((new Date()).getTime());
            //String nazwakrotka = wpisView.getPodatnikObiekt().getNip()+"_"+dt+"_"+"logo."+extension;
            inputstream = uploadedFile.getInputstream();
            Msg.msg("Sukces. Plik " + filename + " został skutecznie załadowany");
        } catch (Exception ex) {
            E.e(ex);
            Msg.msg("e","Wystąpił błąd. Nie udało się załadowanać pliku");
        }
    }
    
    public void weryfikuj(int coweryfikowac) {
        if (inputstream!=null) {
            Object[] zwrot = XMLValid.walidujsprawozdanieView(inputstream, coweryfikowac);
            boolean wynik = (boolean) zwrot[0];
            String wyniktext = (String) zwrot[1];
            if (wynik==true) {
                Msg.msg("Nie ma błędów. Plik prawidłowy");
            } else {
                Msg.msg("e", "Plik nieprawidłowy."+wyniktext);  
            }
        } else {
            Msg.msg("e", "Nie załadowano pliku");  
        }
    }
    
}
