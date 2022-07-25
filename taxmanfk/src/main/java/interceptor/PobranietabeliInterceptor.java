/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interceptor;

import dao.ZmianatablicyDAO;
import java.io.Serializable;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 *
 * @author Osito
 */
public class PobranietabeliInterceptor implements Serializable{
   
   @Inject private ZmianatablicyDAO zmianatablicyDAO;
    
   @AroundInvoke
   public Object businessIntercept(InvocationContext ctx) throws Exception {
       Object result = null;
       try {
               try {
                    zmianatablicyDAO.dodaj("class entity.Dok", false);
               } catch (Exception es) {
                   zmianatablicyDAO.edytuj("class entity.Dok", false);
               }
           // PreInvoke: do something before handing control to the next in chain
           result = ctx.proceed();
           return result;
       }catch (Exception e) {
        return null;
       } finally {
           // PostInvoke: do something (cleanup, etc) after the main processing is done
       }
}
}
