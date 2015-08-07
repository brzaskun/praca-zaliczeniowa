/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plik;

import java.io.File;

/**
 *
 * @author Osito
 */
public class Plik {
    public static File plik(String nazwa, boolean temp) {
        File file = null;
        String katalog = "";
        String pelnanazwa = katalog+nazwa;
        if (temp == true) {
            file = new File(pelnanazwa);
            file.deleteOnExit();
        } else {
            file = new File(pelnanazwa);
        }
        return file;
    }
}
