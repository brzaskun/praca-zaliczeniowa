/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import embeddable.FakturaCis;
import error.E;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Osito
 */
public class ImportJsonCislowski {

    
    
    public static void main(String[] args) {
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            BufferedReader bufferedReader = new BufferedReader(
             new FileReader("D:\\Cis.json"));
            Type type = new TypeToken<HashMap<String, FakturaCis>>() {}.getType();
            Map<String, FakturaCis> map = gson.fromJson(bufferedReader, type);
            for (FakturaCis data : map.values()) {
                System.out.println(data);
            }
            System.out.println("");
        } catch (Exception e) {
            E.e(e);
        }

    }

    
    public static List<FakturaCis> pobierz(InputStream is) {
        List<FakturaCis> zwrot  = new ArrayList<>();
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            BufferedReader bufferedReader = new BufferedReader(
             new InputStreamReader(is, "UTF-8"));
            Type type = new TypeToken<HashMap<String, FakturaCis>>() {}.getType();
            Map<String, FakturaCis> map = gson.fromJson(bufferedReader, type);
            zwrot.addAll(map.values());
        } catch (Exception e) {
            E.e(e);
        }
        return zwrot;
    }

    
    
}
