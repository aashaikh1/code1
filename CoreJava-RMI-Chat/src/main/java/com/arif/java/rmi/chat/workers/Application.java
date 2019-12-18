package com.arif.java.rmi.chat.workers;

import com.arif.rmi.chat.peer.ChatPeerImpl;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author arif
 */
public class Application {
    
    private static Application application;
    private ExecutorService executorFixed;
    private ExecutorService executorCached;
    
    private ChatPeerImpl localChatPeer;
    
    public static Application getInstance(){
        if(application == null){
            synchronized(Application.class){
                if(application == null){
                    application = new Application();
                }
            }
        }
        return application;
    } 
    
    private Application(){
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shuttingdown this ChatPeer...");
            if(executorFixed != null){
                executorFixed.shutdownNow();
            }
            if(executorFixed != null){
                executorFixed.shutdownNow();
            }
            if(localChatPeer != null){
                localChatPeer.deRegisterAllPeers();
            }
            System.out.println("Goodby");
        }));        
    }
    
    public synchronized ExecutorService getFixedThreadPool(int numOfThreads){
        if(executorFixed == null){
            synchronized(Application.class){
                if(executorFixed == null){
                    executorFixed = Executors.newFixedThreadPool(numOfThreads);
                }
            }
        }
        return executorFixed;
    }
    
    public synchronized ExecutorService getCachedThreadPool(){
        if(executorCached == null){
            synchronized(Application.class){
                if(executorCached == null){
                    executorCached = Executors.newCachedThreadPool();
                }
            }
        }
        return executorCached;
    }

    public ChatPeerImpl getLocalChatPeer() {
        return localChatPeer;
    }

    public void setLocalChatPeer(ChatPeerImpl localChatPeer) {
        this.localChatPeer = localChatPeer;
    }
    
    
}


