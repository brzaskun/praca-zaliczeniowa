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
        if(numer.equals("1113")){
        this.Westep =  "<Deklaracja xmlns=\"http://crd.gov.pl/wzor/2013/04/09/1113/\" xmlns:etd=\"http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2011/06/21/eD/DefinicjeTypy/\">";    
        } else {
        this.Westep =  "<Deklaracja xmlns=\"http://crd.gov.pl/wzor/2013/01/17/"+numer+"/\" xmlns:etd=\"http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2011/06/21/eD/DefinicjeTypy/\">";
        }
    }
    public String getWestep() {
        return Westep;
    }

    
}

