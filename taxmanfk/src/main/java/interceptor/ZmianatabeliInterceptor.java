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
public class ZmianatabeliInterceptor implements Serializable{
   
   @Inject private ZmianatablicyDAO zmianatablicyDAO;
    
   @AroundInvoke
   public Object businessIntercept(InvocationContext ctx) throws Exception {
       Object result = null;
       try {
           Object[] parameters = ctx.getParameters();
           Class param =  parameters[0].getClass();
           if(param.toString().equals("class entity.Dok")){
                try {
                    zmianatablicyDAO.dodaj(param.toString(),true);
               } catch (Exception es) {
                   zmianatablicyDAO.edytuj(param.toString(), true);
               }
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
