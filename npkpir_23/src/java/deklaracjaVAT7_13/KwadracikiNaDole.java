/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package deklaracjaVAT7_13;

import embeddable.PozycjeSzczegoloweVAT;
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

    public KwadracikiNaDole(Vatpoz selected, DeklaracjaVatSchema schema, boolean vatzd, String nrtelefonu, boolean splitpayment) {
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
            case "K-9":
                this.schemaM15K9(pozycjelista, today);
                break;
            case "M-16":
            case "K-10":
                this.schemaM16K10(pozycjelista, today);
                break;
            case "M-17":
            case "K-11":
                this.schemaM17K11(pozycjelista, today, vatzd);
                break;
            case "M-18":
            case "K-12":
                this.schemaM18K12(pozycjelista, today, vatzd, nrtelefonu);
                break;
            case "M-19":
            case "K-13":
                this.schemaM19K13(pozycjelista, today, vatzd, nrtelefonu);
                break;
            case "M-20":
            case "K-14":
                this.schemaM20K14(pozycjelista, today, vatzd, nrtelefonu, splitpayment);
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
    
    private void schemaM15K9(PozycjeSzczegoloweVAT pozycjelista, String today){
        if(pozycjelista.getPoleI54()>0 && pozycjelista.getPoleI55()==0){
            kwadracikiNaDole = kwadracikiNaDole.concat("<P_64>1</P_64>");
        }
        if(pozycjelista.getPoleI54()>0 && pozycjelista.getPoleI55()>0){
            kwadracikiNaDole = kwadracikiNaDole.concat("<P_65>1</P_65>");
        }
        kwadracikiNaDole = kwadracikiNaDole.concat("<P_72>"+today+"</P_72>");
        kwadracikiNaDole = kwadracikiNaDole.concat("</PozycjeSzczegolowe>");
    }
    
    private void schemaM16K10(PozycjeSzczegoloweVAT pozycjelista, String today){
        if(pozycjelista.getPoleI55()>0 && pozycjelista.getPoleI56()==0){
            kwadracikiNaDole = kwadracikiNaDole.concat("<P_65>1</P_65>");
        }
        if(pozycjelista.getPoleI55()>0 && pozycjelista.getPoleI56()>0){
            kwadracikiNaDole = kwadracikiNaDole.concat("<P_66>1</P_66>");
        }
        kwadracikiNaDole = kwadracikiNaDole.concat("<P_73>"+today+"</P_73>");
        kwadracikiNaDole = kwadracikiNaDole.concat("</PozycjeSzczegolowe>");
    }
    //przystosowac w nowych dekalracjach numery pol
    private void schemaM17K11(PozycjeSzczegoloweVAT pozycjelista, String today, boolean vatzd){
        if(pozycjelista.getPoleI57()>0 && pozycjelista.getPoleI58()==0){
            kwadracikiNaDole = kwadracikiNaDole.concat("<P_66>1</P_66>");
        }
        if(pozycjelista.getPoleI57()>0 && pozycjelista.getPoleI58()>0){
            kwadracikiNaDole = kwadracikiNaDole.concat("<P_66>1</P_66>");
            kwadracikiNaDole = kwadracikiNaDole.concat("<P_67>1</P_67>");
        }
        if(vatzd) {
            kwadracikiNaDole = kwadracikiNaDole.concat("<P_68>1</P_68>");
        }
        kwadracikiNaDole = kwadracikiNaDole.concat("<P_73>918120976</P_73>");
        kwadracikiNaDole = kwadracikiNaDole.concat("<P_74>"+today+"</P_74>");
        kwadracikiNaDole = kwadracikiNaDole.concat("</PozycjeSzczegolowe>");
    }
    
    private void schemaM18K12(PozycjeSzczegoloweVAT pozycjelista, String today, boolean vatzd, String nrtelefonu){
        //inne kwadraciki 60 sa robione w PozycjeSzczegolowe bo ktos odwrocil kolejnosc kwadracikow
        if(vatzd) {
            kwadracikiNaDole = kwadracikiNaDole.concat("<P_71>1</P_71>");
        }
        kwadracikiNaDole = kwadracikiNaDole.concat("<P_75>"+nrtelefonu+"</P_75>");
        kwadracikiNaDole = kwadracikiNaDole.concat("<P_76>"+today+"</P_76>");
        kwadracikiNaDole = kwadracikiNaDole.concat("</PozycjeSzczegolowe>");
    }
    
    private void schemaM19K13(PozycjeSzczegoloweVAT pozycjelista, String today, boolean vatzd, String nrtelefonu){
        //inne kwadraciki 60 sa robione w PozycjeSzczegolowe bo ktos odwrocil kolejnosc kwadracikow
        if(vatzd) {
            kwadracikiNaDole = kwadracikiNaDole.concat("<P_69>1</P_69>");
        }
        kwadracikiNaDole = kwadracikiNaDole.concat("<P_73>"+nrtelefonu+"</P_73>");
        kwadracikiNaDole = kwadracikiNaDole.concat("<P_74>"+today+"</P_74>");
        kwadracikiNaDole = kwadracikiNaDole.concat("</PozycjeSzczegolowe>");
    }
    
    private void schemaM20K14(PozycjeSzczegoloweVAT pozycjelista, String today, boolean vatzd, String nrtelefonu, boolean splitpayment){
        //inne kwadraciki 60 sa robione w PozycjeSzczegolowe bo ktos odwrocil kolejnosc kwadracikow
        if(splitpayment) {
            kwadracikiNaDole = kwadracikiNaDole.concat("<P_69>1</P_69>");
        }
        if(vatzd) {
            kwadracikiNaDole = kwadracikiNaDole.concat("<P_70>1</P_70>");
        }
        kwadracikiNaDole = kwadracikiNaDole.concat("<P_74>"+nrtelefonu+"</P_74>");
        kwadracikiNaDole = kwadracikiNaDole.concat("<P_75>"+today+"</P_75>");
        kwadracikiNaDole = kwadracikiNaDole.concat("</PozycjeSzczegolowe>");
    }
    
    
    public String getKwadracikiNaDole() {
        return kwadracikiNaDole;
    }

    public void setKwadracikiNaDole(String kwadracikiNaDole) {
        this.kwadracikiNaDole = kwadracikiNaDole;
    }
    
    
    
}
