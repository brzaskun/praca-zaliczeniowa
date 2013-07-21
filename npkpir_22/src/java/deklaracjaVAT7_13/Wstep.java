/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deklaracjaVAT7_13;

/**
 *
 * @author Osito
 */
class Wstep {
    
    final String Westep; 

    Wstep(String numer) {
        this.Westep =  "<Deklaracja xmlns=\"http://crd.gov.pl/wzor/"+numer+"\" xmlns:etd=\"http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2011/06/21/eD/DefinicjeTypy/\">";    

    }
    public String getWestep() {
        return Westep;
    }

    
}

