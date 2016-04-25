/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import error.E;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
public abstract class DAO<T> {

    @Inject
    protected SessionFacade sessionFacade;
    private Class<T> entityClass;
    /**
     *
     */
    protected ArrayList<T> downloaded;

    protected DAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public DAO() {
    }

    /**
     *
     * @param selected
     */
    public void dodaj(T selected) {
        try {
            sessionFacade.create(selected);
        } catch (EJBException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getCause().getMessage(), e);
            throw new EJBException(e.getCause().getMessage(), e);
        } catch (Exception e) {
            E.e(e);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getCause().getMessage(), e);
            throw new PersistenceException(e.getCause().getMessage(), e);
        }
    }
    
    public void dodaj(List<T> selected) {
        try {
            sessionFacade.create(selected);
        } catch (EJBException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getCause().getMessage(), e);
            throw new EJBException(e.getCause().getMessage(), e);
        } catch (Exception e) {
            E.e(e);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getCause().getMessage(), e);
            throw new PersistenceException(e.getCause().getMessage(), e);
        }
    }

    /**
     *
     * @param selected
     */
    public void destroy(T selected) {
        try {
            sessionFacade.remove(selected);
        } catch (EJBException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getCause().getMessage(), e);
            throw new EJBException(e.getCause().getMessage(), e);
        } catch (Exception e) {
            E.e(e);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getCause().getMessage(), e);
            throw new PersistenceException(e.getCause().getMessage(), e);
        }
    }

    /**
     *
     * @param selected
     */
    public void edit(T selected) {
        try {
            sessionFacade.edit(selected);
        } catch (EJBException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getCause().getMessage(), e);
            throw new EJBException(e.getCause().getMessage(), e);
        } catch (Exception e) {
            E.e(e);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getCause().getMessage(), e);
            throw new PersistenceException(e.getCause().getMessage(), e);
        }
    }

    public void editList(List<T> selected) {
        try {
            sessionFacade.edit(selected);
        } catch (EJBException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getCause().getMessage(), e);
            throw new EJBException(e.getCause().getMessage(), e);
        } catch (Exception e) {
            E.e(e);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getCause().getMessage(), e);
            throw new PersistenceException(e.getCause().getMessage(), e);
        }
    }

    public void createListRefresh(List<T> selected) {
        try {
            sessionFacade.createRefresh(selected);
        } catch (EJBException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getCause().getMessage(), e);
            throw new EJBException(e.getCause().getMessage(), e);
        } catch (Exception e) {
            E.e(e);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getCause().getMessage(), e);
            throw new PersistenceException(e.getCause().getMessage(), e);
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
