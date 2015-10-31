/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pushService;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class GrowlBean {

    private final static String CHANNEL_1 = "/notify";
    private final static String CHANNEL_2 = "/ksiegowa";
    private String text, summary, detail;
    //getters-setters

    public void send_ch1() {
        EventBus eventBus = EventBusFactory.getDefault().eventBus();
        eventBus.publish(CHANNEL_1, new FacesMessage(summary, detail));
    }
    
    public void send_ch2() {
        EventBus eventBus = EventBusFactory.getDefault().eventBus();
        eventBus.publish(CHANNEL_2, new FacesMessage(summary, detail));
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    
    
}
