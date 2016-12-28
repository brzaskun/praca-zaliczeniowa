/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication3;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 *
 * @author Osito
 */
public class SystemVer {
    public interface activeds extends Library {
        void printf(String format, Object... args);
        public long GetSystemTime();
    }
    
    public static void main(String[] args) throws InterruptedException {
        activeds lib = (activeds) Native.loadLibrary("kernel32", activeds.class);
        int structureAlignment = Native.getStructureAlignment(activeds.class);
        System.out.println("koniec "+lib.GetSystemTime());
    }
}
