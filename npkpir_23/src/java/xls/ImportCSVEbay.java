/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import embeddable.FakturaEbay;
import embeddable.FakturaCis;
import error.E;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import treasures.Filtrcsvbezsrednika;


/**
 *
 * @author Osito
 */
public class ImportCSVEbay {

    
//    
//    public static void main(String[] args) {
//        try {
//            GsonBuilder builder = new GsonBuilder();
//            builder.setPrettyPrinting();
//            Gson gson = builder.create();
//            BufferedReader bufferedReader = new BufferedReader(
//             new FileReader("D:\\Cis.json"));
//            Type type = new TypeToken<HashMap<String, FakturaCis>>() {}.getType();
//            Map<String, FakturaCis> map = gson.fromJson(bufferedReader, type);
//            for (FakturaCis data : map.values()) {
//                error.E.s(data);
//            }
//            error.E.s("");
//        } catch (Exception e) {
//            E.e(e);
//        }
//
//    }

    
    public static List<FakturaEbay> pobierz(InputStream is) {
        List<FakturaEbay> zwrot = new ArrayList<>();
        FakturaEbay tmpzwrot = null;
        String line = "";
        String cvsSplitBy = ";";
        String numertransakcjiold = null;
        String numertransakcjinew = null;
        int linianr = 0;
        int licznikwierszy = 0;
        try {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "windows-1252"))) {
                List<FakturaEbay> wierszefaktury = new ArrayList<>();
                while ((line = br.readLine()) != null) {
                    line = Filtrcsvbezsrednika.usunsrednik(line, ';', '\"');
                    try {
                        if (linianr>0) {
                            // use comma as separator
                            String[] tmpline = line.split(cvsSplitBy);
                            numertransakcjinew = tmpline[0];
                            numertransakcjiold = numertransakcjiold == null ? tmpline[0]:numertransakcjiold;
                            if (numertransakcjinew.equals(numertransakcjiold)) {
                                if (licznikwierszy==0) {
                                    tmpzwrot = new FakturaEbay(tmpline,0);
                                    wierszefaktury.add(tmpzwrot);
                                    licznikwierszy++;
                                } else {
                                    tmpzwrot = new FakturaEbay(tmpline,1);
                                    wierszefaktury.add(tmpzwrot);
                                }
                            } else {
                                licznikwierszy = 0;
                                tmpzwrot = zrobjednafakture(wierszefaktury);
                                if (tmpzwrot!=null) {
                                    zwrot.add(tmpzwrot);
                                }
                                wierszefaktury = new ArrayList<>();
                                tmpzwrot = new FakturaEbay(tmpline,0);
                                wierszefaktury.add(tmpzwrot);
                                numertransakcjiold = tmpline[0];
                                licznikwierszy++;
                            }
                        } else {
                            linianr++;
                        }
                    } catch (Exception ex) {
                        E.e(ex);
                    }
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            E.e(ex);
        }
        return zwrot;
    }    

    private static FakturaEbay zrobjednafakture(List<FakturaEbay> wierszefaktury) {
        FakturaEbay zwrot = wierszefaktury.get(0);
        if (zwrot.getPayPalTransaktionsID()!=null&& zwrot.getPayPalTransaktionsID().length()>0 && !zwrot.getGesamtpreis().equals("0")) {
            int wielkosc = wierszefaktury.size();
            if (wielkosc>1) {
                zwrot.setInklusiveMehrwertsteuersatz(wierszefaktury.get(1).getInklusiveMehrwertsteuersatz());
                zwrot.setArtikelnummer(wierszefaktury.get(1).getArtikelnummer());
                zwrot.setArtikelbezeichnung(wierszefaktury.get(1).getArtikelbezeichnung());
            }
        } else {
            zwrot = null;
        }
        return zwrot;       
    }
    
}
