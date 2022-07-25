/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interceptor;

import dao.SesjaDAO;
import entity.Sesja;
import error.E;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Osito
 */
@Interceptor
public class WydrukInterceptor implements Serializable{
    
    @Inject
    private SesjaDAO sesjaDAO;
    
    @AroundInvoke
    public Object dodajwydruk(InvocationContext ctx) throws Exception {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        Object result = null;
        try {
            Sesja sesja = sesjaDAO.find(session.getId());
            error.E.s("sesia id "+session.getId());
            int ilosc = sesja.getIloscwydrukow();
            ilosc = ilosc + 1;
            sesja.setIloscwydrukow(ilosc);
            result = ctx.proceed();
            sesjaDAO.edit(sesja);
        } catch (Exception e) { 
            E.e(e);
        } finally {
            return result;
        }
    }
    
}
