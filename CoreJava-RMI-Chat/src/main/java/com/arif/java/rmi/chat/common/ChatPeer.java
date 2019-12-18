package com.arif.java.rmi.chat.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author arif
 */
public interface ChatPeer extends Remote {
    void sendMessage(String peerName, String message) throws RemoteException;
    void addPeer(ChatPeer peer) throws RemoteException;
    void removePeer(ChatPeer peer) throws RemoteException;
    String getPeerName() throws RemoteException;
}
