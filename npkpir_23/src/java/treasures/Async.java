/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treasures;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.apache.commons.math3.util.CombinatoricsUtils.factorial;

/**
 *
 * @author Osito
 */
public class Async {
    
    
    public static void main(String[] args) {
        try {
            int number = 4;
            CompletableFuture<Long> completableFuture = CompletableFuture.supplyAsync(() -> factorial(number));
            CompletableFuture<Long> completableFuture1 = CompletableFuture.supplyAsync(() -> factorial(number*12));
            CompletableFuture<Long> completableFuture2 = CompletableFuture.supplyAsync(() -> factorial(number*125));
            if (completableFuture.isDone()) {
                System.out.println("CompletableFuture is not finished yet...");
            }
            long result = completableFuture.get();
            System.out.println("Result "+result);
            if (completableFuture1.isDone()) {
                System.out.println("CompletableFuture is not finished yet...");
            }
            long result1 = completableFuture1.get();
            if (completableFuture1.isDone()) {
                System.out.println("CompletableFuture1 is not finished yet...");
            }
            System.out.println("Result "+result1);
            long result2 = completableFuture2.get();
            if (completableFuture2.isDone()) {
                System.out.println("CompletableFuture1 is not finished yet...");
            }
            System.out.println("Result "+result2);
        } catch (InterruptedException ex) {
            Logger.getLogger(Async.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(Async.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
