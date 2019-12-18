package com.arif.rmi.chat.peer;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import com.arif.java.rmi.chat.common.ChatPeer;
import com.arif.java.rmi.chat.workers.Application;
import com.arif.java.rmi.chat.workers.MessageConsumer;
import com.arif.java.rmi.chat.workers.MessageProducer;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author arif
 */
public class ChatPeerImpl extends UnicastRemoteObject implements ChatPeer{
    
    private String peerName;
    private ConcurrentHashMap<String, ChatPeer> peers;
    private static final long serialVersionUID = 1234L;

    protected ChatPeerImpl(String _peerName) throws RemoteException {
        this.peerName = _peerName;
        this.peers = new ConcurrentHashMap<String, ChatPeer>();
    }

    public ConcurrentHashMap<String, ChatPeer> getPeers(){
        return this.peers;
    }
    
    @Override
    public synchronized void sendMessage(String peerName, String message) throws RemoteException {
        System.out.println(peerName + ": " + message);
    }

    @Override
    public synchronized void addPeer(ChatPeer peer) throws RemoteException {
        if(peers.containsKey(peer.getPeerName())){
            throw new RemoteException(peer.getPeerName() + " is already in my chat list" );
        }
        peers.put(peer.getPeerName(), peer);
    }

    @Override
    public synchronized void removePeer(ChatPeer peer) throws RemoteException {
        if(!peers.containsKey(peer.getPeerName())){
            throw new RemoteException(peer.getPeerName() + " not in my chat listt" );
        }
        peers.remove(peer.getPeerName());
        System.out.println("Peer : " + peer.getPeerName() + " has terminated their chat session.");
    }

    @Override
    public String getPeerName() throws RemoteException {
        return this.peerName;
    }
    
    public synchronized void deRegisterAllPeers(){
        peers.values().stream().forEach(peer -> { //make it parallelstream
            try{
                peer.removePeer(this);
            }catch(RemoteException re){
                re.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        try {
            if(args.length < 2){
                System.out.println("Mandatory arguments missing");
                System.out.println("Usage: chatName localPortNum RMI-URL-OF-Other-Chat-Instance");
                System.out.println("First two arguments are mandatory, third arguments is needed to connect with other chat instance");
                System.exit(1);
            }
            
            String thisPeerName = args[0];
            if (System.getSecurityManager() == null){
                System.setSecurityManager(new RMISecurityManager());
            }
            ChatPeerImpl thisLocalPeer = new ChatPeerImpl(thisPeerName);
            Application.getInstance().setLocalChatPeer(thisLocalPeer);

            int rmiLocalPort = Integer.parseInt(args[1]);
            Registry rmiRegistry = LocateRegistry.createRegistry(rmiLocalPort);
            rmiRegistry.bind("ChatPeer", thisLocalPeer);            
            
            if(args.length > 2){
                System.out.println("Connecting to: " + args[2]);
                ChatPeer remotePeer = (ChatPeer) Naming.lookup(args[2]);
                remotePeer.addPeer(thisLocalPeer);
                thisLocalPeer.addPeer(remotePeer);
            }

            BlockingQueue<String> sharedMessageQueue = new LinkedBlockingQueue<>();
            ExecutorService executor = Application.getInstance().getFixedThreadPool(2);
            executor.execute(new MessageProducer(sharedMessageQueue));
            executor.execute(new MessageConsumer(sharedMessageQueue, thisLocalPeer.getPeers(), thisLocalPeer));
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
