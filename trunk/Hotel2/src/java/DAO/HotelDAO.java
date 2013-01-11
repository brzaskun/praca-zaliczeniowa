/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import entity.Pokoj;
import entity.Rezerwacja;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;


/** 
 *
 * @author School
 */
@Named
@Stateless
public class HotelDAO implements Serializable{
    @Inject private SessionFacade sessionFacade;
    public static List<Pokoj> pokoje;
    public static List<Rezerwacja> rezerwacje;

    public HotelDAO() {
        pokoje = new ArrayList<Pokoj>();
        rezerwacje = new ArrayList<Rezerwacja>();
    }
    
    @PostConstruct
    public void init(){
        Collection c = null;
        try{
            c = sessionFacade.findAll(Pokoj.class);
            pokoje.addAll(c);
        } catch (Exception e){
            System.out.println("Baza danych pokoi pusta");
        }
        try{
            c = sessionFacade.findAll(Rezerwacja.class);
            rezerwacje.addAll(c);
        } catch (Exception e){
            System.out.println("Baza danych rezerwacji pusta");
        }
        
    }

    public List<Pokoj> getPokoje() {
        return pokoje;
    }

    public void setPokoje(List<Pokoj> pokoje) {
        HotelDAO.pokoje = pokoje;
    }

    public List<Rezerwacja> getRezerwacje() {
        return rezerwacje;
    }

    public void setRezerwacje(List<Rezerwacja> rezerwacje) {
        HotelDAO.rezerwacje = rezerwacje;
    }

    public SessionFacade getSessionFacade() {
        return sessionFacade;
    }

    public void setSessionFacade(SessionFacade sessionFacade) {
        this.sessionFacade = sessionFacade;
    }
    
  
 }

