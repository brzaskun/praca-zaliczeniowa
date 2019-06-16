/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plik;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 *
 * @author Osito
 */
public class Plik {
    
    
    public static File plik(String nazwa, boolean temp) {
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String realPath = ctx.getRealPath("/")+"wydruki\\";
        File file = null;
        String pelnanazwa = realPath+nazwa;
        if (temp == true) {
            file = new File(pelnanazwa);
            file.deleteOnExit();
        } else {
            file = new File(pelnanazwa);
        }
        return file;
    }
    
    public static BufferedOutputStream plikR(String nazwa) {
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String realPath = ctx.getRealPath("/")+"wydruki\\";
        BufferedOutputStream fileOutputStream = null;
        String pelnanazwa = realPath+nazwa;
        try {
            fileOutputStream = new BufferedOutputStream(new FileOutputStream(pelnanazwa));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Plik.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return fileOutputStream;
        }
    }

    public static String getKatalog() {
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String realPath = ctx.getRealPath("/")+"wydruki\\";
        return realPath;
    }

    public static void usun(String nazwa) {
        File f = new File(nazwa);
        if (f.exists()) {
            f.delete();
        }
    }

    public static void zapiszjako(String zrodlo, String nazwadocelowa) {
        File plik = new File(zrodlo);
        if (plik.exists()) {
            plik.renameTo(new File(nazwadocelowa));
        }
    }
    
    public static File plikTmp(String nazwa, boolean temp) {
        File file = null;
        String pelnanazwa = "d://"+nazwa+".pdf";
        if (temp == true) {
            file = new File(pelnanazwa);
            file.deleteOnExit();
        } else {
            file = new File(pelnanazwa);
        }
        return file;
    }
    
    public static BufferedOutputStream plikRTmp(String nazwa) {
        BufferedOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new BufferedOutputStream(new FileOutputStream(nazwa));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Plik.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return fileOutputStream;
        }
    }
}
