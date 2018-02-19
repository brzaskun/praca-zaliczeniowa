package filereader;

import dao.STRDAO;
import entity.Klienci;
import entity.SrodekTrw;
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
import javax.inject.Named;
import session.SessionFacade;


@Named
public class ReadWriteTextFileJDK7 implements Serializable{
  
  

  final static String FILE_NAME = "C:\\Temp\\dane.txt";
  final static String OUTPUT_FILE_NAME = "C:\\Temp\\outputdane.txt";
  final static Charset ENCODING = StandardCharsets.UTF_8;
  
  public static void main(String... aArgs) throws IOException {
      ReadWriteTextFileJDK7 text = new ReadWriteTextFileJDK7();
      //treat as a large file - use some buffering
      text.readLargerTextFile(FILE_NAME);
      List<String> lines = Arrays.asList("Down to the Waterline", "Water of Love");
      text.writeLargerTextFile(OUTPUT_FILE_NAME, lines);
  }
  
    
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
        Klienci klient = new Klienci();
        Klienci klientX = new Klienci();
        SrodekTrw str = new SrodekTrw();
//        KlienciDAO kDAO = new KlienciDAO();
        SessionFacade sf = new SessionFacade();
        STRDAO sdao = new STRDAO();
        Magazyn magazyn = new Magazyn();
        String przechowalnia;
        String wynik = String.valueOf(aMsg);
        if (wynik.contains("Kontrahent")) {
        } else if (wynik.contains("nazwa")) {
            String tmp = String.valueOf(aMsg).substring(8).trim();
            try {
                przechowalnia = tmp;
                magazyn.setJeden(przechowalnia);
                klient.setNpelna(przechowalnia);
                klientX.setNpelna(przechowalnia);
                str.setNazwa(tmp);
            } catch (Exception e) {
            }
        } else if (wynik.contains("nip")) {
            String tmp = String.valueOf(aMsg).substring(6).trim();
            przechowalnia = tmp;
            try {
                magazyn.setDwa(przechowalnia);
                klient.setNip(przechowalnia);
                klientX.setNip(przechowalnia);
                str.setSymbol(tmp);
            } catch (Exception e) {
            }
            
        } else {
            try {
                sf.create(str);
            } catch (Exception e) {
            }
        }
    }
}
