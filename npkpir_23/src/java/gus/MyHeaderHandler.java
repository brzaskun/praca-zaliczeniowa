/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gus;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import org.slf4j.LoggerFactory;


/**
 *
 * @author Osito
 */
public class MyHeaderHandler implements SOAPHandler<SOAPMessageContext> {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(MyHeaderHandler.class);
    private final String sid;

    public MyHeaderHandler(String sid) {
        this.sid = sid;
    }
    
    
@Override
    public boolean handleMessage(SOAPMessageContext context) {
        try {
            SOAPMessage message = context.getMessage();
            SOAPHeader header = message.getSOAPHeader();
            SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
            if (header == null) {
                header = envelope.addHeader();
            }
            QName qNameUserCredentials = new QName("http://tempuri.org/", "sid");
            SOAPHeaderElement userCredentials = header.addHeaderElement(qNameUserCredentials);
            userCredentials.addTextNode(this.sid);
            message.saveChanges();
            //TODO: remove this writer when the testing is finished
            StringWriter writer = new StringWriter();
            message.writeTo(new StringOutputStream(writer));
            LOGGER.log(Level.INFO,"SOAP message: \n" + writer.toString());
        } catch (SOAPException e) {
            LOGGER.log(Level.SEVERE,"Error occurred while adding credentials to SOAP header.", e);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,"Error occurred while writing message to output stream.", e);
        }
        return true;
    }   

    //TODO: remove this class after testing is finished
    private static class StringOutputStream extends OutputStream {

        private StringWriter writer;

        public StringOutputStream(StringWriter writer) {
            this.writer = writer;
        }


        @Override
        public void write(int b) throws IOException {
            writer.write(b);
        }
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

    @Override
    public void close(MessageContext context) {
    }

    @Override
    public Set<QName> getHeaders() {
        return null;
    }
}
