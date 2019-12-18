package com.arif.java.rmi.chat.workers;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author arif
 */
public class MessageProducer implements Runnable{

    private BlockingQueue sharedMessageQueue;
    public MessageProducer(BlockingQueue _sharedMessageQueue){
        sharedMessageQueue = _sharedMessageQueue;
    }
    
    @Override
    public void run() {
        messageListener();
    }

    private void messageListener(){
        Scanner scanner = new Scanner(System.in);
        try{
            while(!Thread.currentThread().isInterrupted()){
                String message = scanner.nextLine();
                if(!message.isEmpty()){
                    sharedMessageQueue.put(message);
                }
            }
        }catch(InterruptedException ie){
            System.out.println("Exiting Chat");
        }catch(NoSuchElementException e){
            System.out.println("Exiting Chat");
        }
    }
}
