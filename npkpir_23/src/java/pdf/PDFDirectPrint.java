/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pdf;

import com.lowagie.tools.Executable;
import java.io.IOException;
import msg.Msg;

/**
 *
 * @author Osito
 */
public class PDFDirectPrint {
       public static void silentPrintPdf(String nazwapliku) {
         try{
               Executable.printDocumentSilent(nazwapliku);
               Msg.msg("i", "Wysłano dokument na drukarkę");
            }catch(IOException e){
               e.printStackTrace();
               Msg.msg("e", "Nie udało się wydrukować. Wsytąpił błąd");
            }        
    }
}
