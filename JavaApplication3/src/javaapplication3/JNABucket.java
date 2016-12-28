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
public class JNABucket {

    public static void main(String args[]) {
        JNAApiInterface jnaLib = JNAApiInterface.INSTANCE;
        jnaLib.printf("Hello World");
        String testName = null;
//        for (int i = 0; i < args.length; i++) {
//            jnaLib.printf("\nArgument %d : %s", i, args[i]);
//        }
        jnaLib.printf("\nPlease Enter Your Name\n");
//        jnaLib.scanf("%s", testName);
//        jnaLib.printf("\nYour name is %s", testName);
    }
}
