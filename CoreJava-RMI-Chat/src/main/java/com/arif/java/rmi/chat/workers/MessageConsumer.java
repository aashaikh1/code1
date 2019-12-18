package com.arif.java.rmi.chat.workers;

import com.arif.java.rmi.chat.common.ChatPeer;
import java.rmi.RemoteException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author arif
 */
public class MessageConsumer implements Runnable{
    
    private BlockingQueue<String> sharedMessageQueue;
    private ConcurrentHashMap<String, ChatPeer> peers;//ChatPeerImpl can add/remove peers from this map so wee need the same reference to send messages to all current listed peers with this local peer.
    private String localPeerName;
    private String message;//so it can be used in lambda 
    private ExecutorService executor;
    public MessageConsumer(BlockingQueue<String> _sharedMessageQueue, ConcurrentHashMap<String, ChatPeer> _peers, ChatPeer _localpeer) throws RemoteException{
        sharedMessageQueue = _sharedMessageQueue;
        peers = _peers;
        localPeerName = _localpeer.getPeerName();
        executor = Application.getInstance().getCachedThreadPool();
    }
    

    @Override
    public void run() {
        try{
            while(!Thread.currentThread().isInterrupted()){
                message = sharedMessageQueue.take();
                if(peers.isEmpty()){
                    System.out.println("No Peers connected to chat with");
                }
                executor.execute(() -> { peers.values().stream().forEach(peer -> {
                    try{
                        peer.sendMessage(localPeerName , message);
                    }catch(RemoteException re){
                        re.printStackTrace();
                    }
                });});
            }
        }catch(InterruptedException ie){
            System.out.println("Exiting Chat");
        }catch(Exception e){
            System.out.println("Exiting Chat " + e);
        }
    }
}
