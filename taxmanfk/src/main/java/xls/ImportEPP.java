/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import data.Data;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author Osito
 */
public class ImportEPP {
    
    public static void main(String[] args) {
        try {
            String filename = "D://sprzedaz.epp";
            //FileInputStream file = new FileInputStream(new File(filename));
            //Iterable<CSVRecord> recordss = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(new InputStreamReader(file, Charset.forName("windows-1252")));
            Path path = Paths.get(filename);
            // Java 8
            List<String> list = Files.readAllLines(path, Charset.forName("cp1250"));
            List<String> listbezinfo = list.subList(3, list.size()-1);
            List<String> rekordy = new ArrayList<>();
            StringBuilder sb = null;
            for (String p : listbezinfo) {
                if (p.equals("[NAGLOWEK]")) {
                    sb = new StringBuilder();
                } else if (p.equals("")) {
                    
                } else if (p.equals("[ZAWARTOSC]")) {
                    rekordy.add(sb.toString());
                    sb = null;
                } else if (p.equals("KONTRAHENCI")) {
                    sb = null;
                    break;
                } else {
                    if (sb!=null) {
                        sb.append(p);
                    }
                }
            }
           List<String> listfaktury = rekordy.stream().filter(p->p.startsWith("\"FS\",")).collect(Collectors.toList());
           List<String> listkorekty = rekordy.stream().filter(p->p.startsWith("\"KFS\",")).collect(Collectors.toList());
           listfaktury.addAll(listkorekty);
            //list2.forEach(System.out::println);
            List<CSVRecord> recordss = new ArrayList<>();
            for (String p : listfaktury) {
                Reader targetReader = new StringReader(p);
                Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(targetReader);
                records.forEach(recordss::add);
                System.out.println("");
            }
            
            String zmienkolejnoscEPP = Data.zmienkolejnoscEPP("20210802000000");
            System.out.println(zmienkolejnoscEPP);
            //"KONTRAHENCI"
            // close the reader
        } catch (Exception e) {
            System.out.println("BLAD");
        }
        
    }
    
}
