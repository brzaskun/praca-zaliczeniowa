/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication3;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

/**
 *
 * @author Osito
 */
public interface JNAApiInterface extends Library {

    JNAApiInterface INSTANCE = (JNAApiInterface) Native.loadLibrary((Platform.isWindows() ? "msvcrt" : "c"), JNAApiInterface.class);

    void printf(String format, Object... args);

    int sprintf(byte[] buffer, String format, Object... args);

    int scanf(String format, Object... args);
}
