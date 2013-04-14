/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entity.Adminnews;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import msg.Msg;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class AdminNews {
    
    private String nowynews;
    @Inject Adminnews adminnews;
    @Inject SessionFacade sessionFacade;
    private List<Adminnews> newslist;

    public AdminNews() {
        newslist = new ArrayList<>();
    }

    @PostConstruct
    private void init(){
        newslist = sessionFacade.findAll(Adminnews.class);
        int wielkosc = newslist.size();
        if(wielkosc>6){
            newslist = newslist.subList(wielkosc-6, wielkosc);
        }
    }
    
    
    public void dodaj(){
        Adminnews an = new Adminnews();
        an.setData(new Date());
        an.setTresc(nowynews);
        sessionFacade.create(an);
        newslist.add(an);
        Msg.msg("i","Nowy news dodany","form:messages");
    }
    
    public void usun(){
        sessionFacade.remove(adminnews);
        newslist.remove(adminnews);
        Msg.msg("i","News usuniety","form:messages");
    }
    
    public String getNowynews() {
        return nowynews;
    }

    public void setNowynews(String nowynews) {
        this.nowynews = nowynews;
    }

    public List<Adminnews> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<Adminnews> newslist) {
        this.newslist = newslist;
    }

    public Adminnews getAdminnews() {
        return adminnews;
    }

    public void setAdminnews(Adminnews adminnews) {
        this.adminnews = adminnews;
    }
    
    
}
