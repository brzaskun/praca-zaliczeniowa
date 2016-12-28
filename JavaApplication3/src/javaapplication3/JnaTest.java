/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication3;

/**
 *
 * @author Osito
 */
public class JnaTest {

    public static void main(String[] args) throws InterruptedException {
        JNAApiInterface jnaLib = JNAApiInterface.INSTANCE;
        int TOTAL = 1000;
        long start = System.currentTimeMillis();
        for (int i = 0; i < TOTAL; i++) {
            jnaLib.printf("Hello, World 1\n");
        }
        long start2 = System.currentTimeMillis();
        for (int i = 0; i < TOTAL; i++) {
            System.out.print("Hello, World 2\n");
        }
        long end = System.currentTimeMillis();
        System.out.flush();
        Thread.sleep(1000);
        System.out.println("***** ratio: ***** " + (start2 - start) / (end - start2));
    }
}
