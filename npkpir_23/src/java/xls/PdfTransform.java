/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.spire.pdf.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import plik.Plik;


/**
 *
 * @author Osito
 */
public class PdfTransform {
    
    
    

        public static void readText(File file, String charset) {
            FileInputStream in = null;
            ByteArrayOutputStream out = null;
            try {
                in = new FileInputStream(file);
                out = new ByteArrayOutputStream();
                byte[] data = new byte[1024];
                int p;
                while ((p = in.read(data)) != -1) {
                    out.write(data, 0, p);
                }
                if (charset == null) {
                    System.out.println(out.toString());
                } else {
                    
                    String b = new String(out.toByteArray(), charset);
                    System.out.println(b);
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("");
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    
     public static void main(String[] args){
        try {
            File f =  new File("D:\\Rach.pdf");
            readText(f, "UTF-8");
//            String inputFile = "D:\\Rach.pdf";
//            String outputFile = "D:\\Rach.pcl";
//
//            //Open pdf document
//            PdfDocument pdf = new PdfDocument();
//            pdf.loadFromFile(inputFile);
//            pdf.saveToFile(outputFile,FileFormat.PCL);
//            System.out.println("koniec");
        } catch (Exception ex) {
        }
}
}
