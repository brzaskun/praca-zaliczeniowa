/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package deklaracjaVAT7_13;

import embeddable.PozycjeSzczegoloweVAT;
import embeddable.Schema;
import embeddable.Vatpoz;
import entity.DeklaracjaVatSchema;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Osito
 */
public class KwadracikiNaDole {
    private String kwadracikiNaDole = "";

    public KwadracikiNaDole() {
    }

    public KwadracikiNaDole(Vatpoz selected, DeklaracjaVatSchema schema) {
        PozycjeSzczegoloweVAT pozycjelista = selected.getPozycjeszczegolowe();
        String nazwaschemy = schema.getNazwaschemy();
        Date date = Calendar.getInstance().getTime();
        DateFormat formatt = new SimpleDateFormat("yyyy-MM-dd");
        String today = formatt.format(date);
        switch (nazwaschemy) {
            case "M-13":
            case "K-7":
                this.schemaM13K7(pozycjelista);
                break;
            case "M-14":
            case "K-8":
                this.schemaM14K8(pozycjelista, today);
                break;
            case "M-15":
                this.schemaM15(pozycjelista, today);
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

    private void schemaM14K8(PozycjeSzczegoloweVAT pozycjelista, String today){
        if(pozycjelista.getPoleI61()>0 && pozycjelista.getPoleI62()==0){
            kwadracikiNaDole = kwadracikiNaDole.concat("<P_61>1</P_61>");
        }
        if(pozycjelista.getPoleI61()>0 && pozycjelista.getPoleI62()>0){
            kwadracikiNaDole = kwadracikiNaDole.concat("<P_62>1</P_62>");
        }
        kwadracikiNaDole = kwadracikiNaDole.concat("<P_69>"+today+"</P_69>");
        kwadracikiNaDole = kwadracikiNaDole.concat("</PozycjeSzczegolowe>");
    }
    
    private void schemaM15(PozycjeSzczegoloweVAT pozycjelista, String today){
        if(pozycjelista.getPoleI54()>0 && pozycjelista.getPoleI55()==0){
            kwadracikiNaDole = kwadracikiNaDole.concat("<P_64>1</P_64>");
        }
        if(pozycjelista.getPoleI54()>0 && pozycjelista.getPoleI55()>0){
            kwadracikiNaDole = kwadracikiNaDole.concat("<P_65>1</P_65>");
        }
        kwadracikiNaDole = kwadracikiNaDole.concat("<P_72>"+today+"</P_72>");
        kwadracikiNaDole = kwadracikiNaDole.concat("</PozycjeSzczegolowe>");
    }
    
    public String getKwadracikiNaDole() {
        return kwadracikiNaDole;
    }

    public void setKwadracikiNaDole(String kwadracikiNaDole) {
        this.kwadracikiNaDole = kwadracikiNaDole;
    }
    
    
    
}
