package com.palo.palo.fragments.directMessage;

import org.java_websocket.handshake.ServerHandshake;

public interface IDirectMessageSocketListener {
    public void connectWebSocket();
    public void onOpen(ServerHandshake serverHandshake);
    public void onMessage(String msg);
    public void onClose(int errorCode, String reason, boolean remote);
    public void onError(Exception e);
}
