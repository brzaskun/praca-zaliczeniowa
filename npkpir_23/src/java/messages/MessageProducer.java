/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package messages;



/**
 *
 * @author Osito
 */
//@Named
//@RequestScoped
public class MessageProducer {
//    @Resource(mappedName = "jms/pkpirQueue")
//    private Queue pkpirQueue;
//    @Resource(mappedName = "jms/pkpirQueueFactory")
//    private ConnectionFactory pkpirQueueFactory;
//    private String message;
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    
//     public void send() {
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//        try {
//            sendJMSMessageToPkpirQueue(message);
//            FacesMessage facesMessage = new FacesMessage("Message sent: " + message);
//            facesMessage.setSeverity(FacesMessage.SEVERITY_INFO);
//            facesContext.addMessage("messageForm:messageWiad", facesMessage);
//        } catch (JMSException jmse) {
//            FacesMessage facesMessage = new FacesMessage("Message NOT sent: " + message);
//            facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
//            facesContext.addMessage("messageForm:messageWiad", facesMessage);
//        }
//     }
//    
//    
//    private Message createJMSMessageForjmsPkpirQueue(Session session, Object messageData) throws JMSException {
//        // TODO create and populate message to send
//        TextMessage tm = session.createTextMessage();
//        tm.setText(messageData.toString());
//        return tm;
//    }
//
//    private void sendJMSMessageToPkpirQueue(Object messageData) throws JMSException {
//        Connection connection = null;
//        Session session = null;
//        try {
//            connection = pkpirQueueFactory.createConnection();
//            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//            javax.jms.MessageProducer messageProducer = session.createProducer(pkpirQueue);
//            messageProducer.send(createJMSMessageForjmsPkpirQueue(session, messageData));
//        } finally {
//            if (session != null) {
//                try {
//                    session.close();
//                } catch (JMSException e) {
//                    Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Cannot close session", e);
//                }
//            }
//            if (connection != null) {
//                connection.close();
//            }
//        }
//    }
//    
    
    
}
