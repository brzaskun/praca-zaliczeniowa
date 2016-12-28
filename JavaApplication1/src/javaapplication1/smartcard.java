/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import java.lang.reflect.Field;
import java.security.KeyStore;
import java.security.KeyStoreSpi;
import java.util.Enumeration;
import java.util.logging.Level;


/**
 *
 * @author Osito
 */
public class smartcard {

//    public static void main(String[] args) {
//        KeyStore keyStore = KeyStore.getInstance(getKeyStoreType(), "SunMSCAPI");
//keyStore.load(null, null);
//
//try {
//    Field field = keyStore.getClass().getDeclaredField("keyStoreSpi");
//    field.setAccessible(true);
//
//    KeyStoreSpi keyStoreVeritable = (KeyStoreSpi)field.get(keyStore);
//    field = keyStoreVeritable.getClass().getEnclosingClass().getDeclaredField("entries");
//    field.setAccessible(true);
//} catch (Exception e) {
//    LOGGER.log(Level.SEVERE, "Set accessible keyStoreSpi problem", e);
//}
//
//Enumeration enumeration = keyStore.aliases();
//    }
}
