/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package msg;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Osito
 */
public class Msg implements Serializable {
    
    /**
     *
     * @param severitylevel
     * @param messagetext
     * @param updateelement
     */
    public static void msg(String severitylevel,String messagetext, String updateelement){
          Severity sl = null;
          switch (severitylevel){
              case "i": sl = FacesMessage.SEVERITY_INFO;
                  break;
              case "e": sl = FacesMessage.SEVERITY_ERROR;
                  break;
              case "w": sl = FacesMessage.SEVERITY_WARN;
                  break;
              case "f": sl = FacesMessage.SEVERITY_FATAL;
                  break;
          }
          FacesMessage msg = new FacesMessage(sl,messagetext, null);
          FacesContext.getCurrentInstance().addMessage(null, msg);
          PrimeFaces.current().ajax().update(updateelement);
    }
    
    public static void msg(String severitylevel,String summary, String details, String updateelement){
          Severity sl = null;
          switch (severitylevel){
              case "i": sl = FacesMessage.SEVERITY_INFO;
                  break;
              case "e": sl = FacesMessage.SEVERITY_ERROR;
                  break;
              case "w": sl = FacesMessage.SEVERITY_WARN;
                  break;
              case "f": sl = FacesMessage.SEVERITY_FATAL;
                  break;
          }
          FacesMessage msg = new FacesMessage(sl,summary, details);
          FacesContext.getCurrentInstance().addMessage(null, msg);
          PrimeFaces.current().ajax().update(updateelement);
    }
    
//    public static void msg(String severitylevel,String summary, String details, String updateelement, String formelement){
//          Severity sl = null;
//          switch (severitylevel){
//              case "i": sl = FacesMessage.SEVERITY_INFO;
//                  break;
//              case "e": sl = FacesMessage.SEVERITY_ERROR;
//                  break;
//              case "w": sl = FacesMessage.SEVERITY_WARN;
//                  break;
//              case "f": sl = FacesMessage.SEVERITY_FATAL;
//                  break;
//          }
//          FacesMessage msg = new FacesMessage(sl,summary, details);
//          FacesContext.getCurrentInstance().addMessage(formelement, msg);
//          PrimeFaces.current().ajax().update(updateelement);
//    }
    
    public static void msg(String severitylevel,String messagetext){
          Severity sl = null;
          switch (severitylevel){
              case "i": sl = FacesMessage.SEVERITY_INFO;
                  break;
              case "e": sl = FacesMessage.SEVERITY_ERROR;
                  break;
              case "w": sl = FacesMessage.SEVERITY_WARN;
                  break;
              case "f": sl = FacesMessage.SEVERITY_FATAL;
                  break;
          }
          FacesMessage msg = new FacesMessage(sl,messagetext, null);
          FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
     public static void msg(String messagetext){
          Severity sl = FacesMessage.SEVERITY_INFO;
          FacesMessage msg = new FacesMessage(sl,messagetext, null);
          FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public static void uP(){
          Severity sl = FacesMessage.SEVERITY_INFO;
          FacesMessage msg = new FacesMessage(sl,"Usunięto pozycję z tabeli", null);
          FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    public static void uPe(){
          Severity sl = FacesMessage.SEVERITY_ERROR;
          FacesMessage msg = new FacesMessage(sl,"Wystąpił błąd, nie usunięto pozycji z tabeli", null);
          FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public static void dP(){
          Severity sl = FacesMessage.SEVERITY_INFO;
          FacesMessage msg = new FacesMessage(sl,"Operacja zakończona sukcesem", null);
          FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public static void dPe(){
          Severity sl = FacesMessage.SEVERITY_ERROR;
          FacesMessage msg = new FacesMessage(sl,"Wystąpił błąd, operacja nie udana", null);
          FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public static FacesMessage validator(String severitylevel,String messagetext){
          Severity sl = null;
          switch (severitylevel){
              case "i": sl = FacesMessage.SEVERITY_INFO;
                  break;
              case "e": sl = FacesMessage.SEVERITY_ERROR;
                  break;
              case "w": sl = FacesMessage.SEVERITY_WARN;
                  break;
              case "f": sl = FacesMessage.SEVERITY_FATAL;
                  break;
          }
          return new FacesMessage(sl,messagetext, null);
    }
}
