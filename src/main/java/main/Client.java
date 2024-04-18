package main;

import java.nio.channels.SocketChannel;

public class Client {

    public Client(SocketChannel c) {
        channel = c;
    }
    boolean firstMessageFromClient = true;
    SocketChannel channel;


    public boolean isFirstMessageFromClient() {
        return firstMessageFromClient;
    }

    public void setFirstMessageFromClient(boolean firstMessageFromClient) {
        this.firstMessageFromClient = firstMessageFromClient;
    }

    public SocketChannel getChannel() {
        return channel;
    }

    public void setChannel(SocketChannel channel) {
        this.channel = channel;
    }
}
