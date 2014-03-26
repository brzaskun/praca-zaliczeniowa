/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
public abstract class DAO<T> {

    @Inject
    private SessionFacade sessionFacade;
    private Class<T> entityClass;
    /**
     *
     */
    protected ArrayList<T> downloaded;

    protected DAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
   
    /**
     *
     * @param selected
     */
    
    public void dodaj(T selected) {
        try {
            if(sessionFacade==null){
            } else {
            }
            sessionFacade.create(selected);
        } catch (Exception e) {
            throw new PersistenceException();
        }
    }

    /**
     *
     * @param selected
     */
    public void destroy(T selected) {
        try {
            sessionFacade.remove(selected);
        } catch (Exception e) {
        }
    }

    /**
     *
     * @param selected
     */
    public void edit(T selected) {
        try {
            sessionFacade.edit(selected);
        } catch (Exception e) {
        }
    }

    /**
     *
     * @return
     */
    public ArrayList<T> getDownloaded() {
        return downloaded;
    }

    /**
     *
     * @param downloaded
     */
    public void setDownloaded(ArrayList<T> downloaded) {
        this.downloaded = downloaded;
    }
    
    
}
