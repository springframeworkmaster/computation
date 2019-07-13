package com.tasks.threads.computation;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Phaser;

public class Task2 
{
	private static Phaser phaser = new Phaser(3);

    public static void main( String[] args )
    {
    	System.out.println("Main Thread: " + Thread.currentThread().getName());
    	CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
    		printMultiplesOf5Below5000();
    	});
    	
    	CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
    		printEvenNumbersBelow2000();
    	});
    	
    	CompletableFuture<Void> future3 =  CompletableFuture.runAsync(() -> {
    		printOddNumbersBelow2000();
    	});
    
    	while(!future1.isDone() && !future2.isDone() && !future3.isDone()) {
          sleep(1000L);
    	} 
    	System.out.println("Done");
    }
    
    private static void sleep(Long milliSeconds) {
		try {
			Thread.sleep(milliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    private static void printMultiplesOf5Below5000() {
		for(int i = 0, j = 0, k = 1; j<5000; i++, k++) {
			System.out.print(Thread.currentThread().getName() + " " +  (j = 5*(2 + i)) + " ");
			if(k == 10) {
				phaser.arriveAndAwaitAdvance();
				System.out.println();
		        k=0;
			}
		}	
    }
    
    private static void printEvenNumbersBelow2000() {
		for(int i = 1, k = 1; i<2001; i++, k++) {
			if( i % 2 == 0) System.out.print(Thread.currentThread().getName() + " " +  i + " ");
			if(k == 20) {
				phaser.arriveAndAwaitAdvance();
				System.out.println();
		        k=0;
			}
		}	
    }
    
    private static void printOddNumbersBelow2000() {
		for(int i = 1, k = 1; i<2001; i++, k++) {
			if( i % 2 == 1)	System.out.print(Thread.currentThread().getName() + " " +  i + " ");
			if(k == 20) {
				phaser.arriveAndAwaitAdvance();
				System.out.println();
		        k=0;  
			}
		}	
    }
}
