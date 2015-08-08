/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plik;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Osito
 */
public class Plik {
    
    private static final String katalog = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/";
    
    public static File plik(String nazwa, boolean temp) {
        File file = null;
        String pelnanazwa = katalog+nazwa;
        if (temp == true) {
            file = new File(pelnanazwa);
            file.deleteOnExit();
        } else {
            file = new File(pelnanazwa);
        }
        return file;
    }
    
    public static FileOutputStream plikR(String nazwa) {
        FileOutputStream fileOutputStream = null;
        String pelnanazwa = katalog+nazwa;
        try {
            fileOutputStream = new FileOutputStream(pelnanazwa);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Plik.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return fileOutputStream;
        }
    }
    
}
