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
        this.Westep =  "<?xml version=\"1.0\" encoding=\"utf-8\"?><ns:Deklaracja xmlns:etd=\"http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2011/06/21/eD/DefinicjeTypy/\" xmlns:ns=\"http://crd.gov.pl/wzor/2013/01/17/"+numer+"/\" xmlns:vzd=\"http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2013/01/10/eD/VATZD/\" xmlns:vzt=\"http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2012/08/28/eD/VATZT/\" xmlns:vzz=\"http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2012/08/28/eD/VATZZ/\" xmlns:zzu=\"http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2011/10/07/eD/ORDZU/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://crd.gov.pl/wzor/2013/01/17/1085/ file:///C:/uslugi/schemat%20(2).xsd\">";
    }
    public String getWestep() {
        return Westep;
    }

    
}
