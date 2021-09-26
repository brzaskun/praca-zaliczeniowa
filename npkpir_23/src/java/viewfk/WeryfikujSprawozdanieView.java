/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import error.E;
import java.io.InputStream;
import java.io.Serializable;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import msg.Msg;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import xml.XMLValid;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class WeryfikujSprawozdanieView  implements Serializable {
    private static final long serialVersionUID = 1L;
    private InputStream inputstream;
    private String wersjaschemy;
    
    public void zachowajplik(FileUploadEvent event) {
        try {
            UploadedFile uploadedFile = event.getFile();
            String filename = uploadedFile.getFileName();
            //String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
            //String dt = String.valueOf((new Date()).getTime());
            //String nazwakrotka = wpisView.getPodatnikObiekt().getNip()+"_"+dt+"_"+"logo."+extension;
            inputstream = uploadedFile.getInputStream();
            Msg.msg("i","Sukces. Plik " + filename + " został skutecznie załadowany","form_dialogweryfikujsprawozdaniel:mes1");
        } catch (Exception ex) {
            E.e(ex);
            Msg.msg("e","Wystąpił błąd. Nie udało się załadowanać pliku","form_dialogweryfikujsprawozdaniel:mes1");
        }
    }
    
    public void weryfikuj(int coweryfikowac) {
        if (wersjaschemy==null) {
            Msg.msg("e","Nie wybrano wersji schemy. Nie można walidować");
        } else if (inputstream!=null) {
            Object[] zwrot = XMLValid.walidujsprawozdanieView(inputstream, coweryfikowac, wersjaschemy);
            boolean wynik = (boolean) zwrot[0];
            String wyniktext = (String) zwrot[1];
            if (wynik==true) {
                Msg.msg("i","Nie ma błędów. Plik prawidłowy","form_dialogweryfikujsprawozdanie:mes2");
            } else {
                Msg.msg("e", "Plik nieprawidłowy."+wyniktext,"form_dialogweryfikujsprawozdanie:mes2");  
            }
        } else {
            Msg.msg("e", "Nie załadowano pliku","form_dialogweryfikujsprawozdanie:mes2");  
        }
    }


    public String getWersjaschemy() {
        return wersjaschemy;
    }

    public void setWersjaschemy(String wersjaschemy) {
        this.wersjaschemy = wersjaschemy;
    }
    
}
