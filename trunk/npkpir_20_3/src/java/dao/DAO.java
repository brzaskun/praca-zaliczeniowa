/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Dok;
import entity.Sesja;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
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
            sessionFacade.create(selected);
            System.out.println("Dodano wpis " + selected);
        } catch (Exception e) {
            System.out.println("Nie dodano wpisu " + selected + " " + e.toString());
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
            System.out.println("Usunieto " + selected);
        } catch (Exception e) {
            System.out.println("Nie usnieto " + selected + " " + e.toString());
        }
    }

    /**
     *
     * @param selected
     */
    public void edit(T selected) {
        try {
            sessionFacade.edit(selected);
            System.out.println("Wyedytowano " + selected);
        } catch (Exception e) {
            System.out.println("Edycja nieudana " + selected + " " + e.toString());
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
