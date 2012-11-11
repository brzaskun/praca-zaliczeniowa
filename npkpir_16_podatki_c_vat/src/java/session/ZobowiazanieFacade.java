/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import embeddable.Mce;
import entity.Pitpoz;
import entity.Zobowiazanie;
import entity.ZobowiazaniePK;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Osito
 */
@Stateless
public class ZobowiazanieFacade extends AbstractFacade<Zobowiazanie> {
    @PersistenceContext(unitName = "npkpir_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ZobowiazanieFacade() {
        super(Zobowiazanie.class);
    }
    
    public Zobowiazanie find(String rok, String mc) throws Exception{
        try{
        Integer przesunmc = Mce.getMapamcyX().get(mc)+1;
        String pm = Mce.getMapamcy().get(przesunmc);
        Zobowiazanie tmp = (Zobowiazanie) em.createQuery("SELECT p FROM Zobowiazanie p WHERE p.zobowiazaniePK.rok = :rok AND p.zobowiazaniePK.mc = :mc").setParameter("rok", rok).setParameter("mc", pm).getSingleResult();
        return tmp;
        } catch (Exception e){
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Brak danych", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);   
        throw new Exception();
        }
    }
}
