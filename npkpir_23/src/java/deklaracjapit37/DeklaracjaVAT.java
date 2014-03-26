/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deklaracjapit37;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Osito
 */
public class DeklaracjaVAT {

    public static void main(String[] args) {
        final String wiersz;
        Wstep wstep = new Wstep();
        Naglowek naglowek = new Naglowek();
        Podmiot podmiot = new Podmiot();
        PozycjeSzczegolowe pozycjeSzczegolowe = new PozycjeSzczegolowe();
        Pouczenie pouczenie = new Pouczenie();
        Oswiadczenie oswiadczenie = new Oswiadczenie();
        DaneAutoryzujace daneAutoryzujace = new DaneAutoryzujace();
        wiersz = wstep.getWestep()+naglowek.getNaglowek()+podmiot.getPodmiot()+pozycjeSzczegolowe.getPozycjeSzczegolowe()+pouczenie.getPouczenie()
                +oswiadczenie.getOswiadczenie()+daneAutoryzujace.getDaneAutoryzujace();
        FileWriter fileWriter = null;
        try {
            File newTextFile = new File("C:/uslugi/test6.xml");
            fileWriter = new FileWriter(newTextFile);
            fileWriter.write(wiersz);
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(DeklaracjaVAT.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fileWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(DeklaracjaVAT.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
     final String wiersz;
     Wstep wstep;
     Naglowek naglowek;
     Podmiot podmiot;
     PozycjeSzczegolowe pozycjeSzczegolowe;
     Pouczenie pouczenie;
     Oswiadczenie oswiadczenie;
     DaneAutoryzujace daneAutoryzujace;

    public DeklaracjaVAT() {
        this.wiersz = wstep.getWestep()+naglowek.getNaglowek();
    }

}
