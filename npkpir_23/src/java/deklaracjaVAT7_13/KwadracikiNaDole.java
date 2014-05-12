/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package deklaracjaVAT7_13;

import embeddable.PozycjeSzczegoloweVAT;
import embeddable.Schema;
import embeddable.Vatpoz;

/**
 *
 * @author Osito
 */
public class KwadracikiNaDole {
    private String kwadracikiNaDole = "";

    public KwadracikiNaDole() {
    }

    public KwadracikiNaDole(Vatpoz selected, Schema schema) {
        PozycjeSzczegoloweVAT pozycjelista = selected.getPozycjeszczegolowe();
        String nazwaschemy = schema.getNazwaschemy();
        switch (nazwaschemy) {
            case "M-13":
            case "K-7":
                this.schemaM13K7(pozycjelista);
                break;
            case "M-14":
            case "K-8":
                this.schemaM14K8(pozycjelista);
                break;
        }
    }
    
    //to niestety jest rozwiazanie dla 14-tki
       
       
    private void schemaM13K7(PozycjeSzczegoloweVAT pozycjelista){
        if(pozycjelista.getPoleI61()>0 && pozycjelista.getPoleI62()==0){
            kwadracikiNaDole = kwadracikiNaDole.concat("<P_71>1</P_71>");
        }
        if(pozycjelista.getPoleI61()>0 && pozycjelista.getPoleI62()>0){
            kwadracikiNaDole = kwadracikiNaDole.concat("<P_72>1</P_72>");
        }
        kwadracikiNaDole = kwadracikiNaDole.concat("</PozycjeSzczegolowe>");
    }

    private void schemaM14K8(PozycjeSzczegoloweVAT pozycjelista){
        if(pozycjelista.getPoleI61()>0 && pozycjelista.getPoleI62()==0){
            kwadracikiNaDole = kwadracikiNaDole.concat("<P_61>1</P_61>");
        }
        if(pozycjelista.getPoleI61()>0 && pozycjelista.getPoleI62()>0){
            kwadracikiNaDole = kwadracikiNaDole.concat("<P_62>1</P_62>");
        }
        kwadracikiNaDole = kwadracikiNaDole.concat("</PozycjeSzczegolowe>");
    }
    
    public String getKwadracikiNaDole() {
        return kwadracikiNaDole;
    }

    public void setKwadracikiNaDole(String kwadracikiNaDole) {
        this.kwadracikiNaDole = kwadracikiNaDole;
    }
    
    
    
}
