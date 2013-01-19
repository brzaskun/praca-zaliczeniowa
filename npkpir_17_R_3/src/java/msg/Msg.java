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
          }
          FacesMessage msg = new FacesMessage(sl,messagetext, null);
          FacesContext.getCurrentInstance().addMessage(null, msg);
          RequestContext.getCurrentInstance().update(updateelement);
    }
}
