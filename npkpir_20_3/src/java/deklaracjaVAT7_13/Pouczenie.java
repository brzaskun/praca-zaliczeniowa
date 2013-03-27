/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deklaracjaVAT7_13;

/**
 *
 * @author Osito
 */
class Pouczenie {
    
    static String Pouczenie;

    public Pouczenie() {
        Pouczenie = "<ns:Pouczenie>W przypadku niewpłacenia w obowiązującym terminie kwoty z poz.58 lub wpłacenia jej w niepełnej wysokości, niniejsza deklaracja stanowi podstawę do wystawienia tytułu wykonawczego, zgodnie z przepisami ustawy z dnia 17 czerwca 1966 r. o postępowaniu egzekucyjnym w administracji (Dz.U. z 2012 r. poz. 1015, z późn. zm.).</ns:Pouczenie>";
    }
    
    public String getPouczenie() {
        return Pouczenie;
    }

    
}
