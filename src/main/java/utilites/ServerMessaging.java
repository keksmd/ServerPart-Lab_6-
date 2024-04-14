package utilites;

import com.fasterxml.jackson.core.type.TypeReference;
import exceptions.LOLDIDNTREAD;
import main.Request;
import main.Response;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import static main.App.log;


public class ServerMessaging {

    public static Request nioRead(SocketChannel clientChannel) throws IOException, LOLDIDNTREAD {
        ByteBuffer buf = ByteBuffer.allocate(clientChannel.socket().getReceiveBufferSize());
        int readed = clientChannel.read(buf);
        if (readed > 0) {
            buf.flip();
            String msg = new String(buf.array());
            log.info("readed {} , after seserialization {}", msg, ObjectConverter.toJson(ObjectConverter.deserialize(msg, new TypeReference<>() {
            })));
            return ObjectConverter.deserialize(msg, new TypeReference<>() {
            });
        } else throw new LOLDIDNTREAD();
    }

    public static void nioSend(SocketChannel clientChannel, String message) throws IOException {
        Response resp = new Response();
        resp.addMessage(message);
        message = ObjectConverter.toJson(resp);
        log.info("sended {}", message);
        ByteBuffer buf = ByteBuffer.allocate(message.getBytes().length).put(message.getBytes());

        buf = buf.flip();
        while (buf.hasRemaining()) {
            clientChannel.write(buf);
        }
    }

    public static void nioSend(SocketChannel clientChannel, Response resp) throws IOException {
        String message = ObjectConverter.toJson(resp);
        log.info("sended {}", message);
        ByteBuffer buf = ByteBuffer.allocate(message.getBytes().length);

        buf.put(message.getBytes());
        buf = buf.flip();
        while (buf.hasRemaining()) {
            clientChannel.write(buf);
        }
    }

}
