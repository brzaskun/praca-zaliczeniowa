/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Osito
 */
public class BankImportWykaz {
    private static final List<ImportowanyPlik> WYKAZ = new ArrayList<ImportowanyPlik>(Arrays.asList(
        new ImportowanyPlik("Bank PeKaO SA xml","xml",1),
        new ImportowanyPlik("Santander csv ;","csv",";",2),
        new ImportowanyPlik("Mbank csv ;","csv",";",3),
        new ImportowanyPlik("MT940 csv ;","csv",";",4),
        new ImportowanyPlik("Bank iPKO BP xls ;","xls",5),
        new ImportowanyPlik("BNP Paribas BP csv ;","csv",6),
        new ImportowanyPlik("ING xml","xml",7),
        new ImportowanyPlik("Bank iPKO BP biznes xls ;","xls",8),
        new ImportowanyPlik("Paypal csv ,","csv",9)
    ));

    
    public static void main (String[] args) {
        for (ImportowanyPlik p : WYKAZ) {
            error.E.s(p.getOpis());
        }
    }

    public static List<ImportowanyPlik> getWYKAZ() {
        return WYKAZ;
    }
    
    
}
