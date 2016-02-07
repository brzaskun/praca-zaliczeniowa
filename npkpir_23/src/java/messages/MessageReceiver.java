/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package messages;

import java.util.Date;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author Osito
 */


//@MessageDriven(mappedName="jms/pkpirQueue", activationConfig= {
//    @ActivationConfigProperty(propertyName="acknowledgeMode",
//        propertyValue="Auto-acknowledge"),
//    @ActivationConfigProperty(propertyName="destinationType",
//        propertyValue="javax.jms.Queue")
//})

@ManagedBean
@RequestScoped
public class MessageReceiver implements MessageListener{
    private static String wiadomosc;
    
    public MessageReceiver(){
    }

    @Override
    public void onMessage(Message message) {
        try{
            TextMessage tm = (TextMessage) message;
            Date date = new Date(tm.getJMSTimestamp());
            wiadomosc = date.toString().substring(12, 20) +" "+tm.getText();
            
        } catch (JMSException jex) {
        }
    }

    public String getWiadomosc() {
        return wiadomosc;
    }

    public void setWiadomosc(String wiadomosc) {
        MessageReceiver.wiadomosc = wiadomosc;
    }
    
    
}
