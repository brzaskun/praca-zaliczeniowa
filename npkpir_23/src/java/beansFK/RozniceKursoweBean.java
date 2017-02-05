/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import data.Data;
import entityfk.StronaWiersza;
import entityfk.Transakcja;
import error.E;
import java.util.List;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class RozniceKursoweBean {
    
    
    public static void rozliczroznicekursowe(Transakcja loop) {
        try {
            if (!loop.getRozliczajacy().getSymbolWaluty().equals("PLN") || !loop.getNowaTransakcja().getSymbolWaluty().equals("PLN")) {
                double placonakwota = loop.getKwotatransakcji();
                if (placonakwota != 0.0) {
                    double kursPlatnosci = loop.getRozliczajacy().getWiersz().getTabelanbp().getKurssredni();
                    double kursRachunku = loop.getNowaTransakcja().getKursWalutyBOSW();
                    if (kursPlatnosci == 0.0 && kursRachunku != 0.0) {
                        if (placonakwota > 0.0) {
                            double kwotaPlatnosciwWalucie = Z.z(placonakwota / kursRachunku);
                            double kwotaRachunkuwWalucie = loop.getNowaTransakcja().getKwota() - loop.getNowaTransakcja().getRozliczono() + placonakwota;
                            double kwotaRachunkuwPLN = kwotaRachunkuwWalucie * kursRachunku;
                            double roznicakursowa = Z.z(placonakwota - kwotaRachunkuwPLN);
                            if (roznicakursowa > 0.0) {
                                loop.setRoznicekursowe(roznicakursowa);
                            } else {
                                loop.setRoznicekursowe(0.0);
                            }
                            loop.setKwotawwalucierachunku(kwotaPlatnosciwWalucie > kwotaRachunkuwWalucie ? kwotaRachunkuwWalucie : kwotaPlatnosciwWalucie);
                        }
                    } else if (kursPlatnosci == 0.0 && kursRachunku == 0.0) {
                        if (placonakwota > 0.0) {
                            loop.setKwotawwalucierachunku(placonakwota);
                        }
                    } else if (kursPlatnosci != 0.0 && kursRachunku == 0.0) {
                        if (placonakwota > 0.0) {
                            double kwotaPlatnosciwPLN = Z.z(placonakwota * kursPlatnosci);
                            double kwotaRachunkuwPLN = loop.getNowaTransakcja().getKwota() - loop.getNowaTransakcja().getRozliczono() + placonakwota;
                            double roznicakursowa = Z.z(kwotaPlatnosciwPLN - kwotaRachunkuwPLN);
                            if (roznicakursowa > 0.0) {
                                loop.setRoznicekursowe(roznicakursowa);
                            } else {
                                loop.setRoznicekursowe(0.0);
                            }
                            loop.setKwotawwalucierachunku(kwotaPlatnosciwPLN > kwotaRachunkuwPLN ? kwotaRachunkuwPLN : kwotaPlatnosciwPLN);
                        }
                    } else if (kursPlatnosci != 0.0 && kursRachunku != 0.0) {
                        if (placonakwota > 0.0) {
                            double kwotaPlatnosciwPLN = Z.z(placonakwota * kursPlatnosci);
                            double kwotaRachunkuwPLN = Z.z(placonakwota * kursRachunku);
                            double roznicakursowa = Z.z(kwotaPlatnosciwPLN - kwotaRachunkuwPLN);
                            loop.setRoznicekursowe(roznicakursowa);
                            loop.setKwotawwalucierachunku(placonakwota);
                        }
                    }
                }
            }
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    
    
}
