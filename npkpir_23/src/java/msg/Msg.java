/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package msg;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

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
          RequestContext.getCurrentInstance().update(updateelement);
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
          RequestContext.getCurrentInstance().update(updateelement);
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
//          RequestContext.getCurrentInstance().update(updateelement);
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
