package main;

import java.nio.channels.SocketChannel;

public class Client {
    boolean firstMessageFromClient = true;
    SocketChannel channel;

    public Client(SocketChannel c) {
        channel = c;
    }

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
