package filereader;

import dao.KlienciDAO;
import dao.STRDAO;
import embeddable.Kl;
import entity.Klienci;
import entity.SrodekTrw;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import javax.inject.Inject;
import javax.inject.Named;
import session.STRFacade;

@Named
public class ReadWriteTextFileJDK7 implements Serializable{
  
  
  public static void main(String... aArgs) throws IOException{
    ReadWriteTextFileJDK7 text = new ReadWriteTextFileJDK7();
    //treat as a large file - use some buffering
    text.readLargerTextFile(FILE_NAME);
    List<String> lines = Arrays.asList("Down to the Waterline", "Water of Love");
    text.writeLargerTextFile(OUTPUT_FILE_NAME, lines);   
  }

  final static String FILE_NAME = "C:\\Temp\\dane.txt";
  final static String OUTPUT_FILE_NAME = "C:\\Temp\\outputdane.txt";
  final static Charset ENCODING = StandardCharsets.UTF_8;
  
 

  //For larger files
  
  void readLargerTextFile(String aFileName) throws IOException {
    Path path = Paths.get(aFileName);

    try (Scanner scanner =  new Scanner(path, ENCODING.name())){
      while (scanner.hasNextLine()){
        //process each line in some way
        log(scanner.nextLine());
      }      
    }
  }
  
    
  void writeLargerTextFile(String aFileName, List<String> aLines) throws IOException {
    Path path = Paths.get(aFileName);
    try (BufferedWriter writer = Files.newBufferedWriter(path, ENCODING)){
      for(String line : aLines){
        writer.write(line);
        writer.newLine();
      }
    }
  }

    public void log(Object aMsg) {
        Kl klient = new Kl();
        Klienci klientX = new Klienci();
        SrodekTrw str = new SrodekTrw();
//        KlienciDAO kDAO = new KlienciDAO();
        STRFacade sf = new STRFacade();
        STRDAO sdao = new STRDAO();
        Magazyn magazyn = new Magazyn();
        String przechowalnia;
        String wynik = String.valueOf(aMsg);
        if (wynik.contains("Kontrahent")) {
            System.out.println("Wykryłem kontrahenta");
            System.out.println(String.valueOf(aMsg));
        } else if (wynik.contains("nazwa")) {
            System.out.println(String.valueOf(aMsg).substring(8).trim());
            String tmp = String.valueOf(aMsg).substring(8).trim();
            try {
                przechowalnia = tmp;
                magazyn.setJeden(przechowalnia);
                klient.setNpelna(przechowalnia);
                klientX.setNpelna(przechowalnia);
                str.setNazwa(tmp);
            } catch (Exception e) {
                System.out.println("Blad magazyn.setJeden" + e.toString());
            }
        } else if (wynik.contains("nip")) {
            System.out.println(String.valueOf(aMsg).substring(6).trim());
            String tmp = String.valueOf(aMsg).substring(6).trim();
            przechowalnia = tmp;
            try {
                magazyn.setDwa(przechowalnia);
                klient.setNIP(przechowalnia);
                klientX.setNip(przechowalnia);
                str.setSymbol(tmp);
            } catch (Exception e) {
                System.out.println("Blad magazyn.setDwa" + e.toString());
            }
            
        } else {
            try {
              sf.create(str);
            } catch (Exception e) {
                System.out.println("Blad KlienciDAO" + e.toString());
            }
        }
        
    }
}
