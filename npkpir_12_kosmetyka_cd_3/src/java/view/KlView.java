
package view;

import dao.KlienciDAO;
import entity.Klienci;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Osito
 */
@ManagedBean(name="KlView")
@ViewScoped
public class KlView implements Serializable{
    final static String FILE_NAME = "C:\\Temp\\dane.txt";
    final static String OUTPUT_FILE_NAME = "C:\\Temp\\outputdane.txt";
    final static Charset ENCODING = StandardCharsets.UTF_8;
    
    @Inject
    private KlienciDAO klDAO;
    @Inject
    private Klienci selected;
    
    private Integer ilesrodkow;

    
    public Klienci getSelected() {
        return selected;
    }

    public void setSelected(Klienci selected) {
        this.selected = selected;
    }
    
    public Integer getIlesrodkow() {
        return ilesrodkow;
    }

    public void setIlesrodkow(Integer ilesrodkow) {
        this.ilesrodkow = ilesrodkow;
    }

    public KlienciDAO getKlDAO() {
        return klDAO;
    }

    public void setKlDAO(KlienciDAO klDAO) {
        this.klDAO = klDAO;
    }

  
//    public void dodajKlienta(){
//      try { 
//        klDAO.dodajNowyWpis(selected);
//        RequestContext ctx = null;
//        ctx.getCurrentInstance().update("formX:");
//        } catch (Exception e) {
//        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Nowy srodek nie zachowany", "");
//        FacesContext.getCurrentInstance().addMessage(null, msg);
//        }
//         
//   }
//    
     public void dodajKlienta() throws IOException{
          readLargerTextFile(FILE_NAME);
    
   }
    
    public void readLargerTextFile(String aFileName) throws IOException {
    Path path = Paths.get(aFileName);
    try (Scanner scanner =  new Scanner(path, ENCODING.name())){
      int i = 0;
      while (scanner.hasNextLine()){
        String tmp = String.valueOf(scanner.nextLine());
        while(i<28){
        if(tmp.contains("bnazwa")){
            i++;
            break;
        } else if(tmp.contains("nazwa")){
            i++;
            selected.setNpelna(tmp.substring(8));
            break;
        } else if (tmp.contains("nip")) {
            i++;
            tmp = tmp.replace("-","");
            selected.setNip(tmp.substring(6));
            break;
        } else {
            i++;
            break;
        }
        }
        if(i==27){
           klDAO.dodajNowyWpis(selected);
           i=0;
        }
      }      
    }
  }
  
  
 
}
