/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Osito
 */
public class ImportScanFolderXls {
    public static void main(String[] args) {
        try {
            Files.newDirectoryStream(Paths.get("E:\\Biuro\\Firmy\\_MAŁGOSIA\\Murawski Grzegorz\\Niemcy deklaracje\\012020\\Faktury"),
                    path -> path.toString().endsWith(".xls"))
                    .forEach(System.out::println);
        } catch (IOException ex) {
            // Logger.getLogger(ImportScanFolderXls.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
