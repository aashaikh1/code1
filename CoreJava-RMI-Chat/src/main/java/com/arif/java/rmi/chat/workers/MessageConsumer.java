package com.arif.java.rmi.chat.workers;

import com.arif.java.rmi.chat.common.ChatPeer;
import com.arif.rmi.chat.peer.ChatPeerImpl;
import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

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
    private ChatPeerImpl localPeer;
    public MessageConsumer(BlockingQueue<String> _sharedMessageQueue, ConcurrentHashMap<String, ChatPeer> _peers, ChatPeerImpl _localpeer) throws RemoteException{
        sharedMessageQueue = _sharedMessageQueue;
        peers = _peers;
        localPeerName = _localpeer.getPeerName();
        localPeer = _localpeer;
        executor = Application.getInstance().getCachedThreadPool();
    }
    

    @Override
    public void run() {
        try{
            while(!Thread.currentThread().isInterrupted()){
                message = sharedMessageQueue.take();
                if(peers.isEmpty()){
                    System.out.println("No Peers connected to chat with");
                }else{
                    executor.execute(() -> { peers.values().stream().forEach(peer -> {
                        try{
                            peer.sendMessage(localPeerName , message);
                        }catch(ConnectException ce){
                            System.out.println(ce.getMessage() + ". Client might have exit without calling ChatPeer.remove which gets called if exit properly via Control C");
                            localPeer.removePeerLocally(peer);
                        }catch(RemoteException re){
                            System.out.println(re.getMessage());
                            localPeer.removePeerLocally(peer);
                        }
                    });});
                }
            }
        }catch(InterruptedException ie){
            System.out.println("Exiting Chat");
        }catch(Exception e){
            System.out.println("Exiting Chat " + e);
        }
    }
}
