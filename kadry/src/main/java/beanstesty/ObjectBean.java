/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Osito
 */
public class ObjectBean {

    //object to byte array
    public static byte[] convertToBytes(Object object) {
        byte[] zwrot = null;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(object);
            zwrot = bos.toByteArray();
        } catch (Exception e) {
        }
        return zwrot;
    }
    //And the other way around:

    public static Object convertFromBytes(byte[] bytes) {
        Object zwrot = null;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                ObjectInputStream in = new ObjectInputStream(bis)) {
            zwrot = in.readObject();
        } catch (Exception e) {
        }
        return zwrot;
    }
}
